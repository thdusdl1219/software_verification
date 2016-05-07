package hw3;

import java.util.Map;
import hw3.formula.Formula;

public class Problem4 {
	
	/**
	 * Find a satisfiable valuation of a given Boolean formula f. If the formula is unsatisfiable,
	 * then return null. 
	 * 
	 * @param f a Boolean formula
	 * @return a satisfiable valuation if f is satisfiable, and null if f is unsatisfiable.
	 */
	public Map<Integer,Boolean> sat(Formula f) {
		//TODO: translate f into a set of clauses in CNF using Problem2.CNF		
		//TODO: Implement a simple DPLL algorithm.
		//TODO: unit propagation, split, pure literal rules are appropriately applied until a satisfiable assignment is found
		return null;
	}
}