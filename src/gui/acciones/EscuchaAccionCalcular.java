/*
 * EscuchaAccionCalcular.java
 *
 * Created on 4 de marzo de 2007, 04:35 AM
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
 *
 */

package gui.acciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.UndeclaredThrowableException;
import javax.swing.JOptionPane;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionCalcular implements ActionListener{
    
    /** Creates a new instance of EscuchaAccionCalcular */
    public EscuchaAccionCalcular() {
    }

    public void actionPerformed(ActionEvent e) {
        calcularCortantesX();
        calcularCortantesZ();
        
        calcularCentroTorsionX();
        calcularCentroTorsionZ();
        
        calcularMomentosTorsionantesX();
        calcularMomentosTorsionantesZ();
        
        distribuirFuerzasMarcosX();
        distribuirFuerzasMarcosZ();
        JOptionPane.showMessageDialog(null,"Calculo completado!!!","INFORMACION",JOptionPane.INFORMATION_MESSAGE);
        
    }

    private void calcularCortantesX() {
        
        float periodoT = Main.getArchivoDeTrabajo().getCriterioOpcional_TX();
        float periodoTa = Main.getArchivoDeTrabajo().getCriterio_Ta();
        float periodoTb = Main.getArchivoDeTrabajo().getCriterio_Tb();
        float Qx = Main.getArchivoDeTrabajo().getCriterio_Qx();
        float criterio_a = 0f;
        float Qprima = 0f;
        float criterio_c = Main.getArchivoDeTrabajo().getCriterio_c();
        float criterio_aCero = Main.getArchivoDeTrabajo().getCriterio_aCero();
        float criterio_r = Main.getArchivoDeTrabajo().getCriterio_r();
        
        float sumaWi = 0f;
        float sumaWihi = 0f;
        float sumaWihihi = 0f;
        
        //float[] fuerzas = new float[20];
        
        if( (periodoT==0f)||(periodoT>=periodoTa) ){
            Qprima = Qx;
        }else{
            Qprima = ( (periodoT/periodoTa)*(Qx-1) )+1;
        }
        
        //calcular columna alturas absolutas de cada entrepiso
        float estaAltura = 0f;
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                estaAltura = estaAltura + Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[0].calcularAlturaN();
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setAlturaAbsoluta(estaAltura);
                
                //Se aprovecha el ciclo para calcular sumatorias de peso  y de producto peso x altura absoluta
                sumaWi = sumaWi + Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                sumaWihi = sumaWihi + (
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0])*
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta())  );
                sumaWihihi = sumaWihihi + (
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0])*
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta())*
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta())  );
            }
        }
        //calcular la columna de fuerzas Fi
        int indiceADescender = 0;
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                float estePeso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                float alt = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta();
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setFuerzaSismicaX(
                        (criterio_c/Qprima)*
                        (sumaWi)*
                        ( (estePeso*alt)/sumaWihi)  );
//                System.out.println("c"+criterio_c);
//                System.out.println("Q'"+Qprima);
//                System.out.println("sumaWi"+sumaWi);
//                System.out.println("este peso"+estePeso);
//                System.out.println("alt"+alt);
//                System.out.println("sumaWihi"+sumaWihi);
                indiceADescender = entrepiso ;
            }
        }
        //calcular la columna de cortantes
        float cortante_i = 0f;
        for(int entrepiso = indiceADescender;entrepiso>=0;entrepiso--){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                cortante_i = cortante_i + 
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaX();
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCortanteX(cortante_i);
            }
        }
        
        //calcular la columna vi/ki (cortante/rigidez) y el desplazamiento absoluto
        float desplazamientoAbsoluto = 0f;
        for(int entrepiso = 0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                
                float rigidezTotal_i = 0f;
                int numMarcos = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX().length;
                for(int marco=0;marco<numMarcos;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        rigidezTotal_i = rigidezTotal_i +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].calcularRigidez();
                        //se aprovecha el ciclo para asignarle la nueva rigidez a los marcos (esta rigidez es nueva porque ya se pudo
                        // haber replanteado la informacion de las alturas superior e inferior de cada marco)
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setRigidezR(
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].calcularRigidez() );
                    }
                }
