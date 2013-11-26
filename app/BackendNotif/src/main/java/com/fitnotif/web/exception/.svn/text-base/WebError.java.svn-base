package com.fitnotif.web.exception;

import com.fitnotif.notification.Request;
import com.fitnotif.util.RemoteException;
import com.fitnotif.web.data.WebResponse;


/**
 * Custom Throwable Class
 * @author santiago
 * @version 1.0
 */
public class WebError extends Error{    

    public WebError() {
        super();
    }

    public WebError(String mensaje, Throwable error) {
        super(mensaje, error);
    }

    public WebError(String mensaje) {
        super(mensaje);
    }
/*
    public ErrorWeb(Throwable error) {
        super(error);
    }
*/
    public WebError(WebResponse response) {
        this(response.getTransportData());
    }

    public WebError(Request request) {
        super(getInfo(request), getCause(request));        
    }

    /**
     * Obtiene el mensaje de Error enviado del Servidor
     * @param request
     * @return 
     */
    private static String getInfo(Request request) {
        return  request != null ? request.getStatus() : "";        
    }

    /**
     * Obtiene el StackTrace como un Throwable
     * @param transporteDB
     * @return 
     */
    private static Throwable getCause(Request request) {
        return request != null ? new RemoteException(request.getStatus(),request.getStacktrace())
                : null;        
    }
}
