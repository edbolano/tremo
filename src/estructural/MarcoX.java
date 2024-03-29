/*
 * MarcoX.java
 *
 * Created on 10 de febrero de 2007, 02:29 PM
 *
 * Copyright (C) 2007  Edgar Bola�os Lujan
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
 * @author __Edgar Bola�os Lujan__
 */
public class MarcoX extends Marco implements Serializable{
    private float cortEfVxDirecto;
    private float cortEfVxTorsion;
    private float cortEfVxTotal;
    private float cortEfVzTorsion;
    private float cortanteTotal;
    private float desplazRelativo;
    private float distorsion;
    
    /** Creates a new instance of MarcoX */
    public MarcoX() {
        super();
        this.cortEfVxDirecto = 0f;
        this.cortEfVxTorsion = 0f;
        this.cortEfVxTotal = 0f;
        this.cortEfVzTorsion = 0f;
        this.cortanteTotal = 0f;
        this.desplazRelativo = 0f;
        this.distorsion = 0f;
    }
    
    public MarcoX(String ident, Viga[] vigasN,Viga[] vigasM,Columna[] colsN,
            float altM,float altN,float altO){
        super(ident, vigasN, vigasM, colsN,
             altM, altN, altO);
    }

    public float getCortEfVxDirecto() {
        return cortEfVxDirecto;
    }

    public void setCortEfVxDirecto(float cortEfVxDirecto) {
        this.cortEfVxDirecto = cortEfVxDirecto;
    }

    public float getCortEfVxTorsion() {
        return cortEfVxTorsion;
    }

    public void setCortEfVxTorsion(float cortEfVxTorsion) {
        this.cortEfVxTorsion = cortEfVxTorsion;
    }

    public float getCortEfVxTotal() {
        return cortEfVxTotal;
    }

    public void setCortEfVxTotal(float cortEfVxTotal) {
        this.cortEfVxTotal = cortEfVxTotal;
    }

    public float getCortEfVzTorsion() {
        return cortEfVzTorsion;
    }

    public void setCortEfVzTorsion(float cortEfVzTorsion) {
        this.cortEfVzTorsion = cortEfVzTorsion;
    }

    public float getDesplazRelativo() {
        return desplazRelativo;
    }

    public void setDesplazRelativo(float desplazRelativo) {
        this.desplazRelativo = desplazRelativo;
    }

    public float getCortanteTotal() {
        return cortanteTotal;
    }

    public void setCortanteTotal(float cortanteTotal) {
        this.cortanteTotal = cortanteTotal;
    }

    public float getDistorsion() {
        return distorsion;
    }

    public void setDistorsion(float distorsion) {
        this.distorsion = distorsion;
    }
    
}
