/*
 * VentanaDefinirEstructura.java
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
import estructural.Seccion;
import gui.panelesVentDefinirEstruct.PanelColumnas;
import gui.panelesVentDefinirEstruct.PanelEntrepisos;
import gui.panelesVentDefinirEstruct.PanelMarcosX;
import gui.panelesVentDefinirEstruct.PanelMarcosZ;
import gui.panelesVentDefinirEstruct.PanelNodos;
import gui.panelesVentDefinirEstruct.PanelVigas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class VentanaDefinirEstructura extends JFrame{
    private static final int POSICION_X = 200;
    private static final int POSICION_Y = 150;
    private static final int ANCHO = 600;
    private static final int ALTO = 420;
    
    private JButton botonCerrar;
    private JTabbedPane fichasGenerales;
    
    protected PanelNodos estePanelNodos;
    protected PanelVigas estePanelVigas;
    protected PanelColumnas estePanelColumnas;
    protected PanelMarcosX estePanelMarcosX;
    protected PanelMarcosZ estePanelMarcosZ;
    protected PanelEntrepisos estePanelEntrepisos;
    
    /** Creates a new instance of VentanaDefinirEstructura */
    public VentanaDefinirEstructura() {
        //Se definen los parametros generales de la ventana "Definir Estructura"
        super("Definir Estructura");
        Main.getAplicacion().setEnabled(false);
        this.setBounds(this.POSICION_X,this.POSICION_Y,this.ANCHO,this.ALTO); 
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        
        //Se define el panel de fichas
        fichasGenerales = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        fichasGenerales.setBounds(0,0,(this.ANCHO-10),(this.ALTO-50) );
        
        this.estePanelNodos = new PanelNodos();
        this.estePanelVigas = new PanelVigas();
        this.estePanelColumnas = new PanelColumnas();
        this.estePanelMarcosX = new PanelMarcosX();
        this.estePanelMarcosZ = new PanelMarcosZ();
        this.estePanelEntrepisos = new PanelEntrepisos();
        //Agregacion de todos los paneles al panel de fichas
        fichasGenerales.addTab("Nodos",new ImageIcon("NO DISPONIBLE"),this.estePanelNodos,
                "Defina en este panel la posicion en el espacio de los nodos de la estructura");
        fichasGenerales.addTab("Columnas",new ImageIcon("NO DISPONIBLE"),this.estePanelColumnas,
                "Defina en este panel los nodos que delimitan a las columnas, asi como las propiedades de las mismas");
        fichasGenerales.addTab("Vigas",new ImageIcon("NO DISPONIBLE"),this.estePanelVigas,
                "Defina en este panel los nodos que delimitan a las vigas, asi como las propiedades de las mismas");
        fichasGenerales.addTab("Marcos en X",new ImageIcon("NO DISPONIBLE"),this.estePanelMarcosX,
                "Defina en este panel las vigas y columnas que conforman a los marcos de entrepiso en X");
        fichasGenerales.addTab("Marcos en Z",new ImageIcon("NO DISPONIBLE"),this.estePanelMarcosZ,
                "Defina en este panel las vigas y columnas que conforman a los marcos de entrepiso en Z");
        fichasGenerales.addTab("Entrepisos",new ImageIcon("NO DISPONIBLE"),this.estePanelEntrepisos,
                "Defina en este panel los marcos que conforman a los entrepisos");
        
//--------------------------------------------------------------------------------------------------------------        
        //Se implementan los comportamientos para actualizar los campos dependientes de otras
        //fichas, por ejemplo el combobox de los nodos en las fichas vigas y  columnas depende de
        //la ficha de nodos...
//--------------------------------------------------------------------------------------------------------------        
        //Ficha nodos indice 0
        fichasGenerales.getComponentAt(0).addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
                //JOptionPane.showMessageDialog(null,"se ha mostrado la ficha panel de nodos");
            }
            public void componentHidden(ComponentEvent e) {
                //JOptionPane.showMessageDialog(null,"se ha ocultado la ficha panel de nodos");
            }
        } );
