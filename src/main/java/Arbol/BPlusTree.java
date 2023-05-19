/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

/**
 *
 * @author maverick
 */
public class BPlusTree {
    
    private Node root;
    //Definir el tamanio maximo de un nodo
    private static final int n = 10;

    public BPlusTree() {
        //se le llama a nodo diciendole que es una hoja
        root = new Node(true);
    }

    public void insert(int value) {
        Node leafNode = findLeafNode(value);
        try {
            insertIntoLeaf(leafNode, value);
        } catch (DuplicateValueException e) {
            System.out.println("ERROR: "+e.getMessage());
        }

    }
    //encuentra el nodo hoja donde el valor debería ser insertado
    private Node findLeafNode(int value) {
        Node actual = root;
        while (!actual.isLeaf()) {
            int i = 0;
            while (i < actual.getNumKeys() && value > actual.getKey(i)) {
                i++;
            }
            actual = actual.getChild(i);
        }
        return actual;
    }
    // insertar el valor en el nodo hoja
    private void insertIntoLeaf(Node leafNode, int value) throws DuplicateValueException {
        int index = 0;
        while (index < leafNode.getNumKeys() && value > leafNode.getKey(index)) {
            index++;
        }
        if (index < leafNode.getNumKeys() && value == leafNode.getKey(index)) {
            throw new DuplicateValueException("El valor "+ value+" ya existe en el arbol");
        }
        for (int i = leafNode.getNumKeys(); i > index; i--) {
            leafNode.setKey(i, leafNode.getKey(i - 1));
        }
        leafNode.setKey(index, value);
        leafNode.setNumKeys(leafNode.getNumKeys() + 1);
        //Si el nodo hoja está lleno después de la inserción
        if (leafNode.getNumKeys() == 2 * Node.n) {
            splitLeafNode(leafNode);
        }
    }

    private void splitLeafNode(Node leafNode) {
        Node newLeafNode = new Node(true);
        //Se determina el índice del medio en el nodo hoja original.
        int middleIndex = leafNode.getNumKeys() / 2;
        int newLeafIndex = 0;
        for (int i = middleIndex; i < leafNode.getNumKeys(); i++) {
            //Los elementos a partir del índice medio se copian en el nuevo nodo hoja.
            newLeafNode.setKey(newLeafIndex++, leafNode.getKey(i));
        }
        newLeafNode.setNumKeys(newLeafIndex);
        //El número de claves en el nuevo nodo hoja se establece en la cantidad de claves a partir del índice medio.
        leafNode.setNumKeys(middleIndex);
        //Se establece el puntero al siguiente nodo hoja del nuevo nodo hoja al siguiente nodo hoja del nodo original.
        newLeafNode.setNext(leafNode.getNext());
        //El puntero al siguiente nodo hoja del nodo original se actualiza para apuntar al nuevo nodo hoja.
        leafNode.setNext(newLeafNode);

/*         Si el nodo hoja dividido es la raíz del árbol, se crea
            una nueva raíz y se establecen los punteros de los nodos 
            hoja divididos como hijos de la nueva raíz.*/
        if (leafNode == root) {
            Node newRoot = new Node(false);
            newRoot.setKey(0, newLeafNode.getKey(0));
            newRoot.setChild(0, leafNode);
            newRoot.setChild(1, newLeafNode);
            newRoot.setNumKeys(1);
            leafNode.setParent(newRoot);
            newLeafNode.setParent(newRoot);
            root = newRoot;
        } else {
            /*Si el nodo hoja dividido no es la raíz del árbol, 
            se busca el padre del nodo hoja original y se inserta 
            la clave del primer elemento del nuevo nodo hoja en el padre. 
            Si el padre del nodo hoja original está lleno después de la inserción, 
            el nodo padre también se divide en dos nodos internos. */
            Node parent = leafNode.getParent();
            int index = 0;
            while (parent.getChild(index) != leafNode) {
                index++;
            }
            for (int i = parent.getNumKeys(); i > index; i--) {
                parent.setChild(i + 1, parent.getChild(i));
                parent.setKey(i, parent.getKey(i - 1));
            }
            parent.setKey(index, newLeafNode.getKey(0));
            parent.setChild(index + 1, newLeafNode);
            parent.setNumKeys(parent.getNumKeys() + 1);
            newLeafNode.setParent(parent);
            if (parent.getNumKeys() == 2 * Node.n) {
                splitInternalNode(parent);
            }
        }
    }

    private int findChildIndex(Node parent, Node child) {
        for (int i = 0; i <= parent.getNumKeys(); i++) {
            if (parent.getChild(i) == child) {
                return i;
            }
        }
        return -1;
    }

