package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IAndExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AndExpression extends ABinaryExpression implements IAndExpression {

	public AndExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitAndExpression(this);
	}
	
}
