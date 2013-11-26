package com.fitbank.bpm.query;

import com.fitbank.common.ApplicationDates;
import com.fitbank.common.BeanManager;
import com.fitbank.common.Helper;
import com.fitbank.common.exception.FitbankException;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.common.properties.PropertiesHandler;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Field;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.hb.persistence.gene.Tarea;
import com.fitbank.hb.persistence.gene.TareaKey;
import com.fitbank.hb.persistence.gene.Tflowauthorization;
import com.fitbank.hb.persistence.gene.TflowauthorizationKey;
import com.fitbank.hb.persistence.loc.Tbranch;
import com.fitbank.hb.persistence.loc.TbranchKey;
import com.fitbank.hb.persistence.loc.Toffice;
import com.fitbank.hb.persistence.loc.TofficeKey;
import com.fitbank.hb.persistence.person.Tperson;
import com.fitbank.hb.persistence.person.TpersonKey;
import com.fitbank.hb.persistence.safe.Tuser;
import com.fitbank.hb.persistence.safe.TuserKey;
import com.fitbank.hb.persistence.trans.Transaction;
import com.fitbank.hb.persistence.trans.TransactionKey;
import com.fitbank.processor.RequestProcessorEmbedded;
import com.fitbank.processor.query.QueryCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.io.IOUtils;

/**
 * Interfaz con el servidor de Notificaciones
 * @author santiago
 * @version 2.0
 */
public class NotificationInterface extends QueryCommand{    

    
    private PropertiesHandler ph;
    
    private static final String FIELD_CONSTANT = "$FIELD$";
    
    
    @Override
    public Detail execute(Detail pDetail) throws Exception {
        if(pDetail.findFieldByName("_AUT_ESTADO").getStringValue().compareTo("AUTORIZACION")!=0){
            return pDetail;
        }
        try{
            this.getspecTransactionInfo(pDetail);
            this.getAuthorizationDetail(pDetail);
            this.getGeneralInfo(pDetail);

            PropertiesHandler properties = new PropertiesHandler("notifmobile");
            String server = properties.getStringValue("notification.server");
            Integer port = properties.getIntValue("notification.port");
            Socket client = new Socket( InetAddress.getByName( server ), port );
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(pDetail.toXml());
            output.flush();
            client.close();
            output.close();            
        }catch(IOException e){
            throw new FitbankException("FITN", "ERROR AL CONECTARSE A FITNOTIFICATION MOBILE");
        }
        return pDetail;
    }
    
    /**
     * Obtiene la informacion especifica para cada transaccion desde comandos
     * tipo N
     * @param pDetail
     * @return
     * @throws Exception 
     */
    private void getspecTransactionInfo(Detail pDetail)throws Exception{
        Detail detail = pDetail.cloneMe();
        detail.setSubsystem(pDetail.findFieldByName("_ORIGIN_SUBSYSTEM").getStringValue());
        detail.setTransaction(pDetail.findFieldByName("_ORIGIN_TRANSACTION").getStringValue());
        detail.setType("CON");
        detail.removeField("__BPM_CALL_");
        detail.addField(new Field("__NOTIFICATION_M","1"));
        detail = new RequestProcessorEmbedded(detail).process();
        for(Table table:detail.getTables()){
            pDetail.addTable(table);
        }
    }
    
