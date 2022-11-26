package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;

public class AsmCodeGenerator implements FileGenerator {

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("TODO");
    }

    public String obtenerCabecera(){
        return ".CODE\nSTART:\nmov AX,@DATA\nmov DS,AX\nmov es,ax\n\n";
    }
}