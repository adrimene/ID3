/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerv2;

import java.util.ArrayList;

/**
 *
 * @author Adrián-Trabajo
 */
class ID3 {

    /**
     * result Nodo que contiene los valores del resultado.
     */
    private final Node result;
    
    /**
     * Factoría del algoritmo. Solo se requiere del nodo resultado, que no 
     * participa en el propio algoritmo.
     * @param result Nodo que se dará a result.
     */
    ID3(Node result) {
        this.result = result;
    }

    /**
     * Código del algoritmo ID3.
     * @param lista_ejemplos Ejemplos que quedan para la ejecución del algoritmo
     * @param lista_atributos Atributos que quedan para la ejecución del 
     *                          algoritmo.
     * @return Atributo best, actualizado con la lista de sus hijos.
     * @throws AttributesVoidException Excepción que se lanza en el caso de
     *          quedarse sin atributos.
     */
    Node id3(ArrayList<Integer> lista_ejemplos, ArrayList<Node> lista_atributos) 
            throws AttributesVoidException {
        // Casos en los que la rama termina y se devuelve un nodo solución.
        if(lista_ejemplos.isEmpty())
            return new Node("No hay suficientes ejemplos");
        
        if(this.result.allValuesPositive(lista_ejemplos)) {
            return new Node("Si");
        }
        
        if(this.result.allValuesNegative(lista_ejemplos)) {
            return new Node("No");
        }
        
        // Excepción en caso de error.
        if(lista_atributos.isEmpty())
            throw new AttributesVoidException();
        
        /** En caso de que la rama siga abriendose. Busca el mejor nodo y 
         *  sus distintos valores.
         */
        Node best = findBest(lista_ejemplos, lista_atributos);
        ArrayList<String> differentValues = best.getDifferentValues(lista_ejemplos);
        
        /**
         * Por cada valor, se extiende el arbol, reduciendo la lista de atributos
         * y la lista de ejemplos restantes. 
         * Se inicia un nuevo ID3 con las nuevas listas.
         * Se actualiza la lisa de hijos del nodo mejor y se coloca el padre de
         * estos nodos hijos.
         */
        lista_atributos.remove(best);
        
        for(int i = 0; i < differentValues.size(); i++) {
            ArrayList<Integer> ejemplos_restantes;
            
            ejemplos_restantes = best.find(differentValues.get(i), lista_ejemplos);
            
            Node child = id3(ejemplos_restantes, lista_atributos);
            
            child.setFather(best, differentValues.get(i));
            best.addChild(child);
        }
        
        /**
         * Se devuleve el nodo mejor con la información actualizada para 
         * actualizarlo en el nivel superior.
         */
        return best;
    }

    /**
     * Se busca el nodo que minimice el mérito.
     * @param lista_ejemplos Se le pasa la lista de los ejemplos restantes.
     * @param lista_atributos Se le pasa la lista de los nodos restantes.
     * @return Se devuelve el Node con mínimo mérito.
     */
    private Node findBest(ArrayList<Integer> lista_ejemplos, 
            ArrayList<Node> lista_atributos) {
        Node sol = lista_atributos.get(0);
        for(int i = 1; i < lista_atributos.size(); i++) {
            if(sol.merit(lista_ejemplos) > lista_atributos.get(i).merit(lista_ejemplos))
                sol = lista_atributos.get(i);
        }
        return sol;
    }
}
