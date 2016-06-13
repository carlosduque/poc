#!/usr/bin/env jruby
require 'java'
require 'lib/lucene-core-3.6.1.jar'


java_import java.io.FileReader
java_import org.apache.lucene.analysis.standard.StandardAnalyzer
java_import org.apache.lucene.analysis.standard.StandardAnalyzer
java_import org.apache.lucene.document.Document
java_import org.apache.lucene.document.Field
java_import org.apache.lucene.index.IndexWriter
java_import org.apache.lucene.search.TopScoreDocCollector
java_import org.apache.lucene.store.Directory
java_import org.apache.lucene.store.SimpleFSDirectory
java_import org.apache.lucene.util.Version

class LIAIndexer

  def initialize(indexDir)
    dir = SimpleFSDirectory.open(java.io.File.new(indexDir))
    @writer = IndexWriter.new(dir, StandardAnalyzer.new(Version::LUCENE_30), true, IndexWriter::MaxFieldLength::UNLIMITED)
  end

  def close
    @writer.close
  end

  def index(dataDir, filter)
    files = java.io.File.new(dataDir).listFiles()
    files.each do |file|
        if(!file.isDirectory && 
          !file.isHidden && 
          file.exists && 
          file.canRead &&
          filter.accept(file))
          index_file(file)
        end
    end

    return @writer.numDocs
  end

  protected
  def get_document(file)
    doc = Document.new
    doc.add(Field.new("contents", FileReader.new(file)))
    doc.add(Field.new("filename", file.getName, Field::Store::YES, Field::Index::NOT_ANALYZED))
    doc.add(Field.new("fullpath", file.getCanonicalPath, Field::Store::YES, Field::Index::NOT_ANALYZED))
    return doc
  end

  private
  def index_file(file)
    puts("Indexing " + file.getCanonicalPath)
    doc = get_document(file)
    @writer.addDocument(doc)
  end

  class TextFilesFilter
    def accept(path)
      return path.getName.end_with?(".txt")
    end
  end

end

if __FILE__ == $0
  unless(ARGV.length == 2)
    puts("Usage: {$0} <index dir> <data dir>")
    exit
  end
  indexDir = ARGV[0]
  dataDir = ARGV[1]
  indexer = LIAIndexer.new(indexDir)
  numIndexed = indexer.index(dataDir, LIAIndexer::TextFilesFilter.new)
  indexer.close
  puts("Finished indexing #{numIndexed} files.")
end
