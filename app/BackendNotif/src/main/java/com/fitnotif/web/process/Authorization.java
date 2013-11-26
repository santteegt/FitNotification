
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

/**
 * Encargado de enviar la decision del usuario para la autorizacion en cuestion
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.AUTH)
public class Authorization implements Controller{

    private Boolean inEnviroment = Boolean.FALSE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        Notification requestData = (Notification)WebEnviroment.getTransportData();
        requestData.deleteAllPages();
        requestData.setId(WebEnviroment.getAuthToken());
        requestData.setSid(WebEnviroment.getSessionId());
        requestData.setUser(WebEnviroment.getSessionData().getUserName());
        requestData.addControlField("ESTATUS", request.getRequestValue("radio-choice"));
        requestData.addControlField("DETALLE", request.getRequestValue("comment"));        
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        request.setRequestData(requestData);
        WebResponse response = new NotificationLinker().process(request);        
        ErrorHandler.checkOkCodes(response,inEnviroment);         
        
        request.redirect(WebEnviroment.URI_ENTORNO);
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
