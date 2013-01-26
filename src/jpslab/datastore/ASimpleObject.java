package jpslab.datastore;

import edu.pjwstk.jps.datastore.ISimpleObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class ASimpleObject<T> extends ASBAObject implements ISimpleObject<T>{

	private T obj;
	
	public ASimpleObject(OID oid, String name, T obj) {
		super(name, oid);
		this.obj = obj;
		// TODO Auto-generated constructor stub
	}
	
	public T getValue(){
		return this.obj;
	}

	@Override
	public String toString(){
		return "<"+super.toString()+",\""+this.obj.toString()+"\">";
	}
	
}
