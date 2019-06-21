/*
 * PanelDeFichas.java
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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import tridimensional.Panel3D;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class PanelDeFichas extends JTabbedPane {
    
    public static final int PANEL_BIENVENIDA = 0;
    //public static final int PANEL_INSTRUCCIONES = 1;
    public static final int PANEL_VISTA_UNO = 1;
    public static final int PANEL_VISTA_DOS = 2;
    private JPanel paneles[] = new JPanel[4];
    public static final int POSICION_X = 0;
    public static final int POSICION_Y = 68;
    public static final int ANCHO =  1020;
    public static final int ALTO = 645;
    private Panel3D panelVistaUno, panelVistaDos;
    
    /**
     * Creates a new instance of PanelDeFichas
     */
    public PanelDeFichas() {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        this.setBounds(POSICION_X, POSICION_Y, ANCHO, ALTO);
        
        panelVistaUno = new Panel3D();
        panelVistaDos = new Panel3D();
        this.addTab("Bienvenida",new ImageIcon("lib//imagenes//Pestañas.itr"),new PanelImagen("lib//imagenes//Bienvenida.itr",0,0,1010,675),"Pantalla de bienvenida" );
        this.addTab("Vista Uno",new ImageIcon("lib//imagenes//Pestañas.itr"),panelVistaUno, "Vista uno de la escena 3D" );
        this.addTab("Vista Dos",new ImageIcon("lib//imagenes//Pestañas.itr"),panelVistaDos, "Vista dos de la escena 3D" );
        
        this.setSelectedIndex(0);
    }

    public JPanel[] getPaneles() {
        return paneles;
    }

    public Panel3D getPanelVistaUno() {
        return panelVistaUno;
    }

    public void setPanelVistaUno(Panel3D panelVistaUno) {
        this.panelVistaUno = panelVistaUno;
    }

    public Panel3D getPanelVistaDos() {
        return panelVistaDos;
    }

    public void setPanelVistaDos(Panel3D panelVistaDos) {
        this.panelVistaDos = panelVistaDos;
    }
    
}
