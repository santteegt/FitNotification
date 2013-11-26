package com.fitnotif.parser;

import com.fitnotif.common.NotificationException;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serializer.ToXMLStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase que permite la lectura y creacion de archivos XML
 * @author malgia
 * @version 1.0
 */
public final class XMLHelper{
    
    private XMLHelper(){}
    
    /**
     * Método que permite agregar atributos a un Nodo. 
     * @param pNode
     * @param pName
     * @param pValue
     */
    public static void addAttribute(Node pNode, String pName, Object pValue){
        ((Element) pNode).setAttribute(pName, pValue.toString());
    }

    /**
     * Método que permite agregar Nodos a un Nodo. 
     * @param pParent Nodo Padre
     * @param pChild  Nodo Hijo
     */
    public static void appendChild(Node pParent, Node pChild){
        pParent.appendChild(pParent.getOwnerDocument().importNode(pChild, true));
    }
    
    /**
     * Método que permite agregar Nodos a un Nodo indicando el valor del mismo
     * @param pParent
     * @param pChildName
     * @param pValue 
     */
    public static void appendChild(Node pParent, String pChildName, Object pValue){
        Node child = XMLHelper.createNode(pChildName, pValue);
        pParent.appendChild(pParent.getOwnerDocument().importNode(child, true));
    }    
    
    /**
     * Método que crea un Nodo en base a su nombre
     * @param pName
     * @return 
     */
    public static Node createNode(String pName){
        Document d = new DocumentImpl();
        return createNode(d, pName);
    }    

    /**
     * Método que crea un nodo dentro de un documento
     * @param pDocument
     * @param pName
     * @return 
     */
    public static Node createNode(Document pDocument, String pName){
        return pDocument.createElement(pName);
    }    
    
    /**
     * Método que crea un nodo y le asigna un valor
     * @param pName
     * @param pValue
     * @return 
     */
    public static Node createNode(String pName, Object pValue){
        Document d = new DocumentImpl();
        return createNode(d, pName, pValue);
    }    
    
    /**
     * Método que crea un nodo y le asigna un valor dentro de un documento
     * @param pDocument
     * @param pName
     * @param pValue
     * @return 
     */
    public static Node createNode(Document pDocument, String pName, Object pValue){
        Node value=null;
        Document d = pDocument;
        Node node = XMLHelper.createNode(pName);
        if(pValue!=null){
            value = d.createTextNode(pValue.toString());
        }
        if (value != null) {
            XMLHelper.appendChild(node, value);
        }
        return node;
    }
    
    /**
     * Devuelve un Nodo de acuerdo a su nombre y a su Nodo padre.
     * @param pNode
     * @param pTag
     * @return 
     */
    public static Node getChildByName(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0);
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    /**
     * Devuelve un Nodo de acuerdo a su nombre y al documento al que pertenece.
     * @param document
     * @param pTag
     * @return 
     */
    public static Node getChildByName(Document document, String pTag) {
        return getChildByName(document.getDocumentElement(), pTag);
    }
    
    /**
     * Devuelve el valor del atributo de un Nodo
     * @param pNode
     * @param pAttribute
     * @return 
     */
    public static Boolean getBooleanValueByAttribute(Node pNode, String pAttribute){
        try {
            Element e = (Element) pNode;
            return e.getAttribute(pAttribute).compareTo("1")==0?true:false;
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */      
    
    public static Boolean getBooleanValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue().compareTo("1")==0?true:false;
        } catch (NullPointerException e) {
            return null;
        }
    }

    
    
    /**
     * Devuelve el valor del atributo de un Nodo
     * @param pNode
     * @param pAttribute
     * @return 
     */
    public static String getStringValueByAttribute(Node pNode, String pAttribute){
        try {
            Element e = (Element) pNode;
            return e.getAttribute(pAttribute);
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */      
    
    public static String getStringValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Devuelve el valor del atributo de un Nodo
     * @param pNode
     * @param pAttribute
     * @return 
     */    
    public static Timestamp getTimestampValueByAttribute(Node pNode, String pAttribute){
        try {
            Element e = (Element) pNode;
            return Timestamp.valueOf(e.getAttribute(pAttribute));
        } catch (NullPointerException e) {
            return null;
        }
    } 
         
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public static Timestamp getTimestampValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return Timestamp.valueOf(e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue());
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    /**
     * Devuelve el valor del atributo de un Nodo
     * @param pNode
     * @param pAttribute
     * @return 
     */    
    public static Date getDateValueByAttribute(Node pNode, String pAttribute){
        try {
            Element e = (Element) pNode;
            return Date.valueOf(e.getAttribute(pAttribute));
        } catch (NullPointerException e) {
            return null;
        }
    } 
         
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public static Date getDateValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return Date.valueOf(e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue());
        } catch (NullPointerException e) {
            return null;
        }
    }    
    
    /**
     * Devuelve el valor del atributo de un Nodo
     * @param pNode
     * @param pAttribute
     * @return 
     */    
    public static BigDecimal getBigDecimalValueByAttribute(Node pNode, String pAttribute){
        try {
            Element e = (Element) pNode;
            return new BigDecimal(e.getAttribute(pAttribute));
        } catch (NullPointerException e) {
            return null;
        }
    }     
    
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public static BigDecimal getBigDecimalValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return new BigDecimal(e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue());
        } catch (NullPointerException e) {
            return null;
        }
    }  
    
    /**
     * Devuelve el valor del atributo de un Nodo
     * @param pNode
     * @param pAttribute
     * @return 
     */    
    public static Integer getIntegerValueByAttribute(Node pNode, String pAttribute){
        try {
            return Integer.valueOf(getStringValueByAttribute(pNode, pAttribute));
        } catch (NumberFormatException e) {
            return null;
        }
    }     
    
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public static Integer getIntegerValueByTag(Node pNode, String pTag) {
        try {
            return Integer.valueOf(getStringValueByTag(pNode, pTag));
        } catch (NumberFormatException e) {
            return null;
        }
    }  
    
    /**
     * Devuelve los Nodos hijos de acuerdo al Nodo padre y a un nombre
     * @param pNode
     * @param pTag
     * @return 
     */
    public static NodeList getChildrenByName(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag);
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    /**
     * Crea un String en donde se encuentra el Nodo en formato XML
     * @param pNode
     * @return 
     */
    public static String nodeToString(Node pNode){
        String xml;
        try {
            ToXMLStream XMLStream = (ToXMLStream) (new ToXMLStream()).asDOMSerializer();
            StringWriter sw = new StringWriter();
            XMLStream.setWriter(sw);
            XMLStream.serialize(pNode);
            xml=sw.toString();
        } catch (IOException ex) {
            throw new NotificationException("ERRXML", "ERROR AL CREAR EL ARCHIVO XML", ex);
        }
        return xml.substring(xml.indexOf('>')+1);
    }
}
