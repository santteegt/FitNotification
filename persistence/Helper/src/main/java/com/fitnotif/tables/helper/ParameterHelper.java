package com.fitnotif.tables.helper;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.persistence.tablas.TParametrosSistema;
import com.fitnotif.persistence.util.HibernateUtil;
import org.hibernate.Query;

/**
 * Clase que permite obtener informacion de la tabla TPARAMETROSSISTEMA
 * @author malgia
 * @version 1.0
 */
public final class ParameterHelper {
    
    private static final String HQL_PARAMETER = "from com.fitnotif.persistence.tablas.TParametrosSistema t where "
            + "t.cparametro=:parameter "
            + "and t.fhasta=:expireDate";

    private static final String PARAMETER = "parameter";
    
    private static final String EXPIREDATE = "expireDate";
    
    private ParameterHelper(){}
    
    /**
     * Método que devuelve un parametro entero
     * @param parameter
     * @return 
     */
    public static Integer getIntegerValue(String parameter){
        Query qry=HibernateUtil.createQuery(HQL_PARAMETER);
        qry.setString(PARAMETER, parameter);
        qry.setTimestamp(EXPIREDATE, DatesHelper.getExpireTimeStamp());
        TParametrosSistema parametro = (TParametrosSistema) qry.uniqueResult();
        if(parametro==null || parametro.getValorentero()==null){
            throw new NotificationException("PAR001", "EL PARAMETRO {0} NO ESTA PARAMETRIZADO", parameter);
        }
        return parametro.getValorentero();
    }
    
    /**
     * Método que devuelve un parametro de tipo cadena.
     * @param parameter
     * @return 
     */
    public static String getStringValue(String parameter){
        Query qry=HibernateUtil.createQuery(HQL_PARAMETER);
        qry.setString(PARAMETER, parameter);
        qry.setTimestamp(EXPIREDATE, DatesHelper.getExpireTimeStamp());
        TParametrosSistema parametro = (TParametrosSistema) qry.uniqueResult();
        if(parametro==null || parametro.getValorcadena()==null){
            throw new NotificationException("PAR001", "EL PARAMETRO {0} NO ESTA PARAMETRIZADO", parameter);
        }
        return parametro.getValorcadena();
    }
    
    /**
     * Método que determina si un parametro es String.
     * @param parameter
     * @return 
     */
    public static boolean isString(String parameter){
        Query qry=HibernateUtil.createQuery(HQL_PARAMETER);
        qry.setString(PARAMETER, parameter);
        qry.setTimestamp(EXPIREDATE, DatesHelper.getExpireTimeStamp());
        TParametrosSistema parametro = (TParametrosSistema) qry.uniqueResult();
        return parametro!=null && parametro.getValorcadena()!=null;
    }
    
    /**
     * Método que determina si un parametro es Integer.
     * @param parameter
     * @return 
     */
    public static boolean isInteger(String parameter){
        Query qry=HibernateUtil.createQuery(HQL_PARAMETER);
        qry.setString(PARAMETER, parameter);
        qry.setTimestamp(EXPIREDATE, DatesHelper.getExpireTimeStamp());
        TParametrosSistema parametro = (TParametrosSistema) qry.uniqueResult();
        return parametro!=null && parametro.getValorentero()!=null;
    }
    
    /**
     * Método que almacena o actualiza un parametro en la BD.
     * @param parameter
     * @param type
     */
    public static void saveParameter(String parameter, String value){
        TParametrosSistema parametro=null;
        Query qry=HibernateUtil.createQuery(HQL_PARAMETER);
        qry.setString(PARAMETER, parameter);
        qry.setTimestamp(EXPIREDATE, DatesHelper.getExpireTimeStamp());
        TParametrosSistema nuevoParametro = (TParametrosSistema) qry.uniqueResult();
        if(nuevoParametro==null){
            nuevoParametro = new TParametrosSistema();
            nuevoParametro.setCparametro(parameter);
            nuevoParametro.setFhasta(DatesHelper.getExpireTimeStamp());
        }else{
            parametro=(TParametrosSistema) nuevoParametro.cloneMe();
            parametro.setFhasta(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(parametro);
        }
        nuevoParametro.setFdesde(DatesHelper.getCurrentTimeStamp());
        nuevoParametro.setValorcadena(value);
        HibernateUtil.saveOrUpdate(nuevoParametro);
    }
    
/**
     * Método que almacena o actualiza un parametro en la BD.
     * @param parameter
     * @param type
     */
    public static void saveParameter(String parameter, Integer value){
        TParametrosSistema parametro=null;
        Query qry=HibernateUtil.createQuery(HQL_PARAMETER);
        qry.setString(PARAMETER, parameter);
        qry.setTimestamp(EXPIREDATE, DatesHelper.getExpireTimeStamp());
        TParametrosSistema nuevoParametro = (TParametrosSistema) qry.uniqueResult();
        if(nuevoParametro==null){
            nuevoParametro = new TParametrosSistema();
            nuevoParametro.setCparametro(parameter);
            nuevoParametro.setFhasta(DatesHelper.getExpireTimeStamp());
        }else{
            parametro=(TParametrosSistema) nuevoParametro.cloneMe();
            parametro.setFhasta(DatesHelper.getCurrentTimeStamp());
            HibernateUtil.save(parametro);
        }
        nuevoParametro.setFdesde(DatesHelper.getCurrentTimeStamp());
        nuevoParametro.setValorentero(value);
        HibernateUtil.saveOrUpdate(nuevoParametro);
    }    
}
