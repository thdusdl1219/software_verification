package hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hw3.formula.Constant;
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
		
		
		Map<Integer, Set<Set<Integer>>> backTrack = new HashMap<Integer, Set<Set<Integer>>> ();
		
		if(snf instanceof Constant) {
			Constant c = (Constant) snf;
			if(c.getValue()) return new HashMap<Integer, Boolean> ();
			else return null;
		}
		List<Integer> result = new ArrayList<Integer> ();
		
		Set<Integer> vars = Problem2.vars(cls);
		Set<Integer> posvars = new HashSet<Integer> (vars);
		
		vars.addAll(negationSet(vars));
		
		
		cls = pure(cls, result, vars, posvars);
		
		if(cls.isEmpty()) {
			return makeListToMap(result);
		}
		
		
		List<Integer> splitresult = new ArrayList<Integer> ();
		
		while(true) {
 			cls = pure(cls, result, vars, posvars);
			if(checkEND(cls, posvars, result, splitresult)) {
				result.addAll(splitresult);
				return makeListToMap(result);
			}
			if(vars.isEmpty()) {
				break;
			}
			if(hasFalse(cls)) {
 				cls = backtrack(cls, vars, splitresult, backTrack, result, posvars);
			}
			if(hasVars(cls, splitresult)) {
				cls = unit(cls, vars, splitresult);
			}
			else {
				split(cls, vars, posvars, splitresult, backTrack);
			}
			if(hasVars(cls, splitresult)) {
				cls = unit(cls, vars, splitresult);
			}
		}
		
		
		
		//TODO: Implement a simple DPLL algorithm.
		//TODO: unit propagation, split, pure literal rules are appropriately applied until a satisfiable assignment is found
		return null;
	}
	
	private Integer pickInteger(Set<Integer> posvars, List<Integer> result) {
		Set<Integer> r = new HashSet<Integer> (posvars);
		r.removeIf(i -> result.contains(i) || result.contains(-i));
		return r.iterator().next();
	}
	
	private boolean containsAll(Set<Integer> posvars, List<Integer> result) {
		Set<Integer> r = new HashSet<Integer> (posvars);
		r.removeIf(i -> result.contains(i) || result.contains(-i));
		return r.isEmpty();
	}
	
	private void split(Set<Set<Integer>> cls, Set<Integer> vars, Set<Integer> posvars, List<Integer> result, Map<Integer, Set<Set<Integer>>> backTrack) {
		if(!containsAll(posvars, result)) {
			Integer v = pickInteger(posvars, result);
			result.add(v);
			backTrack.put(v, cls);
			vars.remove(v);
		}
		else {
			Integer v = result.get(result.size() - 1);
			result.remove(v);
			result.add(-v);
			backTrack.put(-v, cls);
			vars.remove(-v);
		}
		
	}
	
	private Set<Set<Integer>> backtrack(Set<Set<Integer>> cls, Set<Integer> vars, List<Integer> result, Map<Integer, Set<Set<Integer>>> backTrack, List<Integer> presult, Set<Integer> posvars) {
		if(result.isEmpty()) {
			return cls;
		}
		else {
			Integer v = result.get(result.size() - 1);
			if(v > 0) {
				cls = backTrack.get(v);
				backTrack.remove(v);
				result.remove(v);
				result.add(-v);
				vars.remove(-v);
		
			}
			else {
				backTrack.remove(v);
				result.remove(v);
				vars.add(v);
				vars.add(-v);
				cls = backtrack(cls, vars, result, backTrack, presult, posvars);
			}
			Set<Set<Integer>> tmp = new HashSet(cls);
			presult.removeIf(i -> {
				if ( tmp.stream().anyMatch(s -> s.contains(i))) {
					vars.add(i);
					vars.add(-i);
					posvars.add(Math.abs(i));
					return true;
				}
				else return false;
			});
			return cls;
		}
	}
	
	private Set<Set<Integer>> unit(Set<Set<Integer>> cls, Set<Integer> vars, List<Integer> result) {
		Integer v = result.get(result.size() - 1);
		cls = cls.stream().filter(s -> !s.contains(v)).collect(Collectors.toCollection(HashSet::new));
		//cls.removeIf(s -> s.contains(v));
		//cls.removeIf(s -> s.contains(-v) && s.size() == 1);
		cls = cls.stream().map(s -> {
			Set<Integer> newSet = new HashSet<Integer> (s);
			if(s.contains(-v)) { newSet.remove(-v); return newSet; }
			else return newSet;
		}).collect(Collectors.toCollection(HashSet::new));
		return cls;
		//vars.remove(v);
	}
	
	private boolean hasFalse(Set<Set<Integer>> cls) {
		return cls.stream().anyMatch(s -> s.isEmpty());
	}
	
	private boolean hasVars(Set<Set<Integer>> cls, List<Integer> result) {
		try {
			return cls.stream().anyMatch(s -> s.stream().anyMatch(i -> result.contains(i) || result.contains(-i)));
		}
		catch (Exception e) {
			System.out.println(cls);
			System.out.println(result);
			return false;
		}
	}
	
	private Map<Integer, Boolean> makeListToMap (List<Integer> s) {
		Map<Integer, Boolean> result = new HashMap<Integer, Boolean> ();
		for(Integer i : s) {
			if(i > 0) {
				result.put(i, true);
			}
			else {
				result.put(-i, false);
			}
		}
		return result;
	}
	
	private boolean checkEND(Set<Set<Integer>> cls, Set<Integer> vars, List<Integer> result, List<Integer> splitresult) {
		if(cls.isEmpty()) {
			if(vars.size() == splitresult.size()) {
				Set<Integer> cvars = new HashSet<Integer> (vars);
				cvars.removeIf(i -> splitresult.contains(i) || splitresult.contains(-i));
				if(cvars.isEmpty())
					return true;
				else
					return false;
			}
			else return false;
		}
		else return false;
	}
	
	private Set<Integer> negationSet(Set<Integer> vars) {
		return vars.stream().map(i -> -i).collect(Collectors.toCollection(HashSet::new));
	}
	
	private Set<Set<Integer>> pure(Set<Set<Integer>> cls, List<Integer> r, Set<Integer> vars, Set<Integer> posvars) {
		
		Set<Integer> pureSet = new HashSet<Integer> ();
		for(Integer v : vars) {
			boolean pure = true;
			if(cls.stream().anyMatch(s -> s.contains(-v)))
				pure = false;
			if(pure && cls.stream().anyMatch(s -> s.contains(v)))
				pureSet.add(v);
		}
		
		
		if(pureSet.isEmpty()) return cls;
		cls.stream().forEach(s -> {
			if(s.stream().anyMatch(i -> pureSet.contains(i)))
				s.stream().forEach(i -> {
					if(!pureSet.contains(i))
						r.add(Math.abs(i));
				});
		});
		cls.removeIf(s -> s.stream().anyMatch(i -> pureSet.contains(i)));
		vars.removeIf(i -> pureSet.contains(i) || pureSet.contains(-i));
		posvars.removeIf(i -> pureSet.contains(i) || pureSet.contains(-i));
		r.addAll(pureSet);
		cls = pure(cls, r, vars, posvars);
		return cls;
	}
	
	
}
