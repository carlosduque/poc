"""
Hardscan.py -- A brute force trustworthy equivalent to netstat
Copyright (C) 2005 Eli Fulkerson

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

----------------------------------------------------------------------

Other license terms may be negotiable.  Contact the author if you would
like a copy that is licensed differently.

Contact information (as well as this script) lives at http://www.elifulkerson.com

"""

import sys

from socket import socket, SOCK_STREAM, SOCK_DGRAM, AF_INET, SOL_SOCKET,SO_REUSEADDR
from random import randint
from threading import Thread
from time import time, localtime, asctime, sleep
from select import select
from getopt import getopt, GetoptError
from string import split

def usage():
    usagestring = """
Usage: hardscan [OPTION]
Scan the local machine for any open ports without trusting the accuracy of
'netstat' or similar tools.

  -f, --fast             Don't do the full validation, just try to open
                         a listening socket on each port.
                         (default is Off)

  -h, --help             Display this documentation.

  -i, --interval         Display a progress update every specified interval.
                         (default is 100)

                         Example Usage:
                         -i 500

  -l, --length           Specify the length of key to exchange during
                         validation.
                         (default is 4)

                         Example Usage:
                         -l 5

  -m, --max-threads      Specify the max number of client/server pairs to use.
                         Note that the real number of threads the program uses
                         will be something along the lines of (max-threads*3)+1
                         due to the subthreading involved.

                         Example Usage:
                         --max-threads 10
                         -m 10

  -p, --port             Specify the ports to check.

                         Example Usage:
                         --port=40,50,60
                         --port 1-1024
                         -p 1-500,400,500,1000-2000

  -s, --sockettype       Specify "tcp" or "udp"
                         (default is tcp)

                         Example Usage:
                         --sockettype tcp
                         -s udp

  -t, --timeout          Specify how long server threads should wait for their
                         connection.
                         (default is 1 second)

                         Example Usage:
                         --timeout 10

  -v, --verbose          Show verbose output.  This will show, for instance,
                         every port discovered as it happens.
                         (default is Off)

  -w, --very-verbose     Show very verbose output.  You almost certainly don't
                         want to use this option.
                         (default is Off)

Hardscan operates on the premise that your system has been compromised, and
netstat and any other kernel reporting tools can not be trusted.  Rather than
trusting what the OS reports, it manually attempts to open every port that you
specify (by default every tcp port on the system) and pass data to itself.  If
this communication succeeds, it is assumed that the port was not in use.

This differs from 'netstat', which only reports what the OS thinks is happening.
It also differs from 'nmap', in that it is capable of servers or outgoing
connections even if they are configured to lurk in stealth mode.

As outgoing TCP connections use ephemeral ports, it is quite likely that you
are going to get false positives based on whatever it is the machine is doing.
These can be verified against netstat or the like to determine if they are
legitimate. This has the side benefit of identifying outgoing connections even
if they are not in a 'listening' mode.
"""
    print usagestring

        
def uniq(list):
    """
    this function removes duplicate items from a list
    """
    uniqued_list = {}
    for each in list:
        uniqued_list[each] = 1
    return uniqued_list.keys()


def format_hour_min_sec( number_of_seconds ):
    seconds = number_of_seconds % 60
    minutes = int(number_of_seconds / 60)
    
    hours = int(minutes / 60 )
    minutes = minutes % 60

    if hours > 0:
        return "%sh %sm %ss" % (hours,minutes,seconds)
    if minutes > 0:
        return "%sm %ss" % (minutes, seconds)
    return "%ss" % (seconds)

                
def unmark_suspicious_port(port):
    """
    central place to unmark suspicious ports that we changed our mind about
    """
    global dirty_ports

    try:
        dirty_ports.pop(port)
    except:
        pass

def mark_strict_port(port):
    """
    central place to mark suspicious ports.
    """
    global strict_dirty_ports
    global currently_used_by_clients
    global ports_to_check
    global collided_with_self
    global used_by_clients_ever
    
    global VERBOSE

    """
    if we are currently using this outgoing port in another thread, we can't mark it as suspicious.
    """
    if port in currently_used_by_clients:
        if VERBOSE:
            print "MAIN:   Collision for port %s ...putting it back in the todo list" % (port)
        PORTS_TO_CHECK.append(port)
        return

    tmp = len(used_by_clients_ever)
