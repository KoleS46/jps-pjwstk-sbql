package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class LessThanExpression extends ABinaryExpression implements ILessThanExpression{

	public LessThanExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitLessThanExpression(this);
	}
}
