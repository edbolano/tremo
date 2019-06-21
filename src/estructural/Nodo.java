/*
 * Nodo.java
 *
 * Created on 20 de diciembre de 2006, 01:32 PM
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
public class Nodo implements Serializable{
    private String identificador;
    private float cordX;
    private float cordY;
    private float cordZ;
    /** Creates a new instance of Nodo */
    public Nodo() {
        this.identificador = "Nodo sin nombre";
        this.cordX = 0.0f;
        this.cordY = 0.0f;
        this.cordZ = 0.0f;
    }
    public Nodo(String id, float x, float y, float z){
        this.identificador = id;
        this.cordX = x;
        this.cordY = y;
        this.cordZ = z;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public float getCordX() {
        return cordX;
    }

    public void setCordX(float cordX) {
        this.cordX = cordX;
    }

    public float getCordY() {
        return cordY;
    }

    public void setCordY(float cordY) {
        this.cordY = cordY;
    }

    public float getCordZ() {
        return cordZ;
    }

    public void setCordZ(float cordZ) {
        this.cordZ = cordZ;
    }
    
}
