/*
 * EscuchaAccionApariencias.java
 *
 * Created on 5 de diciembre de 2006, 05:55 PM
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

package gui.acciones;

import gui.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionApariencias implements ActionListener {
    
    /** Creates a new instance of EscuchaAccionApariencias */
    public EscuchaAccionApariencias() {
    }

    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==Main.getAplicacion().getUnaBarraMenus().getRadioBMIMetal()
        ||e.getSource()==Main.getAplicacion().getUnPanelBarrasHerramientas().getBotonJava()){
            Main.getAplicacion().cambiarApariencia(VentanaPrincipal.AP_METAL);
        }else if(e.getSource()==Main.getAplicacion().getUnaBarraMenus().getRadioBMIMotif() 
        ||e.getSource()==Main.getAplicacion().getUnPanelBarrasHerramientas().getBotonMotif() ){
            Main.getAplicacion().cambiarApariencia(VentanaPrincipal.AP_MOTIF);
        }else if(e.getSource()==Main.getAplicacion().getUnaBarraMenus().getRadioBMIPlatLocal() 
        ||e.getSource()==Main.getAplicacion().getUnPanelBarrasHerramientas().getBotonLocal() ){
            Main.getAplicacion().cambiarApariencia(VentanaPrincipal.AP_PLATAF_LOCAL);
        }
                        
    }
    
}
