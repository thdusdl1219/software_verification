package hw3;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

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

@RunWith (JUnitQuickcheck.class)
public class Problem4Test {

	@Property(trials = 50)
	public void testSat(@From(FormulaGenerator.class) Formula f) throws Exception {
		Problem4 p = new Problem4 ();
		Problem3 p3 = new Problem3 ();
		
		//Formula ff = Formula.parseFormula("((((((! p8) || p1) || p8) || p7) || p6) && (! p2))");
		Formula snf = Problem1.simplify(Problem1.NNF(f));
		System.out.println(snf.toString());
		Map<Integer, Boolean> m = p.sat(f);
		
		Boolean b = satisfied(m);
		if(!b) {
			Boolean b1 = Problem2Test.satisfiable(snf);
			assertTrue(!b1);
		}
		else {
			assertTrue(Problem1.eval(snf, m));
		}
		/*Boolean b2 = !p3.unsat(f);
		if(!b)
			System.out.println(snf.toString());
		if(b != b2)
			System.out.println(snf.toString());
		//System.out.println(b.toString() + " , " + b2.toString());
		assertEquals(b, b2) ; */
		
	}
	private boolean satisfied(Map<Integer, Boolean> m) {
		if(m == null)
			return false;
		else
			return true;
	}
}