    public boolean look(int value){
        Node leafNode = findLeafNode(value);
        for (int i = 0; i < leafNode.getNumKeys(); i++) {
                if (leafNode.getKey(i) == value) {
                    return true;
                }
        }
        return false;
    }
    private void splitInternalNode(Node internalNode) {
        // Create a new internal node.
        Node newInternalNode = new Node(false);
        // Copy the second half of the keys and pointers from the original internal node to the new internal node.
        int middleIndex = internalNode.getNumKeys() / 2;
        int newInternalIndex = 0;
        for (int i = middleIndex + 1; i < internalNode.getNumKeys(); i++) {
        newInternalNode.setKey(newInternalIndex, internalNode.getKey(i));
        newInternalNode.setChild(newInternalIndex, internalNode.getChild(i));
        internalNode.setChild(i, null);
        newInternalIndex++;
        }
        newInternalNode.setChild(newInternalIndex, internalNode.getChild(internalNode.getNumKeys()));
        internalNode.setChild(internalNode.getNumKeys(), null);
        newInternalNode.setNumKeys(newInternalIndex);
        internalNode.setNumKeys(middleIndex);

        /* Si el nodo interno dividido es la raíz del árbol, 
se crea una nueva raíz y los punteros del nodo interno 
nuevo nodo interno se establecen como hijos de la nueva raíz. */
if (internalNode == root) {
    Node newRoot = new Node(false);
    newRoot.setKey(0, internalNode.getKey(middleIndex));
    newRoot.setChild(0, internalNode);
    newRoot.setChild(1, newInternalNode);
    newRoot.setNumKeys(1);
    internalNode.setParent(newRoot);
    newInternalNode.setParent(newRoot);
    root = newRoot;
} else {
/* Si el nodo interno dividido no es la raíz del árbol, 
    la clave del elemento medio del nodo interno original 
    se inserta en el nodo padre del nodo interno original. 
    Si el nodo padre del nodo interno original está lleno 
    después de la inserción, el nodo padre también se divide en dos nodos internos. */
    Node parent = internalNode.getParent();
    int index = 0;
    while (parent.getChild(index) != internalNode) {
        index++;
    }
    for (int i = parent.getNumKeys(); i > index; i--) {
        parent.setChild(i + 1, parent.getChild(i));
        parent.setKey(i, parent.getKey(i - 1));
    }
    parent.setKey(index, internalNode.getKey(middleIndex));
    parent.setChild(index + 1, newInternalNode);
    parent.setNumKeys(parent.getNumKeys() + 1);
    newInternalNode.setParent(parent);
    if (parent.getNumKeys() == 2 * Node.n) {
        splitInternalNode(parent);
    }
}
}
    @Override
    public String toString() {
        return root.toString();
    }
    public void delete(int value) {
        Node leafNode = findLeafNode(value);
    
        // Buscar la posición del valor en el nodo hoja
        int index = 0;
        while (index < leafNode.getNumKeys() && value > leafNode.getKey(index)) {
            index++;
        }
    
        // Verificar si el valor no está presente en el árbol
        if (index == leafNode.getNumKeys() || value != leafNode.getKey(index)) {
            System.out.println("El valor " + value + " no existe en el árbol.");
            return;
        }
    
        // Eliminar el valor del nodo hoja
        for (int i = index; i < leafNode.getNumKeys() - 1; i++) {
            leafNode.setKey(i, leafNode.getKey(i + 1));
        }
        leafNode.setNumKeys(leafNode.getNumKeys() - 1);
    
        // Verificar si el nodo hoja está por debajo del factor de ocupación mínimo
        if (leafNode.getNumKeys() < Node.n) {
            Node parent = leafNode.getParent();
            int leafIndex = findChildIndex(parent, leafNode);
    
            // Intentar pedir prestado una clave de los hermanos
            if (leafIndex > 0 && parent.getChild(leafIndex - 1).getNumKeys() > Node.n) {
                Node leftSibling = parent.getChild(leafIndex - 1);
                borrowFromLeftSibling(leafNode, leftSibling, parent, leafIndex);
            } else if (leafIndex < parent.getNumKeys() && parent.getChild(leafIndex + 1).getNumKeys() > Node.n) {
                Node rightSibling = parent.getChild(leafIndex + 1);
                borrowFromRightSibling(leafNode, rightSibling, parent, leafIndex);
            } else {
                // Fusionar con un hermano si no se puede pedir prestado una clave
                if (leafIndex > 0) {
                    Node leftSibling = parent.getChild(leafIndex - 1);
                    mergeWithLeftSibling(leafNode, leftSibling, parent, leafIndex - 1);
                    leafNode = leftSibling; // Actualizar el nodo hoja actual
                } else {
                    Node rightSibling = parent.getChild(leafIndex + 1);
                    mergeWithRightSibling(leafNode, rightSibling, parent, leafIndex);
                }
            }
        }
    }
    

    private void deleteFromLeaf(Node leafNode, int value) throws ValueNotFoundException {
        int index = 0;
        while (index < leafNode.getNumKeys() && value > leafNode.getKey(index)) {
            index++;
        }
        if (index >= leafNode.getNumKeys() || value != leafNode.getKey(index)) {
            throw new ValueNotFoundException("El valor " + value + " no se encuentra en el árbol");
        }
        for (int i = index; i < leafNode.getNumKeys() - 1; i++) {
            leafNode.setKey(i, leafNode.getKey(i + 1));
        }
        leafNode.setNumKeys(leafNode.getNumKeys() - 1);
        // Si el nodo hoja no es la raíz y tiene menos de n claves después de la eliminación, se realiza el proceso de préstamo o fusión.
        if (leafNode != root && leafNode.getNumKeys() < n) {
            borrowOrMergeLeafNode(leafNode);
        }
    }

