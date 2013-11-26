
package com.fitnotif.web.process;

import com.fitnotif.web.Controller;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import net.sf.json.JSONObject;
import com.fitnotif.util.Handler;
import com.fitnotif.web.RequestTypes;

/**
 * Obtiene del entorno si el usuario ya ha pasado el numero maximo de dispositivos
 * registrados
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.AEDEV)
public class AskExeededDevices implements Controller{

    @Override
    public WebResponse process(WebRequest request) throws Exception {
        JSONObject json = new JSONObject();
        json.put("devices", WebEnviroment.isExeededDevices());        
        WebResponse response = new WebResponse(WebEnviroment.getTransportData(), request);
        response.setContenido(json.toString(), "application/json");
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
