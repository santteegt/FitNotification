package com.fitnotif.web.data;

import com.fitnotif.webpages.NotifWebPage;
import com.fitnotif.webpages.parser.HTMLSerializer;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Objeto que contiene la data de la web generada para la notificacion
 * @author santiago
 * @version 1.0
 */
public class WebPageTransport {
    
    private final WebResponse response;
    
    private final NotifWebPage webpage;
    
    public WebPageTransport(WebResponse response, NotifWebPage webpage){
        this.response = response;
        this.webpage = webpage;
    }
    
    /**
     * Envia el contenido como un String json
     * @return String JSON
     */
    public String toJSON() {
        JSONObject res = new JSONObject();
        JSONArray arrayNames = new JSONArray();
        JSONArray arrayPages = new JSONArray();
        Map<String, String> pages = new HTMLSerializer().serialize(webpage);
        for(String pageId:pages.keySet()){
            arrayNames.add(pageId);
            arrayPages.add(pages.get(pageId));            
        }
        res.element("ids", arrayNames);
        res.element("pages", arrayPages);
        return res.toString();
    }
    
}
