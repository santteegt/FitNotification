package com.fitnotif.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Manejo de archivos properties. Implementa Singleton
 * @author santiago
 * @version 1.0
 */
public final class PropertiesHandler {    
        
    private final Properties properties = new Properties();
    
    private static final String EXTENSION = ".properties";
    
    private static PropertiesHandler instance = null;
    
    private PropertiesHandler(){
        
    }
    
    /**
     * implementacion Singleton
     * @return 
     */
    public static PropertiesHandler getInstance(){
        return instance == null ? new PropertiesHandler():instance;
        
    }
    
    /**
     * Obtiene las propiedades del archivo
     * @param name
     * @return 
     */
    public Properties getProperties(String name){        
        try{
            properties.load(Thread.currentThread().getContextClassLoader().
                getResourceAsStream(name + EXTENSION));
        }catch(IOException e){
            
        }
        return properties;
    }
    
    
    
}
