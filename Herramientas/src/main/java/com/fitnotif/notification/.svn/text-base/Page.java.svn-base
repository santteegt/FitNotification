package com.fitnotif.notification;

import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase que representa un Page dentro de la notificación
 * @author malgia
 * @version 1.0
 */
public class Page implements Serializable{
    private String id;
    private List<Register> registers;
    
    /**
     * Constructor de la clase
     */
    public Page(){
        this.id="";
        this.registers=new ArrayList<Register>();
    }
    
    /**
     * Constructor de la clase en base a su id
     * @param id 
     */
    public Page(String id){
        this.id=id;
        this.registers=new ArrayList<Register>();
    }
    
    /**
     * Constructor de la clase en base a un Nodo de tipo Page
     * @param pageNode 
     */
    public Page(Node pageNode){
        this.id=XMLHelper.getStringValueByAttribute(pageNode, "id");
        this.registers=new ArrayList<Register>();
        NodeList registerNodes = XMLHelper.getChildrenByName(pageNode, "REG");
        if(registerNodes!=null){
            for(int i=0; i<registerNodes.getLength(); i++){
                Node registerNode=registerNodes.item(i);
                Register register=new Register(registerNode);
                this.addRegister(register);
            }
        }
    }
    
    /**
     * Método que permite agregar Registers al Page
     * @param register 
     */
    public void addRegister(Register register){
        this.registers.add(register);
    }
   
    /**
     * Método que permite agregar Pages a un archivo XML
     * @return
     * @throws Exception 
     */
    public Node toNode() throws Exception {
        Node pageNode = XMLHelper.createNode("PAGE");
        XMLHelper.addAttribute(pageNode, "id", this.id);
        for (Register reg : this.getRegisters()) {
            XMLHelper.appendChild(pageNode, reg.toNode());
        }
        return pageNode;
    }    
    
    /**
     * Devuelve el registro de acuerdo a su id.
     * @param id
     * @return 
     */
    public Register getRegisterById(String id){
        for(Register register:this.registers){
            if(register.getId().compareTo(id)==0){
                return register;
            }
        }
        return null;
    }
    
    /**
     * Devuelve el valor del registro de acuerdo a su id.
     * @param id
     * @return 
     */
    public String getRegisterValueById(String id){
        String value=null;
        for(Register register:this.registers){
            if(register.getId().compareTo(id)==0){
                value=register.getId();
                break;
            }
        }
        return value;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public void setRegisters(List<Register> registers) {
        this.registers = registers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }        
}
