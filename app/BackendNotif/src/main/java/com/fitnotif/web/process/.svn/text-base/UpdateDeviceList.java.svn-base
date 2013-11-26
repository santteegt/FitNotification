package com.fitnotif.web.process;

import com.fitnotif.notification.Notification;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.DeviceManager;
import com.fitnotif.web.manager.NotificationLinker;
import java.util.Iterator;
import net.sf.json.JSONObject;

/**
 * Envia un dispositivo a ser eliminado de la lista de dispositivos del usuario
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.UPDEV)
public class UpdateDeviceList implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        Notification notification = (Notification) WebEnviroment.getTransportData();
        notification.deleteAllPages();
        notification.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        DeviceManager devman = new DeviceManager(notification, true);
        JSONObject json = JSONObject.fromObject(request.getRequestValue("dvs"));        
        Iterator iterator = json.keys();
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            devman.addDevicetoPage(key, json.getJSONObject(key));            
        }        
        request.setRequestData(notification); 
        
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);     
        WebEnviroment.exeededDevices(Boolean.FALSE);
        return response;
                
        
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json); 
    }
    
}