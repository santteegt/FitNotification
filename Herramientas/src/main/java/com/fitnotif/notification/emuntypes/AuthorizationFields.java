package com.fitnotif.notification.emuntypes;

/**
 * Campos de la tabla de informacion de dispositivos
 * @author santiago
 * @version 1.0
 */
public enum AuthorizationFields {
    
    ID("Id"),
    TYPE("Tipo"),
    TYPE_NAME("NombreTipo"),
    NAME("Autorizacion"),
    DATE("Fecha"),
    USER("Usuario"),
    STATUS("Estado"),
    OFFICER("Oficial"),
    MESSAGE("Mensaje");
    
    private String field;
    
    private AuthorizationFields(String field){
        this.field=field;
    }
    
    public String getField(){
        return this.field;
    }
    
}
