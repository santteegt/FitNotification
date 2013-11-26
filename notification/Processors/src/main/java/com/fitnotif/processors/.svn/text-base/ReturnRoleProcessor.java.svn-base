package com.fitnotif.processors;

import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TUsuarios;
import com.fitnotif.tables.helper.UserHelper;

/**
 * Clase que devuelve el rol de un usuario
 * @author malgia
 * @version 1.0
 */
public class ReturnRoleProcessor implements Processor{
     
    /**
     * Proceso normal
     * Retorna el rol del usuario.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        TUsuarios user = UserHelper.getUser(notification.getUser());
        notification.addControlField("ROLEOK", user.getFkcrol().toString());        
        return notification;        
    }
}
