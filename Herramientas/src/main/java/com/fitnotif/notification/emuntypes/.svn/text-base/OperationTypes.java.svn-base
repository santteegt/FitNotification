package com.fitnotif.notification.emuntypes;

/**
 * Enum en donde se indica a que procesador se tiene que llamar
 * @author malgia
 * @version 1.0
 */
public enum OperationTypes {
    
    NOTIFY("NOT", "com.fitnotif.processors.NotifyProcessor"),
    LOGIN("SIGN", "com.fitnotif.processors.LoginProcessor"),
    CHANGETK("AUTHD0", "com.fitnotif.processors.UpdateTokenProcessor"),
    CHANGEPWD("CHPWD", "com.fitnotif.processors.UpdatePasswordProcessor"),
    RESETPWD("RUPAS", "com.fitnotif.processors.ResetPasswordProcessor"),
    REGDEVICE("RDEV", "com.fitnotif.processors.RegisterDeviceProcessor"),
    EXPDEVICE("UPD", "com.fitnotif.processors.ExpireDeviceProcessor"),
    LISTDEVICES("LDEV", "com.fitnotif.processors.ListDevicesProcessor"),
    FILLUSERS("FUSR", "com.fitnotif.processors.FillUsersTableProcessor"),
    LISTNOTIF("NOTIF", "com.fitnotif.processors.ListNotificationsProcessor"),
    LOGOUT("LOUT", "com.fitnotif.processors.CloseSessionProcessor"),
    GETNOT("AUTHD", "com.fitnotif.processors.ReturnNotificationProcessor"),
    AUTHORIZE("AUTH", "com.fitnotif.processors.AuthorizeProcessor"),
    AUTHORIZE2("AUTH2", "com.fitnotif.processors.AuthorizeProcessor"),
    PARAMETERS("SERVD", "com.fitnotif.processors.ReturnParametersProcessor"),
    PARAMETER("GSYSP", "com.fitnotif.processors.ReturnParameterProcessor"),
    CHANGEPARAM("UPSRD", "com.fitnotif.processors.UpdateParametersProcessor"),
    ROLE("USPRI", "com.fitnotif.processors.ReturnRoleProcessor"),
    CLOSESESSION("FUSSN", "com.fitnotif.processors.CloseUserSessionProcessor"),
    DELETEAUTH("DAUTH", "com.fitnotif.processors.DeleteNotificationProcessor");
    
    private String type;
    private String classname;

    /**
     * Método que devuelve la operación dependiendo del tipo
     * @param op
     * @return 
     */
    public static OperationTypes getOperationType(String op){
        OperationTypes operationType = null;
        for (OperationTypes obj : OperationTypes.values()) {
            if (obj.getType().compareTo(op) == 0) {
                operationType = obj;
                break;
            }
        }
        return operationType;
    }
    
    private OperationTypes(String type, String classname) {
        this.type = type;
        this.classname = classname;
    }

    public String getType(){
        return this.type;
    }    
    
    public String getClassname() {
        return classname;
    }   
}
