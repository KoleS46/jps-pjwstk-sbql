package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IMinExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MinExpression extends AUnaryExpression implements IMinExpression{

	public MinExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitMinExpression(this);
	}
}