    /**
     * Obtiene el detalle del flujo de autorizacion que se envia por correo
     * y lo coloca en el Detail
     * @param pDetail
     * @throws Exception 
     */
    private void getAuthorizationDetail(Detail pDetail)throws Exception{
        String user = pDetail.findFieldByNameCreate("_USER_NOTIFY").getStringValue();
        Long sequence = BeanManager.convertObject(pDetail.findFieldByName("SECAUTORIZACION").getValue(), Long.class);        
        TflowauthorizationKey key = new TflowauthorizationKey(user, sequence);
        Tflowauthorization flow = (Tflowauthorization) Helper.getSession().get(Tflowauthorization.class, key);        
        
        BufferedReader br = new BufferedReader(flow.getDetalle().getCharacterStream());
        String message = IOUtils.toString(br);     
        
        pDetail.addField(new Field("_MAILMESSAGE",message));        
    }
    
    
    /**
     * Obtiene la informacion adicional que se manda al correo para poder
     * adjuntarla al servidor de notificaciones
     * @param pDetail
     * @throws Exception 
     */
    private void getGeneralInfo(Detail pDetail)throws Exception{
        
        String subsystem = pDetail.findFieldByNameCreate("_ORIGIN_SUBSYSTEM").getStringValue();
        String transaction = pDetail.findFieldByNameCreate("_ORIGIN_TRANSACTION").getStringValue();
        String version = pDetail.findFieldByNameCreate("_ORIGIN_VERSION").getStringValue();

        String user_origin = pDetail.findFieldByNameCreate("_ORIGIN_USER").getStringValue();
        if ((user_origin == null) || (user_origin.compareTo("") == 0)) {
            user_origin = pDetail.getUser();
        }
        
        TuserKey userKey = new TuserKey(user_origin, ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        Tuser tuser = (Tuser) Helper.getSession().get(Tuser.class, userKey);
        TpersonKey key = new TpersonKey(tuser.getCpersona(), ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        Tperson person = (Tperson) Helper.getSession().get(Tperson.class, key);
        
        TbranchKey bkey = new TbranchKey(pDetail.getOriginBranch(), pDetail.getCompany(), ApplicationDates.getDefaultExpiryTimestamp());
        Tbranch tbranch = (Tbranch) Helper.getSession().get(Tbranch.class, bkey);
        
        TofficeKey okey = new TofficeKey(pDetail.getOriginOffice(), pDetail.getCompany(), ApplicationDates.getDefaultExpiryTimestamp());
        Toffice toffice = (Toffice) Helper.getSession().get(Toffice.class, okey);
        
        TareaKey akey = new TareaKey(pDetail.getLanguage(), pDetail.getCompany(), pDetail.getArea(), ApplicationDates.getDefaultExpiryTimestamp());
        Tarea tarea = (Tarea) Helper.getSession().get(Tarea.class, akey);
        
        TransactionKey tkey = new TransactionKey(pDetail.getLanguage(), subsystem, transaction, version,
                ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        Transaction trn = (Transaction) Helper.getSession().get(Transaction.class, tkey);
        
        String className = pDetail.findFieldByNameCreate("_MESSAGE").getStringValue();
        
        this.ph = new PropertiesHandler("NotificationOrders");
        String message = ph.getStringValue(className + ".DATA");
        
        Table general = new Table("E-GENERAL"," E-GENERAL");
        general.setSpecial(true);
        Record record = new Record();
        Field field = new Field("ASUNTO", ph.getStringValue(className));
        record.addField(field);
        field = new Field("USUARIO", user_origin);
        record.addField(field);
        field = new Field("NOMBRE_USUARIO", person.getNombrelegal());
        record.addField(field);
        field = new Field("SUCURSAL", tbranch.getNombre());
        record.addField(field);
        field = new Field("OFICINA", toffice.getNombre());
        record.addField(field);
        field = new Field("AREA", tarea.getNombre());
        record.addField(field);
        field = new Field("TRANSACCION ", subsystem + "-" + transaction + " " + trn.getDescripcion());
        record.addField(field);                
        
        //Obtencion de datos especificos de la notificacion
        //que esta definido en la NotificationOrders.properties
        Field data = null;
        if(message.indexOf('|')<0){
            data = new Field("DATA","");
        }else{
            data = this.getDataforGeneral(ph.getList(className + ".DATA"), pDetail);
        }
        
        record.addField(data);
        general.addRecord(record);
        pDetail.addTable(general);
    }
    
    /**
     * Obtiene informacion para aniadir a la informacion general de la notificacion
     * @param data
     * @param pDetail
     * @return
     * @throws Exception 
     */
    private Field getDataforGeneral(List<String> data, Detail pDetail)throws Exception{        
        
        if (data.indexOf("HISTORIAL|_AUTH_HISTORY") < 0) {
            data.add("HISTORIAL|_AUTH_HISTORY");
        }

        List<String> values = new ArrayList<String>();        
        StringTokenizer token = null;
        for (String s : data) {            
            token = new StringTokenizer(s, "|");
            values.add(token.nextToken() + "|" + this.getValue(token.nextToken(), pDetail));
            
        }
        
        String value = "";
        for(String s: values.toArray(new String[values.size()]) ){
            s = s.replaceAll("null", "");
            value +=  s + "-!-";            
        }
        
         return new Field("DATA",value);
    }
    
    /**
     * Obtiene los valores para los campos especificados a enviar en la notificacion
     * @param valueKey
     * @return
     * @throws Exception 
     */
    private String getValue(String valueKey, Detail pDetail) throws Exception {
        String HQL_NOMBREUSUARIO = "select a.nombrelegal" + " from Tperson a, Tuser b"
            + " where a.pk.cpersona=b.cpersona"
            + " and a.pk.fhasta=:fhasta and b.pk.fhasta=:fhasta and b.pk.cusuario=:user";
        StringTokenizer token = new StringTokenizer(valueKey, "@");
        String values = "";
        while (token.hasMoreTokens()) {
            String field = token.nextToken();
            String format = null;
            try {
                format = this.ph.getStringValue("control." + field);
            } catch (Exception e) {
            }

            Object value = "";
            if (format == null) {
                value = pDetail.findFieldByNameCreate(field).getValue();
                if (value instanceof Map) {
                    Map valor = (Map) value;
                    value = "";
                    for (Object v : valor.keySet()) {
                        UtilHB util = new UtilHB(HQL_NOMBREUSUARIO);
                        util.setString("user", BeanManager.convertObject(v, String.class));
                        util.setDate("fhasta", ApplicationDates.getDefaultExpiryDate());
                        util.setReadonly(true);
                        String persona = (String) util.getObject();
                        value = "" + value + v + " " + persona;
                    }

                }
            } else {
                String val = pDetail.findFieldByNameCreate(field).getStringValue();
                do {
                    if (format.indexOf(FIELD_CONSTANT) > -1) {
                        format = format.substring(0, format.indexOf(FIELD_CONSTANT)) + val
                                + format.substring(format.indexOf(FIELD_CONSTANT) + 7);
                    }
                } while (format.indexOf(FIELD_CONSTANT) > -1);
                value = "<a href=\"javascript:" + format + "\">" + val + "</a>";
            }
            values += value;
        }
        return values;

    }
}
