package com.fitnotif.web.process;

import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import net.sf.json.JSONObject;

/**
 * Obtiene los datos de usuario desde la sesion para mostrarlos en el entorno
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.USINF)
public class GetUserInfo implements Controller{    
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {        
        JSONObject json = new JSONObject();
        json.element("username", WebEnviroment.getSessionData().getUserName());
        json.element("name", WebEnviroment.getSessionData().getName());
        json.element("email", WebEnviroment.getSessionData().getEmail());
        WebResponse response = new WebResponse(WebEnviroment.getTransportData(), request);
        response.setContenido(json.toString(), "application/json");
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
