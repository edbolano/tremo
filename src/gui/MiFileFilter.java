/*
 * MiFileFilter.java
 *
 * Created on 16 de febrero de 2007, 08:20 AM
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

package gui;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class MiFileFilter extends FileFilter{
    
    private static String TIPO_DESCONOCIDO = "Tipo desconocido";
    private static String ARCHIVO_OCULTO = "Archivo oculto";
    private Hashtable filtros = null;
    private String descripcion = null;
    private String descripcionCompleta = null;
    private boolean usarExtensionesEnDescripcion = true;
    
    /** Creates a new instance of MiFileFilter */
    public MiFileFilter() {
        this.filtros = new Hashtable();
    }
    
    public MiFileFilter(String extension){
        this(extension, null);
    }
    
    public MiFileFilter(String extension, String descripcion){
        this();
        if(extension!=null) addExtension(extension);
        if(descripcion!=null) setDescripcion(descripcion);
    }
    
    public MiFileFilter(String[] filtros){
        this(filtros, null);
    }
    
    public MiFileFilter(String[] filtros, String descripcion){
        this();
        for(int i=0;i<filtros.length;i++){
            addExtension(filtros[i]);
        }
        if(descripcion!=null) setDescripcion(descripcion);
    }

    public boolean accept(File f) {
        if(f!=null){
            if(f.isDirectory()){
                return true;
            }
            String extension = getExtension(f);
            if( (extension!=null)&&(filtros.get(getExtension(f))!=null ) ){
                return true;
            };
        }
        return false;
    }
    
    public String getExtension(File f){
        if(f!=null){
            String nombreArchivo = f.getName();
            int i = nombreArchivo.lastIndexOf('.');
            if(  (i>0)&&(i<nombreArchivo.length()-1)   ){
                return nombreArchivo.substring(i+1).toLowerCase();
            };
        }
        return null;
    }
    
    public void addExtension(String extension){
        if(filtros == null){
            filtros = new Hashtable(5);
        }
        filtros.put(extension.toLowerCase(), this);
        descripcionCompleta = null;
    }

    public String getDescription() {
        if(descripcionCompleta == null){
            if( (descripcion ==null)||(isExtensionListInDescription())   ){
                descripcionCompleta = descripcion==null?"(":descripcion+"(";
                Enumeration extensiones = filtros.keys();
                if(extensiones!=null){
                    descripcionCompleta += "." +(String) extensiones.nextElement();
                    while(extensiones.hasMoreElements()){
                        descripcionCompleta += ", ." + (String) extensiones.nextElement();
                    }
                }
                descripcionCompleta += ")";
            } else {
                descripcionCompleta = descripcion;
            }
        }
        return descripcionCompleta;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        descripcionCompleta = null;
    }
    
    public void setExtensionListInDescription(boolean b){
        usarExtensionesEnDescripcion = b;
        descripcionCompleta = null;
    }
    
    public boolean isExtensionListInDescription(){
        return usarExtensionesEnDescripcion;
    }
    
    
}

