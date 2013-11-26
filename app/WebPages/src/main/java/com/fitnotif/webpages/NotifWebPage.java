package com.fitnotif.webpages;

import com.fitnotif.webpages.parser.HTMLConstructor;
import com.fitnotif.webpages.parser.SerializableHTML;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Representacion de una web de notificacion 
 * @author santiago
 * @version 1.0
 */
public class NotifWebPage extends WebElement<WPage> {
    
    public NotifWebPage(){
        setTag("not");
    }
        
    @Override
    public void generateHTML(HTMLConstructor html)throws Exception{        
        for (WPage page : this) {
            page.generateHTML(html);
        }        
    }
    
    @Override
    public Node getNode(Document document)throws Exception{
        Element element = null;
        return element;
        
    }
    
    @Override
    public Collection<SerializableHTML<?>> getChildren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
