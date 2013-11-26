package com.fitnotif.web.manager;

import com.fitnotif.client.NotifClient;
import com.fitnotif.notification.Notification;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;

/**
 * Establece una conexion con el servidor de Notificaciones
 * @author santiago
 * @version 1.0
 */
public class NotificationLinker {
    
    private Boolean isDebug;
    
    public NotificationLinker(Boolean isDebug){
        this.isDebug = isDebug;
    }
    
    public NotificationLinker(){
        this.isDebug = false;
    }
    
    public WebResponse process(WebRequest request) throws Exception{        
        Notification response = (Notification) request.getRequestData();
        WebResponse webResponse = null;
        if(!isDebug){
            webResponse = new WebResponse(NotifClient.send(response), request);
        }else{
            webResponse = new WebResponse(NotifClient.tstCommunication(), request);
        }        
        
        return webResponse;
    }
    
}
