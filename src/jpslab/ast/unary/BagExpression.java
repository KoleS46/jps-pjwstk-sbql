package jpslab.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BagExpression extends AUnaryExpression implements IBagExpression{

	public BagExpression(IExpression exp) {
		super(exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitBagExpression(this);
	}
}
