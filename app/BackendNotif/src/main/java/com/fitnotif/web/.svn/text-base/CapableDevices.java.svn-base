package com.fitnotif.web;

/**
 * Lista de dispositivos que soporta la aplicacion
 * @author santiago
 * @version 1.0
 */
public enum CapableDevices {
    
    BLACKBERRY("BLACKBERRY","xx","",true),
    IOS("APPLE","xx","",true),
    ANDROID("ANDROID","xx","",true),
    PC("GENERIC_WEB_BROWSER","xx","",false);
    
    private String platform;
    private String minimumVersion;
    private String browser;
    private Boolean allowed;
    
    
    private CapableDevices(String platform, String minimumVersion, String browser, Boolean allowed){
        this.platform = platform;
        this.minimumVersion = minimumVersion;
        this.browser = browser;
        this.allowed = allowed;
    }    
        
    public String getPlatform(){
        return this.platform;
    }
    
    public String getMinimumVersion(){
        return this.minimumVersion;
    }
    
    public String getBrowser(){
        return this.browser;
    }
    
    public Boolean isAllowed(){
        return this.allowed;
    }
}