#    if VERBOSE:
#        print "We are checking port %s against the %s ports we've already used." % (port, tmp)
    if port in used_by_clients_ever:
        if VERBOSE:
            print "MAIN: Strict failure on port %s, but possible internal collision." % (port)
        collided_with_self[port]=port
        
    strict_dirty_ports[port] = port
    

    
def mark_suspicious_port(port):
    """
    central place to mark suspicious ports.
    """
    global dirty_ports
    global currently_used_by_clients
    global ports_to_check
    global VERBOSE

    """
    if we are currently using this outgoing port in another thread, we can't mark it as suspicious.
    """
    if port in currently_used_by_clients:
        if VERBOSE:
            print "MAIN:   Collision for port %s ...putting it back in the todo list" % (port)
        PORTS_TO_CHECK.append(port)
        return
        
    dirty_ports[port] = port


class Server(Thread):
    """
    a server thread waits on a specified port/type, and awaits 'data'.
    If it recieves 'data' properly, then the connection with its client sibling is
    successful, and nothing was using the specified port and type.
    """
    def __init__(self, port, type, data):
        Thread.__init__(self)

        self.port = port    # tcp/udp port we will be listening on
        self.type = type    # SOCK_STREAM for tcp, SOCK_DGRAM for udp
        self.data = data    # this was generated by random_key and is shared with the matching client to this server
        self.done = False   # when the thread terminates, self.done will be set to True

        global TIMEOUT
        self.time_to_stop = time() + TIMEOUT    # the thread will know it is 5 seconds old thusly        
        
    def run(self):
        global currently_used_by_servers
        global VERBOSE
        global VERY_VERBOSE
        global LENGTH_OF_KEY
        global TIMEOUT

        #global craplist

        incoming_data = ""  # if things are successful, this will match self.data

        """
        create a socket of the proper type and bind it to the specified port
        """
        sock = socket(AF_INET, self.type)

        try:
            sock.bind(('', self.port))
        except:
            try:
                sock.setsockopt(SOL_SOCKET,SO_REUSEADDR,1)
                sock.bind(('', self.port))
                mark_strict_port(self.port)
                if VERBOSE:
                    print "SERVER:  bound to port %s via SO_REUSEADDR" % (self.port)
            except:
                if VERBOSE:
                    print "SERVER: Couldn't bind to port %s" % (self.port)
                mark_suspicious_port(self.port)
                self.done = True
                sock.close()
                #craplist.remove(self)
                return

        "in fast mode, if we opened a port its enough"
        global FAST
        if FAST:
            self.done = True
            sock.close()
            return

        "The TCP version"
        if self.type == SOCK_STREAM:
            sock.listen(1)          # we don't need a queue of connections
            keep_listening = True   #use a boolean, because we can't use 'break' to get out of 2 loops
            currently_used_by_servers[self.port] = self.port

            "Listen for a TCP connection on our established socket until it is time_to_stop"
            while keep_listening and time() < self.time_to_stop:
                tmp = select([sock,], [], [], 0.1)[0]
                for conn in tmp:
                    connection,address = conn.accept()
                    incoming_data = connection.recv(LENGTH_OF_KEY)
                    connection.close()
                    keep_listening = False
                    
        #"The UDP version"
        #elif self.type == SOCK_DGRAM:
        else:
            sock.settimeout(TIMEOUT)
            "Listen for a UDP connection on our established socket until it is time_to_stop"
            currently_used_by_servers[self.port] = self.port
            try:
                while time() < self.time_to_stop:
                    incoming_data, address = sock.recvfrom(LENGTH_OF_KEY)
                    if incoming_data:
                        "we have some data, stop listening and check it out"
                        break
            except:
                pass # we most likely timed out here.

        if not incoming_data:
            "For some reason, we never got a connection before it was time_to_stop"
            if VERBOSE:
                print "SERVER: No data came in on port %s" % (self.port)
            mark_suspicious_port(self.port)
            
        else:
            if self.data == incoming_data:
                "The client and server properly exchanged their key."
                if VERY_VERBOSE:
                    print "SERVER: valid matching connection: %s,%s" % (self.data,incoming_data)

                "clean up any previous bad instances - this might have collided with itself on an earlier run"
                unmark_suspicious_port(self.port)
                pass
            else:
                if VERBOSE:
                    print "SERVER: Connection recieved, but transferred key does not match. %s  Expected: '%s' but got '%s'" % (self.port, self.data, incoming_data)
                mark_suspicious_port(self.port)

        "we are done with our socket, so clean it up"
        currently_used_by_servers.pop(self.port)
        sock.close()

        #craplist.remove(self)

        "Let our parent Thread know that we are done"
        self.done = True


