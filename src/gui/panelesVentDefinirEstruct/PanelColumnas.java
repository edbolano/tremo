/*
 * PanelColumnas.java
 *
 * Created on 4 de febrero de 2007, 03:47 PM
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

import estructural.Columna;
import estructural.Material;
import estructural.Seccion;
import gui.PanelImagen;
import gui.VentanaDefinirEstructura;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class PanelColumnas extends JPanel{
    private final String[] TITULOS = {"Columna","Nodo A","Nodo B","Seccion","Material"}; 
    private JTable tablaColumnas = new JTable();
    private JFrame ventInfoColumnas;
    private TableColumn[] columnasDeTabla;
    
    /** Creates a new instance of PanelColumnas */
    public PanelColumnas() {
        super();
        this.setLayout(null);
        
        //Creacion de la tabla con base en la tabla ModeloTablaGeneral de este paquete
        tablaColumnas.setModel(new ModeloTablaGeneral(512,5) );
//        tablaColumnas.setSelectionBackground(Color.getHSBColor(.5836f,.7266f,.8125f));
        tablaColumnas.setSelectionBackground(Color.LIGHT_GRAY);
        tablaColumnas.setRowSelectionAllowed(true);
        
        columnasDeTabla = new TableColumn[5];
        for(int i=0;i<5;i++){
            columnasDeTabla[i] = tablaColumnas.getColumnModel().getColumn(i);
            columnasDeTabla[i].setHeaderValue(this.TITULOS[i] );
            columnasDeTabla[i].setResizable(false);
        }
        
        //Generar un formato de campo de texto y asignarlo a la primera columna (Columnas)
        JTextField campoColumnasNum = new JTextField();
        campoColumnasNum.setBackground(Color.LIGHT_GRAY);
        campoColumnasNum.setEditable(false);
        columnasDeTabla[0].setCellEditor(new DefaultCellEditor(campoColumnasNum) );
        columnasDeTabla[0].setMaxWidth(50);
        
        //Creacion del panel con barras de desplazamiento para meter ahi la tabla
        JScrollPane scrPane = new JScrollPane();
        scrPane.setViewportView(tablaColumnas);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPane.setBounds(0,0,
                (VentanaDefinirEstructura.getANCHO()-95),(VentanaDefinirEstructura.getALTO()-75) );
        
        //Agregacion de botones
        this.add(this.crearBoton("Aceptar",'A',(VentanaDefinirEstructura.getANCHO()-95),(20),
                (80),(25),accionAceptar() ));
        this.add(this.crearBoton("Borrar",'B',
                (VentanaDefinirEstructura.getANCHO()-95),(50),
                (80),(25),accionBorrar() ));
        
        //Agregacion del panel con barras de desplaz
        this.add(scrPane);
        
        //Agregacion de la imagen
        this.add(new PanelImagen("lib//imagenes//columna.itr",
                (VentanaDefinirEstructura.getANCHO()-95),(80),
                (80),(VentanaDefinirEstructura.getALTO()-155) ));//95 x 265 osea 95 x (420-155)
        
        //Definimos los JComboBox//-----------------------------------------------------        
        //Los combobox se definen en la clase VentanaDefinirEstructura pues estos se actualizan
        //al hacer visibles las fichas que son miembros de VentanaDefinirEstructura
        //esto se implementa con el ComponentLisnener en dicha clase...
        //------------------------------------------------------------------------------
        
        //Se llena la tabla con los valores en el archivo de trabajo antes de hacerla visible
        this.llenarTablaColumnas();
        
        //Se hace visible este panel
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
                Seccion[] arrTempArchivoSeccion = VentanaDefinirEstructura.abrirArchivoSeccion();
                Material[] arrTempArchivoMaterial = VentanaDefinirEstructura.abrirArchivoMaterial();
                
                //se repasa toda la tabla para recoger los registros en la instancia de archivo
                for(int filas=0;filas<512;filas++){
                    boolean columnaSinCrear = true;
                    for(int cols=1;cols<5;cols++){
                        if( (tablaColumnas.getValueAt(filas,1) != "")&&
                                (tablaColumnas.getValueAt(filas,2) != "")&&
                                (tablaColumnas.getValueAt(filas,3) != "")&&
                                (tablaColumnas.getValueAt(filas,4) != "") ){
                            if(columnaSinCrear){
                                Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas] = new Columna();
                                String identificadorDeColumna = "Columna "+(filas+1);
                                Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].setIdentificador(identificadorDeColumna);
                                columnaSinCrear = false;
                                                                                    
                                switch(cols){
                                    case 1://nodoA
                                    {
                                        String identDeTabla = (String) tablaColumnas.getValueAt(filas,1);
                                        for(int i=0;i<512;i++){
                                            if(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i]!=null){
                                                if(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador()== identDeTabla){
                                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].setNodoA(
                                                            Main.getArchivoDeTrabajo().getNodosDeArchivo()[i] );
                                                }
                                            }
                                        }
                                    }//break;
                                    case 2: //nodoB
                                    {
                                        String identDeTabla = (String) tablaColumnas.getValueAt(filas,2);
                                        for(int i=0;i<512;i++){
                                            if(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i]!=null){
                                                if(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador()== identDeTabla){
                                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].setNodoB(
                                                            Main.getArchivoDeTrabajo().getNodosDeArchivo()[i] );
                                                }
                                            }
                                        }
                                    }//break;
                                    case 3: //seccion
                                    {
                                        String identDeTabla = (String) tablaColumnas.getValueAt(filas,3);
                                        for(int i=0;i<512;i++){
                                            if( (arrTempArchivoSeccion[i]!=null)&&(identDeTabla!=null) ){
                                                //if(arrTempArchivoSeccion[i].getIdentificador() == identDeTabla){  <-Esto no funciono... sospecho que es porque comparaba referencias                                                
                                                if(arrTempArchivoSeccion[i].getIdentificador().compareTo(identDeTabla) == 0 ){//cero es el valor que regresa el metodo compareTo 
                                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].setSeccion(         //cuando las cadenas son iguales
                                                        arrTempArchivoSeccion[i] );
                                                    
                                                    //JOptionPane.showMessageDialog(null,"Ya esta asignando el identificador y es este: "+ Main.getArchivoDeTrabajo().getVigasDeArchivo()[filas].getSeccion().getArea()+"cm2" );
                                                }                                            
                                            }
                                        }
                                    }//break;
                                    case 4://material
                                    {
                                        String identDeTabla = (String) tablaColumnas.getValueAt(filas,4);
                                        for(int i=0;i<512;i++){
                                            if( (arrTempArchivoMaterial[i] != null)&&(identDeTabla!=null) ){
                                                if(arrTempArchivoMaterial[i].getIdentificador().compareTo(identDeTabla) == 0){
                                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].setMaterial(
                                                            arrTempArchivoMaterial[i] );
                                                }
                                            }
                                        }
                                    }//break;

                                }//fin de switch
                            }//---------------temp
                            
                        } 
                    }//fin de for columnas
                }//fin de for filas
                
                //------------------------------------------------------------------------
                //------------------------------------------------------------------------
                //Mostrar estado de las columnas creadas--------------------------------------
                String[] titulosTablaInfoColumnas = {"Num.","ColumnaID","NodoA","NodoB","Seccion","Material","Longitud"};
                
                JPanel contenedor = new JPanel();                
                contenedor.setBounds(25,50,400,300);
                contenedor.setLayout(new BorderLayout());

                JScrollPane scroll = new JScrollPane();  
                
                JTable tablaInfoColumnas = new JTable();
                tablaInfoColumnas.setModel(new ModeloTablaGeneral(512,7) );
                tablaInfoColumnas.setBackground(Color.LIGHT_GRAY);
                
                JTextField campoTextoInfo = new JTextField();
                campoTextoInfo.setBackground(Color.LIGHT_GRAY);
                campoTextoInfo.setEditable(false);
                
                TableColumn[] columnasDeTabla = new TableColumn[7];                
                for(int i=0;i<7;i++){
                    columnasDeTabla[i] = tablaInfoColumnas.getColumnModel().getColumn(i);
                    columnasDeTabla[i].setHeaderValue( titulosTablaInfoColumnas[i] );
                    columnasDeTabla[i].setCellEditor(new DefaultCellEditor(campoTextoInfo) );                    
                }
                columnasDeTabla[0].setResizable(false);
                columnasDeTabla[0].setMaxWidth(50);
                
                for(int filas=0;filas<512;filas++){
                    for(int cols=1;cols<7;cols++){
                        if( (Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas]!=null) ){
                        //&&(Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getIdentificador()!="elemento sin nombre") ){
                            switch(cols){
                                case 1:
                                {
                                    tablaInfoColumnas.setValueAt(
                                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getIdentificador(),
                                            filas,1); 
                                }//break;
                                case 2:
                                {
                                    tablaInfoColumnas.setValueAt(
                                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getNodoA().getIdentificador(),
                                            filas,2);
                                }//break;
                                case 3:
                                {
                                    tablaInfoColumnas.setValueAt(
                                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getNodoB().getIdentificador(),
                                            filas,3);
                                }//break;
                                case 4:
                                {
                                    tablaInfoColumnas.setValueAt(
                                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getSeccion().getIdentificador(),
                                            filas,4);
                                }//break;
                                case 5:
                                {
                                    tablaInfoColumnas.setValueAt(
                                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getMaterial().getIdentificador(),
                                            filas,5);
                                }//break;
                                case 6:
                                {
                                    tablaInfoColumnas.setValueAt(
                                            Float.toString(Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].calcularLongitud() ),
                                            filas,6);
                                }//break;
                            }//fin switch
                            
                        }//fin if no null
                    }//fin for cols
                }//fin for filas
                
                scroll.setViewportView(tablaInfoColumnas);
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                

                contenedor.add(scroll);
                contenedor.setVisible(true);                    

                JLabel etiqueta = new JLabel();
                JButton aceptar = new JButton("Aceptar");
                aceptar.setMnemonic('A');
                etiqueta.setText("Columnas creadas hasta el momento:");
                etiqueta.setBounds(25,17,200,15);
                aceptar.setBounds(200,380,80,30);                    

                ventInfoColumnas = new JFrame("INFORMACION");
                ventInfoColumnas.setLayout(null);
                ventInfoColumnas.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                ventInfoColumnas.setResizable(false);
                ventInfoColumnas.setBounds(300,200,450,450);
                Main.getVentDefEst().setEnabled(false);

                aceptar.addActionListener(
                    new ActionListener(){                        
                        public void actionPerformed(ActionEvent e) {
                            ventInfoColumnas.setVisible(false); 
                            Main.getAplicacion().setVisible(true);
                            Main.getVentDefEst().setEnabled(true);
                            Main.getVentDefEst().setVisible(true);
                        }
                    }
                );
                ventInfoColumnas.add(contenedor);
                ventInfoColumnas.add(etiqueta);
                ventInfoColumnas.add(aceptar);
                ventInfoColumnas.setVisible(true); 
                //---------------------------Fin de mostrar vigas creadas/------------------
                //------------------------------------------------------------------------
                //------------------------------------------------------------------------
            }
        };
        return retorno;
    }
    
    private ActionListener accionBorrar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,"Esta seguro de que desea borrar la tabla de columnas?",
                        "PREGUNTA",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(opcion == 0){//pulsa si
                    for(int filas=0;filas<512;filas++){
                        for(int cols=1;cols<5;cols++){
                            tablaColumnas.getModel().setValueAt("",filas,cols);
                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas] = null;
                        }
                    }
                }//fin de if opcion
            }
        };
        return retorno;
    }
    
    public TableColumn[] getColumnasDeTabla() {
        return columnasDeTabla;
    }
    
    public void llenarTablaColumnas(){
        //Se llena la tabla con los valores en el archivo de trabajo antes de hacerla visible
        for(int filas=0;filas<512;filas++){
            for(int cols=1;cols<5;cols++){
                if( (Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas]!=null) ){
                //&&(Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getIdentificador()!="elemento sin nombre") ){
                    switch(cols){
                        case 1://nodoA
                        {
                            tablaColumnas.setValueAt(
                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getNodoA().getIdentificador(),
                                    filas,1);
                        }//break;
                        case 2://nodoB
                        {
                            tablaColumnas.setValueAt(
                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getNodoB().getIdentificador(),
                                    filas,2);
                        }//break;
                        case 3://seccion
                        {
                            tablaColumnas.setValueAt(
                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getSeccion().getIdentificador(),
                                    filas,3);
                        }//break;
                        case 4://material
                        {
                            tablaColumnas.setValueAt(
                                    Main.getArchivoDeTrabajo().getColumnasDeArchivo()[filas].getMaterial().getIdentificador(),
                                    filas,4);
                        }//break;
                    }//fin de if
                }//fin de switch
            }//fin de for cols
        }//fin de for filas
    }
    
}
