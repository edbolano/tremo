/*
 * Entrepiso.java
 *
 * Created on 5 de diciembre de 2006, 01:33 PM
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
public class Entrepiso implements Serializable{
    private String identificador;
    private MarcoX[] marcosX;
    private MarcoZ[] marcosZ;
    private float pesoPropio;
    private float xPesoPropio;
    private float zPesoPropio;
    private float pesoColsSuperiores;
    private float xPesoColsSuperiores;
    private float zPesoColsSuperiores;
    private float[] pesosTabla = new float[4];
    private float[] xPesosTabla = new float[4];
    private float[] zPesosTabla = new float[4];
    private float alturaAbsoluta;
    private float fuerzaSismicaX;
    private float fuerzaSismicaZ;
    private float cortanteX;
    private float cortanteZ;
    private float desplazamientoAbsolutoX;
    private float desplazamientoAbsolutoZ;
    private float centroTorsionX;
    private float centroTorsionZ;
    private float excentricidadX;
    private float excentricidadZ;
    private float momentoUnoVX;
    private float momentoDosVX;
    private float momentoUnoVZ;
    private float momentoDosVZ;
    
    /** Creates a new instance of Entrepiso */
    public Entrepiso() {
        this.identificador = "Entrepiso sin definir";
        this.marcosX = new MarcoX[50];
        this.marcosZ = new MarcoZ[50];
        this.pesoPropio = 0f;
        this.xPesoPropio = 0f;
        this.zPesoPropio = 0f;
        this.pesoColsSuperiores = 0f;
        this.xPesoColsSuperiores = 0f;
        this.zPesoColsSuperiores = 0f;
        for(int i=0;i<pesosTabla.length;i++){
            this.pesosTabla[i] = 0f;
            this.xPesosTabla[i] = 0f;
            this.zPesosTabla[i] = 0f;
        }
        this.alturaAbsoluta = 0f;
        this.fuerzaSismicaX = 0f;
        this.fuerzaSismicaZ = 0f;
        this.cortanteX = 0f;
        this.cortanteZ = 0f;
        this.desplazamientoAbsolutoX = 0f;
        this.desplazamientoAbsolutoZ = 0f;
        this.centroTorsionX = 0f;
        this.centroTorsionZ = 0f;
        this.excentricidadX = 0f;
        this.excentricidadZ = 0f;
        this.momentoUnoVX = 0f;
        this.momentoDosVX = 0f;
        this.momentoUnoVZ = 0f;
        this.momentoDosVZ = 0f;
    }
    
    //regresa un arreglo de tres float que indican peso total 
    //posicion de este peso en x y su posicion en z...
    public float[] calcularPesoTotal(){
        ///primero hay que calcular el peso propio y sus coordenadas
        ///para hacer uso de esos valores al calcular el peso total
        float pesosCols[] = asignarEstePesoPropio();
        //Este metodo ademas de asignar el peso propio devuelve un arreglo
        //que contiene el peso y posicion de centro de masas de las columnas 
        //para ocuparlas en el peso del entrepiso inferior...
        
        float[] retorno = new float[6];
        retorno[3] = pesosCols[0];
        retorno[4] = pesosCols[1];
        retorno[5] = pesosCols[2];
        ////////Peso total
        retorno[0] = this.pesoPropio+
                     this.pesosTabla[0]+
                     this.pesosTabla[1]+
                     this.pesosTabla[2]+
                     this.pesosTabla[3]+
                     this.pesoColsSuperiores;
        
        /////Coordenada x del peso total
        retorno[1] = (
                        (this.pesoPropio * this.xPesoPropio)+
                        (this.pesosTabla[0] * this.xPesosTabla[0])+
                        (this.pesosTabla[1] * this.xPesosTabla[1])+
                        (this.pesosTabla[2] * this.xPesosTabla[2])+
                        (this.pesosTabla[3] * this.xPesosTabla[3])+
                        (this.pesoColsSuperiores*this.xPesoColsSuperiores)
                     )/retorno[0];
        
        //////Coordenada z del peso total
        retorno[2] = (
                        (this.pesoPropio * this.zPesoPropio)+
                        (this.pesosTabla[0] * this.zPesosTabla[0])+
                        (this.pesosTabla[1] * this.zPesosTabla[1])+
                        (this.pesosTabla[2] * this.zPesosTabla[2])+
                        (this.pesosTabla[3] * this.zPesosTabla[3])+
                        (this.pesoColsSuperiores*this.zPesoColsSuperiores)
                     )/retorno[0];
        return retorno;
    }
    
    public float[] asignarEstePesoPropio(){
        float[] retorno = new float[3];//el metodo regresa en un arreglo el peso cord x y cord z de las cols
        float asignarPeso = 0f;
        float asignarX = 0f;
        float asignarZ = 0f;
        
        float[] pesos = new float[102400];////512vigas+512cols x 50marcos x 2sentidos(X y Y)=102,400
        float[] xPesos = new float[102400];
        float[] zPesos = new float[102400];
        float[] pesosCols = new float[51200];
        float[] xPesosCols = new float[51200];
        float[] zPesosCols = new float[51200];
        int indicePesos = 0;
        int indicePesosCols = 0;
        //for para los 50 marcos en cada sentido que contiene este entrepiso
        for(int i=0;i<50;i++){
            ///Marcos en x
            if(this.marcosX[i]!=null){
                //vigas n de marcos x
                for(int j=0;j<512;j++){
                    if(this.marcosX[i].getVigasN()[j]!=null){
                        pesos[indicePesos] = this.marcosX[i].getVigasN()[j].getPesoPropio()[0];
                        xPesos[indicePesos] = this.marcosX[i].getVigasN()[j].getPesoPropio()[1];
                        zPesos[indicePesos] = this.marcosX[i].getVigasN()[j].getPesoPropio()[2];
                        indicePesos++;
                    }
                }
                //columnas n de marcos x
                for(int j=0;j<512;j++){
                    if(this.marcosX[i].getColumnasN()[j]!=null){
                        pesosCols[indicePesosCols] = this.marcosX[i].getColumnasN()[j].getPesoPropio()[0];
                        xPesosCols[indicePesosCols] = this.marcosX[i].getColumnasN()[j].getPesoPropio()[1];
                        zPesosCols[indicePesosCols] = this.marcosX[i].getColumnasN()[j].getPesoPropio()[2];
                        indicePesosCols++;
                    }
                }
            }
            
            ///Marcos en z
            if(this.marcosZ[i]!=null){
                //vigas n de marcos z
                for(int j=0;j<512;j++){
                    if(this.marcosZ[i].getVigasN()[j]!=null){
                        pesos[indicePesos] = this.marcosZ[i].getVigasN()[j].getPesoPropio()[0];
                        xPesos[indicePesos] = this.marcosZ[i].getVigasN()[j].getPesoPropio()[1];
                        zPesos[indicePesos] = this.marcosZ[i].getVigasN()[j].getPesoPropio()[2];
                        indicePesos++;
                    }
                }
//                //columnas n de marcos z
//                for(int j=0;j<512;j++){
//                    if(this.marcosZ[i].getColumnasN()[j]!=null){
//                        pesos[indicePesos] = this.marcosZ[i].getColumnasN()[j].getPesoPropio()[0];
//                        xPesos[indicePesos] = this.marcosZ[i].getColumnasN()[j].getPesoPropio()[1];
//                        zPesos[indicePesos] = this.marcosZ[i].getColumnasN()[j].getPesoPropio()[2];
//                        indicePesos++;
//                    }
//                }
            }
        }
        
        //rutina para hacer uso de los arreglos que ya se tienen con todos los pesos y posiciones indexados
        for(int i=0;i<pesos.length;i++){ //el arreglo pesos xPesos y zPesos tienen la misma longitud
            if(pesos[i]!=0f){  //este indice de verificacion es valido para los tres arreglos
                asignarPeso = asignarPeso + pesos[i];
            }
        }
        for(int i=0;i<xPesos.length;i++){
            if(pesos[i]!=0f){
                asignarX = asignarX + ( (pesos[i]*xPesos[i])/asignarPeso );
                asignarZ = asignarZ + ( (pesos[i]*zPesos[i])/asignarPeso );
            }
        }
        
        for(int i=0;i<pesosCols.length;i++){ //el arreglo pesos xPesos y zPesos tienen la misma longitud
            if(pesosCols[i]!=0f){  //este indice de verificacion es valido para los tres arreglos
                retorno[0] = retorno[0] + pesosCols[i];
            }
        }
        for(int i=0;i<xPesosCols.length;i++){
            if(pesosCols[i]!=0f){
                retorno[1] = retorno[1] + ( (pesosCols[i]*xPesosCols[i])/retorno[0] );
                retorno[2] = retorno[2] + ( (pesosCols[i]*zPesosCols[i])/retorno[0] );
            }
        }
        
        
        this.pesoPropio = asignarPeso;
        this.xPesoPropio = asignarX;
        this.zPesoPropio = asignarZ;
        return retorno;
    }
    
    public float calcularMayorLongitudX(){
        float retorno = 0f;
        for(int marcoX=0;marcoX<50;marcoX++){
            if( (this.marcosX[marcoX]!=null) 
            &&(this.marcosX[marcoX].getIdentificador().compareTo("Marco no definido")!=0) ){
                
                float longitudEsteMarco = 0f;
                for(int viga=0;viga<512;viga++){
                    if( (this.marcosX[marcoX].getVigasN()[viga]!=null)
                    &&(this.marcosX[marcoX].getVigasN()[viga].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                        longitudEsteMarco = longitudEsteMarco + 
                                this.marcosX[marcoX].getVigasN()[viga].calcularLongitud();
                    }
                }
                if(longitudEsteMarco > retorno){
                    retorno = longitudEsteMarco;
                }
                
            }
        }
        return retorno;
    }
    
    public float calcularMayorLongitudZ(){
        float retorno = 0f;
        for(int marcoZ=0;marcoZ<50;marcoZ++){
            if( (this.marcosZ[marcoZ]!=null) 
            &&(this.marcosZ[marcoZ].getIdentificador().compareTo("Marco no definido")!=0) ){
                
                float longitudEsteMarco = 0f;
                for(int viga=0;viga<512;viga++){
                    if( (this.marcosZ[marcoZ].getVigasN()[viga]!=null)
                    &&(this.marcosZ[marcoZ].getVigasN()[viga].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                        longitudEsteMarco = longitudEsteMarco + 
                                this.marcosZ[marcoZ].getVigasN()[viga].calcularLongitud();
                    }
                }
                if(longitudEsteMarco > retorno){
                    retorno = longitudEsteMarco;
                }
                
            }
        }
        return retorno;
    }

    public MarcoX[] getMarcosX() {
        return marcosX;
    }

    public void setMarcosX(MarcoX[] marcosX) {
        this.marcosX = marcosX;
    }

    public MarcoZ[] getMarcosZ() {
        return marcosZ;
    }

    public void setMarcosZ(MarcoZ[] marcosZ) {
        this.marcosZ = marcosZ;
    }

    public float getPesoPropio() {
        return pesoPropio;
    }

    public void setPesoPropio(float pesoPropio) {
        this.pesoPropio = pesoPropio;
    }

    public float getXPesoPropio() {
        return xPesoPropio;
    }

    public void setXPesoPropio(float xPesoPropio) {
        this.xPesoPropio = xPesoPropio;
    }

    public float getZPesoPropio() {
        return zPesoPropio;
    }

    public void setZPesoPropio(float zPesoPropio) {
        this.zPesoPropio = zPesoPropio;
    }

    public float[] getPesosTabla() {
        return pesosTabla;
    }

    public void setPesosTabla(float[] pesosTabla) {
        this.pesosTabla = pesosTabla;
    }

    public float[] getXPesosTabla() {
        return xPesosTabla;
    }

    public void setXPesosTabla(float[] xPesosTabla) {
        this.xPesosTabla = xPesosTabla;
    }

    public float[] getZPesosTabla() {
        return zPesosTabla;
    }

    public void setZPesosTabla(float[] zPesosTabla) {
        this.zPesosTabla = zPesosTabla;
    }

    public float getAlturaAbsoluta() {
        return alturaAbsoluta;
    }

    public void setAlturaAbsoluta(float alturaAbsoluta) {
        this.alturaAbsoluta = alturaAbsoluta;
    }

    public float getFuerzaSismicaX() {
        return fuerzaSismicaX;
    }

    public void setFuerzaSismicaX(float fuerzaSismicaX) {
        this.fuerzaSismicaX = fuerzaSismicaX;
    }

    public float getFuerzaSismicaZ() {
        return fuerzaSismicaZ;
    }

    public void setFuerzaSismicaZ(float fuerzaSismicaZ) {
        this.fuerzaSismicaZ = fuerzaSismicaZ;
    }

    public float getCentroTorsionX() {
        return centroTorsionX;
    }

    public void setCentroTorsionX(float centroTorsionX) {
        this.centroTorsionX = centroTorsionX;
    }

    public float getCentroTorsionZ() {
        return centroTorsionZ;
    }

    public void setCentroTorsionZ(float centroTorsionZ) {
        this.centroTorsionZ = centroTorsionZ;
    }

    public float getExcentricidadX() {
        return excentricidadX;
    }

    public void setExcentricidadX(float excentricidadX) {
        this.excentricidadX = excentricidadX;
    }

    public float getExcentricidadZ() {
        return excentricidadZ;
    }

    public void setExcentricidadZ(float excentricidadZ) {
        this.excentricidadZ = excentricidadZ;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public float getCortanteX() {
        return cortanteX;
    }

    public void setCortanteX(float cortanteX) {
        this.cortanteX = cortanteX;
    }

    public float getCortanteZ() {
        return cortanteZ;
    }

    public void setCortanteZ(float cortanteZ) {
        this.cortanteZ = cortanteZ;
    }

    public float getDesplazamientoAbsolutoX() {
        return desplazamientoAbsolutoX;
    }

    public void setDesplazamientoAbsolutoX(float desplazamientoAbsolutoX) {
        this.desplazamientoAbsolutoX = desplazamientoAbsolutoX;
    }

    public float getDesplazamientoAbsolutoZ() {
        return desplazamientoAbsolutoZ;
    }

    public void setDesplazamientoAbsolutoZ(float desplazamientoAbsolutoZ) {
        this.desplazamientoAbsolutoZ = desplazamientoAbsolutoZ;
    }

    public float getMomentoUnoVX() {
        return momentoUnoVX;
    }

    public void setMomentoUnoVX(float momentoUnoVX) {
        this.momentoUnoVX = momentoUnoVX;
    }

    public float getMomentoDosVX() {
        return momentoDosVX;
    }

    public void setMomentoDosVX(float momentoDosVX) {
        this.momentoDosVX = momentoDosVX;
    }

    public float getMomentoUnoVZ() {
        return momentoUnoVZ;
    }

    public void setMomentoUnoVZ(float momentoUnoVZ) {
        this.momentoUnoVZ = momentoUnoVZ;
    }

    public float getMomentoDosVZ() {
        return momentoDosVZ;
    }

    public void setMomentoDosVZ(float momentoDosVZ) {
        this.momentoDosVZ = momentoDosVZ;
    }

    public float getPesoColsSuperiores() {
        return pesoColsSuperiores;
    }

    public void setPesoColsSuperiores(float pesoColsSuperiores) {
        this.pesoColsSuperiores = pesoColsSuperiores;
    }

    public float getXPesoColsSuperiores() {
        return xPesoColsSuperiores;
    }

    public void setXPesoColsSuperiores(float xPesoColsSuperiores) {
        this.xPesoColsSuperiores = xPesoColsSuperiores;
    }

    public float getZPesoColsSuperiores() {
        return zPesoColsSuperiores;
    }

    public void setZPesoColsSuperiores(float zPesoColsSuperiores) {
        this.zPesoColsSuperiores = zPesoColsSuperiores;
    }

    
}
