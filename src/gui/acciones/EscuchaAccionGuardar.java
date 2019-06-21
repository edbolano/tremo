/*
 * EscuchaAccionGuardar.java
 *
 * Created on 5 de diciembre de 2006, 05:54 PM
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

import gui.MiFileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionGuardar implements ActionListener {
    private ObjectOutputStream salida;
    
    /** Creates a new instance of EscuchaAccionGuardar */
    public EscuchaAccionGuardar() {
    }

    public void actionPerformed(ActionEvent e) {
        
        if(Main.getArchivoDeTrabajo().getArchivo().getName() != "sin nombre.trm"){
            Main.getArchivoDeTrabajo().guardar();
            
        } else if(Main.getArchivoDeTrabajo().getArchivo().getName() == "sin nombre.trm"){
            Main.getArchivoDeTrabajo().guardarComo();
            
        }
            
        
    }
    
}
