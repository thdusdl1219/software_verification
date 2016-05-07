package hw3.parser;
// Generated from BooleanFormula.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BooleanFormulaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BooleanFormulaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code parenFormula}
	 * labeled alternative in {@link BooleanFormulaParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenFormula(BooleanFormulaParser.ParenFormulaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constant}
	 * labeled alternative in {@link BooleanFormulaParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(BooleanFormulaParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link BooleanFormulaParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(BooleanFormulaParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryFormula}
	 * labeled alternative in {@link BooleanFormulaParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryFormula(BooleanFormulaParser.BinaryFormulaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryFormula}
	 * labeled alternative in {@link BooleanFormulaParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryFormula(BooleanFormulaParser.UnaryFormulaContext ctx);
}