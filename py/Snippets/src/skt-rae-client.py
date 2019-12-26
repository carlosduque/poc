#!/usr/bin/python

import sys
from socket import *
serverHost = 'www.duque-murillo.com' 	    # serverHost is localhost
serverPort = 80                   # use arbitrary port > 1024

s = socket(AF_INET, SOCK_STREAM)    # create a TCP socket


s.connect((serverHost, serverPort)) # connect to server on the port
s.send('GET / HTTP/1.1\nHost: www.duque-murillo.com\n\n')               # send the data
data = s.recv(1024)                 # receive up to 1K bytes
print data
