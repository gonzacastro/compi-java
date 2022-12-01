package lyc.compiler.files;

import lyc.compiler.constants.Comparadores;
import lyc.compiler.constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AsmCodeGenerator implements FileGenerator {

    private Map<String, String> operadores = Map.ofEntries(
            Map.entry("+","FADD"),
            Map.entry("-","FSUB"),
            Map.entry("*","FMUL"),
            Map.entry("/","FDIV")
    );

    private Map<String,String> comparadores = Map.ofEntries(
            Map.entry("BNE","JNE"),
            Map.entry("BLT","JNAE"),
            Map.entry("BLE","JNA"),
            Map.entry("BGT","JA"),
            Map.entry("BGE","JAE")
    );

    public AsmCodeGenerator(TercetoCodigoIntermedio intermediateCode) {
        this.intermediateCode = intermediateCode;
    }

    private Set<String> variablesAuxiliares = new HashSet<String>();

    private TercetoCodigoIntermedio intermediateCode;

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        String programa = generarPrograma();
        fileWriter.write(generarData() + programa);
    }

    private String generarData() {
        HashMap<String, Simbolo> tablaDeSimbolos = SymbolTableGenerator.getInstance().getTablaDeSimbolos();
        String data = ".MODEL  LARGE\n.386\n.STACK 200h\n\n";
        data += ".DATA\n";
        for (Map.Entry<String, Simbolo> entry : tablaDeSimbolos.entrySet()) {
            String nombre = entry.getKey();
            Simbolo simbolo = entry.getValue();

            if(simbolo.getTipoDato().equals("Cadena")) {
                if(nombre.startsWith("_")){
                    data += nombre + "\t" + "db " + simbolo.getValor() + ",'$'," + Integer.valueOf(simbolo.getLongitud()) + " dup (?)\n";
                } else {
                    data += nombre + "\t" + "db " + Constants.MAX_LENGTH + " dup (?)\n";
                }
            } else {
                if(simbolo.getValor() == null)
                    data += nombre + "\t" + "dd ? \n";
                else {
                    String valor = simbolo.getValor();
                    if(simbolo.getTipoDato() == "Entero")
                        valor += ".0";
                    data += nombre + "\t" + "dd " + valor + "\n";
                }
            }
        }

        for (String auxiliar : variablesAuxiliares) {
            data += auxiliar + "\t" + "dd ? \n";
        }

        return data + "\n";
    }

    public boolean esOperacion(String operando) {
        return operando == "+" || operando == "-" || operando == "*" || operando == "/";
    }

    private String generarPrograma() {
        String programa = ".CODE\nSTART:\nmov AX,@DATA\nmov DS,AX\nmov es,ax\n\n";

        for (Terceto terceto : intermediateCode.getTercetos()) {
            String operando1 = terceto.operando1;

            if(esOperacion(operando1)){
                programa += operadores.get(operando1) + "\n";
            } else if(operando1 == "=")
                programa += generarAsignacion(terceto);
            else if(operando1 == "READ")
                programa += operando1 + " " + obtenerAux(terceto.operando2) + "\n";
            else if(operando1 == "WRITE")
                programa += operando1 + " " + obtenerAux(terceto.operando2) + "\n";
            else if(operando1 == "CMP")
                programa += generarComparacion(terceto);
            else if(comparadores.containsKey(operando1))
                programa += generarSaltoCondicional(terceto);
            else if(operando1 == "BI")
                programa += "JMP " + intermediateCode.obtenerTerceto(terceto.operando2).operando1 + "\n";
            else if(operando1.startsWith("%salto%")){
                programa += operando1 + "\n";
                System.out.println(operando1);
            } else {
                // Es una constante o variable.
                programa += cargarValor(terceto);
            }
        }

        programa += "\nmov ax, 4C00h\nint 21h\nEND START";

        return programa;
    }

    private String generarAsignacion(Terceto terceto) {
        String asignacion = "FLD " + obtenerAux(terceto.operando3) + "\n";
        asignacion += "FST " + obtenerAux(terceto.operando2) + "\n";
        return asignacion;
    }

    private String cargarValor(Terceto terceto) {
        String operando1 = terceto.operando1;
        try{
            operando1 = "_" + String.valueOf(Integer.parseInt(operando1));
            // is an integer!
        } catch (NumberFormatException e) {
        }
        String valor = "FLD " + operando1 + "\n";
        valor += "FST " + obtenerAux(intermediateCode.obtenerPosicionTerceto(terceto)) + "\n";
        return valor;
    }

    private String generarComparacion(Terceto terceto) {
        String comparacion = "FLD " + obtenerAux(terceto.operando2) + "\n";
        comparacion += "FLD " + obtenerAux(terceto.operando3) + "\n";
        comparacion += "fxch\n";
        comparacion += "fcom\n";
        comparacion += "fstsw ax\n";
        comparacion += "sahf\n";
        return comparacion;
    }

    private String generarSaltoCondicional(Terceto terceto) {
        String salto = comparadores.get(terceto.operando1) + " " + intermediateCode.obtenerTerceto(terceto.operando2).operando1 + "\n";
        return salto;
    }

    private String obtenerAux(int tercetoId) {
        String aux = "@aux" + String.valueOf(tercetoId);
        variablesAuxiliares.add(aux);
        return aux;
    }
}