
package com.fitnotif.web.process;

import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.common.Encryption;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.web.Controller;
import com.fitnotif.util.Handler;
import com.fitnotif.web.RequestTypes;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.util.PropertiesHandler;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.exception.WebError;
import com.fitnotif.web.manager.DeviceFilter;
import com.fitnotif.web.manager.DeviceManager;
import com.fitnotif.web.manager.NotificationLinker;
import java.util.Properties;

/**
 * Proceso de Inicio de Sesion
 * @author santiago
 * @version 1.0
 */
@Handler(RequestTypes.LOGIN)
public class Login implements Controller{
    
    private Boolean inEnviroment = Boolean.FALSE;    
    private DeviceFilter devfilter;
    private Properties properties;

    @Override
    public WebResponse process(WebRequest request) throws Exception{
        this.devfilter = new DeviceFilter(request);        
        if(!this.devfilter.hasCookieSupport()){
            throw new WebError("EL DISPOSITIVO NO TIENE HABILITADO EL USO DE COOKIES");
        }
        if(!request.getHttpServletRequest().getSession().isNew()){
            request.getHttpServletRequest().getSession(true);
        }
        properties = PropertiesHandler.getInstance().getProperties("notifclient");
        String authType = properties.getProperty("auth");
        Encryption encryption = (Encryption)Class.forName(authType).newInstance();        
        Boolean deviceFilter = Boolean.parseBoolean(properties.getProperty("deviceFilter"));
        String admin = properties.getProperty("server.admin");
        
        Request requestData = WebEnviroment.getTransportData();
        requestData.setSid(WebEnviroment.getSessionId());
        requestData.setOperation(this.getClass().getAnnotation(Handler.class).value().toUpperCase());
        String user = request.getRequestValue("username").toUpperCase();
        requestData.setUser(user);
        requestData.setPassword( encryption.encrypt( request.getRequestValue("password") ) ); 
        requestData.setIp(request.getHttpServletRequest().getRemoteAddr());
        /** Se omite el filtro de dispositivos para el Administrador **/
        deviceFilter = user.equals(admin)?false:deviceFilter;
        this.getDeviceInfo(requestData, deviceFilter);
        request.setRequestData(requestData);   
        WebResponse response = new NotificationLinker().process(request);
        ErrorHandler.checkOkCodes(response, inEnviroment);
        
        WebEnviroment.getSessionData().setUserName(response.getTransportData().getUser());
        String page = response.getTransportData().getOperation().compareTo(RequestTypes.CHPWD.toUpperCase()) == 0 ?
                WebEnviroment.URI_CLAVE:WebEnviroment.URI_ENTORNO;
        
        Boolean isAdmin = verifyRole(request);
        page = this.registerUserData(request, isAdmin, response, page);               
        page = isAdmin && !page.equals(WebEnviroment.URI_CLAVE) ? WebEnviroment.URI_ADMIN : page;    
        
        request.redirect(page);        
        return response;
    }
    
    /**
     * Obtiene la informacion del dispositivo, llamando a DeviceFilter
     * @param request Peticion de la aplicacion
     * @param pRequest Peticion xml a enviar al servidor
     * @throws Exception 
     */
    private void getDeviceInfo(Request pRequest, Boolean deviceFilter)throws Exception{        
        pRequest.setDevice( this.devfilter.getDeviceName(deviceFilter) );
    }
           
    /**
     * Llamada al proceso de verificacion de rol adminstrador
     * @return
     * @throws Exception 
     */
    private Boolean verifyRole(WebRequest request)throws Exception{
        Controller controller = new VerifyAdminPrivileges();
        WebResponse response = controller.process(request);
        Boolean role = ((Notification)response.getTransportData()).getControlFieldValueByName("ROLEOK").equals("1")?
                Boolean.TRUE:Boolean.FALSE;
        WebEnviroment.getSessionData().setAdminp(role);
        return role;        
    }
    
    /**
     * Registra los datos de Usuario (No Administrador) y realiza la verificacion
     * de dispositivos
     * @param isAdmin true si el usuario es Administrador
     * @param response Respuesta del servidor
     * @param page Pagina de redireccion
     * @return Pagina de redireccion
     * @throws Exception 
     */
    private String registerUserData(WebRequest request, Boolean isAdmin, WebResponse response, String page)throws Exception{        
        if(!isAdmin){
            
            Integer maxdev = Integer.parseInt(new GetSystemParameter().internalProcess(request, "maxdev"));
            WebEnviroment.exeededDevices( new DeviceManager(response.getTransportData(),false)
                .getDeviceCount().intValue() > maxdev ? Boolean.TRUE : Boolean.FALSE);
        
            WebEnviroment.getSessionData().setName(
                    ((Notification)response.getTransportData()).getControlFieldValueByName("NAME"));
            WebEnviroment.getSessionData().setEmail(
                    ((Notification)response.getTransportData()).getControlFieldValueByName("EMAIL"));

        }
        return page;
    }

    @Override
    public void onError(WebResponse respuesta, String mensajeUsuario, String stackTrace) {
        throw new UnsupportedOperationException("Not supported yet.");        
    }
}
