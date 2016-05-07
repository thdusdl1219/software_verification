package hw3.formula;

/**
 * An atomic formula for true or false
 *
 */
public class Constant extends AtomicFormula {
	private boolean value;
	
	public Constant(boolean b) {
		this.value = b;
	}

	/**
	 * @return true or false
	 */
	public boolean getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(value);
	}
	
	@Override
	public <T> T operation(FormulaVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
