package jpslab.ast.visitor;

import java.util.Collection;
import java.util.LinkedList;

import jpslab.helpers.CartesianProduct;
import jpslab.helpers.Eval;
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
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.ast.binary.IAndExpression;
import edu.pjwstk.jps.ast.binary.ICloseByExpression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;
import edu.pjwstk.jps.ast.binary.IDivideExpression;
import edu.pjwstk.jps.ast.binary.IDotExpression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;
import edu.pjwstk.jps.ast.binary.IForAnyExpression;
import edu.pjwstk.jps.ast.binary.IGreaterOrEqualThanExpression;
import edu.pjwstk.jps.ast.binary.IGreaterThanExpression;
import edu.pjwstk.jps.ast.binary.IInExpression;
import edu.pjwstk.jps.ast.binary.IIntersectExpression;
import edu.pjwstk.jps.ast.binary.IJoinExpression;
import edu.pjwstk.jps.ast.binary.ILessOrEqualThanExpression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;
import edu.pjwstk.jps.ast.binary.IMinusExpression;
import edu.pjwstk.jps.ast.binary.IMinusSetExpression;
import edu.pjwstk.jps.ast.binary.IModuloExpression;
import edu.pjwstk.jps.ast.binary.IMultiplyExpression;
import edu.pjwstk.jps.ast.binary.INotEqualsExpression;
import edu.pjwstk.jps.ast.binary.IOrExpression;
import edu.pjwstk.jps.ast.binary.IOrderByExpression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;
import edu.pjwstk.jps.ast.binary.IUnionExpression;
import edu.pjwstk.jps.ast.binary.IWhereExpression;
import edu.pjwstk.jps.ast.binary.IXORExpression;
import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;
import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;
import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.ast.terminal.IStringTerminal;
import edu.pjwstk.jps.ast.unary.IAvgExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.ast.unary.ICountExpression;
import edu.pjwstk.jps.ast.unary.IExistsExpression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;
import edu.pjwstk.jps.ast.unary.IMinExpression;
import edu.pjwstk.jps.ast.unary.INotExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.ast.unary.ISumExpression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBooleanResult;
import edu.pjwstk.jps.result.ISimpleResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class Visitor implements IInterpreter {

	private IQResStack qres;
	private IENVS envs;
	private ISBAStore store;

	public Visitor(ISBAStore store) {
		this.qres = new QresStack();
		this.envs = new ENVS();
		this.store = store;
		this.envs.init(this.store.getEntryOID(), this.store);
	}

	public Visitor() {
		this.qres = new QresStack();
		this.envs = new ENVS();
	}

	public IAbstractQueryResult getResult(){
		return (IAbstractQueryResult) this.qres.pop();
	}
	
	@Override
	public void visitAsExpression(IAsExpression expr) {
		expr.getInnerExpression().accept(this);
		String name = expr.getAuxiliaryName();
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IBagResult) {
			BagResult bag = new BagResult();
			for (ISingleResult x : ((IBagResult) res).getElements()) {
				bag.add(new BinderResult(name, x));
			}
			this.qres.push(bag);
		} else if (res instanceof IStructResult) {
			this.qres.push(new BinderResult(name, (IStructResult) res));
		} else {
			res = Eval.toBag(res);
			for (ISingleResult x : ((IBagResult) res).getElements()) {
				this.qres.push(new BinderResult(name, x));
			}
		}
	}

	@Override
	public void visitGroupAsExpression(IGroupAsExpression expr) {
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult res = this.qres.pop();
		this.qres.push(new BinderResult(expr.getAuxiliaryName(), res));
	}

	@Override
	public void visitAllExpression(IForAllExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = this.qres.pop();
		IBagResult leftBag = Eval.toBag(leftRes);
		for (ISingleResult leftEl : leftBag.getElements()) {
			this.envs.nested(leftEl, this.store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = this.qres.pop();
			rightRes = Eval.toSingleResult(rightRes);
			rightRes = Eval.deref(rightRes, this.store);
			if (!(rightRes instanceof IBooleanResult)) {
				throw new RuntimeException("Not single boolean result.");
			}
			Boolean rres = ((BooleanResult) rightRes).getValue();
			if (!rres) {
				this.envs.pop();
				this.qres.push(new BooleanResult(false));
				return;
			}
		}
		this.qres.push(new BooleanResult(true));
	}

	@Override
	public void visitAndExpression(IAndExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		if (!(leftRes instanceof IBooleanResult)) {
			throw new RuntimeException("Can only negate boolean.");
		}
		Boolean lres = ((BooleanResult) leftRes).getValue();
		if (lres && rightRes instanceof IBooleanResult) {
			Boolean rres = ((BooleanResult) rightRes).getValue();
			this.qres.push(new BooleanResult(lres && rres));
		} else if (!lres) {
			this.qres.push(new BooleanResult(false));
		} else {
			throw new RuntimeException("Can only negate boolean.");
		}
	}

	@Override
	public void visitAnyExpression(IForAnyExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = this.qres.pop();
		IBagResult leftBag = Eval.toBag(leftRes);
		for (ISingleResult leftEl : leftBag.getElements()) {
			this.envs.nested(leftEl, this.store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = this.qres.pop();
			rightRes = Eval.toSingleResult(rightRes);
			rightRes = Eval.deref(rightRes, this.store);
			if (!(rightRes instanceof IBooleanResult)) {
				throw new RuntimeException("Not single boolean result.");
			}
			Boolean rres = ((BooleanResult) rightRes).getValue();
			if (rres) {
				this.envs.pop();
				this.qres.push(new BooleanResult(true));
				return;
			}
		}
		this.qres.push(new BooleanResult(false));
	}

	@Override
	public void visitCloseByExpression(ICloseByExpression expr) {}

	@Override
	public void visitCommaExpression(ICommaExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		rightRes = Eval.toBag(rightRes);
		leftRes = Eval.toBag(leftRes);
		IAbstractQueryResult bag = Eval.cartesianProduct(leftRes, rightRes);
		this.qres.push(bag);
	}

	@Override
	public void visitDivideExpression(IDivideExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);

		Double left = null;
		Double right = null;
		Double result = null;
		if (leftRes instanceof DoubleResult && rightRes instanceof DoubleResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
		} else if (leftRes instanceof DoubleResult
				&& rightRes instanceof IntegerResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue().doubleValue();
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof DoubleResult) {
			left = ((IntegerResult) leftRes).getValue().doubleValue();
			right = ((DoubleResult) rightRes).getValue();
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof IntegerResult) {
			left = ((IntegerResult) leftRes).getValue().doubleValue();
			right = ((IntegerResult) rightRes).getValue().doubleValue();
		} else {
			throw new RuntimeException("Nieznany typ result"
					+ leftRes.getClass() + " lub " + rightRes.getClass());
		}
		result = left / right;
		if (result == result.intValue()) {
			this.qres.push(new IntegerResult(result.intValue()));
		} else {
			this.qres.push(new DoubleResult(result));
		}
	}

	@Override
	public void visitDotExpression(IDotExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = this.qres.pop();
		IBagResult leftBag = Eval.toBag(leftRes);
		BagResult dotRes = new BagResult();
		for (ISingleResult leftEl : leftBag.getElements()) {
			this.envs.nested(leftEl, this.store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = this.qres.pop();
			// dotRes.add(Eval.deref(rightRes, this.store));
			dotRes.add(rightRes);
			this.envs.pop();
		}
		dotRes = (BagResult) Eval.derefBagStruct(dotRes, this.store);
		this.qres.push(dotRes);
	}

	@Override
	public void visitEqualsExpression(IEqualsExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);
		
		this.qres.push(new BooleanResult(leftRes.equals(rightRes)));
	}

	@Override
	public void visitGreaterThanExpression(IGreaterThanExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);
		Boolean bol = false;
		double num2 = Eval.get2cmp(rightRes);
		double num1 = Eval.get2cmp(leftRes);
		if (num1 > num2) {
			bol = true;
		}
		this.qres.push(new BooleanResult(bol));
	}

	@Override
	public void visitGreaterOrEqualThanExpression(
			IGreaterOrEqualThanExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);
		Boolean bol = false;
		double num2 = Eval.get2cmp(rightRes);
		double num1 = Eval.get2cmp(leftRes);
		if (num1 >= num2) {
			bol = true;
		}
		this.qres.push(new BooleanResult(bol));
	}

	@Override
	public void visitInExpression(IInExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		Collection<ISingleResult> leftSide = CartesianProduct
				.toSingleResultList(leftRes);
		Collection<ISingleResult> rightSide = CartesianProduct
				.toSingleResultList(rightRes);
		Integer count = 0;
		for (ISingleResult x : leftSide) {
			if(rightSide.contains(x)){
				count += 1;
			}
		}
		this.qres.push(new BooleanResult((count == leftSide.size())));
	}

	@Override
	public void visitIntersectExpression(IIntersectExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		BagResult rightBag = (BagResult) Eval.toBag(rightRes);
		BagResult leftBag = (BagResult) Eval.toBag(leftRes);
//		for (ISingleResult x : rightSide) {
//			bagunion.getElements().add(
//					(ISingleResult) Eval.deref(x, this.store));
//		}
		BagResult bag = new BagResult();
		for (ISingleResult x : leftBag.getElements()) {
			if(rightBag.getElements().contains(x)){
				bag.add(x);
			}
		}
		this.qres.push(bag);
	}

	@Override
	public void visitJoinExpression(IJoinExpression expr) {
		expr.getLeftExpression().accept(this);
		BagResult eres = new BagResult();
		IAbstractQueryResult leftRes = this.qres.pop();
		IBagResult leftBag = Eval.toBag(leftRes);
		for (ISingleResult leftEl : leftBag.getElements()) {
			this.envs.nested(leftEl, this.store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = this.qres.pop();
			eres.add(Eval.cartesianProduct(Eval.toBag(leftEl), Eval.toBag(rightRes)));
			this.envs.pop();
		}
		eres = (BagResult) Eval.derefBagStruct(eres, this.store);
		this.qres.push(eres);
	}

	@Override
	public void visitLessOrEqualThanExpression(ILessOrEqualThanExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);
		Boolean bol = false;
		double num2 = Eval.get2cmp(rightRes);
		double num1 = Eval.get2cmp(leftRes);
		if (num1 <= num2) {
			bol = true;
		}
		this.qres.push(new BooleanResult(bol));
	}

	@Override
	public void visitLessThanExpression(ILessThanExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);
		Boolean bol = false;
		double num2 = Eval.get2cmp(rightRes);
		double num1 = Eval.get2cmp(leftRes);
		if (num1 < num2) {
			bol = true;
		}
		this.qres.push(new BooleanResult(bol));
	}

	@Override
	public void visitMinusExpression(IMinusExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);

		Object left = null;
		Object right = null;
		IAbstractQueryResult result = null;
		if (leftRes instanceof DoubleResult && rightRes instanceof DoubleResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
			result = new DoubleResult((Double) left - (Double) right);
		} else if (leftRes instanceof DoubleResult
				&& rightRes instanceof IntegerResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue();
			result = new DoubleResult((Double) left
					- ((Integer) right).doubleValue());
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof DoubleResult) {
			left = ((IntegerResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
			result = new DoubleResult(((Integer) left).doubleValue()
					- (Double) right);
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof IntegerResult) {
			left = ((IntegerResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue();
			result = new IntegerResult((Integer) left - (Integer) right);
		} else {
			throw new RuntimeException("Nieznany typ result"
					+ leftRes.getClass() + " lub " + rightRes.getClass());
		}
		this.qres.push(result);
	}

	@Override
	public void visitMinusSetExpression(IMinusSetExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		BagResult rightBag = (BagResult) Eval.toBag(rightRes);
		BagResult leftBag = (BagResult) Eval.toBag(leftRes);
		BagResult bag = new BagResult();
		for (ISingleResult x : leftBag.getElements()) {
			if(!rightBag.getElements().contains(x)){
				bag.add(x);
			}
		}
		this.qres.push(bag);
	}

	@Override
	public void visitModuloExpression(IModuloExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);

		Double left = null;
		Double right = null;
		Double result = null;
		if (leftRes instanceof DoubleResult && rightRes instanceof DoubleResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
		} else if (leftRes instanceof DoubleResult
				&& rightRes instanceof IntegerResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue().doubleValue();
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof DoubleResult) {
			left = ((IntegerResult) leftRes).getValue().doubleValue();
			right = ((DoubleResult) rightRes).getValue();
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof IntegerResult) {
			left = ((IntegerResult) leftRes).getValue().doubleValue();
			right = ((IntegerResult) rightRes).getValue().doubleValue();
		} else {
			throw new RuntimeException("Nieznany typ result"
					+ leftRes.getClass() + " lub " + rightRes.getClass());
		}
		result = left % right;
		if (result == result.intValue()) {
			this.qres.push(new IntegerResult(result.intValue()));
		} else {
			this.qres.push(new DoubleResult(result));
		}
	}

	@Override
	public void visitMultiplyExpression(IMultiplyExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);

		Double left = null;
		Double right = null;
		Double result = null;
		if (leftRes instanceof DoubleResult && rightRes instanceof DoubleResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
		} else if (leftRes instanceof DoubleResult
				&& rightRes instanceof IntegerResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue().doubleValue();
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof DoubleResult) {
			left = ((IntegerResult) leftRes).getValue().doubleValue();
			right = ((DoubleResult) rightRes).getValue();
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof IntegerResult) {
			left = ((IntegerResult) leftRes).getValue().doubleValue();
			right = ((IntegerResult) rightRes).getValue().doubleValue();
		} else {
			throw new RuntimeException("Nieznany typ result"
					+ leftRes.getClass() + " lub " + rightRes.getClass());
		}
		result = left * right;
		if (result == result.intValue()) {
			this.qres.push(new IntegerResult(result.intValue()));
		} else {
			this.qres.push(new DoubleResult(result));
		}
	}

	@Override
	public void visitNotEqualsExpression(INotEqualsExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);
		Boolean bol = false;
		double num2 = Eval.get2cmp(rightRes);
		double num1 = Eval.get2cmp(leftRes);
		if (num1 != num2) {
			bol = true;
		}
		this.qres.push(new BooleanResult(bol));
	}

	@Override
	public void visitOrderByExpression(IOrderByExpression expr) {}

	@Override
	public void visitOrExpression(IOrExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		if (!(leftRes instanceof IBooleanResult)) {
			throw new RuntimeException("Can only negate boolean.");
		}
		Boolean lres = ((BooleanResult) leftRes).getValue();
		if (!lres && rightRes instanceof IBooleanResult) {
			Boolean rres = ((BooleanResult) rightRes).getValue();
			this.qres.push(new BooleanResult(lres || rres));
		} else if (lres) {
			this.qres.push(new BooleanResult(true));
		} else {
			throw new RuntimeException("Can only negate boolean.");
		}
	}

	@Override
	public void visitPlusExpression(IPlusExpression expr) {
		expr.getRightExpression().accept(this);
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = this.qres.pop();
		IAbstractQueryResult rightRes = this.qres.pop();
		leftRes = Eval.toSingleResult(leftRes);
		rightRes = Eval.toSingleResult(rightRes);
		leftRes = Eval.deref(leftRes, this.store);
		rightRes = Eval.deref(rightRes, this.store);

		Object left = null;
		Object right = null;
		IAbstractQueryResult result = null;
		if (leftRes instanceof StringResult || rightRes instanceof StringResult
				|| leftRes instanceof BooleanResult
				|| rightRes instanceof BooleanResult) {
			left = ((ISimpleResult<?>) leftRes).getValue().toString();
			right = ((ISimpleResult<?>) rightRes).getValue().toString();
			result = new StringResult((String) left + (String) right);
		} else if (leftRes instanceof DoubleResult
				&& rightRes instanceof DoubleResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
			result = new DoubleResult((Double) left + (Double) right);
		} else if (leftRes instanceof DoubleResult
				&& rightRes instanceof IntegerResult) {
			left = ((DoubleResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue();
			result = new DoubleResult((Double) left
					+ ((Integer) right).doubleValue());
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof DoubleResult) {
			left = ((IntegerResult) leftRes).getValue();
			right = ((DoubleResult) rightRes).getValue();
			result = new DoubleResult(((Integer) left).doubleValue()
					+ (Double) right);
		} else if (leftRes instanceof IntegerResult
				&& rightRes instanceof IntegerResult) {
			left = ((IntegerResult) leftRes).getValue();
			right = ((IntegerResult) rightRes).getValue();
			result = new IntegerResult((Integer) left + (Integer) right);
		} else {
			throw new RuntimeException("Nieznany typ result"
					+ leftRes.getClass() + " lub " + rightRes.getClass());
		}
		this.qres.push(result);
	}

	@Override
	public void visitUnionExpression(IUnionExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		BagResult bagunion = new BagResult();
		IBagResult bagLeft = Eval.toBag(leftRes);
		IBagResult bagRight = Eval.toBag(rightRes);
		bagunion.add(bagLeft);
		bagunion.add(bagRight);
		bagunion = (BagResult) Eval.derefBagStruct(bagunion, this.store);
		this.qres.push(bagunion);
	}

	@Override
	public void visitWhereExpression(IWhereExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = this.qres.pop();
		BagResult whereRes = new BagResult();
		IBagResult leftBag = Eval.toBag(leftRes);
		for (ISingleResult leftElem : leftBag.getElements()) {
			this.envs.nested(leftElem, this.store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightResult = this.qres.pop();
			rightResult = Eval.toSingleResult(rightResult);
			rightResult = Eval.deref(rightResult, this.store);
			if (rightResult instanceof IBooleanResult) {
				if(((IBooleanResult) rightResult).getValue()){
					whereRes.add(leftElem);
				}
			}
			envs.pop();
		}
		whereRes = (BagResult) Eval.derefBagStruct(whereRes, this.store);
		qres.push(whereRes);
	}

	@Override
	public void visitXORExpression(IXORExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = this.qres.pop();
		IAbstractQueryResult leftRes = this.qres.pop();
		if (leftRes instanceof IBooleanResult
				&& rightRes instanceof IBooleanResult) {
			Boolean rres = ((BooleanResult) rightRes).getValue();
			Boolean lres = ((BooleanResult) leftRes).getValue();
			this.qres.push(new BooleanResult(!lres != !rres));
		} else {
			throw new RuntimeException("Can only negate boolean.");
		}
	}

	@Override
	public void visitBooleanTerminal(IBooleanTerminal expr) {
		Boolean num = expr.getValue();
		this.qres.push(new BooleanResult(num));
	}

	@Override
	public void visitDoubleTerminal(IDoubleTerminal expr) {
		Double num = expr.getValue();
		this.qres.push(new DoubleResult(num));
	}

	@Override
	public void visitIntegerTerminal(IIntegerTerminal expr) {
		Integer num = expr.getValue();
		this.qres.push(new IntegerResult(num));
	}

	@Override
	public void visitNameTerminal(INameTerminal expr) {
		String exp = expr.getName();
		IBagResult bag1 = this.envs.bind(exp);
		this.qres.push(bag1);
	}

	@Override
	public void visitStringTerminal(IStringTerminal expr) {
		String num = expr.getValue();
		this.qres.push(new StringResult(num));
	}

	@Override
	public void visitBagExpression(IBagExpression expr) {
		BagResult eres = new BagResult();
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult res = this.qres.pop();
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		for (ISingleResult y : list) {
			eres.add(y);
		}
		this.qres.push(eres);
	}

	@Override
	public void visitCountExpression(ICountExpression expr) {
		Integer count = 0;
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult result = this.qres.pop();
		if (result instanceof IBagResult) {
			count = ((IBagResult) result).getElements().size();
		} else {
			count = 1;
		}
		this.qres.push(new IntegerResult(count));
	}

	@Override
	public void visitExistsExpression(IExistsExpression expr) {}

	@Override
	public void visitMaxExpression(IMaxExpression expr) {
		Object max = null;
		expr.getInnerExpression().accept(this);
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		for (ISingleResult x : list) {
			if (x instanceof IntegerResult) {
				if (max != null && (max instanceof Integer)) {
					if (((IntegerResult) x).getValue() > ((Integer) max)) {
						max = ((IntegerResult) x).getValue();
					}
				} else if (max != null && (max instanceof Double)) {
					if (((IntegerResult) x).getValue().doubleValue() > ((Double) max)) {
						max = ((IntegerResult) x).getValue();
					}
				} else {
					max = ((IntegerResult) x).getValue();
				}
			} else if (x instanceof DoubleResult) {
				if (max != null && (max instanceof Integer)) {
					if (((DoubleResult) x).getValue() > ((Integer) max)
							.doubleValue()) {
						max = ((DoubleResult) x).getValue();
					}
				} else if (max != null && (max instanceof Double)) {
					if (((DoubleResult) x).getValue().doubleValue() > ((Double) max)) {
						max = ((DoubleResult) x).getValue();
					}
				} else {
					max = ((DoubleResult) x).getValue();
				}
			} else {
				throw new RuntimeException("Can only max Double and Integer");
			}
		}
		if (max instanceof Double) {
			this.qres.push(new DoubleResult((Double) max));
		} else if (max instanceof Integer) {
			this.qres.push(new IntegerResult((Integer) max));
		}
	}

	@Override
	public void visitMinExpression(IMinExpression expr) {
		Object min = null;
		expr.getInnerExpression().accept(this);
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		for (ISingleResult x : list) {
			if (x instanceof IntegerResult) {
				if (min != null && (min instanceof Integer)) {
					if (((IntegerResult) x).getValue() < ((Integer) min)) {
						min = ((IntegerResult) x).getValue();
					}
				} else if (min != null && (min instanceof Double)) {
					if (((IntegerResult) x).getValue().doubleValue() < ((Double) min)) {
						min = ((IntegerResult) x).getValue();
					}
				} else {
					min = ((IntegerResult) x).getValue();
				}
			} else if (x instanceof DoubleResult) {
				if (min != null && (min instanceof Integer)) {
					if (((DoubleResult) x).getValue() < ((Integer) min).doubleValue()) {
						min = ((DoubleResult) x).getValue();
					}
				} else if (min != null && (min instanceof Double)) {
					if (((DoubleResult) x).getValue().doubleValue() < ((Double) min)) {
						min = ((DoubleResult) x).getValue();
					}
				} else {
					min = ((DoubleResult) x).getValue();
				}
			} else {
				throw new RuntimeException("Can only max Double and Integer");
			}
		}
		if (min instanceof Double) {
			this.qres.push(new DoubleResult((Double) min));
		} else if (min instanceof Integer) {
			this.qres.push(new IntegerResult((Integer) min));
		}
	}

	@Override
	public void visitNotExpression(INotExpression expr) {
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IBooleanResult) {
			this.qres.push(new BooleanResult(!((BooleanResult) res).getValue()));
		} else {
			throw new RuntimeException("Can only negate boolean.");
		}
	}

	@Override
	public void visitStructExpression(IStructExpression expr) {
		StructResult eres = new StructResult();
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult res = this.qres.pop();
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		for (ISingleResult y : list) {
			eres.elements().add(y);
		}
		this.qres.push(eres);
	}

	@Override
	public void visitSumExpression(ISumExpression expr) {
		Double sum = 0.0;
		expr.getInnerExpression().accept(this);
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		Boolean doble = Eval.doubleInList(list);
		for (ISingleResult x : list) {
			if (x instanceof IntegerResult) {
				sum += ((Integer) ((IntegerResult) x).getValue()).doubleValue();
			} else if (x instanceof DoubleResult) {
				sum += ((Double) ((DoubleResult) x).getValue()).doubleValue();
			} else {
				throw new RuntimeException("Avg only double and integer.");
			}
		}
		if (!doble) {
			this.qres.push(new IntegerResult(sum.intValue()));
		} else {
			this.qres.push(new DoubleResult(sum));
		}
	}

	@Override
	public void visitUniqueExpression(IUniqueExpression expr) {
		expr.getInnerExpression().accept(this);
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		BagResult bag = new BagResult();
		for(ISingleResult x:list){
			if(list.indexOf(x) == list.lastIndexOf(x)){
				bag.add(x);
			}
		}
		this.qres.push(bag);
	}

	@Override
	public void visitAvgExpression(IAvgExpression expr) {
		Double avg = 0.0;
		Integer count = 0;
		expr.getInnerExpression().accept(this);
		LinkedList<ISingleResult> list = new LinkedList<ISingleResult>();
		IAbstractQueryResult res = this.qres.pop();
		if (res instanceof IStructResult) {
			list = (LinkedList<ISingleResult>) ((IStructResult) res).elements();
		} else if (res instanceof IBagResult) {
			list = (LinkedList<ISingleResult>) ((IBagResult) res).getElements();
		} else {
			list = (LinkedList<ISingleResult>) Eval.toBag(res).getElements();
		}
		count = list.size();
		Boolean doble = Eval.doubleInList(list);
		for (ISingleResult x : list) {
			if (x instanceof IntegerResult) {
				avg += ((Integer) ((IntegerResult) x).getValue()).doubleValue();
			} else if (x instanceof DoubleResult) {
				avg += ((Double) ((DoubleResult) x).getValue()).doubleValue();
			} else {
				throw new RuntimeException("Avg only double and integer.");
			}
		}
		avg /= count;
		if (!doble && avg == avg.intValue()) {
			this.qres.push(new IntegerResult(avg.intValue()));
		} else {
			this.qres.push(new DoubleResult(avg));
		}
	}

	@Override
	public IAbstractQueryResult eval(IExpression queryTreeRoot) {
		return null;
	}

}
