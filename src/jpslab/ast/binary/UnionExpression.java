package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IUnionExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class UnionExpression extends ABinaryExpression implements IUnionExpression{

	public UnionExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitUnionExpression(this);
	}
}
