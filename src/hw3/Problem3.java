package hw3;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import hw3.formula.Disjunction;
import hw3.formula.Formula;
import hw3.formula.Negation;
import hw3.formula.Variable;

public class Problem3 {
	
	/**
	 * Check whether a given Boolean formula is unsatisfiable by using resolution. The function
	 * unsat should return TRUE if the formula f is UNSATISFIABLE.
	 *  
	 * @param f a Boolean formula
	 * @return true if the formula is unsatisfiable, and false if the formula is satisfiable.
	 * @throws Exception 
	 */
	public boolean unsat(Formula f) throws Exception {
		
		//TODO: translate f into a set of clauses in CNF using Problem2.CNF
		Set<Set<Integer>> cls = Problem2.CNF(f);
		
		//TODO: Implement a simple resolution algorithm.
		
		
		//TODO: resolution rule is applied until the empty clause is derived or, the resolution of every possible pair of clauses is performed.
		Set<Integer> vars = Problem2.vars(cls);
		
		while(true) {
			if(cls.stream().anyMatch(subs -> subs.isEmpty())) {
				return true;
			}
			if(vars.isEmpty()) {
				return false;
			}
			resolution(cls, vars);
		}

		//TODO: may want to use some data structure to maintain information about which pairs of clauses are considered (to avoid infinite loop).  
	}
	
	
	private Set<Integer> mapper(Set<Integer> s, Integer n) {
		s.remove(n);
		return s;
	}
	
	private Set<Integer> simplify (Set<Integer> s1, Set<Integer> s2) {
		Set<Integer> result = new HashSet<Integer> ();
		
		result.addAll(s1);
		result.addAll(s2);
		
		Set<Integer> tmp = new HashSet<Integer> (result);
		
		result = result.stream().filter(i -> !tmp.contains(-i)).collect(Collectors.toCollection(HashSet::new));
		
		return result;
	}
	
	private Set<Set<Integer>> makeDis(Set<Set<Integer>> c, Set<Set<Integer>> d) {
		Set<Set<Integer>> result = new HashSet<Set<Integer>> ();

		for(Set<Integer> s1 : c) {
			for(Set<Integer> s2 : d) {
				result.add(simplify(s1, s2));
			}
		}
		
		return result;
	}
	
	private void resolution(Set<Set<Integer>> cls, Set<Integer> vars) {
		Integer i = vars.iterator().next();
		Set<Set<Integer>> cs = cls.stream().filter(s -> s.contains(i)).map(s -> mapper(s, i)).collect(Collectors.toCollection(HashSet::new));
		Set<Set<Integer>> ds = cls.stream().filter(s -> s.contains(-i)).map(s -> mapper(s, -i)).collect(Collectors.toCollection(HashSet::new));
		
		Set<Set<Integer>> discd = makeDis(cs, ds);
		
		cls.addAll(discd);
	}
}
