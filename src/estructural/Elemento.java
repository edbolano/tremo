/*
 * Elemento.java
 *
 * Created on 5 de diciembre de 2006, 05:52 PM
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
public class Elemento implements Serializable{
    private String identificador;
    private Nodo nodoA;
    private Nodo nodoB;
    private Seccion seccion;
    private Material material;
    //private int marcoPadre;
    
    
    /** Creates a new instance of Elemento */
    public Elemento() {
        this.setIdentificador("elemento sin nombre");
        this.setNodoA(new Nodo());
        this.setNodoB(new Nodo());
        this.setSeccion(new Seccion());
        this.setMaterial(new Material());
        //this.setMarcoPadre(0);
    }
    
    public Elemento(String identificador, Nodo nodoA, Nodo nodoB, Seccion secc, Material mat){
        this.setIdentificador(identificador);
        this.setNodoA(nodoA);
        this.setNodoB(nodoB);
        this.setSeccion(secc);
        this.setMaterial(mat);
        //this.setMarcoPadre(0);
    }
    
    public float calcularLongitud(){
        float retorno = 0f;
        retorno = (float)Math.sqrt(                                      //Raiz cuadrada de ...
            (this.getNodoA().getCordX()-this.getNodoB().getCordX())*(this.getNodoA().getCordX()-this.getNodoB().getCordX())+//difX al cuadrado +...
            (this.getNodoA().getCordY()-this.getNodoB().getCordY())*(this.getNodoA().getCordY()-this.getNodoB().getCordY())+//difY al cuadrado +...
            (this.getNodoA().getCordZ()-this.getNodoB().getCordZ())*(this.getNodoA().getCordZ()-this.getNodoB().getCordZ()) //difZ al cuadrado 
            );
        return retorno;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Nodo getNodoA() {
        return nodoA;
    }

    public void setNodoA(Nodo nodoA) {
        this.nodoA = nodoA;
    }

    public Nodo getNodoB() {
        return nodoB;
    }

    public void setNodoB(Nodo nodoB) {
        this.nodoB = nodoB;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    
    public float[] getPesoPropio(){
        float[] retorno = new float[3];
        ///peso propio
        retorno[0] = (this.material.getPesoVolumetrico() ) * //Ton/m3
                     (this.seccion.getArea()/10000 ) *   //m2
                     (this.calcularLongitud());          //m
                
        ///coordenada en x de peso propio
        retorno[1] = (this.nodoA.getCordX()/2)+
                     (this.nodoB.getCordX()/2);
        
        ///coordenada en z de peso propio
        retorno[2] = (this.nodoA.getCordZ()/2)+
                     (this.nodoB.getCordZ()/2);
        return retorno;
    }
    

//    public int getMarcoPadre() {
//        return marcoPadre;
//    }
//
//    public void setMarcoPadre(int marcoPadre) {
//        this.marcoPadre = marcoPadre;
//    }
    
}
