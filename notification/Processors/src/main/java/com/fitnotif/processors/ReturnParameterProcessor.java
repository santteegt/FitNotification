package com.fitnotif.processors;

import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.tables.helper.ParameterHelper;

/**
 * Clase que devuelve un parametro del sistema
 * @author malgia
 * @version 1.0
 */
public class ReturnParameterProcessor implements Processor{
    
    /**
     * Proceso normal
     * Retorna los parametros del sistema
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        String parameter = notification.getControlFieldValueByName("PARAMETER");
        if(parameter!=null){
            String value = null;
            if(ParameterHelper.isInteger(parameter)){
                value = ParameterHelper.getIntegerValue(parameter).toString();
            }
            if(ParameterHelper.isString(parameter)){
                value = ParameterHelper.getStringValue(parameter);
            }
            if(value!=null){
                notification.addControlField("PARAMETER", value);
            }else{
                throw new NotificationException("PAR001", "EL PARAMETRO {0} NO ESTA PARAMETRIZADO", parameter);
            }
        }
        return notification;
    }
}
