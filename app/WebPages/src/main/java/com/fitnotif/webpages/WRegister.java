
package com.fitnotif.webpages;

import com.fitnotif.webpages.parser.HTMLConstructor;
import com.fitnotif.webpages.parser.SerializableHTML;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Representa un registro de una pagina de notificacion
 * @author santiago
 * @version 1.0
 */
public class WRegister extends WebElement<WColumn> {
    
    public WRegister(){
        setTag("reg");
    }
    
    @Override
    public void generateHTML(HTMLConstructor html)throws Exception{
        if(this.getId().equals("header") || this.getId().equals("rowT")){
            html.open("tr");
            for(WColumn col:this){
                col.generateHTML(html);
            }
            html.close("tr");
        }else{
            this.generateColumnRegister(html);            
        }
        
    }
    
    private void generateColumnRegister(HTMLConstructor html)throws Exception{
        int count = 0;
        for(WColumn col:this){
            if(count%2 ==0){
                html.open("tr");
            }
            col.generateHTML(html);
            if(count%2 !=0){
                html.close("tr");
            }
            count++;
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
