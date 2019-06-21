/*
 * Columna3D.java
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
import estructural.Columna;
import estructural.Nodo;
import java.awt.Font;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class Columna3D extends BranchGroup{
    
    /** Creates a new instance of Columna3D */
//    public Columna3D(Nodo n1, Nodo n2) {
//        super();
//        this.setCapability(BranchGroup.ALLOW_DETACH);
//        if( (n1.getCordX()==n2.getCordX()) && (n1.getCordZ()==n2.getCordZ()) && (n1.getCordY()!=n2.getCordY()) ){
//            TransformGroup tr = new TransformGroup();        
//            LineArray columna = new LineArray(2, LineArray.COORDINATES|LineArray.COLOR_3);
////            columna.setCoordinate(0,new Point3d(n1.getCordX()/10, n1.getCordY()/10, n1.getCordZ()/10 ) );
////            columna.setCoordinate(1,new Point3d(n2.getCordX()/10, n2.getCordY()/10, n2.getCordZ()/10 ) );
//            columna.setCoordinate(0,new Point3d(n1.getCordX()/5, n1.getCordY()/5, n1.getCordZ()/5 ) );
//            columna.setCoordinate(1,new Point3d(n2.getCordX()/5, n2.getCordY()/5, n2.getCordZ()/5 ) );
//            columna.setColor(0, new Color3f(1f,1f,0f) );
//            columna.setColor(1, new Color3f(0f,1f,1f) );
//            Shape3D shapeColumna = new Shape3D(columna);
//            tr.addChild(shapeColumna);
//            this.addChild(tr);
//        } else {
//            JOptionPane.showMessageDialog(null,"No se puede crear una columna que no sea perfectamente" +
//                    "\nvertical y/o de altura 0\"cero\"","Error de definicion",JOptionPane.ERROR_MESSAGE);
//        }
//        
//
//    }
    
    public Columna3D(Columna columnaArgumento) {
        super();
        Nodo n1 = columnaArgumento.getNodoA();
        Nodo n2 = columnaArgumento.getNodoB();
        
        //////RUTINA PARA CALCULAR POSICION CORRECTA DEL ROTULO//////////
        float cordMayorY;
        float cordMenorY;
        if(n1.getCordY()>n2.getCordY()){
            cordMayorY = n1.getCordY();
            cordMenorY = n2.getCordY();
        }else if(n1.getCordY()<n2.getCordY()){
            cordMayorY = n2.getCordY();
            cordMenorY = n1.getCordY();
        }else{
            cordMayorY = n1.getCordY();
            cordMenorY = n1.getCordY();
        }        
        float cordXText = n1.getCordX();
        float cordZText = n1.getCordZ();
        float cordYText = ( (cordMayorY - cordMenorY)/2)+cordMenorY;
        /////////////////////////////////////////////////////////////////    
        
        this.setCapability(BranchGroup.ALLOW_DETACH);
        if( (n1.getCordX()==n2.getCordX()) && (n1.getCordZ()==n2.getCordZ()) && (n1.getCordY()!=n2.getCordY()) ){
            TransformGroup tr = new TransformGroup();        
            LineArray columna = new LineArray(2, LineArray.COORDINATES|LineArray.COLOR_3);
//            columna.setCoordinate(0,new Point3d(n1.getCordX()/10, n1.getCordY()/10, n1.getCordZ()/10 ) );
//            columna.setCoordinate(1,new Point3d(n2.getCordX()/10, n2.getCordY()/10, n2.getCordZ()/10 ) );
            columna.setCoordinate(0,new Point3d(n1.getCordX()/5, n1.getCordY()/5, n1.getCordZ()/5 ) );
            columna.setCoordinate(1,new Point3d(n2.getCordX()/5, n2.getCordY()/5, n2.getCordZ()/5 ) );
            columna.setColor(0, new Color3f(1f,1f,0f) );
            columna.setColor(1, new Color3f(0f,1f,1f) );
            Shape3D shapeColumna = new Shape3D(columna);
            tr.addChild(shapeColumna);
            this.addChild(tr);
            
            TransformGroup transText = new TransformGroup();
            Text2D rotuloColumna = new Text2D(columnaArgumento.getIdentificador(),new Color3f(1f,1f,1f),"Arial",12,Font.BOLD);
            Transform3D t3dText = new Transform3D();
            t3dText.setTranslation(new Vector3f(cordXText/5,cordYText/5,cordZText/5 ) );
            transText.setTransform(t3dText);
            transText.addChild(rotuloColumna);
            
            this.addChild(transText);
        } else {
            JOptionPane.showMessageDialog(null,"No se puede crear una columna que no sea perfectamente" +
                    "\nvertical y/o de altura 0\"cero\"","Error de definicion",JOptionPane.ERROR_MESSAGE);
        }
        

    }
    
}
