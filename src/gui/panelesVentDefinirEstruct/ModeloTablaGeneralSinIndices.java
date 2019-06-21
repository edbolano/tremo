/*
 * ModeloTablaGeneralSinIndices.java
 *
 * Created on 10 de noviembre de 2006, 02:25 PM
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

package gui.panelesVentDefinirEstruct;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class ModeloTablaGeneralSinIndices extends AbstractTableModel{
    private Object[][] objetosTablaGeneral;// = new Object[512][4];
//    private class TablaNodosListener implements TableModelListener{
//        public void tableChanged(TableModelEvent e) {            
//            for(int fila = 0;fila < getRowCount();fila++){
//                for(int col = 0;col < getColumnCount();col++){
//                    System.out.print(objetosTablaGeneral[fila][col]+" ");
//                }System.out.println("");
//            }
//        }        
//    }
    /**
     * Creates a new instance of ModeloTablaGeneral
     */
    public ModeloTablaGeneralSinIndices(int numeroFilas, int numeroColumnas) {
        //crea un modelo de tabla del tamaño adhoc
        objetosTablaGeneral = new Object[numeroFilas][numeroColumnas];
        //int _ds, $k;
        //Burbuja para inicializar los valores del arreglo
        for(int filas = 0;filas<getRowCount();filas++){
            for(int cols =0;cols<getColumnCount();cols++){
//                if(cols == 0){
//                    int contador = filas + 1;
//                    objetosTablaGeneral[filas][cols] = ""+contador;
//                }else{
                    objetosTablaGeneral[filas][cols] = "";
//                }
            }
        }
        
    }

    public int getRowCount() {
        return objetosTablaGeneral.length;
    }

    public int getColumnCount() {
        return objetosTablaGeneral[0].length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return objetosTablaGeneral[rowIndex][columnIndex];
    }

    public void setValueAt(Object valor,int rowIndex, int columnIndex){
        objetosTablaGeneral[rowIndex][columnIndex] = valor;
        this.fireTableDataChanged();
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
    
}
