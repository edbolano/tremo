/*
 * VentanaDefinirCriterios.java
 *
 * Created on 5 de diciembre de 2006, 01:31 PM
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
public class VentanaDefinirCriterios extends JFrame{
    private static final int POSICION_X = 200;
    private static final int POSICION_Y = 150;
    private static final int ANCHO = 600;
    private static final int ALTO = 420;
    private JTextField campo_c;
    private JTextField campo_aCero;
    private JTextField campo_Ta;
    private JTextField campo_Tb;
    private JTextField campo_r;
    private JTextField campo_Qx;
    private JTextField campo_Qz;
    private JTextField campoOpcional_TX;
    private JTextField campoOpcional_TZ;
//    private JTextField campoLongitudX;
//    private JTextField campoLongitudZ;
    
    
    /**
     * Creates a new instance of VentanaDefinirCriterios
     */
    public VentanaDefinirCriterios() {
        //Se definen los parametros generales de la ventana "Definir Estructura"
        super("Definir Criterios");
        Main.getAplicacion().setEnabled(false);
        this.setBounds(this.POSICION_X,this.POSICION_Y,this.ANCHO,this.ALTO); 
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        
        //Agregacion de etiquetas
        JLabel etiCampo_c = new JLabel("c");
        JLabel etiCampo_aCero = new JLabel("a0");
        JLabel etiCampo_Ta = new JLabel("Ta");
        JLabel etiCampo_Tb = new JLabel("Tb");
        JLabel etiCampo_r = new JLabel("r");
        JLabel etiCampo_Qx = new JLabel("Qx");
        JLabel etiCampo_Qz = new JLabel("Qz");
        JLabel etiCampoOpcional_TX = new JLabel("Tx");
        JLabel etiCampoOpcional_TZ = new JLabel("Tz");
        JLabel etiOpcional = new JLabel("(opcional)");
        JLabel etiFormulaT = new JLabel("T= 0.09H /(L)^1/2");
//        JLabel etiLongitudX = new JLabel("Longitud X(m)");
//        JLabel etiLongitudZ = new JLabel("Longitud Z(m)");
        etiCampo_c.setBounds(40,30,30,20);
        etiCampo_aCero.setBounds(40,70,30,20);
        etiCampo_Ta.setBounds(40,110,30,20);
        etiCampo_Tb.setBounds(40,150,30,20);
        etiCampo_r.setBounds(40,190,30,20);
        etiCampo_Qx.setBounds(40,230,30,20);
        etiCampo_Qz.setBounds(40,270,30,20);
        etiCampoOpcional_TX.setBounds(40,320,50,20);
        etiCampoOpcional_TZ.setBounds(40,350,50,20);        
        etiOpcional.setBounds(70,370,50,20);
        etiFormulaT.setBounds(30,300,110,20);
//        etiLongitudX.setBounds();
//        etiLongitudZ.setBounds();
        this.add(etiCampo_c);
        this.add(etiCampo_aCero);
        this.add(etiCampo_Ta);
        this.add(etiCampo_Tb);
        this.add(etiCampo_r);
        this.add(etiCampo_Qx);
        this.add(etiCampo_Qz);
        this.add(etiCampoOpcional_TX);
        this.add(etiCampoOpcional_TZ);
        this.add(etiOpcional);
        this.add(etiFormulaT);
        
        //Agreagacion de campos de texto
        this.campo_c = new JTextField();
        this.campo_aCero = new JTextField();
        this.campo_Ta = new JTextField();
        this.campo_Tb = new JTextField();
        this.campo_r = new JTextField();
        this.campo_Qx = new JTextField();
        this.campo_Qz = new JTextField();
        this.campoOpcional_TX = new JTextField();
        this.campoOpcional_TZ = new JTextField();
        this.campo_c.setBounds(60,30,60,20);
        this.campo_aCero.setBounds(60,70,60,20);
        this.campo_Ta.setBounds(60,110,60,20);
        this.campo_Tb.setBounds(60,150,60,20);
        this.campo_r.setBounds(60,190,60,20);
        this.campo_Qx.setBounds(60,230,60,20);
        this.campo_Qz.setBounds(60,270,60,20);
        this.campoOpcional_TX.setBounds(60,320,60,20);
        this.campoOpcional_TZ.setBounds(60,350,60,20);
        this.add(this.campo_c);
        this.add(this.campo_aCero);
        this.add(this.campo_Ta);
        this.add(this.campo_Tb);
        this.add(this.campo_r);
        this.add(this.campo_Qx);
        this.add(this.campo_Qz);
        this.add(this.campoOpcional_TX);
        this.add(this.campoOpcional_TZ);
        
        //Agregacion de botones
        this.add(this.crearBoton("Aceptar",'A',
                (this.ANCHO-95),80,
                80,25,accionAceptar()) );
        this.add(this.crearBoton("Ayuda",'y',
                (this.ANCHO-95),30,
                80,25,accionAyuda()) );
        this.add(this.crearBoton("Cerrar",'C',
                (this.ANCHO-95),360,
                80,25,accionCerrar() ));
        
        //Agregacion de la imagen haciendo uso de la clase PanelImagen del paquete gui
        this.add(new PanelImagen("lib//imagenes//espectro.itr",
                150,30,
                435,320 ));
        this.llenarCampos();
        
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
                try{
                    float criterio = Float.parseFloat( campo_c.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_c(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"c\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campo_aCero.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_aCero(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"a0\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campo_Ta.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_Ta(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"Ta\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campo_Tb.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_Tb(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"Tb\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campo_r.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_r(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"r\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campo_Qx.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_Qx(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"Qx\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campo_Qz.getText() );
                    Main.getArchivoDeTrabajo().setCriterio_Qz(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo \"Qz\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campoOpcional_TX.getText() );
                    Main.getArchivoDeTrabajo().setCriterioOpcional_TX(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo opcional \"Tx\", y este valor se ignorará");
                }
                try{
                    float criterio = Float.parseFloat( campoOpcional_TZ.getText() );
                    Main.getArchivoDeTrabajo().setCriterioOpcional_TZ(criterio);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Se ha ingresado un valor incorrecto en el " +
                            "\ncampo opcional \"Tz\", y este valor se ignorará");
                }
                
                
                llenarCampos();
                JOptionPane.showMessageDialog(null,"Cambios Registrados","INFORMACION",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        return retorno;
    }
    
    private ActionListener accionAyuda(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Normas Tecnicas Complementarias para diseño por sismo en la pagina 55","INFORMACION",JOptionPane.INFORMATION_MESSAGE);
                File norma = new File("lib//tutorial//norma.pdf");
                String ruta = "";
                if(norma.exists()&&norma.isFile()){
                    ruta = norma.getPath();
                }else {
                    JOptionPane.showMessageDialog(null,"No se puede encontrar el archivo Norma");
                }
                File reglamento = new File("lib//tutorial//reglamento.pdf");
                String ruta2 = "";
                if(reglamento.exists()&&reglamento.isFile()){
                    ruta2 = reglamento.getPath();
                }else {
                    JOptionPane.showMessageDialog(null,"No se puede encontrar el archivo Reglamento");
                }

                try {
                    Process pp = Runtime.getRuntime().exec("explorer "+ruta2);
                    Process p = Runtime.getRuntime().exec("explorer "+ruta);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        return retorno;
    }
    
    private ActionListener accionCerrar(){
        ActionListener retorno = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.getAplicacion().setVisible(true);
                Main.getAplicacion().setEnabled(true);
            }
        };
        return retorno;
    }
    
    private void llenarCampos(){
        this.campo_c.setText(""+Main.getArchivoDeTrabajo().getCriterio_c() );
        this.campo_aCero.setText(""+Main.getArchivoDeTrabajo().getCriterio_aCero() );
        this.campo_Ta.setText(""+Main.getArchivoDeTrabajo().getCriterio_Ta() );
        this.campo_Tb.setText(""+Main.getArchivoDeTrabajo().getCriterio_Tb() );
        this.campo_r.setText(""+Main.getArchivoDeTrabajo().getCriterio_r() );
        this.campo_Qx.setText(""+Main.getArchivoDeTrabajo().getCriterio_Qx() );
        this.campo_Qz.setText(""+Main.getArchivoDeTrabajo().getCriterio_Qz() );
        this.campoOpcional_TX.setText(""+Main.getArchivoDeTrabajo().getCriterioOpcional_TX() );
        this.campoOpcional_TZ.setText(""+Main.getArchivoDeTrabajo().getCriterioOpcional_TZ() );
    }
}
