package com.fitnotif.web;

import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.webpages.NotifWebPage;

import lombok.Data;
import net.sf.json.JSONObject;
import net.sourceforge.wurfl.core.WURFLHolder;
/**
 * Contiene la informacion en la Sesion
 * @author santiago
 * @version 1.0
 */
@Data
public class SessionData {
    
    private String userName;
    
    private String name;
    
    private Boolean adminp;
    
    private String email;

    private String tokenId;   
    
    private Request transportData = new Notification(); 
    
    private WURFLHolder wurflHolder;
    
    private NotifWebPage webpage;
    
    private String process;
    
    private JSONObject responseObj;
    
    private Boolean maxDevices;
    
    public SessionData(){
        
    }
    
}
