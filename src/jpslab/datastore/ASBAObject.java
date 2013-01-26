package jpslab.datastore;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class ASBAObject implements ISBAObject {

	private String name;
	private OID oid;
	
	public ASBAObject(String name, OID oid){
		this.name = name;
		this.oid = oid;
	}
	
	public String getName(){
		return this.name;
	}
	
	public OID getOID(){
		return this.oid;
	}
	
	@Override
	public String toString(){
		return this.oid.toString()+", "+this.name.toString()+"";
	}
	
}
