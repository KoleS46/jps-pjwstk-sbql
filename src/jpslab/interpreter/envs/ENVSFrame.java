package jpslab.interpreter.envs;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;

public class ENVSFrame implements IENVSFrame {

	private LinkedList<IENVSBinder> frame = new LinkedList<IENVSBinder>();
	
	public ENVSFrame(){
	}
	
	public ENVSFrame(LinkedList<IENVSBinder> frame) {
		this.frame.addAll(frame);
	}

	@Override
	public Collection<IENVSBinder> getElements() {
		return frame;
	}
	
	public String toString(){
		String str = "(ENVSFrame)[";
		Iterator<IENVSBinder> iter = this.frame.iterator();
		while(iter.hasNext()){
			str += iter.next().toString();
			if(iter.hasNext()){
				str += ",";
			}
		}
		return str + "]";
	}
}
