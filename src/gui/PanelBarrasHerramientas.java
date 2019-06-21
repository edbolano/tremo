/*
 * PanelBarrasHerramientas.java
 *
 * Created on 15 de diciembre de 2006, 11:38 AM
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
import gui.acciones.EscuchaAccionDefCriterios;
import gui.acciones.EscuchaAccionDefEstruct;
import gui.acciones.EscuchaAccionDesplaz;
import gui.acciones.EscuchaAccionDetener;
import gui.acciones.EscuchaAccionFacultad;
import gui.acciones.EscuchaAccionFuerzas;
import gui.acciones.EscuchaAccionGenerar3D;
import gui.acciones.EscuchaAccionGuardar;
import gui.acciones.EscuchaAccionGuardarComo;
import gui.acciones.EscuchaAccionLicencia;
import gui.acciones.EscuchaAccionNuevo;
import gui.acciones.EscuchaAccionRegMateriales;
import gui.acciones.EscuchaAccionRegSecciones;
import gui.acciones.EscuchaAccionRegenerar3D;
import gui.acciones.EscuchaAccionSimulX;
import gui.acciones.EscuchaAccionSimulZ;
import gui.acciones.EscuchaAccionTutorial;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class PanelBarrasHerramientas extends JPanel {
    private final int POS_X = 0;
    private final int POS_Y = 25;
    private final int ANCHO = 980;//1024;
    private final int ALTO = 43;
    
    //botones tool bar archivo
    private JToolBar toolsArchivo;
    private JButton botonNuevo;
    private JButton botonAbrir;
    private JButton botonGuardar;
    private JButton botonGuardarComo;
    
    //botones tool bar ver
    private JToolBar toolsVer;
    private JButton botonJava;
    private JButton botonMotif;
    private JButton botonLocal;
    private JButton botonRegen;
        
    //botones tool bar registro
    private JToolBar toolsRegistro;
    private JButton botonSecciones;
    private JButton botonMateriales;
    private JButton botonElimSecciones;
    private JButton botonElimMateriales;
    
    //botones tool bar construir
    private JToolBar toolsConstruir;
    private JButton botonDefEstructura;
    private JButton botonDefCriterios;
    private JButton botonGenerar3D;
    
    //botones tool bar simulacion
    private JToolBar toolsSimulacion;
    private JButton botonSimulX;
    private JButton botonSimulZ;
    private JButton botonDetener;
    
    //botones tool bar resultados
    private JToolBar toolsResultados;
    private JButton botonCalcular;
    private JButton botonDesplazam;
    private JButton botonFuerzas;
    
    //botones tool bar ayuda
    private JToolBar toolsAyuda;
    private JButton botonAcerca;
    private JButton botonLicencia;
    private JButton botonTutorial;
    private JButton botonFacultad;
    
    /** Creates a new instance of PanelBarrasHerramientas */
    public PanelBarrasHerramientas() {
        super();
        this.setBounds(POS_X, POS_Y, ANCHO, ALTO);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new FlowLayout(FlowLayout.LEFT) );        
        
        //*************ARCHIVO**********************************
        toolsArchivo = new JToolBar("Herramientas archivo");
        botonNuevo = crearBotonDeTool("lib//iconos//New24.itr","Crea un nuevo proyecto",new EscuchaAccionNuevo());
        botonAbrir = crearBotonDeTool("lib//iconos//Open24.itr","Abre un proyecto existente...",new EscuchaAccionAbrir());
        botonGuardar = crearBotonDeTool("lib//iconos//Save24.itr","Guardar",new EscuchaAccionGuardar());
        botonGuardarComo = crearBotonDeTool("lib//iconos//Save-as24.itr","Guardar como...",new EscuchaAccionGuardarComo());
        toolsArchivo.add(botonNuevo);
        toolsArchivo.add(botonAbrir);
        toolsArchivo.add(botonGuardar);
        toolsArchivo.add(botonGuardarComo);
        this.add(toolsArchivo);
        
        //*************VER****************************************
        toolsVer = new JToolBar("Herramientas de vista");
        botonJava = crearBotonDeTool("lib//iconos//java.itr","Cambia la apariencia de la interfaz a la apariencia nativa de java",new EscuchaAccionApariencias() );
        botonMotif = crearBotonDeTool("lib//iconos//motif.itr","Cambia la apariencia de la interfaz a la aparienca de UNIX motif",new EscuchaAccionApariencias() );
        botonLocal = crearBotonDeTool("lib//iconos//local.itr","Cambia la apariencia de la interfaz a la apariencia de la plataforma local",new EscuchaAccionApariencias() );
        botonRegen = crearBotonDeTool("lib//iconos//regen.itr","Regenera la vista de la escena tridimensional en caso de perder de vista la estructura",new EscuchaAccionRegenerar3D() );
        toolsVer.add(botonJava);
        toolsVer.add(botonMotif);
        toolsVer.add(botonLocal);
        toolsVer.add(botonRegen);
        this.add(toolsVer);
        
        //*************REGISTRO**********************************
        toolsRegistro = new JToolBar("Registro");                
        botonSecciones = crearBotonDeTool("lib//iconos//secciones.itr","Registro de secciones",new EscuchaAccionRegSecciones() );
        botonMateriales = crearBotonDeTool("lib//iconos//materiales.itr","Registro de materiales",new EscuchaAccionRegMateriales() );
        toolsRegistro.add(botonSecciones);
        toolsRegistro.add(botonMateriales);
        this.add(toolsRegistro);
        
        //*************CONSTRUIR**********************************
        toolsConstruir = new JToolBar("Construir");
        botonDefEstructura = crearBotonDeTool("lib//iconos//estructura.itr","Definir parametros de estructura",new EscuchaAccionDefEstruct() );
        botonDefCriterios = crearBotonDeTool("lib//iconos//criterio.itr","Definir criterios",new EscuchaAccionDefCriterios() );
        botonGenerar3D = crearBotonDeTool("lib//iconos//generar.itr","Generar objetos creados en la escena tridimensional",new EscuchaAccionGenerar3D() );
        toolsConstruir.add(botonDefEstructura);
        toolsConstruir.add(botonDefCriterios);
        toolsConstruir.add(botonGenerar3D);
        this.add(toolsConstruir);
        
        //*************SIMULACION**********************************
        toolsSimulacion = new JToolBar("Simulacion");
        botonSimulX = crearBotonDeTool("lib//iconos//simulx.itr","Inicia simulacion de la estructura en el sentido X",new EscuchaAccionSimulX() );
        botonSimulZ = crearBotonDeTool("lib//iconos//simulz.itr","Inicia simulacion de la estructura en el sentido Z",new EscuchaAccionSimulZ() );
        botonDetener = crearBotonDeTool("lib//iconos//detener.itr","Detiene la simulacion",new EscuchaAccionDetener() );
        toolsSimulacion.add(botonSimulX);
        toolsSimulacion.add(botonSimulZ);
        toolsSimulacion.add(botonDetener);
        this.add(toolsSimulacion);
        //deshabilitar opciones hasta implementacion
        this.botonSimulX.setEnabled(false);
        this.botonSimulZ.setEnabled(false);
        this.botonDetener.setEnabled(false);
        
        //*************RESULTADOS**********************************
        toolsResultados = new JToolBar("Resultados");
        botonCalcular = crearBotonDeTool("lib//iconos//calcular.itr","Realiza el calculo de cortantes, distribuciones, torsiones y desplazamientos...",
                new EscuchaAccionCalcular() );
        botonDesplazam = crearBotonDeTool("lib//iconos//desplaza.itr","Muestra los desplazamientos de cada marco",new EscuchaAccionDesplaz() );
        botonFuerzas = crearBotonDeTool("lib//iconos//fuerza.itr","Muestra las fuerzas resultantes en cada marco",new EscuchaAccionFuerzas() );
        toolsResultados.add(botonCalcular);
        toolsResultados.add(botonDesplazam);
        toolsResultados.add(botonFuerzas);
        this.add(toolsResultados);
        
        //*************AYUDA**********************************
        toolsAyuda = new JToolBar("Ayuda");
        botonAcerca = crearBotonDeTool("lib//iconos//acerca.itr","Acerca de...",new EscuchaAccionAcercaDe() );
        botonLicencia = crearBotonDeTool("lib//iconos//licencia.itr","Despliega la licencia", new EscuchaAccionLicencia() );
        botonTutorial = crearBotonDeTool("lib//iconos//tutorial.itr","Abre un documento html con el tutorial",new EscuchaAccionTutorial() );
        botonFacultad = crearBotonDeTool("lib//iconos//facultad.itr","Abre un explorador con la pagina de la Facultad de Ingenieria Civil de la Universidad Veracruzana",new EscuchaAccionFacultad() );
        toolsAyuda.add(botonAcerca);
        toolsAyuda.add(botonLicencia);
        toolsAyuda.add(botonTutorial);
        toolsAyuda.add(botonFacultad);
        this.add(toolsAyuda);
        
        
    }
    
    private JButton crearBotonDeTool(String rutaImagen,String toolTip,ActionListener accion){
        JButton retorno = new JButton(new ImageIcon(rutaImagen) );
        retorno.setToolTipText(toolTip);
        retorno.addActionListener(accion);
        return retorno;
    }

    public JButton getBotonNuevo() {
        return botonNuevo;
    }

    public JButton getBotonAbrir() {
        return botonAbrir;
    }

    public JButton getBotonGuardar() {
        return botonGuardar;
    }

    public JButton getBotonGuardarComo() {
        return botonGuardarComo;
    }

    public JButton getBotonSecciones() {
        return botonSecciones;
    }

    public JButton getBotonMateriales() {
        return botonMateriales;
    }

    public JButton getBotonElimSecciones() {
        return botonElimSecciones;
    }

    public JButton getBotonElimMateriales() {
        return botonElimMateriales;
    }

    public JButton getBotonDefEstructura() {
        return botonDefEstructura;
    }

    public JButton getBotonDefCriterios() {
        return botonDefCriterios;
    }

    public JButton getBotonSimulX() {
        return botonSimulX;
    }

    public JButton getBotonSimulZ() {
        return botonSimulZ;
    }

    public JButton getBotonDetener() {
        return botonDetener;
    }

    public JButton getBotonDesplazam() {
        return botonDesplazam;
    }

    public JButton getBotonFuerzas() {
        return botonFuerzas;
    }

    public JButton getBotonTutorial() {
        return botonTutorial;
    }

    public JToolBar getToolsVer() {
        return toolsVer;
    }

    public void setToolsVer(JToolBar toolsVer) {
        this.toolsVer = toolsVer;
    }

    public JButton getBotonJava() {
        return botonJava;
    }

    public void setBotonJava(JButton botonJava) {
        this.botonJava = botonJava;
    }

    public JButton getBotonMotif() {
        return botonMotif;
    }

    public void setBotonMotif(JButton botonMotif) {
        this.botonMotif = botonMotif;
    }

    public JButton getBotonLocal() {
        return botonLocal;
    }

    public void setBotonLocal(JButton botonLocal) {
        this.botonLocal = botonLocal;
    }
    
}
