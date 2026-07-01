/* -----------------------------------------------------------------------------
 * Antenna - An Ant-to-end solution for wireless Java 
 *
 * Copyright (c) 2002-2004 Joerg Pleumann <joerg@pleumann.de>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * -----------------------------------------------------------------------------
 */
package antenna.preprocessor.v1;

import antenna.preprocessor.PreprocessorException;

/**
 * Implements a mini-scanner for boolean expressions. The
 * tokenizer detects variable names, parentheses and the usual
 * Java operators for boolean "and", "or", "exclusive or", and
 * "not" ("&&", "||", "^", and "!", respectively. It has a
 * special symbol for reporting the end of the input text.
 */
class BooleanTokenizer {

    /**
     * Token code for end-of-input.
     */
	public static final int TYPE_STOP = 0;
    
    /**
     * Token code for an identifier.
     */
	public static final int TYPE_ID = 1;
    
    /**
     * Token code for a left (opening) parenthesis.
     */
	public static final int TYPE_LPAR = 2;
    
    /**
     * Token code for a right (closing) parenthesis.
     */
	public static final int TYPE_RPAR = 3;
    
    /**
     * Token code for the boolean "and" operator "&&".
     */
	public static final int TYPE_AND = 4;

    /**
     * Token code for the boolean "or" operator "||".
     */
	public static final int TYPE_OR = 5;

    /**
     * Token code for the boolean "exclusive or" operator "^".
     */
	public static final int TYPE_XOR = 6;

    /**
     * Token code for the negation operator "!".
     */
	public static final int TYPE_NOT = 7;

	/**
	 * Holds the source expression to be scanned.
	 */
	private String sourceText;
    
    /**
     * Holds the current position inside the source, that is,
     * the next character to be consumed.
     */
	private int sourcePos;
    
    /**
     * Holds the type of the token most recently scanned.
     */
	private int tokenType;
    
    /**
     * Holds the position of the token most recently scanned.
     */
	private int tokenPos;

	/**
	 * Creates a new scanner for the given expression.
	 */
	public BooleanTokenizer(String expr) {
		this.sourceText = expr + " ";
		this.sourcePos = 0;
		this.tokenType = -1;
		this.tokenPos = 0;
	}

	/**
	 * Ignores whitespace and tab characters beginning at the
	 * current position.
	 */
	private void skipBlanks() {
		while (true) {
			if (this.sourcePos >= this.sourceText.length())
				break;
			char c = this.sourceText.charAt(this.sourcePos);
			if ((c != ' ') && (c != '\t'))
				break;
			this.sourcePos++;
		}
	}

	/**
	 * Skips the current token and scans the next one, returning
	 * the token code of the new token.
	 */
	public int nextToken() throws PreprocessorException {
		this.skipBlanks();
		this.tokenPos = this.sourcePos;

		/* Check for end of source */
		if (this.sourcePos >= this.sourceText.length()) {
			this.tokenType = BooleanTokenizer.TYPE_STOP;
			return this.tokenType;
		}

		/* Check for other kinds of tokens */
		char c = this.sourceText.charAt(this.sourcePos);
		if (Character.isJavaIdentifierStart(c)) {
			this.tokenType = BooleanTokenizer.TYPE_ID;
			this.sourcePos++;
			while (this.isIdentifierPart(this.sourceText.charAt(this.sourcePos))) {
				this.sourcePos++;
			}
		}
		else if (c == '(') {
			this.tokenType = BooleanTokenizer.TYPE_LPAR;
			this.sourcePos++;
		}
		else if (c == ')') {
			this.tokenType = BooleanTokenizer.TYPE_RPAR;
			this.sourcePos++;
		}
		else if (c == '&') {
			this.tokenType = BooleanTokenizer.TYPE_AND;
			this.sourcePos++;
			if (this.sourceText.charAt(this.sourcePos) == '&') {
				this.sourcePos++;
			}
		}
		else if (c == '|') {
			this.tokenType = BooleanTokenizer.TYPE_OR;
			this.sourcePos++;
			if (this.sourceText.charAt(this.sourcePos) == '|') {
				this.sourcePos++;
			}
		}
		else if (c == '^') {
			this.tokenType = BooleanTokenizer.TYPE_XOR;
			this.sourcePos++;
		}
		else if (c == '!') {
			this.tokenType = BooleanTokenizer.TYPE_NOT;
			this.sourcePos++;
		}
		else
			throw new PreprocessorException("Syntax Error");

		return this.tokenType;
	}

	private boolean isIdentifierPart(char c)
	{
		return Character.isJavaIdentifierPart(c) || c == '\\' || c == '/';
	}

	/**
	 * Returns the token code of the token most recently
	 * scanned.
	 */
	public int getTokenType() {
		return this.tokenType;
	}

	/**
	 * Returns the text of the token most recently scanned.
	 * Usually called to find out the name of an identifier.
	 */
	public String getTokenText() {
		if (this.tokenType == BooleanTokenizer.TYPE_STOP) {
			return "";
		}
		else {
			return this.sourceText.substring(this.tokenPos, this.sourcePos);
		}
	}
}
