/*
 * Nodo3D.java
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

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.Text2D;
import estructural.Nodo;
import java.awt.Font;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class Nodo3D extends BranchGroup{
    private TransformGroup trans;
    /** Creates a new instance of Nodo3D */
    public Nodo3D(Nodo n) {
        super();
        this.setCapability(BranchGroup.ALLOW_DETACH);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        trans = new TransformGroup();
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformGroup transText = new TransformGroup();
        
        Appearance appari = new Appearance();
        Material mat = new Material();
        

        mat.setShininess(5f);
        mat.setDiffuseColor(0f,0f,1f);
        appari.setMaterial(mat);
        
        DirectionalLight lightD1 = new DirectionalLight();
        lightD1.setInfluencingBounds(new BoundingSphere());
        Vector3f direction1 = new Vector3f(-1.0f, -1.0f, -0.5f);
        direction1.normalize();
        lightD1.setDirection(direction1);
        lightD1.setColor(new Color3f(1.0f, 0.0f, 1.0f));
        trans.addChild(lightD1);
        
        DirectionalLight lightD2 = new DirectionalLight();
        lightD2.setInfluencingBounds(new BoundingSphere());
        Vector3f direction2 = new Vector3f(1.0f, 1.0f, 0.5f);
        direction2.normalize();
        lightD2.setDirection(direction2);
        lightD2.setColor(new Color3f(1.0f, 0.0f, 1.0f));
        trans.addChild(lightD2);                

        Sphere esfera = new Sphere(.02f,Sphere.GENERATE_NORMALS, appari);
        
        Text2D rotuloNodo = new Text2D(n.getIdentificador(),new Color3f(1f,1f,1f),"Arial",12,Font.BOLD);//tamaño anterior 18
        
        Transform3D t3d = new Transform3D();
//        t3d.setTranslation(new Vector3f(n.getCordX()/10, n.getCordY()/10, n.getCordZ()/10));
//        Transform3D t3dText = new Transform3D();
//        t3dText.setTranslation(new Vector3f( (n.getCordX()/10)+.02f, (n.getCordY()/10)+.02f, (n.getCordZ()/10)+.02f));
        t3d.setTranslation(new Vector3f(n.getCordX()/5, n.getCordY()/5, n.getCordZ()/5));
        Transform3D t3dText = new Transform3D();
        t3dText.setTranslation(new Vector3f( (n.getCordX()/5)+.02f, (n.getCordY()/5)+.02f, (n.getCordZ()/5)+.02f));
        trans.setTransform(t3d);
        trans.addChild(esfera);
        transText.setTransform(t3dText);
        transText.addChild(rotuloNodo);
        this.addChild(trans);        
        this.addChild(transText);
        
        //*****************************************************             
        //*****************************************************
    }
    
    
}
