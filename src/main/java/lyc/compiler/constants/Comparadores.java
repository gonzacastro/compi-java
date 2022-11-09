package lyc.compiler.constants;

import java.util.HashMap;

public class Comparadores{
    public HashMap<String, String> opuestosComparadores;

    public Comparadores() {
        this.opuestosComparadores = new HashMap<String, String>();
        this.opuestosComparadores.put("BNE", "BE");
        this.opuestosComparadores.put("BE", "BNE");
        this.opuestosComparadores.put("BGE", "BLT");
        this.opuestosComparadores.put("BLT", "BGE");
        this.opuestosComparadores.put("BLE", "BGT");
        this.opuestosComparadores.put("BGT", "BLE");
    }

    public String obtenerOpuesto(String comparador){
        return this.opuestosComparadores.get(comparador);
    }
}