class Client(Thread):
    """
    I removed all the parts where the client is marking the port as bad... if the server never hears from the
    client it marks it anyway.
    """
    def __init__(self, port, type, data):
        Thread.__init__(self)
        
        self.port = port    # tcp/udp target port
        self.type = type    # SOCK_STREAM for tcp, SOCK_DGRAM for udp
        self.data = data    # this was generated by random_key and is shared with the matching server to this client
        self.done = False   # when this thread terminates, this will be set to True

    def run(self):
        global currently_used_by_clients
        global used_by_clients_ever
        global currently_used_by_servers
        global dirty_ports

        global VERY_VERBOSE

        while 1:
            if self.port in currently_used_by_servers:
                break
            elif self.port in dirty_ports:
                if VERY_VERBOSE:
                    print "Client starting for port %s but server has already marked it as bad." % (self.port)
                break
            else:
                if VERY_VERBOSE:
                    print "Client is starting for port %s but server isn't ready yet, sleeping..." % (self.port)
        
        "create a socket to connect to the server"
        try:
            sock = socket(AF_INET, self.type)
            #sock.bind(('', 3333))
            
            sock.settimeout(1)                      # set the timeout to 1 second, so the thread won't block.  This requires python2.3
            sock.connect(('localhost', self.port))  # try to connect to our sibling server

            "take a note of what outgoing socket is now in use"
            origin_ip = sock.getsockname()[1]
            currently_used_by_clients[origin_ip] = origin_ip
            used_by_clients_ever[origin_ip] = origin_ip
            
        except:
            pass

        "OK, we successfully connected to something.  Send it our key so it will know who we are"
        try:
            sock.sendall(self.data)
        except:
            pass

        "We don't care if any of this was successful.  The server thread will note that stuff down."

        "Clean up our leftovers"
        try:
            sock.close()
        except:
            pass
        #craplist.remove(self)

        try:
            currently_used_by_clients.pop(origin_ip)
        except:
            pass
        self.done = True

        return

class portchecker(Thread):
    """
    each portchecker is a seperate thread, that is told to check a specific
    port/protocol combination.  It determines a random key, then launches a
    client and server to exchange that key between themselves.
    """
    def __init__(self, port, protocol="tcp"):
        Thread.__init__(self)

        """
        change tcp/udp string into its internal representation
        """
        if protocol == "tcp":
            self.protocol = SOCK_STREAM
        elif protocol == "udp":
            self.protocol = SOCK_DGRAM

        self.port = port            # This is the port we are going to check
        self.done = False           # when the check is complete, this will be set to True

        "Determine our random key"
        global LENGTH_OF_KEY
        buf = []
        for each in range(0, LENGTH_OF_KEY):
            buf.append( chr (randint(33,126)))
        self.data = "%s" * LENGTH_OF_KEY % tuple(buf)

        "determine when this thread needs to terminate"
        global TIMEOUT
        self.time_to_stop = time() + TIMEOUT

        "a server"
        self.server = Server(self.port, self.protocol, self.data)
        self.server.setName("server for %s" % self.port)

        #@@
        #global craplist
        #craplist.append(self.server)

        "If we are using '--fast', don't bother with the client threads at all"
        global FAST
        if not FAST:
            "the matching client"
            self.client = Client(self.port, self.protocol, self.data)
            self.client.setName("client for %s" % self.port)

            #@@
            #craplist.append(self.client)

        self.run()

    def getName(self):
        """
        This function is only here to report status if something is hung, it isn't
        functionally useful.
        """
        buf = "Checking port: " + str(self.port)
        try:
            if self.server.isAlive():
                buf = buf + " server thread is active, "
        except:
            buf = buf + " server thread doesn't exist, "
        try:
            if self.client.isAlive():
                buf = buf + "client thread is active."
        except:
            buf = buf + "client thread doesn't exist."
            
        return buf

    def run(self):
        "start up a server"
        self.server.start()

        global currently_being_tested

        global FAST
        if not FAST:
            "start up the matching client"
            self.client.start()

        if FAST:
            while not self.done:
                if self.server.done:
                    currently_being_tested.pop(self.port)
                    self.done = True
                    return
        
        "loop waiting for both threads to be 'done'"
        while not self.done:
            if self.server.done and self.client.done:
                currently_being_tested.pop(self.port)
                self.done = True                            # this thread is finished
                break

