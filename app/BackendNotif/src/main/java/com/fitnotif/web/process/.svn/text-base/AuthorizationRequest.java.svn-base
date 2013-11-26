package com.fitnotif.web.process;

import com.fitnotif.notification.Notification;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.NotificationLinker;
import net.sf.json.JSONObject;

/**
 * Guarda el id y realiza el manejo de estado de la autorizacion a trabajar
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.AUTHR)
public class AuthorizationRequest implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        String status = request.getRequestValue("status");
        String id = request.getRequestValue("id");
        String keyword = request.getRequestValue("keyword");

        Notification requestData = (Notification) WebEnviroment.getTransportData();
        String operation = this.getClass().getAnnotation(Handler.class).value().toUpperCase();
        operation = operation.substring(0, operation.length()-1);
        WebResponse response;
        if(status.compareTo("0") == 0){
            requestData.setId(id);                        
            requestData.setOperation(operation + "D" + status);
            requestData.addControlField("KEYWORD", keyword);
            request.setRequestData(requestData);            
            response = new NotificationLinker().process(request);
            ErrorHandler.checkOkCodes(response, inEnviroment);            
        }else{
            response = new WebResponse(requestData, request);
            WebEnviroment.setAuthToken(request.getRequestValue("id"));
        }
        
        JSONObject json = new JSONObject();        
        json.element("status", status);        
        response.setContenido(json.toString(), "application/json");
        /*if(!page.isEmpty()){
            request.redirect(page);
        }*/
        
        return response;        
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);        
    }
    
}