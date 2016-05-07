package hw3;

import hw3.formula.Formula;

public class Problem3 {
	
	/**
	 * Check whether a given Boolean formula is unsatisfiable by using resolution. The function
	 * unsat should return TRUE if the formula f is UNSATISFIABLE.
	 *  
	 * @param f a Boolean formula
	 * @return false if the formula is unsatisfiable, and false if the formula is satisfiable.
	 */
	public boolean unsat(Formula f) {
		//TODO: translate f into a set of clauses in CNF using Problem2.CNF		
		//TODO: Implement a simple resolution algorithm.
		//TODO: resolution rule is applied until the empty clause is derived or, the resolution of every possible pair of clauses is performed.
		//TODO: may want to use some data structure to maintain information about which pairs of clauses are considered (to avoid infinite loop).  

		return false;
	}
	
}
