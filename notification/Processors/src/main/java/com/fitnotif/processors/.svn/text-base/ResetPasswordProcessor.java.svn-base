package com.fitnotif.processors;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TUsuarioPassword;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.LoginHelper;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.tables.helper.SessionHelper;

/**
 * Clase que resetea la contraseña
 * @version 1.0
 */
public class ResetPasswordProcessor implements Processor{
    
    
    /**
     * Proceso normal
     * Caduca la contraseña anterior y crea una nueva con un valor por defecto
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification = (Notification) request;
        String username=null;
        if(notification.getControlFieldValueByName("USER_PASS")!=null){
            username=notification.getControlFieldValueByName("USER_PASS");
        }else{
            username=request.getUser();
        }
        TUsuarioPassword userPassword=LoginHelper.getUserPassword(username);
        if(userPassword!=null){
            TUsuarioPassword nUserPassword=userPassword;
            LoginHelper.expirePassword(userPassword);
            String newPassword=ParameterHelper.getStringValue("password");
            nUserPassword.setPassword(newPassword);
            nUserPassword.setFdesde(DatesHelper.getCurrentTimeStamp());        
            HibernateUtil.save(nUserPassword);
            SessionHelper.expireUserActiveSessions(username);
        }else{
            throw new NotificationException("RPW001", "EL USUARIO {0} NO TIENE CONTRASEÑA", username);
        }
        return notification;
    }
}
