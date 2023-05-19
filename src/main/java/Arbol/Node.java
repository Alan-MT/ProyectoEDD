/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

/**
 *
 * @author maverick
 */
public class Node {
    
    //Un array de enteros que almacena las claves(valores) asociados con este nodo
    private int[] keys;
    //Un array de nodos que alamacena los hijos del nodo actual
    private Node[] children;
    //Un puntero al nodo padre del nodo actual
    private Node parent;
    //Un puntero al siguiente nodo en el mismo nivel de la jerarquia del arbol 
    private Node next;
    //Un entero que indica el numero de claves actuales almacendas en este nodo
    private int numKeys;
    //Un booleano que indica si este nodo es una hoja en el arbol B+
    private boolean isLeaf;

    public static final int n = 3;

    public Node(boolean isLeaf) {
        keys = new int[2 * n];
        children = new Node[2 * n + 1];
        numKeys = 0;
        parent = null;
        next = null;
        this.isLeaf = isLeaf;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < numKeys; i++) {
            sb.append(keys[i]);

            if (i != numKeys - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        if (!isLeaf) {
            sb.append("{");
            for (int i = 0; i <= numKeys; i++) {
                sb.append(children[i]);
   
                if (i != numKeys) {
                    break;
                }
            }
            sb.append("}");
        }

        if (next != null) {
            sb.append(" -> ");
           sb.append(next.toString());
        }

        return sb.toString();
    }

    public int getKey(int index) {
        return keys[index];
    }

    public void setKey(int index, int value) {
        keys[index] = value;
    }

    public Node getChild(int index) {
        return children[index];
    }

    public void setChild(int index, Node child) {
        children[index] = child;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
}

