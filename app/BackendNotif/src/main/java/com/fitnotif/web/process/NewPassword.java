package com.fitnotif.web.process;

import com.fitnotif.common.Encryption;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.util.Handler;
import com.fitnotif.util.PropertiesHandler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.NotificationLinker;
import java.util.Properties;
import net.sf.json.JSONObject;

/**
 * Manejo de nuevas contrasenias
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.CHPWD)
public class NewPassword implements Controller {

    private Boolean inEnviroment = Boolean.FALSE;
    private static final String NAMES = "names";
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        Properties properties = PropertiesHandler.getInstance().getProperties("notifclient");
        String authType = properties.getProperty("auth");
        Encryption encryption = (Encryption)Class.forName(authType).newInstance();        
        
        Request requestData = new Notification();
        requestData.setSid(WebEnviroment.getSessionId());
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        requestData.setUser(WebEnviroment.getSessionData().getUserName());
        requestData.setPassword( encryption.encrypt( request.getRequestValue("password") ) );
        String toPage = WebEnviroment.URI_DEVICES;
        if(!request.getRequestValue("nupass").equals("")){
            requestData.setNewPassword( encryption.encrypt( request.getRequestValue("nupass") ) );
            toPage = WebEnviroment.URI_INGRESO;
        }
        if(request.getRequestValue("ctoken")!= null && !request.getRequestValue("ctoken").equals("")){
            ((Notification)requestData).addControlField("KEYWORD", request.getRequestValue("ctoken"));
        }        
        this.checkNuInfo(request, (Notification)requestData);
        request.setRequestData(requestData);        
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        
        if(request.getRequestValue(NAMES) != null){
            WebEnviroment.getSessionData().setName(request.getRequestValue(NAMES).toUpperCase());
            WebEnviroment.getSessionData().setEmail(request.getRequestValue("email"));
        }        
        
        if(toPage.equals(WebEnviroment.URI_DEVICES)){
            JSONObject json = new JSONObject();
            json.put("devices", "true");                    
            response.setContenido(json.toString(), "application/json");
        }else{
            request.redirect(toPage);        
        }        
        return response;
    }
    
    /**
     * Verifica si se envia tambien una actualizacion de los datos de usuario
     * @throws Exception 
     */
    private void checkNuInfo(WebRequest request, Notification requestData)throws Exception{
        if( (request.getRequestValue("cemail") != null && !request.getRequestValue("cemail").equals("") && request.getRequestValue("cemail").compareTo(WebEnviroment.getSessionData().getEmail()) != 0 )
                || (request.getRequestValue(NAMES) !=null && request.getRequestValue(NAMES).toUpperCase().compareTo(WebEnviroment.getSessionData().getName()) !=0  ) ){
            requestData.addControlField("NAME", request.getRequestValue(NAMES).toUpperCase());
            requestData.addControlField("EMAIL", request.getRequestValue("email").toLowerCase());
        }
    }   
    
    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
