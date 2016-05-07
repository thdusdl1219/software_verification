package hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import hw3.formula.Conjunction;
import hw3.formula.Constant;
import hw3.formula.Disjunction;
import hw3.formula.Formula;
import hw3.formula.FormulaVisitor;
import hw3.formula.Implication;
import hw3.formula.Negation;
import hw3.formula.Variable;

public class Problem2 {
	
	/**
	 * Compute the truth value of a set of clauses, given valuation
	 * 
	 * @param cls
	 * @param valuation
	 * @return
	 */
	public static boolean eval(Set<Set<Integer>> cls, Map<Integer,Boolean> valuation) {
		// for every clause cl in cls, for some literal l in cl, l evaluates to true by valuation. 
		return cls.stream().allMatch(cl->cl.stream().anyMatch(l->valuation.get(Math.abs(l))^(l<0)));
	}
	
	/**
	 * 
	 * @param cls
	 * @return the set of Boolean variables in the set of clauses cls
	 */
	public static Set<Integer> vars(Set<Set<Integer>> cls) {
		return cls.stream().flatMap(cl->cl.stream().map(i->Math.abs(i))).collect(Collectors.toCollection(HashSet::new));
	}
	
	
	private static Formula cnf (Variable n, Formula f) throws Exception {
		if(f instanceof Conjunction) {
			Conjunction cf = (Conjunction)f;
			Variable newv = n;
			Formula f1 = cf.getSubformulas().get(0);
			Formula f2 = cf.getSubformulas().get(1);
			Formula negv = new Negation(newv);
			return new Conjunction(new Disjunction(negv, f1), new Disjunction(negv, f2), new Disjunction(f1, f2, newv));  
		}
		else if(f instanceof Disjunction) {
			Disjunction df = (Disjunction)f;
			Variable newv = n;
			Formula f1 = df.getSubformulas().get(0);
			Formula f2 = df.getSubformulas().get(1);
			Formula negv = new Negation(newv);
			return new Conjunction(new Disjunction(negv, f1, f2), new Disjunction(new Negation(f1), newv), new Disjunction(new Negation(f2), newv));
		}
		else 
			throw new Exception("error");
	}
	
	
	private static Formula Tesjtin (Formula f) throws Exception {  
		if(f instanceof Negation) return f;  
		else if(f instanceof Conjunction) {
			Map<Formula, Variable> m = new HashMap<Formula, Variable>();
			findsubF(f, m);
			Formula g = m.keySet().iterator().next();
			return new Conjunction(Tesjtin(modify(f, m)), cnf(m.get(g), g));
		}
		
		else if(f instanceof Disjunction) {
			Map<Formula, Variable> m = new HashMap<Formula, Variable>();
			findsubF(f, m);
			Formula g = m.keySet().iterator().next();
			return new Conjunction(Tesjtin(modify(f, m)), cnf(m.get(g), g));
		}
		else return f;	
	}
	
