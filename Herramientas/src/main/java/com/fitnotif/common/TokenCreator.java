package com.fitnotif.common;
import java.rmi.server.UID;

/**
 * Clase que permite crear tokens.
 * @author malgia
 */
public final class TokenCreator {
    
    private TokenCreator(){}
    
    public static String createToken(){
        return new UID().toString();
    }
}
