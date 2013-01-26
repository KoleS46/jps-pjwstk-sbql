package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IDivideExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class DivideExpression extends ABinaryExpression implements IDivideExpression {

	public DivideExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitDivideExpression(this);
	}
}
