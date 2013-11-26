package com.fitnotif.processors;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.common.TokenCreator;
import com.fitnotif.util.TokenManager;
import com.fitnotif.mailsender.SMTPConnection;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TAutorizaciones;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.tables.helper.UserHelper;
import com.fitnotif.util.StatusTypes;

/**
 * Clase que se encarga de recibir la notificacion y de enviar el correo electrónico
 * @author malgia
 * @version 1.0
 */
public class NotifyProcessor implements Processor{
    
    /**
     * Método que envía el correo electrónico
     * @param request
     * @throws Exception 
     */
    private void sendMessage(Request request) throws Exception{ 
        Notification notification=(Notification) request;
        //HTMLMailBuilder mailBuilder=new HTMLMailBuilder(notification);
        String email=UserHelper.getMailByUser(notification.getUser());
        if (email!=null){
            String message=notification.getControlFieldValueByName("_MAILMESSAGE");
            SMTPConnection smtpConnection = new SMTPConnection(email, "FITNOTIFICATION: "+notification.getName(), this.addLink(message));
            smtpConnection.start();
        }
    }

    /**
     * Proceso normal
     * Guarda una autorizacion y un token en la base de datos
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        String token=TokenCreator.createToken();
        TokenManager.saveToken(token);
        TAutorizaciones tautorizaciones=new TAutorizaciones(request.getId(), DatesHelper.getExpireTimeStamp());
        tautorizaciones.setFkctipoautorizacion(request.getType());
        tautorizaciones.setCtransaccion(request.getTransaction());
        tautorizaciones.setFkcestatusautorizacion(StatusTypes.NEW.getType());
        tautorizaciones.setNombre(request.getName());
        tautorizaciones.setFkcusuario(request.getUser());
        tautorizaciones.setCusuarioOrigen(request.getOriginUser());
        tautorizaciones.setFdesde(DatesHelper.getCurrentTimeStamp());
        tautorizaciones.setFktoken(token);
        HibernateUtil.save(tautorizaciones);
        try{ 
            sendMessage(request);
        }catch(Exception ex){
            throw new NotificationException("ERRMAIL", "NO SE PUDO ENVIAR EL CORREO ELECTRONICO", ex);
        }
        return request;
    }
    
    private String addLink(String message){
        String newMessage="";
        String server=ParameterHelper.getStringValue("web-ip");
        Integer port=ParameterHelper.getIntegerValue("web-port");
        String text="<p align='center'>De click <a href='https://"+server+":"+port+"/Notifweb'>aqui</a> para acceder.</p>";
        if(message!=null){
            newMessage=message.substring(0, message.indexOf("</body>"))+text+message.substring(message.indexOf("</body>"),message.length());
            newMessage=newMessage.replace("http://srv.fit-bank.com/mantis/logo.jpg", "http://www.malgia.com/fitnotification/logo48.png");
        }else{
            newMessage="<html><body>"+text+"</body></html>";
        }
        return newMessage;
    }
}
