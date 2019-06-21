/*
 * PanelMarcosX.java
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

import estructural.Columna;
import estructural.Marco;
import estructural.MarcoX;
import estructural.MarcoZ;
import estructural.Nodo;
import estructural.Viga;
import gui.PanelImagen;
import gui.VentanaDefinirEstructura;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class PanelMarcosX extends JPanel{
    private JTable tablaMarcos = new JTable();
    private final String[] TITULOS = {"Marco id","Columnas","Vigas inf","Vigas sup",
                                                 "hm (m)","ho (m)","Rigidez(Ton/cm)"};
    private TableColumn[] columnasDeTabla;
    private MarcoX[] arrLocalMarcosX = new MarcoX[512];
    private MarcoZ[] arrLocalMarcosZ = new MarcoZ[512];
    
    private JFrame ventInfoColumnasN;
    private JFrame ventInfoVigasM;
    private JFrame ventInfoVigasN;
    
    
    /**
     * Creates a new instance of PanelMarcosX
     */
    public PanelMarcosX() {
        super();
        this.setLayout(null);
        
        //Creacion de la tabla con base en la clase ModeloTablaGeneral de este paquete
//        tablaMarcos.setModel(new ModeloTablaGeneralSinIndices(512,7) );
        tablaMarcos.setModel(new ModeloTablaGeneral(512,7) );
//        tablaMarcos.setSelectionBackground(Color.getHSBColor(.5836f,.7266f,.8125f));
        tablaMarcos.setSelectionBackground(Color.LIGHT_GRAY);
        tablaMarcos.setRowSelectionAllowed(true);
        
        //inicializo todos los valores de la tabla a empty String ""
        for(int filas=0;filas<512;filas++){
            for(int cols=0;cols<7;cols++){
                tablaMarcos.setValueAt("",filas,cols);
            }
        }
        
        
        columnasDeTabla = new TableColumn[7];
        for(int i=0;i<7;i++){
            columnasDeTabla[i] = tablaMarcos.getColumnModel().getColumn(i);
            columnasDeTabla[i].setHeaderValue(this.TITULOS[i] );
            columnasDeTabla[i].setResizable(true);
        }
        columnasDeTabla[0].setResizable(false);
        columnasDeTabla[1].setResizable(false);
        columnasDeTabla[2].setResizable(false);
        columnasDeTabla[3].setResizable(false);
        
        JTextField campoParaEditorDeCelda = new JTextField();
        campoParaEditorDeCelda.setBackground(Color.LIGHT_GRAY);
        campoParaEditorDeCelda.setEditable(false);
        
        columnasDeTabla[0].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        columnasDeTabla[1].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        columnasDeTabla[2].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        columnasDeTabla[3].setCellEditor(new DefaultCellEditor(campoParaEditorDeCelda) );
        
        
        //Creacion del panel con barras de desplazamiento para meter ahi la tabla
        JScrollPane scrPane = new JScrollPane();
        scrPane.setViewportView(tablaMarcos);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPane.setBounds(0,0,(VentanaDefinirEstructura.getANCHO()-95),
                (VentanaDefinirEstructura.getALTO()-75) );
        
        //Agregacion de botones
        this.add(this.crearBoton("Aceptar",'A',(VentanaDefinirEstructura.getANCHO()-95),(20),
                (80),(25),accionAceptar() ));
        this.add(this.crearBoton("Crear Marcos",'C',(VentanaDefinirEstructura.getANCHO()-95),(50),
                (80),(25),accionCrearMarcos() ));
        this.add(this.crearBoton("Borrar",'B',(VentanaDefinirEstructura.getANCHO()-95),(80),
                (80),(25),accionBorrar() ));
        
//        JButton boton = new JButton("temp");
//        boton.setBounds((VentanaDefinirEstructura.getANCHO()-95),(20),(80),(25));
//        boton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                Columna[] colsValidas = seleccionarColumnasValidas();
//                MarcoX[] marcosEnX = generarMarcosX();
//                MarcoZ[] marcosEnZ = generarMarcosZ();
//                //temporal
//                String msj = "";
//                for(int i=0;i<512;i++){
//                    if(colsValidas[i]!=null){
//                        msj = msj +"\n"+colsValidas[i].getIdentificador()+"  marco en x: "+colsValidas[i].getMarcoPadreEnX()+"  marco en z: "+colsValidas[i].getMarcoPadreEnZ();
//                    }
//                }
//                JOptionPane.showMessageDialog(null,"columnas validas y su marco :"+msj);
//                int num = Integer.parseInt(JOptionPane.showInputDialog("ingresa el numero de marco en X que quieres consultar") );
//                String msj2 = "columnas";
//                for(int i=0;i<512;i++){
//                    if(marcosEnX[num]!=null){
//                        if(marcosEnX[num].getColumnasN()[i]!=null){
//                            msj2 = "\n"+msj2 + "\n"+marcosEnX[num].getColumnasN()[i].getIdentificador();
//                        }
//                    }
//                }
//                String msj3 = "\n\nvigas M (abajo)";
//                for(int i=0;i<512;i++){
//                    if(marcosEnX[num]!=null){
//                        if(marcosEnX[num].getVigasM()[i]!=null){
//                            msj3 = "\n"+msj3 + "\n"+marcosEnX[num].getVigasM()[i].getIdentificador();
//                        }
//                    }
//                }
//                String msj4 = "\n\nvigas N (arriba)";
//                for(int i=0;i<512;i++){
//                    if(marcosEnX[num]!=null){
//                        if(marcosEnX[num].getVigasN()[i]!=null){
//                            msj4 = "\n"+msj4 + "\n"+marcosEnX[num].getVigasN()[i].getIdentificador();
//                        }
//                    }
//                }
//                JOptionPane.showMessageDialog(null,"columnas del marco "+num+" de nombre: "+marcosEnX[num].getIdentificador()
//                        +msj2+msj3+msj4);
//                int numZ = Integer.parseInt(JOptionPane.showInputDialog("ingresa el numero de marco en Z que quieres consultar") );
//                String msj2Z = "columnas";
//                for(int i=0;i<512;i++){
//                    if(marcosEnZ[numZ]!=null){
//                        if(marcosEnZ[numZ].getColumnasN()[i]!=null){
//                            msj2Z = "\n"+msj2Z + "\n"+marcosEnZ[numZ].getColumnasN()[i].getIdentificador();
//                        }
//                    }
//                }
//                String msj3Z = "\n\nvigas M (abajo)";
//                for(int i=0;i<512;i++){
//                    if(marcosEnZ[numZ]!=null){
//                        if(marcosEnZ[numZ].getVigasM()[i]!=null){
//                            msj3Z = "\n"+msj3Z + "\n"+marcosEnZ[numZ].getVigasM()[i].getIdentificador();
//                        }
//                    }
//                }
//                String msj4Z = "\n\nvigas N (arriba)";
//                for(int i=0;i<512;i++){
//                    if(marcosEnZ[numZ]!=null){
//                        if(marcosEnZ[numZ].getVigasN()[i]!=null){
//                            msj4Z = "\n"+msj4Z + "\n"+marcosEnZ[numZ].getVigasN()[i].getIdentificador();
//                        }
//                    }
//                }
//                JOptionPane.showMessageDialog(null,"columnas del marco "+numZ+" de nombre:"+marcosEnZ[numZ].getIdentificador()
//                        +msj2Z+msj3Z+msj4Z);
//            }
//        } );
//        this.add(boton);
        
        //Agregacion del panel con barras de desplazamiento
        this.add(scrPane);
        
        //Agregacion de la imagen
        this.add(new PanelImagen("lib//imagenes//marco.itr",
                (VentanaDefinirEstructura.getANCHO()-95),(105),
                (80),(VentanaDefinirEstructura.getALTO()-155) ));//95 x 265 osea 95 x (420-155)
                
        //Se llena la tabla con los valores del archivo de trabajo
        this.llenarTablaMarcos();
        
        this.setVisible(true);
        ////////////////////consultas relativas a cada celda//////////////////////
        tablaMarcos.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int y = e.getY();
                int x = e.getX();
                String[] posiciones = new String[7];
                for(int i=0;i<7;i++){
                    posiciones[i] = (String)tablaMarcos.getColumnModel().getColumn(i).getHeaderValue();
                }
                int fila = y/16;
                int columna = x/69;
                
                if(  (columna==0)||(columna==1)||(columna==2)||(columna==3)  ){
                    if(TITULOS[columna].compareTo(posiciones[columna])==0){
                        switch (columna){
                            case 0://marco id
                                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila]!=null)
                                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador().compareTo("Marco no definido")!=0) ){
                                    int opcion = JOptionPane.showConfirmDialog(null,"La rigidez del marco: " +
                                            "\n"+Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador()
                                            +"  es : "+Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getRigidezR()
                                            +" Ton/cm" +
                                            "\n\nDesea asignar la rigidez de este marco manualmente?","PREGUNTA",JOptionPane.YES_NO_OPTION);
                                    if(opcion==JOptionPane.YES_OPTION){
                                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].setRigidezDesignadaManualmente(true);
                                        JOptionPane.showMessageDialog(null,"Ahora puede asignar manualmente la rigidez de este marco" +
                                                "\nen el campo \"Asignar_opcionalmente\" de la columna rigidez","INFORMACION",
                                                JOptionPane.INFORMATION_MESSAGE);
                                    }else{
                                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].setRigidezDesignadaManualmente(false);
                                    }
                                }
                                break;
                            case 1://columnasN
                                informacionColumnasN(fila, columna);
                                break;
                            case 2://vigas inferiores
                                informacionVigasM(fila, columna);
                                break;
                            case 3://vigas superiores
                                informacionVigasN(fila, columna);
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
    
    private Columna[] seleccionarColumnasValidas(){    
        Columna[] retorno = new Columna[512];
        for(int i=0;i<512;i++){            
            if(Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i]!=null){                
                float xA = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoA().getCordX(),
                      zA = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoA().getCordZ(),
                      yA = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoA().getCordY(),
                      xB = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoB().getCordX(),
                      zB = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoB().getCordZ(),
                      yB = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoB().getCordY();
                if ((xA==xB)&&(zA==zB)&&(yA!=yB) ){
                    retorno[i] = Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i];                    
                }
            }else{
                i=512;
            }
        }
        return retorno;
    }
    private Viga[] seleccionarVigasValidasX(){
        Viga[] retorno = new Viga[512];
        for(int i=0;i<512;i++){            
            if(Main.getArchivoDeTrabajo().getVigasDeArchivo()[i]!=null){                
                float xA = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA().getCordX(),
                      zA = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA().getCordZ(),
                      yA = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA().getCordY(),
                      xB = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB().getCordX(),
                      zB = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB().getCordZ(),
                      yB = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB().getCordY();
                if ((xA!=xB)&&(zA==zB)&&(yA==yB) ){
                    retorno[i] = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i];                    
                }
            }else{
                i=512;
            }
        }
        return retorno;
    }
    private Viga[] seleccionarVigasValidasZ(){
        Viga[] retorno = new Viga[512];
        for(int i=0;i<512;i++){            
            if(Main.getArchivoDeTrabajo().getVigasDeArchivo()[i]!=null){                
                float xA = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA().getCordX(),
                      zA = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA().getCordZ(),
                      yA = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA().getCordY(),
                      xB = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB().getCordX(),
                      zB = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB().getCordZ(),
                      yB = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB().getCordY();
                if ((xA==xB)&&(zA!=zB)&&(yA==yB) ){
                    retorno[i] = Main.getArchivoDeTrabajo().getVigasDeArchivo()[i];                    
                }
            }else{
                i=512;
            }
        }
        return retorno;
    }
    
    public MarcoX[] generarMarcosX(){
        MarcoX[] marcosEnX = new MarcoX[512];
        Columna[] colsValidas = seleccionarColumnasValidas();
        Viga[] vigasValsX = seleccionarVigasValidasX();

        //*******armar los marcos en X***************************************************************

        //asignar marco padre a cada columna valida
        int numeroDeMarcoX = 1;
        for(int i=0;i<512;i++){
            if(colsValidas[i]!=null){
                for(int j=0;j<512;j++){
                    if(colsValidas[j]!=null){
                        if( (colsValidas[i].getNodoMenor().getCordY()==colsValidas[j].getNodoMenor().getCordY())
                        &&(colsValidas[i].getNodoMenor().getCordZ()==colsValidas[j].getNodoMenor().getCordZ())
                        &&(colsValidas[i].calcularLongitud()==colsValidas[j].calcularLongitud()) ){
                        //&&(colsValidas[i].getNodoA().getCordX()!=colsValidas[j].getNodoA().getCordX()) ){
                            colsValidas[j].setMarcoPadreEnX(numeroDeMarcoX);
                        }
                    }
                }
                numeroDeMarcoX++;
            }else{
                i=512;
            }
        }

        //crear marcos en base al atributo marco padre de cada columna
        //int contadorArrCols = 0;
        for(int i=0;i<512;i++){
            int contadorArrCols = 0;
            marcosEnX[i] = new MarcoX();
            marcosEnX[i].setIdentificador("Marco x-"+(i) );
            for(int j=0;j<512;j++){
                if(colsValidas[j] !=null){
                    if(colsValidas[j].getMarcoPadreEnX()== i){
                        marcosEnX[i].getColumnasN()[contadorArrCols] = colsValidas[j];
                        contadorArrCols++;
                    }
                }
            }
        }

        //Asignar vigas a los marcos creados en la direccion X//********************************
        for(int i=0;i<512;i++){
            Nodo nodoAbajo =null;
            Nodo nodoArriba =null;
            for(int j=0;j<512;j++){
            ///////////////////
                if(marcosEnX[i].getColumnasN()[j] != null){
                    if(marcosEnX[i].getColumnasN()[j].getNodoA().getCordY() >
                            marcosEnX[i].getColumnasN()[j].getNodoB().getCordY()){
                        nodoAbajo = marcosEnX[i].getColumnasN()[j].getNodoB();
                        nodoArriba = marcosEnX[i].getColumnasN()[j].getNodoA();
                    }else if(marcosEnX[i].getColumnasN()[j].getNodoA().getCordY() <
                            marcosEnX[i].getColumnasN()[j].getNodoB().getCordY()){
                        nodoAbajo = marcosEnX[i].getColumnasN()[j].getNodoA();
                        nodoArriba = marcosEnX[i].getColumnasN()[j].getNodoB();
                    }
                }
            ///////////////////
            }
            if( (nodoAbajo!=null)&&(nodoArriba!=null) ){
                int contadorVigasMarcosXn = 0;
                int contadorVigasMarcosXm = 0;
                for(int j=0;j<512;j++){
                    if(vigasValsX[j] != null){
                        if( (vigasValsX[j].getNodoA().getCordZ()==nodoArriba.getCordZ() )
                          &&(vigasValsX[j].getNodoA().getCordY()==nodoArriba.getCordY()) ){
                            marcosEnX[i].getVigasN()[contadorVigasMarcosXn] = vigasValsX[j];
                            contadorVigasMarcosXn++;
                        }else if( (vigasValsX[j].getNodoA().getCordZ()==nodoAbajo.getCordZ()) 
                                &&(vigasValsX[j].getNodoA().getCordY()==nodoAbajo.getCordY()) ){
                            marcosEnX[i].getVigasM()[contadorVigasMarcosXm] = vigasValsX[j];
                            contadorVigasMarcosXm++;
                        }
                    }
                }

            }
        }

        //*********************************************************************************
        return marcosEnX;
        
    }
    
    public MarcoZ[] generarMarcosZ(){
        MarcoZ[] marcosEnZ = new MarcoZ[512];
        Columna[] colsValidas = seleccionarColumnasValidas();
        Viga[] vigasValsZ = seleccionarVigasValidasZ();
        
        //*******armar los marcos en Z*****************************************************
        int numeroDeMarcoZ = 1;
        for(int i=0;i<512;i++){
            if(colsValidas[i]!=null){
                for(int j=0;j<512;j++){
                    if(colsValidas[j]!=null){
                        if( (colsValidas[i].getNodoMenor().getCordY()==colsValidas[j].getNodoMenor().getCordY())
                        &&(colsValidas[i].getNodoMenor().getCordX()==colsValidas[j].getNodoMenor().getCordX())
                        &&(colsValidas[i].calcularLongitud()==colsValidas[j].calcularLongitud()) ){
                        //&&(colsValidas[i].getNodoA().getCordX()!=colsValidas[j].getNodoA().getCordX()) ){
                            colsValidas[j].setMarcoPadreEnZ(numeroDeMarcoZ);
                        }
                    }
                }
                numeroDeMarcoZ++;
            }else{
                i=512;
            }
        }

        //crear marcos en base al atributo marco padre de cada columna
//        int contadorArrColsZ = 0;
        for(int i=0;i<512;i++){
            int contadorArrColsZ = 0;
            marcosEnZ[i] = new MarcoZ();
            marcosEnZ[i].setIdentificador("Marco z-"+(i) );
            for(int j=0;j<512;j++){
                if(colsValidas[j] !=null){
                    if(colsValidas[j].getMarcoPadreEnZ()== i){
                        marcosEnZ[i].getColumnasN()[contadorArrColsZ] = colsValidas[j];
                        contadorArrColsZ++;
                    }
                }
            }
        }  

        //Asignar vigas a los marcos creados en la direccion Z//********************************
        for(int i=0;i<512;i++){
            Nodo nodoAbajo =null;
            Nodo nodoArriba =null;
            for(int j=0;j<512;j++){
            ///////////////////
                if(marcosEnZ[i].getColumnasN()[j] != null){
                    if(marcosEnZ[i].getColumnasN()[j].getNodoA().getCordY() >
                            marcosEnZ[i].getColumnasN()[j].getNodoB().getCordY()){
                        nodoAbajo = marcosEnZ[i].getColumnasN()[j].getNodoB();
                        nodoArriba = marcosEnZ[i].getColumnasN()[j].getNodoA();
                    }else if(marcosEnZ[i].getColumnasN()[j].getNodoA().getCordY() <
                            marcosEnZ[i].getColumnasN()[j].getNodoB().getCordY()){
                        nodoAbajo = marcosEnZ[i].getColumnasN()[j].getNodoA();
                        nodoArriba = marcosEnZ[i].getColumnasN()[j].getNodoB();
                    }
                }
            ///////////////////
            }
            if( (nodoAbajo!=null)&&(nodoArriba!=null) ){
                int contadorVigasMarcosZn = 0;
                int contadorVigasMarcosZm = 0;
                for(int j=0;j<512;j++){
                    if(vigasValsZ[j] != null){
                        if( (vigasValsZ[j].getNodoA().getCordX()==nodoArriba.getCordX() )
                          &&(vigasValsZ[j].getNodoA().getCordY()==nodoArriba.getCordY()) ){
                            marcosEnZ[i].getVigasN()[contadorVigasMarcosZn] = vigasValsZ[j];
                            contadorVigasMarcosZn++;
                        }else if( (vigasValsZ[j].getNodoA().getCordX()==nodoAbajo.getCordX()) 
                                &&(vigasValsZ[j].getNodoA().getCordY()==nodoAbajo.getCordY()) ){
                            marcosEnZ[i].getVigasM()[contadorVigasMarcosZm] = vigasValsZ[j];
                            contadorVigasMarcosZm++;
                        }
                    }
                }

            }
        }


        //*************************************************************************************
        
        return marcosEnZ;
    }
    
    private JButton crearBoton(String nombre, char mnemonic,int x,int y,int ancho,int alto,
            ActionListener accion){
        JButton retorno = new JButton(nombre);
        retorno.setMnemonic(mnemonic);
        retorno.setBounds(x,y,ancho,alto);
        retorno.addActionListener(accion);
        
        return retorno;
    }
    
    private ActionListener accionCrearMarcos(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                arrLocalMarcosX = generarMarcosX();
                arrLocalMarcosZ = generarMarcosZ();
                
                int indiceLimpio = 0;
                for(int i=0;i<512;i++){
                    if((arrLocalMarcosX[i]!=null)
                    &&(arrLocalMarcosX[i].getIdentificador().compareTo("Marco no definido")!=0)
                    &&(arrLocalMarcosX[i].calcularAlturaN()!=0)){
                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[indiceLimpio] = arrLocalMarcosX[i];
                        indiceLimpio++;
                    }
                }
                
                llenarTablaMarcos();
            }
        };
        return retorno;
    }
    
    private ActionListener accionAceptar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int filas=0;filas<512;filas++){
                    if( tablaMarcos.getValueAt(filas,0).toString().compareTo("")!=0 ){
                        float altM = 0f;
                        float altO = 0f;
                        try{
                            altM =  Float.parseFloat( (String) tablaMarcos.getValueAt(filas,4) );
                        }catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Formato erroneo, revisar casilla de altura inferior"
                                    +"\nen "+Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getIdentificador()+
                                    ", este campo se considerara como cero",
                                    "ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                        }
                        try{
                            altO = Float.parseFloat((String) tablaMarcos.getValueAt(filas,5));
                        }catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Formato erroneo, revisar casilla de altura superior"
                                    +"\nen "+Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getIdentificador()+
                                    ", este campo se considerara como cero",
                                    "ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                        }
                       
                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].setAlturaM(altM );
                        Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].setAlturaO(altO );
                        
                        if( (tablaMarcos.getValueAt(filas,6).toString().compareTo("Asignar_opcionalmente")==0) 
                        ||(tablaMarcos.getValueAt(filas,6).toString().compareTo("")==0)
                        ||( !Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].isRigidezDesignadaManualmente()) ){
                            float rigid = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].calcularRigidez();
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].setRigidezR(rigid);
                        } else {
                            float rig = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].calcularRigidez();
                            try{
                                rig = Float.parseFloat((String) tablaMarcos.getValueAt(filas,6) );
                            } catch (NumberFormatException ex){
                                JOptionPane.showMessageDialog(null,"Formato erroneo, revisar casilla de rigidez opcional"
                                    +"\nen "+Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getIdentificador()+
                                    ", este valor se obtendra de el calculo del marco estructural...",
                                    "ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
                            }
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].setRigidezR(rig);
                        }
                        
