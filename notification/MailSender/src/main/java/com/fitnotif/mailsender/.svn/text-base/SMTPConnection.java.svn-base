/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitnotif.mailsender;

import com.fitnotif.common.NotificationException;
import com.fitnotif.tables.helper.ParameterHelper;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase que crea la conección SMTP
 * @author malgia
 */
public class SMTPConnection extends Thread{

    private String server;
    private String port;
    private String ssl;
    private String auth;
    private String user;
    private String password;
    private Session session;
    private String recipient;
    private String subject;
    private String message;

    /**
     * Método que envia correos electrónicos
     * @param recipient
     * @param subject
     * @param message 
     */
    private void sendMail(){

        if(this.session!=null){         
            try {
                Message msg = new MimeMessage(this.session);

                msg.setFrom(new InternetAddress(this.user));

                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipient));

                msg.setSubject(this.subject);
                msg.setContent(this.message, "text/html");
                Transport.send(msg);
            } catch (MessagingException ex) {
                throw new NotificationException("ERRMAIL", "NO SE PUDO ENVIAR EL CORREO ELECTRONICO", ex);
            }
        }
    }
    
    /**
     * Constructor de la clase
     */
    public SMTPConnection(){
        this.server=ParameterHelper.getStringValue("smtp-host");
        this.port=ParameterHelper.getStringValue("smtp-port");
        this.ssl=ParameterHelper.getStringValue("smtp-ssl");
        this.auth=ParameterHelper.getStringValue("smtp-auth");
        this.user=ParameterHelper.getStringValue("smtp-user");
        this.password=ParameterHelper.getStringValue("smtp-password");
    }
    
    /**
     * Constructor de la clase
     */
    public SMTPConnection(String recipient, String subject, String message){
        this();
        this.recipient=recipient;
        this.subject=subject;
        this.message=message;
    }    
    
    /**
     * Método que inicializa la conexión con el servidor SMTP
     */
    private void getConnection(){
        Properties props = new Properties();
        props.put("mail.smtp.host", this.server);
        props.put("mail.smtp.socketFactory.port", this.port);
        props.put("mail.smtp.socketFactory.class", this.ssl);
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.port", this.port);
        
        Authenticator authenticator=new SMTPAuthenticator(this.user, this.password);
        this.session = Session.getInstance(props, authenticator);
    }
    
    @Override
    public void run(){
        this.getConnection();
        this.sendMail();
    }
}
