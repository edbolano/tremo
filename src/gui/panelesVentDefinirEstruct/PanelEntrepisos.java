/*
 * PanelEntrepisos.java
 *
 * Created on 4 de febrero de 2007, 03:50 PM
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

import estructural.Entrepiso;
import estructural.MarcoX;
import estructural.MarcoZ;
import gui.PanelImagen;
import gui.VentanaDefinirEstructura;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class PanelEntrepisos extends JPanel{
    private JTable tablaEntrepisos = new JTable();
    private final String[] TITULOS = {"Entrepiso id","Marcos en X","Marcos en Z",
                                      "Peso 1(ton)","Xpeso 1(m)","Zpeso 1(m)",
                                      "Peso 2(ton)","Xpeso 2(m)","Zpeso 2(m)",
                                      "Peso 3(ton)","Xpeso 3(m)","Zpeso 3(m)",
                                      "Peso 4(ton)","Xpeso 4(m)","Zpeso 4(m)"};
    private TableColumn[] columnasDeTabla;
    private Entrepiso[] arrLocalEntrepisos = new Entrepiso[20];
    
    private JFrame ventInfoMarcosX;
    private JFrame ventInfoMarcosZ;
    /** Creates a new instance of PanelEntrepisos */
    public PanelEntrepisos() {
        super();
        this.setLayout(null);
        
        //Creacion de la tabla con base en la clase ModeloTablaGeneral de este paquete
        tablaEntrepisos.setModel(new ModeloTablaGeneral(20,15) );
//        tablaEntrepisos.setSelectionBackground(Color.getHSBColor(.5836f,.7266f,.8125f));
        tablaEntrepisos.setSelectionBackground(Color.LIGHT_GRAY);
        tablaEntrepisos.setRowSelectionAllowed(true);
        tablaEntrepisos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        //inicializo todos los valores de la tabla a empty String ""
        for(int filas=0;filas<20;filas++){
            for(int cols=0;cols<15;cols++){
                tablaEntrepisos.setValueAt("",filas,cols);
            }
        }
        
        columnasDeTabla = new TableColumn[15];
        for(int i=0;i<15;i++){
            columnasDeTabla[i] = tablaEntrepisos.getColumnModel().getColumn(i);
            columnasDeTabla[i].setHeaderValue(this.TITULOS[i] );
            columnasDeTabla[i].setResizable(true);
        }
        columnasDeTabla[0].setResizable(false);
        columnasDeTabla[1].setResizable(false);
        columnasDeTabla[2].setResizable(false);
        
        JTextField campoParaEditorDeCelda = new JTextField();
        campoParaEditorDeCelda.setBackground(Color.LIGHT_GRAY);
        campoParaEditorDeCelda.setEditable(false);
        
        columnasDeTabla[0].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        columnasDeTabla[1].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        columnasDeTabla[2].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        
        //Creacion del panel con barras de desplazamiento para meter ahi la tabla
        JScrollPane scrPane = new JScrollPane();
        scrPane.setViewportView(tablaEntrepisos);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPane.setBounds(0,0,(VentanaDefinirEstructura.getANCHO()-95),
                (VentanaDefinirEstructura.getALTO()-75) );
        
        //Agregacion de botones
        this.add(this.crearBoton("Aceptar",'A',(VentanaDefinirEstructura.getANCHO()-95),(20),
                (80),(25),accionAceptar() ));
        this.add(this.crearBoton("Crear Entrepisos",'C',(VentanaDefinirEstructura.getANCHO()-95),(50),
                (80),(25),accionCrearEntrepisos() ));
        this.add(this.crearBoton("Borrar",'B',(VentanaDefinirEstructura.getANCHO()-95),(80),
                (80),(25),accionBorrar() ));
        
        //Agregacion del panel con barras de desplazamiento
        this.add(scrPane);
        
        //Agregacion de la imagen
        this.add(new PanelImagen("lib//imagenes//entrepiso.itr",
                (VentanaDefinirEstructura.getANCHO()-95),(105),
                (80),(VentanaDefinirEstructura.getALTO()-155) ));//95 x 265 osea 95 x (420-155)
        
        //Se llena la tabla con los valores del archivo de trabajo
        this.llenarTablaEntrepisos();
        
        this.setVisible(true);
        
        ////////////////////consultas relativas a cada celda//////////////////////
        tablaEntrepisos.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int y = e.getY();
                int x = e.getX();
                String[] posiciones = new String[3];
                for(int i=0;i<3;i++){
                    posiciones[i] = (String)tablaEntrepisos.getColumnModel().getColumn(i).getHeaderValue();
                }
                int fila = y/16;
                int columna = x/75;

                if(  (columna==0)||(columna==1)||(columna==2)  ){
                    if(TITULOS[columna].compareTo(posiciones[columna])==0){
                        switch (columna){
                            case 0://entrepiso id
                                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila]!=null)
                                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                                    float[] datosPesoTotal = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].calcularPesoTotal();
                                    JOptionPane.showMessageDialog(null,"El peso total del entrepiso: "+
                                            "\n"+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador()
                                            +" es: "+datosPesoTotal[0]+" Ton"
                                            +"\nen las coordenadas:"
                                            +"\nX: "+datosPesoTotal[1]+" metros"
                                            +"\nZ: "+datosPesoTotal[2]+" metros"
                                            ,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);
                                }
                                break;
                            case 1://marcos en X
                                informacionMarcosX(fila, columna);
                                break;
                            case 2://marcos en Z
                                informacionMarcosZ(fila, columna);
                                break;
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Es necesario reposicionar las columnas de la tabla " +
                                "\n a su posicion original para poder consultar la informacion","AVISO",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        ////////////////////consultas relativas a cada celda//////////////////////
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
                for(int filas=0;filas<20;filas++){
                    if(tablaEntrepisos.getValueAt(filas,0)!=""){
                        try{
                            ///////PESOS-------------------
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[0] = 
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,3).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[1] = 
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,6).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[2] = 
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,9).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[3] = 
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,12).toString() );

                            //////COORDENADAS X------------------------
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[0] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,4).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[1] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,7).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[2] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,10).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[3] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,13).toString() );

                            //////COORDENADAS Z------------------------
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[0] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,5).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[1] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,8).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[2] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,11).toString() );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[3] =
                                    Float.parseFloat( tablaEntrepisos.getModel().getValueAt(filas,14).toString() );
                        }catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Se han introducido valores erroneos en algunos campos" +
                                    "\nestos valores se consideraran cero","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                        }
                        
                        
                    }
                }
                llenarTablaEntrepisos();
                JOptionPane.showMessageDialog(null,"Cambios Registrados!","AVISO",JOptionPane.INFORMATION_MESSAGE);
            }
        };
        return retorno;
    }
    
    private ActionListener accionCrearEntrepisos(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                arrLocalEntrepisos = generarEntrepisos();
                for(int i=0;i<20;i++){
                    if( (arrLocalEntrepisos[i]!=null)
                    &&(arrLocalEntrepisos[i].getIdentificador().compareTo("Entrepiso sin definir")!=0) 
                    &&(arrLocalEntrepisos[i].getMarcosX()[0]!=null||arrLocalEntrepisos[i].getMarcosZ()[0]!=null) ){
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[i] = arrLocalEntrepisos[i];
                    }
                }
                for(int entrepiso=0;entrepiso<20;entrepiso++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                        if((entrepiso!=0)){
                            float[] pesos = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal();
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].setPesoColsSuperiores(pesos[3] );
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].setXPesoColsSuperiores(pesos[4]);
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].setZPesoColsSuperiores(pesos[5]);
                        }
                    }
                }
                llenarTablaEntrepisos();
                
                //Rutina para asignar las alturas m,n y o correctas a cada marco
                for(int entrepiso=0;entrepiso<20;entrepiso++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                        float altM = 0f;
                        float altO = 0f;
                        //Si se trata del primer entrepiso
                        if(entrepiso==0){
                            altM = 0f;
                            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1]!=null)
                            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1].getIdentificador().compareTo("Entrepiso sin definir")!=0) 
                            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1].getMarcosX()[0]!=null) ){
                                altO = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1].getMarcosX()[0].calcularAlturaN();
                            }
                            
                        //Si se trata de algun entrepiso intermedio
                        }else if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1]!=null)
                        &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].getIdentificador().compareTo("Entrepiso sin definir")!=0) 
                        &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1]!=null)
                        &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                            if(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].getMarcosX()[0]!=null){
                                altM = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].getMarcosX()[0].calcularAlturaN();
                            }
                            if(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1].getMarcosX()[0]!=null){
                                altO = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso+1].getMarcosX()[0].calcularAlturaN();
                            }
                            
                        //Si se trata del ultimo entrepiso
                        }else if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1]!=null)
                        &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                            if(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].getMarcosX()[0]!=null){
                                altM = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso-1].getMarcosX()[0].calcularAlturaN();
                            }
                        }
                        
                        
                        for(int marcoX=0;marcoX<50;marcoX++){
                            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX]!=null)
                            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].getIdentificador().compareTo("Marco no definido")!=0) ){
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].setAlturaM(altM);
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].setAlturaO(altO);
                            }
                        }
                        for(int marcoZ=0;marcoZ<50;marcoZ++){
                            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ]!=null)
                            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].getIdentificador().compareTo("Marco no definido")!=0) ){
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].setAlturaM(altM);
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].setAlturaO(altO);
                            }
                        }
                    }
                }
                //Fin de rutina para asignar las alturas m,n y o correctas a cada marco
                
            }
        };
        return retorno;
    }
    
    public void llenarTablaEntrepisos(){
        for(int filas=0;filas<20;filas++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                //Entrepiso id
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getIdentificador(),
                        filas, 0);
                
                //Marcos en X
                tablaEntrepisos.setValueAt("Informacion",filas,1);
                
                //Marcos en Z
                tablaEntrepisos.setValueAt("Informacion",filas,2);
                
                //Peso 1
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[0],
                        filas,3);
                //X peso 1
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[0],
                        filas,4);
                //Z peso 1
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[0],
                        filas,5);
                
                //Peso 2
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[1],
                        filas,6);
                //X peso 2
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[1],
                        filas,7);
                //Z peso 2
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[1],
                        filas,8);
                
                //Peso 3
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[2],
                        filas,9);
                //X peso 3
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[2],
                        filas,10);
                //Z peso 3
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[2],
                        filas,11);
                
                //Peso 4
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getPesosTabla()[3],
                        filas,12);
                //X peso 4
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getXPesosTabla()[3],
                        filas,13);
                //Z peso 4
                tablaEntrepisos.setValueAt(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas].getZPesosTabla()[3],
                        filas,14);
            }
        }
    }
    
    private Entrepiso[] generarEntrepisos(){
        //Antes que cualquier cosa hay que retirar la bandera de asignacion a todos los marcos de
        //archivo tanto en x como en z
        for(int i=0;i<512;i++){
            if(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i]!=null){
                Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].setAsignadoAEntrepiso(false);
            }
            if(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i]!=null){
                Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].setAsignadoAEntrepiso(false);
            }
        }
        Entrepiso[] retorno = new Entrepiso[20];
        
        for(int j=0;j<retorno.length;j++){
            
            int indiceDeMarcoMasBajoX = 0;
            int indiceDeMarcoMasBajoZ = 0;
            boolean determinadoX = false;
            boolean determinadoZ = false;
            for(int i=0;i<512;i++){
                if(!determinadoX){
                    if( 
                          (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i]!=null)
                        &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].getIdentificador().compareTo("Marco no definido")!=0)
                        &&( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].isAsignadoAEntrepiso() ) 
                    ){
                        indiceDeMarcoMasBajoX = i;
                        determinadoX = true;
                    }
                }
                if(!determinadoZ){
                    if( 
                          (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i]!=null)
                        &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].getIdentificador().compareTo("Marco no definido")!=0)
                        &&( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].isAsignadoAEntrepiso() ) 
                    ){
                        indiceDeMarcoMasBajoZ = i;
                        determinadoZ = true;
                    }
                }
            }
            for(int i=0;i<512;i++){
                if( 
                      (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i]!=null)
                    &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].getIdentificador().compareTo("Marco no definido")!=0)
                    &&( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].isAsignadoAEntrepiso() ) 
                ){
                    if (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].getColumnasN()[0].getNodoMenor().getCordY()
                            <Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[indiceDeMarcoMasBajoX].getColumnasN()[0].getNodoMenor().getCordY() ){
                                indiceDeMarcoMasBajoX = i;
                    }
                }
                if( 
                      (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i]!=null)
                    &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].getIdentificador().compareTo("Marco no definido")!=0)
                    &&( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].isAsignadoAEntrepiso() ) 
                ){
                    if(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].getColumnasN()[0].getNodoMenor().getCordY()
                            <Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[indiceDeMarcoMasBajoZ].getColumnasN()[0].getNodoMenor().getCordY()){
                                indiceDeMarcoMasBajoZ = i;
                    }
                }
            }
            
            //Hasta este punto ya conozco el marco mas bajo Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[indiceDeMarcoMasBajo]

            retorno[j] = new Entrepiso();
            retorno[j].setIdentificador("Entrepiso "+(j+1) );
            int indiceMarcosX = 0;
            int indiceMarcosZ = 0;
            for(int i=0;i<512;i++){
                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].getIdentificador().compareTo("Marco no definido")!=0)
                &&( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].isAsignadoAEntrepiso()) ){
                    if(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].getColumnasN()[0].getNodoMenor().getCordY() ==
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[indiceDeMarcoMasBajoX].getColumnasN()[0].getNodoMenor().getCordY() ){
                        retorno[j].getMarcosX()[indiceMarcosX] = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i];
                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[i].setAsignadoAEntrepiso(true);
                        indiceMarcosX++;
                    }
                }
                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].getIdentificador().compareTo("Marco no definido")!=0)
                &&( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].isAsignadoAEntrepiso()) ){
                    if(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].getColumnasN()[0].getNodoMenor().getCordY() ==
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[indiceDeMarcoMasBajoZ].getColumnasN()[0].getNodoMenor().getCordY() ){
                        retorno[j].getMarcosZ()[indiceMarcosZ] = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i];
                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnZ()[i].setAsignadoAEntrepiso(true);
                        indiceMarcosZ++;
                    }
                }
            }
        
        }//fin de for entrepisos i<20
        
        return retorno;
    }
    
    private void informacionMarcosX(int fila, int col){
        String nombreEntrepiso = "";
        String mensajeTemp = "";
        if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila]!=null)
        &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
            nombreEntrepiso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador();
            for(int i=0;i<50;i++){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getMarcosX()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getMarcosX()[i].getIdentificador().compareTo("Marco no definido")!=0) ){
                    mensajeTemp = mensajeTemp +"\n"+ 
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getMarcosX()[i].getIdentificador();
                }
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
        etiqueta.setText("Marcos en X de entrepiso "+nombreEntrepiso+":");
        etiqueta.setBounds(25,17,200,15);
        aceptar.setBounds(110,280,80,30);                    

        ventInfoMarcosX = new JFrame("INFORMACION");
        ventInfoMarcosX.setLayout(null);
        ventInfoMarcosX.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventInfoMarcosX.setResizable(false);
        ventInfoMarcosX.setBounds(300,200,300,350);
        Main.getVentDefEst().setEnabled(false);

        aceptar.addActionListener(
            new ActionListener(){                        
                public void actionPerformed(ActionEvent e) {
                    ventInfoMarcosX.setVisible(false); 
                    Main.getAplicacion().setVisible(true);
                    Main.getVentDefEst().setEnabled(true);
                    Main.getVentDefEst().setVisible(true);
                }
            }
        );
        ventInfoMarcosX.add(contenedor);
        ventInfoMarcosX.add(etiqueta);
        ventInfoMarcosX.add(aceptar);
        ventInfoMarcosX.setVisible(true);  
    }
    
    private void informacionMarcosZ(int fila, int col){
        String nombreEntrepiso = "";
        String mensajeTemp = "";
        if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila]!=null)
        &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
            nombreEntrepiso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador();
            for(int i=0;i<50;i++){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getMarcosZ()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getMarcosZ()[i].getIdentificador().compareTo("Marco no definido")!=0) ){
                    mensajeTemp = mensajeTemp +"\n"+ 
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getMarcosZ()[i].getIdentificador();
                }
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
        etiqueta.setText("Marcos en Z de entrepiso "+nombreEntrepiso+":");
        etiqueta.setBounds(25,17,200,15);
        aceptar.setBounds(110,280,80,30);                    

        ventInfoMarcosZ = new JFrame("INFORMACION");
        ventInfoMarcosZ.setLayout(null);
        ventInfoMarcosZ.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventInfoMarcosZ.setResizable(false);
        ventInfoMarcosZ.setBounds(300,200,300,350);
        Main.getVentDefEst().setEnabled(false);

        aceptar.addActionListener(
            new ActionListener(){                        
                public void actionPerformed(ActionEvent e) {
                    ventInfoMarcosZ.setVisible(false); 
                    Main.getAplicacion().setVisible(true);
                    Main.getVentDefEst().setEnabled(true);
                    Main.getVentDefEst().setVisible(true);
                }
            }
        );
        ventInfoMarcosZ.add(contenedor);
        ventInfoMarcosZ.add(etiqueta);
        ventInfoMarcosZ.add(aceptar);
        ventInfoMarcosZ.setVisible(true); 
    }
    
    private ActionListener accionBorrar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,"Esta seguro que desea borrar la tabla?",
                        "PREGUNTA",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(opcion == 0){   //Si el usuario pulsa SI
                    for(int filas = 0;filas<20;filas++){
                        for(int cols =0;cols<15;cols++){
                            tablaEntrepisos.getModel().setValueAt("",filas,cols);
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[filas] = new Entrepiso();
                        }
                    }

                }//Si el usuario pulsa NO no ocurre nada
            }
        };
        return retorno;
    }
    
}
