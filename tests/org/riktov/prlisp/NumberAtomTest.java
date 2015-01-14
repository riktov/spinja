package org.riktov.prlisp;

import static org.junit.Assert.assertTrue;

//import org.junit.Before;
import org.junit.Test;


public class NumberAtomTest {
/**
 *    @Before
    public void setUp() {
        str = new StringAtom("I am a StringAtom.") ;
    }
*/
	
    @Test public void testIntegerObjectAtom() {
    	ObjectAtom iA = (ObjectAtom)DataAtom.make(41) ;
    	
       assertTrue(iA.toString().equals("41")) ;
       assertTrue(iA.data.equals(41)) ;
       
       ObjectAtom oDA = (ObjectAtom)DataAtom.make(26) ;
	
       assertTrue(iA.toString().equals("41")) ;	//not 41.0
       assertTrue(oDA.toString().equals("26")) ; //not 26.0

    }
  
}
