
package com.fitnotif.web;

import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;

/**
 * Interface Controlador para procesos
 * @author santiago
 * @version 1.0
 */
public interface Controller {
    
    WebResponse process(WebRequest request) throws Exception;
    
    void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace);
    
}
