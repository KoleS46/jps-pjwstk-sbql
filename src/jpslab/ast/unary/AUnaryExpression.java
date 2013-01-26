package jpslab.ast.unary;

import jpslab.ast.exp.AExpression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUnaryExpression;

public abstract class AUnaryExpression extends AExpression implements IUnaryExpression {
	
	private IExpression iexp; 
	
	public AUnaryExpression(IExpression exp){
		this.iexp = exp;
	}
	
	@Override
	public IExpression getInnerExpression(){
		return this.iexp;
	}
}
