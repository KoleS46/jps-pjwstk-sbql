package jpslab.datastore;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.OID;

public class ComplexObject extends ASBAObject implements IComplexObject {

	List<OID> oids;
	
	public ComplexObject(OID oid, String name, OID... oids) {
		super(name, oid);
		for(OID x: oids){
			this.oids.add(x);
		}
	}
	
	public ComplexObject(OID oid, String name, List<OID> oids) {
		super(name, oid);
		this.oids = new LinkedList<OID>(oids);
	}

	@Override
	public List<OID> getChildOIDs() {
		return this.oids;
	}
	
	@Override
	public String toString(){
		String text = "{";
		Iterator<OID> iter = this.oids.iterator();
		while(iter.hasNext()){
			text += iter.next().toString();
			if(iter.hasNext()){
				text += ",";
			}
		}
		text += "}";
		return "<"+super.toString()+","+text+">";
	}

}