//                System.out.println("\nrigidez total: "+rigidezTotal_i);
//                //desplazamientoRelativo = cortante/rigidez
//                System.out.println("cortante x: "+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX() );
                float desplazamientoDeEntrepisoRelativo_i = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX()
                        /(rigidezTotal_i);
                desplazamientoAbsoluto = desplazamientoAbsoluto + desplazamientoDeEntrepisoRelativo_i;
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setDesplazamientoAbsolutoX(desplazamientoAbsoluto);
//                System.out.println("desplazamiento relativo: "+desplazamientoDeEntrepisoRelativo_i);
//                System.out.println("desplazamiento absoluto: "+desplazamientoAbsoluto);
                
                
            }
        }
        
        //calcular Sumatoria(Wixi2)  y Sumatoria(Fixi) para estimar el periodo T
        float sumaWiXiCuad = 0f;
        float sumaFiXi = 0f;
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                sumaWiXiCuad = sumaWiXiCuad +
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0]
                        *Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoX()
                        *Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoX();
                
                sumaFiXi = sumaFiXi +
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaX()
                        *Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoX();
            }
        }
        double perDoubleTemp = (2*Math.PI)*(Math.sqrt(sumaWiXiCuad/(sumaFiXi*981) ) );
        periodoT = (float)perDoubleTemp;
        Main.getArchivoDeTrabajo().setCriterioOpcional_TX(periodoT);
        
        ///////CALCULAR   " a "   /////////////////////////////////////////////////////
        if(periodoT < periodoTa){
            criterio_a = criterio_aCero + ( (criterio_c - criterio_aCero)*(periodoT/periodoTa) );
        } else if(periodoT > periodoTb){
            double aTemporal = (criterio_c)*Math.pow( (periodoTb/periodoT),criterio_r );
            criterio_a = (float)aTemporal;
        } else { //cualquier otro caso implica que Ta <= T <= Tb
            criterio_a = criterio_c;
        }
        ///////CALCULAR   " a "   /////////////////////////////////////////////////////
        
        ///////CALCULAR   " Q' "   /////////////////////////////////////////////////////
        if( (periodoT==0f)||(periodoT>=periodoTa) ){
            Qprima = Qx;
        }else{
            Qprima = ( (periodoT/periodoTa)*(Qx-1) )+1;
        }
        ///////CALCULAR   " Q' "   /////////////////////////////////////////////////////
        
        //se evalua la posible reduccion de las fuerzas cortantes
        if(periodoT <= periodoTb){
            //Fi = a/Q' x SumaWi x wihi/SumaWihi
            for(int entrepiso=0;entrepiso<20;entrepiso++){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    float estePeso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                    float alt = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta();
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setFuerzaSismicaX(
                            (criterio_a/Qprima)*
                            (sumaWi)*
                            ( (estePeso*alt)/sumaWihi)  );
    //                System.out.println("c"+criterio_a);
    //                System.out.println("Q'"+Qprima);
    //                System.out.println("sumaWi"+sumaWi);
    //                System.out.println("este peso"+estePeso);
    //                System.out.println("alt"+alt);
    //                System.out.println("sumaWihi"+sumaWihi);
                    indiceADescender = entrepiso ;
                }
            }
            //calcular la columna de cortantes
            float cortante_i_segundoCiclo = 0f;
            for(int entrepiso = indiceADescender;entrepiso>=0;entrepiso--){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    cortante_i_segundoCiclo = cortante_i_segundoCiclo + 
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaX();
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCortanteX(cortante_i_segundoCiclo);
                }
            }
            
        }else if(periodoT > periodoTb){
            //Fi = a/Q' x Wi( k1hi + k2*hi*hi )
            for(int entrepiso=0;entrepiso<20;entrepiso++){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    float estePeso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                    float alt = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta();
                    double qTemporal = (criterio_c)*Math.pow( (periodoTb/periodoT),criterio_r );
                    float factor_q = (float)qTemporal;
                    double kUnoTemporal = (sumaWi/sumaWihi)*( 1 - (0.5*criterio_r)*(1-factor_q) );
                    float kUno = (float)kUnoTemporal;
                    double kDosTemporal = (sumaWi/sumaWihihi)*(0.75)*(criterio_r)*(1 - factor_q) ;
                    float kDos = (float)kDosTemporal;
                    
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setFuerzaSismicaX(
                            (criterio_a/Qprima)*
                            (estePeso)*
                            ( (kUno*alt) + (kDos*alt*alt) )  );
    //                System.out.println("c"+criterio_a);
    //                System.out.println("Q'"+Qprima);
    //                System.out.println("sumaWi"+sumaWi);
    //                System.out.println("este peso"+estePeso);
    //                System.out.println("alt"+alt);
    //                System.out.println("sumaWihi"+sumaWihi);
                    indiceADescender = entrepiso ;
                }
            }
            //calcular la columna de cortantes
            float cortante_i_segundoCiclo = 0f;
            for(int entrepiso = indiceADescender;entrepiso>=0;entrepiso--){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    cortante_i_segundoCiclo = cortante_i_segundoCiclo + 
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaX();
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCortanteX(cortante_i_segundoCiclo);
                }
            }
            
        } //fin de else if(periodoT > periodoTb)
        
        //AL FINALIZAR ESTE METODO LOS ENTREPISOS DE ARCHIVO TENDRAN ASIGNADOS SUS CORRESPONDIENTES
        //FUERZAS Y CORTANTES DE ENTREPISO EN EL SENTIDO X
        
    }

    private void calcularCortantesZ() {
        float periodoT = Main.getArchivoDeTrabajo().getCriterioOpcional_TZ();
        float periodoTa = Main.getArchivoDeTrabajo().getCriterio_Ta();
        float periodoTb = Main.getArchivoDeTrabajo().getCriterio_Tb();
        float Qz = Main.getArchivoDeTrabajo().getCriterio_Qz();
        float criterio_a = 0f;
        float Qprima = 0f;
        float criterio_c = Main.getArchivoDeTrabajo().getCriterio_c();
        float criterio_aCero = Main.getArchivoDeTrabajo().getCriterio_aCero();
        float criterio_r = Main.getArchivoDeTrabajo().getCriterio_r();
        
        float sumaWi = 0f;
        float sumaWihi = 0f;
        float sumaWihihi = 0f;
        
        //float[] fuerzas = new float[20];
        
        if( (periodoT==0f)||(periodoT>=periodoTa) ){
            Qprima = Qz;
        }else{
            Qprima = ( (periodoT/periodoTa)*(Qz-1) )+1;
        }
        
        //calcular columna alturas absolutas de cada entrepiso
        float estaAltura = 0f;
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                estaAltura = estaAltura + Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[0].calcularAlturaN();
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setAlturaAbsoluta(estaAltura);
                
                //Se aprovecha el ciclo para calcular sumatorias de peso  y de producto peso x altura absoluta
                sumaWi = sumaWi + Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                sumaWihi = sumaWihi + (
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0])*
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta())  );
                sumaWihihi = sumaWihihi + (
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0])*
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta())*
                        (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta())  );
            }
        }
        //calcular la columna de fuerzas Fi
        int indiceADescender = 0;
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                float estePeso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                float alt = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta();
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setFuerzaSismicaZ(
                        (criterio_c/Qprima)*
                        (sumaWi)*
                        ( (estePeso*alt)/sumaWihi)  );
