package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class UniqueExpression extends AUnaryExpression implements IUniqueExpression{

	public UniqueExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitUniqueExpression(this);
	}
}
