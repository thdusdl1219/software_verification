package hw3.formula;

public interface FormulaVisitor<T> {
	
	public T visit (Negation f);
	public T visit (Conjunction f);
	public T visit (Disjunction f);
	public T visit (Implication f);
	public T visit (Variable f);
	public T visit (Constant f);
}
