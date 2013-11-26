
package com.fitnotif.webpages.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Tag que representa un texto html
 * @author santiago
 */
public class Text extends Tag{
    
    public Text(String pvalue){
        super(null);
        setValue(pvalue);
    }
    
    @Override
    public Node getNode(Document document) {
        return document.createTextNode(getValue());
    }
}
