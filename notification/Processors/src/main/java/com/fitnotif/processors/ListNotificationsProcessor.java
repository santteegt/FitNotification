package com.fitnotif.processors;

import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.AuthorizationFields;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.notification.emuntypes.UserTypes;
import com.fitnotif.persistence.tablas.TAutorizaciones;
import com.fitnotif.tables.helper.AuthorizationHelper;
import com.fitnotif.tables.helper.UserHelper;
import com.fitnotif.util.TokenManager;
import java.util.Iterator;

/**
 * Clase que devuelve las autorizaciones registrados en la base de datos para un usuario.
 * @author malgia
 * @version 1.0
 */
public class ListNotificationsProcessor implements Processor{

    /**
     * Proceso normal
     * Devuelve las autorizaciones de un usuario.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        notification=ListNotificationsProcessor.fillAuthorizationsPage(notification);
        return notification;
    }
      
    /**
     * Método que pone en la notificación las autorizaciones pendientes del usuarios. 
     * @param notification
     * @return 
     */
    public static Notification fillAuthorizationsPage(Notification notification){
        Page authorizationsPage=new Page(ConfPagesTypes.AUTHORIZATIONS.getPage());
        Iterator authorizations=null;
        if(UserHelper.getUser(notification.getUser()).getFkcrol()==UserTypes.ADMIN.getRole()){
            authorizations=AuthorizationHelper.getErrorAuthorizations();
        }else{
            authorizations=AuthorizationHelper.getAuthorizations(notification.getUser());
        }
        Integer i=1;
        while(authorizations.hasNext()){
            TAutorizaciones authorization=(TAutorizaciones)authorizations.next();
            Register reg=new Register(i.toString());
            reg.addColumn(new Column(AuthorizationFields.ID.getField(), authorization.getNumeromensaje()));
            String auth_type=authorization.getFkctipoautorizacion();
            reg.addColumn(new Column(AuthorizationFields.TYPE.getField(), auth_type));
            reg.addColumn(new Column(AuthorizationFields.TYPE_NAME.getField(), AuthorizationHelper.getAuthorizationType(auth_type).getDescripcion()));
            reg.addColumn(new Column(AuthorizationFields.NAME.getField(), authorization.getNombre()));
            String fecha=authorization.getFdesde().toString();
            reg.addColumn(new Column(AuthorizationFields.DATE.getField(), fecha.substring(0, fecha.indexOf('.'))));
            reg.addColumn(new Column(AuthorizationFields.USER.getField(), authorization.getCusuarioOrigen()));
            Boolean tokenStatus=TokenManager.checkTokenTimeout(authorization.getFktoken());
            reg.addColumn(new Column(AuthorizationFields.STATUS.getField(), tokenStatus?"1":"0"));
            reg.addColumn(new Column(AuthorizationFields.OFFICER.getField(), authorization.getFkcusuario()!=null?authorization.getFkcusuario():""));
            reg.addColumn(new Column(AuthorizationFields.MESSAGE.getField(), authorization.getMensaje()!=null?authorization.getMensaje():""));
            authorizationsPage.addRegister(reg);
            i++;
        }
        notification.addPage(authorizationsPage);
        return notification;
    }
}

