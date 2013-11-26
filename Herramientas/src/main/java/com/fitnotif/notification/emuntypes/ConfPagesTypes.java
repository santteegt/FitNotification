package com.fitnotif.notification.emuntypes;

/**
 * Lista de pages que se utilizan para traer datos del sistema de notificaciones
 * @author santiago
 * @version 1.0
 */
public enum ConfPagesTypes {
    
    DEVICES("Dispositivos"),
    AUTHORIZATIONS("Autorizaciones"),
    CONFIGURATTION("configuracion"),
    SESSION("sesiones");
    
    private String page;
    
    private ConfPagesTypes(String page){
        this.page=page;
    }
    
    public String getPage(){
        return this.page;
    }
    
}
