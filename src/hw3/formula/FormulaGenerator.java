package hw3.formula;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * A Fomula generator JUnit quick check. It randomly generates Formula instances.
 *
 */
public class FormulaGenerator extends Generator<Formula> {

	public FormulaGenerator() {
		super(Formula.class);
	}

	@Override
	public Formula generate(SourceOfRandomness random, GenerationStatus status) {
		int size = status.size();
		return generate(random, size, size > 0 ? random.nextInt(size) : 0);
	}
	
	/**
	 * Randomly create a Formula instance of given size.
	 * 
	 * @param random
	 * @param size	the number of Boolean operators in formula
	 * @param vars	the number of "possible" Boolean variables
	 * @return a randomly generated formula
	 */
	private Formula generate(SourceOfRandomness random, int size, int vars) {
		if (size > 0) {
			int choice = random.nextInt(4);
			int subsize = choice > 0 ? size-1 : random.nextInt(size); 
			
			switch (choice) {
				case 0:	// negation
					return new Negation(generate(random, subsize, vars));
				case 1: // and
					return new Conjunction(generate(random,subsize,vars), generate(random, size-subsize-1, vars));
				case 2: // or 
					return new Disjunction(generate(random,subsize,vars), generate(random, size-subsize-1, vars));
				case 3: // imp
					return new Implication(generate(random,subsize,vars), generate(random, size-subsize-1, vars));
			}
		}
		else {
			// uniformly choose one from: true, false, p1, p2, \ldots, p_{vars+1}
			int choice = random.nextInt(vars + 3);	
			
			if (choice > vars)
				return new Constant(choice - vars > 0);	
			else
				return new Variable(choice+1);
		}
		
		return null;
	}
}