//                        ///prueba
//                        System.out.println("marco: "
//                                +Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getIdentificador()+" "
//                                +Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getColumnasN()[0]+" "
//                                +Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getAlturaN()+" "
//                                +Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getAlturaM()+" "
//                                +Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getAlturaO()+" "
//                                +Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getRigidezR());
                    }
                }
                JOptionPane.showMessageDialog(null,"Cambios Registrados!","AVISO",JOptionPane.INFORMATION_MESSAGE);
                llenarTablaMarcos();
            }
        };
        return retorno;
    }

    public TableColumn[] getColumnasDeTabla() {
        return columnasDeTabla;
    }
    
    public void llenarTablaMarcos(){
        //Se llena la tabla con los valores en el archivo de trabajo antes de hacerla visible
        for(int filas=0;filas<512;filas++){
            for(int cols=0;cols<7;cols++){
                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas]!=null) 
                  &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getIdentificador().compareTo("Marco no definido")!=0)  ){
                    switch(cols){
                        case 0://id marco
                        {
                            tablaMarcos.setValueAt(
                                    Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getIdentificador(),
                                    filas,0);
                        }//break;
                        case 1://columnas n
                        {
//                            String[] nombres = new String[512];
//                            int indiceLimpio = 0;
//                            for(int i=0;i<512;i++){
//                                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getColumnasN()[i]!=null)
//                                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getColumnasN()[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
//                                    nombres[indiceLimpio] = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getColumnasN()[i].getIdentificador();
//                                    indiceLimpio++;
//                                }
//                            }
//                            
//                            JComboBox combo = new JComboBox(nombres);
//                            columnasDeTabla[1].setCellEditor(new DefaultCellEditor(combo) );
                            
                            tablaMarcos.setValueAt(
                                    "Informacion",
                                    filas,1);
                        }//break;
                        case 2://vigas inferiores o vigas m
                        {
                            tablaMarcos.setValueAt(
                                    "Informacion",
                                    filas,2);
                        }//break;
                        case 3://vigas superiores o vigas n
                        {
                            tablaMarcos.setValueAt(
                                    "Informacion",
                                    filas,3);
                        }//break;
                        case 4://hm altura entre pisoinferior
                        {
                            tablaMarcos.setValueAt( String.valueOf(
                                    Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getAlturaM()  
                                                   ),
                                    filas,4);
                        }
                        case 5://ho altura entrepiso superior
                        {
                            tablaMarcos.setValueAt( String.valueOf(
                                    Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getAlturaO()
                                                   ),
                                    filas,5);
                        }
                        case 6://Rigidez opcional
                        {
                            if(  Float.isNaN(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getRigidezR())  ){
                                tablaMarcos.setValueAt(
                                                "Asignar_opcionalmente",
                                                filas,6);
                            }else{
                                tablaMarcos.setValueAt( String.valueOf(
                                    Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas].getRigidezR()
                                                   ),
                                    filas,6);
                            }
                        }
                    }//fin de if
                }//fin de switch
            }//fin de for cols
        }//fin de for filas
    }
    
    private void informacionColumnasN(int fila, int col){
        String nombreMarco = "";
        String mensajeTemp = "";
        if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila]!=null)
        &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador().compareTo("Marco no definido")!=0) ){
            nombreMarco = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador();
            for(int i=0;i<512;i++){
                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getColumnasN()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getColumnasN()[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                    mensajeTemp = mensajeTemp +"\n"+ 
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getColumnasN()[i].getIdentificador();
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
        etiqueta.setText("Columnas de marco "+nombreMarco+":");
        etiqueta.setBounds(25,17,200,15);
        aceptar.setBounds(110,280,80,30);                    

        ventInfoColumnasN = new JFrame("INFORMACION");
        ventInfoColumnasN.setLayout(null);
        ventInfoColumnasN.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventInfoColumnasN.setResizable(false);
        ventInfoColumnasN.setBounds(300,200,300,350);
        Main.getVentDefEst().setEnabled(false);

        aceptar.addActionListener(
            new ActionListener(){                        
                public void actionPerformed(ActionEvent e) {
                    ventInfoColumnasN.setVisible(false); 
                    Main.getAplicacion().setVisible(true);
                    Main.getVentDefEst().setEnabled(true);
                    Main.getVentDefEst().setVisible(true);
                }
            }
        );
        ventInfoColumnasN.add(contenedor);
        ventInfoColumnasN.add(etiqueta);
        ventInfoColumnasN.add(aceptar);
        ventInfoColumnasN.setVisible(true);    
    }
    
    private void informacionVigasM(int fila, int col){
        String nombreMarco = "";
        String mensajeTemp = "";
        if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila]!=null)
        &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador().compareTo("Marco no definido")!=0) ){
            nombreMarco = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador();
            for(int i=0;i<512;i++){
                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getVigasM()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getVigasM()[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                    mensajeTemp = mensajeTemp +"\n"+ 
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getVigasM()[i].getIdentificador();
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
        etiqueta.setText("Vigas inferiores(m) de marco "+nombreMarco+":");
        etiqueta.setBounds(25,17,200,15);
        aceptar.setBounds(110,280,80,30);                    

        ventInfoVigasM = new JFrame("INFORMACION");
        ventInfoVigasM.setLayout(null);
        ventInfoVigasM.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventInfoVigasM.setResizable(false);
        ventInfoVigasM.setBounds(300,200,300,350);
        Main.getVentDefEst().setEnabled(false);

        aceptar.addActionListener(
            new ActionListener(){                        
                public void actionPerformed(ActionEvent e) {
                    ventInfoVigasM.setVisible(false); 
                    Main.getAplicacion().setVisible(true);
                    Main.getVentDefEst().setEnabled(true);
                    Main.getVentDefEst().setVisible(true);
                }
            }
        );
        ventInfoVigasM.add(contenedor);
        ventInfoVigasM.add(etiqueta);
        ventInfoVigasM.add(aceptar);
        ventInfoVigasM.setVisible(true);  
        
    }
    
    private void informacionVigasN(int fila, int col){
        String nombreMarco = "";
        String mensajeTemp = "";
        if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila]!=null)
        &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador().compareTo("Marco no definido")!=0) ){
            nombreMarco = Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getIdentificador();
            for(int i=0;i<512;i++){
                if( (Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getVigasN()[i]!=null)
                &&(Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getVigasN()[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                    mensajeTemp = mensajeTemp +"\n"+ 
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[fila].getVigasN()[i].getIdentificador();
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
        etiqueta.setText("Vigas superiores(n) de marco "+nombreMarco+":");
        etiqueta.setBounds(25,17,200,15);
        aceptar.setBounds(110,280,80,30);                    

        ventInfoVigasN = new JFrame("INFORMACION");
        ventInfoVigasN.setLayout(null);
        ventInfoVigasN.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventInfoVigasN.setResizable(false);
        ventInfoVigasN.setBounds(300,200,300,350);
        Main.getVentDefEst().setEnabled(false);

        aceptar.addActionListener(
            new ActionListener(){                        
                public void actionPerformed(ActionEvent e) {
                    ventInfoVigasN.setVisible(false); 
                    Main.getAplicacion().setVisible(true);
                    Main.getVentDefEst().setEnabled(true);
                    Main.getVentDefEst().setVisible(true);
                }
            }
        );
        ventInfoVigasN.add(contenedor);
        ventInfoVigasN.add(etiqueta);
        ventInfoVigasN.add(aceptar);
        ventInfoVigasN.setVisible(true);  
        
    }
    
    private ActionListener accionBorrar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,"Esta seguro que desea borrar la tabla?",
                        "PREGUNTA",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(opcion == 0){   //Si el usuario pulsa SI
                    for(int filas = 0;filas<512;filas++){
                        for(int cols =0;cols<7;cols++){
                            tablaMarcos.getModel().setValueAt("",filas,cols);
                            Main.getArchivoDeTrabajo().getMarcosDeArchivoEnX()[filas] = new MarcoX();
                        }
                    }

                }//Si el usuario pulsa NO no ocurre nada
            }
        };
        return retorno;
    }
    
    
    
}
