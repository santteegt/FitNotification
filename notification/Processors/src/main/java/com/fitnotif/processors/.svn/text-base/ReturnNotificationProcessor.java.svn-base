package com.fitnotif.processors;

import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.OperationTypes;
import com.fitnotif.parser.NotificationParser;
import com.fitnotif.tables.helper.AuthorizationHelper;
import com.fitnotif.util.StatusTypes;
import java.io.IOException;

/**
 * Clase que devuelve una autorizacion de acuerdo a un Id.
 * @author malgia
 * @version 1.0
 */
public class ReturnNotificationProcessor implements Processor{

    /**
     * Proceso normal
     * Devuelve la autorizacion de acuerdo a su ID.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=null;
        NotificationParser np=null;
        String id = request.getId();
        String not = AuthorizationHelper.getXMLNotification(id, StatusTypes.NEW.getType());
        if(not.compareTo("")==0){
            throw new NotificationException("NOT001", "NO EXISTEN NOTIFICACIONES INGRESADAS CON NUMERO MENSAJE: {0}", id);
        }else{
            try {
                np = new NotificationParser(not);
                notification = new Notification(np);
                notification.setOperation(OperationTypes.GETNOT.getType());
                notification.setSid(request.getSid());
            } catch (IOException ex) {
                throw new NotificationException(ex);
            }
        }
        return notification;
    }
}

