package com.fitnotif.webpages.parser;

import java.util.Stack;

/**
 * Clase que genera un documento HTML
 * @author santiago
 * @version 1.0
 */
public class HTMLConstructor {
    
    private Tag head = new Tag("div");

    private Tag attributesTag = head;

    private Tag actualTag = head;
    
    private Stack<Tag> tags = new Stack<Tag>();
    
    public HTMLConstructor(){
        tags.push(head);
    }

    public Tag getHead() {
        return head;
    }

    public void setHead(Tag header) {
        this.head = header;
    }

    public Tag getActualTag() {
        return actualTag;
    }

    public void setActualTag(Tag tagActual) {
        this.actualTag = tagActual;
    }

    public Tag getAttributesTag() {
        return this.attributesTag;
    }

    public void setAttributesTag(Tag tagAtributos) {
        this.attributesTag = tagAtributos;
    }
    
    public Tag open(String tagName) {
        attributesTag = new Tag(tagName);
        actualTag.getChildren().add(attributesTag);
        actualTag = attributesTag;
        tags.push(attributesTag);

        return actualTag;
    }
    
    public void close(String tagName) {
        if (!getActualTag().getName().equals(tagName)) {
            throw new Error("Tag que cierra no coincide: " + tagName
                    + ", se esperaba: " + getActualTag().getName());
        }       

        tags.pop();
        actualTag = attributesTag = tags.peek();
    }
    
    public void setAttribute(String name, String value){
        attributesTag.getAttributes().put(name, value);
    }
    
    public void setText(String text){
        attributesTag.getChildren().add(new Text(text));
    }
}
