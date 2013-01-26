package jpslab.datastore;

import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.OID;

public class BooleanObject extends ASimpleObject<Boolean> implements IBooleanObject {

	public BooleanObject(OID oid, String name, Boolean obj) {
		super(oid, name, obj);
	}

}
