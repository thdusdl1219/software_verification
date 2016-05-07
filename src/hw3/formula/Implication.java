package hw3.formula;

/**
 * A formula to represent: antecedent => consequent
 *
 */
public class Implication extends Formula {
	private Formula antecedent;
	private Formula consequent;
	
	public Implication(Formula ant, Formula con) {
		antecedent = ant;
		consequent = con;
	}
	
	/**
	 * @return the antecedent p of formula p => q
	 */
	public Formula getAntecedent() {
		return antecedent;
	}

	/**
	 * @return the consequent q of formula p => q
	 */
	public Formula getConsequence() {
		return consequent;
	}
	
	@Override
	public String toString() {
		return "(" + antecedent + " => " + consequent + ")";
	}
	
	@Override
	public <T> T operation(FormulaVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
