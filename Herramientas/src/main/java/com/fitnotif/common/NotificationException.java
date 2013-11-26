package com.fitnotif.common;

import java.text.MessageFormat;

/**
 * Manejador de errores en servidor de Notificaciones.
 * Extiende a clase {@link RuntimeException}
 * 
 * @author santiago
 * @version 1.0
 */
public class NotificationException extends RuntimeException{
    
    /** Codigo de mensaje aplicativo. */
    private String            code;

    /** Arreglo de parametros a mostrar en la aplicacion */
    private Object[]          parameters;

    /**
     * Creacion de excepcion
     *
     * @param pCause     
     */
    public NotificationException(Exception pCause) {
        super(pCause);
    }
    
    /**
     * Creacion de excepcion
     * 
     * @param code Codigo de error
     * @param message Mensaje al usuario
     * @param pParameters Arreglo de parametros a presentar en mensaje
     */
    public NotificationException(String code, String message, Exception pCause, Object... pParameters) {
        super(code + ": " + message, pCause);
        this.code = code;
        this.parameters = pParameters;
    }
    
    /**
     * Creacion de mensaje de error
     * 
     * @param code Codigo de error
     * @param message Mensaje al usuario
     * @param pParameters Arreglo de parametros a presentar en mensaje
     */
    public NotificationException(String code, String message, Object... pParameters) {
        super(code + ": " + message);
        this.code = code;
        this.parameters = pParameters;
    }

    /**
     * Retorna el valor del codigo de error.
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Retorna el mensaje de la excepciï¿½n.
     * 
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return this.getMessage(super.getMessage());
    }

    /**
     * Retorna el mensaje de la excepcion
     * 
     * @param pLocale
     *            Codigo de Locale a utilizar para presentar el mensaje
     *            traducido.
     * @return
     */
    public String getMessage(String msg) {
        //ResourceBundle resource = ResourceBundle.getBundle("usermessages", pLocale);                
        msg = msg + " <*>";
        if (this.parameters != null) {
            msg = MessageFormat.format(msg, this.parameters);
        }
        return msg;
    }
/*
    public Object[] getParameters() {
        return this.parameters;
    }
  */  
}
