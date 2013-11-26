package com.fitbank.general.maintenance;

import com.fitbank.common.ApplicationDates;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.common.properties.PropertiesHandler;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Field;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.hb.persistence.person.Taddressperson;
import com.fitbank.hb.persistence.person.Tperson;
import com.fitbank.hb.persistence.safe.Tuser;
import com.fitbank.processor.maintenance.MaintenanceCommand;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import org.hibernate.ScrollableResults;

/**
 * Pone en el Detail la información del usuario que se tiene que enviar
 * al Sistema de Notificacion.
 * 
 * @author FitBank
 * @version 2.0
 */
public class FillUsersInformationForNotification extends MaintenanceCommand {

    static final String EXPIRE="expireDate";
    
    static final String HQL_USER="from com.fitbank.hb.persistence.safe.Tuser t "
            + "where t.pk.cusuario=:user "
            + "and t.pk.fhasta=:expireDate";
    
    static final String HQL_USERS="from com.fitbank.hb.persistence.safe.Tuser t "
            + "where t.pk.fhasta=:expireDate "
            + "and t.esoficial=1";
    
    static final String HQL_ADDRESS="from com.fitbank.hb.persistence.person.Taddressperson t "
            + "where t.pk.cpersona=:person "
            + "and t.pk.fhasta=:expireDate "
            + "and t.ctipodireccion='CE'";
    
    static final String HQL_PERSON="from com.fitbank.hb.persistence.person.Tperson t "
            + "where t.pk.cpersona=:person "
            + "and t.pk.fhasta=:expireDate";
    
        @Override
	public Detail executeNormal(Detail pDetail) throws Exception {
                Detail det=pDetail.cloneMe();
                det.removeTables();
                Table table=new Table("USUARIOS", "USUARIOS");
                if(pDetail.findFieldByName("TODOS")!=null){
                    Integer sendAll=pDetail.findFieldByName("TODOS").getIntegerValue();
                    Integer i=1;
                    if(sendAll==0){
                        for(Record record:pDetail.findTableByName("TUSUARIOS").getRecords()){ 
                            String usuario=record.findFieldByName("CUSUARIO").getStringValue();
                            table.addRecord(addUser(usuario, i));
                            i++;
                        }
                    }else{
                        table=fillAllUsers(table);
                    }
                }else{
                    Integer i=1;
                    for(Record record:pDetail.findTableByName("TUSUARIOS").getRecords()){ 
                        if(record.findFieldByName("ESOFICIAL").getIntegerValue()==1){
                            String usuario=record.findFieldByName("CUSUARIO").getStringValue();
                            if(record.findFieldByName("FHASTA")!=null){
                                table.addRecord(addUser(usuario, i));
                            }else{
                                Integer cpersona=record.findFieldByName("CPERSONA").getIntegerValue();
                                table.addRecord(addNewUser(usuario,cpersona,i));
                            }
                            i++;
                        }
                    }
                }
                det.addTable(table);
                this.sendDetail(det);
                return pDetail;
	}

        
    private Tuser getUserByName(String user){
        UtilHB utilHB=new UtilHB();
        utilHB.setSentence(HQL_USER);
        utilHB.setString("user", user);
        utilHB.setTimestamp(EXPIRE, ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return (Tuser) utilHB.getObject();
    }
    
    private Taddressperson getAddressByPerson(Integer person){
        UtilHB utilHB=new UtilHB();
        utilHB.setSentence(HQL_ADDRESS);
        utilHB.setInteger("person", person);
        utilHB.setTimestamp(EXPIRE, ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return (Taddressperson) utilHB.getObject();
    }    
    
    private Tperson getPerson(Integer person){
        UtilHB utilHB=new UtilHB();
        utilHB.setSentence(HQL_PERSON);
        utilHB.setInteger("person", person);
        utilHB.setTimestamp(EXPIRE, ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return (Tperson) utilHB.getObject();
    }    
    
    private Table fillAllUsers(Table table){
        UtilHB utilHB=new UtilHB();
        utilHB.setSentence(HQL_USERS);
        utilHB.setTimestamp(EXPIRE, ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        ScrollableResults users=utilHB.getScroll();
        Integer i=1;
        while(users.next()){
            Tuser tusuario=(Tuser) users.get(0);
            String usuario=tusuario.getPk().getCusuario();
            table.addRecord(addUser(usuario, i));
            i++;
        }
        return table;
    }
    
    private Record addUser(String usuario, Integer i){
        Record rusuario=new Record(i);
        Integer cpersona=this.getUserByName(usuario).getCpersona();
        Field fusuario=new Field("USUARIO", usuario);
        rusuario.addField(fusuario);
        String email=this.getAddressByPerson(cpersona)!=null?this.getAddressByPerson(cpersona).getDireccion():"";        
        Field femail=new Field("EMAIL", email);
        rusuario.addField(femail);  
        String nombre=this.getPerson(cpersona)!=null?this.getPerson(cpersona).getNombrelegal():"";        
        Field fnombre=new Field("NOMBRE", nombre);
        rusuario.addField(fnombre);
        return rusuario;
    }
    
    private Record addNewUser(String usuario, Integer cpersona, Integer i){
        Record rusuario=new Record(i);
        Field fusuario=new Field("USUARIO", usuario);
        rusuario.addField(fusuario);
        String email=this.getAddressByPerson(cpersona)!=null?this.getAddressByPerson(cpersona).getDireccion():"";        
        Field femail=new Field("EMAIL", email);
        rusuario.addField(femail);  
        String nombre=this.getPerson(cpersona)!=null?this.getPerson(cpersona).getNombrelegal():"";        
        Field fnombre=new Field("NOMBRE", nombre);
        rusuario.addField(fnombre);
        return rusuario;
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
