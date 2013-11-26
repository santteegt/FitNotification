package com.fitnotif.util;

/**
 * Enum en la que se listan los posibles estados en los que puede estar la notificacion
 * @author malgia
 * @version 1.0
 */
public enum StatusTypes {
    NEW("ING"),
    APPROVED("APR"),
    DENIED("NEG"),
    DETAIL("DET"),
    DELETED("ELI");
    
    private String type;
    
    private StatusTypes(String type){
        this.type=type;
    }
    
    public String getType(){
        return this.type;
    }
}
