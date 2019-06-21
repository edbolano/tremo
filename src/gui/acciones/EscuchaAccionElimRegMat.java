/*
 * EscuchaAccionElimRegMat.java
 *
 * Created on 5 de diciembre de 2006, 05:57 PM
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

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionElimRegMat implements ActionListener {
    
    /** Creates a new instance of EscuchaAccionElimRegMat */
    public EscuchaAccionElimRegMat() {
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,"Para eliminar el fichero de registro de materiales:" +
                "\nmateriales.dat, cierre la aplicacion y borre manualmente el archivo que se encuentra" +
                "\nen el DIRECTORIO DE INSTALACION\\Tremo\\lib\\registros\\materiales.dat " +
                "\no ejecute el archivo \"BorrarMateriales.bat\" que se encuentra en el mismo directorio" +
                "\n\nIMPORTANTE:" +
                "\n     TENGA EN CUENTA QUE SI BORRA EL FICHERO CON LOS REGISTROS PERDERA LA INFORMACION" +
                "\n     CONTENIDA EN ESTE, ALTERNATIVAMENTE PUEDE INTERCAMBIAR DE UBICACION ESTOS ARCHIVOS" +
                "\n     Y EL PROGRAMA RECONOCERA EL QUE SE ENCUENTRE EN EL DIRECTORIO MENCIONADO AL MOMENTO" +
                "\n     DE LA EJECUCION.","AYUDA",JOptionPane.INFORMATION_MESSAGE);
    }
    
}
