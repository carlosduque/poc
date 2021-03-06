#!/usr/bin/env ruby

# encoding: utf-8
# find_duplicates2.rb
require 'find'
BLOCK_SIZE = 1024*8
def each_set_of_duplicates(*paths, &block)
  sizes = Hash.new {|h, k| h[k] = [] }
  Find.find(*paths) { |f| sizes[File.size(f)] << f if File.file? f }
  sizes.each_pair do |size, files|
    next unless files.size > 1
    files = [files]
    while !files.empty? && offset <= size
      files = eliminate_non_duplicates(files, size, offset, &block)
      offset += BLOCK_SIZE
    end
  end
end

def eliminate_non_duplicates(partition, size, offset)
  possible_duplicates = []
  partition.each do |possible_duplicate_set|
    blocks = Hash.new {|h, k| h[k] = [] }
    possible_duplicate_set.each do |f|
      block = open(f, 'rb') do |file|
        file.seek(offset)
        file.read(BLOCK_SIZE)
      end
      blocks[block || ''] << f
    end
    blocks.each_value do |files|
      if files.size > 1
        if offset+BLOCK_SIZE >= size
          # We know these are duplicates.
          yield files
        else
          # We suspect these are duplicates, but we need to compare
          # more blocks of data.
          possible_duplicates << files
        end
      end
    end
  end
  return possible_duplicates
end

each_set_of_duplicates(*ARGV) do |f|
      puts "Duplicates: #{f.join(", ")}"
end