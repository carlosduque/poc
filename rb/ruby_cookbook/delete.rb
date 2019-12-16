#!/usr/bin/env ruby

# encoding: utf-8
# delete.rb
def delete_matching_regexp(dir, regex)
  Dir.entries(dir).each do |name|
    path = File.join(dir, name)
    if name =~ regex
      ftype = File.directory?(path) ? Dir : File
      begin
        ftype.delete(path)
      rescue SystemCallError => e
        $stderr.puts e.message
      end
    end
  end
end

#Dir.entries(tmp_dir)
  ### => [".", "..", "A", "A.txt", "A.html", "p.html", "A.html.bak",
  ###     "text.dir", "Directory.for.html"]
#delete_matching_regexp(tmp_dir, /^[A-Z].*\.[^.]{4,}$/)
#Dir.entries(tmp_dir)
  ### => [".", "..", "A", "A.txt", "p.html", "A.html.bak", "text.dir"]