//--------------------------------------------------------------------------------------------------------------        
        //Ficha columnas indice 1
        fichasGenerales.getComponentAt(1).addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
                //Generamos un arreglo con los nombres de los nodos para que aparezcan en el combo
                String[] nombresNodos = new String[512];    
                Seccion[] arrTempSecc = abrirArchivoSeccion();//se genera este arreglo para eficientar al no llamar al metodo abrirArchivoSeccion en el ciclo
                Material[] arrTempMat = abrirArchivoMaterial();
                for(int i=0;i<Main.getArchivoDeTrabajo().getNodosDeArchivo().length;i++){
                    if( (Main.getArchivoDeTrabajo().getNodosDeArchivo()[i]!=null) 
                    &&(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador().compareTo("Nodo sin nombre")!=0)  ){
                        nombresNodos[i] = Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador();
                    }
                }                
                String[] nombresSecciones = new String[512];                
                for(int i=0;i<512;i++){
                    if(arrTempSecc[i]!=null){
                        nombresSecciones[i] = arrTempSecc[i].getIdentificador();
                    }
                }
                String[] nombresMateriales = new String[512];
                for(int i=0;i<512;i++){
                    if(arrTempMat[i]!=null){
                        nombresMateriales[i] = arrTempMat[i].getIdentificador();
                    }                    
                }
                

                //Generamos los comboBox
                JComboBox comboNodoA = new JComboBox(nombresNodos);
                estePanelColumnas.getColumnasDeTabla()[1].setCellEditor( new DefaultCellEditor(comboNodoA) );
                
                JComboBox comboNodoB = new JComboBox(nombresNodos);
                estePanelColumnas.getColumnasDeTabla()[2].setCellEditor( new DefaultCellEditor(comboNodoB) );
                
                JComboBox comboSeccion = new JComboBox(nombresSecciones);
                estePanelColumnas.getColumnasDeTabla()[3].setCellEditor( new DefaultCellEditor(comboSeccion) );
                
                JComboBox comboMaterial = new JComboBox(nombresMateriales);
                estePanelColumnas.getColumnasDeTabla()[4].setCellEditor( new DefaultCellEditor(comboMaterial) );
                
                estePanelColumnas.llenarTablaColumnas();
                
            }
            public void componentHidden(ComponentEvent e) {
            }
        } );
//--------------------------------------------------------------------------------------------------------------        
        //Ficha vigas indice 2
        fichasGenerales.getComponentAt(2).addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
                //Generamos un arreglo con los nombres de los nodos para que aparezcan en el combo
                String[] nombresNodos = new String[512];    
                Seccion[] arrTempSecc = abrirArchivoSeccion();//se genera este arreglo para eficientar al no llamar al metodo abrirArchivoSeccion en el ciclo
                Material[] arrTempMat = abrirArchivoMaterial();
                for(int i=0;i<Main.getArchivoDeTrabajo().getNodosDeArchivo().length;i++){
                    if( (Main.getArchivoDeTrabajo().getNodosDeArchivo()[i]!=null) 
                    &&(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador().compareTo("Nodo sin nombre")!=0)  ){
//                    &&(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador()!="Nodo sin nombre")  ){
                        nombresNodos[i] = Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador();
                    }
                }                
                String[] nombresSecciones = new String[512];                
                for(int i=0;i<512;i++){
                    if(arrTempSecc[i]!=null){
                        nombresSecciones[i] = arrTempSecc[i].getIdentificador();
                    }
                }
                String[] nombresMateriales = new String[512];
                for(int i=0;i<512;i++){
                    if(arrTempMat[i]!=null){
                        nombresMateriales[i] = arrTempMat[i].getIdentificador();
                    }                    
                }
                

                //Generamos los comboBox
                JComboBox comboNodoA = new JComboBox(nombresNodos);
                estePanelVigas.getColumnasDeTabla()[1].setCellEditor( new DefaultCellEditor(comboNodoA) );
                
                JComboBox comboNodoB = new JComboBox(nombresNodos);
                estePanelVigas.getColumnasDeTabla()[2].setCellEditor( new DefaultCellEditor(comboNodoB) );
                
                JComboBox comboSeccion = new JComboBox(nombresSecciones);
                estePanelVigas.getColumnasDeTabla()[3].setCellEditor( new DefaultCellEditor(comboSeccion) );
                
                JComboBox comboMaterial = new JComboBox(nombresMateriales);
                estePanelVigas.getColumnasDeTabla()[4].setCellEditor( new DefaultCellEditor(comboMaterial) );
                
                estePanelVigas.llenarTablaVigas();
            }
            public void componentHidden(ComponentEvent e) {
            }
        } );
//--------------------------------------------------------------------------------------------------------------        
        //Ficha marcos x indice3
        fichasGenerales.getComponentAt(3).addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
//                JOptionPane.showMessageDialog(null,"Para conocer informacion relativa a los marcos" +
//                        "\ngenerados, se debe hacer click en las casillas rotuladas: \"Informacion\"","AVISO",
//                        JOptionPane.INFORMATION_MESSAGE);
                estePanelMarcosX.llenarTablaMarcos();
            }
            public void componentHidden(ComponentEvent e) {
            }
        } );
