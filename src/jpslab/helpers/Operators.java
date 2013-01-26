package jpslab.helpers;

import java.util.LinkedList;

import jpslab.result.BagResult;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISimpleResult;
import edu.pjwstk.jps.result.ISingleResult;

public class Operators {

	public static IBagResult dot(String binder_str, IBagResult bag, IQResStack qres,
			IENVS env, ISBAStore sba){
 		IBagResult bage = new BagResult();
 		for(ISingleResult x: bag.getElements()){
 			env.nested(x, sba);
 			IBagResult baga = env.bind(binder_str);
 			qres.push(baga);
 			baga = (IBagResult) qres.pop();
 			for(ISingleResult y: baga.getElements()){
 				bage.getElements().add(y);
 			}
 			env.pop();
 		}
 		return bage;
	}
	
	public static IBagResult where(String binder_str, IBagResult bag, IQResStack qres,
			IENVS env, ISBAStore sba, Object compare, String compareDir) {
		LinkedList<Integer> compDir = new LinkedList<Integer>();
		if(compareDir.equals("==")){
			compDir.add(0);
		}else if(compareDir.equals("<")){
			compDir.add(-1);
		}else if(compareDir.equals(">")){
			compDir.add(1);
		}else if(compareDir.equals("<=")){
			compDir.add(0);
			compDir.add(-1);
		}else if(compareDir.equals(">=")){
			compDir.add(0);
			compDir.add(1);
		}else if(compareDir.equals("!=") || compareDir.equals("<>")){
			compDir.add(-1);
			compDir.add(1);
		}
		IBagResult bage = new BagResult();
 		for(ISingleResult x: bag.getElements()){
			env.nested(x, sba);
			IBagResult baga = env.bind(binder_str);
			qres.push(baga);
			baga = (IBagResult) qres.pop();
			for(ISingleResult y: baga.getElements()){
				Eval.eval((IReferenceResult) y, sba, qres);
				IBagResult bag_eval = (IBagResult) qres.pop();
				Object obj = ((ISimpleResult<?>)((bag_eval.getElements()).toArray()[0])).getValue();
				if(obj instanceof Boolean){
					Boolean _bool = (Boolean) compare;
					if(compDir.contains(Boolean.compare((Boolean) obj, _bool))){
						bage.getElements().add(x);
					}
				}else if(obj instanceof String){
					String _str = (String) obj;
					if(compDir.contains(_str.compareTo((String) compare))){
						bage.getElements().add(x);
					}
				}else if(obj instanceof Integer){
					Integer _int = (Integer) compare;
					if(compDir.contains(((Integer) obj).compareTo(_int))){
						bage.getElements().add(x);
					}
				}else if(obj instanceof Double){
					Double _int = (Double) compare;
					if(compDir.contains(((Double) obj).compareTo(_int))){
						bage.getElements().add(x);
					}
				}
//				try {
//					@SuppressWarnings("rawtypes")
//					Class b = Class.forName(klass.toString().substring(6));
//					Object val = b.cast();
//					if(val.equals(test)){
//						bage.getElements().add(x);
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			env.pop();
		}	
		return bage;
	}
	
	public static IBagResult union(String binder_str, IQResStack qres, IENVS env) {
		IBagResult bagunion = new BagResult();
		IBagResult bag5 = env.bind(binder_str);
 		qres.push(bag5);
 		IBagResult bag4 = (IBagResult) qres.pop();
 		IBagResult bag6 = (IBagResult) qres.pop();
 		bagunion.getElements().addAll(bag4.getElements());
 		bagunion.getElements().addAll(bag6.getElements());
 		return bagunion;
	}
	
}
