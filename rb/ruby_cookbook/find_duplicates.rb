#!/usr/bin/env ruby

# encoding: utf-8
# find_duplicates.rb
require 'find'
require 'digest/md5'
require 'pp'

def each_set_of_duplicates(*paths)
  sizes = {}
  Find.find(*paths) do |f|
    (sizes[File.size(f)] ||= []) << f if File.file? f
  end
  #pp sizes
  sizes.each do |size, files|
    next unless files.size > 1
    md5s = {}
    files.each do |f|
      digest = Digest::MD5.hexdigest(File.read(f))
      (md5s[digest] ||= []) << f
    end
    #pp md5s
    md5s.each { |sum, files| yield files if files.size > 1 }
  end
end

each_set_of_duplicates(*ARGV) do |f|
  puts "Duplicates: #{f.join(", ")}"
end
