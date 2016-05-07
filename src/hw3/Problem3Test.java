package hw3;

import static org.junit.Assert.*;

import org.junit.Test;

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

@RunWith (JUnitQuickcheck.class)
public class Problem3Test {
	
	@Property(trials = 5000)
	public void testUnsat(@From(FormulaGenerator.class) Formula f) throws Exception {
		Problem3 p = new Problem3 ();
		boolean b = p.unsat(f);
		if(b) {
			System.out.println(Problem1.simplify(Problem1.NNF(f)).toString());
			System.out.println(Problem2.CNF(f));
		}
	}
	
	/*@Test
	public void test() throws Exception {
		Problem3 p = new Problem3 ();
		Formula f = Formula.parseFormula("(((((! p2) && (! p3)) || (! p3)) || p2) || p2)");
		boolean b = p.unsat(f);
	} */

}
