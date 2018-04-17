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

    private Node result;
    
    ID3(Node result) {
        this.result = result;
    }

    Node id3(ArrayList<Integer> lista_ejemplos, ArrayList<Node> lista_atributos) throws AttributesVoidException {
        if(lista_ejemplos.isEmpty())
            return new Node("No hay suficientes ejemplos");
        
        if(this.result.allValuesPositive(lista_ejemplos)) {
            return new Node("Si");
        }
        
        if(this.result.allValuesNegative(lista_ejemplos)) {
            return new Node("No");
        }
        
        if(lista_atributos.isEmpty())
            throw new AttributesVoidException();
        
        Node best = findBest(lista_ejemplos, lista_atributos);
        ArrayList<String> differentValues = best.getDifferentValues(lista_ejemplos);
        
        for(int i = 0; i < differentValues.size(); i++) {
            ArrayList<Integer> ejemplos_restantes;
            ArrayList<Node> atributos_restantes = lista_atributos;
            
            ejemplos_restantes = best.find(differentValues.get(i), lista_ejemplos);
            
            atributos_restantes.remove(best);
            
            Node child = id3(ejemplos_restantes, atributos_restantes);
            child.setFather(best, differentValues.get(i));
            best.addChild(child);
        }
        
        return best;
    }

    private Node findBest(ArrayList<Integer> lista_ejemplos, ArrayList<Node> lista_atributos) {
        Node sol = lista_atributos.get(0);
        for(int i = 1; i < lista_atributos.size(); i++) {
            float s = sol.merit(lista_ejemplos);
            float n = lista_atributos.get(i).merit(lista_ejemplos);
            if(s > n)
                sol = lista_atributos.get(i);
        }
        return sol;
    }
}
