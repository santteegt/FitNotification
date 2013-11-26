package com.fitnotif.notification;

import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase que representa un Register dentro de una página
 * @author malgia
 * @version 1.0
 */
public class Register implements Serializable {
    private String id;
    private List<Column> columns=new ArrayList<Column>();
    
    /**
     * Constructor de la clase
     */
    public Register(){
        this.id="";
    }
    
    /**
     * Constructor de la clase en base a su id
     * @param id 
     */
    public Register(String id){
        this.id=id;
    }
    
    /**
     * Constructor de la clase en base a un nodo de tipo Register
     * @param registerNode 
     */
    public Register(Node registerNode){
        this.id=XMLHelper.getStringValueByAttribute(registerNode, "id");
        NodeList columnNodes = XMLHelper.getChildrenByName(registerNode, "COL");
        if(columnNodes!=null){
            for(int i=0; i<columnNodes.getLength(); i++){
                Node columnNode=columnNodes.item(i);
                Column column=new Column(columnNode);
                this.addColumn(column);
            }
        }
    }
    
    /**
     * Método que permite agregar Columns a un Register
     * @param column 
     */
    public void addColumn(Column column){
        this.columns.add(column);
    }
    
    /**
     * Método que permite agregar Registers a un archivo XML
     * @return
     * @throws Exception 
     */
    public Node toNode() throws Exception {
        Node registerNode = XMLHelper.createNode("REG");
        XMLHelper.addAttribute(registerNode, "id", this.id);
        for (Column col : this.getColumns()) {
            XMLHelper.appendChild(registerNode, col.toNode());
        }
        return registerNode;
    }     
    
    /**
     * Devuelve la columna de acuerdo a su nombre.
     * @param name
     * @return 
     */    
    public Column getColumnByName(String name){
        for(Column column:this.columns){
            if(column.getName().compareTo(name)==0){
                return column;
            }
        }
        return null;
    }
    
    /**
     * Devuelve el valor de la columna de acuerdo a su nombre.
     * @param name
     * @return 
     */      
    public String getColumnValueByName(String name){
        String value=null;
        for(Column column:this.columns){
            if(column.getName().compareTo(name)==0){
                value=column.getValue();
                break;
            }
        }
        return value;
    }

    /**
     * Elimina la columna de acuerdo a su nombre.
     * @param name
     * @return 
     */    
    public void deleteColumnByName(String name){
        Column col=null;
        for(Column column:this.columns){
            if(column.getName().compareTo(name)==0){
                col=column;
                break;
            }
        }
        if(col!=null){
            this.columns.remove(col);
        }
    }    
    
    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    } 
}
