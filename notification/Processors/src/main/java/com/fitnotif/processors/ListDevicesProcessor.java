package com.fitnotif.processors;

import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.notification.emuntypes.DeviceFields;
import com.fitnotif.persistence.tablas.TUsuarioDispositivos;
import com.fitnotif.tables.helper.DeviceHelper;
import org.hibernate.ScrollableResults;

/**
 * Clase que devuelve todos los dispositivos registrados en la base de datos para un usuario.
 * @author malgia
 * @version 1.0
 */
public class ListDevicesProcessor implements Processor{

    /**
     * Proceso normal
     * Devuelve los dispositivo de un usuario.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        notification=ListDevicesProcessor.fillDevices(notification);
        return notification;
    }
      
    /**
     * Método que pone en el Notification los dispositvos de un usuario
     * @param notification
     * @return 
     */
    public static Notification fillDevices(Notification notification){
        String username=notification.getUser();
        ScrollableResults userDevices=DeviceHelper.getDevices(username);
        Integer i=1;
        Page devicesPage=new Page(ConfPagesTypes.DEVICES.getPage());
        while (userDevices.next()){
            TUsuarioDispositivos userDevice=(TUsuarioDispositivos) userDevices.get()[0];
            Register reg=new Register(i.toString());
            reg.addColumn(new Column(DeviceFields.IPADDRESS.getField(), userDevice.getId()));
            reg.addColumn(new Column(DeviceFields.MODEL.getField(), userDevice.getModelo()));
            reg.addColumn(new Column(DeviceFields.STATUS.getField(), userDevice.getEstado()));
            String fecha=userDevice.getFconexion().toString();
            reg.addColumn(new Column(DeviceFields.CONECTION_DATE.getField(), fecha.substring(0, fecha.indexOf('.'))));
            devicesPage.addRegister(reg);
            i++;
        }
        notification.addPage(devicesPage);
        return notification;
    }
}

