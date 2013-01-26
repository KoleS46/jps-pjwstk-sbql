package jpslab.datastore;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;

public class SBAStore implements ISBAStore {

	private HashMap<OID, ISBAObject> mapa = new HashMap<OID, ISBAObject>();
	private Integer entryId = 0;
	private Integer countId = entryId;
	
	public void create(String typ, String nazwa, String value, String wezel) {
		OID oid;
		String name = nazwa;
		if (wezel == "-") {
			oid = this.getEntryOID();
			List<OID> oids = new ArrayList<OID>();
			ComplexObject co = new ComplexObject(oid, name, oids);
			mapa.put(oid, co);
		} else {
			oid = this.generateUniqueOID();
			Integer wezel_int = Integer.parseInt(wezel);
			if(typ.equalsIgnoreCase("complex")){
				ComplexObject temp = (ComplexObject) mapa.get(new Oid(wezel_int));
				List<OID> temp_lista = temp.getChildOIDs();
				temp_lista.add(oid);
				mapa.put(new Oid(wezel_int), temp);
				ComplexObject coo = new ComplexObject(oid, name, new ArrayList<OID>());
				mapa.put(oid, coo);
			} else if(typ.equalsIgnoreCase("boolean")) {
				ComplexObject temp = (ComplexObject) mapa.get(new Oid(wezel_int));
				List<OID> temp_lista = temp.getChildOIDs();
				temp_lista.add(oid);
				mapa.put(new Oid(wezel_int), temp);
				BooleanObject boo = new BooleanObject(oid, name, Boolean.parseBoolean(value));
				mapa.put(oid, boo);
			} else if(typ.equalsIgnoreCase("double")) {
				ComplexObject temp = (ComplexObject) mapa.get(new Oid(wezel_int));
				List<OID> temp_lista = temp.getChildOIDs();
				temp_lista.add(oid);
				mapa.put(new Oid(wezel_int), temp);
				DoubleObject doo = new DoubleObject(oid, name, Double.parseDouble(value));
				mapa.put(oid, doo);
			} else if(typ.equalsIgnoreCase("integer")) {
				ComplexObject temp = (ComplexObject) mapa.get(new Oid(wezel_int));
				List<OID> temp_lista = temp.getChildOIDs();
				temp_lista.add(oid);
				mapa.put(new Oid(wezel_int), temp);
				IntegerObject ino = new IntegerObject(oid, name, Integer.parseInt(value));
				mapa.put(oid, ino);
			} else if(typ.equalsIgnoreCase("string")) {
				ComplexObject temp = (ComplexObject) mapa.get(new Oid(wezel_int));
				List<OID> temp_lista = temp.getChildOIDs();
				temp_lista.add(oid);
				mapa.put(new Oid(wezel_int), temp);
				StringObject sto = new StringObject(oid, name, value);
				mapa.put(oid, sto);
			} else System.out.println("Błąd: podano zły typ.");
		}
	}
	
	private void parseElements(Document doc){
		
		Element root = doc.getRootElement();
		OID oid = this.getEntryOID();
		@SuppressWarnings("unchecked")
		List<Element> list = root.getChildren();
		List<OID> oid_list = this.parse(list, oid);
		ComplexObject cmplx = new ComplexObject(oid, root.getName(), oid_list);
		this.mapa.put(oid, cmplx);
//		System.out.println(cmplx);
	}
	
	private List<OID> parse(List<Element> list, OID parent){
		
		List<OID> oid_list = new LinkedList<OID>();
		for(Element x: list){
			if(!x.getChildren().isEmpty()){
				OID this_oid = this.generateUniqueOID();
				@SuppressWarnings("unchecked")
				List<OID> local_list = parse(x.getChildren(), this_oid);
				ComplexObject cmplx = new ComplexObject(this_oid, x.getName(), local_list);
//				System.out.println(cmplx);
				this.mapa.put(this_oid, cmplx);
				oid_list.add(this_oid);
			}else{
				OID this_oid = this.generateUniqueOID();
				oid_list.add(this_oid);
				Object val = null;
				ISBAObject isba = null;
				try{
					val = Integer.parseInt(x.getValue());
					isba = new IntegerObject(this_oid, x.getName(), (Integer)val);
				}catch(Exception e1){
//					System.out.println("Not integer");
					try{
						val = Double.parseDouble(x.getValue());
						isba = new DoubleObject(this_oid, x.getName(), (Double) val);
					}catch(Exception e2){
//						System.out.println("Not double");
						try{
							val = Boolean.parseBoolean(x.getValue().toLowerCase());
							if(val.equals(false) && !x.getValue().equals("false")){
								throw new Exception();
							}
							isba = new BooleanObject(this_oid, x.getName(), (Boolean)val);
						}catch(Exception e3){
//							System.out.println("Not boolen?");
							try{
								val = (String) x.getValue();
								isba = new StringObject(this_oid, x.getName(), (String)val);
							}catch(Exception e){
								System.out.println("Not string?");
							}
						}
						
					}
				}
//				System.out.println(this_oid.toString()+"|"+x.getName()+":"+val+"|"+val.getClass().getSimpleName());
//				System.out.println(isba);
				this.mapa.put(this_oid, isba);
			}
		}
		return oid_list;
	}
	
