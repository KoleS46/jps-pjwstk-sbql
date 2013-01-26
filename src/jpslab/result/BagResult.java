package jpslab.result;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult extends ACollectionResult implements IBagResult {

	private Collection<ISingleResult> coll = new LinkedList<ISingleResult>();
	
	public BagResult(){}
	
	public BagResult(ISingleResult... coll){
		for(ISingleResult i: coll){
			this.coll.add(i);
		}
	}
	
	public BagResult(Collection<ISingleResult> coll) {
		for(ISingleResult i: coll){
			this.coll.add(i);
		}
	}

	public void add(IAbstractQueryResult res) {
		if(res instanceof ISingleResult) {
			add((ISingleResult)res);
		} else if(res instanceof IBagResult) {
			add((IBagResult)res);
		}
	}
	
	public void add(ISingleResult bag) {
		this.coll.add(bag);
	}

	public void add(IBagResult bag) {
		this.coll.addAll(bag.getElements());
	}
	
	@Override
	public Collection<ISingleResult> getElements() {
		return this.coll;
	}
	
	@Override
	public String toString(){
		int count = 0;
		String text = "(QRES) bag(";
		Iterator<ISingleResult> coll = this.coll.iterator();
		while(coll.hasNext()){
			text += count+"=\""+coll.next().toString()+"\"";
			if(coll.hasNext()){
//				text += ",\n\t";
				text += ",";
			}
			count++;
		}
		text += ")";
		return text;
	}
}
