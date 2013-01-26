package jpslab.datastore;

import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.OID;

public class IntegerObject extends ASimpleObject<Integer> implements IIntegerObject {

	public IntegerObject(OID oid, String name, Integer obj) {
		super(oid, name, obj);
	}

}
