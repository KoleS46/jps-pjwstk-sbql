package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StructExpression extends AUnaryExpression implements IStructExpression{

	public StructExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitStructExpression(this);
	}
}
