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
		Formula snf = Problem1.simplify(Problem1.NNF(f));
		Set<Set<Integer>> cls = Problem2.CNF(f);
		
		//TODO: Implement a simple resolution algorithm.
		
		
		//TODO: resolution rule is applied until the empty clause is derived or, the resolution of every possible pair of clauses is performed.
		Set<Integer> vars = Problem1.vars(snf);
		
		while(true) {
			if(cls.stream().anyMatch(subs -> subs.isEmpty())) {
				return true;
			}
			if(vars.isEmpty()) {
				return false;
			}
			resolution(cls, vars, new HashSet<Integer>(vars));
		}

		//TODO: may want to use some data structure to maintain information about which pairs of clauses are considered (to avoid infinite loop).  
	}
	
	
	private Set<Integer> mapper(Set<Integer> s, Integer n) {
		Set<Integer> result = new HashSet<Integer> (s);
		result.remove(n);
		return result;
	}
	
	private Set<Set<Integer>> simplify (Set<Integer> s) {
		
		Set<Integer> tmp = new HashSet<Integer> (s);
		//Set<Integer> ss = s.stream().filter(i -> !tmp.contains(-i)).collect(Collectors.toCollection(HashSet::new));
		Set<Set<Integer>> result = new HashSet<Set<Integer>> ();
		if(s.stream().anyMatch(i -> tmp.contains(-i)))
			return result;
		else {
			result.add(s);
			return result;
		}
	}
	
	private Set<Set<Integer>> makeDis(Set<Set<Integer>> c, Set<Set<Integer>> d, Integer i) {
		Set<Set<Integer>> result = new HashSet<Set<Integer>> ();
		c = c.stream().map(s -> mapper(s, i)).collect(Collectors.toCollection(HashSet::new));
		d = d.stream().map(s -> mapper(s, -i)).collect(Collectors.toCollection(HashSet::new));
		for(Set<Integer> s1 : c) {
			for(Set<Integer> s2 : d) {
				if(s1.isEmpty() && s2.isEmpty()) {result.add(new HashSet<Integer>()); return result;}
				else {
					Set<Integer> ns = new HashSet<Integer> (s1);
					ns.addAll(s2);
					result.addAll(simplify(ns));
				}
			}
		}
		
		return result;
	}
	
	private Integer select(Set<Set<Integer>> cls, Set<Integer> vars) {
		for(Integer j : vars) {
			if(cls.stream().anyMatch(s -> s.contains(j) && s.size() == 1))
				return j;
		}
		
		return vars.iterator().next();
	}
	
	private void resolution(Set<Set<Integer>> cls, Set<Integer> vars, Set<Integer> cvars) {
		Integer i  = select(cls, vars);
		Set<Set<Integer>> tmp = new HashSet<Set<Integer>> (cls);
		for(Integer j : cvars) {
			tmp.stream().filter(s ->s.contains(j) && s.contains(-j)).forEach(s -> cls.remove(s));
		}
		//int size = cls.size();
		Set<Set<Integer>> cs = cls.stream().filter(s -> s.contains(i)).collect(Collectors.toCollection(HashSet::new));
		Set<Set<Integer>> ds = cls.stream().filter(s -> s.contains(-i)).collect(Collectors.toCollection(HashSet::new));
		
		Set<Set<Integer>> discd = makeDis(cs, ds, i);
		
		//Set<Set<Integer>> scs = cls.stream().filter(s -> s.contains(i) && s.size() == 1).collect(Collectors.toCollection(HashSet::new));
		//Set<Set<Integer>> dcs = cls.stream().filter(s -> s.contains(-i) && s.size() == 1).collect(Collectors.toCollection(HashSet::new));
		
		cls.addAll(discd);
		cls.removeAll(cs);
		cls.removeAll(ds);
		
		//if(!scs.isEmpty() && !dcs.isEmpty())
			//cls.add(new HashSet<Integer> ());
		//if(size == cls.size())
		vars.remove(i);
	}
}
