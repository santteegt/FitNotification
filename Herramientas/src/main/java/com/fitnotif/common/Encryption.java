package com.fitnotif.common;

/**
 * Interfaz para la implementacion de un metodo de encripcion de contrasenias
 * @author santiago
 * @version 1.0
 */
public interface Encryption {
    
    public String encrypt(String pText);
    public String decrypt(String pText);
    
}
