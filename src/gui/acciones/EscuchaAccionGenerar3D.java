/*
 * EscuchaAccionGenerar3D.java
 *
 * Created on 5 de febrero de 2007, 05:28 PM
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

import estructural.Nodo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tremo.Main;
import tridimensional.Columna3D;
import tridimensional.Nodo3D;
import tridimensional.Viga3D;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionGenerar3D implements ActionListener{
    
    /** Creates a new instance of EscuchaAccionGenerar3D */
    public EscuchaAccionGenerar3D() {
    }

    public void actionPerformed(ActionEvent e) {

        //Se limpian los objetos viejos de la escena
        borrarNodosDeEscena();
        borrarVigasDeEscena();
        borrarColumnasDeEscena();
        
        //Se cargan los objetos nuevos 
        for(int i=0;i<512;i++){
            if( (Main.getArchivoDeTrabajo().getNodosDeArchivo()[i]!=null) 
               &&(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador().compareTo("Nodo sin nombre")!=0) ){
                //Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().addChild(
                        Main.getNodos3D()[i] =new Nodo3D(
                        Main.getArchivoDeTrabajo().getNodosDeArchivo()[i] );
                        Main.getNodos3D2()[i] =new Nodo3D(
                        Main.getArchivoDeTrabajo().getNodosDeArchivo()[i] );
            }
            if(Main.getArchivoDeTrabajo().getVigasDeArchivo()[i]!=null){
                //Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().addChild(
                        Main.getVigas3D()[i] = new Viga3D(
//                            Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA(),
//                            Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB()
                                Main.getArchivoDeTrabajo().getVigasDeArchivo()[i]
                        ) ;
                        Main.getVigas3D2()[i] = new Viga3D(
//                            Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoA(),
//                            Main.getArchivoDeTrabajo().getVigasDeArchivo()[i].getNodoB()
                                Main.getArchivoDeTrabajo().getVigasDeArchivo()[i]
                        ) ;
            }
            if(Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i]!=null){
                //Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().addChild(
                        Main.getColumnas3D()[i] = new Columna3D(
//                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoA(),
//                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoB()
                                Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i]
                        ) ;
                        Main.getColumnas3D2()[i] = new Columna3D(
//                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoA(),
//                            Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i].getNodoB()
                                Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i]
                        ) ;
            }
        }
        
        //Se despliegan los objetos nuevos en la escena
        for(int i=0;i<512;i++){
            if((Main.getArchivoDeTrabajo().getNodosDeArchivo()[i]!=null) 
               &&(Main.getArchivoDeTrabajo().getNodosDeArchivo()[i].getIdentificador().compareTo("Nodo sin nombre")!=0) ){
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().addChild(
                        Main.getNodos3D()[i] );
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaDos().getEjes().addChild(
                        Main.getNodos3D2()[i] );
            }
            if(Main.getArchivoDeTrabajo().getVigasDeArchivo()[i]!=null){
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().addChild(
                        Main.getVigas3D()[i] );
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaDos().getEjes().addChild(
                        Main.getVigas3D2()[i] );
            }
            if(Main.getArchivoDeTrabajo().getColumnasDeArchivo()[i]!=null){
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().addChild(
                        Main.getColumnas3D()[i] );
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaDos().getEjes().addChild(
                        Main.getColumnas3D2()[i] );
            }
        }
        
    }
    
    private void borrarNodosDeEscena(){
        for(int i=0;i<512;i++){
            if(Main.getNodos3D()[i]!=null){
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().removeChild(
                        Main.getNodos3D()[i]);
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaDos().getEjes().removeChild(
                        Main.getNodos3D2()[i]);
            }
        }
    }
    private void borrarVigasDeEscena(){
        for(int i=0;i<512;i++){
            if(Main.getVigas3D()[i]!=null){
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().removeChild(
                        Main.getVigas3D()[i]);
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaDos().getEjes().removeChild(
                        Main.getVigas3D2()[i]);
            }
        }
    }
    private void borrarColumnasDeEscena(){
        for(int i=0;i<512;i++){
            if(Main.getColumnas3D()[i]!=null){
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaUno().getEjes().removeChild(
                        Main.getColumnas3D()[i] );
                Main.getAplicacion().getUnPanelDeFichas().getPanelVistaDos().getEjes().removeChild(
                        Main.getColumnas3D2()[i] );
            }
        }
    }
    
}
