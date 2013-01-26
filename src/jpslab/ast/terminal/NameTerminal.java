package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

// TODO Spytać się o co chodzi z tym warningiem przy tym terminalu
@SuppressWarnings("rawtypes")
public class NameTerminal extends ATermianlExpression implements INameTerminal {
	
	@SuppressWarnings("unchecked")
	public NameTerminal(String ter) {
		super(ter);
	}

	@Override
	public String getName() {
		return this.ter.toString();
	}

	@Override
	public void accept(ASTVisitor vsitor) {
		vsitor.visitNameTerminal(this);
	}	
}