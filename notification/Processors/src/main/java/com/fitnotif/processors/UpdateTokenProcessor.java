package com.fitnotif.processors;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.common.TokenCreator;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TAutorizaciones;
import com.fitnotif.persistence.tablas.TUsuarioInformacionAdicional;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.AuthorizationHelper;
import com.fitnotif.tables.helper.UserHelper;
import com.fitnotif.util.TokenManager;

/**
 * Clase que actualiza el token cuando el usuario lo solicita.
 * @author malgia
 * @version 1.0
 */
public class UpdateTokenProcessor implements Processor{
    
    /**
     * Proceso normal
     * Caduca la autorizacion anterior y crea una nueva (con un nuevo token)
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification = (Notification) request;
        TUsuarioInformacionAdicional userInfo = UserHelper.getUserInfo(notification.getUser());
        String savedKeyword = null;
        if(userInfo!=null){
            savedKeyword = userInfo.getKeyword();
        }else{
            throw new NotificationException("KWD001", "EL USUARIO {0} NO TIENE INFORMACION ADICIONAL.", notification.getUser());
        }
        String keyword = notification.getControlFieldValueByName("KEYWORD");
        if(savedKeyword==null){
            throw new NotificationException("KWD002", "EL USUARIO {0} NO TIENE PALABRA CLAVE PARA SOLICITAR TOKENS.", notification.getUser());
        }
        if(keyword==null || keyword.compareTo("")==0){
            throw new NotificationException("KWD003", "DEBE INGRESAR UNA PALABRA CLAVE PARA SOLICITAR TOKENS.");
        }
        if(keyword.compareTo(savedKeyword)==0){
            String numeromensaje=request.getId();
            String token=TokenCreator.createToken().replace(":", "t");
            TAutorizaciones tautorizaciones=AuthorizationHelper.getAuthorization(numeromensaje);        
            AuthorizationHelper.expire(tautorizaciones);
            TokenManager.saveToken(token);
            tautorizaciones.setFktoken(token);
            tautorizaciones.setFdesde(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(tautorizaciones);
        }else{
            throw new NotificationException("KWD004", "LA PALABRE CLAVE INGRESADA ES INCORRECTA.");
        }
        return request;
    }
}
