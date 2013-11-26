package com.fitnotif.web.process;

import com.fitnotif.notification.Request;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import net.sf.json.JSONObject;

/**
 * Realiza el envio de la informacion de un Error Ocurrido en el proceso
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.ERROR)
public class ErrorInfo implements Controller {

    @Override
    public WebResponse process(WebRequest request) throws Exception {
        JSONObject json;
        Request data = WebEnviroment.getTransportData();
        if(request.getRequestValue("isModal") != null){            
            json = WebEnviroment.getResponseObj();
            json.put("sactive",  (json.get("message") != null && ((String)json.get("message")).matches("CAD:.*")) ? 0:1);
        }else{            
            json = new JSONObject();
            json.put("message", data.getStatus());
            json.put("stacktr", data.getStacktrace());
            json.put("sactive", data.getStatus().matches("CAD:.*")? 0:1);
        }
        WebResponse response = new WebResponse(data, request);
        response.setContenido(json.toString(), "application/json");
        if(data.getStatus().matches("CAD:.*")){
            WebEnviroment.killSession();            
        }
        return response;        
    }    
    

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
