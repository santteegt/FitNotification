package com.fitnotif.notification;

import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import org.w3c.dom.Node;

/**
 * Clase que representa las posibles opciones de respuesta que tiene la notifiación
 * @author malgia
 * @version 1.0
 */
public class Option implements Serializable {
    private String id;
    private String description;
    private boolean answer;
    
    /**
     * Constructor de la clase
     */
    public Option(){
        this.id="";
        this.description="";
        this.answer=false;
    }
    
    /**
     * Constructor de la clase en base a toda su información
     * @param id
     * @param description
     * @param answer 
     */
    public Option(String id, String description, boolean answer){
        this.id=id;
        this.description=description;
        this.answer=answer;
    }
    
    /**
     * Método que permite agregar Options a un archivo XML
     * @return
     * @throws Exception 
     */
    public Node toNode() throws Exception{
        Node optionNode = XMLHelper.createNode("OP");
        XMLHelper.addAttribute(optionNode, "id", this.id);
        XMLHelper.addAttribute(optionNode, "desc", this.description);
        XMLHelper.addAttribute(optionNode, "ans", this.answer);
        return optionNode;   
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public boolean isAnswer(){
        return this.answer;
    }
    
    public void setId(String id){
        this.id=id;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public void setAnswer(boolean answer){
        this.answer=answer;
    }    
}
