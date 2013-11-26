package com.fitbank.bpm.maintenance;

import com.fitbank.common.properties.PropertiesHandler;
import com.fitbank.dto.management.Detail;
import com.fitbank.processor.maintenance.MaintenanceCommand;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Envía un Detail al Sistema de Notificacion.
 * 
 * @author FitBank
 * @version 2.0
 */
public class SendDetailToFitNotification extends MaintenanceCommand {

    
    @Override
    public Detail executeNormal(Detail pDetail) throws Exception {
        if(pDetail.findFieldByName("FITBANK")!=null && pDetail.findFieldByName("FITBANK").getIntegerValue()==1){
            this.sendDetail(pDetail);
        }
        return pDetail;
    }

    
    public void sendDetail(Detail pDetail) throws Exception {
        PropertiesHandler properties = new PropertiesHandler("notifmobile");
        String server = properties.getStringValue("notification.server");
        Integer port = properties.getIntValue("notification.port");
        Socket client = new Socket( InetAddress.getByName( server ), port );
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        output.writeObject(pDetail.toXml());
        output.flush();
        client.close();
        output.close();
    }
    
    @Override
    public Detail executeReverse(Detail pDetail) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
