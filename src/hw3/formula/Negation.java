package hw3.formula;

/**
 * A negation of formula.
 *
 */
public class Negation extends Formula {
	private Formula sub;
	
	public Negation(Formula subformula) {
		sub = subformula;
	}

	/**
	 * @return a negated sub-formula
	 */
	public Formula getSubformula() {
		return sub;
	}
	
	@Override
	public String toString() {
		return "(! " + sub + ")";
	}
	
	@Override
	public <T> T operation(FormulaVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
