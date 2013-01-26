package jpslab.result;

import edu.pjwstk.jps.result.IStringResult;

public class StringResult extends ASimpleResult<String> implements IStringResult {

	public StringResult(String res) {
		super(res);
		// TODO Auto-generated constructor stub
	}

	public String toString(){
		String str = "StringResult(";
		str += this.result.toString();
		str += ")";
		return str;
	}
}
