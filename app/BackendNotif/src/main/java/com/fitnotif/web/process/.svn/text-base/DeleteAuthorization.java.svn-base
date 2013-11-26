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
 * Elimina una autorizacion con codigo de error
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.DAUTH)
public class DeleteAuthorization implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {        
        String id = request.getRequestValue("id");        

        Notification requestData = (Notification) WebEnviroment.getTransportData();
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        requestData.addControlField("DELETE_TOKEN", id);
        request.setRequestData(requestData);            
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        request.redirect(WebEnviroment.URI_ADMIN/* + "#autorizaciones"*/);
        return response;        
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);        
    }
    
}