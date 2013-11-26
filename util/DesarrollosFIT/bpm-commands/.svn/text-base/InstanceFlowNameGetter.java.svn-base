
package com.fitbank.bpm.maintenance;

import com.fitbank.common.Helper;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.dto.management.Detail;
import com.fitbank.hb.persistence.gene.Tflowinstance;
import com.fitbank.processor.maintenance.MaintenanceCommand;

/**
 *
 * @author BANTEC inc.
 */
public class InstanceFlowNameGetter extends MaintenanceCommand {

    private static String HQL_MESSAGE = "from com.fitbank.hb.persistence.gene.Tflowinstance t "
            + "where t.nombreinstancia = :instanceName";
    
    @Override
    public Detail executeNormal(Detail pDetail) throws Exception {
        if(pDetail.findFieldByName("BPMInstanceName").getStringValue().indexOf('.')==-1){
            String messageId = pDetail.findFieldByName("BPMInstanceName").getStringValue();
            Tflowinstance flowInstance = (Tflowinstance)Helper.getSession().get(Tflowinstance.class,messageId);
            pDetail.findFieldByNameCreate("BPMInstanceName").setValue(flowInstance.getNombreinstancia());
        }else{
            String instanceName = pDetail.findFieldByName("BPMInstanceName").getStringValue();
            pDetail.findFieldByNameCreate("MessageId").setValue(this.getMessageId(instanceName));
        }
        return pDetail;
    }

    private String getMessageId(String instanceName){
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_MESSAGE);
        utilHB.setString("instanceName", instanceName);
        Tflowinstance flowInstance = (Tflowinstance) utilHB.getObject();
        return flowInstance.getPk();
    }
    
    @Override
    public Detail executeReverse(Detail pDetail) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
