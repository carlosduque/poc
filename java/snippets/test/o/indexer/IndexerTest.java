package o.indexer;

import java.io.File;
import java.io.IOException;

import o.indexer.Indexer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class IndexerTest {

    private static final Logger log = LoggerFactory.getLogger(IndexerTest.class);
    private static File existentDir = null;
    private Indexer indexer = null;
    private static File docsDir = null;
    private static File indexDir = null;

    @Before
    public void setup() {
        existentDir = new File("/tmp/dir");
        if(!existentDir.exists()) {
            existentDir.mkdir();
        }
        docsDir = new File("/tmp/docs-to-index");
        indexDir = new File("/tmp/index-dir");
        indexer = new Indexer(docsDir, indexDir);
    }
    @After
    public void teardown() {
        docsDir = null;
    }    

    public static void globalTeardown() {
        if(existentDir.exists()) {
            existentDir.delete();
        }

        if(indexDir.exists()) {
            File[] files = indexDir.listFiles();
            for(File file : files) {
                if(!file.delete()){
                    log.debug("unable to delete: " + file.getAbsolutePath());
                }
            }
            indexDir.delete();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexerCreatedWithNullPaths() {
        indexer = new Indexer(null, null);
        indexer = new Indexer(new File("."), null);
        indexer = new Indexer(null, new File("."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexerCreatedWithNonExistentDocsPath() {
        docsDir = new File("/tmp/nonexistent-docs-dir");
        // Initial setup
        if(docsDir.exists()) {
            docsDir.delete();
        }
        indexer = new Indexer(docsDir, existentDir);
    }
    
    @Test
    public void testCreationOfIndexDirectory() {
        indexDir = new File("/tmp/nonexistent-index-dir");
        // Initial setup
        if(indexDir.exists()) {
            indexDir.delete();
        }
        indexer = new Indexer(existentDir, indexDir);
        assertTrue("The directory should have been created.", indexDir.exists());
        indexDir.delete();
    }

    @Test
    public void testIndexCreation() {
        docsDir = new File("/tmp/docs-to-index");
        indexDir = new File("/tmp/index-dir");
        indexer = new Indexer(docsDir, indexDir);
        assertTrue("Index creation failed.", indexer.createIndex());
    }

    @Test
    public void testIndexQuery() {
        final String wrongNumberOfHitsMessage = "Search returned wrong number of hits.";
        int matchProximity = 10;
        int hits = 0;
        docsDir = new File("/tmp/docs-to-index");
        indexDir = new File("/tmp/index-dir");
        indexer = new Indexer(docsDir, indexDir);
        hits = (int) indexer.getHitsFor("lorem", matchProximity);
        assertThat(wrongNumberOfHitsMessage, hits, equalTo(1));
        hits = (int) indexer.getHitsFor("Dios", matchProximity);
        assertThat(wrongNumberOfHitsMessage, hits, equalTo(2));
        hits = (int) indexer.getHitsFor("Rey", matchProximity);
        assertThat(wrongNumberOfHitsMessage, hits, equalTo(2));
        hits = (int) indexer.getHitsFor("Quijote", matchProximity);
        assertThat(wrongNumberOfHitsMessage, hits, equalTo(1));
    }
}
