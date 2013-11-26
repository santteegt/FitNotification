
package com.fitnotif.webpages.parser;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Representa un tag HTML con atributos e hijos
 * @author santiago
 * @version 1.0
 */
public class Tag {
    
    //private static final long serialVersionUID = 1L;

    private String name;

    private Map<String, String> attributes = new LinkedHashMap<String, String>();

    private List<Tag> children = new LinkedList<Tag>();

    private String value = null;

    public Tag(String nam) {
        this.name = nam;
    }

    /**
     * Establece un nodo para html con atributos y valor
     * @param document
     * @return 
     */    
    public Node getNode(Document document) {
        Element element = document.createElement(this.getName());

        for (String nam : this.getAttributes().keySet()) {
            element.setAttribute(nam, this.getAttributes().get(nam));
        }
        if (this.getValue() != null) {
            element.setNodeValue(this.getValue());
        }
        return element;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setName(String name) {     
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
    }

    public List<Tag> getChildren() {
        return this.children;
    }
    
}
