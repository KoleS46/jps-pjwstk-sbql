package jpslab.ast.parser;

import java_cup.runtime.*;
import java.io.IOException;

import java_cup.runtime.Symbol;
import jpslab.ast.parser.CupSym; 
import static jpslab.ast.parser.CupSym.*;

%%
%{ 
	private Symbol createToken(int id) {
		//System.out.println(id);
		return new Symbol(id, yyline, yycolumn);
	}
	private Symbol createToken(int id, Object o) {
		//System.out.println(id+"|"+o.toString());
		return new Symbol(id, yyline, yycolumn, o);
	}
%}
 
%public
%class LexLex
%cup
%line 
%column
%char
%eofval{
	return createToken(EOF);
%eofval}

INTEGER = [0-9]+
BOOLEAN = true|false
IDENTIFIER = [_a-zA-Z][0-9a-zA-Z]*
DOUBLE = [0-9]+\.[0-9]+
STRING = [\"][^\"]*[\"]
CHAR = [\'][^\"][\']
LineTerminator = \r|\n|\r\n 
WHITESPACE = {LineTerminator} | [ \t\f]
  
%% 
 
<YYINITIAL> {
	"+"					{ return createToken(PLUS				); }
	"-"					{ return createToken(MINUS				); }
	"*"					{ return createToken(MULTIPLY			); }
	"/"					{ return createToken(DIVIDE				); }
	"%"					{ return createToken(MODULO				); }
	">"					{ return createToken(MORE				); }
	"="|"=="			{ return createToken(EQUALS				); }
	"!="|"<>"			{ return createToken(NOT_EQUALS			); }
	"UNIQUE"|"unique"	{ return createToken(UNIQUE				); }
	"UNION"|"union"		{ return createToken(UNION				); }
	"COUNT"|"count"		{ return createToken(COUNT				); }
	"SUM"|"sum"			{ return createToken(SUM				); }
	"AVG"|"avg"			{ return createToken(AVG				); }
	">="				{ return createToken(MORE_OR_EQUAL		); }
	"<"					{ return createToken(LESS				); }
	"<="				{ return createToken(LESS_OR_EQUAL		); }
	"MIN"|"min"			{ return createToken(MIN				); }
	"MAX"|"max"			{ return createToken(MAX				); }
	"("					{ return createToken(LEFT_ROUND_BRACKET	); }
	")"					{ return createToken(RIGHT_ROUND_BRACKET); }
	"OR"|"or"|"\|\|"	{ return createToken(OR					); }
	"AND"|"and"|"&&"	{ return createToken(AND				); }
	"XOR"|"xor"|"^"		{ return createToken(XOR				); }
	"NOT"|"not"|"!"		{ return createToken(NOT				); }
	"WHERE"|"where"		{ return createToken(WHERE				); }
	"JOIN"|"join"		{ return createToken(JOIN				); }
	"ALL"|"all"			{ return createToken(FORALL				); }
	"ANY"|"any"			{ return createToken(FORANY				); }
	"AS"|"as"			{ return createToken(AS					); }
	"GROUP AS"|"group as" { return createToken(GROUP_AS			); }
	"IN"|"in" 			{ return createToken(IN					); }
	"INTERSECT"|"intersect" { return createToken(INTERSECT		); }
	"MINUS"|"minus"		{ return createToken(MINUS_FUNCTION		); }
	"BAG"|"bag"			{ return createToken(BAG				); }
	"STRUCT"|"struct"	{ return createToken(STRUCT				); }
	
	","					{ return createToken(COMMA				); }
	"."					{ return createToken(DOT				); }
	 
	{WHITESPACE} { }
	{STRING} {return createToken(STRING_LITERAL, yytext().substring(1,yytext().length()-1)) ; }
	{INTEGER} {
		int val;
		try {
			val = Integer.parseInt(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(INTEGER_LITERAL, new Integer(val));
	}
	{DOUBLE} {
		double val;
		try {
			val = Double.parseDouble(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(DOUBLE_LITERAL, new Double(val));
	}
	{BOOLEAN} {
		boolean val;
		try {
			val = Boolean.parseBoolean(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(BOOLEAN_LITERAL, new Boolean(val));
	}
	
	{IDENTIFIER} {
		return createToken(IDENTIFIER, yytext());
	} 
}

