package hw3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import hw3.formula.Conjunction;
import hw3.formula.Constant;
import hw3.formula.Disjunction;
import hw3.formula.Formula;
import hw3.formula.FormulaGenerator;
import hw3.formula.FormulaVisitor;
import hw3.formula.Implication;
import hw3.formula.Negation;
import hw3.formula.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith (JUnitQuickcheck.class)
public class Problem1Test {

	@Property(trials = 500)
	public void testParser(@From(FormulaGenerator.class) Formula f) {
		Formula g = Formula.parseFormula(f.toString());
		assertEquals(f.toString(), g.toString());
	}
	
	@Property(trials = 500)
	public void testMaxVar(@From(FormulaGenerator.class) Formula f) {
		final int mv = Problem1.maxVar(f);
		assertTrue(f.operation(new FormulaVisitor<Boolean>() {
			@Override public Boolean visit(Negation f) { return f.getSubformula().operation(this); }
			@Override public Boolean visit(Conjunction f) { return f.getSubformulas().stream().allMatch(g->g.operation(this)); }
			@Override public Boolean visit(Disjunction f) { return f.getSubformulas().stream().allMatch(g->g.operation(this)); }
			@Override public Boolean visit(Implication f) { return f.getAntecedent().operation(this) && f.getConsequence().operation(this); }
			@Override public Boolean visit(Variable f) { return f.getName() <= mv; }
			@Override public Boolean visit(Constant f) { return true; }
		}));
	}
	
	@Property(trials = 500)
	public void testNNF(@From(FormulaGenerator.class) Formula f) {
		Formula nf = Problem1.NNF(f);
		assertTrue(nf.operation(new FormulaVisitor<Boolean>() {
			@Override public Boolean visit(Negation f) { return f.getSubformula() instanceof Variable; }
			@Override public Boolean visit(Conjunction f) { return f.getSubformulas().stream().allMatch(g->g.operation(this)); }
			@Override public Boolean visit(Disjunction f) { return f.getSubformulas().stream().allMatch(g->g.operation(this)); }
			@Override public Boolean visit(Implication f) { return false; }
			@Override public Boolean visit(Variable f) { return true; }
			@Override public Boolean visit(Constant f) { return true; }
		}));
	}
	
	@Property(trials = 500)
	public void testSimplify(@From(FormulaGenerator.class) Formula f) {
		Formula sf = Problem1.simplify(f);
		assertTrue(sf instanceof Constant 
				|| sf.operation(new FormulaVisitor<Boolean>() {
					@Override public Boolean visit(Negation f) { return f.getSubformula().operation(this); }
					@Override public Boolean visit(Conjunction f) { return f.getSubformulas().stream().allMatch(g->g.operation(this)); }
					@Override public Boolean visit(Disjunction f) { return f.getSubformulas().stream().allMatch(g->g.operation(this)); }
					@Override public Boolean visit(Implication f) { return f.getAntecedent().operation(this) && f.getConsequence().operation(this); }
					@Override public Boolean visit(Variable f) { return true; }
					@Override public Boolean visit(Constant f) { return false; }
				}));
	}
	
	@Property(trials = 500)
	public void testfsameNNF(@From(FormulaGenerator.class) Formula f) {
		Formula nff = Problem1.NNF(f);
		assertEquals(Problem1.vars(nff), Problem1.vars(f));
	}

	@Property(trials = 200)
	public void testfValueSameNNF(@From(FormulaGenerator.class) Formula f) {
		long systemTime = System.currentTimeMillis();
		Formula nff = Problem1.NNF(f);
		Formula simplef = Problem1.simplify(f);
		Formula simplenff = Problem1.simplify(nff);
		Set<Integer> Vars = Problem1.vars(simplef);
		Map<Integer, Integer> VarsMap = new HashMap<Integer, Integer> ();
		Map<Integer, Boolean> BoolMap = new HashMap<Integer, Boolean> ();
		System.out.println(f.toString());
		int i = 0;
		for(Integer in : Vars) {
			VarsMap.put(i, in);
			BoolMap.put(in, true);
			i++;
		}
		
		System.out.println("Vars.size : " + Vars.size());

		int size = Vars.size();
		check(size, BoolMap, VarsMap, simplef, simplenff);
		
		systemTime = System.currentTimeMillis() - systemTime;
		System.out.println("PrintTime : " + systemTime);
		
	}

	
	public void check(int n, Map<Integer, Boolean> m, Map<Integer, Integer> m2, Formula f, Formula nff) {
		if(n == 0)
			return;
		else {
			int reali = m2.get(n-1);
			assertEquals(Problem1.eval(f, m), Problem1.eval(nff, m));
			check(n-1, m, m2, f, nff);
 			Map<Integer, Boolean> newmap = new HashMap<Integer, Boolean> (m);
			newmap.put(reali, false);
			assertEquals(Problem1.eval(f, newmap), Problem1.eval(nff, newmap));
			check(n-1, newmap, m2, f, nff);
		}
		
	}
}
