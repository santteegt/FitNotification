package com.fitnotif.processors;

import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.UserTypes;
import com.fitnotif.persistence.tablas.TAutorizaciones;
import com.fitnotif.tables.helper.AuthorizationHelper;
import com.fitnotif.tables.helper.UserHelper;
import com.fitnotif.util.StatusTypes;

/**
 * Clase que elimina un autorización
 * @author malgia
 * @version 1.0
 */
public class DeleteNotificationProcessor implements Processor{

    /**
     * Proceso normal
     * Actualiza el estado de una notificacion a Eliminada.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        if(UserHelper.getUser(notification.getUser()).getFkcrol()==UserTypes.ADMIN.getRole()){
            String auth = notification.getControlFieldValueByName("DELETE_TOKEN");
            TAutorizaciones authorization = AuthorizationHelper.getAuthorization(auth);
            if(authorization!=null){
                AuthorizationHelper.updateStatus(auth, StatusTypes.DELETED.getType());
            }else{
                throw new NotificationException("AUT001", "LA AUTORIZACION NO EXISTE.");
            }
        }else{
            throw new NotificationException("AUT002", "TIENE QUE SER ADIMINSTRADOR PARA ELIMINAR AUTORIZACIONES.");
        }
        return notification;
    }
}
