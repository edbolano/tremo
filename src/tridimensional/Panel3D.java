/*
 * Panel3D.java
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

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.LineArray;
import javax.media.j3d.Locale;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class Panel3D extends JPanel{
    private Canvas3D canvas3d;
    //private BranchGroup ramaDeContenido;
    private BranchGroup ejes;
    Transform3D t3d;
    
    /** Creates a new instance of Panel3D */
    public Panel3D() {
        super();
        canvas3d = new Canvas3D(SimpleUniverse.getPreferredConfiguration() );
        this.setLayout(new BorderLayout());
        this.add(canvas3d);
        
        VirtualUniverse univ = new VirtualUniverse();
        Locale locale = new Locale(univ);
        BranchGroup ramaDeRepresentacion = crearRamaDeRepresentacion();
       
        BranchGroup ramaDeContenido = crearRamaDeContenido();
        BranchGroup raizDeContenido = new BranchGroup();
        raizDeContenido.addChild(ramaDeContenido);
        
        locale.addBranchGraph(ramaDeRepresentacion);
        locale.addBranchGraph(raizDeContenido);
        //this.setVisible(true);
    }
    
    public BranchGroup crearRamaDeRepresentacion(){
        BranchGroup retorno = new BranchGroup();
        
        View vista = new View();
        vista.addCanvas3D(canvas3d);
        vista.setPhysicalBody(new PhysicalBody() );
        vista.setPhysicalEnvironment(new PhysicalEnvironment() );
        
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            t3d = new Transform3D();
//            t3d.setTranslation( new Vector3f(0f,0f,8f) );
            t3d.setTranslation( new Vector3f(1f,1f,8f) );
            tg.setTransform(t3d);
            
        ViewPlatform plataforma = new ViewPlatform();
        vista.attachViewPlatform(plataforma);
        tg.addChild(plataforma);
        retorno.addChild(tg);
        retorno.compile();
        
        return retorno;
    }
//    public void regenerarVista(){
//        t3d.setTranslation( new Vector3f(0f,0f,4f) );
//    }
    
    public BranchGroup crearRamaDeContenido(){
        BranchGroup retornoCont = new BranchGroup();
        retornoCont.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        retornoCont.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        //**********************************************************
        TransformGroup tgCont = new TransformGroup();
        tgCont.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        BoundingSphere limite = new BoundingSphere();
        limite.setRadius(1000);
        MouseZoom zoom = new MouseZoom(tgCont);
        zoom.setSchedulingBounds(limite);
        tgCont.addChild(zoom);
        MouseRotate orbit = new MouseRotate(tgCont);
        orbit.setSchedulingBounds(limite);
        tgCont.addChild(orbit);
        MouseTranslate move = new MouseTranslate(tgCont);
        move.setSchedulingBounds(limite);
        tgCont.addChild(move);
        //************************************************************
        
        ejes = new BranchGroup();
        ejes.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        ejes.setCapability(BranchGroup.ALLOW_DETACH);
        ejes.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        AmbientLight lightA = new AmbientLight();
        BoundingSphere limit = new BoundingSphere();
        limit.setRadius(1000);
        lightA.setInfluencingBounds(limit);
        ejes.addChild(lightA);
        
        LineArray ejeX = new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
        ejeX.setCoordinate(0,new Point3f(0,0,0) );
        ejeX.setCoordinate(1,new Point3f(.2f,0,0) );
        ejeX.setColor(0,new Color3f(1,1,1));
        ejeX.setColor(1,new Color3f(1,1,1));        
        Shape3D shapeX = new Shape3D(ejeX);  
        ejes.addChild(shapeX);
        LineArray ejeY = new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
        ejeY.setCoordinate(0,new Point3f(0,0,0) );
        ejeY.setCoordinate(1,new Point3f(0,.2f,0) );
        ejeY.setColor(0,new Color3f(1,1,1));
        ejeY.setColor(1,new Color3f(1,1,1));        
        Shape3D shapeY = new Shape3D(ejeY);  
        ejes.addChild(shapeY);
        LineArray ejeZ = new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
        ejeZ.setCoordinate(0,new Point3f(0,0,0) );
        ejeZ.setCoordinate(1,new Point3f(0,0,.2f) );
        ejeZ.setColor(0,new Color3f(1,1,1));
        ejeZ.setColor(1,new Color3f(1,1,1));        
        Shape3D shapeZ = new Shape3D(ejeZ);  
        ejes.addChild(shapeZ);
        
        tgCont.addChild(ejes);
        retornoCont.addChild(tgCont);        
        retornoCont.compile();
        
        return retornoCont;
    }   
    
    public BranchGroup getEjes(){
        return this.ejes;
    }
    
}
