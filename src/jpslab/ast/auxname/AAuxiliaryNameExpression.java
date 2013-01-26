package jpslab.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAuxiliaryNameExpression;
import jpslab.ast.exp.AExpression;

public abstract class AAuxiliaryNameExpression extends AExpression implements IAuxiliaryNameExpression{
	
	private String name;
	private IExpression exp;
	
	public AAuxiliaryNameExpression(String name, IExpression exp){
		this.name = name;
		this.exp = exp;
	}
	
	@Override
	public String getAuxiliaryName(){
		return this.name;
	}
	
	@Override
	public IExpression getInnerExpression(){
		return this.exp;
	}
}