//--------------------------------------------------------------------------------------------------------------        
        //Ficha marcos z indice 4
        fichasGenerales.getComponentAt(4).addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
//                JOptionPane.showMessageDialog(null,"Para conocer informacion relativa a los marcos" +
//                        "\ngenerados, se debe hacer click en las casillas rotuladas: \"Informacion\"","AVISO",
//                        JOptionPane.INFORMATION_MESSAGE);
                estePanelMarcosZ.llenarTablaMarcos();
            }
            public void componentHidden(ComponentEvent e) {
            }
        } );
//--------------------------------------------------------------------------------------------------------------        
//--------------------------------------------------------------------------------------------------------------                
        //Agregacion del panel de fichas
        this.add(fichasGenerales);
        
        //Agregacion de boton de cerrado accesible desde toda la clase y con metodos de acceso
        this.botonCerrar = new JButton("Cerrar");
        this.botonCerrar.setMnemonic('C');
        this.botonCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.getAplicacion().setVisible(true);
                Main.getAplicacion().setEnabled(true);
            }
        });
        this.botonCerrar.setBounds((this.ANCHO-90),(this.ALTO-50),80,25);
        this.add(this.botonCerrar);
        
        //Se hace visible esta ventana al final del constructor
        this.setVisible(true);
    }

    public static int getPOSICION_X() {
        return POSICION_X;
    }

    public static int getPOSICION_Y() {
        return POSICION_Y;
    }

    public static int getANCHO() {
        return ANCHO;
    }

    public static int getALTO() {
        return ALTO;
    }

    public JButton getBotonCerrar() {
        return botonCerrar;
    }

    public void setBotonCerrar(JButton cerrar) {
        this.botonCerrar = cerrar;
    }        
    
    public static Seccion[] abrirArchivoSeccion(){        
        Seccion[] arrSecciones = new Seccion[512];
        File secciones = new File("lib//registros//secciones.dat");
        FileInputStream fisSec;                        
        if(secciones.isFile() && secciones.exists()){                            
            try {
                fisSec = new FileInputStream(secciones);
                ObjectInputStream ois = new ObjectInputStream(fisSec);
                int sizeOfFile = sizeOfFile("lib//registros//secciones.dat");
                for(int i=0;i<sizeOfFile;i++){
                    try {
                        arrSecciones[i] = (Seccion)ois.readObject();
                    } catch (EOFException endOfFileException) {
                        JOptionPane.showMessageDialog(null,"Archivo corrupto","Error de archivo",JOptionPane.ERROR_MESSAGE);
                        return null;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }                        
                }
       
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            
        }else{
            JOptionPane.showMessageDialog(null,"No se encuentra el archivo \"secciones.dat\" ",
                    "ARCHIVO NO EXISTENTE",JOptionPane.ERROR_MESSAGE);
            
            JOptionPane.showMessageDialog(null,"En tanto no genere el archivo en el menu registro->secciones, " +
                    "\nno se podra definir la estructura","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);            
        }                
        return arrSecciones;
    }
    
    public static Material[] abrirArchivoMaterial(){       
        Material[] arrMateriales = new Material[512];
        File materiales = new File("lib//registros//materiales.dat");
        FileInputStream fisMat;                        
        if(materiales.isFile() && materiales.exists()){                            
            try {
                fisMat = new FileInputStream(materiales);
                ObjectInputStream ois = new ObjectInputStream(fisMat);
                int sizeOfFile = sizeOfFile("lib//registros//materiales.dat");
                for(int i=0;i<sizeOfFile;i++){
                    try {
                        arrMateriales[i] = (Material)ois.readObject();
                    } catch (EOFException endOfFileException) {
                        JOptionPane.showMessageDialog(null,"Archivo corrupto","Error de archivo",JOptionPane.ERROR_MESSAGE);
                        return null;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }                        
                }
       
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            
        }else{
            JOptionPane.showMessageDialog(null,"No se encuentra el archivo \"materiales.dat\" ",
                    "ARCHIVO NO EXISTENTE",JOptionPane.ERROR_MESSAGE);
            
            JOptionPane.showMessageDialog(null,"En tanto no genere el archivo en el menu registro->materiales, " +
                    "\nno se podra definir la estructura","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);            
        }                
        return arrMateriales;
    }
    
    private static int sizeOfFile(String nombreArchivo){   
        
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
