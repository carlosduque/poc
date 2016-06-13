package o.generics.mock;

import static org.easymock.EasyMock.*;

import o.mock.ClassUnderTest;
import o.mock.Collaborator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ExampleTest {

    private ClassUnderTest classUnderTest;
    private Collaborator mock;

    @Before
    public void setUp() {
        mock = createMock(Collaborator.class);  //1
        classUnderTest = new ClassUnderTest();
        classUnderTest.addListener(mock);
    }

    @Test
    public void testRemoveNonExistingDocument() {
        // 2 (we do not expect anything)
        replay(mock); //3
        classUnderTest.removeDocument("Does not exist");
        // mock.documentRemoved("Salt"); // this call is not expected
    }

    @Test
    public void testAddDocument() {
        mock.documentAdded("New Document"); //2
        replay(mock); //3
        //classUnderTest.addDocument("Wrong Document", new byte[0]);  //fails because the wrong document is added
        classUnderTest.addDocument("New Document", new byte[0]);
        verify(mock);
    }

    @Test
    @Ignore
    public void testAddAndChangeDocument() {
        //train the mock object
        mock.documentAdded("Document");
        mock.documentChanged("Document");
        mock.documentChanged("Document");
        mock.documentChanged("Document");

        //end of training
        replay(mock);

        classUnderTest.addDocument("Document", new byte[0]);
        classUnderTest.addDocument("Document", new byte[0]);
        classUnderTest.addDocument("Document", new byte[0]);
        classUnderTest.addDocument("Document", new byte[0]);

        //check the right behavior/calls
        verify(mock);
    }
}