def main(argv=None):
    
    "we need 2.3 for sockets to be able to time out"
    version = split(split(sys.version)[0], ".")
    if map(int, version) < [2,3]:
        print "This program requires python 2.3 or higher."
        return

    "Pull in our arguments if we were not spoonfed some"
    if argv is None:
        argv = sys.argv

    "Parse our arguments"
    try:
        options, args = getopt(argv[1:], "p:s:fm:l:t:hvwi:", ["port=", "sockettype=", "fast", "max-threads=", "length=", "timeout=", "help", "verbose", "very-verbose", "interval="])
    except GetoptError:
        usage()
        return

    "These globals store our configured options"
    global PORTS_TO_CHECK
    global SOCKETTYPE
    global FAST
    global MAX_THREADS
    global LENGTH_OF_KEY
    global TIMEOUT
    global VERBOSE
    global VERY_VERBOSE

    "Here are our very sensible defaults"
    PORTS_TO_CHECK = range(1, 65536)    # one higher than max because range drops it
    SOCKETTYPE = "tcp"                  # as opposed to UDP
    FAST = False                        # we don't want fast mode
    MAX_THREADS = 10
    LENGTH_OF_KEY = 4
    TIMEOUT = 1
    VERBOSE = False
    VERY_VERBOSE = False
    PROGRESS_MOD = 100

    "Loop through the options and set our globals.  See usage() for documentation"
    for o,a in options:
        if o in ("-p", "--port"):
            PORTS_TO_CHECK = []
            ports = split(a,",")
            for each in ports:
                try:
                    each = int(each)
                    if each < 1 or each > 65535:
                        print "Invalid argument for -p : Valid ports are 1-65535"
                        return
                    if VERY_VERBOSE:
                        print "Checking port: %s" % each
                    PORTS_TO_CHECK.append(each)
                    
                except:
                    if "-" in each:
                        tmp = split(each, "-")
                        startport = int(tmp[0])
                        stopport = int(tmp[1])
                        if startport < 1 or stopport > 65535:
                            print "Invalid argument for -p : Valid ports are 1-65535"
                            return
                        if VERY_VERBOSE:
                            print "Checking range: %s - %s" % (startport, stopport)

                        PORTS_TO_CHECK.extend( range(startport, stopport+1) )

        if o in ("-s", "--sockettype"):
            if a in ("tcp", "udp"):
                SOCKETTYPE = a
            else:
                print "Illegal socket type.  Valid options are 'tcp' or 'udp'"
                return
            
        if o in ("-f", "--fast"):
            FAST = True
            
        if o in ("-m", "--max-threads"):
            try:
                a = int(a)
            except:
                a = -1
            if a < 1:
                print "Invalid argument for -m: Max threads must be a postitive integer."
                return
            MAX_THREADS = int(a)

        if o in ("-l", "--length"):
            try:
                a = int(a)
            except:
                a = -1
            if a < 1:
                print "Invalid argument for -l: Key length must be a postitive integer."
                return          
            LENGTH_OF_KEY = int(a)
            
        if o in ("-t", "--timeout"):
            try:
                a = int(a)
            except:
                a = -1
            if (a < 0 or a > 120):
                print "Invalid argument for -t : Valid timeouts are 0 to 120 seconds."
                return            
            TIMEOUT = int(a)
            
        if o in ("-h", "--help"):
            usage()
            return
            
        if o in ("-v", "--verbose"):
            print "Verbose mode enabled."
            VERBOSE = True

        if o in ("-w", "--very-verbose"):
            print "Extremely verbose mode enabled."
            VERBOSE = True
            VERY_VERBOSE = True

        if o in ("-i", "--interval"):
            try:
                a = int(a)
            except:
                a = -1
            if a < 10 or a > 10000:
                print "Invalid argument for -i : Valid intervals are between 10 and 10000."
                return
            PROGRESS_MOD = a

    global dirty_ports                  # suspect ports are noted here.
    global strict_dirty_ports           # a stricter list of dirty ports, where we do not use REUSEADDR
    global currently_being_tested       # we want to store who is being tested currently to avoid false positive collisions
    global currently_used_by_clients    # these are currently being used as outgoing ports by the clients
    global currently_used_by_servers    # these are currently being listened to by servers
    global start
    global remember_number_of_ports
    global used_by_clients_ever
    global collided_with_self



    global threadlist

    dirty_ports = {}
    strict_dirty_ports = {}
    currently_being_tested = {}
    currently_used_by_clients = {}
    used_by_clients_ever = {}
    collided_with_self = {}
    currently_used_by_servers = {}
    threadlist = []


    #global craplist
    #craplist = []

    print "hardscan - Brute force replacement for netstat"
    print "Eli Fulkerson, 2005.  The author reserves all rights."
    print "Starting scan at:", asctime( localtime()) + "." 
    print "(Press CTRL-C to terminate.  --help flag for help)"
    start = time()

    remember_number_of_ports = len(PORTS_TO_CHECK)

    global remember_ports_so_far
    remember_ports_so_far = 0
    warning_thread_limit_reached = False

    while len(PORTS_TO_CHECK) > 0:
        "first, pull out any dead threads"
        newlist = []
        for each in threadlist:
            if not each.done:
                newlist.append(each)
        threadlist = newlist

        "now, if we have under MAX_THREADS threads, fire off some more"
        if len(threadlist) < MAX_THREADS:
            remember_ports_so_far += 1

            "pick a new port out.  This is done randomly to avoid internal deadlock between the competing threads"
            port = PORTS_TO_CHECK[randint(0, len(PORTS_TO_CHECK)-1)]

            "remember that one of the threads is currently working on this port"
            currently_being_tested[port] = port

            "create a portchecker, it autoruns"
            newThread = portchecker(port, SOCKETTYPE)

            "add the new portchecker to our list of threads in progress"
            threadlist.append(newThread)

            "this port is being handled, remove it from the todo list"
            PORTS_TO_CHECK.remove(port)

        else:
            if warning_thread_limit_reached == False:
                print "Warning: Thread limit reached."
                warning_thread_limit_reached = True

        if remember_ports_so_far % PROGRESS_MOD == 0:
            "do the estimate of time remaining"
            sofar = time()
            time_taken = sofar - start

            "our estimate is a ratio, we know how long its taken to do X ports so far, and only Y are remaining"
            estimate = int(time_taken * remember_number_of_ports / remember_ports_so_far) - int(time_taken)

            print "   %s ports checked.  Estimated time remaining: %s" % (str(remember_ports_so_far), format_hour_min_sec(estimate) )
                                                                                  
    "wait for the rest of the threads to end"
    while len(threadlist) > 0:
        newlist = []
        for each in threadlist:
            if not each.done:
                newlist.append(each)
        threadlist = newlist

    "send our results"
    report()

    return

