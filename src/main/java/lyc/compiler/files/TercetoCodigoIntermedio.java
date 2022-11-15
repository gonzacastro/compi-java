package lyc.compiler.files;

import lyc.compiler.constants.TipoDePuntero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TercetoCodigoIntermedio {

    private static TercetoCodigoIntermedio instance;
    private ArrayList<Terceto> tercetos;
    private HashMap<TipoDePuntero,Integer> punteros;
    private Stack<Integer> pila;
    private Stack<String> pilaVariables;
    public String ultimoComparadorLeido = "";

    public static TercetoCodigoIntermedio getInstance() {
        if (instance == null) {
            instance = new TercetoCodigoIntermedio();
        }
        return instance;
    }

    public TercetoCodigoIntermedio() {
        this.tercetos = new ArrayList<Terceto>();
        this.punteros = new HashMap<TipoDePuntero,Integer>();
        this.pila = new Stack<Integer>();
        this.pilaVariables = new Stack<String>();
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

    public void actualizarTerceto(Integer idTerceto, Integer... operandos) {
        Integer operando2 = operandos.length > 0 ? operandos[0] : null;
        Integer operando3 = operandos.length > 1 ? operandos[1] : null;

        Terceto terceto = tercetos.get(idTerceto);
        terceto.operando2 = operando2;
        terceto.operando3 = operando3;
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

    public Integer obtenerCantidadTercetos() {
        return tercetos.size();
    }

    public String mostrarTercetos() {
        int idTerceto = 0;
        String tercetos = "";
        
        for (Terceto terceto : this.tercetos) {
            System.out.println("[" +String.valueOf(idTerceto) + "] " + terceto.toString());
            tercetos = tercetos + "[" +String.valueOf(idTerceto) + "] " + terceto.toString() + "\n";
            idTerceto++;
        }
        return tercetos;
    }

    public void apilarVariable(String id) { pilaVariables.push(id); }

    public String desapilarVariable() { return pilaVariables.pop(); }

    public Boolean pilaVariablesVacia() { return pilaVariables.isEmpty(); }

}
