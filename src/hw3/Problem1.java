package hw3;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hw3.formula.Conjunction;
import hw3.formula.Constant;
import hw3.formula.Disjunction;
import hw3.formula.Formula;
import hw3.formula.FormulaVisitor;
import hw3.formula.Implication;
import hw3.formula.Negation;
import hw3.formula.Variable;

public class Problem1 {

	/**
	 * Compute the truth value of formula, given valuation
	 * 
	 * eval(! f, val) = ! eval(f, val)
	 * eval(f && g, val) = eval(f, val) && eval(g, val)
	 * eval(f || g, val) = eval(f, val) || eval(g, val)
	 * eval(f => g, val) = ! eval(f, val) || eval(g, val)
	 * eval(c, val) = c
	 * eval(v, val) = val[v]
	 * 
	 * @param f
	 * @param valuation
	 * @return
	 */
	public static boolean eval(Formula f, Map<Integer,Boolean> valuation) {
		FormulaVisitor<Boolean> evaluator = new FormulaVisitor<Boolean>() {
			@Override public Boolean visit (Negation f) {  return ! f.getSubformula().operation(this);  }
			@Override public Boolean visit (Conjunction f) {  return f.getSubformulas().stream().reduce(true, (r,g)-> r&&g.operation(this), (a,b)->a&&b);  }
			@Override public Boolean visit (Disjunction f) {  return f.getSubformulas().stream().reduce(false, (r,g)-> r||g.operation(this), (a,b)->a||b);  }
			@Override public Boolean visit (Implication f) {  return ! f.getAntecedent().operation(this) || f.getConsequence().operation(this);  }
			@Override public Boolean visit (Constant f) { return f.getValue(); }
			@Override public Boolean visit (Variable f) {
				if (valuation.containsKey(f.getName()))
					return valuation.get(f.getName()).booleanValue();
				else
					throw new IllegalArgumentException();
			}
		};
		return f.operation(evaluator);
	}

	/**
	 * Compute the set of variables in formula
	 * 
	 * vars(! f) = vars(f);
	 * vars(f && g) = vars(f) \cup vars(g)
	 * vars(f || g) = vars(f) \cup vars(g)
	 * vars(f => g) = vars(f) \cup vars(g)
	 * vars(c) = \emptyset
	 * vars(v) = v
	 * 
	 * @param f formula
	 * @return the set of Boolean variables in formula
	 */
	public static Set<Integer> vars(Formula f) {
		Set<Integer> vars = new HashSet<>();
		f.operation(new FormulaVisitor<Void>() {
			@Override public Void visit(Negation f) { f.getSubformula().operation(this); return null; }
			@Override public Void visit(Conjunction f) { f.getSubformulas().forEach(g->g.operation(this)); return null; }
			@Override public Void visit(Disjunction f) { f.getSubformulas().forEach(g->g.operation(this)); return null; }
			@Override public Void visit(Implication f) { f.getAntecedent().operation(this); f.getConsequence().operation(this); return null; }
			@Override public Void visit(Constant f) { return null; }
			@Override public Void visit(Variable f) { vars.add(f.getName()); return null; }
		});
		return vars;
	}
	
	
	/**
	 * Return the maximal variable id in formula f
	 * 
	 * maxVar(! f) = maxVar(f);
	 * maxVar(f && g) = max(maxVar(f),maxVar(g))
	 * maxVar(f || g) = max(maxVar(f),maxVar(g))
	 * maxVar(f => g) = max(maxVar(f),maxVar(g))
	 * maxVar(v) = v
	 * maxVar(c) = 9=0
	 * 
	 * @param f
	 * @return
	 */
	public static int maxVar(Formula f) {
		return f.operation(new FormulaVisitor<Integer>() {
			@Override public Integer visit(Negation f) { return f.getSubformula().operation(this); }
			@Override public Integer visit(Conjunction f) { return f.getSubformulas().stream().map(g->g.operation(this)).max(Integer::compare).get(); }
			@Override public Integer visit(Disjunction f) { return f.getSubformulas().stream().map(g->g.operation(this)).max(Integer::compare).get(); }
			@Override public Integer visit(Implication f) { return Math.max(f.getAntecedent().operation(this),f.getConsequence().operation(this)); }
			@Override public Integer visit(Variable f) { return f.getName(); }
			@Override public Integer visit(Constant f) { return 0; }
		});
	}
	
	/**
	 * Convert a formula into its negation normal form
	 * 
	 * @param f a Boolean formula 
	 * @return an equivalent formula in NNF
	 */
	public static Formula NNF(Formula f) {
		PosNNF pNNF = new PosNNF();
		NegNNF nNNF = new NegNNF();
		pNNF.neg = nNNF;  
		nNNF.pos = pNNF;
		return f.operation(pNNF);
	}
	