    private void borrowOrMergeLeafNode(Node leafNode) {
        Node parent = leafNode.getParent();
        int leafIndex = 0;
        while (parent.getChild(leafIndex) != leafNode) {
            leafIndex++;
        }
        if (leafIndex > 0 && parent.getChild(leafIndex - 1).getNumKeys() > n) {
            // Prestar una clave del hermano izquierdo
            Node leftSibling = parent.getChild(leafIndex - 1);
            borrowFromLeftSibling(leafNode, leftSibling, parent, leafIndex);
        } else if (leafIndex < parent.getNumKeys() && parent.getChild(leafIndex + 1).getNumKeys() > n) {
            // Prestar una clave del hermano derecho
            Node rightSibling = parent.getChild(leafIndex + 1);
            borrowFromRightSibling(leafNode, rightSibling, parent, leafIndex);
        } else if (leafIndex > 0) {
            // Fusionar con el hermano izquierdo
            Node leftSibling = parent.getChild(leafIndex - 1);
            mergeWithLeftSibling(leafNode, leftSibling, parent, leafIndex - 1);
        } else {
            // Fusionar con el hermano derecho
            Node rightSibling = parent.getChild(leafIndex + 1);
            mergeWithRightSibling(leafNode, rightSibling, parent, leafIndex);
        }
    }

    private void borrowFromLeftSibling(Node leafNode, Node leftSibling, Node parent, int leafIndex) {
        // Prestar la clave más grande del hermano izquierdo
        int borrowedKey = leftSibling.getKey(leftSibling.getNumKeys() - 1);
        leftSibling.setNumKeys(leftSibling.getNumKeys() - 1);
    
        // Insertar la clave prestada en el nodo hoja actual
        for (int i = leafNode.getNumKeys(); i > 0; i--) {
            leafNode.setKey(i, leafNode.getKey(i - 1));
        }
        leafNode.setKey(0, borrowedKey);
        leafNode.setNumKeys(leafNode.getNumKeys() + 1);
    
        // Actualizar la clave en el padre
        parent.setKey(leafIndex - 1, leafNode.getKey(0));
    }
    
    private void borrowFromRightSibling(Node leafNode, Node rightSibling, Node parent, int leafIndex) {
        // Prestar la clave más pequeña del hermano derecho
        int borrowedKey = rightSibling.getKey(0);
    
        // Mover las claves en el hermano derecho hacia la izquierda
        for (int i = 0; i < rightSibling.getNumKeys() - 1; i++) {
            rightSibling.setKey(i, rightSibling.getKey(i + 1));
        }
        rightSibling.setNumKeys(rightSibling.getNumKeys() - 1);
    
        // Insertar la clave prestada en el nodo hoja actual
        leafNode.setKey(leafNode.getNumKeys(), borrowedKey);
        leafNode.setNumKeys(leafNode.getNumKeys() + 1);
    
        // Actualizar la clave en el padre
        parent.setKey(leafIndex, rightSibling.getKey(0));
    }
    
    private void mergeWithLeftSibling(Node leafNode, Node leftSibling, Node parent, int leftIndex) {
        // Mover todas las claves del nodo hoja actual al hermano izquierdo
        for (int i = 0; i < leafNode.getNumKeys(); i++) {
            leftSibling.setKey(leftSibling.getNumKeys() + i, leafNode.getKey(i));
        }
        leftSibling.setNumKeys(leftSibling.getNumKeys() + leafNode.getNumKeys());
    
        // Actualizar los punteros de siguiente nodo hoja
        leftSibling.setNext(leafNode.getNext());
    
        // Eliminar el nodo hoja actual
        parent.setChild(leftIndex + 1, null);
        parent.setKey(leftIndex, parent.getKey(leftIndex + 1));
        parent.setNumKeys(parent.getNumKeys() - 1);
    }
    
    private void mergeWithRightSibling(Node leafNode, Node rightSibling, Node parent, int leafIndex) {
        // Mover todas las claves del hermano derecho al nodo hoja actual
        for (int i = 0; i < rightSibling.getNumKeys(); i++) {
            leafNode.setKey(leafNode.getNumKeys() + i, rightSibling.getKey(i));
        }
        leafNode.setNumKeys(leafNode.getNumKeys() + rightSibling.getNumKeys());
    
        // Actualizar los punteros de siguiente nodo hoja
        leafNode.setNext(rightSibling.getNext());
    
        // Eliminar el hermano derecho
        parent.setChild(leafIndex + 1, null);
        parent.setKey(leafIndex, parent.getKey(leafIndex + 1));
        parent.setNumKeys(parent.getNumKeys() - 1);
    }
    


}