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
import java.io.InputStreamReader;
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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> lista_ejemplos = new ArrayList<>();
        ArrayList<Node> lista_atributos = new ArrayList<>();
        String file = "";
        
        try {
            System.out.println("Introduzca el nombre del fichero (con .txt) de los atributos");
            file = br.readLine();
            
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            String[] atributos = line.split(",");
            for (String node : atributos) {
                Node n = new Node(node);
                lista_atributos.add(n);
            }
            in.close();
            
            System.out.println("Introduzca el nombre del fichero (con .txt) de los ejemplos");
            file = br.readLine();
            
            System.out.println("\n");
            
            in = new BufferedReader(new FileReader(file));
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
        } catch(FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
        } catch (IOException ex) {
            System.out.println("No se ha leído el fichero correctamente");
        }  
        
        System.out.println("Pulse Enter para terminar...");
        
        try {
            br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
