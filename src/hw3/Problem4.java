package hw3;

import java.util.Map;
import java.util.Set;

import hw3.formula.Formula;

public class Problem4 {
	
	/**
	 * Find a satisfiable valuation of a given Boolean formula f. If the formula is unsatisfiable,
	 * then return null. 
	 * 
	 * @param f a Boolean formula
	 * @return a satisfiable valuation if f is satisfiable, and null if f is unsatisfiable.
	 * @throws Exception 
	 */
	public Map<Integer,Boolean> sat(Formula f) throws Exception {
		//TODO: translate f into a set of clauses in CNF using Problem2.CNF
		Formula snf = Problem1.simplify(Problem1.NNF(f));
		Set<Set<Integer>> cls = Problem2.CNF(f);
		
		Set<Integer> vars = Problem1.vars(snf);
		
		
		
		//TODO: Implement a simple DPLL algorithm.
		//TODO: unit propagation, split, pure literal rules are appropriately applied until a satisfiable assignment is found
		return null;
	}
	
	
}
