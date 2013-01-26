package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class PlusExpression extends ABinaryExpression implements IPlusExpression{

	public PlusExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitPlusExpression(this);
	}
}
