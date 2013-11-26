package com.fitnotif.server;

import com.fitnotif.common.StreamManager;
import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.Server;
import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.processors.Processor;
import com.fitnotif.notification.emuntypes.OperationTypes;
import com.fitnotif.persistence.tablas.TSesiones;
import com.fitnotif.tables.helper.AuthorizationHelper;
import com.fitnotif.tables.helper.DeviceHelper;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.tables.helper.SessionHelper;
import com.fitnotif.tables.helper.UserHelper;
import com.fitnotif.util.PropertiesHandler;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Iterator;
import org.jboss.logging.Logger;

/**
 * Clase que se encarga de procesar los requests recibidos por los otros módulos
 * @author malgia
 * @version 1.0
 */
public class NotificationServer extends Server{
    
    private boolean running;
    
    private int port;
    
    private ServerSocket server;
    
    private static final Logger LOGGER = Logger.getLogger(NotificationServer.class);
    
    private boolean clientError;
    /**
     * Constructor de la clase
     * @param port 
     */
    public NotificationServer(){
        if(this.server!=null){
            try {
                this.server.close();
            } catch (IOException ex) {
                throw new NotificationException("ERRSRV", "ERROR AL CERRAR LA CONEXION", ex);
            }
        }        
        this.port=Integer.parseInt(PropertiesHandler.getInstance().getProperties("notificationlistener").getProperty("notificationserver.port"));
    }

    /**
     * Método que se ejectua cuándo se instancia la clase.
     * Recibe un request, lo procesa y envia una respuesta.
     */

    public void run() {
        try {
            this.getConnection();
            this.expireActiveSessions(true);
            while (this.running) {
                Socket client = this.server.accept();
                if(client!=null){
                    this.clientError=false;
                    HibernateUtil.beginTransaction();
                    Notification processed=this.manageStream(client);
                    client.close();
                    if(processed.getStatus().compareTo("OK")==0){
                        HibernateUtil.commit();
                    }else{
                        HibernateUtil.rollback();
                    }
                    if(this.clientError){
                        HibernateUtil.beginTransaction();
                        AuthorizationHelper.saveErrorMessage(processed.getId(), processed.getStatus());
                        HibernateUtil.commit();
                    }
                }
            }
        } catch (Exception ex) {
            throw new NotificationException("SRV001", "NO SE PUDO INICIAR EL SERVIDOR", ex);
        }
    }
    
    private Notification manageStream(Socket client) throws Exception{
        StreamManager sm = new StreamManager(client);
        Notification notification = (Notification) sm.readMessage();
        if(notification.getOperation().compareTo(OperationTypes.NOTIFY.getType())==0){
            AuthorizationHelper.saveXMLDetail(notification.getId(), notification.getControlFieldValueByName("DETAIL"), 0);
            notification.deleteControlFieldByName("DETAIL");
            AuthorizationHelper.saveXMLAuthorization(notification, 0);
        }
        Notification processed = (Notification) this.processRequest(notification);
        if(processed.getOperation().compareTo(OperationTypes.AUTHORIZE.getType())==0 && processed.getControlFieldValueByName("DETAIL")!=null){
            Socket client2 = new Socket(InetAddress.getByName("localhost"), 4321);
            StreamManager sm2 = new StreamManager(client2);
            processed = (Notification) sm2.sendMessage(processed, true);
            if(processed.getStatus().compareTo("OK")!=0){
                this.clientError=true;
            }
            sm2.close();
            client2.close();
        } 
        if(processed.getOperation().compareTo(OperationTypes.NOTIFY.getType())!=0 && processed.getOperation().compareTo(OperationTypes.FILLUSERS.getType())!=0
                && processed.getOperation().compareTo(OperationTypes.AUTHORIZE2.getType())!=0){
            sm.sendMessage(processed, false);
        }
        sm.close();
        return processed;
    }
    
    /**
     * Método que inicializa la conexión
     * @throws IOException 
     */
    @Override
    public void getConnection(){
        try {
            this.running = true;
            this.server=new ServerSocket(this.port);
            HibernateUtil.buildSession();
        } catch (IOException ex) {
            throw new NotificationException("CON001", "NO SE PUDO INICIAR LA CONEXIÓN", ex);
        }
    }
   
    /**
     * Método que cierra la conexión
     */
    
    @Override
    public void closeConnection(){
        try {
            this.running=false;
            this.server.close();
            HibernateUtil.close();
        } catch (IOException ex) {
            throw new NotificationException("CON002", "NO SE PUDO CERRAR LA CONEXIÓN", ex);
        }
    }
    
