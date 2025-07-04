// $ANTLR : "HELPER.g" -> "HelperParser.java"$

/**
 * Automatically generated code, do not edit!
 * To modify, make changes to HELPER.g (ANTLR file).
 */
package antenna.preprocessor.v2.parser;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class HelperParser extends antlr.LLkParser       implements HelperLexerTokenTypes
 {

protected HelperParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public HelperParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected HelperParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public HelperParser(TokenStream lexer) {
  this(lexer,1);
}

public HelperParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void line() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST line_AST = null;
		
		AST tmp1_AST = null;
		tmp1_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp1_AST);
		match(PREFIX);
		AST tmp2_AST = null;
		tmp2_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp2_AST);
		match(LITERAL_include);
		AST tmp3_AST = null;
		tmp3_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp3_AST);
		match(FILE);
		line_AST = (AST)currentAST.root;
		returnAST = line_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"WS",
		"FSLASH",
		"BSLASH",
		"ASLASH",
		"COLON",
		"DIGIT_0",
		"DIGIT_1",
		"CHAR",
		"PREFIX",
		"FILE",
		"\"include\""
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	
	}
