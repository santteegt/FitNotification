package com.fitnotif.web.process;

import com.fitnotif.notification.Notification;
import com.fitnotif.web.Controller;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.util.Handler;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.NotificationLinker;
import net.sf.json.JSONObject;
/**
 * Proceso que obtiene un parametro del sistema
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.GSYSP)
public class GetSystemParameter implements Controller{
    
    public Boolean inEnviroment = Boolean.FALSE;    

    @Override
    public WebResponse process(WebRequest request) throws Exception {        
        Notification notification = (Notification) WebEnviroment.getTransportData();
        notification.deleteAllPages();
        notification.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        notification.addControlField("PARAMETER", request.getRequestValue("parameter"));        
        request.setRequestData(notification);        
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        
        JSONObject json = new JSONObject();
        json.put("value", ((Notification)response.getTransportData()).getControlFieldValueByName("PARAMETER"));
        response.setContenido(json.toString(), "application/json");        
        return response;        
    }
    
    /**
     * Proceso interno cuya respuesta es enviada a un proceso Padre     
     * @param request
     * @return
     * @throws Exception 
     */
    public String internalProcess(WebRequest request, String parameter)throws Exception{
        Notification notification = (Notification) WebEnviroment.getTransportData();
        notification.deleteAllPages();
        notification.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        notification.addControlField("PARAMETER", parameter);        
        request.setRequestData(notification);        
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        return ((Notification)response.getTransportData()).getControlFieldValueByName("PARAMETER");
    }
 
    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
