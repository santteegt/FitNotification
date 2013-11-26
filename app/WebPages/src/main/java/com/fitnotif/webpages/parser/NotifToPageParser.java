
package com.fitnotif.webpages.parser;

import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.webpages.NotifWebPage;
import com.fitnotif.webpages.WColumn;
import com.fitnotif.webpages.WPage;
import com.fitnotif.webpages.WRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Clase encargada de convertir un detalle de Notificacion a su similar en
 * NotifWebPage
 * @author santiago
 * @version 1.0
 */
public class NotifToPageParser {
    
    Notification notification;
    
    public NotifToPageParser(Notification notification){
        this.notification = notification;
    }   
    
    /**
     * Conversion de definicion a un Objeto que representa un WebPage
     * @param node
     * @return
     * @throws Exception 
     */
    public NotifWebPage parseWebPage()throws Exception {
        
        NotifWebPage webPage = new NotifWebPage();
        
        for(Page page:this.notification.getPages()){
            if(page.getId().compareTo("CONTROL") != 0 && page.getRegisters().size() > 0){
                parseNotifPage(page, webPage);
            }
        }
        return webPage;
    }
    
    /**
     * Maneja la definicion de las paginas de la notificacion
     * @param node
     * @param webPage
     * @return
     * @throws Exception 
     */
    private NotifWebPage parseNotifPage(Page page, NotifWebPage webPage)throws Exception {
        
        WPage wpage = new WPage();
        wpage.setParent(webPage);
        webPage.add(wpage);

        wpage.setAttrValue("id", page.getId().toLowerCase());        
        /*NamedNodeMap attrs = node.getAttributes();
        NodeList children = node.getChildNodes();

        for (int i = 0; i < attrs.getLength(); i++) {
            Node nodo = attrs.item(i);
            if (nodo.getNodeType() != Node.TEXT_NODE) {
                page.setXMLValue(nodo.getNodeName(), nodo.getNodeValue());
            }
        }*/

        Collection<WRegister> items = new ArrayList<WRegister>(page.getRegisters().size());
        Boolean isColumn = page.getRegisters().size() == 1 ? true:false;
        wpage.setId(isColumn ? "col":"tbl");
        
        if(!isColumn){
            WRegister header = new WRegister();
            header.setId("header");
            header.addAll( this.parseHeader(page.getRegisters().iterator().next(), header) );
            items.add(header);
        }
        
        for(Register register:page.getRegisters()){
            items.add(this.parseRegister(register, wpage, isColumn));
        }            

        wpage.addAll(items);        
        return webPage;
    }
    
    /**
     * Genera la cabecera para una pagina de tipo Tabla
     * @param node
     * @return
     * @throws Exception 
     */
    private Collection<WColumn> parseHeader(Register register, WRegister parent) throws Exception {
        //String tipo = getClassName(node.getAttributes());
        Collection<WColumn> items = new ArrayList<WColumn>();
        
        WColumn label;
        for(Column column:register.getColumns()){
            label = new WColumn();
            label.setId("labelt");            
            label.setText(column.getName());
            label.setParent(parent);
            items.add(label);
        }
        
        return items;
    }   
    
    /**
     * Genera un registro de una pagina
     * @param node
     * @param page
     * @return
     * @throws Exception 
     */
    private WRegister parseRegister(Register register, WPage page, Boolean isColumn) throws Exception{            
        WRegister wreg = new WRegister();
        wreg.setId("row"+ (isColumn?"C":"T"));
        wreg.setParent(page);
        //page.add(wreg);

        /*
        NamedNodeMap attrs = node.getAttributes();
        NodeList children = node.getChildNodes();

        for (int i = 0; i < attrs.getLength(); i++) {
            Node nodo = attrs.item(i);
            if (nodo.getNodeType() != Node.TEXT_NODE) {
                register.setXMLValue(nodo.getNodeName(), nodo.getNodeValue());
            }
        }*/

        Collection<WColumn> items = new ArrayList<WColumn>(register.getColumns().size());        
        
        
        for(Column column:register.getColumns()){            
            items.addAll(this.parseColumn(column, wreg, isColumn));
        }
        

        wreg.addAll(items);

        return wreg;
    }
    
