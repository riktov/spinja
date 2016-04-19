package org.riktov.spinja;

//import java.util.List ;
//import java.util.ArrayList ;

/**
 * A LispProcedure can be a compound procedure (lambda), a primitive procedure, or a special operation
 * @author Paul RICHTER &lt;riktov@freeshell.de&gt;
 *
 */
abstract class LispProcedure extends LispObject {
	abstract LispObject apply(LispList argsToApply) ;
    public LispList processArguments(LispList argForms, Environment evalEnv) {
    	//System.out.println("LispProcedure.processArguments() : argForms: " + argForms) ;
		return argForms.listOfValues(evalEnv) ;
	}
    /**
     * requireArgumentCount - 
     * @param count - number of arguments this function requires
     * @param argForms - the actual passed arguments
     * @param procName - the function name, used for the error message
     * @return Normally returns true, otherwise throw a restart condition
     */
	protected boolean requireArgumentCount(int count, LispList argForms, String procName) {
		int len = argForms.length() ;
		if(len != count) {
			new LispRestarter().offerRestarts("The procedure " + procName + " has been called with " + len + " arguments; it requires exactly " + count + " argument(s).") ;
			throw new LispAbortEvaluationException() ;
		}	
		return true ;
	}

	protected boolean requireArgumentCountAtLeast(int count, LispList argForms, String procName) {
		int len = argForms.length() ;
		if(len < count) {
			new LispRestarter().offerRestarts("The procedure " + procName + " has ben called with " + len + " arguments; it requires at least " + count + " argument(s).") ;
			throw new LispAbortEvaluationException() ;
		}	
		return true ;
	}

}

class CompoundProcedure extends LispProcedure {
	private LispObject formalParams; //may be a list, or an Atom for list-binding
	private LispList body;
	private Environment env;

	// constructors
	/**
	 * Constructor
	 * @param formalParams
	 * @param body
	 * @param env
	 */
	public CompoundProcedure(LispObject formalParams, LispList body, Environment env) {
		this.formalParams = formalParams;
		this.body = body;
		this.env = env;
	}

	// implementation of LispObject
	/**
	 * Copying CLISP notation
	 */
	@Override public String toString() {
		ConsCell paramsAndBody = new ConsCell((LispObject)formalParams, (LispObject) body) ;
		return "#<FUNCTION :LAMBDA " + paramsAndBody.toString(true) + ">"; 
		}

	/**
	 * accessors
	 */
	LispObject formalParams() { return formalParams ; }//can be a list, or nil, or a rest-bound atom
	LispList body() { return body ; }	

	/**
	 * APPLY Evaluate in turn each subform of the procedure body, in an environment created by extending
	 * the procedure's own environment with the argument bindings.
	 * 
	 * @param argVals
	 *            This is an array of objects (evaluated) that is passed to the
	 *            method.
	 * @return LispObject The value of the last form in the procedure body
	 * @throws LispAbortEvaluationException 
	 */
	public LispObject apply(LispList argForms) {
		//System.out.println("CompoundProcedure.apply() : argForms: " + argForms + " formalParams: " + formalParams) ;
		Environment newEnv ;

		//TODO: modify to handle list-bound params (&rest in CL) 
		//where a single formal parameter might be bound to NIL
		
		ConsCell paramsToBind, valuesToBind ;			

		if(!formalParams.isAtom()) {	//normal params (define (foo arg1 arg2)...
			paramsToBind = (ConsCell)formalParams ;
			valuesToBind = (ConsCell)argForms ;
		} else {	//list-bound (define (foo . args) ...
			paramsToBind = new ConsCell(formalParams, NilAtom.nil) ;
			valuesToBind = new ConsCell((LispObject) argForms, NilAtom.nil) ;
		}
		
		if(!valuesToBind.isNull()) {
			newEnv = new ChildEnvironment(env);
			valuesToBind.bindToParams(paramsToBind, newEnv) ;
		} else {
			newEnv = env ;
		}
		return body.evalSequence(newEnv) ;
	}	
}
