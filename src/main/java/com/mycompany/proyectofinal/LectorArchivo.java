package com.mycompany.proyectofinal;

/**
 *
 * @author maverick
 */

    import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LectorArchivo {
    
    public LectorArchivo(){}
    
    public void lector(String ruta, String[] tabla, String[] KeyP){
        try {
      File archivo = new File(ruta);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(archivo);
      doc.getDocumentElement().normalize();
      NodeList listaEstructuras = doc.getElementsByTagName("estructura");
      
      int contador = 0;
      int contadorP = 0;
      
      for (int i = 0; i < listaEstructuras.getLength(); i++) {
        Node nodoEstructura = listaEstructuras.item(i); 
        
        
        if (nodoEstructura.getNodeType() == Node.ELEMENT_NODE) {
          Element elementoEstructura = (Element) nodoEstructura;
          String nombreTabla = elementoEstructura.getElementsByTagName("tabla").item(0).getTextContent();
          
          System.out.println("Tabla: " + nombreTabla);


          NodeList listaCampos = elementoEstructura.getChildNodes();

          for (int j = 0; j < listaCampos.getLength(); j++) {
            Node nodoCampo = listaCampos.item(j);
               
            if (nodoCampo.getNodeType() == Node.ELEMENT_NODE) {
              Element elementoCampo = (Element) nodoCampo;
              String nombreCampo = elementoCampo.getNodeName();
              String tipoCampo = elementoCampo.getTextContent();

              if ("clave" == nombreCampo) {
                KeyP[contadorP] = tipoCampo;
                contadorP++;
              }
              if ("tabla" == nombreCampo){
                  tabla[contador] = tipoCampo;
                  contador++;
              }
              System.out.println("Campo: " + nombreCampo + ", Tipo: " + tipoCampo);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
        System.out.print("tablas");
                for(int i =0; i < tabla.length;i++){
                    if(tabla[i] != null)
                    System.out.print(tabla[i]);
                   
                }
                System.out.print("LLave primaria");
                 for(int i =0; i < tabla.length;i++){
                    if(KeyP[i] != null)
                   System.out.print(KeyP[i]);
                }

                    

    }
    
    
}

  