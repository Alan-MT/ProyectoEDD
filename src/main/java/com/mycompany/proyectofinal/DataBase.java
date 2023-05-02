package com.mycompany.proyectofinal;

import UI.CargaMasiva;
import UI.Eliminar;
import UI.IngresoTabla;
import UI.principalScreen;
import java.awt.BorderLayout;
import static java.awt.SystemColor.text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author maverick
 */
public class DataBase {

    public principalScreen fra;
    public CargaMasiva carga;
    public Eliminar borrar ;
    public IngresoTabla ingreso;

    public DataBase() {
        this.fra = new principalScreen();
        fra.setVisible(true);
        this.carga = new CargaMasiva();
        this.borrar = new Eliminar();
        this.ingreso  = new IngresoTabla();
    }

    public void Iniciar() {
        fra.getCargar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fra.getComboBoxDatos().getSelectedItem().equals("Carga de Datos")) {
                    showPanel(carga);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Eliminacion")) {
                    showPanel(borrar);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Ingreso de Tablas")){
                    showPanel(ingreso);
                }   
            }
        });
    }

    public void showPanel(JPanel segundo) {
        segundo.setSize(795, 550);
        segundo.setLocation(0, 0);
        fra.getPanelCambiante().removeAll();
        fra.getPanelCambiante().add(segundo, BorderLayout.CENTER);
        fra.getPanelCambiante().revalidate();
        fra.getPanelCambiante().repaint();
    }

}