package com.fitnotif.common;

import java.sql.Timestamp;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase que devuelve fechas requeridas por el sistema
 * @author malgia
 * @version 1.0
 */
public final class DatesHelper {
    
    private DatesHelper(){}
    
    /**
     * Método que devuelve la fecha actual
     * @return 
     */
    public static Date getCurrentDate(){
	 return new Date(new java.util.Date().getTime());
    }
    
    /**
     * Método que devuelve la fecha actual
     * @return 
     */
    public static Timestamp getCurrentTimeStamp(){
        return new Timestamp(new java.util.Date().getTime());
    }
    
    /**
     * Método que devuelve la fecha actual sumanda (days) días
     * @param days
     * @return 
     */
    public static Timestamp getAddedTimeStamp(Integer days){
        Long addedTime=1000*3600*24*Long.valueOf(days);
        return new Timestamp(new java.util.Date().getTime()+addedTime);
    }    
    
    /**
     * Método que devuelve la fecha actual sumanda (days) días
     * @param days
     * @return 
     */
    public static Date getAddedDate(Integer days){
        Long addedTime=1000*3600*24*Long.valueOf(days);
        return new Date(new java.util.Date().getTime()+addedTime);
    }    
    
    /**
     * Método que devuelve la fecha utilizada por la base de datos para controlar caducidad
     * @return 
     */
    public static Timestamp getExpireTimeStamp(){
        Timestamp expireDate=null;
        try {
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date a = fd.parse("2999-12-31");
            expireDate=new Timestamp(a.getTime());
        } catch (ParseException ex) {
            throw new NotificationException("ERRDATE", "ERROR AL PARSEAR LA FECHA", ex);
        }
        return expireDate;
    }
    
    /**
     * Método que devuelve la fecha actual sumanda (ms) milisegundos.
     * @param days
     * @return 
     */
    public static Timestamp getAddedTimeStamp(Long ms){
        return new Timestamp(new java.util.Date().getTime()+ms);
    }     
}
