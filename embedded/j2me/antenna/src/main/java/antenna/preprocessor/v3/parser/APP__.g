lexer grammar APP;
@members {

/**
 * list of Exceptions reported by APPLexer
 */
List exceptions = new ArrayList();

/**
 * Return the list of Exceptions reported by APPLexer
 * 
 * @return list of Exceptions
 */
public List getExceptions() {
    return exceptions;
}

/*
 * (non-Javadoc)
 * 
 * @see org.antlr.runtime.Lexer#reportError(org.antlr.runtime.RecognitionException)
 */
public void reportError(RecognitionException e) {
    super.reportError(e);
    exceptions.add(e);
}
}
@header {
/**
 * Automatically generated code, do not edit!
 * To modify, make changes to APP.g (ANTLR file).
 */
package antenna.preprocessor.v3.parser;
}

DOT : '.' ;
COMMA : ',' ;
SEMI : ';' ;
PERCENT : '%' ;
LPAR : '(' ;
RPAR : ')' ;
BLPAR : '[' ;
BRPAR : ']' ;
NOT : '!' ;
XOR : '^' ;
AT : '@' ;
NEQ : '!=' ;
LT : '<' ;
GT : '>' ;
LTE : '<=' ;
GTE : '>=' ;
DEBUG : 'debug' ;
INFO : 'info' ;
WARN : 'warn' ;
ERROR : 'error' ;
FATAL : 'fatal' ;
DEFINE : 'define' ;
UNDEFINE : 'undefine' ;
IF : 'if' ;
ELIF : 'elif' ;
CONDITION : 'condition' ;
IFDEF : 'ifdef' ;
IFNDEF : 'ifndef' ;
ELIFDEF : 'elifdef' ;
ELIFNDEF : 'elifndef' ;
ENDIF : 'endif' ;
ELSE : 'else' ;
ENDINCLUDE : 'endinclude' ;
INCLUDE : 'include' ;
EXPAND : 'expand' ;
MDEBUG : 'mdebug' ;
ENDDEBUG : 'enddebug' ;
UNSET : 'unset' ;
ADD_IF_NEW : 'add_if_new' ;
TRUE : 'true' ;
FALSE : 'false' ;
T60 : '//' ;
T61 : '#' ;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 130
WS : (' '|'\t')+ {skip();}  ;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 132
fragment FSLASH: '/';
// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 133
fragment BSLASH: '\\';
// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 134
ASLASH : FSLASH | BSLASH;
// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 135
fragment COLON: ':';
// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 136
fragment DIGIT_0 : '0'..'9';   
// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 137
fragment DIGIT_1 : '1'..'9'; 
// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 138
fragment CHAR : ('a'..'z' | 'A'..'Z');

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 140
EOL : ('\n' | '\r' | '\r\n');

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 142
SYMBOL 
  : 
  CHAR (CHAR | DIGIT_0 | '_' | '-' | '+' | '.' | BSLASH | FSLASH | SEMI | COLON)* ;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 146
NUMBER : ('+'|'-')? (DIGIT_0)+ (('.')(DIGIT_0)+)?;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 148
STRING : '"'! (~'"')* '"'! | '\''! (~'\'')* '\''!;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 150
AND : '&' ('&')?;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 152
OR  : '|' ('|')?;

// $ANTLR src "src/antenna/preprocessor/v3/parser/APP.g" 154
EQ  : '=' ('=')?;

