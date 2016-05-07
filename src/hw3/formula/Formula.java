package hw3.formula;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import hw3.parser.BooleanFormulaBaseVisitor;
import hw3.parser.BooleanFormulaLexer;
import hw3.parser.BooleanFormulaParser;

/**
 * The Formula class represents Boolean formulas.
 *
 */
public abstract class Formula {
	
	/**
	 * Return the string to represent the formula
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Perform an operator given an implementation of FormulaVisitor 
	 * 
	 * @param visitor
	 * @return
	 */
	public abstract <T> T operation(FormulaVisitor<T> visitor);
	
	
	/**
	 * Parse a string and create its formula data structure. The syntax is as follows:
	 * 
	 * formula ::=  true  |  false  |  variable  | ! formula  |  ( formula )
	 *           |  formula && formula  |  formula || formula  |  formula => formula
	 *  
	 * where variable has the form pN with positive number N, e.g., p1, p2, p3, ...
	 * 
	 * @param s string to be parsed
	 * @return Formula instance
	 * @throws IllegalStateException if the string cannot be parsed as Formula
	 */
	public static Formula parseFormula(String s) throws IllegalStateException {
		BooleanFormulaLexer lex = new BooleanFormulaLexer(new ANTLRInputStream(s));
		BooleanFormulaParser par = new BooleanFormulaParser(new CommonTokenStream(lex));

		par.addErrorListener(new BaseErrorListener() {
			@Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("Failed to parse at line " + line + " (" + msg + ")", e);
            }
		});
		
		return new BooleanFormulaBaseVisitor<Formula>() {
			@Override public Formula visitParenFormula(BooleanFormulaParser.ParenFormulaContext ctx) { 
				return visit(ctx.sub); 			
			}
			
			@Override public Formula visitUnaryFormula(BooleanFormulaParser.UnaryFormulaContext ctx) {
				return new Negation(visit(ctx.sub)); 
			}
			
			@Override public Formula visitBinaryFormula(BooleanFormulaParser.BinaryFormulaContext ctx) {
				switch (ctx.op.getType()) {
					case BooleanFormulaLexer.AND:
						return new Conjunction(visit(ctx.left),visit(ctx.right));
					case BooleanFormulaLexer.OR:
						return new Disjunction(visit(ctx.left),visit(ctx.right));
					case BooleanFormulaLexer.IMP:
						return new Implication(visit(ctx.left),visit(ctx.right));
					default:
						throw new UnsupportedOperationException();
				}
			}
			
			@Override public Formula visitConstant(BooleanFormulaParser.ConstantContext ctx) {
				return new Constant(Boolean.parseBoolean(ctx.value.getText()));
			}
			
			@Override public Formula visitVariable(BooleanFormulaParser.VariableContext ctx) {
				return new Variable(Integer.parseUnsignedInt(ctx.name.getText().substring(1)));
			}
		}.visit(par.formula());
	}
}
