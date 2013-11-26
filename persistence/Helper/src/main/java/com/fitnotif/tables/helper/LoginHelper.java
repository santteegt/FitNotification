package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.persistence.tablas.TUsuarioPassword;
import com.fitnotif.persistence.util.HibernateUtil;
import org.hibernate.Query;


/**
 * Clase que permite obtener informacion de la tabla TUSUARIOPASSWORD
 * @author malgia
 * @version 1.0
 */
public final class LoginHelper {
    
    private static final String HQL_PASSWORD = "from com.fitnotif.persistence.tablas.TUsuarioPassword t where "
            + "t.fkcusuario=:user "
            + "and t.fhasta=:expireDate";
      
    private LoginHelper(){}
    
    /**
     * Método que devuelve todo el registro de la tabla TUSUARIOPASSWORD para un usuario
     * @param username
     * @return 
     */
    public static TUsuarioPassword getUserPassword(String username){
        Query qry=HibernateUtil.createQuery(HQL_PASSWORD);
        qry.setString("user", username);
        qry.setTimestamp("expireDate", DatesHelper.getExpireTimeStamp());
        return (TUsuarioPassword) qry.uniqueResult();
    }
    
    /**
     * Método que caduca una contraseña
     * @param tautorizaciones 
     */
    public static void expirePassword(TUsuarioPassword tusuariopassword){
        TUsuarioPassword expired=(TUsuarioPassword) tusuariopassword.cloneMe();
        expired.setFhasta(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.save(expired);
    }
}
