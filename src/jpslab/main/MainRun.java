package jpslab.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import jpslab.ast.auxname.AsExpression;
import jpslab.ast.auxname.GroupAsExpression;
import jpslab.ast.binary.AndExpression;
import jpslab.ast.binary.CommaExpression;
import jpslab.ast.binary.DivideExpression;
import jpslab.ast.binary.DotExpression;
import jpslab.ast.binary.EqualsExpression;
import jpslab.ast.binary.ForAllExpression;
import jpslab.ast.binary.ForAnyExpression;
import jpslab.ast.binary.GreaterThanExpression;
import jpslab.ast.binary.InExpression;
import jpslab.ast.binary.IntersectExpression;
import jpslab.ast.binary.JoinExpression;
import jpslab.ast.binary.MinusExpression;
import jpslab.ast.binary.MinusSetExpression;
import jpslab.ast.binary.ModuloExpression;
import jpslab.ast.binary.MultiplyExpression;
import jpslab.ast.binary.OrExpression;
import jpslab.ast.binary.PlusExpression;
import jpslab.ast.binary.UnionExpression;
import jpslab.ast.binary.WhereExpression;
import jpslab.ast.binary.XORExpression;
import jpslab.ast.parser.SbqlParser;
import jpslab.ast.terminal.BooleanTerminal;
import jpslab.ast.terminal.DoubleTerminal;
import jpslab.ast.terminal.IntegerTerminal;
import jpslab.ast.terminal.NameTerminal;
import jpslab.ast.terminal.StringTerminal;
import jpslab.ast.unary.AvgExpression;
import jpslab.ast.unary.BagExpression;
import jpslab.ast.unary.CountExpression;
import jpslab.ast.unary.MaxExpression;
import jpslab.ast.unary.MinExpression;
import jpslab.ast.unary.NotExpression;
import jpslab.ast.unary.SumExpression;
import jpslab.ast.unary.UniqueExpression;
import jpslab.ast.visitor.Visitor;
import jpslab.datastore.Oid;
import jpslab.datastore.SBAStore;
import jpslab.helpers.CartesianProduct;
import jpslab.helpers.Eval;
import jpslab.helpers.Operators;
import jpslab.interpreter.envs.ENVS;
import jpslab.interpreter.qres.QresStack;
import jpslab.result.BagResult;
import jpslab.result.BinderResult;
import jpslab.result.BooleanResult;
import jpslab.result.DoubleResult;
import jpslab.result.IntegerResult;
import jpslab.result.StringResult;
import jpslab.result.StructResult;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class MainRun {

	public static void main(String[] args) {

		// astZad();
		// qresTest();
		// qresZad1();
		// qresZad2();
		// qresZad3();
		// datastoreZad1();
		// datastoreZad2();
		// datastoreZad3();

		// envsZad1();
		// envsZad2();
//		 opZad1();
//		 opZad2();
//		 opZad3();
//		 opZad4();
//		 opZad5();
//		 opZad6();
//		 opZad7();
//		 opZad8();
//		 opZad9();
//		 opZad10();
//		 opZad11();
//		 opZad12();
//		 opZad13();
//		 opZad14();
//		 opZad15();
//		 opZad16();
//		 opZad17();
//		 opZad18();
//		 opZad19();
//		 opZad20();
//		 opZad21();
//		 opZad22();
//		 opZad23();
//		 opZad24();
//		 opZad25();
//		 opZad26();
//		 opZad27();
//		 opZad28();
//		 opZad29();
//		 opZad30();
//		 opZad31();
		parseArgs(args);
		parserZad1(args[0], args[1]);
	}
	
	public static void parseArgs(String[] args){
		System.out.println("Wywołujemy: java -jar pr7.jar emp.address.street XML/plikXML4.xml");
		if(args.length == 0){
			throw new RuntimeException("No args passed.");
		}else if(args.length == 1){
			throw new RuntimeException("Too few args passed.");
		}else if(args.length > 2){
			throw new RuntimeException("Too many args passed.");
		}
	}

	public static void parserZad1(String query, String path) {
		SbqlParser parser = new SbqlParser(query);
		try {
			parser.user_init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			parser.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SBAStore sba = new SBAStore();
		sba.loadXML(path);
		Visitor visit = new Visitor(sba);
		IExpression i = parser.RESULT;
		i.accept(visit);
		System.out.println("Wynik: "+visit.getResult());
	}

	public static void opZad31() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========bag(1,3) minus bag(1,2)============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new MinusSetExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(3))), new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))));

		expr.accept(inter);
	}

	public static void opZad30() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========bag(1,3) intersect bag(1,2)============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new IntersectExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(3))), new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))));

		expr.accept(inter);
	}

	public static void opZad29() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========(1,2) in (1,2)============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new InExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)));

		expr.accept(inter);
	}

	public static void opZad28() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========bag(2,3) in bag(1,2,3)============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new InExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(4),
						new IntegerTerminal(3))), new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));

		expr.accept(inter);
	}

	public static void opZad27() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========unique(bag(1,3,2))============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new UniqueExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(3)), new IntegerTerminal(2))));

		expr.accept(inter);
	}

	public static void opZad26() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========emp join married============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new JoinExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		expr.accept(inter);
	}

	public static void opZad25() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========(1 as n) join n============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new JoinExpression(new AsExpression("n",
				new IntegerTerminal(1)), new NameTerminal("n"));

		expr.accept(inter);
	}

	public static void opZad24() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========1 join 2============================================");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new JoinExpression(new IntegerTerminal(1),
				new IntegerTerminal(2));

		expr.accept(inter);
	}

	public static void opZad23() {
		System.out
				.println("======================================================================");
		System.out.println("===========bag(1,2,1) group as num===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new GroupAsExpression("num", new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(1))));

		expr.accept(inter);
	}

	public static void opZad22() {
		System.out
				.println("======================================================================");
		System.out.println("===========(1,2) as num===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new AsExpression("num", new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)));

		expr.accept(inter);
	}

	public static void opZad21() {
		System.out
				.println("======================================================================");
		System.out.println("===========any emp married===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new ForAnyExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		expr.accept(inter);
	}

	public static void opZad20() {
		System.out
				.println("======================================================================");
		System.out.println("===========all emp married===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new ForAllExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		expr.accept(inter);
	}

	public static void opZad19() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========((false or (not false xor not true)) and \"string\")===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new AndExpression(new OrExpression(
				new BooleanTerminal(false), new XORExpression(
						new NotExpression(new BooleanTerminal(true)),
						new NotExpression(new BooleanTerminal(true)))),
				new StringTerminal("string"));

		expr.accept(inter);
	}

	public static void opZad18() {
		System.out
				.println("======================================================================");
		System.out.println("===========not false===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new NotExpression(new BooleanTerminal(false));

		expr.accept(inter);
	}

	public static void opZad17() {
		System.out
				.println("======================================================================");
		System.out.println("===========bag(2/4,2*4,2%2)===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new BagExpression(new CommaExpression(
				new CommaExpression(new DivideExpression(
						new IntegerTerminal(2), new IntegerTerminal(4)),
						new MultiplyExpression(new IntegerTerminal(2),
								new IntegerTerminal(4))), new ModuloExpression(
						new IntegerTerminal(2), new IntegerTerminal(2))));

		expr.accept(inter);
	}

	public static void opZad16() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========sum(avg(2.0,3,6,6+6),max(1,2.0))===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new SumExpression(
				new CommaExpression(
						new AvgExpression(new CommaExpression(
								new CommaExpression(new CommaExpression(
										new DoubleTerminal(2.0),
										new IntegerTerminal(3)),
										new IntegerTerminal(6)),
								new PlusExpression(new IntegerTerminal(6),
										new IntegerTerminal(6)))),
						new MaxExpression(
								new CommaExpression(new IntegerTerminal(1),
										new DoubleTerminal(2.0)))));

		expr.accept(inter);
	}

	public static void opZad15() {
		System.out
				.println("======================================================================");
		System.out.println("===========max(2.0,3,6)===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new MaxExpression(new CommaExpression(
				new CommaExpression(new DoubleTerminal(2.0),
						new IntegerTerminal(3)), new IntegerTerminal(6)));

		expr.accept(inter);
	}

	public static void opZad14() {
		System.out
				.println("======================================================================");
		System.out.println("===========min(2.0,3,6)===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new MinExpression(new CommaExpression(
				new CommaExpression(new DoubleTerminal(2.0),
						new IntegerTerminal(3)), new IntegerTerminal(6)));

		expr.accept(inter);
	}

	public static void opZad13() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========(((emp.address) where (number>20))).street===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new DotExpression(new WhereExpression(
				new DotExpression(new NameTerminal("emp"), new NameTerminal(
						"address")), new GreaterThanExpression(
						new NameTerminal("number"), new IntegerTerminal(20))),
				new NameTerminal("street"));

		expr.accept(inter);
	}

	public static void opZad12() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========count(((emp.address) where (number>20)))===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new CountExpression(new WhereExpression(
				new DotExpression(new NameTerminal("emp"), new NameTerminal(
						"address")), new GreaterThanExpression(
						new NameTerminal("number"), new IntegerTerminal(20))));

		expr.accept(inter);
	}

	public static void opZad11() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========count(bag(2,2,2))=============================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new CountExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(2),
						new IntegerTerminal(2)), new IntegerTerminal(2))));

		expr.accept(inter);
	}

	public static void opZad10() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========2 - (-10)=======================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new MinusExpression(new IntegerTerminal(2),
				new IntegerTerminal(-10));

		expr.accept(inter);
	}

	public static void opZad9() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========bag(1,2)=======================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new BagExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)));

		expr.accept(inter);
	}

	public static void opZad8() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========bag(1)=========================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new BagExpression(new IntegerTerminal(1));

		inter.visitBagExpression((BagExpression) expr);
	}

	public static void opZad7() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========bag(bag(1,2,3))=====================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new BagExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));

		expr.accept(inter);
	}

	public static void opZad6() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========(1,2)==========================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new CommaExpression(new IntegerTerminal(1),
				new IntegerTerminal(2));

		expr.accept(inter);
	}

	public static void opZad5() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========(bag(1,2),3)===================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new IntegerTerminal(3));

		expr.accept(inter);
	}

	public static void opZad4() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========(bag(1,2),bag(3,4))================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new IntegerTerminal(3),
						new IntegerTerminal(4))));

		expr.accept(inter);
	}

	public static void opZad3() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========emp.book.author================================================");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new DotExpression(new DotExpression(
				new NameTerminal("emp"), new NameTerminal("book")),
				new NameTerminal("author"));

		expr.accept(inter);
	}

	public static void opZad1() {
		System.out
				.println("==========================================================================");
		System.out
				.println("===========((emp where married).book.author) union (realNumber)===========");
		System.out
				.println("==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new UnionExpression(
				new DotExpression(new DotExpression(new WhereExpression(
						new NameTerminal("emp"), new NameTerminal("married")),
						new NameTerminal("book")), new NameTerminal("author")),
				new NameTerminal("realNumber"));

		expr.accept(inter);
	}

	public static void opZad2() {
		System.out
				.println("======================================================================");
		System.out
				.println("===========((emp.address) where (number>20)).(street, city)===========");
		System.out
				.println("======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		Visitor inter = new Visitor(sba);

		IExpression expr = new DotExpression(new WhereExpression(
				new DotExpression(new NameTerminal("emp"), new NameTerminal(
						"address")), new GreaterThanExpression(
						new NameTerminal("number"), new IntegerTerminal(20))),
				new CommaExpression(new NameTerminal("street"),
						new NameTerminal("city")));

		expr.accept(inter);
	}

	public static void envsZad2() {
		System.out
				.println("!======================================================================");
		System.out
				.println("!===========((emp.address) where (number>20)).(street, city)===========");
		System.out
				.println("!======================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		IQResStack qres = new QresStack();
		IENVS env = new ENVS();
		env.init(sba.getEntryOID(), sba);
		IBagResult bag1 = env.bind("emp");
		qres.push(bag1);
		bag1 = (IBagResult) qres.pop();
		// dot
		IBagResult bag2 = (IBagResult) Operators.dot("address", bag1, qres,
				env, sba);
		qres.push(bag2);
		IBagResult bag3 = (IBagResult) qres.pop();
		// where
		IBagResult bag4 = (IBagResult) Operators.where("number", bag3, qres,
				env, sba, 20, ">");
		qres.push(bag4);
		// dot
		IBagResult bag5 = (IBagResult) qres.pop();
		IBagResult end = new BagResult();
		for (ISingleResult x : bag5.getElements()) {
			IBagResult bag6 = (IBagResult) Operators.dot("street",
					(new BagResult(x)), qres, env, sba);
			qres.push(bag6);
			// dot
			IBagResult bag7 = (IBagResult) Operators.dot("city",
					(new BagResult(x)), qres, env, sba);
			qres.push(bag7);
			IBagResult mr3 = (IBagResult) qres.pop();
			IBagResult ml3 = (IBagResult) qres.pop();
			Eval.evalBag(ml3, sba, qres);
			Eval.evalBag(mr3, sba, qres);
			mr3 = (IBagResult) qres.pop();
			ml3 = (IBagResult) qres.pop();
			IBagResult cartP = (IBagResult) CartesianProduct
					.calculate(ml3, mr3);
			// qres.push(cartP);
			end.getElements().addAll(cartP.getElements());
		}
		qres.push(end);
	}

	public static void envsZad1() {
		System.out
				.println("!==========================================================================");
		System.out
				.println("!===========((emp where married).book.author) union (realNumber)===========");
		System.out
				.println("!==========================================================================");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML4.xml");
		IQResStack qres = new QresStack();
		IENVS env = new ENVS();
		env.init(sba.getEntryOID(), sba);
		IBagResult bag1 = env.bind("emp");
		qres.push(bag1);
		bag1 = (IBagResult) qres.pop();
		// where
		IBagResult bage = (IBagResult) Operators.where("married", bag1, qres,
				env, sba, true, "==");
		qres.push(bage);
		// dot
		IBagResult bag2 = (IBagResult) qres.pop();
		IBagResult bagf = (IBagResult) Operators.dot("book", bag2, qres, env,
				sba);
		qres.push(bagf);
		// dot
		IBagResult bag3 = (IBagResult) qres.pop();
		IBagResult bagg = (IBagResult) Operators.dot("author", bag3, qres, env,
				sba);
		qres.push(bagg);
		// uninon_
		IBagResult bagunion = (IBagResult) Operators.union("realNumber", qres,
				env);
		qres.push(bagunion);
		bagunion = (IBagResult) qres.pop();
		Eval.evalBag(bagunion, sba, qres);
	}

	public static void datastoreZad3() {

		System.out.println("+++++++++++Przykład3+++++++++++++");
		SBAStore sba = new SBAStore();
		sba.addJavaObject(new TestClassC());
		System.out.println(sba);
		sba.addJavaObject(new TestClassB(34));
		System.out.println(sba);
		Collection<TestClassA> lista = new ArrayList<TestClassA>();
		lista.add(new TestClassA());
		lista.add(new TestClassB(67));
		lista.add(new TestClassC());
		SBAStore sba1 = new SBAStore();
		sba1.addJavaCollection(lista, "nazwa");
		System.out.println(sba);
	}

	public static void datastoreZad2() {
		System.out.println("+++++++++++Przykład2+++++++++++++");
		SBAStore sba = new SBAStore();
		sba.loadXML("XML/plikXML3.xml");
		System.out.println(sba);
	}

	public static void datastoreZad1() {
		System.out.println("+++++++++++Przykład1+++++++++++++");
		SBAStore sba = new SBAStore();
		sba.create("COMPLEX", "entry", "{}", "-");
		System.out.println(sba);
		sba.create("COMPLEX", "emp", "{}", "0");
		System.out.println(sba);
		sba.create("STRING", "ename", "KOWALSKI", "1");
		System.out.println(sba);
		sba.create("INTEGER", "SALARY", "1000", "1");
		sba.retrieve(new Oid(1));
		sba.retrieve(new Oid(2));
		sba.retrieve(new Oid(3));
	}

	public static void qresZad3() {
		System.out.println("(((bag(\"JPS\",\"rules\"), 2.2, true);");
		@SuppressWarnings("unused")
		IExpression ex8 = new CommaExpression(new CommaExpression(
				new GroupAsExpression("x", new BagExpression(
						new CommaExpression(new StringTerminal("JPS"),
								new StringTerminal("rules")))),
				new DoubleTerminal(2.2)), new BooleanTerminal(true));

		QresStack qres3 = new QresStack();
		qres3.push(new StringResult("JPS"));
		qres3.push(new StringResult("rules"));
		StringResult str1 = (StringResult) qres3.pop();
		StringResult str2 = (StringResult) qres3.pop();
		qres3.push(new StructResult(str2, str1));
		StructResult srt1 = (StructResult) qres3.pop();
		qres3.push(new BagResult(srt1.elements()));
		BagResult bag1 = (BagResult) qres3.pop();
		qres3.push(new BinderResult("x", bag1));
		qres3.push(new DoubleResult(2.2));
		DoubleResult dob1 = (DoubleResult) qres3.pop();
		BinderResult bin1 = (BinderResult) qres3.pop();
		qres3.push(new StructResult(bin1, dob1));
		StructResult srt2 = (StructResult) qres3.pop();
		qres3.push(new BagResult(srt2.elements()));
		qres3.push(new BooleanResult(true));
		BooleanResult bol1 = (BooleanResult) qres3.pop();
		BagResult bag2 = (BagResult) qres3.pop();
		BagResult cartP = (BagResult) CartesianProduct.calculate(bag2, bol1);
		qres3.push(cartP);
	}

	public static void qresZad2() {
		System.out.println("(bag(\"ala\", \"ma\", \"kota\"),(8*10, false));");
		@SuppressWarnings("unused")
		IExpression ex7 = new CommaExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new StringTerminal(
						"Ala"), new StringTerminal("ma")), new StringTerminal(
						"kota"))), new CommaExpression(new MultiplyExpression(
				new IntegerTerminal(8), new IntegerTerminal(10)),
				new BooleanTerminal(false)));

		QresStack qres2 = new QresStack();
		qres2.push(new StringResult("ala"));
		qres2.push(new StringResult("ma"));
		StringResult str1 = (StringResult) qres2.pop();
		StringResult str2 = (StringResult) qres2.pop();
		qres2.push(new StructResult(str2, str1));
		qres2.push(new StringResult("kota"));
		StringResult str3 = (StringResult) qres2.pop();
		StructResult srt1 = (StructResult) qres2.pop();
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		list.addAll(srt1.elements());
		list.add(str3);
		qres2.push(new StructResult(list));
		StructResult srt2 = (StructResult) qres2.pop();
		qres2.push(new BagResult(srt2.elements()));
		qres2.push(new IntegerResult(8));
		qres2.push(new IntegerResult(10));
		IntegerResult int1 = (IntegerResult) qres2.pop();
		IntegerResult int2 = (IntegerResult) qres2.pop();
		qres2.push(new IntegerResult(int1.getValue() * int2.getValue()));
		qres2.push(new BooleanResult(false));
		BooleanResult bo1 = (BooleanResult) qres2.pop();
		IntegerResult int3 = (IntegerResult) qres2.pop();
		qres2.push(new StructResult(int3, bo1));
		StructResult srt3 = (StructResult) qres2.pop();
		BagResult bag = (BagResult) qres2.pop();
		BagResult cartP = (BagResult) CartesianProduct.calculate(bag, srt3);
		qres2.push(cartP);
	}

	public static void qresZad1() {
		System.out.println("(bag(1,2.1),bag(3+4,\"test\")) as nazwa");
		@SuppressWarnings("unused")
		IExpression ex6 = new AsExpression("nazwa", new CommaExpression(
				new BagExpression(new CommaExpression(new IntegerTerminal(1),
						new DoubleTerminal(2.1))), new BagExpression(
						new CommaExpression(
								new PlusExpression(new IntegerTerminal(5),
										new IntegerTerminal(4)),
								new StringTerminal("test")))));

		QresStack qres1 = new QresStack();
		qres1.push(new IntegerResult(1));
		qres1.push(new DoubleResult(2.1));
		DoubleResult mr = (DoubleResult) qres1.pop();
		IntegerResult ml = (IntegerResult) qres1.pop();
		qres1.push(new StructResult(ml, mr));
		StructResult sr1 = (StructResult) qres1.pop();
		qres1.push(new BagResult(sr1.elements()));
		qres1.push(new IntegerResult(3));
		qres1.push(new IntegerResult(4));
		IntegerResult mr1 = (IntegerResult) qres1.pop();
		IntegerResult ml1 = (IntegerResult) qres1.pop();
		qres1.push(new IntegerResult(ml1.getValue() + mr1.getValue()));
		qres1.push(new StringResult("test"));
		StringResult mr2 = (StringResult) qres1.pop();
		IntegerResult ml2 = (IntegerResult) qres1.pop();
		qres1.push(new StructResult(ml2, mr2));
		StructResult sr2 = (StructResult) qres1.pop();
		qres1.push(new BagResult(sr2.elements()));
		BagResult mr3 = (BagResult) qres1.pop();
		BagResult ml3 = (BagResult) qres1.pop();
		BagResult cartP = (BagResult) CartesianProduct.calculate(ml3, mr3);
		qres1.push(cartP);
		BagResult b4 = (BagResult) qres1.pop();
		LinkedList<ISingleResult> sres2 = new LinkedList<ISingleResult>();
		for (ISingleResult x : b4.getElements()) {
			sres2.add(new BinderResult("nazwa", x));
		}
		qres1.push(new BagResult(sres2));
	}

	public static void qresTest() {

		QresStack qre = new QresStack();
		qre.push(new IntegerResult(1));
		qre.push(new IntegerResult(2));
		qre.push(new IntegerResult(3));
		IntegerResult multiRight = (IntegerResult) qre.pop(); // 3
		IntegerResult multiLeft = (IntegerResult) qre.pop(); // 2
		IntegerResult multiRes = new IntegerResult(multiLeft.getValue()
				* multiRight.getValue());
		qre.push(multiRes);
		IntegerResult plusRight = (IntegerResult) qre.pop(); // 6
		IntegerResult plusLeft = (IntegerResult) qre.pop(); // 1
		IntegerResult plusRes = new IntegerResult(plusLeft.getValue()
				+ plusRight.getValue());
		qre.push(plusRes);
		qre.push(new IntegerResult(4));
		IntegerResult minusRight = (IntegerResult) qre.pop(); // 4
		IntegerResult minusLeft = (IntegerResult) qre.pop(); // 7
		IntegerResult minusRes = new IntegerResult(minusLeft.getValue()
				- minusRight.getValue());
		qre.push(minusRes);

		@SuppressWarnings("unused")
		IExpression ex5 = new BagExpression(new CommaExpression(
				new CommaExpression(new StringTerminal("Ala"),
						new StringTerminal("ma")), new AsExpression("co",
						new NameTerminal("kota"))));

		QresStack qres = new QresStack();
		qres.push(new StringResult("Ala"));
		qres.push(new StringResult("ma"));
		StringResult mr = (StringResult) qres.pop();
		StringResult ml = (StringResult) qres.pop();
		qres.push(new StructResult(ml, mr));
		qres.push(new StringResult("kota"));
		StringResult brs = (StringResult) qres.pop();
		BinderResult br = new BinderResult("co", brs);
		qres.push(br);
		BinderResult br2 = (BinderResult) qres.pop();
		StructResult sr1 = (StructResult) qres.pop();
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		list.addAll(sr1.elements());
		list.add(br2);
		BagResult bgr = new BagResult(list);
		qres.push(bgr);
	}

	public static void astZad() {
		@SuppressWarnings("unused")
		IExpression ex = new DotExpression(new DotExpression(
				new WhereExpression(new NameTerminal("osoba"),
						new NameTerminal("żonaty")),
				new NameTerminal("książka")), new NameTerminal("autor"));
		System.out.println("!. osoba where (count(imie) > 1)");
		@SuppressWarnings("unused")
		IExpression ex1 = new WhereExpression(new NameTerminal("osoba"),
				new GreaterThanExpression(new CountExpression(new NameTerminal(
						"imię")), new IntegerTerminal(1)));
		System.out
				.println("2. firma where (lokalizacja in (bag(„Warszawa”, „Łódź”)))");
		@SuppressWarnings("unused")
		IExpression ex2 = new WhereExpression(new NameTerminal("firma"),
				new InExpression(new NameTerminal("lokalizacja"),
						new BagExpression(new CommaExpression(
								new StringTerminal("Warszawa"),
								new StringTerminal("Łódź")))));
		System.out.println("3. bag(1,2) in bag(1,2,3)");
		@SuppressWarnings("unused")
		IExpression ex3 = new InExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));
		System.out
				.println("4. (firma where nazwa=”XYZ”).(zatrudnia where nazwisko=”Kowalski”)");
		@SuppressWarnings("unused")
		IExpression ex4 = new DotExpression(new WhereExpression(
				new NameTerminal("firma"), new EqualsExpression(
						new NameTerminal("nazwa"), new StringTerminal("XYZ"))),
				new WhereExpression(new NameTerminal("zatrudnia"),
						new EqualsExpression(new NameTerminal("nazwisko"),
								new StringTerminal("Kowalski"))));
	}

	// public static <resultCast> void tree_walk(IExpression node, QresStack
	// stack){
	// if(ITerminalExpression.class.isInstance(node)){
	// ITerminalExpression<?> exp = (ITerminalExpression<?>) node;
	// System.out.println("leaf: " + exp.getValue().getClass().getSimpleName());
	// try {
	// String resultClassName = "jpslab.ast.result." +
	// exp.getValue().getClass().getSimpleName() + "Result";
	// String resultCastName = "java.lang." +
	// exp.getValue().getClass().getSimpleName();
	// Class<?> resultClass = Class.forName(resultClassName);
	// Class<?> resultCast = Class.forName(resultCastName);
	// @SuppressWarnings("unchecked")
	// resultCast val = (resultCast) exp.getValue();
	// Constructor<?> val2 = resultClass.getDeclaredConstructor(resultCast);
	// stack.push((IAbstractQueryResult) val2.newInstance(val));
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// }else if(IBinaryExpression.class.isInstance(node)){
	// IBinaryExpression exp = (IBinaryExpression) node;
	// System.out.println("operator: " + exp.getClass().getSimpleName());
	// tree_walk(exp.getLeftExpression(), stack);
	// tree_walk(exp.getRightExpression(), stack);
	// IAbstractQueryResult res_right = (IAbstractQueryResult) stack.pop();
	// IAbstractQueryResult res_left = (IAbstractQueryResult) stack.pop();
	// System.out.println(res_right.getClass().getSimpleName() + "|" +
	// res_left.getClass().getSimpleName());
	// LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
	// if(ICommaExpression.class.isInstance(exp)){
	// list.add((ISimpleResult<?>) res_right);
	// list.add((ISimpleResult<?>) res_left);
	// stack.push(new StructResult(list));
	// }else if(IPlusExpression.class.isInstance(exp)){
	//
	// }
	// }else{
	// if(IAuxiliaryNameExpression.class.isInstance(node)){
	// IAuxiliaryNameExpression exp = (IAuxiliaryNameExpression) node;
	// System.out.println("ANE: " + exp.getAuxiliaryName());
	// tree_walk(exp.getInnerExpression(), stack);
	// }else if(IUnaryExpression.class.isInstance(node)){
	// IUnaryExpression exp = (IUnaryExpression) node;
	// tree_walk(exp.getInnerExpression(), stack);
	// }
	// }
	// }

}
