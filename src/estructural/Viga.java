/*
 * Viga.java
 *
 * Created on 5 de diciembre de 2006, 05:52 PM
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
public class Viga extends Elemento implements Serializable{
    
    /** Creates a new instance of Viga */
    public Viga() {
        super();
    }
    
    public Viga(String identificador, Nodo nodoA, Nodo nodoB, Seccion secc, Material mat){
        super(identificador,nodoA,nodoB,secc,mat);
    }
    
}
