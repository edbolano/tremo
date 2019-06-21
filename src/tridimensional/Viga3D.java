/*
 * Viga3D.java
 *
 * Created on 5 de diciembre de 2006, 01:32 PM
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

package tridimensional;

import com.sun.j3d.utils.geometry.Text2D;
import estructural.Nodo;
import estructural.Viga;
import java.awt.Font;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class Viga3D extends BranchGroup{
    
    /** Creates a new instance of Viga3D */
//    public Viga3D(Nodo n1, Nodo n2) {        
//        super();
//        this.setCapability(BranchGroup.ALLOW_DETACH);
//        if( (n1.getCordY()==n2.getCordY()) && ( (n1.getCordX()==n2.getCordX()) || (n1.getCordZ()==n2.getCordZ()) ) ) {
//            TransformGroup tr = new TransformGroup();
//            LineArray viga = new LineArray (2,LineArray.COORDINATES|LineArray.COLOR_3);
////            Point3f puntoUno = new Point3f(n1.getCordX()/10,n1.getCordY()/10,n1.getCordZ()/10 );
////            Point3f puntoDos = new Point3f(n2.getCordX()/10,n2.getCordY()/10,n2.getCordZ()/10 );
//            Point3f puntoUno = new Point3f(n1.getCordX()/5,n1.getCordY()/5,n1.getCordZ()/5 );
//            Point3f puntoDos = new Point3f(n2.getCordX()/5,n2.getCordY()/5,n2.getCordZ()/5 );
//            viga.setCoordinate(0, puntoUno );
//            viga.setCoordinate(1, puntoDos );
//            viga.setColor(0, new Color3f(1f,0f,0f) );
//            viga.setColor(1, new Color3f(0f,0f,1f) );
//            Shape3D shapeViga = new Shape3D(viga);
//            tr.addChild(shapeViga);
//            this.addChild(tr);
//            
//            TransformGroup transText = new TransformGroup();
//            Text2D rotuloViga = new Text2D(n.getIdentificador(),new Color3f(1f,1f,1f),"Arial",18,Font.BOLD);
//            Transform3D t3dText = new Transform3D();
//            t3dText.setTranslation(new Vector3f() );
//            transText.setTransform(t3dText);
//            transText.addChild(rotuloViga);
//            
//        } else {
//            JOptionPane.showMessageDialog(null,"No se puede crear una viga que no sea" +
//                    "\nperfectamente horizontal y/o paralela a la estructura","Error de definicion",JOptionPane.ERROR_MESSAGE);
//        }
//        
//
//    }
    public Viga3D(Viga vigaArgumento) {        
        super();
        Nodo n1 = vigaArgumento.getNodoA();
        Nodo n2 = vigaArgumento.getNodoB();
        
        //////RUTINA PARA CALCULAR POSICION CORRECTA DEL ROTULO//////////
        float cordMayorX;
        float cordMenorX;
        float cordMayorZ;
        float cordMenorZ;
        if(n1.getCordX()>n2.getCordX()){
            cordMayorX = n1.getCordX();
            cordMenorX = n2.getCordX();
        }else if(n1.getCordX()<n2.getCordX()){
            cordMayorX = n2.getCordX();
            cordMenorX = n1.getCordX();
        }else{
            cordMayorX = n1.getCordX();
            cordMenorX = n1.getCordX();
        }
        if(n1.getCordZ()>n2.getCordZ()){
            cordMayorZ = n1.getCordZ();
            cordMenorZ = n2.getCordZ();
        }else if(n1.getCordZ()<n2.getCordZ()){
            cordMayorZ = n2.getCordZ();
            cordMenorZ = n1.getCordZ();
        }else{
            cordMayorZ = n1.getCordZ();
            cordMenorZ = n1.getCordZ();
        }
        float cordXText = ( (cordMayorX - cordMenorX)/2)+cordMenorX;
        float cordZText = ( (cordMayorZ - cordMenorZ)/2)+cordMenorZ;
        float cordYText = n1.getCordY();
        /////////////////////////////////////////////////////////////////    
            
        this.setCapability(BranchGroup.ALLOW_DETACH);
        if( (n1.getCordY()==n2.getCordY()) && ( (n1.getCordX()==n2.getCordX()) || (n1.getCordZ()==n2.getCordZ()) ) ) {
            TransformGroup tr = new TransformGroup();
            LineArray viga = new LineArray (2,LineArray.COORDINATES|LineArray.COLOR_3);
//            Point3f puntoUno = new Point3f(n1.getCordX()/10,n1.getCordY()/10,n1.getCordZ()/10 );
//            Point3f puntoDos = new Point3f(n2.getCordX()/10,n2.getCordY()/10,n2.getCordZ()/10 );
            Point3f puntoUno = new Point3f(n1.getCordX()/5,n1.getCordY()/5,n1.getCordZ()/5 );
            Point3f puntoDos = new Point3f(n2.getCordX()/5,n2.getCordY()/5,n2.getCordZ()/5 );
            viga.setCoordinate(0, puntoUno );
            viga.setCoordinate(1, puntoDos );
            viga.setColor(0, new Color3f(1f,0f,0f) );
            viga.setColor(1, new Color3f(0f,0f,1f) );
            Shape3D shapeViga = new Shape3D(viga);
            tr.addChild(shapeViga);
            this.addChild(tr);
            
            TransformGroup transText = new TransformGroup();
            Text2D rotuloViga = new Text2D(vigaArgumento.getIdentificador(),new Color3f(1f,1f,1f),"Arial",12,Font.BOLD);
            Transform3D t3dText = new Transform3D();
            t3dText.setTranslation(new Vector3f(cordXText/5,cordYText/5,cordZText/5 ) );
            transText.setTransform(t3dText);
            transText.addChild(rotuloViga);
            
            this.addChild(transText);
            
        } else {
            JOptionPane.showMessageDialog(null,"No se puede crear una viga que no sea" +
                    "\nperfectamente horizontal y/o paralela a la estructura","Error de definicion",JOptionPane.ERROR_MESSAGE);
        }
        

    }
    
}
