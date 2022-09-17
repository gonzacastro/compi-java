package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.model.*;
import static lyc.compiler.constants.Constants.*;

%%

%public
%class Lexer
%unicode
%cup
%line
%column
%throws CompilerException
%eofval{
  return symbol(ParserSym.EOF);
%eofval}


%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Identation =  [ \t\f]

Plus = "+"
Mult = "*"
Sub = "-"
Div = "/"
Assig = "="
Colon = ":"
SemiColon = ";"
OpenBracket = "("
CloseBracket = ")"
OpenSquareBracket = "["
CloseSquareBracket = "]"
OpenAngleBracket = "<"
CloseAngleBracket = ">"
OpenCurlyBracket = "{"
CloseCurlyBracket = "}"
InlineComment = "//"
OpenCommentBlock = "/*"
CloseCommentBlock = "*/"
Letter = [a-zA-Z]
Digit = [0-9]
If = "si"


WhiteSpace = {LineTerminator} | {Identation}
Identifier = {Letter} ({Letter}|{Digit})*
IntegerConstant = {Digit}+
StringConstant = "{InputCharacter}*"

/*Palabras reservadas*/
Else = "sino"
Elif = "sino si"
While = mientras
For = "para"
Int = "ent"
String = "cadena"
Float = "flotante"
Boolean = "booleano"
Switch = "seleccion"
Case = "caso"
Or = "o"
And = "y"
Not = "no"
Write = "escribir"

%%


/* keywords */

<YYINITIAL> {
  /* identifiers */
  {Identifier}                             { return symbol(ParserSym.IDENTIFIER, yytext()); }
  /* constants */
  {IntegerConstant}                        { return symbol(ParserSym.INTEGER_CONSTANT, yytext()); }
  {StringConstant}                         { return symbol(ParserSym.STRING_CONSTANT, yytext()); }

  /* operators */
  {If}                                      { return symbol(ParserSym.IF); }
  {Plus}                                    { return symbol(ParserSym.PLUS); }
  {SemiColon}                               { return symbol(ParserSym.SEMICOLON); }
  {Sub}                                     { return symbol(ParserSym.SUB); }
  {Mult}                                    { return symbol(ParserSym.MULT); }
  {Div}                                     { return symbol(ParserSym.DIV); }
  {Assig}                                   { return symbol(ParserSym.ASSIG); }
  {OpenBracket}                             { return symbol(ParserSym.OPEN_BRACKET); }
  {CloseBracket}                            { return symbol(ParserSym.CLOSE_BRACKET); }
  {OpenCurlyBracket}                        { return symbol(ParserSym.OPEN_CURLY_BRACKET); }
  {CloseCurlyBracket}                       { return symbol(ParserSym.CLOSE_CURLY_BRACKET); }

  /* whitespace */
  {WhiteSpace}                              { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
