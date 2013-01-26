package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class IntegerTerminal extends ATermianlExpression<Integer> implements IIntegerTerminal{

	public IntegerTerminal(Integer ter) {
		super(ter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitIntegerTerminal(this);
	}	
}
