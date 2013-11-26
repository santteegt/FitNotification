package com.fitnotif.processors;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TUsuarioDispositivos;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.DeviceHelper;
import com.fitnotif.util.DeviceStatus;

/**
 * Clase que registra el dispositivo en la base de datos.
 * @author malgia
 * @version 1.0
 */
public class RegisterDeviceProcessor implements Processor{

    /**
     * Proceso normal
     * Agrega un nuevo dispositivo a la base de datos.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        String username=request.getUser();
        String ip=request.getIp();
        String device=request.getDevice();
        if(DeviceHelper.countDevices(username)>=3){
            throw new NotificationException("ERRDEV", "EL USUARIO {0} YA TIENE 3 DISPOSITIVOS REGISTRADOS", username);
        }
        TUsuarioDispositivos tUserDevices=new TUsuarioDispositivos(ip, username, device, DatesHelper.getExpireTimeStamp());
        tUserDevices.setFdesde(DatesHelper.getCurrentTimeStamp());
        tUserDevices.setEstado(DeviceStatus.ACTIVE.getType());
        HibernateUtil.save(tUserDevices);
        return request;
    }
}
