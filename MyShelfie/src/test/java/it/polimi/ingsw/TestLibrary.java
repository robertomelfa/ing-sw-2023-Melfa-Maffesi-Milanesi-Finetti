package it.polimi.ingsw;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestLibrary extends TestCase
{
    Library lib;
    @Before
    public void setup(){
        lib=new Library();
    }

    @Test
    public void TestLibraryConstructor_allgridNONE(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                assertTrue(lib.getPos(i,j)==Card.NONE);
            }
        }
    }

    public void teardown(){
        lib=null;
    }
}
