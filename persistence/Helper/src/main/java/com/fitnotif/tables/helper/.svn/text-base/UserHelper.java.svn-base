package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.persistence.tablas.TSesiones;
import com.fitnotif.persistence.tablas.TUsuarioInformacionAdicional;
import com.fitnotif.persistence.tablas.TUsuarioPassword;
import com.fitnotif.persistence.tablas.TUsuarios;
import com.fitnotif.persistence.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;


/**
 * Clase que permite obtener informacion de la tabla TUSUARIOS
 * @author malgia
 * @version 1.0
 */
public final class UserHelper {
    
    private static final String USER = "user";
    
    private static final String HQL_USER = "from com.fitnotif.persistence.tablas.TUsuarios t "
            + "where t.cusuario=:user "
            + "and t.fkfhasta=:expireDate "
            + "and t.activo=true "
            + "and :currentDate between t.fvigenciadesde and t.fvigenciahasta";
    
    private static final String HQL_USERINFO = "from com.fitnotif.persistence.tablas.TUsuarioInformacionAdicional t "
            + "where t.fkcusuario=:user "
            + "and t.fhasta=:expireDate";
    
    private static final String HQL_USERPWD = "from com.fitnotif.persistence.tablas.TUsuarioPassword t "
            + "where t.fkcusuario=:user "
            + "and t.fhasta=:expireDate";
    
    private static final String HQL_USERSID = "from com.fitnotif.persistence.tablas.TSesiones t "
            + "where t.cusuario=:user "
            + "and t.ffin is null";
    
    private static final String HQL_ACTIVE = "from com.fitnotif.persistence.tablas.TSesiones t "
            + "where t.ffin is null";    
    
    private UserHelper(){}
    
    /**
     * Método que devuelve el correo electrónico de un usuario
     * @param user
     * @return 
     */
    public static String getMailByUser(String user){
        return UserHelper.getUserInfo(user).getMail();
    }
    
    /**
     * Método que devuelve un usuario vigente de acuerdo a su id (cusuario)
     * @param user
     * @return 
     */
    public static TUsuarios getUser(String user){
        Query qry = HibernateUtil.createQuery(HQL_USER);
        qry.setString(USER, user);
        qry.setTimestamp("expireDate", DatesHelper.getExpireTimeStamp());
        qry.setTimestamp("currentDate", DatesHelper.getCurrentTimeStamp());
        return (TUsuarios) qry.uniqueResult();                
    }
    
    /**
     * Método que devuelve la información adicional de un usuario
     * @param user
     * @return 
     */
    public static TUsuarioInformacionAdicional getUserInfo(String user){
        Query qry = HibernateUtil.createQuery(HQL_USERINFO);
        qry.setString(USER, user);
        qry.setTimestamp("expireDate", DatesHelper.getExpireTimeStamp());
        return (TUsuarioInformacionAdicional) qry.uniqueResult(); 
    } 
    
    /**
     * Método que devuelve la contraseña de un usuario
     * @param user
     * @return 
     */
    public static TUsuarioPassword getUserPwd(String user){
        Query qry = HibernateUtil.createQuery(HQL_USERPWD);
        qry.setString(USER, user);
        qry.setTimestamp("expireDate", DatesHelper.getExpireTimeStamp());
        return (TUsuarioPassword) qry.uniqueResult();                
    }   
    
    /**
     * Método que verifica si un usuario tiene sesiones activas.
     * @param user
     * @return 
     */
    public static Boolean hasActiveSession(String user){
        Query qry = HibernateUtil.createQuery(HQL_USERSID);
        qry.setString(USER, user);
        return qry.list().isEmpty()?false:true;
    }
    
    /**
     * Método que devuelve las sesiones activas de un usuario.
     * @param user
     * @return 
     */
    public static List<TSesiones> getActiveSessions(String user){
        Query qry = HibernateUtil.createQuery(HQL_USERSID);
        qry.setString(USER, user);
        return qry.list();
    }    
    
    /**
     * Método que devuelve las sesiones activas
     * @param user
     * @return 
     */
    public static List<TSesiones> getActiveSessions(){
        Query qry = HibernateUtil.createQuery(HQL_ACTIVE);
        return qry.list();
    }    
}
