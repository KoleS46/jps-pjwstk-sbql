package jpslab.result;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBinderResult;

public class BinderResult implements IBinderResult {

	private String name;
	private IAbstractQueryResult result;
	
	public BinderResult(String name, IAbstractQueryResult res){
		this.name = name;
		this.result = res;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public IAbstractQueryResult getValue() {
		// TODO Auto-generated method stub
		return this.result;
	}
	
	@Override
	public String toString(){
		return "binder(name=\""+this.name+"\",value=\""+this.result.toString()+"\")";
	}

}
