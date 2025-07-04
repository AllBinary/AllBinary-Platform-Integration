// $ANTLR 3.0.1 src/antenna/preprocessor/v3/parser/APP.g 2008-06-20 17:51:57

/**
 * Automatically generated code, do not edit!
 * To modify, make changes to APP.g (ANTLR file).
 */
package antenna.preprocessor.v3.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class APPParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DOT", "COMMA", "SEMI", "PERCENT", "LPAR", "RPAR", "BLPAR", "BRPAR", "NOT", "XOR", "AT", "NEQ", "LT", "GT", "LTE", "GTE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "DEFINE", "UNDEFINE", "IF", "ELIF", "CONDITION", "IFDEF", "IFNDEF", "ELIFDEF", "ELIFNDEF", "ENDIF", "ELSE", "ENDINCLUDE", "INCLUDE", "EXPAND", "MDEBUG", "ENDDEBUG", "UNSET", "ADD_IF_NEW", "TRUE", "FALSE", "WS", "FSLASH", "BSLASH", "ASLASH", "COLON", "DIGIT_0", "DIGIT_1", "CHAR", "EOL", "SYMBOL", "NUMBER", "STRING", "AND", "OR", "EQ", "'//'", "'#'"
    };
    public static final int COMMA=5;
    public static final int ELIFNDEF=33;
    public static final int PERCENT=7;
    public static final int BLPAR=10;
    public static final int NUMBER=55;
    public static final int LPAR=8;
    public static final int FALSE=44;
    public static final int FATAL=24;
    public static final int ENDINCLUDE=36;
    public static final int ERROR=23;
    public static final int IFNDEF=31;
    public static final int BRPAR=11;
    public static final int OR=58;
    public static final int IFDEF=30;
    public static final int DOT=4;
    public static final int AND=57;
    public static final int GTE=19;
    public static final int INCLUDE=37;
    public static final int ELIFDEF=32;
    public static final int CONDITION=29;
    public static final int SYMBOL=54;
    public static final int NEQ=15;
    public static final int UNDEFINE=26;
    public static final int AT=14;
    public static final int ADD_IF_NEW=42;
    public static final int ENDIF=34;
    public static final int RPAR=9;
    public static final int INFO=21;
    public static final int UNSET=41;
    public static final int WS=45;
    public static final int WARN=22;
    public static final int CHAR=52;
    public static final int EQ=59;
    public static final int STRING=56;
    public static final int MDEBUG=39;
    public static final int LT=16;
    public static final int GT=17;
    public static final int DIGIT_0=50;
    public static final int ELIF=28;
    public static final int SEMI=6;
    public static final int LTE=18;
    public static final int DEFINE=25;
    public static final int EXPAND=38;
    public static final int FSLASH=46;
    public static final int ELSE=35;
    public static final int BSLASH=47;
    public static final int IF=27;
    public static final int EOF=-1;
    public static final int EOL=53;
    public static final int XOR=13;
    public static final int ENDDEBUG=40;
    public static final int COLON=49;
    public static final int DEBUG=20;
    public static final int ASLASH=48;
    public static final int DIGIT_1=51;
    public static final int NOT=12;
    public static final int TRUE=43;

        public APPParser(TokenStream input) {
            super(input);
            ruleMemo = new HashMap[18+1];
         }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "src/antenna/preprocessor/v3/parser/APP.g"; }

    
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


    public static class prefix_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start prefix
    // src/antenna/preprocessor/v3/parser/APP.g:156:1: prefix : '//' '#' ;
    public final prefix_return prefix() throws RecognitionException {
        prefix_return retval = new prefix_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal1=null;
        Token char_literal2=null;

        Object string_literal1_tree=null;
        Object char_literal2_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:156:8: ( '//' '#' )
            // src/antenna/preprocessor/v3/parser/APP.g:156:10: '//' '#'
            {
            root_0 = (Object)adaptor.nil();

            string_literal1=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_prefix661); if (failed) return retval;
            char_literal2=(Token)input.LT(1);
            match(input,61,FOLLOW_61_in_prefix664); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal2_tree = (Object)adaptor.create(char_literal2);
            adaptor.addChild(root_0, char_literal2_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end prefix

    public static class r_boolean_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start r_boolean
    // src/antenna/preprocessor/v3/parser/APP.g:158:1: r_boolean : ( TRUE | FALSE );
    public final r_boolean_return r_boolean() throws RecognitionException {
        r_boolean_return retval = new r_boolean_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set3=null;

        Object set3_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:158:11: ( TRUE | FALSE )
            // src/antenna/preprocessor/v3/parser/APP.g:
            {
            root_0 = (Object)adaptor.nil();

            set3=(Token)input.LT(1);
            if ( (input.LA(1)>=TRUE && input.LA(1)<=FALSE) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set3));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_r_boolean0);    throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end r_boolean

    public static class ident_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ident
    // src/antenna/preprocessor/v3/parser/APP.g:159:1: ident : ( SYMBOL | STRING | NUMBER | r_boolean );
    public final ident_return ident() throws RecognitionException {
        ident_return retval = new ident_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SYMBOL4=null;
        Token STRING5=null;
        Token NUMBER6=null;
        r_boolean_return r_boolean7 = null;


        Object SYMBOL4_tree=null;
        Object STRING5_tree=null;
        Object NUMBER6_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:159:6: ( SYMBOL | STRING | NUMBER | r_boolean )
            int alt1=4;
            switch ( input.LA(1) ) {
            case SYMBOL:
                {
                alt1=1;
                }
                break;
            case STRING:
                {
                alt1=2;
                }
                break;
            case NUMBER:
                {
                alt1=3;
                }
                break;
            case TRUE:
            case FALSE:
                {
                alt1=4;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("159:1: ident : ( SYMBOL | STRING | NUMBER | r_boolean );", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:159:8: SYMBOL
                    {
                    root_0 = (Object)adaptor.nil();

                    SYMBOL4=(Token)input.LT(1);
                    match(input,SYMBOL,FOLLOW_SYMBOL_in_ident682); if (failed) return retval;
                    if ( backtracking==0 ) {
                    SYMBOL4_tree = (Object)adaptor.create(SYMBOL4);
                    adaptor.addChild(root_0, SYMBOL4_tree);
                    }

                    }
                    break;
                case 2 :
                    // src/antenna/preprocessor/v3/parser/APP.g:159:17: STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    STRING5=(Token)input.LT(1);
                    match(input,STRING,FOLLOW_STRING_in_ident686); if (failed) return retval;
                    if ( backtracking==0 ) {
                    STRING5_tree = (Object)adaptor.create(STRING5);
                    adaptor.addChild(root_0, STRING5_tree);
                    }

                    }
                    break;
                case 3 :
                    // src/antenna/preprocessor/v3/parser/APP.g:159:26: NUMBER
                    {
                    root_0 = (Object)adaptor.nil();

                    NUMBER6=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_ident690); if (failed) return retval;
                    if ( backtracking==0 ) {
                    NUMBER6_tree = (Object)adaptor.create(NUMBER6);
                    adaptor.addChild(root_0, NUMBER6_tree);
                    }

                    }
                    break;
                case 4 :
                    // src/antenna/preprocessor/v3/parser/APP.g:159:35: r_boolean
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_r_boolean_in_ident694);
                    r_boolean7=r_boolean();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, r_boolean7.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end ident

    public static class bool_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start bool
    // src/antenna/preprocessor/v3/parser/APP.g:161:1: bool : ( ident | LPAR expression RPAR );
    public final bool_return bool() throws RecognitionException {
        bool_return retval = new bool_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LPAR9=null;
        Token RPAR11=null;
        ident_return ident8 = null;

        expression_return expression10 = null;


        Object LPAR9_tree=null;
        Object RPAR11_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:161:5: ( ident | LPAR expression RPAR )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=TRUE && LA2_0<=FALSE)||(LA2_0>=SYMBOL && LA2_0<=STRING)) ) {
                alt2=1;
            }
            else if ( (LA2_0==LPAR) ) {
                alt2=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("161:1: bool : ( ident | LPAR expression RPAR );", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:161:7: ident
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ident_in_bool702);
                    ident8=ident();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, ident8.getTree());

                    }
                    break;
                case 2 :
                    // src/antenna/preprocessor/v3/parser/APP.g:162:9: LPAR expression RPAR
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAR9=(Token)input.LT(1);
                    match(input,LPAR,FOLLOW_LPAR_in_bool712); if (failed) return retval;
                    pushFollow(FOLLOW_expression_in_bool715);
                    expression10=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, expression10.getTree());
                    RPAR11=(Token)input.LT(1);
                    match(input,RPAR,FOLLOW_RPAR_in_bool717); if (failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end bool

    public static class not_bool_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start not_bool
    // src/antenna/preprocessor/v3/parser/APP.g:165:1: not_bool : ( NOT )* bool ;
    public final not_bool_return not_bool() throws RecognitionException {
        not_bool_return retval = new not_bool_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT12=null;
        bool_return bool13 = null;


        Object NOT12_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:165:10: ( ( NOT )* bool )
            // src/antenna/preprocessor/v3/parser/APP.g:165:12: ( NOT )* bool
            {
            root_0 = (Object)adaptor.nil();

            // src/antenna/preprocessor/v3/parser/APP.g:165:12: ( NOT )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==NOT) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // src/antenna/preprocessor/v3/parser/APP.g:165:13: NOT
            	    {
            	    NOT12=(Token)input.LT(1);
            	    match(input,NOT,FOLLOW_NOT_in_not_bool740); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    NOT12_tree = (Object)adaptor.create(NOT12);
            	    root_0 = (Object)adaptor.becomeRoot(NOT12_tree, root_0);
            	    }

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            pushFollow(FOLLOW_bool_in_not_bool745);
            bool13=bool();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, bool13.getTree());

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end not_bool

    public static class eq_bool_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start eq_bool
    // src/antenna/preprocessor/v3/parser/APP.g:167:1: eq_bool : not_bool ( ( AT | EQ | NEQ | LT | GT | LTE | GTE ) not_bool )? ;
    public final eq_bool_return eq_bool() throws RecognitionException {
        eq_bool_return retval = new eq_bool_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AT15=null;
        Token EQ16=null;
        Token NEQ17=null;
        Token LT18=null;
        Token GT19=null;
        Token LTE20=null;
        Token GTE21=null;
        not_bool_return not_bool14 = null;

        not_bool_return not_bool22 = null;


        Object AT15_tree=null;
        Object EQ16_tree=null;
        Object NEQ17_tree=null;
        Object LT18_tree=null;
        Object GT19_tree=null;
        Object LTE20_tree=null;
        Object GTE21_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:167:9: ( not_bool ( ( AT | EQ | NEQ | LT | GT | LTE | GTE ) not_bool )? )
            // src/antenna/preprocessor/v3/parser/APP.g:168:5: not_bool ( ( AT | EQ | NEQ | LT | GT | LTE | GTE ) not_bool )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_not_bool_in_eq_bool757);
            not_bool14=not_bool();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, not_bool14.getTree());
            // src/antenna/preprocessor/v3/parser/APP.g:168:14: ( ( AT | EQ | NEQ | LT | GT | LTE | GTE ) not_bool )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=AT && LA5_0<=GTE)||LA5_0==EQ) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:168:15: ( AT | EQ | NEQ | LT | GT | LTE | GTE ) not_bool
                    {
                    // src/antenna/preprocessor/v3/parser/APP.g:168:15: ( AT | EQ | NEQ | LT | GT | LTE | GTE )
                    int alt4=7;
                    switch ( input.LA(1) ) {
                    case AT:
                        {
                        alt4=1;
                        }
                        break;
                    case EQ:
                        {
                        alt4=2;
                        }
                        break;
                    case NEQ:
                        {
                        alt4=3;
                        }
                        break;
                    case LT:
                        {
                        alt4=4;
                        }
                        break;
                    case GT:
                        {
                        alt4=5;
                        }
                        break;
                    case LTE:
                        {
                        alt4=6;
                        }
                        break;
                    case GTE:
                        {
                        alt4=7;
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("168:15: ( AT | EQ | NEQ | LT | GT | LTE | GTE )", 4, 0, input);

                        throw nvae;
                    }

                    switch (alt4) {
                        case 1 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:17: AT
                            {
                            AT15=(Token)input.LT(1);
                            match(input,AT,FOLLOW_AT_in_eq_bool762); if (failed) return retval;
                            if ( backtracking==0 ) {
                            AT15_tree = (Object)adaptor.create(AT15);
                            root_0 = (Object)adaptor.becomeRoot(AT15_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:23: EQ
                            {
                            EQ16=(Token)input.LT(1);
                            match(input,EQ,FOLLOW_EQ_in_eq_bool767); if (failed) return retval;
                            if ( backtracking==0 ) {
                            EQ16_tree = (Object)adaptor.create(EQ16);
                            root_0 = (Object)adaptor.becomeRoot(EQ16_tree, root_0);
                            }

                            }
                            break;
                        case 3 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:29: NEQ
                            {
                            NEQ17=(Token)input.LT(1);
                            match(input,NEQ,FOLLOW_NEQ_in_eq_bool772); if (failed) return retval;
                            if ( backtracking==0 ) {
                            NEQ17_tree = (Object)adaptor.create(NEQ17);
                            root_0 = (Object)adaptor.becomeRoot(NEQ17_tree, root_0);
                            }

                            }
                            break;
                        case 4 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:36: LT
                            {
                            LT18=(Token)input.LT(1);
                            match(input,LT,FOLLOW_LT_in_eq_bool777); if (failed) return retval;
                            if ( backtracking==0 ) {
                            LT18_tree = (Object)adaptor.create(LT18);
                            root_0 = (Object)adaptor.becomeRoot(LT18_tree, root_0);
                            }

                            }
                            break;
                        case 5 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:42: GT
                            {
                            GT19=(Token)input.LT(1);
                            match(input,GT,FOLLOW_GT_in_eq_bool782); if (failed) return retval;
                            if ( backtracking==0 ) {
                            GT19_tree = (Object)adaptor.create(GT19);
                            root_0 = (Object)adaptor.becomeRoot(GT19_tree, root_0);
                            }

                            }
                            break;
                        case 6 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:48: LTE
                            {
                            LTE20=(Token)input.LT(1);
                            match(input,LTE,FOLLOW_LTE_in_eq_bool787); if (failed) return retval;
                            if ( backtracking==0 ) {
                            LTE20_tree = (Object)adaptor.create(LTE20);
                            root_0 = (Object)adaptor.becomeRoot(LTE20_tree, root_0);
                            }

                            }
                            break;
                        case 7 :
                            // src/antenna/preprocessor/v3/parser/APP.g:168:55: GTE
                            {
                            GTE21=(Token)input.LT(1);
                            match(input,GTE,FOLLOW_GTE_in_eq_bool792); if (failed) return retval;
                            if ( backtracking==0 ) {
                            GTE21_tree = (Object)adaptor.create(GTE21);
                            root_0 = (Object)adaptor.becomeRoot(GTE21_tree, root_0);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_not_bool_in_eq_bool797);
                    not_bool22=not_bool();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, not_bool22.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end eq_bool

    public static class and_bool_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start and_bool
    // src/antenna/preprocessor/v3/parser/APP.g:170:1: and_bool : eq_bool ( AND eq_bool )* ;
    public final and_bool_return and_bool() throws RecognitionException {
        and_bool_return retval = new and_bool_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND24=null;
        eq_bool_return eq_bool23 = null;

        eq_bool_return eq_bool25 = null;


        Object AND24_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:170:10: ( eq_bool ( AND eq_bool )* )
            // src/antenna/preprocessor/v3/parser/APP.g:170:12: eq_bool ( AND eq_bool )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_eq_bool_in_and_bool807);
            eq_bool23=eq_bool();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, eq_bool23.getTree());
            // src/antenna/preprocessor/v3/parser/APP.g:170:20: ( AND eq_bool )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==AND) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // src/antenna/preprocessor/v3/parser/APP.g:170:21: AND eq_bool
            	    {
            	    AND24=(Token)input.LT(1);
            	    match(input,AND,FOLLOW_AND_in_and_bool810); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    AND24_tree = (Object)adaptor.create(AND24);
            	    root_0 = (Object)adaptor.becomeRoot(AND24_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_eq_bool_in_and_bool813);
            	    eq_bool25=eq_bool();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, eq_bool25.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end and_bool

    public static class xor_bool_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start xor_bool
    // src/antenna/preprocessor/v3/parser/APP.g:172:1: xor_bool : and_bool ( XOR and_bool )* ;
    public final xor_bool_return xor_bool() throws RecognitionException {
        xor_bool_return retval = new xor_bool_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token XOR27=null;
        and_bool_return and_bool26 = null;

        and_bool_return and_bool28 = null;


        Object XOR27_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:172:10: ( and_bool ( XOR and_bool )* )
            // src/antenna/preprocessor/v3/parser/APP.g:172:12: and_bool ( XOR and_bool )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_bool_in_xor_bool823);
            and_bool26=and_bool();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, and_bool26.getTree());
            // src/antenna/preprocessor/v3/parser/APP.g:172:21: ( XOR and_bool )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==XOR) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // src/antenna/preprocessor/v3/parser/APP.g:172:22: XOR and_bool
            	    {
            	    XOR27=(Token)input.LT(1);
            	    match(input,XOR,FOLLOW_XOR_in_xor_bool826); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    XOR27_tree = (Object)adaptor.create(XOR27);
            	    root_0 = (Object)adaptor.becomeRoot(XOR27_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_and_bool_in_xor_bool829);
            	    and_bool28=and_bool();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, and_bool28.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end xor_bool

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expression
    // src/antenna/preprocessor/v3/parser/APP.g:174:1: expression : xor_bool ( OR xor_bool )* ;
    public final expression_return expression() throws RecognitionException {
        expression_return retval = new expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR30=null;
        xor_bool_return xor_bool29 = null;

        xor_bool_return xor_bool31 = null;


        Object OR30_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:174:12: ( xor_bool ( OR xor_bool )* )
            // src/antenna/preprocessor/v3/parser/APP.g:174:14: xor_bool ( OR xor_bool )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_xor_bool_in_expression839);
            xor_bool29=xor_bool();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, xor_bool29.getTree());
            // src/antenna/preprocessor/v3/parser/APP.g:174:23: ( OR xor_bool )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==OR) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // src/antenna/preprocessor/v3/parser/APP.g:174:24: OR xor_bool
            	    {
            	    OR30=(Token)input.LT(1);
            	    match(input,OR,FOLLOW_OR_in_expression842); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    OR30_tree = (Object)adaptor.create(OR30);
            	    root_0 = (Object)adaptor.becomeRoot(OR30_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_xor_bool_in_expression845);
            	    xor_bool31=xor_bool();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, xor_bool31.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end expression

    public static class anything_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start anything
    // src/antenna/preprocessor/v3/parser/APP.g:176:1: anything : (~ EOL )+ ;
    public final anything_return anything() throws RecognitionException {
        anything_return retval = new anything_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set32=null;

        Object set32_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:176:10: ( (~ EOL )+ )
            // src/antenna/preprocessor/v3/parser/APP.g:176:12: (~ EOL )+
            {
            root_0 = (Object)adaptor.nil();

            // src/antenna/preprocessor/v3/parser/APP.g:176:12: (~ EOL )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=DOT && LA9_0<=CHAR)||(LA9_0>=SYMBOL && LA9_0<=61)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // src/antenna/preprocessor/v3/parser/APP.g:176:13: ~ EOL
            	    {
            	    set32=(Token)input.LT(1);
            	    if ( (input.LA(1)>=DOT && input.LA(1)<=CHAR)||(input.LA(1)>=SYMBOL && input.LA(1)<=61) ) {
            	        input.consume();
            	        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set32));
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_anything856);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
            	    if (backtracking>0) {failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end anything

    public static class debug_level_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start debug_level
    // src/antenna/preprocessor/v3/parser/APP.g:178:1: debug_level : ( DEBUG | INFO | WARN | ERROR | FATAL );
    public final debug_level_return debug_level() throws RecognitionException {
        debug_level_return retval = new debug_level_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set33=null;

        Object set33_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:178:13: ( DEBUG | INFO | WARN | ERROR | FATAL )
            // src/antenna/preprocessor/v3/parser/APP.g:
            {
            root_0 = (Object)adaptor.nil();

            set33=(Token)input.LT(1);
            if ( (input.LA(1)>=DEBUG && input.LA(1)<=FATAL) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set33));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_debug_level0);    throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end debug_level

    public static class line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start line
    // src/antenna/preprocessor/v3/parser/APP.g:180:1: line : ( ( ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG ) ) ( EOL | EOF ) ) ;
    public final line_return line() throws RecognitionException {
        line_return retval = new line_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEFINE35=null;
        Token UNDEFINE37=null;
        Token SYMBOL38=null;
        Token set39=null;
        Token set41=null;
        Token SYMBOL42=null;
        Token set43=null;
        Token INCLUDE44=null;
        Token set45=null;
        Token EXPAND46=null;
        Token set47=null;
        Token set48=null;
        Token ENDDEBUG50=null;
        Token set51=null;
        prefix_return prefix34 = null;

        define_return define36 = null;

        expression_return expression40 = null;

        debug_level_return debug_level49 = null;


        Object DEFINE35_tree=null;
        Object UNDEFINE37_tree=null;
        Object SYMBOL38_tree=null;
        Object set39_tree=null;
        Object set41_tree=null;
        Object SYMBOL42_tree=null;
        Object set43_tree=null;
        Object INCLUDE44_tree=null;
        Object set45_tree=null;
        Object EXPAND46_tree=null;
        Object set47_tree=null;
        Object set48_tree=null;
        Object ENDDEBUG50_tree=null;
        Object set51_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:180:6: ( ( ( ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG ) ) ( EOL | EOF ) ) )
            // src/antenna/preprocessor/v3/parser/APP.g:181:1: ( ( ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG ) ) ( EOL | EOF ) )
            {
            root_0 = (Object)adaptor.nil();

            // src/antenna/preprocessor/v3/parser/APP.g:181:1: ( ( ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG ) ) ( EOL | EOF ) )
            // src/antenna/preprocessor/v3/parser/APP.g:182:5: ( ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG ) ) ( EOL | EOF )
            {
            // src/antenna/preprocessor/v3/parser/APP.g:182:5: ( ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG ) )
            // src/antenna/preprocessor/v3/parser/APP.g:183:9: ( prefix )=> prefix ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG )
            {
            pushFollow(FOLLOW_prefix_in_line913);
            prefix34=prefix();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, prefix34.getTree());
            // src/antenna/preprocessor/v3/parser/APP.g:184:13: ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG )
            int alt14=7;
            switch ( input.LA(1) ) {
            case DEFINE:
            case UNDEFINE:
            case IF:
            case ELIF:
            case CONDITION:
                {
                alt14=1;
                }
                break;
            case IFDEF:
            case IFNDEF:
            case ELIFDEF:
            case ELIFNDEF:
                {
                alt14=2;
                }
                break;
            case ENDIF:
            case ELSE:
            case ENDINCLUDE:
                {
                alt14=3;
                }
                break;
            case INCLUDE:
                {
                alt14=4;
                }
                break;
            case EXPAND:
                {
                alt14=5;
                }
                break;
            case DEBUG:
            case MDEBUG:
                {
                alt14=6;
                }
                break;
            case ENDDEBUG:
                {
                alt14=7;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("184:13: ( ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression ) | ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL | ( ENDIF | ELSE | ENDINCLUDE ) | ( INCLUDE (~ EOL )+ ) | ( EXPAND (~ EOL )+ ) | ( ( DEBUG | MDEBUG ) ( debug_level )? ) | ENDDEBUG )", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:185:17: ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression )
                    {
                    // src/antenna/preprocessor/v3/parser/APP.g:185:17: ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression )
                    int alt10=3;
                    switch ( input.LA(1) ) {
                    case DEFINE:
                        {
                        alt10=1;
                        }
                        break;
                    case UNDEFINE:
                        {
                        alt10=2;
                        }
                        break;
                    case IF:
                    case ELIF:
                    case CONDITION:
                        {
                        alt10=3;
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("185:17: ( DEFINE define | UNDEFINE SYMBOL | ( IF | ELIF | CONDITION ) expression )", 10, 0, input);

                        throw nvae;
                    }

                    switch (alt10) {
                        case 1 :
                            // src/antenna/preprocessor/v3/parser/APP.g:186:17: DEFINE define
                            {
                            DEFINE35=(Token)input.LT(1);
                            match(input,DEFINE,FOLLOW_DEFINE_in_line964); if (failed) return retval;
                            if ( backtracking==0 ) {
                            DEFINE35_tree = (Object)adaptor.create(DEFINE35);
                            adaptor.addChild(root_0, DEFINE35_tree);
                            }
                            pushFollow(FOLLOW_define_in_line966);
                            define36=define();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) adaptor.addChild(root_0, define36.getTree());

                            }
                            break;
                        case 2 :
                            // src/antenna/preprocessor/v3/parser/APP.g:187:17: UNDEFINE SYMBOL
                            {
                            UNDEFINE37=(Token)input.LT(1);
                            match(input,UNDEFINE,FOLLOW_UNDEFINE_in_line986); if (failed) return retval;
                            if ( backtracking==0 ) {
                            UNDEFINE37_tree = (Object)adaptor.create(UNDEFINE37);
                            adaptor.addChild(root_0, UNDEFINE37_tree);
                            }
                            SYMBOL38=(Token)input.LT(1);
                            match(input,SYMBOL,FOLLOW_SYMBOL_in_line988); if (failed) return retval;
                            if ( backtracking==0 ) {
                            SYMBOL38_tree = (Object)adaptor.create(SYMBOL38);
                            adaptor.addChild(root_0, SYMBOL38_tree);
                            }

                            }
                            break;
                        case 3 :
                            // src/antenna/preprocessor/v3/parser/APP.g:188:17: ( IF | ELIF | CONDITION ) expression
                            {
                            set39=(Token)input.LT(1);
                            if ( (input.LA(1)>=IF && input.LA(1)<=CONDITION) ) {
                                input.consume();
                                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set39));
                                errorRecovery=false;failed=false;
                            }
                            else {
                                if (backtracking>0) {failed=true; return retval;}
                                MismatchedSetException mse =
                                    new MismatchedSetException(null,input);
                                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1008);    throw mse;
                            }

                            pushFollow(FOLLOW_expression_in_line1020);
                            expression40=expression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) adaptor.addChild(root_0, expression40.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // src/antenna/preprocessor/v3/parser/APP.g:189:17: ( IFDEF | IFNDEF | ELIFDEF | ELIFNDEF ) SYMBOL
                    {
                    set41=(Token)input.LT(1);
                    if ( (input.LA(1)>=IFDEF && input.LA(1)<=ELIFNDEF) ) {
                        input.consume();
                        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set41));
                        errorRecovery=false;failed=false;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1042);    throw mse;
                    }

                    SYMBOL42=(Token)input.LT(1);
                    match(input,SYMBOL,FOLLOW_SYMBOL_in_line1058); if (failed) return retval;
                    if ( backtracking==0 ) {
                    SYMBOL42_tree = (Object)adaptor.create(SYMBOL42);
                    adaptor.addChild(root_0, SYMBOL42_tree);
                    }

                    }
                    break;
                case 3 :
                    // src/antenna/preprocessor/v3/parser/APP.g:190:17: ( ENDIF | ELSE | ENDINCLUDE )
                    {
                    set43=(Token)input.LT(1);
                    if ( (input.LA(1)>=ENDIF && input.LA(1)<=ENDINCLUDE) ) {
                        input.consume();
                        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set43));
                        errorRecovery=false;failed=false;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1079);    throw mse;
                    }


                    }
                    break;
                case 4 :
                    // src/antenna/preprocessor/v3/parser/APP.g:191:17: ( INCLUDE (~ EOL )+ )
                    {
                    // src/antenna/preprocessor/v3/parser/APP.g:191:17: ( INCLUDE (~ EOL )+ )
                    // src/antenna/preprocessor/v3/parser/APP.g:191:18: INCLUDE (~ EOL )+
                    {
                    INCLUDE44=(Token)input.LT(1);
                    match(input,INCLUDE,FOLLOW_INCLUDE_in_line1109); if (failed) return retval;
                    if ( backtracking==0 ) {
                    INCLUDE44_tree = (Object)adaptor.create(INCLUDE44);
                    adaptor.addChild(root_0, INCLUDE44_tree);
                    }
                    // src/antenna/preprocessor/v3/parser/APP.g:191:26: (~ EOL )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>=DOT && LA11_0<=CHAR)||(LA11_0>=SYMBOL && LA11_0<=61)) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // src/antenna/preprocessor/v3/parser/APP.g:191:27: ~ EOL
                    	    {
                    	    set45=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=DOT && input.LA(1)<=CHAR)||(input.LA(1)>=SYMBOL && input.LA(1)<=61) ) {
                    	        input.consume();
                    	        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set45));
                    	        errorRecovery=false;failed=false;
                    	    }
                    	    else {
                    	        if (backtracking>0) {failed=true; return retval;}
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1112);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                    	    if (backtracking>0) {failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);


                    }


                    }
                    break;
                case 5 :
                    // src/antenna/preprocessor/v3/parser/APP.g:192:17: ( EXPAND (~ EOL )+ )
                    {
                    // src/antenna/preprocessor/v3/parser/APP.g:192:17: ( EXPAND (~ EOL )+ )
                    // src/antenna/preprocessor/v3/parser/APP.g:192:18: EXPAND (~ EOL )+
                    {
                    EXPAND46=(Token)input.LT(1);
                    match(input,EXPAND,FOLLOW_EXPAND_in_line1138); if (failed) return retval;
                    if ( backtracking==0 ) {
                    EXPAND46_tree = (Object)adaptor.create(EXPAND46);
                    adaptor.addChild(root_0, EXPAND46_tree);
                    }
                    // src/antenna/preprocessor/v3/parser/APP.g:192:25: (~ EOL )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>=DOT && LA12_0<=CHAR)||(LA12_0>=SYMBOL && LA12_0<=61)) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // src/antenna/preprocessor/v3/parser/APP.g:192:26: ~ EOL
                    	    {
                    	    set47=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=DOT && input.LA(1)<=CHAR)||(input.LA(1)>=SYMBOL && input.LA(1)<=61) ) {
                    	        input.consume();
                    	        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set47));
                    	        errorRecovery=false;failed=false;
                    	    }
                    	    else {
                    	        if (backtracking>0) {failed=true; return retval;}
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1141);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                    	    if (backtracking>0) {failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);


                    }


                    }
                    break;
                case 6 :
                    // src/antenna/preprocessor/v3/parser/APP.g:193:17: ( ( DEBUG | MDEBUG ) ( debug_level )? )
                    {
                    // src/antenna/preprocessor/v3/parser/APP.g:193:17: ( ( DEBUG | MDEBUG ) ( debug_level )? )
                    // src/antenna/preprocessor/v3/parser/APP.g:193:18: ( DEBUG | MDEBUG ) ( debug_level )?
                    {
                    set48=(Token)input.LT(1);
                    if ( input.LA(1)==DEBUG||input.LA(1)==MDEBUG ) {
                        input.consume();
                        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set48));
                        errorRecovery=false;failed=false;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1169);    throw mse;
                    }

                    // src/antenna/preprocessor/v3/parser/APP.g:193:33: ( debug_level )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( ((LA13_0>=DEBUG && LA13_0<=FATAL)) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // src/antenna/preprocessor/v3/parser/APP.g:193:34: debug_level
                            {
                            pushFollow(FOLLOW_debug_level_in_line1176);
                            debug_level49=debug_level();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) adaptor.addChild(root_0, debug_level49.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 7 :
                    // src/antenna/preprocessor/v3/parser/APP.g:194:17: ENDDEBUG
                    {
                    ENDDEBUG50=(Token)input.LT(1);
                    match(input,ENDDEBUG,FOLLOW_ENDDEBUG_in_line1199); if (failed) return retval;
                    if ( backtracking==0 ) {
                    ENDDEBUG50_tree = (Object)adaptor.create(ENDDEBUG50);
                    adaptor.addChild(root_0, ENDDEBUG50_tree);
                    }

                    }
                    break;

            }


            }

            set51=(Token)input.LT(1);
            if ( input.LA(1)==EOF||input.LA(1)==EOL ) {
                input.consume();
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_line1264);    throw mse;
            }


            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end line

    public static class endof_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start endof
    // src/antenna/preprocessor/v3/parser/APP.g:202:1: endof : EOL ;
    public final endof_return endof() throws RecognitionException {
        endof_return retval = new endof_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOL52=null;

        Object EOL52_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:202:9: ( EOL )
            // src/antenna/preprocessor/v3/parser/APP.g:202:11: EOL
            {
            root_0 = (Object)adaptor.nil();

            EOL52=(Token)input.LT(1);
            match(input,EOL,FOLLOW_EOL_in_endof1280); if (failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end endof

    public static class define_command_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start define_command
    // src/antenna/preprocessor/v3/parser/APP.g:203:1: define_command : ( UNSET | ADD_IF_NEW );
    public final define_command_return define_command() throws RecognitionException {
        define_command_return retval = new define_command_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set53=null;

        Object set53_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:203:16: ( UNSET | ADD_IF_NEW )
            // src/antenna/preprocessor/v3/parser/APP.g:
            {
            root_0 = (Object)adaptor.nil();

            set53=(Token)input.LT(1);
            if ( (input.LA(1)>=UNSET && input.LA(1)<=ADD_IF_NEW) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set53));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_define_command0);    throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end define_command

    public static class define_value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start define_value
    // src/antenna/preprocessor/v3/parser/APP.g:204:1: define_value : ( ident | debug_level );
    public final define_value_return define_value() throws RecognitionException {
        define_value_return retval = new define_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ident_return ident54 = null;

        debug_level_return debug_level55 = null;



        try {
            // src/antenna/preprocessor/v3/parser/APP.g:204:13: ( ident | debug_level )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>=TRUE && LA15_0<=FALSE)||(LA15_0>=SYMBOL && LA15_0<=STRING)) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=DEBUG && LA15_0<=FATAL)) ) {
                alt15=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("204:1: define_value : ( ident | debug_level );", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:204:15: ident
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ident_in_define_value1298);
                    ident54=ident();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, ident54.getTree());

                    }
                    break;
                case 2 :
                    // src/antenna/preprocessor/v3/parser/APP.g:204:23: debug_level
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_debug_level_in_define_value1302);
                    debug_level55=debug_level();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, debug_level55.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end define_value

    public static class define_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start define
    // src/antenna/preprocessor/v3/parser/APP.g:205:1: define : ( define_command AT )? SYMBOL ( EQ define_value )? ;
    public final define_return define() throws RecognitionException {
        define_return retval = new define_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AT57=null;
        Token SYMBOL58=null;
        Token EQ59=null;
        define_command_return define_command56 = null;

        define_value_return define_value60 = null;


        Object AT57_tree=null;
        Object SYMBOL58_tree=null;
        Object EQ59_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:205:8: ( ( define_command AT )? SYMBOL ( EQ define_value )? )
            // src/antenna/preprocessor/v3/parser/APP.g:205:10: ( define_command AT )? SYMBOL ( EQ define_value )?
            {
            root_0 = (Object)adaptor.nil();

            // src/antenna/preprocessor/v3/parser/APP.g:205:10: ( define_command AT )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=UNSET && LA16_0<=ADD_IF_NEW)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:205:11: define_command AT
                    {
                    pushFollow(FOLLOW_define_command_in_define1310);
                    define_command56=define_command();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, define_command56.getTree());
                    AT57=(Token)input.LT(1);
                    match(input,AT,FOLLOW_AT_in_define1312); if (failed) return retval;
                    if ( backtracking==0 ) {
                    AT57_tree = (Object)adaptor.create(AT57);
                    adaptor.addChild(root_0, AT57_tree);
                    }

                    }
                    break;

            }

            SYMBOL58=(Token)input.LT(1);
            match(input,SYMBOL,FOLLOW_SYMBOL_in_define1316); if (failed) return retval;
            if ( backtracking==0 ) {
            SYMBOL58_tree = (Object)adaptor.create(SYMBOL58);
            root_0 = (Object)adaptor.becomeRoot(SYMBOL58_tree, root_0);
            }
            // src/antenna/preprocessor/v3/parser/APP.g:205:39: ( EQ define_value )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==EQ) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:205:40: EQ define_value
                    {
                    EQ59=(Token)input.LT(1);
                    match(input,EQ,FOLLOW_EQ_in_define1320); if (failed) return retval;
                    pushFollow(FOLLOW_define_value_in_define1323);
                    define_value60=define_value();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, define_value60.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end define

    public static class defines_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start defines
    // src/antenna/preprocessor/v3/parser/APP.g:206:1: defines : ( ( EOL | EOF ) | define ( COMMA define )* ( EOL )? );
    public final defines_return defines() throws RecognitionException {
        defines_return retval = new defines_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set61=null;
        Token COMMA63=null;
        Token EOL65=null;
        define_return define62 = null;

        define_return define64 = null;


        Object set61_tree=null;
        Object COMMA63_tree=null;
        Object EOL65_tree=null;

        try {
            // src/antenna/preprocessor/v3/parser/APP.g:206:9: ( ( EOL | EOF ) | define ( COMMA define )* ( EOL )? )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==EOF||LA20_0==EOL) ) {
                alt20=1;
            }
            else if ( ((LA20_0>=UNSET && LA20_0<=ADD_IF_NEW)||LA20_0==SYMBOL) ) {
                alt20=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("206:1: defines : ( ( EOL | EOF ) | define ( COMMA define )* ( EOL )? );", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // src/antenna/preprocessor/v3/parser/APP.g:206:11: ( EOL | EOF )
                    {
                    root_0 = (Object)adaptor.nil();

                    set61=(Token)input.LT(1);
                    if ( input.LA(1)==EOF||input.LA(1)==EOL ) {
                        input.consume();
                        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set61));
                        errorRecovery=false;failed=false;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_defines1332);    throw mse;
                    }


                    }
                    break;
                case 2 :
                    // src/antenna/preprocessor/v3/parser/APP.g:206:23: define ( COMMA define )* ( EOL )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_define_in_defines1340);
                    define62=define();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, define62.getTree());
                    // src/antenna/preprocessor/v3/parser/APP.g:206:30: ( COMMA define )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==COMMA) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // src/antenna/preprocessor/v3/parser/APP.g:206:31: COMMA define
                    	    {
                    	    COMMA63=(Token)input.LT(1);
                    	    match(input,COMMA,FOLLOW_COMMA_in_defines1343); if (failed) return retval;
                    	    pushFollow(FOLLOW_define_in_defines1346);
                    	    define64=define();
                    	    _fsp--;
                    	    if (failed) return retval;
                    	    if ( backtracking==0 ) adaptor.addChild(root_0, define64.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);

                    // src/antenna/preprocessor/v3/parser/APP.g:206:47: ( EOL )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==EOL) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // src/antenna/preprocessor/v3/parser/APP.g:206:48: EOL
                            {
                            EOL65=(Token)input.LT(1);
                            match(input,EOL,FOLLOW_EOL_in_defines1351); if (failed) return retval;
                            if ( backtracking==0 ) {
                            EOL65_tree = (Object)adaptor.create(EOL65);
                            adaptor.addChild(root_0, EOL65_tree);
                            }

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        
        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end defines


 

    public static final BitSet FOLLOW_60_in_prefix661 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_prefix664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_r_boolean0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SYMBOL_in_ident682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_ident686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_ident690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_r_boolean_in_ident694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ident_in_bool702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_bool712 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_expression_in_bool715 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAR_in_bool717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_not_bool740 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_bool_in_not_bool745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_not_bool_in_eq_bool757 = new BitSet(new long[]{0x08000000000FC002L});
    public static final BitSet FOLLOW_AT_in_eq_bool762 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_EQ_in_eq_bool767 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_NEQ_in_eq_bool772 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_LT_in_eq_bool777 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_GT_in_eq_bool782 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_LTE_in_eq_bool787 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_GTE_in_eq_bool792 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_not_bool_in_eq_bool797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eq_bool_in_and_bool807 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_AND_in_and_bool810 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_eq_bool_in_and_bool813 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_and_bool_in_xor_bool823 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_XOR_in_xor_bool826 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_and_bool_in_xor_bool829 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_xor_bool_in_expression839 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_OR_in_expression842 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_xor_bool_in_expression845 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_set_in_anything856 = new BitSet(new long[]{0x3FDFFFFFFFFFFFF2L});
    public static final BitSet FOLLOW_set_in_debug_level0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prefix_in_line913 = new BitSet(new long[]{0x000001FFFE100000L});
    public static final BitSet FOLLOW_DEFINE_in_line964 = new BitSet(new long[]{0x0040060000000000L});
    public static final BitSet FOLLOW_define_in_line966 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_UNDEFINE_in_line986 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SYMBOL_in_line988 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_set_in_line1008 = new BitSet(new long[]{0x01C0180000001100L});
    public static final BitSet FOLLOW_expression_in_line1020 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_set_in_line1042 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SYMBOL_in_line1058 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_set_in_line1079 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_INCLUDE_in_line1109 = new BitSet(new long[]{0x3FDFFFFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_line1112 = new BitSet(new long[]{0x3FFFFFFFFFFFFFF0L});
    public static final BitSet FOLLOW_EXPAND_in_line1138 = new BitSet(new long[]{0x3FDFFFFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_line1141 = new BitSet(new long[]{0x3FFFFFFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_line1169 = new BitSet(new long[]{0x0020000001F00000L});
    public static final BitSet FOLLOW_debug_level_in_line1176 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_ENDDEBUG_in_line1199 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_set_in_line1264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOL_in_endof1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_define_command0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ident_in_define_value1298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_debug_level_in_define_value1302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_define_command_in_define1310 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_AT_in_define1312 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SYMBOL_in_define1316 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_EQ_in_define1320 = new BitSet(new long[]{0x01C0180001F00000L});
    public static final BitSet FOLLOW_define_value_in_define1323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_defines1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_define_in_defines1340 = new BitSet(new long[]{0x0020000000000022L});
    public static final BitSet FOLLOW_COMMA_in_defines1343 = new BitSet(new long[]{0x0040060000000000L});
    public static final BitSet FOLLOW_define_in_defines1346 = new BitSet(new long[]{0x0020000000000022L});
    public static final BitSet FOLLOW_EOL_in_defines1351 = new BitSet(new long[]{0x0000000000000002L});

}