/*
 * Columna.java
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
public class Columna extends Elemento implements Serializable{
    private int marcoPadreEnX;
    private int marcoPadreEnZ;
    
    /** Creates a new instance of Columna */
    public Columna() {
        super();
        this.setMarcoPadreEnX(0);
        this.setMarcoPadreEnZ(0);
    }
    
    public Columna(String identificador, Nodo nodoA, Nodo nodoB, Seccion secc, Material mat){
        super(identificador,nodoA,nodoB,secc,mat);
        this.setMarcoPadreEnX(0);
        this.setMarcoPadreEnZ(0);
    }

    public int getMarcoPadreEnX() {
        return marcoPadreEnX;
    }

    public void setMarcoPadreEnX(int marcoPadre) {
        this.marcoPadreEnX = marcoPadre;
    }

    public int getMarcoPadreEnZ() {
        return marcoPadreEnZ;
    }

    public void setMarcoPadreEnZ(int marcoPadreEnZ) {
        this.marcoPadreEnZ = marcoPadreEnZ;
    }
    
    public Nodo getNodoMayor(){
        Nodo retorno = super.getNodoB();
        if(super.getNodoA().getCordY()>super.getNodoB().getCordY()){
            retorno = super.getNodoA();
        }else if(super.getNodoA().getCordY()<super.getNodoB().getCordY()){
            retorno = super.getNodoB();
        }else if(super.getNodoA().getCordY()==super.getNodoB().getCordY()){
            retorno = super.getNodoB();//en este caso se puede devolver cualquiera de los dos nodos
        }
        return retorno;
    }
    
    public Nodo getNodoMenor(){
        Nodo retorno = super.getNodoA();
        if(super.getNodoA().getCordY()<super.getNodoB().getCordY()){
            retorno = super.getNodoA();
        }else if(super.getNodoA().getCordY()>super.getNodoB().getCordY()){
            retorno = super.getNodoB();
        }else if(super.getNodoA().getCordY()==super.getNodoB().getCordY()){
            retorno = super.getNodoA();//en este caso se puede devolver cualquiera de los dos nodos
        }
        return retorno;
    }
    
}
