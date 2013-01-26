package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.ISumExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class SumExpression extends AUnaryExpression implements ISumExpression{

	public SumExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitSumExpression(this);
	}
}
