package jpslab.result;

import edu.pjwstk.jps.result.IBooleanResult;

public class BooleanResult extends ASimpleResult<Boolean> implements IBooleanResult {

	public BooleanResult(Boolean res) {
		super(res);
	}

	public String toString(){
		String str = "BooleanResult(";
		str += this.result.toString();
		str += ")";
		return str;
	}
}
