package jpslab.helpers;

import java.util.LinkedList;

import jpslab.result.BagResult;
import jpslab.result.BooleanResult;
import jpslab.result.DoubleResult;
import jpslab.result.IntegerResult;
import jpslab.result.StringResult;
import jpslab.result.StructResult;
import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.ISimpleObject;
import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBinderResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISimpleResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class Eval {
	
	public static ISimpleResult<?> getSimple(IReferenceResult iref, ISBAStore store, IQResStack qres){
		ISBAObject obj = store.retrieve(iref.getOIDValue());
		Object obj2 = ((ISimpleObject<?>) obj).getValue();
		ISimpleResult<?> res = null;
		if(obj2 instanceof Boolean){
			res = new BooleanResult((Boolean) ((ISimpleObject<?>) obj).getValue());
		}else if(obj2 instanceof String){
			res = new StringResult((String) ((ISimpleObject<?>) obj).getValue());
		}else if(obj2 instanceof Integer){
			res = new IntegerResult((Integer) ((ISimpleObject<?>) obj).getValue());
		}else if(obj2 instanceof Double){
			res = new DoubleResult((Double) ((ISimpleObject<?>) obj).getValue());
		}
		return res;
	}
	
	public static void evalBag(IBagResult bref, ISBAStore store, IQResStack qres){
		IBagResult res = new BagResult();
		for(ISingleResult x: bref.getElements()){
			res.getElements().add(Eval.getSimple((IReferenceResult) x, store, qres));
		}
		qres.push(res);
	}
	
	public static void eval(IReferenceResult iref, ISBAStore store, IQResStack qres){
		ISimpleResult<?> res = Eval.getSimple(iref, store, qres);
		qres.push(new BagResult(res));
	}
	
	public static IBagResult toBag(IAbstractQueryResult res) {
		if(res instanceof IBagResult) {
			return (IBagResult)res;
		} else if(res instanceof ISingleResult) {
			IBagResult bag = new BagResult();
			bag.getElements().add((ISingleResult)res);
			return bag;
		} else {
			throw new RuntimeException("");
		}
		
	}
	public static IAbstractQueryResult toSingleResult(
			IAbstractQueryResult res) {
		if(res instanceof IBagResult) {
			IBagResult leftBag = ((IBagResult) res); 
			if(leftBag.getElements().size() == 1) {
				res = leftBag.getElements().iterator().next();
			} else {
				throw new RuntimeException("rezultat nie moze byc bagiem wieloelementowym");
			}
		}
		if(res instanceof IStructResult) {
			IStructResult leftStruct = ((IStructResult) res); 
			if(leftStruct.elements().size() == 1) {
				res = leftStruct.elements().iterator().next();
			} else {
				throw new RuntimeException("rezultat nie moze byc struktura wieloelementowa");
			}
		}
		return res;
	}
	
	public static IAbstractQueryResult deref(IAbstractQueryResult res, ISBAStore store) {
		if(res instanceof IReferenceResult) {
			IReferenceResult ref = (IReferenceResult) res;
			ISBAObject obj = store.retrieve(ref.getOIDValue());
			if(obj instanceof ISimpleObject) {
				if(obj instanceof IIntegerObject) {
					IIntegerObject iobj = (IIntegerObject) obj;
					res = new IntegerResult(iobj.getValue());
				} else if(obj instanceof IDoubleObject) {
					IDoubleObject iobj = (IDoubleObject) obj;
					res = new DoubleResult(iobj.getValue());
				} else if(obj instanceof IStringObject) {
					IStringObject iobj = (IStringObject) obj;
					res = new StringResult(iobj.getValue());
				} else if(obj instanceof IBooleanObject) {
					IBooleanObject iobj = (IBooleanObject) obj;
					res = new BooleanResult(iobj.getValue());
				}
			} else {
				throw new RuntimeException("excepted single object got: "+obj.getClass());
			}
		} else if(res instanceof IBinderResult) {
			IBinderResult binder = (IBinderResult) res;
			res = binder.getValue();
		}
		return res;
	}
	
	public static Double get2cmp(IAbstractQueryResult leftRes) {
		double num1 = 0;
		
		if(leftRes instanceof IntegerResult){
			num1 = (double)((IntegerResult) leftRes).getValue();
		}else if(leftRes instanceof DoubleResult){
			num1 = (double)((DoubleResult) leftRes).getValue();
		}else{
			throw new RuntimeException("Można tylko porownywac double i int");
		}
		return num1;
	}
	
	public static Boolean doubleInList(LinkedList<ISingleResult> list){
		for(ISingleResult x:list){
			if(x instanceof DoubleResult){
				return true;
			}
		}
		return false;
	}
	
	public static IAbstractQueryResult cartesianProduct(IAbstractQueryResult leftRes,
			IAbstractQueryResult rightRes){
		BagResult eres = new BagResult();
		for(ISingleResult x: ((IBagResult)leftRes).getElements()){
			for(ISingleResult y: ((IBagResult)rightRes).getElements()){
				IStructResult str = new StructResult();
				if(x instanceof IStructResult){
					for(ISingleResult x1: ((IStructResult) x).elements()){
						str.elements().add(x1);
					}
				}else {
					((StructResult)str).elements().add(x);
				}
				if(y instanceof IStructResult){
					for(ISingleResult y1: ((IStructResult) y).elements()){
						str.elements().add(y1);
					}
				}else{
					((StructResult)str).elements().add(y);
				}
				eres.add(str);
			}
		}
		if(eres.getElements().size() == 1){
			return ((LinkedList<ISingleResult>) eres.getElements()).pop();
		}
		return eres;
	}
	
	public static IAbstractQueryResult deref2(IAbstractQueryResult res, ISBAStore store){
		if(res instanceof IReferenceResult) {
			IReferenceResult ref = (IReferenceResult) res;
			ISBAObject obj = store.retrieve(ref.getOIDValue());
			if(obj instanceof ISimpleObject) {
				if(obj instanceof IIntegerObject) {
					IIntegerObject iobj = (IIntegerObject) obj;
					res = new IntegerResult(iobj.getValue());
				} else if(obj instanceof IDoubleObject) {
					IDoubleObject iobj = (IDoubleObject) obj;
					res = new DoubleResult(iobj.getValue());
				} else if(obj instanceof IStringObject) {
					IStringObject iobj = (IStringObject) obj;
					res = new StringResult(iobj.getValue());
				} else if(obj instanceof IBooleanObject) {
					IBooleanObject iobj = (IBooleanObject) obj;
					res = new BooleanResult(iobj.getValue());
				}
			}
		}
		return res;
	}
	
	public static IAbstractQueryResult derefBagStruct(IAbstractQueryResult bs, ISBAStore store){
		IAbstractQueryResult result = null;
		
		if(bs instanceof IBagResult){
			BagResult bag = new BagResult();
			for(IAbstractQueryResult x: ((IBagResult) bs).getElements()){
				if(x instanceof IStructResult){
					x = derefBagStruct(x, store);					
				}else{					
					x = deref2(x, store);
				}
				bag.add(x);
			}
			result = bag;
		}else if(bs instanceof IStructResult){
			StructResult str = new StructResult();
			for(IAbstractQueryResult x: ((IStructResult) bs).elements()){
				str.elements().add((ISingleResult) deref2(x, store));
			}
			result = str;
		}
		return result;
	}
	
}
