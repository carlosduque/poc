#!/usr/bin/env ruby

# encoding: utf-8

require 'digest/md5'

module Utils
  class DuplicateFinder
    attr_accessor :dir

    def initialize(basedir)
      @dir = basedir
    end

    def each_set_of_duplicates(p)
      sizes = {}
      pattern = @dir + File::SEPARATOR + p
      puts "pattern: #{pattern}" if $DEBUG
      Dir.glob(pattern) do |f|
        (sizes[File.size(f)] ||= []) << f if File.file? f
      end
      sizes.each do |size, files|
        next unless files.size > 1
        md5s = {}
        files.each do |f|
          digest = Digest::MD5.hexdigest(File.read(f))
          (md5s[digest] ||= []) << f
        end
        md5s.each { |sum, files| yield sum, files if files.size > 1 }
      end
    end
  end
end

if __FILE__ == $0
  basedir = ARGV[0]
  pattern = ARGV[1]
  raise ArgumentError, "Usage: ruby #{File.basename(__FILE__)} BASEDIR 'PATTERN'" unless not basedir.nil? and
        not pattern.nil?
  $DEBUG = true
  finder = Utils::DuplicateFinder.new(basedir)
  finder.each_set_of_duplicates(pattern) do |sum, files|
    # move to base dir renamed
    files.sort!
    files.each do |file|
      puts "#{sum}     #{file}" if $DEBUG
    end
  end
end
