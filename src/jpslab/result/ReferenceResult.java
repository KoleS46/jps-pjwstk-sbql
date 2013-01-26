package jpslab.result;

import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.result.IReferenceResult;

public class ReferenceResult extends ASingleResult implements IReferenceResult{

	private OID oid;
	
	public ReferenceResult(OID oid){
		this.oid = oid;
	}
	
	@Override
	public OID getOIDValue() {
		return this.oid;
	}
	
	@Override
	public String toString(){
		return "i"+oid.toString();
	}

}
