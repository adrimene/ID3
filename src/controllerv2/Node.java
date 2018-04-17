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

    private String name;
    private ArrayList<Value> values;
    private Node father;
    private String relation;
    private ArrayList<Node> childrens;
        
    Node(String name) {
        this.name = name;
        values = new ArrayList<>();
        father = null;
        relation = "";
        childrens = new ArrayList<>();
    }

    void addValue(Value value) {
        this.values.add(value);
    }

    /**
     * 
     * @param lista_ejemplos
     * @return 
     */
    boolean allValuesPositive(ArrayList<Integer> lista_ejemplos) {
        boolean allP = true;
        for(int i = 0; i < this.values.size(); i++) {
            if(this.values.get(i).getPositive() == false && 
                    lista_ejemplos.contains(values.get(i).getIndex())) {
                allP = false;
            }
        }
        return allP;
    }

    /**
     * 
     * @param lista_ejemplos
     * @return 
     */
    boolean allValuesNegative(ArrayList<Integer> lista_ejemplos) {
        boolean allN = true;
        for(int i = 0; i < this.values.size(); i++) {
            if(this.values.get(i).getPositive() == true && 
                    lista_ejemplos.contains(values.get(i).getIndex())) {
                allN = false;
            }
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
     * 
     * @param lista_ejemplos
     * @return Devuelve una lista de arrays con el nombre de los dierentes 
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
     * 
     * @param valueName
     * @param lista_ejemplos
     * @return 
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
     * 
     * @param lista_ejemplos
     * @return 
     */
    float merit(ArrayList<Integer> lista_ejemplos) {
        ArrayList<String> diffValues = getDifferentValues(lista_ejemplos);
        ArrayList<Integer> ai = new ArrayList<>();
        ArrayList<Integer> pi = new ArrayList<>();
        ArrayList<Integer> ni = new ArrayList<>();
        
        float merit = 0;
        
        for(int i = 0; i < diffValues.size(); i++) {
            ai.add(0); pi.add(0); ni.add(0);
            for(int j = 0; j < this.values.size(); j++) {
                if(this.values.get(j).equalsByName(diffValues.get(i)) && 
                        lista_ejemplos.contains(this.values.get(j).getIndex())) {
                    if(this.values.get(j).getPositive())
                        pi.set(i, pi.get(i)+1);
                    else
                        ni.set(i, ni.get(i)+1);
                    ai.set(i, ai.get(i)+1);
                }
            }
            merit += (((float) ai.get(i) / (float) lista_ejemplos.size()) * (infor((float)pi.get(i)/ai.get(i), (float)ni.get(i)/ai.get(i))));
        }
        return merit;
    }

    /**
     * 
     * @param pi
     * @param ni
     * @return 
     */
    private float infor(float pi, float ni) {
        float inforP, inforN;
        
        if(pi != 0)
            inforP = 0 - (pi * (float)(Math.log(pi)/Math.log(2)));
        else inforP = 0;
        if(ni != 0)
            inforN = 0 - (ni * (float)(Math.log(ni)/Math.log(2)));
        else inforN = 0;
        
        return inforP + inforN;
    }
    
    /**
     * 
     * @param child 
     */
    void addChild(Node child) {
        this.childrens.add(child);
    }

    /**
     * 
     * @param best
     * @param get 
     */
    void setFather(Node father, String relation) {
        this.father = father;
        this.relation = relation;
    }
    
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
