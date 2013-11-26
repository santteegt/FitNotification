
package com.fitnotif.web.process;

import com.fitnotif.notification.Request;
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
 * Controla el cierre de sesion invocado por el usuario
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.LOGOUT)
public class Logout implements Controller{
    
    private Boolean inEnviroment = Boolean.TRUE;

    @Override
    public WebResponse process(WebRequest request) throws Exception {        
        Request requestData = WebEnviroment.getTransportData();        
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());        
        request.setRequestData(requestData);
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);               
        
        WebEnviroment.killSession();
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);
    }
    
}