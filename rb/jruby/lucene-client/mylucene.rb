require 'java'
require 'lib/lucene-core-3.6.1.jar'

java_import org.apache.lucene.analysis.Analyzer
java_import org.apache.lucene.analysis.standard.StandardAnalyzer
java_import org.apache.lucene.document.Document
java_import org.apache.lucene.document.Field
java_import org.apache.lucene.index.IndexReader
java_import org.apache.lucene.index.IndexWriter
java_import org.apache.lucene.index.IndexWriterConfig
java_import org.apache.lucene.queryParser.QueryParser
java_import org.apache.lucene.search.IndexSearcher
java_import org.apache.lucene.search.Query
java_import org.apache.lucene.search.TopScoreDocCollector
java_import org.apache.lucene.store.Directory
java_import org.apache.lucene.store.SimpleFSDirectory
java_import org.apache.lucene.util.Version

class Indexer
    def initialize(idxDir)
        @index = SimpleFSDirectory.new(java.io.File.new(idxDir))
        @analyzer = StandardAnalyzer.new(Version::LUCENE_36)
        @config = IndexWriterConfig.new(Version::LUCENE_36, @analyzer)
        @writer = IndexWriter.new(@index, @config)
    end

    def addDoc(key, value, storeStr, indexStr)
        store = case storeStr.downcase
                    when "y"
                        Field::Store::YES
                    when "n"
                        Field::Store::NO
                    else
                        nil
                end
        index = case indexStr.downcase
                    when "analyzed"
                        Field::Index::ANALYZED
                    when "not_analyzed"
                        Field::Index::NOT_ANALYZED
                    when "no"
                        Field::Index::NO
                    else 
                        nil
                end

        doc = Document.new()
        field = Field.new(key, value, store, index)
        doc.add(field)
        @writer.addDocument(doc)
    end

    def close
        @writer.close
    end

    def usage
        usage = <<-END

            idxr = Indexer.new("dir")
            idxr.addDoc(<fieldname>, <fieldvalue>, <stored [y|n]>, <indexed [analyzed|not_analyzed|no>])
            idxr.close

        END
        puts usage
    end

    def loadTestData
        addDoc("title", "Lucene in Action", "y", "analyzed")
        addDoc("title", "Lucene for Dummies", "y", "analyzed")
        addDoc("title", "Managing GigaBytes", "y", "analyzed")
        addDoc("title", "The art of computer science", "y", "analyzed")
        addDoc("title", "Dungeons & Dragons", "y", "analyzed")
        close()
        puts("test data loaded!") 
    end

end

class Searcher
    attr_reader :hitsPerPage

    def initialize(idxDir)
        @hitsPerPage = 10
        @index = SimpleFSDirectory.new(java.io.File.new(idxDir))
        @analyzer = StandardAnalyzer.new(Version::LUCENE_36)
        @reader = IndexReader.open(@index)
        @searcher = IndexSearcher.new(@reader)
        @collector = TopScoreDocCollector.create(@hitsPerPage, true)
    end

  public
    def hitsPerPage=(hpp)
        @hitsPerPage = hpp
        @collector = TopScoreDocCollector.create(@hitsPerPage, true)
    end
    def close()
        @searcher.close()
    end

    def usage
        usage = <<-END
            searcher = Searcher.new("dir")
            searcher.search(<fieldname>, <qry>)
            searcher.close
        END

        puts usage
    end

    def search(key, qry)
        parser = QueryParser.new(Version::LUCENE_36, key, @analyzer)
        query = parser.parse(qry)
        @searcher.search(query, @collector)
        hits = @collector.topDocs().scoreDocs

        # Display results
        puts("Found " + hits.length.to_s + " hits.")
        hits.each do |hit| 
            doc = @searcher.doc(hit.doc)
            puts("key: " + doc.get(key))
        end
    end

end

