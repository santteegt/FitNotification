package com.fitnotif.webpages.parser;

import com.fitnotif.common.NotificationException;
import com.fitnotif.parser.XMLHelper;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Conversion a string html
 * @author santiago
 * @version 1.0
 */
public class HTMLSerializer {
    
    
    private Map<String, String> pages = new HashMap<String, String>(); 
    /**
     * Proceso normal
     * @param s WebPage
     * @return 
     */
    public Map<String, String> serialize(SerializableHTML s){
        

        try {            
            HTMLConstructor html = new HTMLConstructor();
            s.generateHTML(html);
            this.finishProcess(html.getHead());
            return pages;
        } catch (Exception e) {
            throw new NotificationException("SER001","FALLO AL OBTENER ENTORNO");
        }
    }
    
    /**
     * Realiza la serializacion del documento
     * @param s
     * @param os
     * @throws Exception 
     */
    private void finishProcess(Tag s)throws Exception{        
        Document d = new DocumentImpl();
        Node root = this.serialize(s, d);
        d.appendChild(root);        
                
        NodeList pagesl = d.getChildNodes().item(0).getChildNodes();
        for(int i=0;i<pagesl.getLength();i++){
            this.serializePage(pagesl.item(i));
        }
    }
    
    private Node serialize(Tag s, Document d)throws Exception {
        Node node = s.getNode(d);

        if (s.getChildren() != null) {
            for (Tag sx : s.getChildren()) {
                node.appendChild(serialize(sx, d));
            }
        }

        return node;
    }
    
    private void serializePage(Node page)throws Exception{        
        String pageId = page.getAttributes().getNamedItem("id").getNodeValue();
        this.pages.put(pageId, XMLHelper.nodeToString(page));
    }
}