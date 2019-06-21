/*
 * VentanaRegistroMaterial.java
 *
 * Created on 5 de diciembre de 2006, 01:30 PM
 *
 * Copyright (C) 2007  Edgar Bolaños Lujan
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *   
 *   contact: jergas_bolanos@hotmail.com
 */

package gui;

import estructural.Material;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class VentanaRegistroMaterial extends JFrame{
    private final int POSICION_X = 200;
    private final int POSICION_Y = 150;
    private final int ANCHO = 450;
    private final int ALTO = 300;
    private JTextField campoIdentificador,
                       campoModulo,
                       campoPesoVol;
    
    private File materiales;
    private Material[] materialesDeRegistro;
    
    /** Creates a new instance of VentanaRegistroMaterial */
    public VentanaRegistroMaterial() {
        //Definicion de parametros propios de la ventana
        super("Registro de Materiales");
        Main.getAplicacion().setEnabled(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(this.POSICION_X,this.POSICION_Y,this.ANCHO,this.ALTO);
        this.setLayout(null);
        
        //Agregacion de etiquetas
        this.add(crearEtiqueta("Identificador",50,45,100,30) );
        this.add(crearEtiqueta("Modulo de Elasticidad",50,75,130,30) );
        this.add(crearEtiqueta("Peso Volumetrico",50,105,100,30) );
        this.add(crearEtiqueta("Kg/cm2",280,80,40,20) );
        this.add(crearEtiqueta("Ton/m3",280,110,40,20) );
        
        //Agregacion de campos de texto con acceso desde cualquier parte de la clase
        campoIdentificador = this.crearCampo(170,50,150,20);        
        campoModulo = this.crearCampo(170,80,100,20);
        campoPesoVol = this.crearCampo(170,110,100,20);
        this.add(campoIdentificador);
        this.add(campoModulo);
        this.add(campoPesoVol);
        
        //Agregacion de icono        
        this.add(new PanelImagen("lib//imagenes//materiales.itr",50,140,120,120) );
        
        //Agregacion de botones
        this.add(crearBoton("Cerrar",'C',210,200,100,30,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.getAplicacion().setVisible(true);
                Main.getAplicacion().setEnabled(true);                
            }
        }) );
        
        materiales = new File("lib//registros//materiales.dat");
        this.add(crearBoton("Guardar registro",'G',210,160,150,30,new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    String id;
                    float mod;
                    float peso;
                    try{
                        id = campoIdentificador.getText();
                        mod = Float.parseFloat(campoModulo.getText());
                        peso = Float.parseFloat(campoPesoVol.getText());                                                               
                        if(materiales.isFile()&&materiales.exists()){  
                            
                            //recuperar los registros del archivo antes de sobregrabar en el mismo
                            leerObjetosArchivo("lib//registros//materiales.dat");
//                            /////////*********************TEMPORAL DE PRUEBA******************///////////
//                            String contenidoArr="";
//                            for(int i=0;i<10;i++){
//                                if(materialesDeRegistro[i]!=null){
//                                    contenidoArr = contenidoArr +"\n"+ materialesDeRegistro[i].getIdentificador();
//                                }                    
//                            }
//                            JOptionPane.showMessageDialog(null,contenidoArr);
//                            /////////*********************TEMPORAL DE PRUEBA******************///////////
                            try {
                                ////////////////////////////////////////////////////////
                                ObjectOutputStream oost = new ObjectOutputStream( new FileOutputStream(materiales) );
                                ////////////////////////////////////////////////////////
                                for(int i=0; i<200; i++){                                
                                    if(materialesDeRegistro[i]!=null){
                                        oost.writeObject(materialesDeRegistro[i]);
                                    }                                    
                                }
                                oost.writeObject(new Material(id,mod,peso));
                                //int siguienteIndice = calcularElementosNoNull();                            
                                //materialesDeRegistro[siguienteIndice] = new Material(id, mod, peso);
                                //oost.writeObject( materialesDeRegistro[siguienteIndice] );       
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex){
                                ex.printStackTrace();
                            }
                            campoIdentificador.setText("");
                            campoModulo.setText("");
                            campoPesoVol.setText("");

                        }else{
                            JOptionPane.showMessageDialog(null,"No se encuentra el archivo materiales.dat",
                                    "ARCHIVO NO EXISTENTE",JOptionPane.ERROR_MESSAGE);
                            int opcion = JOptionPane.showConfirmDialog(null,"Desea crear el archivo materiales.dat ?","?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            if(opcion == 0){        //Si el usuario pulsa SI
                                try {
                                    materiales.createNewFile();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }else if(opcion == 1){       //Si el usuario pulsa NO
                                JOptionPane.showMessageDialog(null,"En tanto no genere el archivo, " +
                                        "\nno se podran guardar los registros","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null,"Los campos modulo de elasticidad y peso volumetrico solo pueden" +
                                "\nrecibir valores de punto decimal","ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                    }
            }
        } ) );        
        
        
        this.setVisible(true);
    }
    
    private JLabel crearEtiqueta(String etiqueta,int posX,int posY,int ancho,int alto){
        JLabel retorno = new JLabel(etiqueta);
        retorno.setBounds(posX,posY,ancho,alto);
        return retorno;
    }
    
    private JTextField crearCampo(int posX,int posY,int ancho,int alto){
        JTextField retorno = new JTextField();
        retorno.setBounds(posX,posY,ancho,alto);
        return retorno;
    }
    
    private JButton crearBoton(String texto,char mnemonic,int posX,int posY,
            int ancho,int alto, ActionListener accion){
        JButton retorno = new JButton(texto);
        retorno.setMnemonic(mnemonic);
        retorno.setBounds(posX,posY,ancho,alto);
        retorno.addActionListener(accion);
        return retorno;
    }      
    
    private void leerObjetosArchivo(String nombreArchivo){
        FileInputStream fis = null;
        ObjectInputStream ois;
        int numDeObjetos = 0;
        numDeObjetos = this.sizeOfFile(nombreArchivo);        
        materialesDeRegistro = new Material[200];
        for(int i=0;i<200;i++){
            materialesDeRegistro[i] = null;
        }
        
        try {
            fis = new FileInputStream( new File(nombreArchivo) );
            ois = new ObjectInputStream( fis );
            for(int i=0; i<numDeObjetos;i++){
                materialesDeRegistro[i] = (Material) ois.readObject();                
            }            
            fis.close();
            ois.close();              
        } catch (EOFException ex) {
            ;//System.out.println("imprime esto si se alcanza el fin de archivo en leerObjetosArchivo");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
       
    }
    
    private int sizeOfFile(String nombreArchivo){   
        
        ObjectInputStream oiStream = null;
        try {
            FileInputStream fiStream = new FileInputStream(new File(nombreArchivo));
            oiStream = new ObjectInputStream(fiStream);
        } catch (EOFException ex){            
            int retorno = 0;
            return retorno;            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        boolean fin = false;
        int sizeOfFile = 0;
        while(!fin){
            try {
                oiStream.readObject();
                sizeOfFile++;
            } catch (EOFException eofex){
                fin = true; 
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return sizeOfFile;
    }
    
}
