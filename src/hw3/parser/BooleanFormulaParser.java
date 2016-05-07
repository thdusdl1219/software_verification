package hw3.parser;
// Generated from BooleanFormula.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BooleanFormulaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NOT=1, AND=2, OR=3, IMP=4, TRUE=5, FALSE=6, LPAREN=7, RPAREN=8, ID=9, 
		WS=10;
	public static final int
		RULE_formula = 0;
	public static final String[] ruleNames = {
		"formula"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'!'", "'&&'", "'||'", "'=>'", "'true'", "'false'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "NOT", "AND", "OR", "IMP", "TRUE", "FALSE", "LPAREN", "RPAREN", 
		"ID", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "BooleanFormula.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BooleanFormulaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FormulaContext extends ParserRuleContext {
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
	 
		public FormulaContext() { }
		public void copyFrom(FormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParenFormulaContext extends FormulaContext {
		public FormulaContext sub;
		public TerminalNode LPAREN() { return getToken(BooleanFormulaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(BooleanFormulaParser.RPAREN, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public ParenFormulaContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BooleanFormulaVisitor ) return ((BooleanFormulaVisitor<? extends T>)visitor).visitParenFormula(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstantContext extends FormulaContext {
		public Token value;
		public TerminalNode TRUE() { return getToken(BooleanFormulaParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(BooleanFormulaParser.FALSE, 0); }
		public ConstantContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BooleanFormulaVisitor ) return ((BooleanFormulaVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableContext extends FormulaContext {
		public Token name;
		public TerminalNode ID() { return getToken(BooleanFormulaParser.ID, 0); }
		public VariableContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BooleanFormulaVisitor ) return ((BooleanFormulaVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryFormulaContext extends FormulaContext {
		public FormulaContext left;
		public Token op;
		public FormulaContext right;
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode AND() { return getToken(BooleanFormulaParser.AND, 0); }
		public TerminalNode OR() { return getToken(BooleanFormulaParser.OR, 0); }
		public TerminalNode IMP() { return getToken(BooleanFormulaParser.IMP, 0); }
		public BinaryFormulaContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BooleanFormulaVisitor ) return ((BooleanFormulaVisitor<? extends T>)visitor).visitBinaryFormula(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryFormulaContext extends FormulaContext {
		public Token op;
		public FormulaContext sub;
		public TerminalNode NOT() { return getToken(BooleanFormulaParser.NOT, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public UnaryFormulaContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BooleanFormulaVisitor ) return ((BooleanFormulaVisitor<? extends T>)visitor).visitUnaryFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		return formula(0);
	}

	private FormulaContext formula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FormulaContext _localctx = new FormulaContext(_ctx, _parentState);
		FormulaContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_formula, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(11);
			switch (_input.LA(1)) {
			case NOT:
				{
				_localctx = new UnaryFormulaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(3);
				((UnaryFormulaContext)_localctx).op = match(NOT);
				setState(4);
				((UnaryFormulaContext)_localctx).sub = formula(7);
				}
				break;
			case LPAREN:
				{
				_localctx = new ParenFormulaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(5);
				match(LPAREN);
				setState(6);
				((ParenFormulaContext)_localctx).sub = formula(0);
				setState(7);
				match(RPAREN);
				}
				break;
			case ID:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(9);
				((VariableContext)_localctx).name = match(ID);
				}
				break;
			case TRUE:
			case FALSE:
				{
				_localctx = new ConstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(10);
				((ConstantContext)_localctx).value = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==TRUE || _la==FALSE) ) {
					((ConstantContext)_localctx).value = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(24);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(22);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryFormulaContext(new FormulaContext(_parentctx, _parentState));
						((BinaryFormulaContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(13);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(14);
						((BinaryFormulaContext)_localctx).op = match(AND);
						setState(15);
						((BinaryFormulaContext)_localctx).right = formula(7);
						}
						break;
					case 2:
						{
						_localctx = new BinaryFormulaContext(new FormulaContext(_parentctx, _parentState));
						((BinaryFormulaContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(16);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(17);
						((BinaryFormulaContext)_localctx).op = match(OR);
						setState(18);
						((BinaryFormulaContext)_localctx).right = formula(6);
						}
						break;
					case 3:
						{
						_localctx = new BinaryFormulaContext(new FormulaContext(_parentctx, _parentState));
						((BinaryFormulaContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(19);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(20);
						((BinaryFormulaContext)_localctx).op = match(IMP);
						setState(21);
						((BinaryFormulaContext)_localctx).right = formula(5);
						}
						break;
					}
					} 
				}
				setState(26);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return formula_sempred((FormulaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean formula_sempred(FormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\f\36\4\2\t\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\16\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\7\2\31\n\2\f\2\16\2\34\13\2\3\2\2\3\2\3\2\2\3\3\2\7\b\"\2\r"+
		"\3\2\2\2\4\5\b\2\1\2\5\6\7\3\2\2\6\16\5\2\2\t\7\b\7\t\2\2\b\t\5\2\2\2"+
		"\t\n\7\n\2\2\n\16\3\2\2\2\13\16\7\13\2\2\f\16\t\2\2\2\r\4\3\2\2\2\r\7"+
		"\3\2\2\2\r\13\3\2\2\2\r\f\3\2\2\2\16\32\3\2\2\2\17\20\f\b\2\2\20\21\7"+
		"\4\2\2\21\31\5\2\2\t\22\23\f\7\2\2\23\24\7\5\2\2\24\31\5\2\2\b\25\26\f"+
		"\6\2\2\26\27\7\6\2\2\27\31\5\2\2\7\30\17\3\2\2\2\30\22\3\2\2\2\30\25\3"+
		"\2\2\2\31\34\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2\33\3\3\2\2\2\34\32\3"+
		"\2\2\2\5\r\30\32";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}