/*
 * Archivo.java
 *
 * Created on 5 de febrero de 2007, 01:17 PM
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

import estructural.Columna;
import estructural.Entrepiso;
import estructural.Estructura;
import estructural.Marco;
import estructural.MarcoX;
import estructural.MarcoZ;
import estructural.Nodo;
import estructural.Viga;
import gui.MiFileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class Archivo implements Serializable{
    private File archivo;
    private String ruta;
    private String nombre;
    private boolean estaGuardado;
    
    private Nodo[] nodosDeArchivo = new Nodo[512];
    private Viga[] vigasDeArchivo = new Viga[512];
    private Columna[] columnasDeArchivo = new Columna[512];
    private MarcoX[] marcosDeArchivoEnX = new MarcoX[512];
    private MarcoZ[] marcosDeArchivoEnZ = new MarcoZ[512];
    private Entrepiso[] entrepisosDeArchivo = new Entrepiso[20];
    private Estructura estructuraDeArchivo;
    
    private float criterio_c;
    private float criterio_aCero;
    private float criterio_Ta;
    private float criterio_Tb;
    private float criterio_r;
    private float criterio_Qx;
    private float criterio_Qz;
    private float criterioOpcional_TX;
    private float criterioOpcional_TZ;
    private float longitudX;
    private float longitudZ;
    
    
    /** Creates a new instance of Archivo */
    public Archivo() {
        this.archivo = new File("sin nombre.trm");
        
        
        this.setEstaGuardado(false);
        
        this.estructuraDeArchivo = new Estructura();
        for(int i=0;i<nodosDeArchivo.length;i++){
            nodosDeArchivo[i] = new Nodo();
        }
        for(int i=0;i<entrepisosDeArchivo.length;i++){
            entrepisosDeArchivo[i] = new Entrepiso();
        }
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstaGuardado() {
        return estaGuardado;
    }

    public void setEstaGuardado(boolean estaGuardado) {
        this.estaGuardado = estaGuardado;
    }

    public Nodo[] getNodosDeArchivo() {
        return nodosDeArchivo;
    }

    public Viga[] getVigasDeArchivo() {
        return vigasDeArchivo;
    }

    public Columna[] getColumnasDeArchivo() {
        return columnasDeArchivo;
    }

    public MarcoX[] getMarcosDeArchivoEnX() {
        return marcosDeArchivoEnX;
    }
    
    public MarcoZ[] getMarcosDeArchivoEnZ() {
        return marcosDeArchivoEnZ;
    }

    public Entrepiso[] getEntrepisosDeArchivo() {
        return entrepisosDeArchivo;
    }

    public Estructura getEstructuraDeArchivo() {
        return estructuraDeArchivo;
    }

    public void setEstructuraDeArchivo(Estructura estructuraDeArchivo) {
        this.estructuraDeArchivo = estructuraDeArchivo;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
    
    public void abrirArchivo(){
        ObjectInputStream entrada = null;
        int opcion = JOptionPane.showConfirmDialog(Main.getAplicacion(),"Esta seguro de que desea cerrar este proyecto?," +
                "\nsi lo hace sin guardar su progreso perdera todos los cambios","CERRAR ?",JOptionPane.YES_NO_OPTION);
        if(opcion == JOptionPane.OK_OPTION){
            
      
        
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setDialogTitle("Abrir proyecto Tremo");
            MiFileFilter filtro = new MiFileFilter();
            filtro.addExtension("trm");
            filtro.setDescripcion("Proyectos tremo");

            selectorArchivo.setFileFilter(filtro);
            selectorArchivo.setMultiSelectionEnabled(false);
            //selectorArchivo.setApproveButtonText("hola mundo");
            selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = selectorArchivo.showOpenDialog(Main.getAplicacion());
            if(resultado==JFileChooser.CANCEL_OPTION)
                return;

            this.archivo = selectorArchivo.getSelectedFile();
            if( (this.archivo==null)||(this.archivo.getName()=="") )
                JOptionPane.showMessageDialog(null,"Nombre de archivo incorrecto","Nombre incorrecto",JOptionPane.ERROR_MESSAGE);
            else{
                try {
                    entrada =  new ObjectInputStream(new FileInputStream(this.archivo) );
                    try {
                        Main.setArchivoDeTrabajo((Archivo)entrada.readObject() ) ;
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"ClassNotFoundException");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"IOException 1");
                    }


                } catch (FileNotFoundException ex) {
                    //ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"El sistema no puede hallar el archivo "+this.archivo.getName(),
                            "ARCHIVO INEXISTENTE",JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    //ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"No se puede abrir un archivo de este tipo o" +
                            "\nel archivo esta corrupto","ERROR I/O",
                            JOptionPane.ERROR_MESSAGE);
                }
//                Main.getArchivoDeTrabajo().setNombre(nombreArchivo.getName()+".trm");
//                Main.getArchivoDeTrabajo().setRuta( nombreArchivo.getPath() );
                Main.getAplicacion().setTitle( "Tremo 1.0 - Software Libre para Ingenieria Civil -"
                    +this.archivo.getPath() );
                try {
                    entrada.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                
                
            }
        }
    }
    
    public void guardarComo(){
        ObjectOutputStream salida = null;
        JFileChooser selectorArchivo = new JFileChooser();
        selectorArchivo.setDialogTitle("Guardar proyecto Tremo como");
        MiFileFilter filtro = new MiFileFilter();
        filtro.addExtension("trm");
        filtro.setDescripcion("Proyectos tremo");
        
        selectorArchivo.setFileFilter(filtro);
        selectorArchivo.setMultiSelectionEnabled(false);
        selectorArchivo.setApproveButtonText("Guardar");
        selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultado = selectorArchivo.showOpenDialog(Main.getAplicacion());
        if(resultado==JFileChooser.CANCEL_OPTION)
            return;
        
        this.archivo = selectorArchivo.getSelectedFile();
        //System.out.println(selectorArchivo.getSelectedFile().getPath() );
        //this.archivo = new File(selectorArchivo.getSelectedFile().getPath());
//        if( (nombreArchivo==null)||(nombreArchivo.getName()=="") )
        if( (this.archivo==null)||(this.archivo.getName()=="") )
            JOptionPane.showMessageDialog(null,"Nombre de archivo incorrecto","Nombre incorrecto",JOptionPane.ERROR_MESSAGE);
        else{
            //System.out.println(this.archivo.getName() );
            int index = this.archivo.getName().length();
            String comodin = this.archivo.getName().substring(index-4).compareTo(".trm")==0 ? "" : ".trm";
            
            try {
                salida =  new ObjectOutputStream(new FileOutputStream(this.archivo+comodin) );
                try {
                    salida.writeObject(Main.getArchivoDeTrabajo() );
                } catch (IOException ex) {
                    //ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"IOException 1");
                }
                
                //Main.activarTodo();
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null,"El sistema no puede hallar el archivo "+nombreArchivo.getName(),
//                        "ARCHIVO INEXISTENTE",JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null,"No se puede abrir un archivo de este tipo o" +
//                        "\nel archivo esta corrupto","ERROR I/O",
//                        JOptionPane.ERROR_MESSAGE);
            }
//            Main.getArchivoDeTrabajo().setArchivo(nombreArchivo);
//            Main.getArchivoDeTrabajo().setNombre(nombreArchivo.getName()+".trm");
//            Main.getArchivoDeTrabajo().setRuta( nombreArchivo.getPath()+comodin );
            Main.getAplicacion().setTitle( "Tremo 1.0 - Software Libre para Ingenieria Civil -"
                +this.archivo.getPath() );
            try {
                salida.flush();
                salida.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
        }
    }
    
    public void guardar(){
        ObjectOutputStream salida = null;
        try { 
            int index = this.archivo.getName().length();
            String comodin = this.archivo.getName().substring(index-4).compareTo(".trm")==0 ? "" : ".trm";
            salida = new ObjectOutputStream(new FileOutputStream(this.archivo+comodin ) );
            salida.writeObject(Main.getArchivoDeTrabajo() );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            salida.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            salida.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public void setMarcosDeArchivoEnX(MarcoX[] marcosDeArchivoEnX) {
        this.marcosDeArchivoEnX = marcosDeArchivoEnX;
    }

    public void setMarcosDeArchivoEnZ(MarcoZ[] marcosDeArchivoEnZ) {
        this.marcosDeArchivoEnZ = marcosDeArchivoEnZ;
    }

    public float getCriterio_c() {
        return criterio_c;
    }

    public void setCriterio_c(float criterio_c) {
        this.criterio_c = criterio_c;
    }

    public float getCriterio_aCero() {
        return criterio_aCero;
    }

    public void setCriterio_aCero(float criterio_aCero) {
        this.criterio_aCero = criterio_aCero;
    }

    public float getCriterio_Ta() {
        return criterio_Ta;
    }

    public void setCriterio_Ta(float criterio_Ta) {
        this.criterio_Ta = criterio_Ta;
    }

    public float getCriterio_Tb() {
        return criterio_Tb;
    }

    public void setCriterio_Tb(float criterio_Tb) {
        this.criterio_Tb = criterio_Tb;
    }

    public float getCriterio_r() {
        return criterio_r;
    }

    public void setCriterio_r(float criterio_r) {
        this.criterio_r = criterio_r;
    }

    public float getCriterio_Qx() {
        return criterio_Qx;
    }

    public void setCriterio_Qx(float criterio_Qx) {
        this.criterio_Qx = criterio_Qx;
    }

    public float getCriterio_Qz() {
        return criterio_Qz;
    }

    public void setCriterio_Qz(float criterio_Qz) {
        this.criterio_Qz = criterio_Qz;
    }

    public float getCriterioOpcional_TX() {
        return criterioOpcional_TX;
    }

    public void setCriterioOpcional_TX(float criterioOpcional_TX) {
        this.criterioOpcional_TX = criterioOpcional_TX;
    }

    public float getCriterioOpcional_TZ() {
        return criterioOpcional_TZ;
    }

    public void setCriterioOpcional_TZ(float criterioOpcional_TZ) {
        this.criterioOpcional_TZ = criterioOpcional_TZ;
    }

    public float getLongitudX() {
        return longitudX;
    }

    public void setLongitudX(float longitudX) {
        this.longitudX = longitudX;
    }

    public float getLongitudZ() {
        return longitudZ;
    }

    public void setLongitudZ(float longitudZ) {
        this.longitudZ = longitudZ;
    }
    
}
