
package com.fitnotif.webpages.parser;

import com.fitnotif.common.NotificationException;
import com.fitnotif.parser.XMLParser;
import com.fitnotif.webpages.WColumn;
import com.fitnotif.webpages.NotifWebPage;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.fitnotif.webpages.WPage;
import com.fitnotif.webpages.WRegister;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Objeto encargado de realizar el parsing de las definiciones de webpages
 * @author santiago
 * @version 1.0
 */
public class Parser extends XMLParser{
    
    /**
     * Inicio de parsing a html
     * @param doc
     * @return
     * @throws ExcepcionParser 
     */
    /*public NotifWebPage parse() throws Exception {               
        if (this.document.getDocumentElement() == null || !this.document.getDocumentElement().getNodeName().equals("notifWp")) {
            throw new NotificationException("PARSE001","DEFINICION DE INFORMACION NO DISPONIBLE");
        }       

        return parseWebPage(this.document.getDocumentElement());
    }*/
    
    /**
     * Conversion de definicion a un Objeto que representa un WebPage
     * @param node
     * @return
     * @throws Exception 
     */
    /*private NotifWebPage parseWebPage(Element node)throws Exception {
        
        NotifWebPage webPage = new NotifWebPage();

        NodeList children = node.getChildNodes();

        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName().equalsIgnoreCase("properties")) {
                        parseNotifPageHeader(node, webPage);
                    }
                    if (node.getNodeName().equalsIgnoreCase("pages")) {
                        parseNotifPage(node, webPage);
                    }
                }
            }
        }

        return webPage;
    }*/    
        
    /**
     * Manejo de las propiedades definidas en el xml de definicion
     * @param node
     * @param webPage
     * @return
     * @throws Exception 
     */
    /*private NotifWebPage parseNotifPageHeader(Node node, NotifWebPage webPage)throws Exception {
        NamedNodeMap attrs = node.getAttributes();
        NodeList nodos = node.getChildNodes();

        for (int i = 0; i < attrs.getLength(); i++) {
            Node nodo = attrs.item(i);
            if (nodo.getNodeType() != Node.TEXT_NODE) {
                webPage.setXMLValue(nodo.getNodeName(), nodo.getNodeValue());
            }
        }

        //pendiente
        for (int i = 0; i < nodos.getLength(); i++) {
            Node item = nodos.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE && item.getFirstChild() != null) {                
                    webPage.setXMLValue(item.getNodeName(), item.getFirstChild().
                            getNodeValue());
            }
        }

        return webPage;
    }*/
    
    /**
     * Maneja la definicion de las paginas de la notificacion
     * @param node
     * @param webPage
     * @return
     * @throws Exception 
     */
    /*private NotifWebPage parseNotifPage(Node node, NotifWebPage webPage)throws Exception {
        NodeList children = node.getChildNodes();

        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    this.parsePage(children.item(i), webPage);
                }
            }
        }

        return webPage;
    }*/
    
    /**
     * Genera una pagina de una notificacion
     * @param node
     * @param webPage
     * @return
     * @throws Exception 
     */
    /*private WPage parsePage(Node node, NotifWebPage webPage) throws Exception{            
        WPage page = new WPage();
        webPage.add(page);

        NamedNodeMap attrs = node.getAttributes();
        NodeList children = node.getChildNodes();

        for (int i = 0; i < attrs.getLength(); i++) {
            Node nodo = attrs.item(i);
            if (nodo.getNodeType() != Node.TEXT_NODE) {
                page.setXMLValue(nodo.getNodeName(), nodo.getNodeValue());
            }
        }

        Collection<WRegister> items = new ArrayList<WRegister>(children.getLength());
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                items.add(this.parseRegister(children.item(i), page));
            }
        }

        page.addAll(items);

        return page;
    }*/
    
    /**
     * Genera un registro de una pagina
     * @param node
     * @param page
     * @return
     * @throws Exception 
     */
    /*private WRegister parseRegister(Node node, WPage page) throws Exception{            
        WRegister register = new WRegister();
        page.add(register);

        NamedNodeMap attrs = node.getAttributes();
        NodeList children = node.getChildNodes();

        for (int i = 0; i < attrs.getLength(); i++) {
            Node nodo = attrs.item(i);
            if (nodo.getNodeType() != Node.TEXT_NODE) {
                register.setXMLValue(nodo.getNodeName(), nodo.getNodeValue());
            }
        }

        Collection<WColumn> items = new ArrayList<WColumn>(children.getLength());
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                items.add(this.parseColumn(children.item(i)));
            }
        }

        register.addAll(items);

        return register;
    }*/
    
    /**
     * Genera un objeto una columna de un registro
     * @param node
     * @return
     * @throws Exception 
     */
    /*private WColumn parseColumn(Node node) throws Exception {
        //String tipo = getClassName(node.getAttributes());
        String tipoAux = "";        

        Class clase;
        //try {
            clase = null;
            clase = Class.forName(tipo, true,
                    Thread.currentThread().getContextClassLoader());
        //} catch (ClassNotFoundException e) {
            //throw new NotificationException("PARSE002", "FALLA AL OBTENER CAMPO");
        //}

        //Widget widget = parseWidget(node, clase);
        WColumn column = this.parseColumnByClass(node, clase);
        
        

        return column;
    }*/
    
    
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