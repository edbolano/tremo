/*
 * Marco.java
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
public class Marco implements Serializable{
    private String identificador;
    private Viga[] vigasN = new Viga[512];
    private Viga[] vigasM = new Viga[512];
    private Columna[] columnasN = new Columna[512];
    private float alturaM;
    private float alturaN;
    private float alturaO;  
    private float rigidezR;
    private boolean asignadoAEntrepiso;
    private boolean rigidezDesignadaManualmente;
    
    /** Creates a new instance of Marco */
    public Marco() {
        this.identificador = "Marco no definido";
        for(int i=0;i<512;i++){
            this.vigasN[i] = null;
            this.vigasM[i] = null;
            this.columnasN[i] = null;
        }
        this.alturaM = 0f;
        this.alturaN = 0f;
        this.alturaO = 0f; 
        this.rigidezR = this.calcularRigidez();
        this.asignadoAEntrepiso = false;
        this.rigidezDesignadaManualmente = false;
    }
    
    public Marco(String ident, Viga[] vigasN,Viga[] vigasM,Columna[] colsN,
            float altM,float altN,float altO){
        this.identificador = ident;
        this.vigasN = vigasN;
        this.vigasM = vigasM;
        this.columnasN = colsN;
        this.alturaM = altM;
        this.alturaN = altN;
        this.alturaO = altO;
        this.rigidezR = this.calcularRigidez();
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Viga[] getVigasN() {
        return vigasN;
    }

    public Viga[] getVigasM() {
        return vigasM;
    }

    public Columna[] getColumnasN() {
        return columnasN;
    }

    public float getAlturaM() {
        return alturaM;
    }

    public void setAlturaM(float alturaM) {
        this.alturaM = alturaM;
    }

    public float getAlturaN() {
        float retorno;
        if(alturaN==0){
            retorno = this.calcularAlturaN();
        }else{
            retorno = this.alturaN;
        }
        
        return retorno;
    }

    public void setAlturaN(float alturaN) {
        this.alturaN = alturaN;
    }

    public float getAlturaO() {
        return alturaO;
    }

    public void setAlturaO(float alturaO) {
        this.alturaO = alturaO;
    }
    
    public float calcularAlturaN(){
        float retorno = 0f;
        for(int i=0;i<this.columnasN.length;i++){
            if( (columnasN[i]!=null)&&(columnasN[i].getIdentificador().compareTo("Marco no definido")!=0) ){
                retorno = columnasN[i].calcularLongitud();
            }
        }
        return retorno;
    }
    
    public float calcularRigidez(){
        float retorno = 0f;
        if(this.isRigidezDesignadaManualmente()){
            retorno = this.rigidezR;
        }else{
            float sumaColsN = 0f;
            float sumaVigasM = 0f;
            float sumaVigasN = 0f;
            for(int i=0;i<512;i++){
                if( (this.columnasN[i]!=null) && (this.columnasN[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                    sumaColsN = sumaColsN + //sumatoria EI/L
                            ( (this.columnasN[i].getMaterial().getModuloDeElasticidad()/1000)* //Se divide entre 1000 para manejar Ton/cm2
                               this.columnasN[i].getSeccion().getMomentoDeInercia() )/ (this.columnasN[i].calcularLongitud()*100);//Longitud en cm
                }else{
                    //i=512;
                }
            }
            for(int i=0;i<512;i++){
                if( (this.vigasM[i]!=null) && (this.vigasM[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                    sumaVigasM = sumaVigasM + //sumatoria EI/L
                            ( (this.vigasM[i].getMaterial().getModuloDeElasticidad()/1000)* //Se divide entre 1000 para manejar Ton/cm2
                               this.vigasM[i].getSeccion().getMomentoDeInercia() )/ (this.vigasM[i].calcularLongitud()*100);//Longitud en cm
                }else{
                    //i=512;
                }
            }
            for(int i=0;i<512;i++){
                if( (this.vigasN[i]!=null) && (this.vigasN[i].getIdentificador().compareTo("elemento sin nombre")!=0) ){
                    sumaVigasN = sumaVigasN + //sumatoria EI/L
                            ( (this.vigasN[i].getMaterial().getModuloDeElasticidad()/1000)* //Se divide entre 1000 para manejar Ton/cm2
                               this.vigasN[i].getSeccion().getMomentoDeInercia() )/ (this.vigasN[i].calcularLongitud()*100);//Longitud en cm
                }else{
                    //i=512;
                }
            }
            float altN = this.calcularAlturaN()*100;
            if( sumaVigasM==0 ){
                retorno =  (  48/( 
                            ((4*altN*altN)/sumaColsN )+ 
                            ((altN*altN + this.alturaO*altN)/sumaVigasN ) )   );
            } else {
                retorno =  (  48/( 
                            ((4*altN*altN)/sumaColsN )+ 
                            ((altN*altN + this.alturaM*altN)/sumaVigasM )+
                            ((altN*altN + this.alturaO*altN)/sumaVigasN ) )   );
            }
        }
        
        return retorno;
    }

    public float getRigidezR() {
        float retorno; 
//        if( (this.rigidezR==0)&&(this.identificador.compareTo("Marco no definido")!=0) ){
//            retorno = this.calcularRigidez();
//        }else{
            retorno = this.rigidezR;
//        }
        return retorno;
    }

    public void setRigidezR(float rigidezR) {
        this.rigidezR = rigidezR;
    }

    public boolean isAsignadoAEntrepiso() {
        return asignadoAEntrepiso;
    }

    public void setAsignadoAEntrepiso(boolean asignadoAEntrepiso) {
        this.asignadoAEntrepiso = asignadoAEntrepiso;
    }

    public boolean isRigidezDesignadaManualmente() {
        return rigidezDesignadaManualmente;
    }

    public void setRigidezDesignadaManualmente(boolean rigidezDesignadaManualmente) {
        this.rigidezDesignadaManualmente = rigidezDesignadaManualmente;
    }
    
}
