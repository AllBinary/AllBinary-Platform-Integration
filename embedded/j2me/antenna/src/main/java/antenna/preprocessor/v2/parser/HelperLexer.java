// $ANTLR : "HELPER.g" -> "HelperLexer.java"$

/**
 * Automatically generated code, do not edit!
 * To modify, make changes to HELPER.g (ANTLR file).
 */
package antenna.preprocessor.v2.parser;

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

public class HelperLexer extends antlr.CharScanner implements HelperLexerTokenTypes, TokenStream
 {
public HelperLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public HelperLexer(Reader in) {
	this(new CharBuffer(in));
}
public HelperLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public HelperLexer(LexerSharedInputState state) {
	super(state);
	this.caseSensitiveLiterals = true;
	this.setCaseSensitive(true);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("include", this), new Integer(14));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		this.resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				if ((this.LA(1)=='/') && (this.LA(2)=='/') && (this.LA(3)=='\t'||this.LA(3)==' '||this.LA(3)=='#')) {
					this.mPREFIX(true);
					theRetToken=this._returnToken;
				}
				else if ((this.LA(1)=='\t'||this.LA(1)==' ')) {
					this.mWS(true);
					theRetToken=this._returnToken;
				}
				else if ((HelperLexer._tokenSet_0.member(this.LA(1))) && (true) && (true)) {
					this.mFILE(true);
					theRetToken=this._returnToken;
				}
				else {
					if (this.LA(1)==CharScanner.EOF_CHAR) {this.uponEOF(); this._returnToken = this.makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)this.LA(1), this.getFilename(), this.getLine(), this.getColumn());}
				}
				
				if ( this._returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = this._returnToken.getType();
				_ttype = this.testLiteralsTable(_ttype);
				this._returnToken.setType(_ttype);
				return this._returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.WS;
		int _saveIndex;
		
		{
		switch ( this.LA(1)) {
		case ' ':
		{
			match(' ');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
		}
		}
		}
		_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mFSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = FSLASH;
		int _saveIndex;
		
		match('/');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mBSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = BSLASH;
		int _saveIndex;
		
		match('\\');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mASLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.ASLASH;
		int _saveIndex;
		
		switch ( this.LA(1)) {
		case '/':
		{
			this.mFSLASH(false);
			break;
		}
		case '\\':
		{
			this.mBSLASH(false);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.COLON;
		int _saveIndex;
		
		match(':');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mDIGIT_0(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.DIGIT_0;
		int _saveIndex;
		
		this.matchRange('0','9');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mDIGIT_1(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.DIGIT_1;
		int _saveIndex;
		
		this.matchRange('1','9');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mCHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.CHAR;
		int _saveIndex;
		
		{
		switch ( this.LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			this.matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			this.matchRange('A','Z');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mPREFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.PREFIX;
		int _saveIndex;
		
		this.match("//");
		{
		_loop13:
		do {
			if ((this.LA(1)=='\t'||this.LA(1)==' ')) {
				this.mWS(false);
			}
			else {
				break _loop13;
			}
			
		} while (true);
		}
		this.match("#");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mFILE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = HelperLexerTokenTypes.FILE;
		int _saveIndex;
		
		{
		int _cnt16=0;
		_loop16:
		do {
			switch ( this.LA(1)) {
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':  case 'a':  case 'b':
			case 'c':  case 'd':  case 'e':  case 'f':
			case 'g':  case 'h':  case 'i':  case 'j':
			case 'k':  case 'l':  case 'm':  case 'n':
			case 'o':  case 'p':  case 'q':  case 'r':
			case 's':  case 't':  case 'u':  case 'v':
			case 'w':  case 'x':  case 'y':  case 'z':
			{
				this.mCHAR(false);
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				this.mDIGIT_0(false);
				break;
			}
			case '_':
			{
				this.match("_");
				break;
			}
			case '-':
			{
				match('-');
				break;
			}
			case '+':
			{
				match('+');
				break;
			}
			case '.':
			{
				match('.');
				break;
			}
			case '\\':
			{
				this.mBSLASH(false);
				break;
			}
			case '/':
			{
				this.mFSLASH(false);
				break;
			}
			case ':':
			{
				this.mCOLON(false);
				break;
			}
			default:
			{
				if ( _cnt16>=1 ) { break _loop16; } else {throw new NoViableAltForCharException((char)this.LA(1), this.getFilename(), this.getLine(), this.getColumn());}
			}
			}
			_cnt16++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = this.makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 576434364024356864L, 576460746263625726L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(HelperLexer.mk_tokenSet_0());
	
	}
