package hw3.formula;

/**
 * A variable, identified by positive integers
 *
 */
public class Variable extends AtomicFormula {
	private int name;
	
	public Variable(int n) {
		assert n > 0;
		this.name = n;
	}

	/**
	 * @return a non-negative integer to represent ids.
	 */
	public int getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "p" + Integer.toString(name);
	}
	
	@Override
	public <T> T operation(FormulaVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
