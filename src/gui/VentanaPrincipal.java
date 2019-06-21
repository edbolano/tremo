/*
 * VentanaPrincipal.java
 *
 * Created on 5 de diciembre de 2006, 01:28 PM
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

import gui.acciones.EscuchaAccionSalir;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class VentanaPrincipal extends JFrame{
    
    private PanelDeFichas unPanelDeFichas;
    private BarraMenus unaBarraMenus;
    private PanelBarrasHerramientas unPanelBarrasHerramientas;
    private int apariencia;
    public static final int AP_METAL = 0;
    public static final int AP_MOTIF = 1;
    public static final int AP_PLATAF_LOCAL = 2;                        
    public static final int POSICION_X = 0;
    public static final int POSICION_Y = 0;
    public static final int ANCHO =  1024;
    public static final int ALTO = 768;
    /** Creates a new instance of VentanaPrincipal */
    public VentanaPrincipal() {
        super("Tremo 1.0 - Software Libre para Ingenieria Civil -"
                +Main.getArchivoDeTrabajo().getArchivo().getPath() );
        this.setBounds(POSICION_X, POSICION_Y, ANCHO, ALTO);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        
        this.apariencia = VentanaPrincipal.AP_PLATAF_LOCAL;
        this.cambiarApariencia(apariencia);
        
        unaBarraMenus = new BarraMenus();
        this.add(unaBarraMenus);
        
        unPanelBarrasHerramientas = new PanelBarrasHerramientas();
        this.add(unPanelBarrasHerramientas);
        
        unPanelDeFichas = new PanelDeFichas();
        this.add(unPanelDeFichas);
        
        //PanelImagen salir = new PanelImagen("lib//iconos//salir.gif",980,25,30,28);
        JButton botonSalir = new JButton(new ImageIcon("lib//iconos//salir.itr") );
        botonSalir.setToolTipText("Salir");
        botonSalir.setBounds(980,25,30,28);
        botonSalir.addActionListener(new EscuchaAccionSalir() );
        this.add(botonSalir);
        
        this.setVisible(true);
    }

    public PanelDeFichas getUnPanelDeFichas() {
        return unPanelDeFichas;
    }

    public void setUnPanelDeFichas(PanelDeFichas unPanelDeFichas) {
        this.unPanelDeFichas = unPanelDeFichas;
    }

    public BarraMenus getUnaBarraMenus() {
        return unaBarraMenus;
    }

    public void setUnaBarraMenus(BarraMenus unaBarraMenus) {
        this.unaBarraMenus = unaBarraMenus;
    }

    public int getApariencia() {
        return apariencia;
    }

    public void setApariencia(int apariencia) {
        this.apariencia = apariencia;
    }
    
    public void cambiarApariencia(int apar){
        //////////Instrucciones para la apariencia
        UIManager.LookAndFeelInfo[] apariencias = UIManager.getInstalledLookAndFeels();
        String metal = apariencias[0].getClassName();
        String motif = apariencias[1].getClassName();
        String windows = apariencias[2].getClassName();
        String nom4 = UIManager.getSystemLookAndFeelClassName();        
        try {
            UIManager.setLookAndFeel(apariencias[apar].getClassName() );
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Esta plataforma no ofrece soporte para la configuracion de graficos deseada","Error de soporte",JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        //////////
    }

    public PanelBarrasHerramientas getUnPanelBarrasHerramientas() {
        return unPanelBarrasHerramientas;
    }

    public void setUnPanelBarrasHerramientas(PanelBarrasHerramientas unPanelBarrasHerramientas) {
        this.unPanelBarrasHerramientas = unPanelBarrasHerramientas;
    }
    
}