	private static Formula modify(Formula f, Map<Formula, Variable> m) {
		
		FormulaVisitor<Formula> evaluator = new FormulaVisitor<Formula>() {
			@Override public Formula visit (Negation f) {  return f;  }
			@Override public Formula visit (Conjunction f) {
				if(m.containsKey(f))
					return m.get(f);
				else {
					return new Conjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new));
				}
			}
			@Override public Formula visit (Disjunction f) {  
				if(m.containsKey(f))
					return m.get(f);
				else {
					return new Disjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new));
				}  
			}
			@Override public Formula visit (Implication f) {  return f; }
			@Override public Formula visit (Constant f) { return f; }
			@Override public Formula visit (Variable f) { return f; }
		};
		return f.operation(evaluator);
	}
	
	private static void findsubF (Formula f, Map<Formula, Variable> m) {
		if(m.isEmpty()) {
			if(f instanceof Conjunction){ 
				Conjunction cf = (Conjunction)f;
				int varnum = 0;
				for(Formula subf : cf.getSubformulas()) {
					if(subf instanceof Variable) {
						varnum++;
					}
					else if(subf instanceof Negation) {
						varnum++;
					}
					if(varnum == 2)
						break;
				}
				if(varnum == 2) {
					Integer maxVar = Problem1.maxVar(f);
					m.put(f, new Variable(maxVar+1));
					return;
				}
				else {
					cf.getSubformulas().stream().forEach(g->findsubF(g, m));;
					return;
				}
				
			}
			else if(f instanceof Disjunction){
				Disjunction df = (Disjunction)f;
				int varnum = 0;
				for(Formula subf : df.getSubformulas()) {
					if(subf instanceof Variable) {
						varnum++;
					}
					else if(subf instanceof Negation) {
						varnum++;
					}
					if(varnum == 2)
						break;
				}
				if(varnum == 2) {
					Integer maxVar = Problem1.maxVar(f);
					m.put(f, new Variable(maxVar+1));
					return;
				}
				else {
					df.getSubformulas().stream().forEach(g->findsubF(g, m));;
					return;
				}
			}
			else return;
		}
		else {
			return;
		}
	}
	
	
	private static boolean checkCNF (Formula f) {
		FormulaVisitor<Boolean> checkor = new FormulaVisitor<Boolean>() {
			@Override public Boolean visit (Negation f) {  return true;  }
			@Override public Boolean visit (Conjunction f) {  return f.getSubformulas().stream().reduce(true, (r,g)-> r && g.operation(this), (a,b)->a&&b);  }
			@Override public Boolean visit (Disjunction f) {  return f.getSubformulas().stream().reduce(true, (r,g)-> r && g.operation(this), (a,b)->a&&b);  }
			@Override public Boolean visit (Implication f) {  return false; }
			@Override public Boolean visit (Constant f) { return true; }
			@Override public Boolean visit (Variable f) { return true; }
		};
		if(f instanceof Conjunction) {
			Conjunction cf = (Conjunction)f;
			return cf.getSubformulas().stream().reduce(true, (r,g) -> r && g.operation(checkor), (a,b) -> a && b);
		}
		else if (f instanceof Disjunction) return false;
		else if (f instanceof Implication) return false;
		else return true;
	}
	
	private static void formulatoSet (Formula f, Set<Set<Integer>> s) {
		FormulaVisitor<Set<Integer>> evaluator = new FormulaVisitor<Set<Integer>>() {
			@Override public Set<Integer> visit (Negation f) {
				Set<Integer> s = new HashSet<Integer> ();
				f.getSubformula()
				s.add();
				return ;  
			}
			@Override public Formula visit (Conjunction f) {
				
				f.getSubformulas().stream().forEach(g -> g.operation(this));
				
				return new Conjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new));
			}
			@Override public Formula visit (Disjunction f) {  
				if(m.containsKey(f))
					return m.get(f);
				else {
					return new Disjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new));
				}  
			}
			@Override public Formula visit (Implication f) {  return f; }
			@Override public Formula visit (Constant f) { return f; }
			@Override public Formula visit (Variable f) { return f; }
		};
		f.operation(evaluator);
		return;
	}
	/**
	 * Convert a formula into its conjunctive normal form. The resulting clause is given by
	 * a set of sets of literals.
	 * 
	 * @param f a Boolean formula 
	 * @return an equivalent formula in NNF
	 * @throws Exception 
	 */
	public static Set<Set<Integer>> CNF(Formula f) throws Exception {
		// TODO: translate f into its simplified NNF version snf using Problem1.NNF and Problem1.simplify
		// TODO: translate snf into  a set of clauses in CNF
		
		Formula snf = Problem1.simplify(Problem1.NNF(f));
		
		Formula checkf = snf;
		if(!checkCNF(checkf)) {
			checkf = Tesjtin(checkf);
			checkf = Problem1.simplify(Problem1.NNF(checkf));
		}
		
		if(!checkCNF(checkf)) throw new Exception();
		
		
		//return f.operation(evaluator);
		
		/*
		 * Each literal is expressed by either positive number or negative number. 
		 * 0 should not be used. Constant "true" is removed by using the identity rule, 
		 * and "false"  is denoted by the empty clause.
		 * 
		 * For example, formula 
		 * 
		 *   (p1 || ! p2) && (p3 || ! p4) && (p5 || false) && false && (true || p7) 
		 *   
		 * can be represented by the set: 
		 * 
		 *   {{1,-2},{3,-4},{5},{}}
		 * 
		 * Positive literals p1, p3, p5 are expressed by positive numbers 1, 3, 5. Negative literals 
		 * "! p2" and "! p4" are expressed by negative numbers -2 and -4. (p5 || false) is equivalent 
		 * to p5. If a clause is equivalent to false, then it is expressed by the empty clause {}. 
		 * (true || p7) is equivalent to true, which is removed since true is the identity of &&.
		 * 
		 * Notice that the formula "true" is expressed as the empty set {}, and the formula "false" is
		 * expressed as the singleton set {{}}.
		 */
		Set<Set<Integer>> s = new HashSet<Set<Integer>> (); 
		formulatoSet(checkf, s);
		return s;
	}
}
