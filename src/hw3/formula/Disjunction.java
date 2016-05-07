package hw3.formula;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A formula connected by the or operator
 *
 */
public class Disjunction extends Formula {
	private List<Formula> sub;
	
	public Disjunction(Formula... subformulas) {
		sub = Arrays.asList(subformulas);
	}

	/**
	 * @return a list of subformulas connected by the or operator
	 */
	public List<Formula> getSubformulas() {
		return sub;
	}
	
	@Override
	public String toString() {
		return "(" + sub.stream().map(i -> i.toString()).collect(Collectors.joining(" || ")) + ")";
	}
	
	@Override
	public <T> T operation(FormulaVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
