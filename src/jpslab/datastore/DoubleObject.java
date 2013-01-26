package jpslab.datastore;

import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.OID;

public class DoubleObject extends ASimpleObject<Double> implements IDoubleObject {

	public DoubleObject(OID oid, String name, Double obj) {
		super(oid, name, obj);
	}

}
