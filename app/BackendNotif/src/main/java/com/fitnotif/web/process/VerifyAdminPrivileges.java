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

/**
 * Verifica que el usuario tenga privilegios de administracion
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.USPRI)
public class VerifyAdminPrivileges implements Controller{
    
    private Boolean inEnviroment = Boolean.FALSE;

    @Override
    public WebResponse process(WebRequest request) throws Exception {
        
        Request requestData = WebEnviroment.getTransportData();
        requestData.setSid(WebEnviroment.getSessionId());
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        requestData.setUser(WebEnviroment.getSessionData().getUserName());        
        requestData.setIp(request.getHttpServletRequest().getRemoteAddr());        
        request.setRequestData(requestData);   
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);       
        
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
