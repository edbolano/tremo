/*
 * BarraMenus.java
 *
 * Created on 5 de diciembre de 2006, 01:29 PM
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

import gui.acciones.EscuchaAccionAbrir;
import gui.acciones.EscuchaAccionAcercaDe;
import gui.acciones.EscuchaAccionApariencias;
import gui.acciones.EscuchaAccionCalcular;
import gui.acciones.EscuchaAccionCerrar;
import gui.acciones.EscuchaAccionDefEstruct;
import gui.acciones.EscuchaAccionDefCriterios;
import gui.acciones.EscuchaAccionDesplaz;
import gui.acciones.EscuchaAccionDetener;
import gui.acciones.EscuchaAccionElimRegMat;
import gui.acciones.EscuchaAccionElimRegSec;
import gui.acciones.EscuchaAccionFacultad;
import gui.acciones.EscuchaAccionFuerzas;
import gui.acciones.EscuchaAccionGenerar3D;
import gui.acciones.EscuchaAccionGuardar;
import gui.acciones.EscuchaAccionGuardarComo;
import gui.acciones.EscuchaAccionInfoMateriales;
import gui.acciones.EscuchaAccionInfoSecciones;
import gui.acciones.EscuchaAccionLicencia;
import gui.acciones.EscuchaAccionNuevo;
import gui.acciones.EscuchaAccionRegMateriales;
import gui.acciones.EscuchaAccionRegSecciones;
import gui.acciones.EscuchaAccionRegenerar3D;
import gui.acciones.EscuchaAccionSalir;
import gui.acciones.EscuchaAccionSimulX;
import gui.acciones.EscuchaAccionSimulZ;
import gui.acciones.EscuchaAccionTutorial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class BarraMenus extends JMenuBar {
    
    private int posicionX = 0;
    private int posicionY = 0;
    private int ancho = 1024;
    private int alto = 25;
    //------------------------
    private JMenu menuArchivo;
    private JMenu menuVer;
    private JMenu menuRegistro;
    private JMenu menuConstruir;
    private JMenu menuSimulacion;
    private JMenu menuResultados;
    private JMenu menuAyuda;
    //---------------------------
    private JMenuItem itemNuevo;
    private JMenuItem itemAbrir;
    private JMenuItem itemGuardar;
    private JMenuItem itemGuardarComo;
    private JMenuItem itemCerrar;
    private JMenuItem itemSalir;
    //---------------------------
    private JMenu menuCambiarApariencia;
    private JRadioButtonMenuItem radioBMIMetal;
    private JRadioButtonMenuItem radioBMIMotif;
    private JRadioButtonMenuItem radioBMIPlatLocal;
    private ButtonGroup grupoApariencias;
    private JMenuItem itemRegen3D;
    //---------------------------
    private JMenuItem itemSecciones;
    private JMenuItem itemMateriales;
    private JMenuItem itemElimRegSec;
    private JMenuItem itemElimRegMat;
    private JMenuItem itemInfoSec;
    private JMenuItem itemInfoMat;
    //----------------------------
    private JMenuItem itemDefEstruct;
    private JMenuItem itemDefCriterios;
    private JMenuItem itemGenerar3D;
    //-----------------------------
    private JMenuItem itemSimulX;
    private JMenuItem itemSimulZ;
    private JMenuItem itemDetener;
    //-----------------------------
    private JMenuItem itemCalcular;
    private JMenuItem itemDesplaz;
    private JMenuItem itemFuerzas;
    //-----------------------------
    private JMenuItem itemAcercaDe;
    private JMenuItem itemLicencia;
    private JMenuItem itemTutorial;
    private JMenuItem itemFacultad;
    
    /** Creates a new instance of BarraMenus */
    public BarraMenus() {
        super();
        this.setBounds(this.posicionX,this.posicionY,this.ancho,this.alto);
        //--------------------menus generales
        this.menuArchivo = this.crearMenu("Archivo",'A',new Ocultar3D() );        
        this.menuVer = this.crearMenu("Ver",'V',new Ocultar3D() );
        this.menuRegistro = this.crearMenu("Registro",'R',new Ocultar3D() );
        this.menuConstruir = this.crearMenu("Construir",'C',new Ocultar3D() );
        this.menuSimulacion = this.crearMenu("Simulacion",'S',new Ocultar3D() );
        this.menuResultados = this.crearMenu("Resultados",'t',new Ocultar3D() );
        this.menuAyuda = this.crearMenu("Ayuda",'y',new Ocultar3D() );
        this.add(this.menuArchivo);
        this.add(this.menuVer);
        this.add(this.menuRegistro);
        this.add(this.menuConstruir);
        this.add(this.menuSimulacion);
        this.add(this.menuResultados);
        this.add(this.menuAyuda);        
        
        //-------------------items archivo
        this.itemNuevo = this.crearItem("Nuevo",'N',new EscuchaAccionNuevo() );
        this.itemAbrir = this.crearItem("Abrir",'A',new EscuchaAccionAbrir() );                
        this.itemGuardar = this.crearItem("Guardar",'G',new EscuchaAccionGuardar() );
        this.itemGuardarComo = this.crearItem("Guardar como...",'u',new EscuchaAccionGuardarComo() );
        this.itemCerrar = this.crearItem("Cerrar",'C',new EscuchaAccionCerrar() );
        this.itemSalir = this.crearItem("Salir",'S',new EscuchaAccionSalir() );
        this.menuArchivo.add(this.itemNuevo);
        this.menuArchivo.add(this.itemAbrir);
        this.menuArchivo.add(this.itemGuardar);
        this.menuArchivo.add(this.itemGuardarComo);
        this.menuArchivo.add(this.itemCerrar);
        this.menuArchivo.add(this.itemSalir);
        
        //-------------------items ver
        this.menuCambiarApariencia = this.crearMenu("Cambiar apariencia",'C',null);
        this.grupoApariencias = new ButtonGroup();
            this.radioBMIMetal = this.crearRadio("Metal/Java",'J',new EscuchaAccionApariencias(),false );
            this.radioBMIMotif = this.crearRadio("Motif/Unix",'U',new EscuchaAccionApariencias(),false );
            this.radioBMIPlatLocal = this.crearRadio("Plataforma Local",'L',new EscuchaAccionApariencias(),true );
            this.grupoApariencias.add(this.radioBMIMetal);
            this.grupoApariencias.add(this.radioBMIMotif);
            this.grupoApariencias.add(this.radioBMIPlatLocal);
            this.menuCambiarApariencia.add(this.radioBMIMetal);
            this.menuCambiarApariencia.add(this.radioBMIMotif);
            this.menuCambiarApariencia.add(this.radioBMIPlatLocal);
        this.itemRegen3D = this.crearItem("Regenerar 3D",'R',new EscuchaAccionRegenerar3D() );
        this.menuVer.add(this.menuCambiarApariencia);
        this.menuVer.add(this.itemRegen3D);
            
        //-------------------items registro
        this.itemSecciones = this.crearItem("Secciones...",'S',new EscuchaAccionRegSecciones() );
        this.itemMateriales = this.crearItem("Materiales...",'M',new EscuchaAccionRegMateriales() );
        this.itemElimRegSec = this.crearItem("Eliminar registro secciones.dat",'c',new EscuchaAccionElimRegSec() );
        this.itemElimRegMat = this.crearItem("Eliminar registro materiales.dat",'t',new EscuchaAccionElimRegMat() );
        this.itemInfoSec = this.crearItem("Informacion secciones",'e',new EscuchaAccionInfoSecciones() );
        this.itemInfoMat = this.crearItem("Informacion materiales",'a',new EscuchaAccionInfoMateriales() );
        this.menuRegistro.add(this.itemSecciones);
        this.menuRegistro.add(this.itemMateriales);
        this.menuRegistro.add(this.itemElimRegSec);
        this.menuRegistro.add(this.itemElimRegMat);
        this.menuRegistro.add(this.itemInfoSec);
        this.menuRegistro.add(this.itemInfoMat);
        
        //-------------------items construir
        this.itemDefEstruct = this.crearItem("Definir Estructura...",'E',new EscuchaAccionDefEstruct() );
        this.itemDefCriterios = this.crearItem("Definir Criterios...",'C',new EscuchaAccionDefCriterios() );
        this.itemGenerar3D = this.crearItem("Generar Escena 3D",'G',new EscuchaAccionGenerar3D() );        
        this.menuConstruir.add(this.itemDefEstruct);
        this.menuConstruir.add(this.itemDefCriterios);
        this.menuConstruir.add(this.itemGenerar3D);
        
        //-------------------items simulacion
        this.itemSimulX = this.crearItem("Simulacion en X",'X',new EscuchaAccionSimulX() );
        this.itemSimulZ = this.crearItem("Simulacion en Z",'Z',new EscuchaAccionSimulZ() );
        this.itemDetener = this.crearItem("Detener simulacion",'D',new EscuchaAccionDetener() );
        this.menuSimulacion.add(this.itemSimulX);
        this.menuSimulacion.add(this.itemSimulZ);
        this.menuSimulacion.add(this.itemDetener);
        ////deshabilitar opciones hasta implementacion
        this.itemSimulX.setEnabled(false);
        this.itemSimulZ.setEnabled(false);
        this.itemDetener.setEnabled(false);
        
        //-------------------items resultados
        this.itemCalcular = this.crearItem("Calcular...",'C',new EscuchaAccionCalcular() );
        this.itemDesplaz = this.crearItem("Desplazamientos",'D',new EscuchaAccionDesplaz() );
        this.itemFuerzas = this.crearItem("Fuerzas",'F',new EscuchaAccionFuerzas() );
        this.menuResultados.add(this.itemCalcular);
        this.menuResultados.add(this.itemDesplaz);
        this.menuResultados.add(this.itemFuerzas);
        
        //-------------------items ayuda
        this.itemAcercaDe = this.crearItem("Acerca de...",'A',new EscuchaAccionAcercaDe() );
        this.itemLicencia = this.crearItem("Licencia",'L',new EscuchaAccionLicencia() );
        this.itemTutorial = this.crearItem("Tutorial",'T',new EscuchaAccionTutorial() );
        this.itemFacultad = this.crearItem("I.C. web site",'w',new EscuchaAccionFacultad() );
        this.menuAyuda.add(this.itemAcercaDe);
        this.menuAyuda.add(this.itemLicencia);
        this.menuAyuda.add(this.itemTutorial);
        this.menuAyuda.add(this.itemFacultad);
        
    }

    public JMenu getMenuArchivo() {
        return menuArchivo;
    }

    public JMenu getMenuVer() {
        return menuVer;
    }

    public JMenu getMenuRegistro() {
        return menuRegistro;
    }

    public JMenu getMenuConstruir() {
        return menuConstruir;
    }

    public JMenu getMenuSimulacion() {
        return menuSimulacion;
    }

    public JMenu getMenuResultados() {
        return menuResultados;
    }

    public JMenu getMenuAyuda() {
        return menuAyuda;
    }

    public JMenuItem getItemNuevo() {
        return itemNuevo;
    }

    public JMenuItem getItemAbrir() {
        return itemAbrir;
    }

    public JMenuItem getItemGuardar() {
        return itemGuardar;
    }

    public JMenuItem getItemGuardarComo() {
        return itemGuardarComo;
    }

    public JMenuItem getItemCerrar() {
        return itemCerrar;
    }

    public JMenuItem getItemSalir() {
        return itemSalir;
    }

    public JMenu getMenuCambiarApariencia() {
        return menuCambiarApariencia;
    }

    public JRadioButtonMenuItem getRadioBMIMetal() {
        return radioBMIMetal;
    }

    public JRadioButtonMenuItem getRadioBMIMotif() {
        return radioBMIMotif;
    }

    public JRadioButtonMenuItem getRadioBMIPlatLocal() {
        return radioBMIPlatLocal;
    }

    public ButtonGroup getGrupoApariencias() {
        return grupoApariencias;
    }

    public JMenuItem getItemRegen3D() {
        return itemRegen3D;
    }

    public JMenuItem getItemSecciones() {
        return itemSecciones;
    }

    public JMenuItem getItemMateriales() {
        return itemMateriales;
    }

    public JMenuItem getItemElimRegSec() {
        return itemElimRegSec;
    }

    public JMenuItem getItemElimRegMat() {
        return itemElimRegMat;
    }

    public JMenuItem getItemDefEstruct() {
        return itemDefEstruct;
    }

    public JMenuItem getItemDefSismo() {
        return itemDefCriterios;
    }

    public JMenuItem getItemSimulX() {
        return itemSimulX;
    }

    public JMenuItem getItemSimulZ() {
        return itemSimulZ;
    }

    public JMenuItem getItemDetener() {
        return itemDetener;
    }

    public JMenuItem getItemDesplaz() {
        return itemDesplaz;
    }

    public JMenuItem getItemFuerzas() {
        return itemFuerzas;
    }

    public JMenuItem getItemAcercaDe() {
        return itemAcercaDe;
    }

    public JMenuItem getItemLicencia() {
        return itemLicencia;
    }

    public JMenuItem getItemTutorial() {
        return itemTutorial;
    }
    
    private JMenu crearMenu(String nombre,char mnemonic,MouseListener accion){
        JMenu retorno = new JMenu(nombre);
        //retorno.setMnemonic(mnemonic);
        retorno.addMouseListener(accion);
        return retorno;
    }
    
    private JMenuItem crearItem(String nombre,char mnemonic,ActionListener accion){
        JMenuItem retorno = new JMenuItem(nombre);
        retorno.setMnemonic(mnemonic);
        retorno.addActionListener(accion);
        return retorno;
    }
    
    private JRadioButtonMenuItem crearRadio(String nombre,char mnemonic,
            ActionListener accion,boolean estaSeleccionado){
        JRadioButtonMenuItem retorno = new JRadioButtonMenuItem(nombre);
        retorno.setMnemonic(mnemonic);
        retorno.addActionListener(accion);
        retorno.setSelected(estaSeleccionado);
        return retorno;
    }
    
    //Clase interna privada para ocultar el bug de java3d con swing que no permite tener la
    //visibilidad de los menus sobre la escena 3d por ser componentes ligeros los asociados a swing
    private class Ocultar3D implements MouseListener{        
        public void mouseClicked(MouseEvent e) {
            //Main.getAplicacion().getUnPanelDeFichas().setSelectedIndex(PanelDeFichas.PANEL_BIENVENIDA);
            //JOptionPane.showMessageDialog(null,"se ejecuta esto?");
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            Main.getAplicacion().getUnPanelDeFichas().setSelectedIndex(PanelDeFichas.PANEL_BIENVENIDA);
            //JOptionPane.showMessageDialog(null,"se ejecuta esto?");
        }

        public void mouseExited(MouseEvent e) {
        }
    }
    
}
