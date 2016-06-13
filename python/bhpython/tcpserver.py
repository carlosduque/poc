import sys
import socket
import threading

def listen(bind_ip = "0.0.0.0", bind_port = 9999):
  server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  server.bind((bind_ip, bind_port))
  server.listen(5)
  print "[*] Listening on %s:%d" % (bind_ip, bind_port)
  return server

# this is our client-handling thread
def handle_client(client_socket):
    request = client_socket.recv(1024)
    print "[*] received: %s" % request
    client_socket.send("ACK!")
    client_socket.close()

if __name__ == "__main__":
  if len(sys.argv) == 3:
    server = listen(sys.argv[1], int(sys.argv[2]))
    while True:
        client, addr = server.accept()
        print "[*] Accepted connection from: %s:%d" % (addr[0], addr[1])
        # spin up our client thread to handle incoming data
        client_handler = threading.Thread(target=handle_client, args=(client,))
        client_handler.start()
  else:
    print "usage: %s <bind ip> <ind port>" % (sys.argv[0])
    sys.exit(2)
