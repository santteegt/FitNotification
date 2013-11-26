package com.fitnotif.web;

import com.fitnotif.notification.Request;
import com.fitnotif.webpages.NotifWebPage;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import net.sourceforge.wurfl.core.WURFLHolder;

/**
 * Propiedades de la sesion del servlet
 * @author santiago
 * @version 1.0
 */
public final class WebEnviroment {
    
    // PROPIEDADES Y METODOS DE LA INSTANCIA
    private final HttpSession session;    
    
    private final ServletContext context;
    
    private static final Logger logger = Logger.getLogger(WebEnviroment.class.getName());

    private WebEnviroment(HttpServletRequest req) {
        if(req.getSession().getAttribute(SESSION_DATA) == null){
            this.session = req.getSession(true);
        }else{
            this.session = req.getSession();            
        }
        this.context = req.getServletContext();       
        
    }

    // PROPIEDADES Y METODOS ESTATICOS
    private static final String SESSION_DATA = "SESSION_DATA";
    
    private static final String WURFL_HOLDER = "WURFL_HOLDER";

    private static final ThreadLocal<WebEnviroment> entornoWeb = new ThreadLocal<WebEnviroment>() {

        @Override
        protected WebEnviroment initialValue() {
            return null;
        }
    };

    public static final String URI_INGRESO = "index.html";

    public static final String URI_CLAVE = "nupass.html";

    public static final String URI_ENTORNO = "entorno.html";
    
    public static final String URI_DEVICES = "entorno.html#opciones";
    
    public static final String URI_AUTHORIZATION = "entorno.html#consolidado";
    
    public static final String URI_ADMIN = "admin.html";

    public static void init(HttpServletRequest req) {
        entornoWeb.set(new WebEnviroment(req));

        // Reinicializar en caso de un pedido de ingreso
        /*
        String pathInfo = StringUtils.trimToEmpty(req.getPathInfo());
        String names = "/" + GeneralRequestTypes.NAMES;
        if (pathInfo.equalsIgnoreCase(names) && req.getParameter("_reset") != null) {
            setDatosSesion(null);
        }*/
    }

    public static HttpSession getSession() {
        return entornoWeb.get().session;
    }
    
    public static ServletContext getServletContext(){
        return entornoWeb.get().context;
    }    

    public static void setSessionData(SessionData data) {
        getSession().setAttribute(SESSION_DATA, data);
    }

    public static SessionData getSessionData() {
        if (getSession().getAttribute(SESSION_DATA) == null) {            
            setSessionData(new SessionData());            
        }

        return (SessionData) getSession().getAttribute(SESSION_DATA);
    }
    
    public static void killSession(){
        getSession().removeAttribute(SESSION_DATA);
    }
    
    public static Boolean isSessionAvailable(){
        return getSession().getAttribute(SESSION_DATA) != null ? true:false;
    }
    
    /**
     * Obtiene el Holder de WURFL que posee informacion de dispositivos
     * @return
     * @throws Exception 
     */
    public static WURFLHolder getWURFLHolder()throws Exception{        
        if(getSessionData().getWurflHolder() == null){
            getSessionData().setWurflHolder((WURFLHolder) getServletContext()
                .getAttribute("net.sourceforge.wurfl.core.WURFLHolder"));
        }       
        return getSessionData().getWurflHolder();
    }   

    public static void setTransportData(Request transport) {
        getSessionData().setTransportData(transport);
    }

    public synchronized static Request getTransportData() {
        return getSessionData().getTransportData();
    }
    
    public static NotifWebPage getWebPage(){
        return getSessionData().getWebpage();
    }
    
    public static void setWebPage(NotifWebPage webPage){
        getSessionData().setWebpage(webPage);
    }
    
    public static String getAuthToken(){
        return getSessionData().getTokenId();
    }
    
    public static void setAuthToken(String token){
        getSessionData().setTokenId(token);
    }
    
    public static void setProcess(String process){
        getSessionData().setProcess(process);
    }
    
    public static Boolean isSameProcess(String process){
        return getSessionData().getProcess() != null 
                && getSessionData().getProcess().compareTo(process) == 0
                && process.compareTo(RequestTypes.NOTIF) != 0;
    }
    
    /**
     * Establece si ya han registrado el numero maximo de dispositivos
     * @param is
     * @throws Exception 
     */
    public static void exeededDevices(Boolean is)throws Exception{
        getSessionData().setMaxDevices(is);
    }
    
    public static Boolean isExeededDevices()throws Exception{
        return getSessionData().getMaxDevices();
    }
    
    public static void setResponseObj(JSONObject json){
        getSessionData().setResponseObj(json);
    }
    
    public static JSONObject getResponseObj(){
        return getSessionData().getResponseObj();
    }

    public static String getSessionId() {
        return entornoWeb.get().session.getId();
    }
    
    public static void log(String message)throws Exception{
        logger.log(Level.INFO, message);
    }
}
