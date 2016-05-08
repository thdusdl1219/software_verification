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
	
	@Property(trials = 100)
	public void testUnsat(@From(FormulaGenerator.class) Formula f) throws Exception {
		Problem3 p = new Problem3 ();
		Boolean b = p.unsat(f);
		
		Formula snf = Problem1.simplify(Problem1.NNF(f));
		System.out.println(snf.toString());		
		
		Boolean b1 = Problem2Test.satisfiable(snf);
		System.out.println(b.toString() + "," + b1.toString());
		if(b) {
			assertTrue(!b1);
		}
		if(!b1) {
			assertTrue(b);
		}
	}
	
	/*@Test
	public void test() throws Exception {
		Problem3 p = new Problem3 ();
		Formula f = Formula.parseFormula("(((((((! p5) || (! p3)) || p4) && p5) || p5) && (! p5)) && (! p2))");
		boolean b = p.unsat(f);
		System.out.println(b);
		f = Formula.parseFormula("(((! p8) || p7) && ((! p2) && (((! p6) || p7) && ((p5 || (! p8)) && (((! p5) || ((! p7) || p8)) && ((p4 || ((! p7) || p6)) && (((! p4) || p7) && ((! p5) && ((p3 || p6) && ((p5 || p6) && ((p5 || p8) && ((! p3) || ((! p5) || (! p6))))))))))))))");
		b = p.unsat(f);
		System.out.println(b);
	} */

}