    /**
     * Genera un objeto una columna de un registro
     * @param node
     * @return
     * @throws Exception 
     */
    private Collection<WColumn> parseColumn(Column column, WRegister parent, Boolean isColumn) throws Exception {
        //String tipo = getClassName(node.getAttributes());
        Collection<WColumn> items = new ArrayList<WColumn>();
        
        if(column.getName().compareTo("DATA") == 0){
            String data = column.getValue();
            StringTokenizer token = new StringTokenizer(data,"-!-");
            items.addAll(parseData(token, parent));
            return items;
        }
        
        if(isColumn){
            WColumn label = new WColumn();
            label.setId("label");
            label.setAttrValue("id", "label");
            label.setText(column.getName());
            label.setParent(parent);
            items.add(label);
        }
        
        WColumn wcol = new WColumn();
        wcol.setId("field");
        wcol.setAttrValue("id", "field");
        wcol.setText(column.getValue() != null ? column.getValue(): "INFORMACIÓN NO PROPORCIONADA");
        wcol.setParent(parent);
        items.add(wcol);        
        
        return items;
    }
    
    private Collection<WColumn> parseData(StringTokenizer token, WRegister parent) throws Exception {
        Collection<WColumn> items = new ArrayList<WColumn>();
        StringTokenizer fieldtoken;
        WColumn label;
        WColumn wcol;
        while(token.hasMoreTokens()){
            fieldtoken = new StringTokenizer(token.nextToken(), "|");
            
            label = new WColumn();
            label.setId("label");
            label.setAttrValue("id", "label");
            label.setText(fieldtoken.nextToken());
            label.setParent(parent);
            items.add(label);
            
            wcol = new WColumn();
            wcol.setId("field");
            wcol.setAttrValue("id", "field");
            wcol.setText(fieldtoken.hasMoreTokens() ?fieldtoken.nextToken():"INFORMACIÓN NO PROPORCIONADA");
            wcol.setParent(parent);
            items.add(wcol);
        }
        return items;
    }
          
    
    /**
     * Implementa una columna por clase especifica
     * @param node
     * @param type
     * @return
     * @throws Exception 
     */
    /*private WColumn parseColumnByClass(Node node, Class type) throws Exception {
        NamedNodeMap attrs = node.getAttributes();
        NodeList nodos = node.getChildNodes();
        WColumn column;       

        try {
            column = (WColumn) type.newInstance();
        } catch (InstantiationException e) {
            throw new NotificationException("PARSE002", "NO SE PUDO CREAR ELEMENTO DE LA CLASE {0}", type);
        } catch (IllegalAccessException e) {
            throw new NotificationException("PARSE003", "NO SE PUDO CREAR ELEMENTO DE LA CLASE {0}", type);
        }

        for (int i = 0; i < attrs.getLength(); i++) {
            Node nodo = attrs.item(i);
            if (nodo.getNodeType() != Node.TEXT_NODE) {
                column.setXMLValue(nodo.getNodeName(), nodo.getNodeValue());
            }
        }

        for (int i = 0; i < nodos.getLength(); i++) {
            Node nodo = nodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                if (nodo.getNodeName().equals("attribute")) {
                    NamedNodeMap attrNodo = nodo.getAttributes();
                    Node nom = attrNodo.getNamedItem("nom");
                    Node val = nodo.getFirstChild();

                    if (nom == null || val == null) {
                        //Debug.warn("No se pudo leer un tag del xml");
                    } else {
                        column.setXMLValue(nom.getNodeValue(),
                                val.getNodeValue());
                    }
                } else if (nodo.getNodeName().equals("dataSource")) {
                    widget.setDataSource(parseDataSource(nodo));
                } else if (nodo.getNodeName().equals("assistant")) {
                    ((FormElement) widget).setAssistant(ParserXml.parse(nodo,
                            Assistant.class));
                } else if (nodo.getNodeName().equals("formatters")) {
                    ((FormElement) widget).getBehaviors().addAll(
                            ParserXml.parse(nodo, LinkedList.class));
                } else if (nodo.getNodeName().equals("behaviors")) {
                    ((FormElement) widget).getBehaviors().addAll(
                            ParserXml.parse(nodo, LinkedList.class));
                } else {
                    DynaBean db = new WrapDynaBean(widget);
                    DynaProperty dp = db.getDynaClass().getDynaProperty(nodo.
                            getNodeName());
                    if (dp != null) {
                        widget.setValorXml(nodo.getNodeName(),
                                ParserXml.parse(nodo, dp.getType()));
                    }
                }
            }
        }
        return column;
    }*/
}
