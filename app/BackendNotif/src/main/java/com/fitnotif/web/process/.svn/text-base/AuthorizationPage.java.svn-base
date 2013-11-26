package com.fitnotif.web.process;

import com.fitnotif.notification.Notification;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebPageTransport;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.NotificationLinker;
import com.fitnotif.webpages.parser.NotifToPageParser;


/**
 * Proceso encargado de enviar la informacion de la autorizacion pedida
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.AUTHPAG)
public class AuthorizationPage implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {        
        Notification requestData = (Notification)WebEnviroment.getTransportData();
        requestData.deleteAllPages();
        requestData.setSid(WebEnviroment.getSessionId());        
        requestData.setId(WebEnviroment.getAuthToken());        
        String operation = this.getClass().getAnnotation(Handler.class).value().toUpperCase();        
        requestData.setOperation(operation);
        request.setRequestData(requestData);
        WebResponse response = new NotificationLinker().process(request);        
        ErrorHandler.checkOkCodes(response, inEnviroment);        
        NotifToPageParser parser = new NotifToPageParser((Notification)response.getTransportData());
        WebPageTransport transport = new WebPageTransport(response, parser.parseWebPage());        
        response.setContent(transport);
        
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        
    }
    
}
