package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ForAllExpression extends ABinaryExpression implements IForAllExpression {

	public ForAllExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitAllExpression(this);
	}
}
