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
public class Problem2Test {

	@Test
	public void testEval() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testVars() {
		fail("Not yet implemented"); // TODO
	}

	@Property(trials = 10)
	public void testCNF(@From(FormulaGenerator.class) Formula f) {
		Problem2.CNF(f);
	}

}
