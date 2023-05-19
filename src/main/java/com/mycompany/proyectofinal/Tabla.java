/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal;

/**
 *
 * @author maverick
 */
public class Tabla {
    
    private char[] nombre;
    private char[] llave;
    
    public Tabla(char[] nombre, char[] llave){
        this.nombre = nombre;
        this.llave = llave;
    }

    public char[] getNombre() {
        return nombre;
    }

    public void setNombre(char[] nombre) {
        this.nombre = nombre;
    }

    public char[] getLlave() {
        return llave;
    }

    public void setLlave(char[] llave) {
        this.llave = llave;
    }
    
    
    
}
