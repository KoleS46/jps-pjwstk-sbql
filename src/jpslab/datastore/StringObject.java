package jpslab.datastore;

import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.datastore.OID;

public class StringObject extends ASimpleObject<String> implements IStringObject {

	public StringObject(OID oid, String name, String obj) {
		super(oid, name, obj);
	}

}
