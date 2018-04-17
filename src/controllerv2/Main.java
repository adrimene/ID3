/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerv2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Adrián-Trabajo
 */
public class Main {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        ArrayList<Integer> lista_ejemplos = new ArrayList<>();
        ArrayList<Node> lista_atributos = new ArrayList<>();
        
        try {
            BufferedReader in = new BufferedReader(new FileReader("AtributosJuego.txt"));
            String line = in.readLine();
            String[] atributos = line.split(",");
            for (String node : atributos) {
                Node n = new Node(node);
                lista_atributos.add(n);
            }
            in.close();
            
            in = new BufferedReader(new FileReader("Juego.txt"));
            line = in.readLine();
            int idx = 0;
            while(line != null) {
                String[] ejemplos = line.split(",");
                for (int i = 0; i < ejemplos.length; i++) {
                    lista_atributos.get(i).addValue(new Value(ejemplos[i], ejemplos[ejemplos.length-1], idx));
                }
                lista_ejemplos.add(idx);
                line = in.readLine();
                idx++;
            }
            in.close();
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Fichero no encontrado","ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "No se ha leído el fichero correctamente","ERROR",JOptionPane.ERROR_MESSAGE);
        }  
        
        Node result = lista_atributos.get(lista_atributos.size()-1);
        lista_atributos.remove(lista_atributos.size()-1);
        
        ID3 id3 = new ID3(result);
        
        Node n;
        try {
            n = id3.id3(lista_ejemplos, lista_atributos);
            System.out.println(n);
        } catch (AttributesVoidException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
