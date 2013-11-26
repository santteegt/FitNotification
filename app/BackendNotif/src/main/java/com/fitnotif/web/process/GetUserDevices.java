package com.fitnotif.web.process;

import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.manager.DeviceManager;
import net.sf.json.JSONObject;
import com.fitnotif.notification.emuntypes.DeviceFields;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.NotificationLinker;

/**
 * Devuelve los dispositivos registrados en el sistema
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.DEVICES)
public class GetUserDevices implements Controller{

    private Boolean inEnviroment = Boolean.FALSE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        Request req = WebEnviroment.getTransportData();
        DeviceManager devman = new DeviceManager(req,false);
        WebResponse response;
        if(devman.getDeviceCount() == 0){
            Request requestData = WebEnviroment.getTransportData();
            requestData.setSid(WebEnviroment.getSessionId());
            requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
            requestData.setUser(WebEnviroment.getSessionData().getUserName());
            request.setRequestData(requestData);
            
            response = new NotificationLinker().process(request);
            ErrorHandler.checkOkCodes(response, inEnviroment);        
            devman = new DeviceManager(response.getTransportData(),false);
            
        }else{
            response = new WebResponse(req, request);
        }         
        
        response.setContenido(this.setDeviceList(devman), "application/json");        
        return response;
    }
    
    /**
     * Establece la lista de dispositivos registrados por el usuario
     * @return
     * @throws Exception 
     */
    private String setDeviceList(DeviceManager devman)throws Exception{
        JSONObject mjson = new JSONObject();
        JSONObject json;
        int count = 1;        
        for(Register dev:devman.getDevices()){            
            json = new JSONObject();
            json.put(DeviceFields.MODEL.getField(), 
                    dev.getColumnByName(DeviceFields.MODEL.getField()).getValue());
            json.put(DeviceFields.CONECTION_DATE.getField(), 
                    dev.getColumnByName(DeviceFields.CONECTION_DATE.getField()).getValue());
            mjson.put("device" + count++ + "-" + dev.getColumnByName(DeviceFields.IPADDRESS.getField()).getValue(), json);            
        }

        return mjson.toString();
    }
    
    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
