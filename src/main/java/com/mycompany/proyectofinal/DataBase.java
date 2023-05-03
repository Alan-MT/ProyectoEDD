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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author maverick
 */
public class DataBase {

    public principalScreen fra;
    public CargaMasiva carga;
    public Eliminar borrar;
    public IngresoTabla ingreso;
    private String[] tablas;
    private String[] PrimaryK;

    public DataBase() {
        this.fra = new principalScreen();
        fra.setVisible(true);
        this.carga = new CargaMasiva();
        this.borrar = new Eliminar();
        this.ingreso = new IngresoTabla();
        
        //boton para Cargar el jcomboBox
                fra.getCargar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fra.getComboBoxDatos().getSelectedItem().equals("Carga de Datos")) {
                    showPanel(carga);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Eliminacion")) {
                    showPanel(borrar);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Ingreso de Tablas")) {
                    showPanel(ingreso);
                }
            }
        });
                
                //Boton de abrir el archivo xml
                 carga.getBotonCargaM().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String ruta = "";
                LectorArchivo lector = new LectorArchivo();
                JFileChooser archivo = new JFileChooser();
                FileNameExtensionFilter filtrado = new FileNameExtensionFilter("XML", "xml");   
                archivo.setFileFilter(filtrado);
                int respuesta = archivo.showOpenDialog(carga.getBotonCargaM());
                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    ruta = archivo.getSelectedFile().getPath();
                    tablas = new String[10];
                    PrimaryK = new String[10];
                }
                lector.lector(ruta, tablas, PrimaryK);
                JOptionPane.showConfirmDialog(carga, ruta);
                mostrarTabla(tablas, PrimaryK);
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
    
    public void mostrarTabla(String[] tabla, String[] KeyP){
        String nombre[] = {"Nombre","Key Primary", "Accion"};
        String data[][] = new String[tabla.length][3];
        for(int i = 0;i<tabla.length;i++){
            data[i][0] = tabla[i];
            data[i][1] = KeyP[i];
            data[i][2] = "nada";
        }
        carga.getTablaCargaMasiva().setModel(new DefaultTableModel(data, nombre));
    }


}