	/**
	 * A visitor class for NNF transformation for positive formula
	 * 
	 * posNNF(f && g) = posNNF(f) && posNNF(g)
	 * posNNF(f || g) = posNNF(f) || posNNF(g)
	 * posNNF(f => g) = negNNF(f) || posNNF(g)
	 * posNNF(! f)    = negNNF(f)
	 * posNNF(v)      = v
	 * posNNF(c)      = c
	 */
	static class PosNNF implements FormulaVisitor<Formula> {
		public NegNNF neg;
		@Override public Formula visit(Conjunction f) { return new Conjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new)); }
		@Override public Formula visit(Disjunction f) { return new Disjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new)); }
		@Override public Formula visit(Implication f) { return new Disjunction(f.getAntecedent().operation(neg), f.getConsequence().operation(this)); }
		@Override public Formula visit(Negation f) { return f.getSubformula().operation(neg); }
		@Override public Formula visit(Variable f) { return f; }
		@Override public Formula visit(Constant f) { return f; }
	};
	
	/**
	 * A visitor class for NNF transformation for negative formula
	 * 
	 * negNNF(f && g) = negNNF(f) || negNNF(g)
	 * negNNF(f || g) = negNNF(f) && negNNF(g)
	 * negNNF(f => g) = posNNF(f) && negNNF(g)
	 * negNNF(! f)    = posNNF(f)
	 * negNNF(v)      = ! v
	 * negNNF(c)      = ! c
	 */
	static class NegNNF implements FormulaVisitor<Formula> {
		public PosNNF pos;
		@Override public Formula visit(Conjunction f) { return new Disjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new)); }
		@Override public Formula visit(Disjunction f) { return new Conjunction(f.getSubformulas().stream().map(g->g.operation(this)).toArray(Formula[]::new)); }
		@Override public Formula visit(Implication f) { return new Conjunction(f.getAntecedent().operation(pos), f.getConsequence().operation(this)); }
		@Override public Formula visit(Negation h) { return h.getSubformula().operation(pos); }
		@Override public Formula visit(Variable f) { return new Negation(f); }
		@Override public Formula visit(Constant f) { return new Constant(! f.getValue()); }
	}
	
	/**
	 * Simplify the formula by removing every constant (true and false) using the 
	 * identity laws, if possible. As a result, if a simplified formula contains
	 * true or false, then the entire simplified formula should be true or false.
	 * 
	 * @param f
	 * @return simplified formula
	 */
	public static Formula simplify(Formula f) {
		return f.operation(new FormulaVisitor<Formula>() {
			@Override
			public Formula visit(Negation f) {
				Formula s = f.getSubformula().operation(this);	// simplify the subformula
				if (s instanceof Constant)
					return new Constant(! ((Constant)s).getValue());	// if it is constant, return its negated constant 
				else
					return new Negation(s);	// return its negation
			}

			@Override
			public Formula visit(Conjunction f) {
				List<Formula> sfs = f.getSubformulas().stream().map(g->g.operation(this)).collect(Collectors.toList());	// simplify the subformulas
				
				// return false if it includes false (by the domination law)
				if (sfs.stream().anyMatch(g-> g instanceof Constant && ! ((Constant)g).getValue()))		
					return new Constant(false);
				
				// remove true by the identity law
				sfs.removeIf(g-> g instanceof Constant && ((Constant)g).getValue());	
				
				if (sfs.isEmpty())
					return new Constant(true);	// if empty, return true (by the identity law)
				else if (sfs.size() == 1)
					return sfs.get(0);	// if singleton, return its only element
				else
					return new Conjunction(sfs.toArray(new Formula[sfs.size()]));	// otherwise, return the conjunction of simplified subformula
			}

			@Override
			public Formula visit(Disjunction f) {
				List<Formula> sfs = f.getSubformulas().stream().map(g->g.operation(this)).collect(Collectors.toList());	// simplify the subformulas
				
				// return true if it includes true (by the domination law)
				if (sfs.stream().anyMatch(g-> g instanceof Constant && ((Constant)g).getValue())) 
					return new Constant(true);
				
				// remove false by the identity law
				sfs.removeIf(g-> g instanceof Constant && ! ((Constant)g).getValue());	
				
				if (sfs.isEmpty())
					return new Constant(false);	// if empty, return false (by the identity law)
				else if (sfs.size() == 1)
					return sfs.get(0);	// if singleton, return its only element
				else
					return new Disjunction(sfs.toArray(new Formula[sfs.size()]));	// otherwise, return the disjunction of simplified subformulas
			}

			@Override
			public Formula visit(Implication f) {
				Formula sa = f.getAntecedent().operation(this);		// simplify the antecedent
				Formula sc = f.getConsequence().operation(this);	// simplify the consequence
				if (sa instanceof Constant)
					return ((Constant)sa).getValue() ? sc : new Constant(true);		// when the antecedent is constant
				else if (sc instanceof Constant)
					return ((Constant)sc).getValue() ? new Constant(true) : new Negation(sa);	// when the antecedent is constant
				else
					return new Implication(sa,sc);	// otherwise
			}

			@Override public Formula visit(Variable f) { return f; }
			@Override public Formula visit(Constant f) { return f; }
		});
	}
	
}
