package o.indexer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PositiveScoresOnlyCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Indexer {

    Indexer(File docsDir, File indexDir) {
        checkDirs(docsDir, indexDir);
        this.docsDir = docsDir;
        this.indexDir = indexDir;
    }

    public boolean createIndex() {
        boolean creationStatus = false;
        Analyzer analyzer;
        Directory directory;
        IndexWriterConfig idxWriterConfig;
        IndexWriter indexWriter;
        try {        
            // An Analyzer builds TokenStreams, which analyze text. 
            // It thus represents a policy for extracting index terms from text.
            analyzer = new StandardAnalyzer(Version.LUCENE_35);
    
            // A Directory is a flat list of files. 
            // Files may be written once, when they are created. 
            // Once a file is created it may only be opened for read, or deleted. 
            // Random access is permitted both when reading and writing.
            directory = new SimpleFSDirectory(this.indexDir);
            
            // Holds all the configuration of IndexWriter.
            idxWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);

            //An IndexWriter creates and maintains an index.
            indexWriter = new IndexWriter(directory, idxWriterConfig);
            
            File[] files = this.docsDir.listFiles();
            for(File file : files) {
                // Documents are the unit of indexing and search. 
                // A Document is a set of fields. Each field has a name and a textual value.
                Document document = new Document();

                FileReader fileReader = new FileReader(file);
                // A field is a section of a Document. Each field has two parts, a name and a value. 
                // Values may be free text, provided as a String or as a Reader, 
                // or they may be atomic keywords, which are not further processed.
                Field pathField = new Field(FLD_PATH, file.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED);
                Field contentField = new Field(FLD_CONTENT, fileReader);
                // Field.Store.YES Save the field in the index to get it directly from the Document.
                // Field.Index.NO  Do not index the field, so no search can be made with this Field.
                Field titleField = new Field(FLD_TITLE, getTitle(fileReader), Field.Store.YES, Field.Index.NO);
                //Feed the Document object with the Fields.
                document.add(pathField);
                document.add(contentField);
                document.add(titleField);

                log.debug("Adding the document with: path=" + pathField + "|title=" + titleField + "|content=" + contentField);
                indexWriter.addDocument(document);
            }
            indexWriter.close();
            creationStatus = true;
        } catch (CorruptIndexException e) {
            log.error(e.getMessage());
        } catch (LockObtainFailedException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return creationStatus;
    }
    
    public long getHitsFor(String word, int proximityValue) {
        boolean readOnly = true;
        float proximity = (float) proximityValue / 10;
        int maxHits = 25;
        long hitCounter = -1;
        String clause;
        // IndexReader is an abstract class, providing an interface for accessing an index. 
        // Search of an index is done entirely through this abstract interface, so that 
        // any subclass which implements it is searchable.
        IndexReader idxReader;
        // Implements search over a single IndexReader.
        IndexSearcher idxSearcher;
        // Class to query the index.
        QueryParser queryParser;
        // The actual Query
        Query query;
        // Represents hits returned
        TopDocs topDocs;
        try {
            idxReader = IndexReader.open(FSDirectory.open(this.indexDir), readOnly);
            idxSearcher = new IndexSearcher(idxReader);

            queryParser = new QueryParser(Version.LUCENE_35, FLD_CONTENT, new StandardAnalyzer(Version.LUCENE_35));
            clause = FLD_CONTENT + SYMBOL_COLON + SYMBOL_DOUBLE_QUOTE + word + SYMBOL_DOUBLE_QUOTE + SYMBOL_TILDE + proximity;
            log.debug("Parsing the clause: " + clause);
            //http://lucene.apache.org/core/old_versioned_docs/versions/3_5_0/queryparsersyntax.html
            query = queryParser.parse(clause);

            log.debug("Searching...");
            topDocs = idxSearcher.search(query, maxHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for(ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = idxSearcher.doc(docId);
                log.debug(FLD_TITLE + SYMBOL_COLON + doc.getFieldable(FLD_TITLE).stringValue());
            }

            hitCounter = topDocs.totalHits;
            log.debug("Found :" + hitCounter);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return hitCounter;
    }
    
    private void checkDirs(File d, File i) {
        if(d == null || i == null) {
            throw new IllegalArgumentException();
        }

        if(!d.exists()) {
            throw new IllegalArgumentException(ERR_MSG_NON_EXISTENT_DIR 
                                                + SYMBOL_COLON + d.getAbsolutePath());
        }
        if(!i.exists()) {
            boolean created = i.mkdir();
            if(!created) {
                throw new IllegalArgumentException(ERR_MSG_CREATION_DIR 
                        + SYMBOL_COLON + i.getAbsolutePath());
            }
        }
    }
    
    private String getTitle(FileReader fileReader) {
        String str = "";
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(fileReader);
            str = bufferedReader.readLine();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return str;
    }

    private static final Logger log = LoggerFactory.getLogger(Indexer.class);

    private final File docsDir;
    private final File indexDir;
    private final String FLD_CONTENT = "content";
    private final String FLD_PATH = "path";
    private final String FLD_TITLE = "title";

    private final String SYMBOL_COLON = ":";
    private final String SYMBOL_DOUBLE_QUOTE = "\"";
    private final String SYMBOL_TILDE = "~";

    private final String ERR_MSG_NON_EXISTENT_DIR = "Directory does not exist";
    private final String ERR_MSG_CREATION_DIR = "Error creating directory";
}
