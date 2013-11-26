package com.fitnotif.webpages;

import com.fitnotif.webpages.parser.HTMLConstructor;
import com.fitnotif.webpages.parser.SerializableHTML;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Representa un campo en un registro perteneciente a una pagina de una notificacion
 * @author santiago
 * @version 1.0
 */
public class WColumn extends WebElement<WColumn> {
    
    private static final long serialVersionUID = 2L;
    
    public WColumn(){
        setTag("col");
        
    }
    
    @Override
    public void generateHTML(HTMLConstructor html)throws Exception{        
        if(this.getId().equals("labelt")){
            html.open("th");
            html.open("span");
            html.setText(this.getText());
            html.close("span");
            html.close("th");
        }else{            
            html.open("td");
            html.open("span");
            html.setAttribute("id", (String)properties.get("id"));
            html.setText(this.getText());
            html.close("span");
            html.close("td");
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
    
    public void setText(String pText){
        properties.put("text", pText);
    }
    
    public String getText(){
        return (String) properties.get("text");
        
    }
    
}
