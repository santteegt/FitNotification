package com.fitnotif.mailsender;

import javax.mail.PasswordAuthentication;

/**
 * Clase que permite la autenticación con el servidor SMTP
 * @author malgia
 */
public class SMTPAuthenticator extends javax.mail.Authenticator
{
    
  private String user;
  private String password;

  public SMTPAuthenticator(String user, String password){
      this.user=user;
      this.password=password;
  }
  
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(this.user, this.password);
    }
}


