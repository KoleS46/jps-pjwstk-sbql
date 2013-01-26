package jpslab.ast.exp;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public abstract class AExpression implements IExpression{
	
	@Override
	public void accept(ASTVisitor vsitor){
		throw new UnsupportedOperationException("No implementation");
	}
}
