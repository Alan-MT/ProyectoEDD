package com.mycompany.proyectofinal;

import UI.*;
import java.awt.BorderLayout;
import static java.awt.SystemColor.text;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.*;
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
    private String[] tablas2;
    public Mostrar mostrar;
    public ArrayList<String> listaTotal;
    public String[] tablaCarga;

    public DataBase() {
        this.fra = new principalScreen();
        fra.setVisible(true);
        this.carga = new CargaMasiva();
        this.borrar = new Eliminar();
        this.ingreso = new IngresoTabla();
        this.mostrar = new Mostrar();
        this.listaTotal = new ArrayList<>();
        
        
        //boton para Cargar el jcomboBox
                fra.getCargar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fra.getComboBoxDatos().getSelectedItem().equals("Carga de Datos")) {
                    showPanel(carga);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Eliminacion")) {
                    showPanel(borrar);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Ingreso de Tablas")) {
                    showPanel(ingreso);
                } else if (fra.getComboBoxDatos().getSelectedItem().equals("Mostrar Tablas")){
                            showPanel(mostrar.getPanelMostrar());
                            tablas2 = new String[ingreso.getTablasIngresadas().getItemCount()];
                            for(int i=0; i<ingreso.getTablasIngresadas().getItemCount();i++){
                                tablas2[i] = ingreso.getTablasIngresadas().getItemAt(i);
                            }
                            llenarMostrar(mostrar.getTablasMostrar(), tablaCarga, tablas2);
                            
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
                    //carga.getTablaCargaMasiva().isc
                    ruta = archivo.getSelectedFile().getPath();
                    tablas = new String[10];
                    PrimaryK = new String[10];
                }
                lector.lector(ruta, tablas, PrimaryK);
                JOptionPane.showMessageDialog(carga, ruta);
                mostrarTabla(tablas, PrimaryK);
                carga.getTablaCargaMasiva().setEnabled(false);
            }
        });
                 
                ingreso.getBotonIngresoManual().addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(ingreso.getNombreTabla().getText().isEmpty() || ingreso.getNombreLLaveP().getText().isEmpty()){  
                            JOptionPane.showMessageDialog(ingreso, "Debes llenar los campos");}
                        else{
                            ingreso.getTablasIngresadas().addItem(ingreso.getNombreTabla().getText()+"---"+ingreso.getNombreLLaveP().getText());
                            ingreso.getNombreLLaveP().setText("");
                            ingreso.getNombreTabla().setText("");

                        }
                    } 
                           
                });
                
                // boton carga masiva ejectura
                carga.getBotonEjecutar().addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(carga.getMasivaComboBox().getSelectedItem().equals("Editar Tabla")){
                            carga.getTablaCargaMasiva().enable(true);
                        }else{
                            DefaultTableModel model = (DefaultTableModel) carga.getTablaCargaMasiva().getModel();
                            model.setRowCount(0);
                            tablaCarga = new String[10];
                            tablaCarga = tablas;

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
    
    public void mostrarTabla(String[] tabla, String[] KeyP){
        DefaultTableModel model = (DefaultTableModel) carga.getTablaCargaMasiva().getModel();
        model.setRowCount(0);
        String nombre[] = {"Nombre","Key Primary"};
        String data[][] = new String[tabla.length][2];
        for(int i = 0;i<tabla.length;i++){
            if(tabla[i] != null && !tabla[i].isEmpty()){
            data[i][0] = tabla[i];
            data[i][1] = KeyP[i];
            }
        }
        carga.getTablaCargaMasiva().setModel(new DefaultTableModel(data, nombre));
    }   

    public void llenarMostrar(JComboBox lista, String[] tabla, String[] ingresoManual){
        lista.removeAllItems();

        // El arreglo no está vacío
        int numLlenos = 0;
        int numlleno2 = 0;
        if (tabla != null) {
            for (String str : tabla) {
                if (str != null && !str.isEmpty()) {
                    numLlenos++;
                }
            }
        }
        for (String str : ingresoManual) {
            if (str != null && !str.isEmpty()) {
                numlleno2++;
            }
        }
        if (numLlenos > 0) {
            listaTotal.addAll(Arrays.asList(tabla));
        }
        if (numlleno2 > 0) {
            listaTotal.addAll(Arrays.asList(ingresoManual));
        }
        HashSet<String> hashSet = new HashSet<>(listaTotal);
        listaTotal.clear();
        listaTotal.addAll(hashSet);
        for (String elemento : listaTotal) {
            lista.addItem(elemento);
        }

    }

            
            //lista.setModel(new DefaultComboBoxModel<>(nombres));
        


}

   
    



