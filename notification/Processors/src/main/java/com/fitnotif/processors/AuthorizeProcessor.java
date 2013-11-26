package com.fitnotif.processors;

import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.tables.helper.AuthorizationHelper;
import com.fitnotif.tables.helper.TokenHelper;
import com.fitnotif.util.StatusTypes;
import com.fitnotif.util.TokenManager;



/**
 * Clase que devuelve la respuesta de la autorización.
 * @author malgia
 * @version 1.0
 */
public class AuthorizeProcessor implements Processor{
     
    /**
     * Proceso normal
     * Verifica la respuesta y la pone en el detail
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        String estatus;
        if(notification.getControlFieldValueByName("_TRNSTATUS")!=null){
            estatus = notification.getControlFieldValueByName("_TRNSTATUS").compareTo("2")==0?"A":"N";
        }else{
            estatus = notification.getControlFieldValueByName("ESTATUS");
        }
        String token = TokenHelper.getTokenByAuthorizationId(notification.getId()).getToken();
        if(!TokenManager.checkTokenTimeout(token) && notification.getControlFieldValueByName("FITBANK")==null){
            throw new NotificationException("AUT001", "EL TOKEN YA EXPIRO.");
        }
        if(estatus.compareTo("A")==0){
            notification.setStatus(StatusTypes.APPROVED.getType());
            AuthorizationHelper.updateStatus(notification.getId(), StatusTypes.APPROVED.getType());
        }else if(estatus.compareTo("N")==0){
            AuthorizationHelper.updateStatus(notification.getId(), StatusTypes.DENIED.getType());
        }
        TokenManager.expireToken(token);
        if(estatus.compareTo("P")!=0 && notification.getControlFieldValueByName("FITBANK")==null){
            AuthorizationHelper.saveXMLAuthorization(notification, 1);            
            notification.addControlField("DETAIL", AuthorizationHelper.getXMLNotification(notification.getId(), StatusTypes.DETAIL.getType()));
            notification.addControlField("TOKEN", token);    
        }
        return notification;
    } 
}
