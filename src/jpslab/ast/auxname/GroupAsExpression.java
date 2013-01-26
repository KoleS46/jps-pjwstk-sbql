package jpslab.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GroupAsExpression extends AAuxiliaryNameExpression implements IGroupAsExpression{

	public GroupAsExpression(String name, IExpression exp) {
		super(name, exp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		// TODO Auto-generated method stub
		vsitor.visitGroupAsExpression(this);
	}
	
}
