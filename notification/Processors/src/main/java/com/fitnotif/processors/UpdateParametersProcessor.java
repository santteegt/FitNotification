package com.fitnotif.processors;

import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.tables.helper.ParameterHelper;

/**
 * Clase que actualiza los parametros del sistema
 * @author malgia
 * @version 1.0
 */
public class UpdateParametersProcessor implements Processor{
     
    private Notification notification = null;
    
    /**
     * Proceso normal
     * Actualiza los parametros del sistema
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        this.notification=(Notification) request;
        this.updateStringValue("web-ip");
        this.updateIntegerValue("web-port");
        this.updateIntegerValue("session-timeout");
        this.updateIntegerValue("user-expiredays");
        this.updateIntegerValue("password-expiredays");
        this.updateStringValue("password"); 
        this.updateStringValue("smtp-host"); 
        this.updateStringValue("smtp-port"); 
        this.updateStringValue("smtp-user"); 
        this.updateStringValue("smtp-password"); 
        this.updateIntegerValue("token-timeout");
        this.updateIntegerValue("maxdev");
        return this.notification;
    }
    
    /**
     * Método que actualiza un entero;
     * @param value 
     */
    private void updateIntegerValue(String value){
        Page conf=this.notification.getPageById(ConfPagesTypes.CONFIGURATTION.getPage());
        Register reg=conf.getRegisterById("1");
        try{
            if(reg.getColumnValueByName(value)!=null && Integer.parseInt(reg.getColumnValueByName(value))!=ParameterHelper.getIntegerValue(value)){
                ParameterHelper.saveParameter(value, Integer.parseInt(reg.getColumnValueByName(value)));
            }
        }catch(NumberFormatException e){
            throw new NotificationException("PAR001", "EL FORMATO DEL PARAMETRO {0} ES INCORRECTO.", value, e);
        }
    }
    
    /**
     * Método que actualiza una cadena;
     * @param value 
     */
    private void updateStringValue(String value){
        Page conf=this.notification.getPageById(ConfPagesTypes.CONFIGURATTION.getPage());
        Register reg=conf.getRegisterById("1");
        if(reg.getColumnValueByName(value)!=null && reg.getColumnValueByName(value).compareTo(ParameterHelper.getStringValue(value))!=0){
            ParameterHelper.saveParameter(value, reg.getColumnValueByName(value));
        }
    }
}
