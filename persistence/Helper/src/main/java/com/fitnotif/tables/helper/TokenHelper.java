package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.persistence.tablas.TTokens;
import org.hibernate.Query;


/**
 * Clase que permite obtener informacion de la tabla TTOKENS
 * @author malgia
 * @version 1.0
 */
public final class TokenHelper {
    
    private static final String HQL_TOKEN = "from com.fitnotif.persistence.tablas.TTokens t "
            + "where t.token=:token";
      
    
    private static final String HQL_AUTH = "select t.fktoken "
            + "from com.fitnotif.persistence.tablas.TAutorizaciones t "
            + "where t.numeromensaje=:messageNumber "
            + "and t.fk2fhasta=:expireDate";
    
    private TokenHelper(){}
    /**
     * Método que devuelve un token de acuerdo a su id (token).
     * @param token
     * @return 
     */
    public static TTokens getToken(String token){
        Query qry = HibernateUtil.createQuery(HQL_TOKEN);
        qry.setString("token", token);
        return (TTokens) qry.uniqueResult();
    }
    
    /**
     * Clase que devuelve el token de la autorización.
     * @param messageNumber
     * @return 
     */
    public static TTokens getTokenByAuthorizationId(String messageNumber){
        Query qry = HibernateUtil.createQuery(HQL_AUTH);
        qry.setString("messageNumber", messageNumber);
        qry.setTimestamp("expireDate", DatesHelper.getExpireTimeStamp());
        String token = (String) qry.uniqueResult();
        return TokenHelper.getToken(token);
    }
    
}
