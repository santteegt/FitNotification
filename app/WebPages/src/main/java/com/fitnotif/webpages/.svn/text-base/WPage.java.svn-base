package com.fitnotif.webpages;

import com.fitnotif.webpages.parser.HTMLConstructor;
import com.fitnotif.webpages.parser.SerializableHTML;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Representa una pagina de una notificacion
 * @author santiago
 * @version 1.0
 */
public class WPage extends WebElement<WRegister> {
    
    public WPage(){
        setTag("pag");        
        define("class", "barra");
        define("title","FitNotification-Mobile");
    }
    
    @Override
    public void generateHTML(HTMLConstructor html)throws Exception{
        html.open("div");
        html.setAttribute("data-role", "page");
        html.setAttribute("id", (String)properties.get("id"));
        html.setAttribute("title", (String)properties.get("title"));
        
        html.open("div");
        html.setAttribute("data-role", "header");
        html.setAttribute("class", (String)properties.get("class"));
        html.close("div");
        
        html.open("div");
        html.setAttribute("data-role", "content");
        html.open("table");
        html.setAttribute("class", (String)properties.get("class"));
        for(WRegister reg:this){
            reg.generateHTML(html);
        }
        html.close("table");
        html.close("div");
        
        html.open("div");
        html.setAttribute("data-role", "footer"); 
        html.setAttribute("class", "footer-bottom");
        html.close("div");
        
        html.close("div");
        
            
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
