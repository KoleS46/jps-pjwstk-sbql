package jpslab.result;

import edu.pjwstk.jps.result.ISimpleResult;

public abstract class ASimpleResult<T> extends ASingleResult implements ISimpleResult<T> {

	protected T result;
	
	public ASimpleResult(T res){
		this.result = res;
	}
	
	public T getValue() {
		return this.result;
	}

	@Override
	public String toString(){
		return this.result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ASimpleResult)) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		ASimpleResult other = (ASimpleResult) obj;
		if (result == null) {
			if (other.result != null) {
				return false;
			}
		} else if (!result.equals(other.result)) {
			return false;
		}
		return true;
	}

}
