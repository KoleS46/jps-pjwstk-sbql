package jpslab.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AsExpression extends AAuxiliaryNameExpression implements IAsExpression {

	public AsExpression(String name, IExpression exp) {
		super(name, exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		// TODO Auto-generated method stub
		vsitor.visitAsExpression(this);
	}

}
