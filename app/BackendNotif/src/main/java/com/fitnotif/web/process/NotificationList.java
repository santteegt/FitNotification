package com.fitnotif.web.process;

import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.emuntypes.AuthorizationFields;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.exception.WebError;
import com.fitnotif.web.manager.NotificationLinker;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Envia la lista de notificaciones pendiente que posee el usuario
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.NOTIF)
public class NotificationList implements Controller {

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        Notification req = (Notification)WebEnviroment.getTransportData();
        req.deleteAllPages();
        req.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        request.setRequestData(req);
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);     
        
        Page authotization = ((Notification)response.getTransportData()).getPageById(ConfPagesTypes.AUTHORIZATIONS.getPage());
        if(authotization == null){
            throw new WebError("ERROR AL OBTENER LAS AUTORIZACIONES DE USUARIO");
        }
        JSONObject mjson = new JSONObject();
        JSONArray jsonArray;
        String value;        
        for(Register reg:authotization.getRegisters()){
            String type = reg.getColumnByName(AuthorizationFields.TYPE.getField()).getValue();
            String type_name = reg.getColumnByName(AuthorizationFields.TYPE_NAME.getField()).getValue();
            String key = "type-"+type+"-"+type_name;
            
                JSONObject json = new JSONObject();                
                //Nombre Autorizacion
                value = reg.getColumnByName(AuthorizationFields.NAME.getField()).getValue();
                json.put(AuthorizationFields.NAME.getField(), value);
                //Usuario que envia autorizacion
                value = reg.getColumnByName(AuthorizationFields.USER.getField()).getValue();
                json.put(AuthorizationFields.USER.getField(), value);                
                //Fecha de autorizacion
                value = reg.getColumnByName(AuthorizationFields.DATE.getField()).getValue();
                json.put(AuthorizationFields.DATE.getField(), value);
                //Status de la autorizacion
                value = reg.getColumnByName(AuthorizationFields.STATUS.getField()).getValue();
                json.put(AuthorizationFields.STATUS.getField(), value);
                //NumeroMensaje
                value = reg.getColumnByName(AuthorizationFields.ID.getField()).getValue();
                json.put(AuthorizationFields.ID.getField(), value);                
                //Oficial
                value = reg.getColumnByName(AuthorizationFields.OFFICER.getField()).getValue();
                json.put(AuthorizationFields.OFFICER.getField(), value);
                //Oficial
                value = reg.getColumnByName(AuthorizationFields.MESSAGE.getField()).getValue();
                json.put(AuthorizationFields.MESSAGE.getField(), value);
                
            if(!mjson.containsKey(key)){
                jsonArray = new JSONArray();
                jsonArray.add(json);
                mjson.put(key, jsonArray);                
            }else{
                jsonArray = mjson.getJSONArray(key);
                jsonArray.add(json);
            }
        }   
        
        response.setContenido(mjson.toString(), "application/json");
        return response;
    }
    
    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);
    }  
    
    
}
