package com.fitnotif.web.manager;

import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.notification.emuntypes.DeviceFields;
import com.fitnotif.util.DeviceStatus;
import java.util.List;
import net.sf.json.JSONObject;

/**
 * Manejador de dispositivos mobiles de usuario
 * @author santiago
 * @version 1.0
 */
public class DeviceManager {
    
    private Page devices;
    
    public DeviceManager(Request pRequest, Boolean pCreate){
        if(pCreate){
            this.devices = new Page(ConfPagesTypes.DEVICES.getPage());
            ((Notification)pRequest).addPage(devices);
        }
        else{
            this.devices = ((Notification)pRequest).getPageById(ConfPagesTypes.DEVICES.getPage());
        }                
                
    }
    
    public void process()throws Exception{
        
    }
    
    /**
     * Obtiene el numero de dispositivos registrados
     **/    
    public Integer getDeviceCount()throws Exception{
        if(devices != null){
            return devices.getRegisters().size();
        }
        else{
            return 0;
        }
    }
    
    /**
     * Obtiene los dispositivos registrados
     * @return
     * @throws Exception 
     */
    public List<Register> getDevices()throws Exception{
        return devices.getRegisters();
    }   
    
    /**
     * Aniade informacion de un dispositivo al detalle
     * @param key ID del dispositivo
     * @param properties Propiedades extras del dispositivo
     * @throws Exception 
     */
    public void addDevicetoPage(String key, JSONObject properties)throws Exception{        
        Column ip = new Column(DeviceFields.IPADDRESS.getField(), key);
        
        Column status = new Column(DeviceFields.STATUS.getField(), 
                Integer.valueOf(properties.getString(DeviceFields.STATUS.getField())) == 0 ? 
                DeviceStatus.DELETED.getType():DeviceStatus.ACTIVE.getType());
        
        Column model = new Column(DeviceFields.MODEL.getField(),
                properties.getString(DeviceFields.MODEL.getField()));
        
        Register device = new Register(String.valueOf(this.devices.getRegisters().size()+1));
        device.addColumn(ip);
        device.addColumn(status);
        device.addColumn(model);
        this.devices.addRegister(device);
    }
}
