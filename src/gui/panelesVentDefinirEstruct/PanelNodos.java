/*
 * PanelNodos.java
 *
 * Created on 4 de febrero de 2007, 03:46 PM
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

package gui.panelesVentDefinirEstruct;

import estructural.Nodo;
import gui.PanelImagen;
import gui.VentanaDefinirEstructura;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class PanelNodos extends JPanel{
    
    private final String[] TITULOS = {"Nodo","X (m)","Z (m)","Y (m)"};
    private JTable tablaNodos = new JTable();
    private JFrame ventInfoNodos;
            
    /** Creates a new instance of PanelNodos */
    public PanelNodos() {
        super();
        this.setLayout(null);
        
        //Creacion de la tabla con base en la clase ModeloTablaGeneral de este paquete         
        tablaNodos.setModel( new ModeloTablaGeneral(512,4) );
//        tablaNodos.setSelectionBackground(Color.getHSBColor(.5836f,.7266f,.8125f));
        tablaNodos.setSelectionBackground(Color.LIGHT_GRAY);
        tablaNodos.setRowSelectionAllowed(true);
        
        TableColumn[] columnasDeTabla = new TableColumn[4];
        for(int i=0;i<4;i++){
            columnasDeTabla[i] = tablaNodos.getColumnModel().getColumn(i);
        }
        for(int i=0;i<4;i++){
            columnasDeTabla[i].setHeaderValue(this.TITULOS[i]);
            columnasDeTabla[i].setResizable(false);
        }
        
        //Generar un formato de campo de texto y asignarlo a la primera columna (Nodos)
        JTextField campoNodosNum = new JTextField();
        campoNodosNum.setBackground(Color.LIGHT_GRAY);
        campoNodosNum.setEditable(false);
        columnasDeTabla[0].setCellEditor(new DefaultCellEditor(campoNodosNum) );
        columnasDeTabla[0].setMaxWidth(50);
        
        //Creacion del panel con barras de desplazamiento para meter ahi la tabla
        JScrollPane scrPane = new JScrollPane();
        scrPane.setViewportView(tablaNodos);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPane.setBounds(0,0,
                (VentanaDefinirEstructura.getANCHO()-95),(VentanaDefinirEstructura.getALTO()-75) );
        
        //Agregacion de botones creados con el metodo crearBoton() de esta clase
        this.add(this.crearBoton("Aceptar",'A',
                (VentanaDefinirEstructura.getANCHO()-95),(20),
                (80),(25),accionAceptar() ));
        this.add(this.crearBoton("Borrar",'B',
                (VentanaDefinirEstructura.getANCHO()-95),(50),
                (80),(25),accionBorrar() ));
        
        //Agregacion del panel con barras de desplazamiento al PanelNodos osea esta clase
        this.add(scrPane);
        
        //Agregacion de la imagen haciendo uso de la clase PanelImagen del paquete gui
        this.add(new PanelImagen("lib//imagenes//nodo.itr",
                (VentanaDefinirEstructura.getANCHO()-95),(80),
                (80),(VentanaDefinirEstructura.getALTO()-155) ));//95 x 265 osea 95 x (420-155)
        
        //Se llena la tabla con los valores en el objeto archivoDeTrabajo
        for(int filas=0;filas<512;filas++){
            for(int cols=0;cols<4;cols++){
                String[][] tablaEnString = new String[512][4];
                if ( (Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas]!=null)
                &&(Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].getIdentificador().compareTo("Nodo sin nombre")!=0 ) ){
                    switch (cols){
//                        case 0:{
//                            JOptionPane.showMessageDialog(null,"col 1");
//                        }
                        case 1:{
                            tablaEnString[filas][1] = Float.toString(Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].getCordX());
                            tablaNodos.getModel().setValueAt(tablaEnString[filas][1],filas,1);
                        }
                        case 2:{
                            tablaEnString[filas][2] = Float.toString(Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].getCordZ());
                            tablaNodos.getModel().setValueAt(tablaEnString[filas][2],filas,2);
                        }
                        case 3:{
                            tablaEnString[filas][3] = Float.toString(Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].getCordY());
                            tablaNodos.getModel().setValueAt(tablaEnString[filas][3],filas,3);
                        }
                    }
                    
                }
            }
        }
        
        //Se hace visible este panel una vez que se le agrego todo
        this.setVisible(true);
    }
    
    private JButton crearBoton(String nombre, char mnemonic,int x,int y,int ancho,int alto,
            ActionListener accion){
        JButton retorno = new JButton(nombre);
        retorno.setMnemonic(mnemonic);
        retorno.setBounds(x,y,ancho,alto);
        retorno.addActionListener(accion);
        
        return retorno;
    }
    
    private ActionListener accionAceptar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //--------Se repasa toda la tabla para recoger los registros en el archivo--------                
                for(int filas=0;filas<512;filas++){
//                    if(Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].getIdentificador() != "Nodo sin nombre"){
                        boolean flagX = true, flagZ = true, flagY = true;
                        for(int columnas=1;columnas<4;columnas++){
                            if( ( (String) tablaNodos.getModel().getValueAt(filas,1)!="")&&
                                    ( (String) tablaNodos.getModel().getValueAt(filas,2)!="")&&
                                    ( (String) tablaNodos.getModel().getValueAt(filas,3)!="") ){
                                //Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas] = new Nodo();
                                String identificadorDeNodo = "Nodo "+(filas+1);
                                Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].setIdentificador(identificadorDeNodo);

                                try{
                                    Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].setCordX(
                                            Float.parseFloat((String) tablaNodos.getModel().getValueAt(filas,1)));
                                }catch(NumberFormatException ex){
                                    if(flagX){
                                        JOptionPane.showMessageDialog(null,"El campo de posicion en X de la fila "+(filas+1)+" debe recibir" +
                                                " solo valores numericos, este campo se considerara como cero","ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                                        flagX = false;
                                    }
                                }
                                try{
                                    Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].setCordZ(
                                            Float.parseFloat((String) tablaNodos.getModel().getValueAt(filas,2)));
                                }catch(NumberFormatException ex){
                                    if(flagZ){
                                        JOptionPane.showMessageDialog(null,"El campo de posicion en Z de la fila "+(filas+1)+" debe recibir" +
                                                " solo valores numericos, este campo se considerara como cero","ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                                        flagZ = false;
                                    }
                                }
                                try{
                                    Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas].setCordY(
                                            Float.parseFloat((String) tablaNodos.getModel().getValueAt(filas,3)));
                                }catch(NumberFormatException ex){
                                    if(flagY){
                                        JOptionPane.showMessageDialog(null,"El campo de posicion en Y de la fila "+(filas+1)+" debe recibir" +
                                                " solo valores numericos, este campo se considerara como cero","ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                                        flagY = false;
                                    }
                                }

                            }else if( ( (String) tablaNodos.getModel().getValueAt(filas,1)=="")&&
                                    ( (String) tablaNodos.getModel().getValueAt(filas,2)=="")&&
                                    ( (String) tablaNodos.getModel().getValueAt(filas,3)=="") ){
                                //Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas] = new Nodo();
                            }
                        }//fin de for columnas
