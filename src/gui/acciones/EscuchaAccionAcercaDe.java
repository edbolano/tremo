/*
 * EscuchaAccionAcercaDe.java
 *
 * Created on 5 de diciembre de 2006, 06:02 PM
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionAcercaDe implements ActionListener {
    
    /** Creates a new instance of EscuchaAccionAcercaDe */
    public EscuchaAccionAcercaDe() {
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(Main.getAplicacion(),"Tremo - version 1.0 (09-Marzo-2007)" +
                    "\n\nCopyright (C) 2007 Edgar Bolaños Lujan" +
                    "\n\nEste es Software libre (free software) y por lo tanto eres libre" +
                    "\nde redistribuirlo y/o modificarlo bajo los terminos de la licencia " +
                    "\nGNU General Public License de la Free Software Foundation version 2 " +
                    "\n\nEste programa es distribuido con la esperanza de ser de utilidad, " +
                    "\nsin embargo no incluye ABSOLUTAMENTE NINGUNA GARANTIA." +
                    "\nPara conocer mas detalles, puedes ver la licencia en el menu:" +
                    "\n\t\t\t Ayuda -> Licencia","Acerca de Tremo",JOptionPane.PLAIN_MESSAGE);
    }
    
}
