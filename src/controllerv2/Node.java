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
class Node {

    /**
     * @name Nombre del nodo actual.
     * @values Lista de los valores que contiene el nodo.
     * @father Nodo padre del Nodo actual.
     * @relation Al imprimir, es la condición que tiene el nodo padre para que
     *          se de el nodo hijo.
     * @childrens Lista de los Nodos hijos a los que parten del nodo actual..
     */    
    private final String name;
    private final ArrayList<Value> values;
    private Node father;
    private String relation;
    private ArrayList<Node> childrens;
        
    /**
     * Factoría para la creación del Nodo. Solo requiere de un nombre.
     * Todos los demás valores se inicializarán como nulos.
     * @param name Nombre del Nodo actual. Es lo que se mostrará por pantalla.
     */
    Node(String name) {
        this.name = name;
        values = new ArrayList<>();
        father = null;
        relation = "";
        childrens = new ArrayList<>();
    }

    /**
     * Añade un valor al nodo actual.
     * Se utiliza al inicializar el nodo. A medida que se lee el fichero, se
     * van añadiendo valores en función de la posición.
     * @param value Values con el valor que se va a incluir.
     */
    void addValue(Value value) {
        this.values.add(value);
    }

    /**
     * Comprueba si todos los valores del nodo tienen una solución positiva.
     * @param lista_ejemplos Lista de los valores que se comprobarán para devolver 
     *                      el resultado. Solo importarán los Values contenidos 
     *                      en la lista
     * @return Devuelve un boolean dependiendo de la condición mencionada.
     */
    boolean allValuesPositive(ArrayList<Integer> lista_ejemplos) {
        boolean allP = true;
        for(int i = 0; i < this.values.size(); i++) {
            if(this.values.get(i).getPositive() == false && 
                    lista_ejemplos.contains(values.get(i).getIndex()))
                allP = false;
        }
        return allP;
    }

    /**
     * Comprueba si todos los valores del nodo tienen una solución negativa.
     * @param lista_ejemplos Lista de los valores que se comprobarán para devolver 
     *                      el resultado. Solo importarán los Values contenidos 
     *                      en la lista
     * @return Devuelve un boolean dependiendo de la condición mencionada.
     */
    boolean allValuesNegative(ArrayList<Integer> lista_ejemplos) {
        boolean allN = true;
        for(int i = 0; i < this.values.size(); i++) {
            if(this.values.get(i).getPositive() == true && 
                    lista_ejemplos.contains(values.get(i).getIndex()))
                allN = false;
        }
        return allN;
    }

    /**
     * 
     * @param lista_ejemplos
     * @return Devuelve una lista de arrays con el nombre de los dierentes 
     *          valores que puede tomar el nodo
     */
    public ArrayList<String> getDifferentValues(ArrayList<Integer> lista_ejemplos) {
        ArrayList<String> result = new ArrayList();
        for(Value v: values) {
            if(!result.contains(v.getName()) && 
                    lista_ejemplos.contains(v.getIndex()))
                result.add(v.getName());
        }
        return result;
    }

    /**
     * Recoge en una lista el nombre de los diferentes valores de un nodo.
     * @param lista_ejemplos Lista de los ejemplos restantes.
     * @return Devuelve una lista de arrays con el nombre de los diferentes 
     *          valores que puede tomar el nodo
     */
    public ArrayList<String> getDifferentValues() {
        ArrayList<String> result = new ArrayList();
        for(Value v: values) {
            if(!result.contains(v.getName()))
                result.add(v.getName());
        }
        return result;
    }

