package lyc.compiler.files;

import lyc.compiler.constants.TipoDePuntero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TercetoCodigoIntermedio {

    private ArrayList<Terceto> tercetos;
    private HashMap<TipoDePuntero,Integer> punteros;
    private Stack<Integer> pila;
    public String ultimoComparadorLeido = "";

    public TercetoCodigoIntermedio() {
        this.tercetos = new ArrayList<Terceto>();
        this.punteros = new HashMap<TipoDePuntero,Integer>();
        this.pila = new Stack<Integer>();
    }

    public Integer insertarTerceto(TipoDePuntero puntero, String operando1, Integer... operandos) {
        Integer operando2 = operandos.length > 0 ? operandos[0] : null;
        Integer operando3 = operandos.length > 1 ? operandos[1] : null;

        Terceto terceto = new Terceto(operando1, operando2, operando3);

        tercetos.add(terceto);
        Integer idTerceto = tercetos.size() - 1;

        if (puntero != null) {
            punteros.put(puntero, idTerceto);
        }

        System.out.println(terceto.toString());

        return idTerceto;
    }

    public void actualizarPuntero(TipoDePuntero punteroNuevo, TipoDePuntero punteroAnterior) {
        punteros.put(punteroNuevo, punteros.remove(punteroAnterior));
    }

    public void apilarTerceto(Integer idTerceto) {
        pila.push(idTerceto);
    }

    public Integer desapilarTerceto() {
        return pila.pop();
    }

    public ArrayList<Terceto> getTercetos() {
        return tercetos;
    }

    public Integer obtenerTercetoDePuntero(TipoDePuntero puntero) {
        return punteros.get(puntero);
    }

    public Integer obtenerPosicionTerceto(Terceto terceto) {
        return tercetos.indexOf(terceto);
    }

    public void mostrarTercetos() {
        for (Terceto terceto : this.tercetos)
            System.out.println(terceto.toString());
    }
}
