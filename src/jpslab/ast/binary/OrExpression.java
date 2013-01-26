package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IOrExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class OrExpression extends ABinaryExpression implements IOrExpression{

	public OrExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitOrExpression(this);
	}
}
