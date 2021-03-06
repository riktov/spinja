package org.riktov.spinja ;

import static org.junit.Assert.* ;

//import org.junit.Test ;
//import org.junit.Ignore;
import org.junit.* ;
import org.riktov.spinja.Atom;
import org.riktov.spinja.IntAtom;
import org.riktov.spinja.NilAtom;
import org.riktov.spinja.StringAtom;
import org.riktov.spinja.SymbolAtom;

//import org.junit.runners.JUnit4;

/**
 * Tests for {@link Foo}.
 *
 * @author riktov@freeshell.org (Paul Richter)
 */
public class NilAtomTest {
    private NilAtom n ;
    private StringAtom str ;
    private SymbolAtom sym ;
    private Atom num ;
    
    @Before
    public void setUp() {
        n = new NilAtom() ;
        str = new StringAtom("I am a StringAtom.") ;
        sym = new SymbolAtom("sym") ;
        num = new IntAtom(45) ;
    }
    
    @Test public void testNil() { assertTrue(n.isNull()) ; }
    @Test public void testNilAsString() { assertTrue(n.toString() == "NIL") ; }
    @Test public void testStringNotNil () { assertFalse(str.isNull()) ; }
    @Test public void testSymbolNotNil () { assertFalse(sym.isNull()) ; }
    @Test public void testNumNotNil () { assertFalse(num.isNull()) ; }
}
