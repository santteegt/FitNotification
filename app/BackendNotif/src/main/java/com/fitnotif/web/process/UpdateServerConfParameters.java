
package com.fitnotif.web.process;
import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.util.Handler;
import com.fitnotif.web.Controller;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.web.manager.NotificationLinker;
import java.util.Iterator;
import net.sf.json.JSONObject;


/**
 * Actualiza los parametros de configuracion del servidor
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.UPSRD)
public class UpdateServerConfParameters implements Controller{

    private Boolean inEnviroment = Boolean.TRUE;
    
    @Override
    public WebResponse process(WebRequest request) throws Exception {
        Notification requestData = (Notification)WebEnviroment.getTransportData();
        requestData.deleteAllPages();
        requestData.setSid(WebEnviroment.getSessionId());
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        requestData.setUser(WebEnviroment.getSessionData().getUserName());
        
        Page configuration = new Page(ConfPagesTypes.CONFIGURATTION.getPage());
        Register register = new Register("1");
        Column column;
        
        JSONObject json = JSONObject.fromObject(request.getRequestValue("flds"));        
        Iterator iterator = json.keys();
        while(iterator.hasNext()){
            String field = (String)iterator.next();
            column = new Column(field, (String)json.getString(field));
            register.addColumn(column);            
        }
        configuration.addRegister(register);
        requestData.addPage(configuration);
        request.setRequestData(requestData);                
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        return response;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        JSONObject json = new JSONObject();
        json.element("message", mensajeUsuario);
        WebEnviroment.setResponseObj(json);
    }
    
}
