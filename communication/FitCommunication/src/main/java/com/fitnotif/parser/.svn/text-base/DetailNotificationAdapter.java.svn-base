package com.fitnotif.parser;

import com.fitnotif.dto.Detail;
import com.fitnotif.dto.Field;
import com.fitnotif.dto.Record;
import com.fitnotif.dto.Table;
import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.emuntypes.AuthorizationPages;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


/**
 * Clase que permite homologar Detail con Notification
 * @author malgia
 */
public class DetailNotificationAdapter{
    
    private Notification notification;
    private Detail detail;
    
    /**
     * Constructor de la clase
     * @param xml
     * @param type 1 si es Detail, 2 si es Notification
     * @throws IOException 
     */
    public DetailNotificationAdapter(String xml, Integer type) throws IOException{
        if(type==1){
            DetailParser dp=new DetailParser(xml);
            this.detail=new Detail(dp);
            this.notification=new Notification();
        }
        if (type==2){
            NotificationParser np=new NotificationParser(xml);
            this.notification=new Notification(np);
            this.detail=new Detail();
        }
    }
    
    /**
     * Método que transforma un Detail en Notification
     * @param det
     * @return 
     */
    public Notification detailToNotification(){
        this.fillNotification();
        return this.notification;
    }
    
    /**
     * Método que transforma un Notification en Detail
     * @param det
     * @return 
     */
    public Detail notificationToDetail(){
        this.fillDetail();
        return this.detail;
    }    
    
    /**
     * Método que llena un Notification
     */
    private void fillNotification(){
        this.fillHeaderNotification();
        this.fillBodyNotification();
        this.fillControlTable();
    }
    
    /**
     * Método que llena un Detail
     */
    private void fillDetail(){
        this.fillHeaderDetail();
        this.fillBodyDetail();
        this.fillControlFields();
    }    
    
    /**
     * Método que llena la cabecera de Notification
     */
    private void fillHeaderNotification(){
        if(detail.findFieldByName("MessageId")!=null){
            this.notification.setId(detail.findFieldByName("MessageId").getStringValue());
        }else{
            this.notification.setId(detail.findFieldByName("BPMInstanceName")!=null?detail.findFieldByName("BPMInstanceName").getStringValue():"");
        }
        this.notification.setSid(detail.getSessionid());
        this.notification.setIp(detail.getIpaddress());
        this.notification.setDevice("");
        this.notification.setUser(detail.findFieldByName("_USER_NOTIFY")!=null?detail.findFieldByName("_USER_NOTIFY").getStringValue():"");
        this.notification.setOriginUser(detail.findFieldByName("_ORIGIN_USER")!=null?detail.findFieldByName("_ORIGIN_USER").getStringValue():"");
        this.fillAuthInformation();
        this.notification.setDate(new Timestamp(detail.getAccountingDate().getTime()));
        this.notification.setPassword("");
        this.notification.setNewPassword("");
        this.notification.setStatus("");
        this.notification.setStacktrace("");       
    }   
    
    /**
     * Metodo que guarda la información propia de la autorizacion
     */
    private void fillAuthInformation(){
        this.notification.setType(detail.findFieldByName("_ORIGIN_SUBSYSTEM")!=null?detail.findFieldByName("_ORIGIN_SUBSYSTEM").getStringValue():"");
        this.notification.setTransaction(detail.findFieldByName("_ORIGIN_TRANSACTION")!=null?detail.findFieldByName("_ORIGIN_TRANSACTION").getStringValue():"");
        this.notification.setName(detail.findTableByName(AuthorizationPages.CONSOLIDATED.getPage())!=null?detail.findTableByName(AuthorizationPages.CONSOLIDATED.getPage()).findRecordByNumber(0).findFieldByName("ASUNTO").getStringValue():"");
    }
    
