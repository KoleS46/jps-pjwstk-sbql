package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BooleanTerminal extends ATermianlExpression<Boolean> implements IBooleanTerminal{

	public BooleanTerminal(Boolean ter) {
		super(ter);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitBooleanTerminal(this);
	}	
}
