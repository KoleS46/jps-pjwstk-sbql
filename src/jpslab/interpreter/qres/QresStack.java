package jpslab.interpreter.qres;

import java.util.Iterator;
import java.util.LinkedList;

import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;

public class QresStack implements IQResStack{
	
	private LinkedList<IAbstractQueryResult> stack = new LinkedList<IAbstractQueryResult>();
	
	@Override
	public IAbstractQueryResult pop() {
		IAbstractQueryResult pop_val = this.stack.pop();
		System.out.println("POP: "+pop_val.toString());
		return pop_val;
	}

	@Override
	public void push(IAbstractQueryResult value) {
		// TODO Auto-generated method stub
		System.out.println("PUSH: "+value.toString());
		this.stack.push(value);
	}

	@Override
	public String toString(){
		String str = "";
		Iterator<IAbstractQueryResult> iter = this.stack.iterator();
		while(iter.hasNext()){
			str += iter.next().toString();
			if(iter.hasNext()){
				str += "\n";
			}
		}
		return str;
	}
}
