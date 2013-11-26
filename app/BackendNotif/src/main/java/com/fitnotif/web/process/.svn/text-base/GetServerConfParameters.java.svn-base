package com.fitnotif.web.process;

import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.exception.WebError;
import com.fitnotif.web.manager.NotificationLinker;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Obtiene los parametros actualies del servidor
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.SERVD)
public class GetServerConfParameters implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;    
    private JSONObject jsonPages = new JSONObject();
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {        
        Notification req = (Notification)WebEnviroment.getTransportData();
        req.deleteAllPages();
        req.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        request.setRequestData(req);
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);     
        if( ((Notification)response.getTransportData()).getPages().size() < 1 ){
            throw new WebError("ERROR AL OBTENER LOS PARAMETROS DEL SERVIDOR");
        }
        
        for(Page page:((Notification)response.getTransportData()).getPages()){            
            this.manageFields(page.getId(), page.getRegisters());
        }        
        
        response.setContenido(jsonPages.toString(), "application/json");
        return response;
    }

    /**
     * Anade los campos del registro de pagina a un Json
     * @param page
     * @param register
     * @throws Exception 
     */
    private void manageFields(String page, List<Register> pRegisters)throws Exception{        
        if(pRegisters.size() == 1){
            Register register = pRegisters.iterator().next();
            JSONObject json = new JSONObject();  
            for(Column col:register.getColumns()){
                json.element(col.getName(), col.getValue());
            }
            jsonPages.element(page, json); 
        }else{            
            JSONArray array = new JSONArray();
            JSONObject json;
            for(Register register:pRegisters){
                json = new JSONObject();
                for(Column col:register.getColumns()){
                    json.put(col.getName(), col.getValue());
                }
                array.add(json);
            }            
            jsonPages.element(page, array);
            
        }        
    }
    
    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);
    }
    
}