def report():
    global start
    global remember_ports_so_far
    global dirty_ports
    global strict_dirty_ports
    global collided_with_self


    print "Scan finished at:", asctime( localtime()), "after", str(int(time() - start)), "seconds."
    print ""
    print "Of the " + str(remember_ports_so_far) + " " + SOCKETTYPE + " ports checked, the following failed to exchange data:"

    "format and print the dirty port list"
    tmp = uniq(dirty_ports)
    tmp.sort()

    if len(tmp) == 0:
        print "     None."

    for each in tmp:
        print "     " + str(each) + "/" + SOCKETTYPE

    print ""
    print "The following ports required SO_REUSEADDR to bind the server:"

    tmp = uniq(strict_dirty_ports)
    tmp.sort()

    if len(tmp) == 0:
        print "     None."

    for each in tmp:
        print "     " + str(each) + "/" + SOCKETTYPE

    print ""
    print "The following ports may be false positives (they were used internally):"

    tmp = uniq(collided_with_self)
    tmp.sort()

    if len(tmp) == 0:
        print "     None."

    for each in tmp:
        print "     " + str(each) + "/" + SOCKETTYPE  

        
def print_threads():
    global threadlist
    print "There are... " + str(len(threadlist)) + " ... threads left"
    for each in threadlist:
        print each,each.getName()

    #global craplist
    #print "there are " + str(len(craplist)) + " ... crap threads left"
    #for each in craplist:
    #    print each, each.getName()

if __name__== "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print ""
        print "!!! Scan was cancelled by user, partial results follow: !!!"
        print "!!! Note:  Ports are checked in random order, so these  !!!"
        print "!!! results do not imply that any particular subset of  !!!"
        print "!!! ports were checked.!!!"
        #print_threads()
        report()


