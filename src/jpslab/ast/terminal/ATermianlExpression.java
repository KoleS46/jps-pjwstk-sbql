package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.ITerminalExpression;
import jpslab.ast.exp.AExpression;

public abstract class ATermianlExpression<T> extends AExpression implements ITerminalExpression<T> {

	protected T ter;
	
	public ATermianlExpression(T ter){
		this.ter = ter;
	}
	
	@Override
	public T getValue(){
		return this.ter;
	}
}
