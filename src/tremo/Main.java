/*
 * Main.java
 *
 * Created on 5 de diciembre de 2006, 01:22 PM
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

package tremo;

import gui.VentanaDefinirEstructura;
import gui.VentanaPrincipal;
import java.io.IOException;
import javax.swing.JOptionPane;
import tridimensional.Columna3D;
import tridimensional.Nodo3D;
import tridimensional.Viga3D;

/**
 *
 * @author Edgar Bolaños Lujan
 */
public class Main {
    private static VentanaPrincipal aplicacion;
    private static Archivo archivoDeTrabajo;
    private static VentanaDefinirEstructura ventDefEst;
    
    private static Nodo3D[] nodos3D = new Nodo3D[512];
    private static Viga3D[] vigas3D = new Viga3D[512];
    private static Columna3D[] columnas3D = new Columna3D[512];
    
    private static Nodo3D[] nodos3D2 = new Nodo3D[512];
    private static Viga3D[] vigas3D2 = new Viga3D[512];
    private static Columna3D[] columnas3D2 = new Columna3D[512];
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Al crear la instancia se despliega la ventana...
        archivoDeTrabajo = new Archivo();
        aplicacion = new VentanaPrincipal(); 
        //desactivarTodo();
    }
    
    public static void nuevaAplicacion(){
        int opcion = JOptionPane.showConfirmDialog(aplicacion,"Esta seguro de que desea cerrar este proyecto?," +
                "\nsi lo hace sin guardar su progreso perdera todos los cambios","CERRAR ?",JOptionPane.YES_NO_OPTION);
        if(opcion == JOptionPane.OK_OPTION){
            Main.getAplicacion().setVisible(false);
            
            archivoDeTrabajo = new Archivo();
            aplicacion = new VentanaPrincipal();
        }
        
    }

    public static VentanaPrincipal getAplicacion() {
        return aplicacion;
    }
    
    public static void setAplicacion(VentanaPrincipal interfazGrafica){
        aplicacion = interfazGrafica;
    }

    public static Archivo getArchivoDeTrabajo() {
        return archivoDeTrabajo;
    }
    
    public static void setArchivoDeTrabajo(Archivo archDeTrab){
        archivoDeTrabajo = archDeTrab;
    }

    public static VentanaDefinirEstructura getVentDefEst() {
        return ventDefEst;
    }

    public static void setVentDefEst(VentanaDefinirEstructura aVentDefEst) {
        ventDefEst = aVentDefEst;
    }

    public static Nodo3D[] getNodos3D() {
        return nodos3D;
    }

    public static Viga3D[] getVigas3D() {
        return vigas3D;
    }

    public static Columna3D[] getColumnas3D() {
        return columnas3D;
    }
    
//    public static void desactivarTodo(){
//        
//    }
//    
//    public static void activarTodo(){
//        
//    }

    public static Nodo3D[] getNodos3D2() {
        return nodos3D2;
    }

    public static Viga3D[] getVigas3D2() {
        return vigas3D2;
    }

    public static Columna3D[] getColumnas3D2() {
        return columnas3D2;
    }
}
