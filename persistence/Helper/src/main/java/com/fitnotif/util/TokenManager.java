package com.fitnotif.util;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.tables.helper.TokenHelper;
import com.fitnotif.persistence.tablas.TTokens;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.ParameterHelper;
import java.sql.Timestamp;

public final class TokenManager{
     
    private TokenManager(){}
    
    public static void saveToken(String token){
        TTokens ttoken=new TTokens();
        ttoken.setToken(token);
        ttoken.setFdesde(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.save(ttoken);
    }
    

    /**
     * Devuelve true si el token todavía es válido. 
     */
    public static Boolean checkTokenTimeout(String token){
        Integer timeout=0;
        Timestamp creationDate=null;
        try {
            TTokens ttoken=TokenHelper.getToken(token);
            timeout=ParameterHelper.getIntegerValue("token-timeout");
            creationDate=ttoken.getFdesde();
        } catch (Exception ex) {
            throw new NotificationException("TKN001", "NO SE PUDO VERIFICAR EL ESTADO DEL TOKEN", ex);
        }
        if(DatesHelper.getCurrentTimeStamp().compareTo(new Timestamp(creationDate.getTime()+timeout*60*1000))>0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Método que expira un token.
     * @param token 
     */
    
    public static void expireToken(String token){     
        Integer timeout=ParameterHelper.getIntegerValue("token-timeout");
        timeout = timeout*60*1000;
        Timestamp expired=DatesHelper.getAddedTimeStamp(-timeout);
        TTokens ttoken=TokenHelper.getToken(token);
        ttoken.setFdesde(expired);
        HibernateUtil.saveOrUpdate(ttoken);
    }
}
