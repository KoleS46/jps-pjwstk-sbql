package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IInExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class InExpression extends ABinaryExpression implements IInExpression {

	public InExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitInExpression(this);
	}
}
