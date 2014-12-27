package org.riktov.prlisp ;

class PrimitiveAdditionProcedure extends PrimitiveProcedure {
    public PrimitiveAdditionProcedure() {
        symbol = "+" ;
    }
    public LispObject apply(LispObject []argVals) {
        Number num0 = (Number)((Atom)argVals[0]).data() ;
        Number num1 = (Number)((Atom)argVals[1]).data() ;
        
        return new Atom(num0.floatValue() + num1.floatValue()) ;
    }
}

class PrimitiveSubtractionProcedure extends PrimitiveProcedure {
    public PrimitiveSubtractionProcedure() {
        symbol = "-" ;
    }
    public LispObject apply(LispObject []argVals) {
        Atom arg0 = (Atom)argVals[0] ;
        Atom arg1 = (Atom)argVals[1] ;
        
        return new Atom((int)arg0.data() - (int)arg1.data()) ;
    }
}

class PrimitiveConsProcedure extends PrimitiveProcedure {
    public PrimitiveConsProcedure() {
        symbol = "cons" ;
    }
    public LispObject apply(LispObject []argVals) {
        return new ConsCell(argVals[0], argVals[1]) ;
    }
}

class PrimitiveCarProcedure extends PrimitiveProcedure {
    public PrimitiveCarProcedure() {
        symbol = "car" ;
    }
    public LispObject apply(LispObject []argVals) {
        return argVals[0].car() ;
    }
}
