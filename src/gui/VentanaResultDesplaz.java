/*
 * VentanaResultDesplaz.java
 *
 * Created on 5 de diciembre de 2006, 06:04 PM
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

import gui.panelesVentDefinirEstruct.ModeloTablaGeneralSinIndices;
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
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class VentanaResultDesplaz extends JFrame{
    private static final int POSICION_X = 200;
    private static final int POSICION_Y = 150;
    private static final int ANCHO = 600;
    private static final int ALTO = 420;
    
    private JButton botonCerrar;
    private JPanel panelResultadosEntrepisos;
    private JTable tablaResultEntrepisos;
    private JFrame ventResultMarcosX;
    private JFrame ventResultMarcosZ;
    private JTable tablaResultMarcosX;
    private JTable tablaResultMarcosZ;
    private JButton botonCerrarX;
    private JButton botonCerrarZ;
    
    /** Creates a new instance of VentanaResultDesplaz */
    public VentanaResultDesplaz() {
        //Se definen los parametros generales de la ventana resultados
        super("Resultados Desplazamientos");
        Main.getAplicacion().setEnabled(false);
        this.setBounds(this.POSICION_X,this.POSICION_Y,this.ANCHO,this.ALTO); 
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        
        //se agregan etiquetas y campos de periodos de vibracion
        JLabel etiResultados = new JLabel("Tabla de resultados de entrepisos (para conocer detalles de cada entrepiso haga click en la casilla informacion) ");
        etiResultados.setBounds(8,10,550,15);
        this.add(etiResultados);
        JLabel etiPeriodoX = new JLabel("Tx (segundos):");
        JLabel etiPeriodoZ = new JLabel("Tz (segundos):");
        etiPeriodoX.setBounds(8,365,80,15);
        etiPeriodoZ.setBounds(220,365,80,15);
        this.add(etiPeriodoX);
        this.add(etiPeriodoZ);
        JTextField campoPeriodoX = new JTextField();
        JTextField campoPeriodoZ = new JTextField();
        campoPeriodoX.setBounds(85,365,80,15);
        campoPeriodoZ.setBounds(295,365,80,15);
        campoPeriodoX.setEditable(false);
        campoPeriodoZ.setEditable(false);
        campoPeriodoX.setBackground(Color.LIGHT_GRAY);
        campoPeriodoZ.setBackground(Color.LIGHT_GRAY);
        campoPeriodoX.setText(""+Main.getArchivoDeTrabajo().getCriterioOpcional_TX() );
        campoPeriodoZ.setText(""+Main.getArchivoDeTrabajo().getCriterioOpcional_TZ() );
        this.add(campoPeriodoX);
        this.add(campoPeriodoZ);
        
        
        //Se define el panel con la tabla de resultados
        panelResultadosEntrepisos = crearTablaResultadosEntrepisos();
        panelResultadosEntrepisos.setBounds(5,30,(this.ANCHO-20),(this.ALTO-100) );
        this.add(panelResultadosEntrepisos);
        
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
    
    private JPanel crearTablaResultadosEntrepisos(){
        JPanel retorno = new JPanel();
        ////////--------------------------------------------------------//////////
        ////////--------------------------------------------------------//////////
        final String[] TITULOS = {"INFORMACION","Entrepiso id","C.T. en X(m)","C.T. en Z(m)",
        "C.M. en X(m)","C.M. en Z(m)","Vx (Ton)","Vz (Ton)","Desplaz. en X","Desplaz. en Z"};
                
        JPanel contenedor = new JPanel();                
        contenedor.setBounds(25,50,400,300);
        contenedor.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane();  

        tablaResultEntrepisos = new JTable();
        tablaResultEntrepisos.setModel(new ModeloTablaGeneralSinIndices(20,10) );
        tablaResultEntrepisos.setSelectionBackground(Color.LIGHT_GRAY);
        tablaResultEntrepisos.setRowSelectionAllowed(true);
        tablaResultEntrepisos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        JTextField campoTextoInfo = new JTextField();
        campoTextoInfo.setBackground(Color.LIGHT_GRAY);
        campoTextoInfo.setEditable(false);

        TableColumn[] columnasDeTabla = new TableColumn[10];                
        for(int i=0;i<10;i++){
            columnasDeTabla[i] = tablaResultEntrepisos.getColumnModel().getColumn(i);
            columnasDeTabla[i].setHeaderValue( TITULOS[i] );
            columnasDeTabla[i].setCellEditor(new DefaultCellEditor(campoTextoInfo) );                    
        }
        columnasDeTabla[0].setMinWidth(80);
        columnasDeTabla[0].setResizable(false);

        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                //col informacion
                tablaResultEntrepisos.setValueAt("Click aqui",entrepiso,0);
                
                //col entrepiso id
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador(),
                        entrepiso,1);
                
                //col C.T. en X
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionX(),
                        entrepiso,2);
                
                //col C.T. en Z
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionZ(),
                        entrepiso,3);
                
                //col C.M. en X
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[1],
                        entrepiso,4);
                
                //col C.M. en Z
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[2],
                        entrepiso,5);
                
                //col V (cortante) en X
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX(),
                        entrepiso,6);
                
                //col V (cortante) en Z
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteZ(),
                        entrepiso,7);
                
                //col Desplazamiento relativo en X
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoX(),
                        entrepiso,8);
                
                //col Desplazamiento relativo en Z
                tablaResultEntrepisos.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoZ(),
                        entrepiso,9);
            }
        }

        scroll.setViewportView(tablaResultEntrepisos);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        contenedor.add(scroll);
        contenedor.setVisible(true);  
        
        ////////////////////consultas relativas a cada celda//////////////////////
        tablaResultEntrepisos.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int y = e.getY();
                int x = e.getX();
                String[] posiciones = new String[3];
                for(int i=0;i<3;i++){
                    posiciones[i] = (String)tablaResultEntrepisos.getColumnModel().getColumn(i).getHeaderValue();
                }
                int fila = y/16;
                int columna = x/80;
                
                if(  (columna==0)  ){
                    if(TITULOS[columna].compareTo(posiciones[columna])==0){
                        switch (columna){
                            case 0://entrepiso id
                                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila]!=null)
                                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[fila].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                                    String[] opciones = {"Marcos en X","Marcos en Z"};
                                    int opcion = JOptionPane.showOptionDialog(null,"Seleccione la opcion que desea consultar:","CONSULTA",
                                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);
                                    if(opcion==0){//selecciono marcos X
                                        informacionResultadosMarcosX(fila);
                                    }else if(opcion==1){//selecciono marcos Z
                                        informacionResultadosMarcosZ(fila);
                                    }
                                }
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
        ////////--------------------------------------------------------//////////
        ////////--------------------------------------------------------//////////
        return contenedor;
    }
    
    private void informacionResultadosMarcosX(int indiceEntrepiso){
        //parametros generales de la ventana
        ventResultMarcosX = new JFrame("Resultados: "+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getIdentificador() );
        Main.getAplicacion().setEnabled(false);
        this.setEnabled(false);
        ventResultMarcosX.setBounds(this.POSICION_X+25,this.POSICION_Y+25,this.ANCHO,this.ALTO); 
        ventResultMarcosX.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventResultMarcosX.setResizable(false);
        ventResultMarcosX.setLayout(null);
        
        //se agregan etiquetas y campos de periodos de vibracion
        JLabel etiResultados = new JLabel("Tabla de resultados de marcos en X de "+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getIdentificador() );
        etiResultados.setBounds(8,10,550,15);
        ventResultMarcosX.add(etiResultados);
        JLabel etiVTotal = new JLabel("El cortante V TOTAL es el mas desfavorable entre \"VX  +  0.3VY(torsion)\" y \"0.3VX  +  VY(torsion)\" ");
        etiVTotal.setBounds(8,365,500,15);
        ventResultMarcosX.add(etiVTotal);
        
        //Se define el panel con la tabla de resultados
        JPanel panelResultadosMarcosX = new JPanel();
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        final String[] TITULOS = {"Marco id","Rig(Ton/cm)","VxDirecto(Ton)","VxTorsion(Ton)",
        "VxTotal(Ton)","Vz Torsion(Ton)","V TOTAL(Ton)","Desplaz(cm)","Distorsion"};
                               
        //panelResultadosMarcosX.setBounds(25,50,400,300);
        panelResultadosMarcosX.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane();  

        tablaResultMarcosX = new JTable();
        tablaResultMarcosX.setModel(new ModeloTablaGeneralSinIndices(512,9) );
        tablaResultMarcosX.setSelectionBackground(Color.LIGHT_GRAY);
        tablaResultMarcosX.setRowSelectionAllowed(true);
        tablaResultMarcosX.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        JTextField campoTextoInfo = new JTextField();
        campoTextoInfo.setBackground(Color.LIGHT_GRAY);
        campoTextoInfo.setEditable(false);

        TableColumn[] columnasDeTabla = new TableColumn[9];                
        for(int i=0;i<9;i++){
            columnasDeTabla[i] = tablaResultMarcosX.getColumnModel().getColumn(i);
            columnasDeTabla[i].setHeaderValue( TITULOS[i] );
            columnasDeTabla[i].setCellEditor(new DefaultCellEditor(campoTextoInfo) );                    
        }

        for(int marco=0;marco<50;marco++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                //col Marco id
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getIdentificador(),
                        marco,0);
                
                //col rigidez
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getRigidezR(),
                        marco,1);
                
                //col Vx Directo
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getCortEfVxDirecto(),
                        marco,2);
                
                //col Vx Torsion
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getCortEfVxTorsion(),
                        marco,3);
                
                //col Vx Total
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getCortEfVxTotal(),
                        marco,4);
                
                //col Vz Torsion
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getCortEfVzTorsion(),
                        marco,5);
                
                //col V TOTAL
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getCortanteTotal(),
                        marco,6);
                
                //col Desplazamiento
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getDesplazRelativo(),
                        marco,7);
                
                //col Distorsion
                tablaResultMarcosX.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosX()[marco].getDistorsion(),
                        marco,8);
                
            }
        }
        scroll.setViewportView(tablaResultMarcosX);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panelResultadosMarcosX.add(scroll);
        panelResultadosMarcosX.setVisible(true);  
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        panelResultadosMarcosX.setBounds(5,30,(this.ANCHO-20),(this.ALTO-100) );
        ventResultMarcosX.add(panelResultadosMarcosX);
        
        //Agregacion de boton de cerrado accesible desde toda la clase y con metodos de acceso
        botonCerrarX = new JButton("Cerrar");
        botonCerrarX.setMnemonic('C');
        botonCerrarX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventResultMarcosX.setVisible(false);
                Main.getAplicacion().setVisible(true);
                Main.getAplicacion().setEnabled(true);
                setVisible(true);
                setEnabled(true);
            }
        });
        botonCerrarX.setBounds((this.ANCHO-90),(this.ALTO-50),80,25);
        ventResultMarcosX.add(botonCerrarX);
        
        //Se hace visible esta ventana al final del constructor
        ventResultMarcosX.setVisible(true);
    }
    
    private void informacionResultadosMarcosZ(int indiceEntrepiso){
        //parametros generales de la ventana
        ventResultMarcosZ = new JFrame("Resultados: "+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getIdentificador() );
        Main.getAplicacion().setEnabled(false);
        this.setEnabled(false);
        ventResultMarcosZ.setBounds(this.POSICION_X+25,this.POSICION_Y+25,this.ANCHO,this.ALTO); 
        ventResultMarcosZ.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventResultMarcosZ.setResizable(false);
        ventResultMarcosZ.setLayout(null);
        
        //se agregan etiquetas y campos de periodos de vibracion
        JLabel etiResultados = new JLabel("Tabla de resultados de marcos en Z de "+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getIdentificador() );
        etiResultados.setBounds(8,10,550,15);
        ventResultMarcosZ.add(etiResultados);
        JLabel etiVTotal = new JLabel("El cortante V TOTAL es el mas desfavorable entre \"VZ  +  0.3VX(torsion)\" y \"0.3VZ  +  VX(torsion)\" ");
        etiVTotal.setBounds(8,365,500,15);
        ventResultMarcosZ.add(etiVTotal);
        
        //Se define el panel con la tabla de resultados
        JPanel panelResultadosMarcosZ = new JPanel();
        //////////////////panel con scroll de resultados marcos Z////////////////////////////////////////
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        final String[] TITULOS = {"Marco id","Rig(Ton/cm)","VzDirecto(Ton)","VzTorsion(Ton)",
        "VzTotal(Ton)","Vx Torsion(Ton)","V TOTAL(Ton)","Desplaz(cm)","Distorsion"};
                               
        panelResultadosMarcosZ.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane();  

        tablaResultMarcosZ = new JTable();
        tablaResultMarcosZ.setModel(new ModeloTablaGeneralSinIndices(512,9) );
        tablaResultMarcosZ.setSelectionBackground(Color.LIGHT_GRAY);
        tablaResultMarcosZ.setRowSelectionAllowed(true);
        tablaResultMarcosZ.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        JTextField campoTextoInfo = new JTextField();
        campoTextoInfo.setBackground(Color.LIGHT_GRAY);
        campoTextoInfo.setEditable(false);

        TableColumn[] columnasDeTabla = new TableColumn[9];                
        for(int i=0;i<9;i++){
            columnasDeTabla[i] = tablaResultMarcosZ.getColumnModel().getColumn(i);
            columnasDeTabla[i].setHeaderValue( TITULOS[i] );
            columnasDeTabla[i].setCellEditor(new DefaultCellEditor(campoTextoInfo) );                    
        }

        for(int marco=0;marco<50;marco++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                //col Marco id
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getIdentificador(),
                        marco,0);
                
                //col rigidez
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getRigidezR(),
                        marco,1);
                
                //col Vz Directo
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getCortEfVzDirecto(),
                        marco,2);
                
                //col Vz Torsion
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getCortEfVzTorsion(),
                        marco,3);
                
                //col Vz Total
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getCortEfVzTotal(),
                        marco,4);
                
                //col Vx Torsion
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getCortEfVxTorsion(),
                        marco,5);
                
                //col V TOTAL
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getCortanteTotal(),
                        marco,6);
                
                //col Desplazamiento
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getDesplazRelativo(),
                        marco,7);
                
                //col Distorsion
                tablaResultMarcosZ.setValueAt(
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[indiceEntrepiso].getMarcosZ()[marco].getDistorsion(),
                        marco,8);
                
            }
        }
        scroll.setViewportView(tablaResultMarcosZ);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panelResultadosMarcosZ.add(scroll);
        panelResultadosMarcosZ.setVisible(true);  
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        //////////////////panel con scroll de resultados marcos X////////////////////////////////////////
        panelResultadosMarcosZ.setBounds(5,30,(this.ANCHO-20),(this.ALTO-100) );
        ventResultMarcosZ.add(panelResultadosMarcosZ);
        
        //Agregacion de boton de cerrado accesible desde toda la clase y con metodos de acceso
        botonCerrarZ = new JButton("Cerrar");
        botonCerrarZ.setMnemonic('C');
        botonCerrarZ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventResultMarcosZ.setVisible(false);
                Main.getAplicacion().setVisible(true);
                Main.getAplicacion().setEnabled(true);
                setVisible(true);
                setEnabled(true);
            }
        });
        botonCerrarZ.setBounds((this.ANCHO-90),(this.ALTO-50),80,25);
        ventResultMarcosZ.add(botonCerrarZ);
        
        //Se hace visible esta ventana al final del constructor
        ventResultMarcosZ.setVisible(true);
    }
    
}
