package com.fitnotif.web.manager;

import com.fitnotif.web.CapableDevices;
import com.fitnotif.web.WebEnviroment;
import com.fitnotif.web.data.WebRequest;
import com.fitnotif.web.exception.WebError;
import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.WURFLHolder;
import net.sourceforge.wurfl.core.WURFLManager;

/**
 * Filtra los dispositivos que no pueden ser utilizados y/o no compatibles
 * @author santiago
 * @version 1.0
 */
public class DeviceFilter {
    
    private Device device;
    private String devName;
    
    private static final String BRAND = "brand_name";
    private static final String MODEL_NAME = "model_name";
    
    public DeviceFilter(WebRequest request) throws Exception{        
        WURFLHolder wurflHolder = WebEnviroment.getWURFLHolder();
        WURFLManager wurfl = wurflHolder.getWURFLManager();
        this.device = wurfl.getDeviceForRequest(request.getHttpServletRequest());        
    }
    
    /**
     * Verifica que se cumpla el minimo de version de plataforma movil
     * @return
     * @throws Exception 
     */
    private Boolean checkPlatform() throws Exception{        
        /*String platform = device.getCapability(BRAND).equals("") ? 
                (device.getCapability(MODEL_NAME).equals("") ?
                device.getDeviceRootId():device.getCapability(MODEL_NAME))
                :device.getCapability(BRAND);        */
        String platform = device.getUserAgent().toUpperCase();
        String version = "xx";
        CapableDevices devs [] = CapableDevices.values();
        for(CapableDevices dev:devs){            
            if( platform.matches(".*" + dev.getPlatform() + ".*") ){
                return dev.getMinimumVersion().compareTo(version) >= 0
                        && dev.isAllowed()? true:false;
            }
        }
        return false;
        
    }
    
    public String checkBrowser()throws Exception{
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Obtiene la informacion del dispositivo, mediante la metadata de WURFL
     * @param request Peticion de la aplicacion
     * @param pRequest Peticion xml a enviar al servidor
     * @throws Exception 
     */
    public String getDeviceName(Boolean deviceFilter)throws Exception{
        this.modelDevice();
        if("".equals(devName)){
            devName = this.device.getDeviceRootId().compareTo("")==0 ? 
                this.device.getId():this.device.getDeviceRootId();                
        }
        
        if(deviceFilter && !this.checkPlatform()){            
            throw new WebError("NO SE PUEDE ACCEDER A LA APLICACIÓN DESDE ESTA PLATAFORMA");            
        }
        return devName;
    }
    
    private void modelDevice()throws Exception{
        devName = !this.device.getCapability(BRAND).equals("") ? this.device.getCapability(BRAND):"";
        devName = devName + (!this.device.getCapability("marketing_name").equals("") ? " " + this.device.getCapability("marketing_name"):"");
        devName = devName + (!this.device.getCapability(MODEL_NAME).equals("") ? " " + this.device.getCapability(MODEL_NAME):"");        
    }
    
    /**
     * Obtiene el UserAgent del dispositivo
     * @return
     * @throws Exception 
     */
    public String getUserAgent()throws Exception{
        return this.device.getUserAgent();
    }
    
    /**
     * Verifica si el dispositivo posee soporte de Cookies
     * @return
     * @throws Exception 
     */
    public Boolean hasCookieSupport()throws Exception{        
            return this.device.getCapability("cookie_support").equals("true");
    }
}
