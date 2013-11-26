package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.persistence.tablas.TAutorizaciones;
import com.fitnotif.persistence.tablas.TLogAutorizacion;
import com.fitnotif.persistence.tablas.TTipoAutorizaciones;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.util.StatusTypes;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.hibernate.Query;


/**
 * Clase que permite obtener informacion de la tabla TAUTORIZACIONES
 * @author malgia
 * @version 1.0
 */
public final class AuthorizationHelper {
    
    private static final String HQL_AUTHORIZATIONS = "from com.fitnotif.persistence.tablas.TAutorizaciones t where "
            + "t.fkcusuario=:user "
            + "and t.fk2fhasta=:expireDate "
            + "and t.fkcestatusautorizacion=:status";
      
    private static final String HQL_ALLAUTHORIZATIONS = "from com.fitnotif.persistence.tablas.TAutorizaciones t where "
            + "t.fk2fhasta=:expireDate "
            + "and t.fkcestatusautorizacion=:status";
    
    private static final String HQL_AUTHORIZATION = "from com.fitnotif.persistence.tablas.TAutorizaciones t where "
            + "t.numeromensaje=:messageNumber "
            + "and t.fk2fhasta=:expireDate";
    
    private static final String HQL_AUTHORIZATION_TYPE = "from com.fitnotif.persistence.tablas.TTipoAutorizaciones t where "
            + "t.ctipoautorizacion=:auth_type";
    
    private static final String HQL_XMLAUTHORIZATION = "from com.fitnotif.persistence.tablas.TLogAutorizacion t where "
            + "t.numeromensaje=:messageNumber "
            + "and t.cestatusautorizacion=:status";
    
    private static final String HQL_ERROR = " and t.mensaje is not null";
    
    private static final String EXPIRE_DATE = "expireDate";
    
    private static final String STATUS = "status";
    
    private AuthorizationHelper(){}
    
    /**
     * Método que devuelve todas las autorizaciones pendientes de un usuario
     * @param username
     * @return 
     */
    public static Iterator getAuthorizations(String username){
        Query qry=HibernateUtil.createQuery(HQL_AUTHORIZATIONS);
        qry.setString("user", username);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        qry.setString(STATUS, StatusTypes.NEW.getType());
        return qry.iterate();
    }
    
    /**
     * Método que devuelve todas las autorizaciones pendientes
     * @param username
     * @return 
     */
    public static Iterator getAuthorizations(){
        Query qry=HibernateUtil.createQuery(HQL_ALLAUTHORIZATIONS);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        qry.setString(STATUS, StatusTypes.NEW.getType());
        return qry.iterate();
    }
    
    /**
     * Método que devuelve todas las autorizaciones pendientes que tienen un mensaje de error
     * @param username
     * @return 
     */
    public static Iterator getErrorAuthorizations(){
        Query qry=HibernateUtil.createQuery(HQL_ALLAUTHORIZATIONS+HQL_ERROR);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        qry.setString(STATUS, StatusTypes.NEW.getType());
        return qry.iterate();
    }
    
    /**
     * Método que devuelve una autorización de acuerdo a su id (numeromensaje)
     * @param messageNumber
     * @return 
     */
    public static TAutorizaciones getAuthorization(String messageNumber){
        Query qry=HibernateUtil.createQuery(HQL_AUTHORIZATION);
        qry.setString("messageNumber", messageNumber);
        qry.setTimestamp(EXPIRE_DATE, DatesHelper.getExpireTimeStamp());
        return (TAutorizaciones) qry.uniqueResult();
    }
    
    /**
     * Método que devuelve un registro que contiene toda la información relacionada
     * al tipo de autorizacion
     * @param authorizationType
     * @return 
     */
    public static TTipoAutorizaciones getAuthorizationType(String authorizationType){
        Query qry=HibernateUtil.createQuery(HQL_AUTHORIZATION_TYPE);
        qry.setString("auth_type", authorizationType);
        return (TTipoAutorizaciones) qry.uniqueResult();
    }
    
