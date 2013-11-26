package com.fitnotif.parser;

import com.fitnotif.common.NotificationException;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xpath.internal.XPathAPI;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase que permite parsear archivos XML
 * @author malgia
 */
public class XMLParser {
    
    protected DOMParser parser=new DOMParser();
    protected Document document;
    
    private static final String PRS002N="PRS002";
    private static final String PRS002S="NO SE PUDO ENCONTRAR EL TAG {0} DENTRO DE {1}";
    
    public XMLParser(){
    
    }
    
    /**
     * Constructor de la clase en base a un archivo XML
     * @param xml
     * @throws IOException 
     */
    public XMLParser(String xml) throws IOException{
        try {
            this.parser.parse(new InputSource(new StringReader(xml)));
            this.document = this.parser.getDocument();
        } catch (SAXException ex) {
            throw new NotificationException("PRS001", "NO SE PUDO PARSEAR EL DOCUMENTO", ex);
        } 
    }    
    
    /**
     * Constructor de la clase en base a un archivo XM. Devuelve un Document
     * @param xml
     * @throws IOException 
     */
    public XMLParser(InputStream xml) throws IOException{
        
        try {
            this.parser.parse(new InputSource(xml));
            this.document=this.parser.getDocument();
        } catch (SAXException ex) {
            throw new NotificationException("PRS001", "NO SE PUDO PARSEAR EL DOCUMENTO", ex);
        } 
    }     
    
    /**
     * Método que pone en un String la información de un Stream
     * @param pIn
     * @return
     * @throws IOException 
     */
    public static String readStream(InputStream pIn) throws IOException {
        byte b[] = new byte[9999];
        int car = 0;
        String data = "";
        do {
            car = pIn.read(b);
            if (car > 0) {
                data += new String(b, 0, car, "ISO-8859-1");
            }
        } while (car > 0);
        return data;
    }
    
    /**
     * Devuelve un Nodo de acuerdo a su nombre y a su Nodo padre.
     * @param pNode
     * @param pTag
     * @return 
     */
    public Node getChildByName(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0);
        } catch (NullPointerException ex) {
            throw new NotificationException(PRS002N, PRS002S, ex, pTag, pNode.getNodeName());
        }
    }
    
    /**
     * Devuelve un Nodo de acuerdo a su nombre (solo Nodos padres)
     * @param pTag
     * @return 
*/
    public Node getChildByName(String pTag) {
        return this.getChildByName(this.document.getDocumentElement(), pTag);
    } 
    
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */
    
    public String getStringValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue();
        } catch (NullPointerException ex) {
            throw new NotificationException(PRS002N, PRS002S, ex, pTag, pNode.getNodeName());
        }
    }

    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public Timestamp getTimestampValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return Timestamp.valueOf(e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue());
        } catch (NullPointerException ex) {
            throw new NotificationException(PRS002N, PRS002S, ex, pTag, pNode.getNodeName());
        }
    }
   
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public BigDecimal getBigDecimalValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return new BigDecimal(e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue());
        } catch (NullPointerException ex) {
            throw new NotificationException(PRS002N, PRS002S, ex, pTag, pNode.getNodeName());
        }
    }  
    
    /**
     * Devuelve el valor de un Nodo de acuerdo a su nombre y a su Nodo padre
     * @param pNode
     * @param pTag
     * @return 
     */    
    public Integer getIntegerValueByTag(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return Integer.parseInt(e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue());
        } catch (NullPointerException ex) {
            throw new NotificationException(PRS002N, PRS002S, ex, pTag, pNode.getNodeName());
        }
    }  
    
    /**
     * Devuelve los Nodos hijos de acuerdo al Nodo padre y a un nombre
     * @param pNode
     * @param pTag Usar (*) para sacar todos los Nodos
     * @return 
     */
    public NodeList getChildrenByName(Node pNode, String pTag) {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag);
        } catch (NullPointerException ex) {
            throw new NotificationException(PRS002N, PRS002S, ex, pTag, pNode.getNodeName());
        }
    }
    
    /**
     * Busca un nodo en el documento dado el xpath.
     * 
     * @param pXpath
     * @return node
     * @throws Exception
     */
    public Node findNode(String pXpath) throws Exception {
        NodeIterator nit = this.findNodeIterator(pXpath);
        return nit.nextNode();
    }

    /**
     * Busca un nodo dentro del documento dado una ruta.
     * 
     * @param pXpath
     * @return
     * @throws Exception
     */
    public NodeIterator findNodeIterator(String pXpath) throws Exception {
        return XPathAPI.selectNodeIterator(this.document, pXpath);
    }   
}
