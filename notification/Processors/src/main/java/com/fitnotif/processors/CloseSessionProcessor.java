package com.fitnotif.processors;

import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TSesiones;
import com.fitnotif.tables.helper.SessionHelper;

/**
 * Clase que se encarga de cerrar la sesión actual.
 * @author malgia
 * @version 1.0
 */
public class CloseSessionProcessor implements Processor{
    
    /**
     * Proceso normal
     * Cierra la sesión actual.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        TSesiones activeSession=SessionHelper.getSession(request.getUser(), request.getSid());
        if(activeSession!=null){
            SessionHelper.expireSession(activeSession);
        }
        return request;
    }
}