//                System.out.println("c"+criterio_c);
//                System.out.println("Q'"+Qprima);
//                System.out.println("sumaWi"+sumaWi);
//                System.out.println("este peso"+estePeso);
//                System.out.println("alt"+alt);
//                System.out.println("sumaWihi"+sumaWihi);
                indiceADescender = entrepiso ;
            }
        }
        //calcular la columna de cortantes
        float cortante_i = 0f;
        for(int entrepiso = indiceADescender;entrepiso>=0;entrepiso--){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                cortante_i = cortante_i + 
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaZ();
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCortanteZ(cortante_i);
            }
        }
        
        //calcular la columna vi/ki (cortante/rigidez) y el desplazamiento absoluto
        float desplazamientoAbsoluto = 0f;
        for(int entrepiso = 0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                
                float rigidezTotal_i = 0f;
                int numMarcos = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ().length;
                for(int marco=0;marco<numMarcos;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        rigidezTotal_i = rigidezTotal_i +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].calcularRigidez();
                        //se aprovecha el ciclo para asignarle la nueva rigidez a los marcos (esta rigidez es nueva porque ya se pudo
                        // haber replanteado la informacion de las alturas superior e inferior de cada marco)
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setRigidezR(
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].calcularRigidez() );
                    }
                }
//                System.out.println("\nrigidez total: "+rigidezTotal_i);
//                //desplazamientoRelativo = cortante/rigidez
//                System.out.println("cortante x: "+Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX() );
                float desplazamientoDeEntrepisoRelativo_i = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteZ()
                        /(rigidezTotal_i);
                desplazamientoAbsoluto = desplazamientoAbsoluto + desplazamientoDeEntrepisoRelativo_i;
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setDesplazamientoAbsolutoZ(desplazamientoAbsoluto);
//                System.out.println("desplazamiento relativo: "+desplazamientoDeEntrepisoRelativo_i);
//                System.out.println("desplazamiento absoluto: "+desplazamientoAbsoluto);
                
                
            }
        }
        
        //calcular Sumatoria(Wixi2)  y Sumatoria(Fixi) para estimar el periodo T
        float sumaWiXiCuad = 0f;
        float sumaFiXi = 0f;
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                sumaWiXiCuad = sumaWiXiCuad +
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0]
                        *Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoZ()
                        *Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoZ();
                
                sumaFiXi = sumaFiXi +
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaZ()
                        *Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getDesplazamientoAbsolutoZ();
            }
        }
        double perDoubleTemp = (2*Math.PI)*(Math.sqrt(sumaWiXiCuad/(sumaFiXi*981) ) );
        periodoT = (float)perDoubleTemp;
        Main.getArchivoDeTrabajo().setCriterioOpcional_TZ(periodoT);
        
        
        ///////CALCULAR   " a "   /////////////////////////////////////////////////////
        if(periodoT < periodoTa){
            criterio_a = criterio_aCero + ( (criterio_c - criterio_aCero)*(periodoT/periodoTa) );
        } else if(periodoT > periodoTb){
            double aTemporal = (criterio_c)*Math.pow( (periodoTb/periodoT),criterio_r );
            criterio_a = (float)aTemporal;
        } else { //cualquier otro caso implica que Ta <= T <= Tb
            criterio_a = criterio_c;
        }
        ///////CALCULAR   " a "   /////////////////////////////////////////////////////
        
        ///////CALCULAR   " Q' "   /////////////////////////////////////////////////////
        if( (periodoT==0f)||(periodoT>=periodoTa) ){
            Qprima = Qz;
        }else if(periodoT < periodoTa){
            Qprima = ( (periodoT/periodoTa)*(Qz-1) )+1;
        }
        ///////CALCULAR   " Q' "   /////////////////////////////////////////////////////
        
        //se evalua la posible reduccion de las fuerzas cortantes
        if(periodoT <= periodoTb){
            //Fi = a/Q' x SumaWi x wihi/SumaWihi
            for(int entrepiso=0;entrepiso<20;entrepiso++){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    float estePeso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                    float alt = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta();
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setFuerzaSismicaZ(
                            (criterio_a/Qprima)*
                            (sumaWi)*
                            ( (estePeso*alt)/sumaWihi)  );
    //                System.out.println("c"+criterio_a);
    //                System.out.println("Q'"+Qprima);
    //                System.out.println("sumaWi"+sumaWi);
    //                System.out.println("este peso"+estePeso);
    //                System.out.println("alt"+alt);
    //                System.out.println("sumaWihi"+sumaWihi);
                    indiceADescender = entrepiso ;
                }
            }
            //calcular la columna de cortantes
            float cortante_i_segundoCiclo = 0f;
            for(int entrepiso = indiceADescender;entrepiso>=0;entrepiso--){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    cortante_i_segundoCiclo = cortante_i_segundoCiclo + 
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaZ();
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCortanteZ(cortante_i_segundoCiclo);
                }
            }
            
        }else if(periodoT > periodoTb){
            //Fi = a/Q' x Wi( k1hi + k2*hi*hi )
            for(int entrepiso=0;entrepiso<20;entrepiso++){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    float estePeso = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[0];
                    float alt = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getAlturaAbsoluta();
                    double qTemporal = (criterio_c)*Math.pow( (periodoTb/periodoT),criterio_r );
                    float factor_q = (float)qTemporal;
                    double kUnoTemporal = (sumaWi/sumaWihi)*( 1 - (0.5*criterio_r)*(1-factor_q) );
                    float kUno = (float)kUnoTemporal;
                    double kDosTemporal = (sumaWi/sumaWihihi)*(0.75)*(criterio_r)*(1 - factor_q) ;
                    float kDos = (float)kDosTemporal;
                    
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setFuerzaSismicaZ(
                            (criterio_a/Qprima)*
                            (estePeso)*
                            ( (kUno*alt) + (kDos*alt*alt) )  );
    //                System.out.println("c"+criterio_a);
    //                System.out.println("Q'"+Qprima);
    //                System.out.println("sumaWi"+sumaWi);
    //                System.out.println("este peso"+estePeso);
    //                System.out.println("alt"+alt);
    //                System.out.println("sumaWihi"+sumaWihi);
                    indiceADescender = entrepiso ;
                }
            }
            //calcular la columna de cortantes
            float cortante_i_segundoCiclo = 0f;
            for(int entrepiso = indiceADescender;entrepiso>=0;entrepiso--){
                if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
                &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                    cortante_i_segundoCiclo = cortante_i_segundoCiclo + 
                            Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getFuerzaSismicaZ();
                    Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCortanteZ(cortante_i_segundoCiclo);
                }
            }
            
        } //fin de else if(periodoT > periodoTb)
        
        //AL FINALIZAR ESTE METODO LOS ENTREPISOS DE ARCHIVO TENDRAN ASIGNADOS SUS CORRESPONDIENTES
        //FUERZAS Y CORTANTES DE ENTREPISO EN EL SENTIDO Z
        
    }
    
    private void calcularCentroTorsionX(){
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                float sumaRizXi = 0f;
                float sumaRiz = 0f;
                for(int marcoZ=0;marcoZ<50;marcoZ++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].getIdentificador().compareTo("Marco no definido")!=0) ){
                        //No es necesario volver a calcular la rigidez porque en el metodo calcular cortantes ya se 
                        //actualizo la rigidez...
                        sumaRizXi = sumaRizXi +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].getRigidezR()
                                *(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].getColumnasN()[0].getNodoA().getCordX());
                        sumaRiz = sumaRiz +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marcoZ].getRigidezR();
                    }
                }
                float torsionX = sumaRizXi/sumaRiz;
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCentroTorsionX(torsionX);
                
            }
        }
    }
    
    private void calcularCentroTorsionZ(){
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                float sumaRixZi = 0f;
                float sumaRix = 0f;
                for(int marcoX=0;marcoX<50;marcoX++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].getIdentificador().compareTo("Marco no definido")!=0) ){
                        //No es necesario volver a calcular la rigidez porque en el metodo calcular cortantes ya se 
                        //actualizo la rigidez...
                        sumaRixZi = sumaRixZi +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].getRigidezR()
                                *(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].getColumnasN()[0].getNodoA().getCordZ());
                        sumaRix = sumaRix +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marcoX].getRigidezR();
                    }
                }
                float torsionZ = sumaRixZi/sumaRix;
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setCentroTorsionZ(torsionZ);
                
            }
        }
    }
    
    private void calcularMomentosTorsionantesX(){
        
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                float excentricidadX = 0f;
                float excentricidadXUno = 0f;
                float excentricidadXDos = 0f;
                float momentoTorsionanteUno = 0f;
                float momentoTorsionanteDos = 0f;
                float longitudMayorZ = 0f;
                excentricidadX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionZ()
                        -Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[2];
                longitudMayorZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularMayorLongitudZ();
                double excentricidadXUnoTemporal = (1.5*excentricidadX) + (0.1*longitudMayorZ);
                excentricidadXUno = (float)excentricidadXUnoTemporal;
                double excentricidadXDosTemporal = excentricidadX - (0.1*longitudMayorZ);
                excentricidadXDos = (float)excentricidadXDosTemporal;
                momentoTorsionanteUno = excentricidadXUno *
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX();
                momentoTorsionanteDos = excentricidadXDos *
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX();
                
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setMomentoUnoVX(
                        momentoTorsionanteUno);
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setMomentoDosVX(
                        momentoTorsionanteDos);
                
            }
        }
        
        
    }
    
    private void calcularMomentosTorsionantesZ(){
        
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                float excentricidadZ = 0f;
                float excentricidadZUno = 0f;
                float excentricidadZDos = 0f;
                float momentoTorsionanteUno = 0f;
                float momentoTorsionanteDos = 0f;
                float longitudMayorX = 0f;
                excentricidadZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionX()
                        -Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularPesoTotal()[1];
                longitudMayorX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].calcularMayorLongitudX();
                double excentricidadZUnoTemporal = (1.5*excentricidadZ) + (0.1*longitudMayorX);
                excentricidadZUno = (float)excentricidadZUnoTemporal;
                double excentricidadZDosTemporal = excentricidadZ - (0.1*longitudMayorX);
                excentricidadZDos = (float)excentricidadZDosTemporal;
                momentoTorsionanteUno = excentricidadZUno *
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteZ();
                momentoTorsionanteDos = excentricidadZDos *
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteZ();
                
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setMomentoUnoVZ(
                        momentoTorsionanteUno);
                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].setMomentoDosVZ(
                        momentoTorsionanteDos);
                
            }
        }
        
    }

    private void distribuirFuerzasMarcosX() {
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                
                float suma_Rix = 0f;
                float suma_Rix_ZiT_ZiT = 0f;
                float suma_Riz_XiT_XiT = 0f;
                for(int marco=0;marco<50;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        suma_Rix = suma_Rix +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getRigidezR();
                        
                        float ZiT = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionZ()
                        - Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getColumnasN()[0].getNodoA().getCordZ();
                        suma_Rix_ZiT_ZiT = suma_Rix_ZiT_ZiT +
                                (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getRigidezR()
                                * ZiT * ZiT );
                    }
                }
                for(int marco=0;marco<50;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        
                        float XiT = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionX()
                        - Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getColumnasN()[0].getNodoA().getCordX();
                        suma_Riz_XiT_XiT = suma_Riz_XiT_XiT +
                                (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getRigidezR()
                                * XiT * XiT );
                    }
                }
                //En este punto ya conozco los valores de las 
                //sumatorias de rigidecesX , productos rigX*zt*zt y productos rigZ*xt*xt
                for(int marco=0;marco<50;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        float cortanteVxDirecto = 0f;
                        float cortanteVxTorsionUno = 0f;
                        float cortanteVxTorsionDos = 0f;
                        float cortanteVxTorsionAsignable = 0f;
                        float cortanteVxTotal = 0f;
                        float cortanteVzTorsionUno = 0f;
                        float cortanteVzTorsionDos = 0f;
                        float cortanteVzTorsionAsignable = 0f;
                        
                        float cortanteEntrepisoX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteX();
                        float rigidez_iX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getRigidezR();
                        float ZiT = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionZ()
                        - Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getColumnasN()[0].getNodoA().getCordZ();
                        float momentoUnoVX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoUnoVX();
                        float momentoDosVX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoDosVX();
                        float momentoUnoVZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoUnoVZ();
                        float momentoDosVZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoDosVZ();
                        
                        cortanteVxDirecto = cortanteEntrepisoX * (rigidez_iX/suma_Rix);
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setCortEfVxDirecto(
                                cortanteVxDirecto );
                        
                        cortanteVxTorsionUno = momentoUnoVX *(rigidez_iX*ZiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        cortanteVxTorsionDos = momentoDosVX *(rigidez_iX*ZiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        if(cortanteVxTorsionUno >= cortanteVxTorsionDos){
                            cortanteVxTorsionAsignable = cortanteVxTorsionUno;
                        }else if(cortanteVxTorsionUno < cortanteVxTorsionDos){
                            cortanteVxTorsionAsignable = cortanteVxTorsionDos;
                        }
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setCortEfVxTorsion(
                                cortanteVxTorsionAsignable);
                        
                        cortanteVxTotal = cortanteVxDirecto + cortanteVxTorsionAsignable;
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setCortEfVxTotal(
                                cortanteVxTotal);
                        
                        cortanteVzTorsionUno = momentoUnoVZ * (rigidez_iX*ZiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        cortanteVzTorsionDos = momentoDosVZ * (rigidez_iX*ZiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        if(cortanteVzTorsionUno >= cortanteVzTorsionDos){
                            cortanteVzTorsionAsignable = cortanteVzTorsionUno;
                        }else if(cortanteVzTorsionUno < cortanteVzTorsionDos){
                            cortanteVzTorsionAsignable = cortanteVzTorsionDos;
                        }
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setCortEfVzTorsion(
                                cortanteVzTorsionAsignable);
                        
                        double vxMasTresDecimasVz = 0f;
                        double vzMasTresDecimasVx = 0f;
                        float cortanteTotal = 0f;
                        vxMasTresDecimasVz = cortanteVxTotal + (0.3*cortanteVzTorsionAsignable);
                        vzMasTresDecimasVx = cortanteVzTorsionAsignable +(0.3*cortanteVxTotal);
                        if(vxMasTresDecimasVz >= vzMasTresDecimasVx){
                            cortanteTotal = (float)vxMasTresDecimasVz;
                        }else if(vxMasTresDecimasVz < vzMasTresDecimasVx){
                            cortanteTotal =  (float)vzMasTresDecimasVx;
                        }
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setCortanteTotal(
                                cortanteTotal);
                        
                        float desplazamientoRelativo = 0f;
                        desplazamientoRelativo = Main.getArchivoDeTrabajo().getCriterio_Qx() * 
                                (cortanteTotal/rigidez_iX);
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setDesplazRelativo(
                                desplazamientoRelativo);
                        
                        float distorsion = 0f;
                        distorsion = desplazamientoRelativo / 
                                (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getAlturaN()*100);
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].setDistorsion(
                                distorsion);
                        
                    }
                }
                
            }
        }
    }

    private void distribuirFuerzasMarcosZ() {
        for(int entrepiso=0;entrepiso<20;entrepiso++){
            if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso]!=null)
            &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getIdentificador().compareTo("Entrepiso sin definir")!=0) ){
                
                float suma_Riz = 0f;
                float suma_Rix_ZiT_ZiT = 0f;
                float suma_Riz_XiT_XiT = 0f;
                for(int marco=0;marco<50;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        
                        float ZiT = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionZ()
                        - Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getColumnasN()[0].getNodoA().getCordZ();
                        suma_Rix_ZiT_ZiT = suma_Rix_ZiT_ZiT +
                                (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosX()[marco].getRigidezR()
                                * ZiT * ZiT );
                    }
                }
                for(int marco=0;marco<50;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        suma_Riz = suma_Riz +
                                Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getRigidezR();
                        
                        float XiT = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionX()
                        - Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getColumnasN()[0].getNodoA().getCordX();
                        suma_Riz_XiT_XiT = suma_Riz_XiT_XiT +
                                (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getRigidezR()
                                * XiT * XiT );
                    }
                }
                //En este punto ya conozco los valores de las 
                //sumatorias de rigidecesZ , productos rigX*zt*zt y productos rigZ*xt*xt
                for(int marco=0;marco<50;marco++){
                    if( (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco]!=null)
                    &&(Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getIdentificador().compareTo("Marco no definido")!=0) ){
                        float cortanteVzDirecto = 0f;
                        float cortanteVzTorsionUno = 0f;
                        float cortanteVzTorsionDos = 0f;
                        float cortanteVzTorsionAsignable = 0f;
                        float cortanteVzTotal = 0f;
                        float cortanteVxTorsionUno = 0f;
                        float cortanteVxTorsionDos = 0f;
                        float cortanteVxTorsionAsignable = 0f;
                        
                        float cortanteEntrepisoZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCortanteZ();
                        float rigidez_iZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getRigidezR();
                        float XiT = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getCentroTorsionX()
                        - Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getColumnasN()[0].getNodoA().getCordX();
                        float momentoUnoVZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoUnoVZ();
                        float momentoDosVZ = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoDosVZ();
                        float momentoUnoVX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoUnoVX();
                        float momentoDosVX = Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMomentoDosVX();
                        
                        cortanteVzDirecto = cortanteEntrepisoZ * (rigidez_iZ/suma_Riz);
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setCortEfVzDirecto(
                                cortanteVzDirecto );
                        
                        cortanteVzTorsionUno = momentoUnoVZ *(rigidez_iZ*XiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        cortanteVzTorsionDos = momentoDosVZ *(rigidez_iZ*XiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        if(cortanteVzTorsionUno >= cortanteVzTorsionDos){
                            cortanteVzTorsionAsignable = cortanteVzTorsionUno;
                        }else if(cortanteVzTorsionUno < cortanteVzTorsionDos){
                            cortanteVzTorsionAsignable = cortanteVzTorsionDos;
                        }
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setCortEfVzTorsion(
                                cortanteVzTorsionAsignable);
                        
                        cortanteVzTotal = cortanteVzDirecto + cortanteVzTorsionAsignable;
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setCortEfVzTotal(
                                cortanteVzTotal);
                        
                        cortanteVxTorsionUno = momentoUnoVX * (rigidez_iZ*XiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        cortanteVxTorsionDos = momentoDosVX * (rigidez_iZ*XiT)/(suma_Rix_ZiT_ZiT + suma_Riz_XiT_XiT);
                        if(cortanteVxTorsionUno >= cortanteVxTorsionDos){
                            cortanteVxTorsionAsignable = cortanteVxTorsionUno;
                        }else if(cortanteVxTorsionUno < cortanteVxTorsionDos){
                            cortanteVxTorsionAsignable = cortanteVxTorsionDos;
                        }
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setCortEfVxTorsion(
                                cortanteVxTorsionAsignable);
                        
                        double vzMasTresDecimasVx = 0f;
                        double vxMasTresDecimasVz = 0f;
                        float cortanteTotal = 0f;
                        vzMasTresDecimasVx = cortanteVzTotal + (0.3*cortanteVxTorsionAsignable);
                        vxMasTresDecimasVz = cortanteVxTorsionAsignable +(0.3*cortanteVzTotal);
                        if(vzMasTresDecimasVx >= vxMasTresDecimasVz){
                            cortanteTotal = (float)vzMasTresDecimasVx;
                        }else if(vzMasTresDecimasVx < vxMasTresDecimasVz){
                            cortanteTotal =  (float)vxMasTresDecimasVz;
                        }
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setCortanteTotal(
                                cortanteTotal);
                        
                        float desplazamientoRelativo = 0f;
                        desplazamientoRelativo = Main.getArchivoDeTrabajo().getCriterio_Qz() * 
                                (cortanteTotal/rigidez_iZ);
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setDesplazRelativo(
                                desplazamientoRelativo);
                        
                        float distorsion = 0f;
                        distorsion = desplazamientoRelativo / 
                                (Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].getAlturaN()*100);
                        Main.getArchivoDeTrabajo().getEntrepisosDeArchivo()[entrepiso].getMarcosZ()[marco].setDistorsion(
                                distorsion);
                        
                    }
                }
                
                
            }
        }
    }
    
}