//                    }//fin de if verificacion no nullpointer
                }//fin de for filas
                
                //--------------Mostrar el estado de los nodos registrados---------------
                String mensajeTemp = "NodoID   X      Z     Y\n";
                for(int i=0;i<Main.getArchivoDeTrabajo().getNodosDeArchivo().length;i++){
                    if( (Main.getArchivoDeTrabajo().getNodosDeArchivo()[i] != null) 
                       && (Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador().compareTo("Nodo sin nombre")!=0)  ){
                        mensajeTemp = mensajeTemp + 
                                Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador()+"  "+
                                Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getCordX()+"  "+
                                Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getCordZ()+"  "+
                                Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getCordY()+"\n";
                    }    
                }
                JPanel contenedor = new JPanel();
                contenedor.setBounds(25,50,250,200);
                contenedor.setLayout(new BorderLayout());

                JScrollPane scroll = new JScrollPane();                                       
                JTextArea area = new JTextArea();
                area.setText(mensajeTemp);
                area.setEditable(false);
                area.setBackground(Color.LIGHT_GRAY);
                area.setCaretPosition(0);

                scroll.setViewportView(area);
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                

                contenedor.add(scroll);
                contenedor.setVisible(true);                    

                JLabel etiqueta = new JLabel();
                JButton aceptar = new JButton("Aceptar");
                aceptar.setMnemonic('A');
                etiqueta.setText("Nodos creados hasta el momento:");
                etiqueta.setBounds(25,17,200,15);
                aceptar.setBounds(110,280,80,30);                    

                ventInfoNodos = new JFrame("INFORMACION");
                getVentInfoNodos().setLayout(null);
                getVentInfoNodos().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                getVentInfoNodos().setResizable(false);
                getVentInfoNodos().setBounds(300,200,300,350);
                Main.getVentDefEst().setEnabled(false);

                aceptar.addActionListener(
                    new ActionListener(){                        
                        public void actionPerformed(ActionEvent e) {
                            getVentInfoNodos().setVisible(false); 
                            Main.getAplicacion().setVisible(true);
                            Main.getVentDefEst().setEnabled(true);
                            Main.getVentDefEst().setVisible(true);
//                            JOptionPane.showMessageDialog(null,"Nodo 1 info:" +
//                                    "\ncordx= "+Main.getArchivoDeTrabajo().getNodosDeArchivo()[0].getCordX()+
//                                    "\ncordz= "+Main.getArchivoDeTrabajo().getNodosDeArchivo()[0].getCordZ()+
//                                    "\ncordy= "+Main.getArchivoDeTrabajo().getNodosDeArchivo()[0].getCordY());
                        }
                    }
                );
                getVentInfoNodos().add(contenedor);
                getVentInfoNodos().add(etiqueta);
                getVentInfoNodos().add(aceptar);
                getVentInfoNodos().setVisible(true);                
                
                
            }//fin de action performed
        };
        return retorno;
    }
    
    private ActionListener accionBorrar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,"Esta seguro que desea borrar la tabla?",
                        "PREGUNTA",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(opcion == 0){   //Si el usuario pulsa SI
                    for(int filas = 0;filas<512;filas++){
                        for(int cols =0;cols<4;cols++){
                            if(cols == 0){
                                int contador = filas + 1;                    
                                tablaNodos.getModel().setValueAt(""+contador,filas,cols);
                            }else{
                                tablaNodos.getModel().setValueAt("",filas,cols);
                                Main.getArchivoDeTrabajo().getNodosDeArchivo()[filas] = new Nodo();
                            }
                        }
                    }
//                    for(int filas = 0;filas<512;filas++){
//                        for(int cols=0;cols<4;cols++){
//                            
//                        }
//                    }
                }//Si el usuario pulsa NO no ocurre nada
            }
        };
        return retorno;
    }
    

    private JFrame getVentInfoNodos() {
        return ventInfoNodos;
    }
    
}
