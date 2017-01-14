#!/usr/bin/env ruby

IO.foreach(ARGV[0]) { |line| puts line }

puts "read :: #{ARGV[0]} ::"
