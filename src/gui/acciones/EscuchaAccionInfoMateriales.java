/*
 * EscuchaAccionInfoMateriales.java
 *
 * Created on 25 de febrero de 2007, 01:20 AM
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

import estructural.Material;
import gui.VentanaDefinirEstructura;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import tremo.Main;

/**
 *
 * @author __Edgar Bolaños Lujan__
 */
public class EscuchaAccionInfoMateriales implements ActionListener{
    private JFrame ventInfoMateriales;
    
    /** Creates a new instance of EscuchaAccionInfoMateriales */
    public EscuchaAccionInfoMateriales() {
    }

    public void actionPerformed(ActionEvent e) {
        String mensajeTemp = "";
        Material[] materiales = VentanaDefinirEstructura.abrirArchivoMaterial();
        for(int i=0;i<materiales.length;i++){
            if( (materiales[i]!=null) ){
                mensajeTemp = mensajeTemp+
                        "Material Id: "+materiales[i].getIdentificador()+
                        "\nMod de elasticidad: "+materiales[i].getModuloDeElasticidad()+" kg/cm2"+
                        "\nPeso volumetrico: "+materiales[i].getPesoVolumetrico()+" ton/m3\n\n";
            }
        }

        JPanel contenedor = new JPanel();
        contenedor.setBounds(25,50,350,200);
        contenedor.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane();                                       
        JTextArea area = new JTextArea();
        area.setText(mensajeTemp);
        area.setEditable(false);
        area.setBackground(Color.LIGHT_GRAY);
        area.setCaretPosition(0);

        scroll.setViewportView(area);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        contenedor.add(scroll);
        contenedor.setVisible(true);                    

        JLabel etiqueta = new JLabel();
        JButton aceptar = new JButton("Aceptar");
        aceptar.setMnemonic('A');
        etiqueta.setText("Materiales en archivo \"materiales.dat\":");
        etiqueta.setBounds(25,17,200,15);
        aceptar.setBounds(160,280,80,30);                    

        ventInfoMateriales = new JFrame("INFORMACION");
        ventInfoMateriales.setLayout(null);
        ventInfoMateriales.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventInfoMateriales.setResizable(false);
        ventInfoMateriales.setBounds(300,200,400,350);
        Main.getAplicacion().setEnabled(false);

        aceptar.addActionListener(
            new ActionListener(){                        
                public void actionPerformed(ActionEvent e) {
                    ventInfoMateriales.setVisible(false); 
                    Main.getAplicacion().setVisible(true);
                    Main.getAplicacion().setEnabled(true);
                    Main.getAplicacion().setVisible(true);
                }
            }
        );
        ventInfoMateriales.add(contenedor);
        ventInfoMateriales.add(etiqueta);
        ventInfoMateriales.add(aceptar);
        ventInfoMateriales.setVisible(true);
    }
    
}
