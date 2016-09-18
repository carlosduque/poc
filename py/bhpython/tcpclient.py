#!/usr/bin/env python
import sys
import socket

def connect(host = "localhost", port = 80):
    response = "connecting to {0} on port {1}: ".format(host, port)
    try:
        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client.connect((host, port))
        client.send("GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n")
        data = client.recv(4096)
        if data:
            response += "[ OPEN ]"
    #except socket.error as msg:
    except IOError as msg:
        client.close()
        response += "[CLOSED] %s" % (msg)

    print response

if __name__ == "__main__":
    if len(sys.argv) == 3:
      connect(sys.argv[1], int(sys.argv[2]))
    else:
      print "usage: %s <host> <port>" % (sys.argv[0])
      sys.exit(2)

