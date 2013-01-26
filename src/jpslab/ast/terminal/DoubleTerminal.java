package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class DoubleTerminal extends ATermianlExpression<Double> implements IDoubleTerminal{

	public DoubleTerminal(Double ter) {
		super(ter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitDoubleTerminal(this);
	}	
}
