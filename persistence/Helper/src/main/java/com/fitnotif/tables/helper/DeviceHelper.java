package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.persistence.tablas.TUsuarioDispositivos;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.util.DeviceStatus;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;


/**
 * Clase que permite obtener informacion de la tabla TUSUARIODISPOSITIVOS
 * @author malgia
 * @version 1.0
 */
public final class DeviceHelper {
    
    private DeviceHelper(){}
    
    private static final String TABLE = "com.fitnotif.persistence.tablas.TUsuarioDispositivos t ";
    private static final String EXPIRE = "and t.fhasta=:expireDate ";
    
    private static final String HQL_COUNT_DEVICES = "select count(*) from "
            + TABLE
            + "where t.fkcusuario=:user "
            + EXPIRE
            + "and t.estado not in ('" 
            + DeviceStatus.DELETED.getType()
            + "')";
  
    private static final String HQL_GET_DEVICE = "from "
            + TABLE
            + "where t.id=:ip "
            + "and t.fkcusuario=:user "
            + "and t.modelo=:device "
            + EXPIRE
            + "and t.estado not in ('"
            + DeviceStatus.DELETED.getType()
            + "')";
    
    private static final String HQL_DELETED_DEVICE = "from "
            + TABLE
            + "where t.id=:ip "
            + "and t.fkcusuario=:user "
            + "and t.modelo=:device "
            + EXPIRE
            + "and t.estado in ('"
            + DeviceStatus.DELETED.getType()
            + "')";
    
    private static final String HQL_GET_DEVICES = "from "
            + TABLE
            + "where t.fkcusuario=:user "
            + EXPIRE
            + "and t.estado not in ('"
            + DeviceStatus.DELETED.getType()
            + "') "
            + "order by t.fconexion";
    
    private static final String USER="user";
    
    private static final String EXPIRE_DATE="expireDate";
    
    /**
     * Método que devuelve el número de dispositivos que tiene registrados el usuario.
     * No cuenta los dispositivos eliminados.
     * @param user
     * @return 
     */
    public static Integer countDevices(String user){
        Query qry = HibernateUtil.createQuery(HQL_COUNT_DEVICES);
        qry.setString(USER, user);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        return Integer.parseInt(qry.uniqueResult().toString());
    }
    
    /**
     * Método que devuelve un dispositivo de acuerdo al usuario y a la ip.
     * Solo dispositivos que no estan eliminados.
     * @param user
     * @return 
     */
    public static TUsuarioDispositivos getDevice(String user, String ip, String device){
        Query qry = HibernateUtil.createQuery(HQL_GET_DEVICE);
        qry.setString(USER, user);
        qry.setString("ip", ip);
        qry.setString("device", device);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        return (TUsuarioDispositivos) qry.uniqueResult();
    }    
    
    /**
     * Método que devuelve los dispositivos de un usuario que no estan eliminados.
     * @param user
     * @return 
     */
    public static ScrollableResults getDevices(String user){
        Query qry = HibernateUtil.createQuery(HQL_GET_DEVICES);
        qry.setString(USER, user);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        return qry.scroll();
    }    
    
    /**
     * Método que expira un dispositivo
     * @param user
     * @return 
     */
    public static void expireDevice(String user, String ip, String device){
        TUsuarioDispositivos userDevice=getDevice(user, ip, device);
        if(userDevice!=null){
            TUsuarioDispositivos expiredDevice=(TUsuarioDispositivos)userDevice.cloneMe();
            userDevice.setEstado(DeviceStatus.DELETED.getType());
            HibernateUtil.saveOrUpdate(userDevice);
            expiredDevice.setFhasta(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(expiredDevice);
        }
    }   
    
    /**
     * Método que expira todos los dispositivos de un usuario
     * @param user
     * @return 
     */
    public static void expireDevices(String user){
        Query qry = HibernateUtil.createQuery(HQL_GET_DEVICES);
        qry.setString(USER, user);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        Iterator userDevices = qry.iterate();
        while (userDevices.hasNext()){
            TUsuarioDispositivos userDevice= (TUsuarioDispositivos) userDevices.next();
            userDevice.setEstado(DeviceStatus.DELETED.getType());
            HibernateUtil.saveOrUpdate(userDevice);
        }
    }      
    
    /**
     * Método que devuelve dispositivos eliminados. 
     * @param user
     * @param ip
     * @param device 
     */
    public static TUsuarioDispositivos getDeletedDevice(String user, String ip, String device){
        Query qry = HibernateUtil.createQuery(HQL_DELETED_DEVICE);
        qry.setString(USER, user);
        qry.setString("ip", ip);
        qry.setString("device", device);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        return (TUsuarioDispositivos) qry.uniqueResult();
    }
    
    /**
     * Método que agrega un nuevo dispositivo en la BD.
     * Si ese dispositivo fue eliminado en el pasado actualiza su estado.
     * @param username
     * @param ip
     * @param device 
     */
    public static void saveNewDevice(String username, String ip, String device){
        TUsuarioDispositivos deletedDevice = DeviceHelper.getDeletedDevice(username, ip, device);
        if(deletedDevice!=null){
            TUsuarioDispositivos oldDevice = (TUsuarioDispositivos) deletedDevice.cloneMe();
            oldDevice.setFhasta(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(oldDevice);
            deletedDevice.setEstado(DeviceStatus.ACTIVE.getType());
            deletedDevice.setFdesde(DatesHelper.getCurrentTimeStamp());
            deletedDevice.setFconexion(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.saveOrUpdate(deletedDevice);
        }else{
            TUsuarioDispositivos newUserDevice = new TUsuarioDispositivos(ip,username,device,DatesHelper.getExpireTimeStamp());
            newUserDevice.setEstado(DeviceStatus.ACTIVE.getType());
            newUserDevice.setFdesde(DatesHelper.getCurrentTimeStamp());
            newUserDevice.setFconexion(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(newUserDevice);
        }
    }
    
    /**
     * Devuelve el ultimo dispositivo que se conecto a la aplicacion
     * @param user
     * @return 
     */
    public static TUsuarioDispositivos lastLoggedDevice(String user){
        TUsuarioDispositivos userDevice = null;
        Query qry = HibernateUtil.createQuery(HQL_GET_DEVICES+" desc");
        qry.setString(USER, user);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        ScrollableResults devices=qry.scroll();
        if(devices.next()){
            userDevice=(TUsuarioDispositivos) devices.get()[0];
        }
        return userDevice;
    }
}
