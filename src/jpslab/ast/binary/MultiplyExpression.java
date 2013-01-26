package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IMultiplyExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MultiplyExpression extends ABinaryExpression implements IMultiplyExpression{

	public MultiplyExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitMultiplyExpression(this);
	}
}
