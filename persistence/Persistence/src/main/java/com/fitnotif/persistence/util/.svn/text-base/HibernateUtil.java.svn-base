package com.fitnotif.persistence.util;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

/**
 * Clase que nos permite manejar las tablas de Hibernate
 * @author malgia
 */
public final class HibernateUtil {

    private static SessionFactory sessionFactory;
    
    private static Transaction transaction;
        
    private HibernateUtil(){}
    
    /**
     * Método que crea la sesión
     */
    public static void buildSession(){
        if(sessionFactory==null){
            sessionFactory=buildSessionFactory();
        }
    }
    
    /**
     * Método que inicia una transacción
     */
    public static void beginTransaction(){
        if(sessionFactory==null){
            buildSession();
        }
        if((transaction!=null && transaction.wasCommitted()) || transaction==null){
            transaction=getSession().beginTransaction();
        }
    }
    
    /**
     * Método que guarda un Objeto
     * @param o 
     */
    public static void save(Object o){
        if(sessionFactory!=null){
            getSession().save(o);
        }
    }
    
    /**
     * Método que guarda o actualiza un Objeto
     * @param o 
     */
    public static void saveOrUpdate(Object o){
        if(sessionFactory!=null){
            getSession().saveOrUpdate(o);
        }
    }    
     
    /**
     * Método que cierra la sesión
     */
    public static void close(){
        if(sessionFactory!=null){        
            getSession().close();
        }
    }
    
    /**
     * Método que hace commit de una transacción
     */
    public static void commit(){
        if(transaction!=null){
            transaction.commit();
            transaction=null;
        }
    }
    
    public static void rollback(){
        if(transaction!=null){
            transaction.rollback();
            transaction=null;
        }
    }
    
    /**
     * Método que permite crear sentencias HQL
     * @param qry
     * @return 
     */
    public static Query createQuery(String qry){
        Query query=null;
        if(sessionFactory!=null){
            query=getSession().createQuery(qry);
        }
        return query;
    }
    
    /**
     * Método que crea la sesión
     * @return 
     */
    private static SessionFactory buildSessionFactory() {
        try {

            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }    
    
    private static Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public static Transaction getTransaction(){
        return transaction;
    }
}