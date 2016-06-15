#!/usr/bin/env python
import sys
import socket

def udp_connect(host = "localhost", port = 123, data = "some data"):
  client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
  client.sendto(data, (host, port))
  data, addr = client.recvfrom(4096)
  print data

if __name__ == "__main__":
  if len(sys.argv) == 4:
    udp_connect(sys.argv[1], int(sys.argv[2]), sys.argv[3])
  else:
    print "usage: %s <host> <udp port> <data>" % (sys.argv[0])
    sys.exit(2)
