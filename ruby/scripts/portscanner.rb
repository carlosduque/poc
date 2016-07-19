# encoding: utf-8
require 'socket'
require 'timeout'
require 'optparse'

class PortScanner
  def initialize(argv)
    @options = Options.new(argv)
  end

  def run
    begin
      hosts = Array.new
      ports = Array.new
      File.open(@options.defined[:hosts], "r").each_line do |h|
        hosts << h.chomp
      end

      File.open(@options.defined[:ports], "r").each_line do |p|
        ports << p.chomp
      end
     
      hosts.each do |host|
        ports.each do |port|
          print "connecting to #{host} :% 5d" % port.to_i + " "
          begin
            Timeout::timeout(10) do
                skt = TCPSocket.new("#{host}", port)
                skt.close
            end
          rescue Errno::ECONNREFUSED, Errno::EHOSTUNREACH
            puts "[CLOSED]"
          rescue Timeout::Error
            puts "[TIMEOUT]"
          rescue
            puts "[ERROR]"
          else
            puts "[OPEN]"
          end
        end
      end
    rescue Exception => e
      puts e.to_s
    end
  end
end

class Options
  attr_reader :defined
  def initialize(argv)
    @defined = Hash.new
    parse(argv)
  end

  private
  def parse(argv)
    optparse = OptionParser.new do |opts|
      opts.banner = "Usage: ruby #{File.basename(__FILE__)} [options] HOSTS PORTS"

     opts.on("-o", "--output [OUTPUTFILE]", "File to store the output") do |out|
        @defined[:output] = out || "out.txt"
      end

     opts.on("-h", "--help", "Show this message") do
       puts opts
       exit
     end
    end
    optparse.parse!(argv)
    if argv.empty?
      puts optparse
      exit(-1)
    end
    @defined[:hosts] = argv.shift
    @defined[:ports] = argv.shift
  end
end

scanner = PortScanner.new(ARGV)
scanner.run

