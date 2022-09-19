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
Comma = ","
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
LowerThan = "<"
GreaterThan = ">"
LowerEqualsThan = "<="
GreaterEqualsThan = ">="
Equals = "=="
Iguales = "iguales"
Repeat = "repetir"

If = "si"
Else = "sino"
While = mientras
For = "para"
Int = "Entero"
String = "Cadena"
Float = "Flotante"
Boolean = "Booleano"
Or = "o"
And = "y"
Not = "no"
Write = "escribir"
Read = "leer"
Repeat = "repetir"
Init = "inicio"

WhiteSpace = {LineTerminator} | {Identation}
Identifier = {Letter} ({Letter}|{Digit})*
IntegerConstant = {Digit}+
StringConstant = \"{InputCharacter}*\"
FloatConstant = ({IntegerConstant}?\.{IntegerConstant})|({IntegerConstant}\.{IntegerConstant}?)
BlockComment = \/\*{InputCharacter}*\*\/

%%


/* keywords */

<YYINITIAL> {

  {Init}                                     { return symbol(ParserSym.INIT); }
  {Write}                                    { return symbol(ParserSym.WRITE); }
  {Read}                                     { return symbol(ParserSym.READ); }
  {Iguales}                                  { return symbol(ParserSym.IGUALES); }
  {Repeat}                                   { return symbol(ParserSym.REPEAT); }

  /* data type */
  {Int}                                      { return symbol(ParserSym.INT); }
  {Float}                                    { return symbol(ParserSym.FLOAT); }
  {String}                                   { return symbol(ParserSym.STRING); }

  /* operators */
  {If}                                      { return symbol(ParserSym.IF); }
  {Else}                                    { return symbol(ParserSym.ELSE); }
  {While}                                   { return symbol(ParserSym.WHILE); }
  {And}                                     { return symbol(ParserSym.AND); }
  {Or}                                      { return symbol(ParserSym.OR); }
  {Not}                                     { return symbol(ParserSym.NOT); }
  {LowerThan}                               { return symbol(ParserSym.LOWER_THAN); }
  {GreaterThan}                             { return symbol(ParserSym.GREATER_THAN); }
  {LowerEqualsThan}                         { return symbol(ParserSym.LOWER_EQUALS_THAN); }
  {GreaterEqualsThan}                       { return symbol(ParserSym.GREATER_EQUALS_THAN); }
  {Equals}                                  { return symbol(ParserSym.EQUALS ); }
  {Plus}                                    { return symbol(ParserSym.PLUS); }
  {Colon}                                   { return symbol(ParserSym.COLON); }
  {SemiColon}                               { return symbol(ParserSym.SEMICOLON); }
  {Comma}                                   { return symbol(ParserSym.COMMA); }
  {Sub}                                     { return symbol(ParserSym.SUB); }
  {Mult}                                    { return symbol(ParserSym.MULT); }
  {Div}                                     { return symbol(ParserSym.DIV); }
  {Assig}                                   { return symbol(ParserSym.ASSIG); }
  {OpenBracket}                             { return symbol(ParserSym.OPEN_BRACKET); }
  {CloseBracket}                            { return symbol(ParserSym.CLOSE_BRACKET); }
  {OpenCurlyBracket}                        { return symbol(ParserSym.OPEN_CURLY_BRACKET); }
  {CloseCurlyBracket}                       { return symbol(ParserSym.CLOSE_CURLY_BRACKET); }
  {OpenSquareBracket}                        { return symbol(ParserSym.OPEN_SQUARE_BRACKET); }
  {CloseSquareBracket}                       { return symbol(ParserSym.CLOSE_SQUARE_BRACKET); }

  /* identifiers */
  {Identifier}                             { return symbol(ParserSym.IDENTIFIER, yytext()); }
  /* constants */
  {IntegerConstant}                        { return symbol(ParserSym.INTEGER_CONSTANT, yytext()); }
  {FloatConstant}                          { return symbol(ParserSym.FLOAT_CONSTANT, yytext()); }
  {StringConstant}                         { return symbol(ParserSym.STRING_CONSTANT, yytext()); }

  /* ignorar */
  {BlockComment}                            { /* ignore */ }
  {WhiteSpace}                              { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
