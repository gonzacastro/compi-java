package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;

public class IntermediateCodeGenerator implements FileGenerator {

    private TercetoCodigoIntermedio intermediateCode;
    private static IntermediateCodeGenerator intermediateCodeGenerator;
    private IntermediateCodeGenerator() {
        this.intermediateCode = null;
    }

    public static IntermediateCodeGenerator getInstance() {
        if(intermediateCodeGenerator == null) {
            intermediateCodeGenerator = new IntermediateCodeGenerator();
        }
        return intermediateCodeGenerator;
    }

    public void setIntermediateCode(TercetoCodigoIntermedio intermediateCode) {
        this.intermediateCode = intermediateCode;
    }
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write(this.intermediateCode.mostrarTercetos());
    }
}