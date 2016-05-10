package hw3;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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


	@Property(trials = 100)
	public void testCNF(@From(FormulaGenerator.class) Formula f) throws Exception {
		Set<Set<Integer>> s = Problem2.CNF(f);
		Problem3 p = new Problem3 ();
		Formula snf = Problem1.simplify(Problem1.NNF(f));
		Formula sf = setToFormula(s);
		Boolean b1 = p.unsat(f);
		Boolean b2 = p.unsat(sf);
		
		if(b1){
			System.out.println(snf.toString());
			System.out.println(sf.toString());
		}
		
		if(!b1) {
			assertTrue(!b2);
		}
		if(!b2) {
			assertTrue(!b1);
		}

	}
	
	public static boolean satisfiable(Formula f) throws Exception {
		Set<Integer> s = Problem1.vars(f);
		Map<Integer, Integer> VarsMap = new HashMap<Integer, Integer> ();
		Map<Integer, Boolean> BoolMap = new HashMap<Integer, Boolean> ();
		int i = 0;
		for(Integer in : s) {
			VarsMap.put(i, in);
			BoolMap.put(in, true);
			i++;
		}
		int size = s.size();
		if(s.isEmpty()){ 
			f = Problem1.simplify(Problem1.NNF(f));
			if(f instanceof Constant)
			{
				Constant c = (Constant) f;
				return c.getValue();
				
			}
			else throw new Exception();
		}
		else return check(size, BoolMap, VarsMap, f);
	}
	
	private boolean satisfiable(Set<Set<Integer>> f) throws Exception {
		Set<Integer> s = Problem2.vars(f);
		Map<Integer, Integer> VarsMap = new HashMap<Integer, Integer> ();
		Map<Integer, Boolean> BoolMap = new HashMap<Integer, Boolean> ();
		int i = 0;
		for(Integer in : s) {
			VarsMap.put(i, in);
			BoolMap.put(in, true);
			i++;
		}
		int size = s.size();
		if(s.isEmpty()){ 
			//f = Problem1.simplify(Problem1.NNF(f));
			if(f.isEmpty())
			{
				return true;
				
			}
			else return false;
		}
		else return check(size, BoolMap, VarsMap, f);
	}
	
	public static boolean check(int n, Map<Integer, Boolean> m, Map<Integer, Integer> m2, Formula f) {
		if(Problem1.eval(f, m))
			return true;
		else if(n == 0)
			return false;
		else {
			int reali = m2.get(n-1);
			boolean result = check(n-1, m, m2, f);
			if(result) return true;
			Map<Integer, Boolean> newmap = new HashMap<Integer, Boolean> (m);
			newmap.put(reali, false);
			return result || check(n-1, newmap, m2, f);
		}
		
	}
	
	public boolean check(int n, Map<Integer, Boolean> m, Map<Integer, Integer> m2, Set<Set<Integer>> f) {	
		if(Problem2.eval(f, m))
			return true;
		else if(n == 0)
			return false;
		else {
			int reali = m2.get(n-1);
			boolean result = check(n-1, m, m2, f);
			if(result) return true;
			Map<Integer, Boolean> newmap = new HashMap<Integer, Boolean> (m);
			newmap.put(reali, false);
			return result || check(n-1, newmap, m2, f);
		}
		
	}

	private Formula mapper (Set<Integer> s) {
		List<Formula> lf = new ArrayList<Formula> ();
		for(Integer i : s) {
			Formula f = new Variable(Math.abs(i));
			if(i > 0)
				lf.add(f);
			else
				lf.add(new Negation(f));
		}
		if(lf.size() == 0)
			return new Disjunction();
		else if(lf.size() == 1)
			return lf.get(0);
		else return makeDisjunction(lf);
	}
	
	private Formula makeDisjunction(List<Formula> lf) {
		Formula f = lf.get(0);	
		if(lf.size() > 2) {
			return new Disjunction(makeDisjunction(lf.subList(1, lf.size())), f);
		}
		else {
			return new Disjunction(lf.get(1), f);
		}
	}
	
	private Formula makeConjunction(List<Formula> lf) {
		Formula f = lf.get(0);
		if(lf.size() > 2) {
			return new Conjunction(makeConjunction(lf.subList(1, lf.size())), f);
		}
		else {
			return new Conjunction(lf.get(1), f);
		}
	}
	
	private Formula setToFormula(Set<Set<Integer>> s) {
		List<Formula> lf = new ArrayList<Formula> ();
		for(Set<Integer> si : s) {
			lf.add(mapper(si));
		}
		if(lf.size() == 0)
			return new Constant(true);
		else if(lf.size() == 1)
			if(s.iterator().next().isEmpty())
				return new Constant(false);
			else return lf.get(0);
		else 
		return makeConjunction(lf);
	}
}
