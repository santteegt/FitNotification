package com.fitnotif.server;

import com.fitnotif.client.UCIxmlClient;
import com.fitnotif.common.NotificationException;
import com.fitnotif.common.Server;
import com.fitnotif.common.StreamManager;
import com.fitnotif.dto.Detail;
import com.fitnotif.dto.GeneralResponse;
import com.fitnotif.notification.Notification;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.OperationTypes;
import com.fitnotif.parser.DetailNotificationAdapter;
import com.fitnotif.parser.XMLParser;
import com.fitnotif.util.PropertiesHandler;
import com.fitnotif.util.StatusTypes;
import java.net.InetAddress;
import org.jboss.logging.Logger;

/**
 * Clase que se encarga de procesar requests recibidos por el CORE de FitBank
 * @author malgia
 * @version 1.0
 */
public class FitReceiverServer extends Server{
    
    private boolean running;
    
    private int port;
    
    private ServerSocket server;
   
    private static final Logger LOG = Logger.getLogger(FitReceiverServer.class);
    
    /**
     * Constructor de la clase
     * @param port 
     */
    public FitReceiverServer(){
        if(this.server!=null){
            try {
                this.server.close();
            } catch (IOException ex) {
                throw new NotificationException("ERRSRV", "ERROR AL CERRAR LA CONEXION", ex);
            }
        }        
        this.port=Integer.parseInt(PropertiesHandler.getInstance().getProperties("notificationlistener").getProperty("fitreceiverserver.port"));
    }

    /**
     * Método que se ejectua cuándo se instancia la clase.
     * Recibe un request, lo procesa y envia una respuesta.
     */
    @Override
    public void run() {
        try {
            this.getConnection();
            while (this.running) {
                Socket client = this.server.accept();
                if(client!=null){
                    StreamManager sm = new StreamManager(client);
                    Object obj = sm.readMessage();
                    if(obj instanceof String){
                        isDetail((String) obj);
                    }else if (obj instanceof Request){
                        Notification not = (Notification) obj;
                        GeneralResponse response = isNotification(not);
                        if(response!=null && response.getCode().compareTo("0")==0){
                            not.setStatus("OK");
                        }else{
                            not.setStatus(response.getUserMessage());
                        }
                        sm.sendMessage(not, false);
                    }
                    sm.close();
                    client.close();
                }
            }
        } catch (Exception ex) {
            throw new NotificationException("SRV001", "NO SE PUDO INICIAR EL SERVIDOR", ex);
        }
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
        } catch (IOException ex) {
            throw new NotificationException("CON002", "NO SE PUDO CERRAR LA CONEXIÓN", ex);
        }
    }
    
    /**
     * Método que se ejecuta cuándo se recibe un detail
     * @param xml
     * @throws IOException 
     */
    private void isDetail(String xml) throws IOException{
        DetailNotificationAdapter dna=new DetailNotificationAdapter(xml, 1);
        Notification not=dna.detailToNotification();                   
        if(not.getPageById("USUARIOS")==null || not.getPageById("USUARIOS").getRegisters().isEmpty()){
            if(not.getControlFieldValueByName("FITBANK")!=null && not.getControlFieldValueByName("FITBANK").compareTo("1")==0){
                not.setOperation(OperationTypes.AUTHORIZE2.getType());
            }else{
                not.setOperation(OperationTypes.NOTIFY.getType());
                not.setStatus(StatusTypes.NEW.getType());
                not.addControlField("DETAIL", xml);
            }
        }else{    
            not.setOperation(OperationTypes.FILLUSERS.getType());
        }
        Integer serverPort=Integer.parseInt(PropertiesHandler.getInstance().getProperties("notificationlistener").getProperty("notificationserver.port"));
        Socket client=new Socket(InetAddress.getByName("localhost"), serverPort);
        StreamManager sm = new StreamManager(client);
        sm.sendMessage(not, false);
        sm.close();
        client.close();
        this.LOG.info("Detail procesado correctamente.");
    }

    /**
     * Método que se ejecuta cuándo se recibe un Notification
     * @param xml
     * @throws IOException 
     */
    private GeneralResponse isNotification(Notification not) throws Exception{
        String detail=not.getControlFieldValueByName("DETAIL");
        GeneralResponse resp = null;
        if(not.getStatus().compareTo(StatusTypes.NEW.getType())==0){
            resp = new GeneralResponse("0");
        }
        if(detail!=null){
            XMLParser prs = new XMLParser(detail);
            Detail det = new Detail(prs);
            det.removeTables();
            det.setMessageId(not.getControlFieldValueByName("TOKEN"));
            det.setUser(not.getUser());
            det.setSubsystem("00");
            det.setTransaction("2009");
            det.setType("MAN");
            det.setChannel("XML");
            det.findFieldByNameCreate("_BPM_OBS").setValue(not.getControlFieldValueByName("DETALLE"));
            det.findFieldByNameCreate("_TRNSTATUS").setValue(not.getControlFieldValueByName("ESTATUS").compareTo("A")==0?"2":"3");
            det.findFieldByNameCreate("USUARIO").setValue(not.getUser());
            det.findFieldByNameCreate("SECUENCIA").setValue(det.findFieldByName("SECAUTORIZACION").getStringValue());
            String response = (String) UCIxmlClient.connect(det.toXml());
            this.LOG.info("Notification enviado.");
            resp = new Detail(new XMLParser(response)).getResponse();
        }
        return resp;
    }    
}