    /**
     * Método que caduca una autorización
     * @param tautorizaciones 
     */
    public static void expire(TAutorizaciones tautorizaciones){
        TAutorizaciones autorizaciones=(TAutorizaciones)tautorizaciones.cloneMe();
        autorizaciones.setFk2fhasta(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.save(autorizaciones);
    }
    
    /**
     * Método que almacena la notificación en la base de datos
     * @param not
     * @param origen 0:Pedido, 1:Respuesta
     * @throws Exception 
     */
    public static void saveXMLAuthorization(Notification not, Integer origen){
        try {
            TLogAutorizacion tlog=new TLogAutorizacion();
            tlog.setCestatusautorizacion(not.getStatus());
            tlog.setNumeromensaje(not.getId());
            Boolean orig=origen==1?true:false;
            tlog.setOrigenMensaje(orig);
            tlog.setMensajeXml(not.toXml().getBytes());
            HibernateUtil.save(tlog);
        } catch (Exception ex) {
            throw new NotificationException("NOT001", "NO SE PUDO GUARDAR EL ARCHIVO XML", ex);
        }
    } 
    
/**
     * Método que almacena el Detail en la base de datos
     * @param not
     * @param origen 0:Pedido, 1:Respuesta
     * @throws Exception 
     */
    public static void saveXMLDetail(String messageId, String detail, Integer origen){
        try {
            TLogAutorizacion tlog=new TLogAutorizacion();
            tlog.setCestatusautorizacion("DET");
            tlog.setNumeromensaje(messageId);
            Boolean orig=origen==1?true:false;
            tlog.setOrigenMensaje(orig);
            tlog.setMensajeXml(detail.getBytes());
            HibernateUtil.save(tlog);
        } catch (Exception ex) {
            throw new NotificationException("NOT001", "NO SE PUDO GUARDAR EL ARCHIVO XML", ex);
        }
    }     
    
    /**
     * Método que devuelve el XML de una Notification de acuerdo a su número de mensaje
     * y estado.
     * @param messageNumber
     * @param status
     * @return 
     */
    public static String getXMLNotification(String messageNumber, String status){
        Query qry=HibernateUtil.createQuery(HQL_XMLAUTHORIZATION);
        qry.setString("messageNumber", messageNumber);
        qry.setString(STATUS, status);
        TLogAutorizacion aut = (TLogAutorizacion) qry.uniqueResult();  
        String resp = "";
        if(aut!=null){
            try {
                resp = new String(aut.getMensajeXml(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new NotificationException(ex);
            }
        }
        return resp;
    }

    /**
     * Método que cambia el estado de una autorización.
     * @param messageNumber
     * @param status 
     */
    
    public static void updateStatus(String messageNumber, String status){
        TAutorizaciones aut = AuthorizationHelper.getAuthorization(messageNumber);
        TAutorizaciones naut = (TAutorizaciones) aut.cloneMe();
        aut.setFkcestatusautorizacion(status);
        aut.setFdesde(DatesHelper.getCurrentTimeStamp());
        if(status.compareTo(StatusTypes.APPROVED.getType())==0 || status.compareTo(StatusTypes.DENIED.getType())==0){   
            aut.setFproceso(DatesHelper.getCurrentTimeStamp());
        }
        naut.setFk2fhasta(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.save(naut);
        HibernateUtil.saveOrUpdate(aut);
    }
    
    /**
     * Método que graba un mensaje de error en una autorizacion
     * @param messageNumber
     * @param message 
     */
    public static void saveErrorMessage(String messageNumber, String message){
        TAutorizaciones aut = AuthorizationHelper.getAuthorization(messageNumber);
        if(aut!=null){
            TAutorizaciones naut = (TAutorizaciones) aut.cloneMe();
            aut.setFdesde(DatesHelper.getCurrentTimeStamp());
            aut.setMensaje(message);
            naut.setFk2fhasta(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(naut);
            HibernateUtil.saveOrUpdate(aut);
        }
    }
}
