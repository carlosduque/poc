#!/usr/bin/env ruby

# encoding: utf-8

require 'fileutils'

module Utils
  class Renamer
    attr_accessor :dir, :outdir

    def initialize(basedir, outdir)
      @dir = basedir
      @outdir = outdir
    end

    def each_file_matching(p)
      pattern = @dir + File::SEPARATOR + '**' + File::SEPARATOR + p
      Dir.glob(pattern) do |file|
        next unless File.file? file # Skip directories, etc
        path, name = File.split(file)
        new_name = yield name
        new_path = @outdir + path[@dir.length..(file.index(name) - 1)]
        new_file = new_path + File::SEPARATOR + new_name
        FileUtils.mkdir_p new_path unless File.exists?(new_path)
        puts "renaming #{file} --> #{new_file}" if $DEBUG
        File.rename(file, new_file)
      end
    end
  end
end

if __FILE__ == $0
  basedir = ARGV[0]
  pattern = ARGV[1]
  outdir = ARGV[2]
  raise ArgumentError, "Usage: ruby #{File.basename(__FILE__)} BASEDIR 'PATTERN' OUTPUTDIR" unless not basedir.nil?
  $DEBUG = true
  renamer = Utils::Renamer.new(basedir, outdir)
  renamer.each_file_matching(pattern) do |filename| 
    filename.gsub(/\s+/, '_').gsub(/_+/,'_').gsub(/[^a-zA-Z0-9_\.]/, '').downcase 
  end
end

