package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.model.*;
import lyc.compiler.files.*;
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
  SymbolTableGenerator tablaDeSimbolos = SymbolTableGenerator.getInstance();
  int IDENTIFIER_RANGE = (int) 20;
  int STRING_RANGE = (int) 40;

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

  private void agregarSimbolo(Simbolo simbolo) {
    tablaDeSimbolos.add(simbolo);
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
While = "mientras"
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

  {Init}                                    { return symbol(ParserSym.INIT); }
  {Write}                                   { return symbol(ParserSym.WRITE); }
  {Read}                                    { return symbol(ParserSym.READ); }
  {Iguales}                                 { return symbol(ParserSym.IGUALES); }
  {Repeat}                                  { return symbol(ParserSym.REPEAT); }

    /* data type */
  {Int}                                     { return symbol(ParserSym.INT); }
  {Float}                                   { return symbol(ParserSym.FLOAT); }
  {String}                                  { return symbol(ParserSym.STRING); }

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
  {OpenSquareBracket}                       { return symbol(ParserSym.OPEN_SQUARE_BRACKET); }
  {CloseSquareBracket}                      { return symbol(ParserSym.CLOSE_SQUARE_BRACKET); }

  /* identifiers */
  {Identifier} 
                                          {
                                            String id = new String(yytext());
                                            int length = id.length();

                                            if (length <= IDENTIFIER_RANGE ) {
                                              agregarSimbolo(new Simbolo(yytext(), null, null, 0));
                                              return symbol(ParserSym.IDENTIFIER, yytext());
                                            } else
                                            {
                                              System.err.println("El identificador [" + id + "] no esta dentro del limite permitido.");                                    
                                              System.in.read();
                                              throw new Error("El identificador [" + id + "] no esta dentro del limite permitido.");                                    
                                            }
                                          }

/* constants */
{IntegerConstant}	     
                                          {                             
                                            Integer constInt = Integer.parseInt(yytext());

                                            if (constInt >= Short.MIN_VALUE && constInt <= Short.MAX_VALUE) {
                                              Simbolo simbolo = new Simbolo("_" + String.valueOf(constInt), "Entero", String.valueOf(constInt), 0);
                                              agregarSimbolo(simbolo);
                                              return symbol(ParserSym.INTEGER_CONSTANT, yytext());                                         
                                            } else
                                            {
                                              System.err.println("El entero [" + yytext() + "] no esta dentro del limite permitido.");
                                              System.in.read();
                                              throw new Error("El entero [" + yytext() + "] no esta dentro del limite permitido."); 
                                            }
                                          }

{FloatConstant}            
                                          {
                                            Double constFloat = Double.parseDouble(yytext());
                                            if (constFloat >= Float.MIN_VALUE && constFloat <= Float.MAX_VALUE) {
                                              Simbolo simbolo = new Simbolo("_" + String.valueOf(constFloat), "Flotante", String.valueOf(constFloat), 0);
                                              agregarSimbolo(simbolo);
                                              return symbol(ParserSym.FLOAT_CONSTANT, yytext());
                                            } else
                                            {
                                              System.err.println("El flotante [" + yytext() + "] no esta dentro del limite permitido.");
                                              System.in.read();
                                              throw new Error("El flotante [" + yytext() + "] no esta dentro del limite permitido.");
                                            }
                                          }

{StringConstant}            
                                          { 
                                            String constString = new String(yytext());
                                            
                                            if (constString.length()-2 <= STRING_RANGE) {
                                              Simbolo simbolo = new Simbolo("_" + constString, "Cadena", constString, constString.length());
                                              agregarSimbolo(simbolo);
                                              return symbol(ParserSym.STRING_CONSTANT, yytext());
                                            } else
                                            {
                                              System.err.println("La cadena [" + yytext() + "] no esta dentro del limite permitido.");
                                              System.in.read();
                                              throw new Error("La cadena [" + yytext() + "] no esta dentro del limite permitido.");
                                            } 
                                          }

  /* ignorar */
  {BlockComment}                            { /* ignore */ }
  {WhiteSpace}                              { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
