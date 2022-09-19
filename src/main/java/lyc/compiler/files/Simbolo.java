package lyc.compiler.files;

import java.util.HashMap;

public class Simbolo {

    private String nombre;
    private String tipoDato;
    private String valor;
    private int longitud;

    public Simbolo(String nombre, String tipoDato, String valor, int longitud) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.valor = valor;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getValor() {
        return valor;
    }

    public int getLongitud() {
        return longitud;
    }

    private String toStringObject(Object o) {
        return o != null ? "" + o : "-";
    }

    @Override
    public String toString() {
        return String.format(
                "%-30s|%-30s|%-30s|%-30s", toStringObject(nombre), toStringObject(tipoDato), toStringObject(valor), toStringObject(longitud)
        );
    }
}