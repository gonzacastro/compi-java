package lyc.compiler.files;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Terceto {

    public String operando1;
    public Integer operando2 = null;
    public Integer operando3 = null;

    public Terceto(String operando1) {
        this.operando1 = operando1;
    }

    public Terceto(String operando1, Integer operando2) {
        this.operando1 = operando1;
        this.operando2 = operando2;
    }

    public Terceto(String operando1, Integer operando2, Integer operando3) {
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operando3 = operando3;
    }

    @Override
    public String toString() {
        return "(" + operando1 + ", " + operando2 + ", " + operando3 + ')';
    }
}