/*
 * Material.java
 *
 * Created on 5 de diciembre de 2006, 05:53 PM
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

package estructural;

import java.io.Serializable;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class Material implements Serializable{
    
    private String identificador;
    private float moduloDeElasticidad;
    private float pesoVolumetrico;
    /** Creates a new instance of Material */
    public Material() {
        this.identificador = "";
        this.moduloDeElasticidad = 0f;
        this.pesoVolumetrico = 0f;
    }
    
    public Material(String identif,float moduloElast,float pesoVolum){
        this.identificador = identif;
        this.moduloDeElasticidad = moduloElast;
        this.pesoVolumetrico = pesoVolum;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public float getModuloDeElasticidad() {
        return moduloDeElasticidad;
    }

    public void setModuloDeElasticidad(float moduloDeElasticidad) {
        this.moduloDeElasticidad = moduloDeElasticidad;
    }

    public float getPesoVolumetrico() {
        return pesoVolumetrico;
    }

    public void setPesoVolumetrico(float pesoVolumetrico) {
        this.pesoVolumetrico = pesoVolumetrico;
    }
    
}
