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

    /**
     * @name Nombre del valor.
     * @positive Contiene si el resultado asociado a este valor es positivo o no.
     * @index Índice en la lista original del valor.
     */
    private final String name;
    private final boolean positive;
    private final int index;
    
    /**
     * Factoría de Value.
     * @param name Nombre del valor.
     * @param positive Resultado asociado.
     * @param index Índice en la lista actual.
     */
    public Value(String name, String positive, int index) {
        this.name = name;
        this.positive = (positive.equalsIgnoreCase("Si"));
        this.index = index;
    }
    
    /**
     * Comprueba si el valor es equivalente al dado en función del nombre.
     * @param name Nombre con el que se compara el valor actual.
     * @return Devuelve si el nombre del valor y el dado son iguales.
     */
    public boolean equalsByName(String name) {
        return this.name.equals(name);
    }

    /**
     * Getter de name.
     * @return Devuelve el nombre.
     */
    String getName() {
        return this.name;
    }

    /**
     * Getter de index.
     * @return Devuelve el índice.
     */
    Integer getIndex() {
        return this.index;
    }
    
    /**
     * Getter de positive.
     * @return Devuelve la postividad.
     */
    boolean getPositive() {
        return this.positive;
    }
}
