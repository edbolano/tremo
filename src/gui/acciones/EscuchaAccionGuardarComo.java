/*
 * EscuchaAccionGuardarComo.java
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionGuardarComo implements ActionListener {
    
    private ObjectOutputStream salida;
    /** Creates a new instance of EscuchaAccionGuardarComo */
    public EscuchaAccionGuardarComo() {
    }

    public void actionPerformed(ActionEvent e) {
//        JFileChooser selectorArchivo = new JFileChooser();
//        MiFileFilter filtro = new MiFileFilter();
//        filtro.addExtension("trm");
//        filtro.setDescripcion("Proyectos tremo");
//        
//        selectorArchivo.setFileFilter(filtro);
//        selectorArchivo.setMultiSelectionEnabled(false);
//        selectorArchivo.setApproveButtonText("Guardar");
//        selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        int resultado = selectorArchivo.showOpenDialog(Main.getAplicacion());
//        if(resultado==JFileChooser.CANCEL_OPTION)
//            return;
//        
//        File nombreArchivo = selectorArchivo.getSelectedFile();
//        if( (nombreArchivo==null)||(nombreArchivo.getName()=="") )
//            JOptionPane.showMessageDialog(null,"Nombre de archivo incorrecto","Nombre incorrecto",JOptionPane.ERROR_MESSAGE);
//        else{
//            int index = nombreArchivo.getName().length();
//            String comodin = nombreArchivo.getName().substring(index-4).compareTo(".trm")==0 ? "" : ".trm";
//            
//            try {
//                salida =  new ObjectOutputStream(new FileOutputStream(nombreArchivo+comodin) );
//                try {
//                    salida.writeObject(Main.getArchivoDeTrabajo() );
//                } catch (IOException ex) {
//                    //ex.printStackTrace();
//                    JOptionPane.showMessageDialog(null,"IOException 1");
//                }
//                
//                //Main.activarTodo();
//                
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
////                JOptionPane.showMessageDialog(null,"El sistema no puede hallar el archivo "+nombreArchivo.getName(),
////                        "ARCHIVO INEXISTENTE",JOptionPane.ERROR_MESSAGE);
//            } catch (IOException ex) {
//                ex.printStackTrace();
////                JOptionPane.showMessageDialog(null,"No se puede abrir un archivo de este tipo o" +
////                        "\nel archivo esta corrupto","ERROR I/O",
////                        JOptionPane.ERROR_MESSAGE);
//            }
//            Main.getArchivoDeTrabajo().setArchivo(nombreArchivo);
//            Main.getArchivoDeTrabajo().setNombre(nombreArchivo.getName()+".trm");
//            Main.getArchivoDeTrabajo().setRuta( nombreArchivo.getPath()+comodin );
//            Main.getAplicacion().setTitle( "Tremo 1.0 - Civil Engineering Free Software -"
//                +Main.getArchivoDeTrabajo().getRuta() );
//            try {
//                salida.flush();
//                salida.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            
//            
//        }
        Main.getArchivoDeTrabajo().guardarComo();
    }
    
}