    /**
     * Método que se encarga de llamar al Procesador correspondiente.
     * Depende del tipo de operación que se quiera realizar
     * @param request
     * @return
     * @throws Exception 
     */
    private Request processRequest(Request request){
        String op = request.getOperation();
        OperationTypes operationTypes=null;
        try{
            if (op != null) {
                    operationTypes = OperationTypes.getOperationType(op);
            }
            if (operationTypes!=null){
                    String message="";
                    if(request.getOperation().compareTo(OperationTypes.NOTIFY.getType())!=0 && request.getOperation().compareTo(OperationTypes.FILLUSERS.getType())!=0
                            && request.getOperation().compareTo(OperationTypes.AUTHORIZE2.getType())!=0){
                        message=this.validateSession(request);
                    }
                    if(message.compareTo("")==0){
                        Processor processor = (Processor) Class.forName(operationTypes.getClassname()).newInstance();
                        request=processor.process(request);
                        request.setStatus("OK");
                        LOGGER.info("SE EJECUTO CORRECTAMENTE LA CLASE: "+processor.getClass().getName());
                    }else{
                        request.setStatus(message);
                    }
                }
        }catch(Exception ex){
                request.setStatus(ex.getMessage());
                request.setStacktrace(this.getStackTrace(ex));
                LOGGER.error(this.getStackTrace(ex));
        }
        return request;
    }
  
     
    /**
     * Método que devuelve el StackTrace de una excepción.
     * @param ex
     * @return 
     */
    private String getStackTrace(Throwable ex) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            ex.printStackTrace(pw);
            String data = sw.toString();
            pw.close();
            return data;
        } catch (Exception e) {
            return null;
        }
    }
    
    private String validateSession(Request rq){
        String sid=rq.getSid();
        String user=rq.getUser();
        this.expireActiveSessions(false);
        TSesiones sesion=SessionHelper.getSession(user, sid);
        if(sesion!=null){
            return this.foundSession(rq, sesion);
        }else{
            return this.notFoundSession(rq);
        }
    }
    
       
    /**
     * Método que expira todas las sesiones activas que hayan expirado.
     */
    
    private void expireActiveSessions(Boolean beginTransaction){
        if(beginTransaction){
            HibernateUtil.beginTransaction();
        }
        Integer timeout=ParameterHelper.getIntegerValue("session-timeout");
        Integer sessionTime=1000*60*timeout;
        Iterator<TSesiones> activeSessions=UserHelper.getActiveSessions().iterator();
        while(activeSessions.hasNext()){
            TSesiones activeSession=activeSessions.next();
            if(DatesHelper.getCurrentTimeStamp().compareTo(new Timestamp(activeSession.getFinicio().getTime()+sessionTime))>0){
                SessionHelper.expireSession(activeSession);
            }
        }
        if(beginTransaction){
            HibernateUtil.commit();
        }
    }    
     
    
    /**
     * Proceso que se ejecuta cuándo encuentra la sesión.
     * @param rq
     * @param sesion
     * @return 
     */
    private String foundSession(Request rq, TSesiones sesion){
        if(DeviceHelper.lastLoggedDevice(rq.getUser())!=null && sesion.getFfin()!=null){
            if(DeviceHelper.lastLoggedDevice(rq.getUser()).getId()==null){
                sesion.setFfin(null);
                sesion.setFinicio(DatesHelper.getCurrentTimeStamp());
                return "";
            }
            if(rq.getIp().compareTo(DeviceHelper.lastLoggedDevice(rq.getUser()).getId())==0 && rq.getOperation().compareTo(OperationTypes.LOGIN.getType())==0){
                sesion.setFfin(null);
                sesion.setFinicio(DatesHelper.getCurrentTimeStamp());
                return "";
            }else{
                this.LOGGER.info("LA SESIÓN HA CADUCADO.");
                return "CAD: LA SESIÓN HA CADUCADO.";
            }
        }else{
            return "";
        }
    }
    
    /**
     * Proceso que se ejecuta cuándo no encuentra la sesión
     * @param rq
     * @param sesion
     * @return 
     */
    private String notFoundSession(Request rq){
        if(UserHelper.hasActiveSession(rq.getUser())){
            if(rq.getIp().compareTo(DeviceHelper.lastLoggedDevice(rq.getUser()).getId())==0 && rq.getOperation().compareTo(OperationTypes.LOGIN.getType())==0){
                SessionHelper.expireUserActiveSessions(rq.getUser());
                SessionHelper.saveSession(rq.getUser(), rq.getSid());
                return "";
            }else{
                this.LOGGER.info("EL USUARIO TIENE UNA SESIÓN ACTIVA EN OTRO DISPOSITIVO.");
                return "CAD: EL USUARIO TIENE UNA SESIÓN ACTIVA EN OTRO DISPOSITIVO.";
            }
        }else{
            SessionHelper.saveSession(rq.getUser(), rq.getSid());
            return "";
        }
    }
}
