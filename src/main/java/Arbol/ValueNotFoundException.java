/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

/**
 *
 * @author maverick
 */
import java.util.NoSuchElementException;

public class ValueNotFoundException extends NoSuchElementException {
    public ValueNotFoundException() {
        super("VALOR NO FUE ENCONTRAS EN EL ARBOL");
    }
    public ValueNotFoundException(String mensaje){
        super(mensaje);
    }
}