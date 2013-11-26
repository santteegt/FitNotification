package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.persistence.tablas.TSesiones;
import com.fitnotif.persistence.util.HibernateUtil;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;


/**
 * Clase que permite obtener informacion de la tabla TSESIONES
 * @author malgia
 * @version 1.0
 */
public final class SessionHelper {
    
    
    private static final String HQL_SESSION = "from com.fitnotif.persistence.tablas.TSesiones t "
            + "where t.cusuario=:user "
            + "and t.sesionid=:sid";
        
    private static final String HQL_ACTIVE_SESSIONS = "from com.fitnotif.persistence.tablas.TSesiones t "
            + "where t.ffin is null";
    
    private SessionHelper(){}
    /**
     * Método que devuelve una sesión
     * @param user
     * @return 
     */
    public static TSesiones getSession(String user, String sid){
        TSesiones session = null;
        if(user!=null && sid!=null){
            Query qry = HibernateUtil.createQuery(HQL_SESSION);
            qry.setString("user", user);
            qry.setString("sid", sid);
            Object result = qry.uniqueResult();
            if(result!=null){
                session = (TSesiones) result;
            }
        }
        return session;       
    }
    
    /**
     * Método que graba una nueva sesión en la BD.
     * @param user
     * @param sid 
     */
    public static void saveSession(String user, String sid){
        TSesiones sesion=new TSesiones();
        if(user==null || sid==null){
            throw new NotificationException("SID001", "NO SE PUDO ALMACENAR LA SESION.");
        }
        sesion.setCusuario(user);
        sesion.setSesionid(sid);
        sesion.setFinicio(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.saveOrUpdate(sesion);
    }
    
    /**
     * Método que expira la sesión.
     * @param sesion 
     */
    public static void expireSession(TSesiones sesion){
        sesion.setFfin(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.saveOrUpdate(sesion);
    }
    
    /**
     * Método que extiende la sesión
     */
    public static void prolongSession(TSesiones sesion){
        sesion.setFinicio(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.saveOrUpdate(sesion);
    }
    
    /**
     * Método que devuelve las sesiones activas
     * @param user
     * @return 
     */
    public static ScrollableResults getActiveSessions(){
        Query qry = HibernateUtil.createQuery(HQL_ACTIVE_SESSIONS);
        return qry.scroll();                
    }
    
    /**
     * Método que expira todas las sesiones activas de un usuario
     */
    
    public static void expireUserActiveSessions(String user){
        Iterator<TSesiones> activeSessions=UserHelper.getActiveSessions(user).iterator();
        while(activeSessions.hasNext()){
            TSesiones activeSession=activeSessions.next();
            SessionHelper.expireSession(activeSession);
        }
    }
}
