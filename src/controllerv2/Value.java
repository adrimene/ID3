/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerv2;

/**
 *
 * @author Adrián-Trabajo
 */
class Value {

    private String name;
    private boolean positive;
    private int index;
    
    public Value(String name, String positive, int index) {
        this.name = name;
        this.positive = (positive.equalsIgnoreCase("Si"));
        this.index = index;
    }
    
    public boolean equalsByName(String name) {
        return this.name.equals(name);
    }

    String getName() {
        return this.name;
    }

    Integer getIndex() {
        return this.index;
    }
    
    boolean getPositive() {
        return this.positive;
    }
}
