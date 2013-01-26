package jpslab.result;

import java.util.LinkedList;
import java.util.List;

import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISingleResult;

public class SequenceResult extends ACollectionResult implements ISequenceResult {

	private List<ISingleResult> list;
	
	public SequenceResult(List<ISingleResult> list){
		this.list = new LinkedList<ISingleResult>(list);
	}
	
	@Override
	public List<ISingleResult> getElements() {
		// TODO Auto-generated method stub
		return this.list;
	}

}
