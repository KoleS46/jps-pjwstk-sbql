package jpslab.datastore;

import edu.pjwstk.jps.datastore.OID;

public class Oid implements OID {

	private static final long serialVersionUID = 1L;
	private Integer oid;
	
	public Oid(Integer oid) {
		this.oid = oid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oid other = (Oid) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return this.oid.toString();
	}

}
