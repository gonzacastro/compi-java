package lyc.compiler.files;

import lyc.compiler.constants.TipoDePuntero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TercetoCodigoIntermedio {

    private ArrayList<Terceto> tercetos;
    private HashMap<TipoDePuntero,Terceto> punteros;
    private Stack<Terceto> pila;

    public TercetoCodigoIntermedio() {
        this.tercetos = new ArrayList<Terceto>();
        this.punteros = new HashMap<TipoDePuntero,Terceto>();
        this.pila = new Stack<Terceto>();
    }

    public Terceto insertarTerceto(TipoDePuntero puntero, String operando1, Terceto... operandos) {
        Terceto operando2 = operandos.length > 0 ? operandos[0] : null;
        Terceto operando3 = operandos.length > 1 ? operandos[1] : null;

        Terceto terceto = new Terceto(operando1, operando2, operando3);

        tercetos.add(terceto);

        if (puntero != null) {
            punteros.put(puntero, terceto);
        }

        System.out.println(terceto.toString());

        return terceto;
    }

    public void actualizarPuntero(TipoDePuntero punteroNuevo, TipoDePuntero punteroAnterior) {
        punteros.put(punteroNuevo, punteros.remove(punteroAnterior));
    }

    public void apilarTerceto(Terceto terceto) {
        pila.push(terceto);
    }

    public Terceto desapilarTerceto() {
        return pila.pop();
    }

    public ArrayList<Terceto> getTercetos() {
        return tercetos;
    }

    public Terceto obtenerTercetoDePuntero(TipoDePuntero puntero) {
        return punteros.get(puntero);
    }

    public void mostrarTercetos() {
        for (Terceto terceto : this.tercetos)
            System.out.println(terceto.toString());
    }
}
