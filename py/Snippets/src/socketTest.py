#!/usr/bin/python

import socket
from optparse import OptionParser
from sys import argv

"""
Utiliza una conexion via sockets.
"""

__author__ = 'Carlos Duque'
__version__ = '0.1'
__homepage__ = 'http://www.duque-murillo.com'
__date__ = '2009.03.07'

def send(_addr, _port, _stream):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((_addr, _port))
    s.send(_stream)
    data = s.recv(1024)
    print data
    s.close()
# /fetch

def main():

    """
    Start the socket test program.
    """
    usage = "usage: python %s -d 192.168.0.1 -p 80 -t 'GET / HTTP/1.0'"    
    parser = OptionParser(usage=usage)
    parser.add_option("-d", "--direccion", dest="addr", action="store", type="string", default="www.rae.es", help="Direccion IP destino.")
    parser.add_option("-p", "--puerto", dest="port", action="store", type="int", default=int(80), help="Puerto destino.")
    parser.add_option("-t", "--trama", dest="stream", action="store", type="string", default="GET / HTTP/1.1\nHost: www.rae.es\n\n", help="Trama a enviar.")
    (options, args) = parser.parse_args()
    
    send(options.addr, options.port, options.stream)
# /main

if __name__ == '__main__':
    main()
