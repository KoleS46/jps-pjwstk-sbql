package jpslab.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IModuloExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ModuloExpression extends ABinaryExpression implements IModuloExpression{

	public ModuloExpression(IExpression left, IExpression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitModuloExpression(this);
	}
}
