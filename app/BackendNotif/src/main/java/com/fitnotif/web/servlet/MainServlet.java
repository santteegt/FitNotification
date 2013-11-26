package com.fitnotif.web.servlet;

import com.fitnotif.web.exception.ErrorHandler;
import com.fitnotif.util.Servicios;
import com.fitnotif.web.Controller;
import com.fitnotif.util.Handler;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import com.fitnotif.web.manager.DeviceFilter;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * App Servlet. Extiende de {@link HttpServlet}
 * @author santiago
 * @version 1.0
 */
public class MainServlet extends HttpServlet{
    
    
    private final Map<String, Controller> controllers = new HashMap<String, Controller>();
    
    @Override
    public void init(ServletConfig config)throws ServletException{
        for (Controller controller : Servicios.load(Controller.class)) {
            Handler handler = controller.getClass().getAnnotation(Handler.class);

            if (handler != null) {                
                controllers.put(handler.value(), controller);
                Logger.getLogger(this.getClass().getName()).config("CARGADO PROCESO " + controller.getClass().getName());
            }
        }      
        
        super.init(config);
    }
        
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{        
        WebEnviroment.init(request);        
        WebRequest webReq = new WebRequest(request, response);
        try{
            DeviceFilter filter = new DeviceFilter(webReq);
            WebEnviroment.log( "PETICION DEL DISPOSITIVO: " + filter.getDeviceName(Boolean.FALSE) );
            if( filter.getUserAgent().toUpperCase().matches(".*ANDROID.*") ){
                this.registerProcess(webReq);  //para requests repetidos que provienen de dispositivos Android
            }            
        }catch(Exception e){
            //NO DEBERIA FALLAR
        }
        WebResponse webRes = null;         
              
        Controller controller = controllers.get(webReq.getTipoPedido());
        //carga-resources 
        if(controller==null){
            webReq.redirect(webReq.getTipoPedido());
            return;
        }                
        try{            
            webRes = controller.process(webReq);
            webRes.escribir();            
            if(WebEnviroment.isSessionAvailable()){
                WebEnviroment.setTransportData(webRes.getTransportData());
                WebEnviroment.log("SE EJECUTA CORRECTAMENTE EL PROCESO " + controller.getClass().getName() );
            }else{
                WebEnviroment.getSession().invalidate();
                WebEnviroment.log("SE TERMINA LA SESION DE USUARIO");
            }
        }catch(Throwable e){            
            webRes = ErrorHandler.procesar(controller, webReq, webRes, response, e);            
        }finally{
            
        }
    }
    
    /**
     * Registra el evento para manejo de request repetidos.
     * Proceso para dispositivos Android
     * @throws Exception 
     */
    private void registerProcess(WebRequest webReq)throws Exception{
        if( WebEnviroment.isSameProcess( webReq.getTipoPedido() ) ){
            return;
        }else{
            WebEnviroment.setProcess(webReq.getTipoPedido());            
        }
    }
    
    @Override
    public void destroy(){
        
    }
    
}