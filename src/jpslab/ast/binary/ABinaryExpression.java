package jpslab.ast.binary;

import jpslab.ast.exp.AExpression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IBinaryExpression;

public abstract class ABinaryExpression extends AExpression implements IBinaryExpression {
	private IExpression leftExp;
	private IExpression rightExp;
	
	public ABinaryExpression(IExpression left, IExpression right){
		this.leftExp = left;
		this.rightExp = right;
	}
	
	@Override
	public IExpression getLeftExpression(){
		return this.leftExp;
	}
	
	@Override
	public IExpression getRightExpression(){
		return this.rightExp;
	}

}
