grammar APP ;
     
// Enable AST construction 
options { k = 3; output = AST; }

tokens {

DOT= '.' ;
COMMA= ',';
SEMI= ';';
PERCENT= '%';

LPAR=   '(' ;
RPAR=   ')' ;
BLPAR=  '[' ;
BRPAR=  ']' ;

// boolean operands
NOT = '!';
XOR = '^';

AT  = '@';
NEQ = '!=';
LT  = '<';
GT  = '>';
LTE = '<=';
GTE = '>=';

/* Debug tokens */
DEBUG = 'debug';
INFO = 'info';
WARN = 'warn';
ERROR = 'error';
FATAL = 'fatal';

DEFINE = 'define'; 
UNDEFINE = 'undefine';
IF = 'if';
ELIF = 'elif';
CONDITION = 'condition';
IFDEF = 'ifdef';
IFNDEF = 'ifndef';
ELIFDEF = 'elifdef';
ELIFNDEF = 'elifndef';
ENDIF = 'endif';
ELSE = 'else';
ENDINCLUDE = 'endinclude';
INCLUDE = 'include';
EXPAND = 'expand';
MDEBUG = 'mdebug';
ENDDEBUG = 'enddebug';

UNSET = 'unset';
ADD_IF_NEW = 'add_if_new';

TRUE = 'true';
FALSE = 'false';

}

@header
{
/**
 * Automatically generated code, do not edit!
 * To modify, make changes to APP.g (ANTLR file).
 */
package antenna.preprocessor.v3.parser;
}

// Alter code generation so catch-clauses get replace with
// this action.
@rulecatch {
catch (RecognitionException e) {
throw e;
}
}

@members {
protected void mismatch(IntStream input, int ttype, BitSet follow)
throws RecognitionException
{
throw new MismatchedTokenException(ttype, input);
}
public void recoverFromMismatchedSet(IntStream input,
RecognitionException e,
BitSet follow)
throws RecognitionException
{
throw e;
}
}

@lexer::header {
/**
 * Automatically generated code, do not edit!
 * To modify, make changes to APP.g (ANTLR file).
 */
package antenna.preprocessor.v3.parser;
}

// Error information must be intercepted because automatic error recovery
// only prints a series of warning messages and don't throw Exceptions
@lexer::members {

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

WS : (' '|'\t')+ {skip();}  ;

fragment FSLASH: '/';
fragment BSLASH: '\\';
ASLASH : FSLASH | BSLASH;
fragment COLON: ':';
fragment DIGIT_0 : '0'..'9';   
fragment DIGIT_1 : '1'..'9'; 
fragment CHAR : ('a'..'z' | 'A'..'Z');

EOL : ('\n' | '\r' | '\r\n');

SYMBOL 
  : 
  CHAR (CHAR | DIGIT_0 | '_' | '-' | '+' | '.' | BSLASH | FSLASH | SEMI | COLON)* ;

NUMBER : ('+'|'-')? (DIGIT_0)+ (('.')(DIGIT_0)+)?;

STRING : '"'! (~'"')* '"'! | '\''! (~'\'')* '\''!;

AND : '&' ('&')?;

OR  : '|' ('|')?;

EQ  : '=' ('=')?;

prefix : '//'! '#';

r_boolean : TRUE | FALSE;
ident: SYMBOL | STRING | NUMBER | r_boolean; // identifier

bool: ident
      | LPAR! expression RPAR!
      ;
      
not_bool : (NOT^)* bool;

eq_bool :
    not_bool (( AT^ | EQ^ | NEQ^ | LT^ | GT^ | LTE^ | GTE^ ) not_bool)?;

and_bool : eq_bool (AND^ eq_bool)*;

xor_bool : and_bool (XOR^ and_bool)*;

expression : xor_bool (OR^ xor_bool)*;

anything : (~EOL)+;

debug_level : DEBUG | INFO | WARN | ERROR | FATAL;

line :
(
    (
        (prefix) => prefix 
            (
                (
                DEFINE define |
                UNDEFINE SYMBOL |
                (IF | ELIF | CONDITION) expression) | 
                (IFDEF | IFNDEF | ELIFDEF | ELIFNDEF) SYMBOL | 
                (ENDIF | ELSE | ENDINCLUDE)|
                (INCLUDE (~EOL)+) /* beware, include is handled using a special hack in the code */|
                (EXPAND (~EOL)+)  /* beware, define is handled using a special hack in the code  */ |
                ((DEBUG|MDEBUG) (debug_level)?) |
                ENDDEBUG 
            )
            //|
            //anything
        )       

    (EOL|EOF)!
);
endof   : EOL!;
define_command : UNSET | ADD_IF_NEW;
define_value: ident | debug_level;
define : (define_command AT)? SYMBOL^ (EQ! define_value)?;
defines : (EOL|EOF) | define (COMMA! define)* (EOL)?;
