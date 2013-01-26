package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IAvgExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AvgExpression extends AUnaryExpression implements IAvgExpression{

	public AvgExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitAvgExpression(this);
	}
	
}
