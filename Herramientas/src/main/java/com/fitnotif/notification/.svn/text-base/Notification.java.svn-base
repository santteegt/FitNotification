package com.fitnotif.notification;

import com.fitnotif.common.NotificationException;
import com.fitnotif.parser.NotificationParser;
import com.fitnotif.parser.XMLHelper;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase que representa una Notificacion
 * @author malgia
 * @version 1.0
 */
public class Notification extends Request{

    private static final String CONTROL="CONTROL";
    
    private List<Page> pages=new ArrayList<Page>();
    private List<Option> options=new ArrayList<Option>();
    
    /**
     * Constructor de la clase
     */
    public Notification(){
    }
    
    /**
     * Constructor de la clase
     * @param nr
     * @throws Exception 
     */
    public Notification(NotificationParser nr){
          Node node = nr.getChildByName("HEADER");
          super.fill(node);
          this.fillDetail(nr);
          this.fillOptions(nr);
    }

    @Override
    public Object cloneMe(){
        Notification notification=null;
        try {
            NotificationParser nr=new NotificationParser(this.toXml());
            notification= new Notification(nr);
        } catch (Exception ex) {
        }
        return notification;
    }
    
    /**
     * Método que recibe un archivo XML y lo transforma en Notification
     * @param nr
     * @throws Exception 
     */
    private void fillDetail(NotificationParser nr){
        Node detailNode = nr.getChildByName("DETAIL");
        NodeList pageNodes = nr.getChildrenByName(detailNode, "PAGE");
        if(pageNodes!=null){
            for(int i=0; i< pageNodes.getLength(); i++){
                Node pageNode=pageNodes.item(i);
                Page page=new Page(pageNode);
                this.addPage(page);
            }
        }
    }
    
    /**
     * Permite agregar Pages en una Notificacion
     * @param page 
     */
    public void addPage(Page page){
        this.pages.add(page);
    }  

    /**
     * Métodoq que pone en el objeto las opciones de respuesta para una notificación
     * @param nr 
     */
    private void fillOptions(NotificationParser nr){
        Node optionsNode = nr.getChildByName("OPTIONS");
        NodeList optionsList = nr.getChildrenByName(optionsNode, "OP");
        if (optionsList != null) {
            for (int i = 0; i < optionsList.getLength(); i++) {
                Node n = optionsList.item(i);
                Option op=new Option();
                op.setId(n.getAttributes().getNamedItem("id").getNodeValue());
                op.setDescription(n.getAttributes().getNamedItem("desc").getNodeValue());
                if(n.getAttributes().getNamedItem("ans").getNodeValue().compareTo("1")==0){
                    op.setAnswer(true);
                }
                this.options.add(op);
            }
        }
    }
   
    /**
     * Método que convierte el objeto Notification en un archivo XML
     * @return
     * @throws Exception 
     */
    public String toXml() throws Exception {
        return XMLHelper.nodeToString(this.createNotification());
    }

    /**
     * Método usado por toXml()
     * @return 
     */
    private Node createNotification(){
        Node newNotification = XMLHelper.createNode("AUTORIZATION");
        XMLHelper.appendChild(newNotification, super.getHeaderNode());
        XMLHelper.appendChild(newNotification, this.toNode());
        XMLHelper.appendChild(newNotification, this.optionsToNode());
        return newNotification;
    }    
    
    /**
     * Método que permite agregar Pages al archivo XML
     * @return
     * @throws Exception 
     */
    private Node toNode(){
        Node detailNode = XMLHelper.createNode("DETAIL");
        for (Page page : this.getPages()) {
            try {
                XMLHelper.appendChild(detailNode, page.toNode());
            } catch (Exception ex) {
                throw new NotificationException("ERRXML", "NO SE PUDO AGREGAR EL PAGE {0}", ex, page.getId());
            }
        }
        return detailNode;
    }
    
    /**
     * Método que permite agregar las opciones de respuesta al archivo XML
     * @return
     * @throws Exception 
     */
    private Node optionsToNode(){
        Node optionNode = XMLHelper.createNode("OPTIONS");
        for (Option op : this.getOptions()) {
            try {
                XMLHelper.appendChild(optionNode, op.toNode());
            } catch (Exception ex) {
                throw new NotificationException("ERRXML", "NO SE PUDO AGREGAR LA OPCIÓN {0}", ex, op.getId());
            }
        }
        return optionNode;
    }    
    
    /**
     * Devuelve un Page de acuerdo a su Id
     * @param id
     * @return 
     */
    public Page getPageById(String id){
        for(Page page:this.pages){
            if(page.getId().compareTo(id)==0){
                return page;
            }
        }
        return null;
    }
    
    /**
     * Devulve el valor de un campo de control
     * @param name
     * @return 
     */
    public String getControlFieldValueByName(String name){
        Page page=this.getPageById(CONTROL);
        String value=null;
        if(page!=null){
            Register reg=page.getRegisterById("0");
            if(reg!=null){
                value=reg.getColumnValueByName(name);
            }
        }
        return value;
    }
    
    /**
     * Agrega un campo de control
     * @param name
     * @param value 
     */
    public void addControlField(String name, String value){
        Page page=this.getPageById(CONTROL);
        Register reg=null;
        if(page!=null){
            reg=page.getRegisterById("0");
            if(reg!=null){
                Column col = reg.getColumnByName(name);
                if(col!=null){
                    col.setValue(value);
                }else{
                    reg.addColumn(new Column(name, value));
                }
            }else{
                reg=new Register("0");
                reg.addColumn(new Column(name, value));
                page.addRegister(reg);
            }
        }else{
            page=new Page(CONTROL);
            reg=new Register("0");
            reg.addColumn(new Column(name, value));
            page.addRegister(reg);
            this.addPage(page);
        }
    }
    
    public void deleteControlFieldByName(String name){
        Page page=this.getPageById(CONTROL);
        if(page!=null){
            Register reg=page.getRegisterById("0");
            if(reg!=null){
                reg.deleteColumnByName(name);
            }
        }
    }
    
    public void deleteAllPages(){
        this.pages.clear();
    }
    
    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
    
    
    
    public List<Option> getOptions(){
        return this.options;
    }
    
    public void setOptions(List<Option> options){
        this.options=options;
    }
    
}
