package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SymbolTableGenerator implements FileGenerator{

    private static SymbolTableGenerator instance;
    private static HashMap<String, Simbolo> tablaDeSimbolos;

    private SymbolTableGenerator() {
        this.tablaDeSimbolos = new HashMap<>();
    }

    public static SymbolTableGenerator getInstance() {
        if (instance == null) {
            instance = new SymbolTableGenerator();
        }
        return instance;
    }

    public static void add(Simbolo simbolo) {
        if (!tablaDeSimbolos.containsKey(simbolo.getNombre()))
            tablaDeSimbolos.put(simbolo.getNombre(), simbolo);
    }

    public static HashMap<String, Simbolo> getTablaDeSimbolos() {
        return tablaDeSimbolos;
    }

    public static void printTabla() {
        System.out.println("TABLA DE SIMBOLOS:\n");
        System.out.println(String.format("%-30s|%-30s|%-30s|%-30s\n", "NOMBRE", "TIPODATO", "VALOR", "LONGITUD"));
        tablaDeSimbolos.forEach(
                (key, value) -> System.out.println(value.toString())
        );
    }

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write(String.format("%-30s|%-30s|%-30s|%-30s\n", "NOMBRE", "TIPODATO", "VALOR", "LONGITUD"));
        tablaDeSimbolos.forEach(
            (key, simbolo) -> {
                try {
                    fileWriter.write(simbolo.toString() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }
}