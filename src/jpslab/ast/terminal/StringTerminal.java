package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IStringTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StringTerminal extends ATermianlExpression<String> implements IStringTerminal{

	public StringTerminal(String ter) {
		super(ter);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitStringTerminal(this);
	}	
}
