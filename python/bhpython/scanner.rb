require 'socket'

def connect(host = "localhost", port = 80)
  response = "connecting to #{port} on port #{port}: "
  begin
    client = Socket.new(Socket::AF_INET, Socket::SOCK_STREAM, 0)
    addr = Socket.pack_sockaddr_in(port, host)
    client.connect addr
    client.write "GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n"
    data = client.recvfrom 4096
    response += "[ OPEN ]" if data
  rescue Exception => e
    response += "[CLOSED] #{e}"
  end

  puts response
end

if __FILE__ == $0
  if ARGV.length == 2
    connect(ARGV[0], ARGV[1])
  else
    puts "usage: #{__FILE__} <host> <port>"
    exit(-1)
  end
end