    /**
     * Método que llena la cabecera de Detail
     */
    private void fillHeaderDetail(){
        this.detail.findFieldByNameCreate("_USER_NOTIFY").setValue(this.notification.getUser());
        this.detail.findFieldByNameCreate("_ORIGIN_SUBSYSTEM").setValue(this.notification.getType());
        this.detail.findFieldByNameCreate("_ORIGIN_TRANSACTION").setValue(this.notification.getTransaction());
        this.detail.findFieldByNameCreate("_ORIGIN_USER").setValue(this.notification.getOriginUser());
    }    
    
    /**
     * Método que llena el cuerpo de Notification
     */
    private void fillBodyNotification(){
        for(Table table:this.detail.getTables()){
            this.notification.addPage(this.createPageFromTable(table.getName()));        
        }
    }
    
    /**
     * Método que llena el cuerpo del Detail
     */
    private void fillBodyDetail(){
        for(Page page:this.notification.getPages()){
            if(page.getId().compareTo("CONTROL")!=0){
                this.detail.addTable(this.createTableFromPage(page.getId()));
            }
        }
    }  
    
    /**
     * Método que llena los campos de control en una Tabla de la Notificacion
     */
    private void fillControlTable(){
        this.addControlFieldForNotifcaton("_MAILMESSAGE");
        this.addControlFieldForNotifcaton("SECAUTORIZACION");
        this.addControlFieldForNotifcaton("FITBANK");
        this.addControlFieldForNotifcaton("_TRNSTATUS");
    }
    
    /**
     * Método que llena los campos en  el Detail
     */
    private void fillControlFields(){
        this.addControlFieldForDetail("_MAILMESSAGE");
        this.addControlFieldForDetail("SECAUTORIZACION");
    }    
    
    /**
     * Método que agrega un campo de control en un Notification
     * @param origin
     * @param destiny 
     */
    
    private void addControlFieldForNotifcaton(String fieldName){
        if(this.detail.findFieldByName(fieldName)!=null){
            this.notification.addControlField(fieldName, this.detail.findFieldByName(fieldName).getStringValue());
        }
    }
    
    /**
     * Método que agrega un campo de control en un Detail
     * @param origin
     * @param destiny 
     */
    
    private void addControlFieldForDetail(String fieldName){
        if(this.notification.getControlFieldValueByName(fieldName)!=null){
            this.detail.findFieldByNameCreate(fieldName).setValue(this.notification.getControlFieldValueByName(fieldName));
        }
    }    
    
    /**
     * Método que crea un Page en base al nombre de un Table
     * @param tableName
     * @return 
     */
    private Page createPageFromTable(String tableName){
        Page page=new Page(tableName);
        Table table;
        table=this.detail.findTableByName(tableName);
        if(table!=null){
            Iterable<Record> records= table.getRecords();
            for(Record record:records){
                page.addRegister(this.createRegisterFromRecord(record));
            }
        }
        return page;
    }
    
/**
     * Método que crea un Table en base al nombre de un Page
     * @param tableName
     * @return 
     */
    private Table createTableFromPage(String pageName){
        Table table=new Table(pageName, pageName);
        Page page=this.notification.getPageById(pageName);      
        if(page!=null){
            List<Register> registers=page.getRegisters();
            for(Register register:registers){
                table.addRecord(this.createRecordFromRegister(register));
            }
        }
        return table;
    }    
    
    /**
     * Método que crea un Register dado un Record
     * @param record
     * @return 
     */
    private Register createRegisterFromRecord(Record record){
        Register register=new Register(record.getNumber().toString());
        List<Field> fields=record.getFields();
        for(Field field:fields){
            Column column=new Column(field.getName(),field.getStringValue());
            register.addColumn(column);
        }
        return register;
    }
    
    /**
     * Método que crea un Record dado un Register
     * @param record
     * @return 
     */
    private Record createRecordFromRegister(Register register){
        Record record=new Record(Integer.parseInt(register.getId()));
        List<Column> columns=register.getColumns();
        for(Column column:columns){
            Field field=new Field(column.getName(),column.getValue());
            record.addField(field);
        }
        return record;
    }    
}
