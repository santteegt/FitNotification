package com.fitnotif.notification.emuntypes;

/**
 * Enum en donde se indican los tipos de usuario
 * @author malgia
 * @version 1.0
 */
public enum UserTypes {
    
    ADMIN(1, "ADM"),
    OFICCER(2, "OFI");
    
    private Integer role;
    private String type;

    private UserTypes(Integer role, String type) {
        this.role = role;
        this.type = type;
    }
 
    public Integer getRole() {
        return role;
    }     
    
    public String getType() {
        return type;
    }   
}
