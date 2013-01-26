package jpslab.result;

import edu.pjwstk.jps.result.IIntegerResult;

public class IntegerResult extends ASimpleResult<Integer> implements IIntegerResult{

	public IntegerResult(Integer res) {
		super(res);
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		String str = "IntegerResult(";
		str += this.result.toString();
		str += ")";
		return str;
	}
}
