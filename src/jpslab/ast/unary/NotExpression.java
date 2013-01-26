package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.INotExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NotExpression extends AUnaryExpression implements INotExpression{

	public NotExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitNotExpression(this);
	}
}
