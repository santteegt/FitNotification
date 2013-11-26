package com.fitnotif.notification;

import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import org.w3c.dom.Node;

/**
 * Clase que representa una Columna dentro de un registro
 * @author malgia
 * @version 1.0
 */
public class Column implements Serializable {
    private String name;
    private String value;
    
    /**
     * Constructor de la clase
     */
    public Column(){
        this.name="";
        this.value="";
    }
    
    /**
     * Constructor de la clase en base al nombre y al valor de la columna
     * @param name
     * @param value 
     */
    public Column(String name, String value){
        this.name=name;
        this.value=value;
    }
    
    /**
     * Constructor de la clase en base a un Nodo de tipo Column
     * @param columnNode 
     */
    public Column(Node columnNode){
        this.name=XMLHelper.getStringValueByAttribute(columnNode, "name");
        this.value=columnNode.getTextContent();
    }
    
    /**
     * Método que permite agregar Columns a un archivo XML
     * @return 
     */
    public Node toNode(){
        Node columnNode = XMLHelper.createNode("COL");
        XMLHelper.addAttribute(columnNode, "name", this.name);
        columnNode.setTextContent(this.value);
        return columnNode;
    }       

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