	@Override
	public ISBAObject retrieve(OID oid) {
		return this.mapa.get(oid);
	}

	@Override
	public OID getEntryOID() {
		
		return new Oid(this.entryId);
	}
	
	@Override
	public void loadXML(String filePath) {
		
		SAXBuilder builder = new SAXBuilder();
		builder.setIgnoringElementContentWhitespace(true);
		File xmlFile = new File(filePath);
		try {
			Document document = (Document) builder.build(xmlFile);
			parseElements(document);

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	@Override
	public OID generateUniqueOID() {
		OID iod = new Oid(++this.countId);
//		System.out.println(iod);
		return iod;
	}

	@Override
	public void addJavaObject(Object o, String objectName) {
		
	}

	public void addJavaObject(Object o){
		OID eoid = this.getEntryOID();
		ComplexObject entryO = (ComplexObject) this.retrieve(eoid);
		List<OID> oid_list = new LinkedList<OID>();
		if(entryO != null){
			oid_list.addAll(entryO.getChildOIDs());
		}
		if (o != null) {
    		oid_list.addAll(addFields(o)); 
		}
		entryO = new ComplexObject(eoid, "root", oid_list);
		this.mapa.put(eoid, entryO);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addJavaCollection(Collection o, String collectionName) {
		Iterator<?> it = o.iterator();
		while(it.hasNext()) {
			addJavaObject(it.next());
		} 
	}

	private List<OID> addFields(Object o){
		Field[] fields = o.getClass().getDeclaredFields();
		List<OID> oid_list = new LinkedList<OID>();
		Oid newOid;
		String name;
		String type;
		for (Field field : fields) {
			try{
				ISBAObject iobj = null;
				newOid = (Oid) this.generateUniqueOID();
				name = field.getName();
				type = field.getType().getSimpleName();
	    		if(type.equalsIgnoreCase("integer") || type.equalsIgnoreCase("int")) {
					Integer value = null;
					try {
						value = (Integer)field.get(o);
					} catch (IllegalAccessException e) {
						System.out.println("Błąd: " + e);
					}
					iobj = new IntegerObject(newOid, name, value);
	    		}else if (type.equalsIgnoreCase("double")) {
					Double value = null;
					try {
						value = (Double)field.get(o);
					} catch (IllegalAccessException e) {
						System.out.println("Błąd: " + e);
					} catch (IllegalArgumentException e){
						System.out.println("Błąd: " + e);
					}
					iobj = new DoubleObject(newOid, name, value);
	    		}else if (type.equalsIgnoreCase("boolean")) {
					Boolean value = null;
					try {
						value = (Boolean)field.get(o);
					} catch (IllegalAccessException e) {
						System.out.println("Błąd: " + e);
					} catch (IllegalArgumentException e){
						System.out.println("Błąd: " + e);
					}
					iobj = new BooleanObject(newOid, name, value);
	    		}else if (type.equalsIgnoreCase("string")) {
					String value = null;
					try {
						value = (String)field.get(o);
					} catch (IllegalAccessException e) {
						System.out.println("Błąd: " + e);
					} catch (IllegalArgumentException e){
						System.out.println("Błąd: " + e);
					}
					iobj = new StringObject(newOid, name, value);
	    		}
	    		oid_list.add(newOid);
	    		mapa.put(newOid, iobj);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return oid_list;
	}
	
	@Override
	public String toString(){
		
		String text = "";
		List<Integer> list = new LinkedList<Integer>();
		for(OID x: this.mapa.keySet()){
			list.add(Integer.parseInt(x.toString()));
		}
		Collections.sort(list);
		Iterator<Integer> iter = list.iterator();
		while(iter.hasNext()){
			text += this.mapa.get(new Oid(iter.next()));
			if(iter.hasNext()){
				text += "\n";
			}
		}		
		return text;
	}
	
}
