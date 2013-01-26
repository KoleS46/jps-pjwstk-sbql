package jpslab.helpers;

import java.util.Collection;
import java.util.LinkedList;

import jpslab.result.BagResult;
import jpslab.result.StructResult;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class CartesianProduct {
	
	public static Collection<ISingleResult> toSingleResultList(IAbstractQueryResult srl){
		Collection<ISingleResult> res = null;
		if(srl instanceof IBagResult){
			res = (((IBagResult) srl).getElements());
		}else if(srl instanceof IStructResult){
			res = (((IStructResult) srl).elements());
		}else if(srl instanceof ISequenceResult){
			throw new RuntimeException("Nie obsługiwana struktura.");
		}else{
			res = (new StructResult((ISingleResult)srl)).elements();
		}
		return res;
	}
	
	public static IAbstractQueryResult calculate(IAbstractQueryResult leftRes, 
			IAbstractQueryResult rightRes){
		Collection<ISingleResult> leftSide = CartesianProduct.toSingleResultList(leftRes);
		Collection<ISingleResult> rightSide = CartesianProduct.toSingleResultList(rightRes);
		LinkedList<ISingleResult> result = new LinkedList<ISingleResult>();
		
		for(ISingleResult x: leftSide){
			for(ISingleResult y: rightSide){
				result.add(new StructResult(x, y));
			}
		}
		if(result.size() == 1){
			return result.pop();
		}
		return new BagResult(result);
	}
	
}
