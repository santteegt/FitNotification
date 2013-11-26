package com.fitnotif.web.exception;

import com.fitnotif.notification.Request;
import com.fitnotif.web.Controller;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.data.WebResponse;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletResponse;

/**
 * Manejador de errores
 * @author santiago
 * @version 1.0
 */
public final class ErrorHandler {
    
    private static Boolean enviroment = false;
    
    private ErrorHandler() {
    }

    public static WebResponse procesar(Controller controller, WebRequest request,
            WebResponse originResponse, HttpServletResponse response,
            Throwable t) {        
        Request responseData;
        //Boolean onController = true;
        //if(originResponse == null){
            responseData = WebEnviroment.getTransportData();
        //    onController = false;
        //}else{
            /*responseData = originResponse.getTransportData() != null ? originResponse.getTransportData():
                    WebEnviroment.getTransportData();*/
        //}
                
        WebResponse respuesta = new WebResponse(responseData,request);
        StringWriter stackTrace = new StringWriter();
        PrintWriter pw = new PrintWriter(stackTrace);
        String mensajeUsuario = "";        
        
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        t.printStackTrace(pw);
        Logger.getLogger(ErrorHandler.class.getName()).warning("ERROR LANZADO: " 
                + (t.getMessage() != null ? t.getMessage():stackTrace.toString()));
        WebEnviroment.getTransportData().setStacktrace(stackTrace.toString());
        

        /*if (respuestaOriginal != null && transporteDB != null) {
            pw.println("Causado por:");
            pw.println(transporteDB.getStackTrace());
            mensajeUsuario = transporteDB.getMessage();
        } else if (t instanceof ErrorWeb) {
            ErrorWeb errorWeb = (ErrorWeb) t;

            if (errorWeb.getTransporteDB() != null) {
                transporteDB = errorWeb.getTransporteDB();
                mensajeUsuario = transporteDB.getMessage();
            } else {
                mensajeUsuario = t.getLocalizedMessage();
            }
        } else {*/
            mensajeUsuario = t.getMessage();
            WebEnviroment.getTransportData().setStatus(mensajeUsuario);
        //}

        /*if (pedido != null) {
            MessageType tipo = pedido.getTransporteDB().getMessageType();

            mensaje += String.format("\nTipo %s (%s)", pedido.getTipoPedido(),
                    tipo);

            if (pedido.getTransporteDB() != null) {
                mensaje += String.format("-> %s", pedido.getTransporteDB().
                        getResponseCode());
            }
        }*/
              
        if (controller != null && enviroment) {
            controller.onError(respuesta, mensajeUsuario, stackTrace.toString());            
        }

        if (respuesta.getContenido() == null) {            
            String error = request.getHttpServletRequest().getContextPath() +
                    (enviroment ? "/entorno.html#error":"/error.html");
            try {                
                response.sendRedirect(error);
            } catch (IOException e) {
            }
        }
        
        pw.close();
        return respuesta;
    }

    /**
     * Revisa el codigo de respuesta y lanza una excepcion
     *
     * @param respuesta
     * @throws ErrorWeb
     */
    public static void checkOkCodes(WebResponse respuesta, Boolean pEnviroment) {
        enviroment = pEnviroment;
        String codigo = respuesta.getTransportData().getStatus();//respuesta.getTransporteDB().getResponseCode();
        if (isError(codigo)) {
            Logger.getLogger(ErrorHandler.class.getName()).warning("ERROR EN EL PROCESO EN EL SERVIDOR");
            throw new WebError(respuesta);
        }        
    }

    /**
     * Revisa que el codigo de respuesta sea positivo o de error
     *
     * @param respuesta
     * 
     * @throws boolean | true es un error, false no es un error
     */
    public static boolean isError(String codigo) {       
        return codigo != null && codigo.compareTo("OK")!=0;//!codigo.matches("(?i)0|.*-0|ok-.*");        
    }
    
    
    
}
