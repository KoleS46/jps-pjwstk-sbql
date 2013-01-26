package jpslab.interpreter.envs;

import java.util.Iterator;
import java.util.LinkedList;

import jpslab.result.BagResult;
import jpslab.result.ReferenceResult;
import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBinderResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class ENVS implements IENVS {

	private ISBAStore store;
	private OID roid;
	private LinkedList<IENVSFrame> estack = new LinkedList<IENVSFrame>();

	@Override
	public void init(OID rootOID, ISBAStore store) {
		this.store = store;
		this.roid = rootOID;
		IComplexObject oids = null;
		oids = (IComplexObject) this.store.retrieve(this.roid);
		IENVSFrame iframe = new ENVSFrame();
		for(OID x: oids.getChildOIDs()){
			ISBAObject sbao = this.store.retrieve(x);
			IENVSBinder ieb = new ENVSBinder(sbao.getName(), new ReferenceResult(sbao.getOID()));
			iframe.getElements().add(ieb);
		}
		this.push(iframe);
	}

	@Override
	public IENVSFrame pop() {
		IENVSFrame val = this.estack.pop();
		System.out.println("POP " + val.toString());
		return val;
	}

	@Override
	public void push(IENVSFrame frame) {
		System.out.println("PUSH " + frame.toString());
		this.estack.push(frame);
	}

	@Override
	public IBagResult bind(String name) {
		IBagResult ibag = new BagResult();
		Boolean found = false;
		for (IENVSFrame ie : this.estack) {
			for (IENVSBinder ib : ie.getElements()) {
				if (ib.getName().equals(name)) {
					ibag.getElements().add((ISingleResult) ib.getValue());
					found = true;
				}
			}
			if (found) {
				break;
			}
		}
		return ibag;
	}

	@Override
	public IENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {
		IENVSFrame ief = new ENVSFrame();
		if (result instanceof IReferenceResult) {
			ISBAObject isbao = store.retrieve(((IReferenceResult) result)
					.getOIDValue());
			if (isbao instanceof IComplexObject) {
				for (OID ir : ((IComplexObject) isbao).getChildOIDs()) {
					ISBAObject isbao2 = store.retrieve(ir);
					ief.getElements().add(
							new ENVSBinder(isbao2.getName(),
									new ReferenceResult(isbao2.getOID())));
				}
			} else if (isbao instanceof IReferenceResult) {
				ISBAObject isbao2 = store.retrieve(((IReferenceResult) isbao)
						.getOIDValue());
				ief.getElements().add(
						new ENVSBinder(isbao2.getName(),
								new ReferenceResult(isbao2.getOID())));
			}
		} else if (result instanceof IStructResult) {
			System.out.println("IStructResult:" + result.toString());
		} else if (result instanceof IBinderResult){
			ief.getElements().add(new ENVSBinder(((IBinderResult)result).getName(),
					((IBinderResult)result).getValue()));
		}else{
			System.out.println("ELSE ENVS.nested:" + result.toString());
		}
		this.push(ief);
//		System.out.println(result.toString());
		return ief;
	}

	@Override
	public String toString() {
		String str = "[ENVS]\n";
		Iterator<IENVSFrame> ieb = this.estack.iterator();
		Integer pos = 0;
		while (ieb.hasNext()) {
			str += "  [" + pos.toString() + "]";
			str += ieb.next().toString();
			if (ieb.hasNext()) {
				str += "\n";
				pos++;
			}
		}
		return str;
	}

}
