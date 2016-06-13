#!/usr/bin/env jruby
require 'java'
require 'lib/lucene-core-3.6.1.jar'


java_import org.apache.lucene.analysis.standard.StandardAnalyzer
java_import org.apache.lucene.document.Document
java_import org.apache.lucene.document.Field
java_import org.apache.lucene.queryParser.QueryParser
java_import org.apache.lucene.search.IndexSearcher
java_import org.apache.lucene.search.Query
java_import org.apache.lucene.store.SimpleFSDirectory
java_import org.apache.lucene.util.Version

class LIAIndexSearcher
  def LIAIndexSearcher.search(indexDir, q)
    dir = SimpleFSDirectory.open(java.io.File.new(indexDir))
    is = IndexSearcher.new(dir)

    parser = QueryParser.new(Version::LUCENE_30, "contents", StandardAnalyzer.new(Version::LUCENE_30))
    query = parser.parse(q)

    hits = is.search(query, 10)
    puts("Found #{hits.totalHits} document(s) matched the query '#{q}':")
    hits.scoreDocs.each do |scoreDoc|
        doc = is.doc(scoreDoc.doc)
        puts(doc.get("fullpath"))
    end
    is.close
  end
end

if __FILE__ == $0
  unless(ARGV.length == 2)
    puts("Usage: #{$0} <index dir> <query>")
    exit
  end
  indexDir = ARGV[0]
  q = ARGV[1]
  LIAIndexSearcher.search(indexDir, q)
end
