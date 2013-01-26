package jpslab.result;

import edu.pjwstk.jps.result.IDoubleResult;

public class DoubleResult extends ASimpleResult<Double> implements IDoubleResult {

	public DoubleResult(Double res) {
		super(res);
	}

	public String toString(){
		String str = "DoubleResult(";
		str += this.result.toString();
		str += ")";
		return str;
	}
}
