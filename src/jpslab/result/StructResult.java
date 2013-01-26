package jpslab.result;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class StructResult extends ASingleResult implements IStructResult {

	private List<ISingleResult> list = new LinkedList<ISingleResult>();

	public StructResult(ISingleResult... list){
		for(ISingleResult i: list){
			this.list.add(i);
		}
	}

	public StructResult(List<ISingleResult> list) {
		for(ISingleResult i: list){
			this.list.add(i);
		}
	}

	@Override
	public List<ISingleResult> elements() {
		return this.list;
	}

	@Override
	public String toString(){
		int count = 0;
		String text = "(QRES) struct(";
		Iterator<ISingleResult> list = this.list.iterator();
		while(list.hasNext()){
			text += count+"=\""+list.next().toString()+"\"";
			if(list.hasNext()){
				text += ",";
			}
			count++;
		}
		text += ")";
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
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
		if (!(obj instanceof StructResult)) {
			return false;
		}
		StructResult other = (StructResult) obj;
		if (list == null) {
			if (other.list != null) {
				return false;
			}
		} else if (!list.equals(other.list)) {
			return false;
		}
		return true;
	}
	
}