    /**
     * Recoge una lista con los índices cuyo valor sea el buscado.
     * @param valueName Nombre de valor que se está buscando.
     * @param lista_ejemplos Lista de los ejemplos restantes.
     * @return Devuelve la lista de enteros co los índices de los valores restantes.
     */
    ArrayList<Integer> find(String valueName, ArrayList<Integer> lista_ejemplos) {
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < this.values.size(); i++) {
            if(this.values.get(i).equalsByName(valueName) && 
                    lista_ejemplos.contains(this.values.get(i).getIndex()))
                result.add(this.values.get(i).getIndex());
        }
        return result;
    }

    /**
     * Calcula el mérito del nodo actual.
     * @param lista_ejemplos Lista de los valores restantes. 
     *                      Solo se considerarán los contenidos en esta lista.
     * @return El valor del mérito del Nodo actual. 
     */
    float merit(ArrayList<Integer> lista_ejemplos) {
        ArrayList<String> diffValues = getDifferentValues(lista_ejemplos);
        float ai;
        float pi;
        float ni;
        float merit = 0;
        
        for(int i = 0; i < diffValues.size(); i++) {
            ai = 0; pi = 0; ni = 0;
            for(int j = 0; j < this.values.size(); j++) {
                if(this.values.get(j).equalsByName(diffValues.get(i)) && 
                        lista_ejemplos.contains(this.values.get(j).getIndex())) {
                    if(this.values.get(j).getPositive())
                        pi++;
                    else
                        ni++;
                    ai++;
                }
            }
            merit += ((ai / lista_ejemplos.size()) * (infor(pi/ai, ni/ai)));
        }
        return merit;
    }

    /**
     * Calcula el infor de un valor del Nodo actual.
     * @param pi Porcentaje de positivos del valor.
     * @param ni Porcentaje de negativos del valor.
     * @return Devuelve el valor de infor.
     */
    private float infor(float pi, float ni) {
        float inforP, inforN;
        
        if(pi != 0)
            inforP = - (pi * (float)(Math.log(pi)/Math.log(2)));
        else inforP = 0;
        if(ni != 0)
            inforN = - (ni * (float)(Math.log(ni)/Math.log(2)));
        else inforN = 0;
        
        return inforP + inforN;
    }
    
    /**
     * Añade un Nodo a la lista de hijos.
     * @param child Nodo que se añadirá a la lista.
     */
    void addChild(Node child) {
        this.childrens.add(child);
    }

    /**
     * Coloca un Nodo como padre del actual.
     * @param father Nodo que se colocará como padre.
     * @param relation Nombre de la relación entre los dos. Se mostrará al 
     *                  imprimir el árbol.
     */
    void setFather(Node father, String relation) {
        this.father = father;
        this.relation = relation;
    }
    
    /**
     * Método sobreescrito que muestra por pantalla el arbol ID3.
     * Es un método recursivo que se irá abriendo hacia los hijos.
     * Tendrá estructura vertical.
     * @return String con los que se tiene que mostrar por pantalla.
     */
    @Override
    public String toString() {
        String data = "";
        
        ArrayList<String> valueNames = getDifferentValues();
        
        data += this.name;
        data += "\n";
        for(int j = 0; j < this.childrens.size(); j++) {
            for(int i = 0; i < getLevel(); i++) {
                data += "   |";
            }
            data += "\n";
            for(int i = 0; i < getLevel(); i++) {
                data += "   |";
            }
            data += valueNames.get(j);
            data += "\n";
            for(int i = 0; i < getLevel(); i++) {
                data += "   |";
            }
            data += "\n";
            for(int i = 0; i < getLevel(); i++) {
                if(j != this.childrens.size()-1 && i == getLevel() - 1)
                    data += "   ├";
                else if(i == getLevel() - 1)
                    data += "   └";
                else
                    data += "   |";
            }
            data += this.childrens.get(j).toString();
        }
        return data; 
    }
    
    /**
     * Comprueba cual es el nivel del Nodo.
     * Sirve para la impresión por consola del árbol.
     * @return Entero con el nivel del hijo.
     */
    public int getLevel() {
        int level = 1;
        Node temp = this;
        while(temp.father != null) {
            level++;
            temp = temp.father;
        }
        return level;
    }
}
