
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
 * Reinicia la contrasena de usuario
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.RUPAS)
public class RebootUserPass implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override    
    public WebResponse process(WebRequest request) throws Exception {
        Notification requestData = (Notification)WebEnviroment.getTransportData();
        requestData.deleteAllPages();
        requestData.setSid(WebEnviroment.getSessionId());
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        requestData.setUser(WebEnviroment.getSessionData().getUserName());
        requestData.addControlField("USER_PASS", request.getRequestValue("user").toUpperCase());
        
        request.setRequestData(requestData);                
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);
    }
    
}
