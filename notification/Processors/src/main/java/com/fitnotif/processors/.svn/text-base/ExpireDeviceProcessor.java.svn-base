package com.fitnotif.processors;

import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.notification.emuntypes.DeviceFields;
import com.fitnotif.tables.helper.DeviceHelper;
import com.fitnotif.util.DeviceStatus;
import java.util.List;

/**
 * Clase que expira el dispositivo en la base de datos.
 * @author malgia
 * @version 1.0
 */
public class ExpireDeviceProcessor implements Processor{

    /**
     * Proceso normal
     * Elimina un dispositivo a la base de datos.
     * Si existe un dispositivo nuevo, lo agrega.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification notification=(Notification) request;
        Page page=notification.getPageById(ConfPagesTypes.DEVICES.getPage());
        List<Register> devices=page.getRegisters();
        String username=request.getUser();
        for(Register device:devices){
            if(device.getColumnValueByName(DeviceFields.STATUS.getField()).compareTo(DeviceStatus.DELETED.getType())==0){
                DeviceHelper.expireDevice(username, device.getColumnValueByName(DeviceFields.IPADDRESS.getField()), device.getColumnValueByName(DeviceFields.MODEL.getField()));
            }
            if(device.getId().compareTo("4")==0){
                String ip=device.getColumnValueByName(DeviceFields.IPADDRESS.getField());
                String model=device.getColumnValueByName(DeviceFields.MODEL.getField());
                DeviceHelper.saveNewDevice(username, ip, model);
            }
        }
        return request;
    }
}
