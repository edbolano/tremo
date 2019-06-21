/*
 * EscuchaAccionFacultad.java
 *
 * Created on 18 de febrero de 2007, 05:22 AM
 *
 * Copyright (C) 2007  Edgar Bola�os Lujan
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
import java.io.IOException;

/**
 *
 * @author __Edgar Bola�os Lujan__
 */
public class EscuchaAccionFacultad implements ActionListener{
    
    /** Creates a new instance of EscuchaAccionFacultad */
    public EscuchaAccionFacultad() {
    }

    public void actionPerformed(ActionEvent e) {
        String url = "http://www.uv.mx/fac_civ";
        try {

            Process p = Runtime.getRuntime().exec("explorer "+ url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }   
    }
    
}
