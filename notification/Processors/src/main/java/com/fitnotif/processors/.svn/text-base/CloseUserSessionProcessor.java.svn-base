package com.fitnotif.processors;

import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TSesiones;
import com.fitnotif.tables.helper.SessionHelper;
import com.fitnotif.tables.helper.UserHelper;
import java.util.Iterator;

/**
 * Clase que se encarga de cerrar la sesión de un usuario
 * @author malgia
 * @version 1.0
 */
public class CloseUserSessionProcessor implements Processor{
    
    /**
     * Proceso normal
     * Cierra la sesión de un usuario en particular.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        String user=notification.getControlFieldValueByName("USER_SESSION");
        if(user!=null){
            Iterator<TSesiones> activeSessions=UserHelper.getActiveSessions(user).iterator();
            while(activeSessions.hasNext()){
                SessionHelper.expireSession(activeSessions.next());
            }
        }
        return notification;
    }
}
