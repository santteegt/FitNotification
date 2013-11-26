package com.fitnotif.web.data;

import com.fitnotif.notification.Request;
import com.fitnotif.web.RequestTypes;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Datos de un pedido Http
 * @author santiago
 * @version 1.0
 */
public class WebRequest {
    
    private HttpServletRequest httpServletRequest;

    

    private HttpServletResponse httpServletResponse;
    
    private String tipoPedido = RequestTypes.ERROR;
    
    private Map<String, List<String>> requestvalues = new HashMap<String, List<String>>();
    
    private Request requestData;

    public Request getRequestData() {
        return requestData;
    }

    public void setRequestData(Request requestData) {
        this.requestData = requestData;
    }
    
    
    public WebRequest(HttpServletRequest request, HttpServletResponse response){
        this.setHttpServletRequest(request);
        this.setHttpServletResponse(response);        
        
        if (request.getServletPath().substring(0, 5).compareTo("/proc") == 0) {
            this.setTipoPedido(request.getPathInfo().substring(1));
        }  
        
        Enumeration e = httpServletRequest.getParameterNames();

        while (e.hasMoreElements()) {
            String s = String.valueOf(e.nextElement());
            requestvalues.put(s, Arrays.asList(httpServletRequest.
                    getParameterValues(s)));
        }
         
        
    }
    
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }    
    

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public final void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public final void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public final void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
    
    public Map<String, List<String>> getRequestvalues() {
        return requestvalues;
    }
    
    public final String getRequestValue(String nombre) {
        return requestvalues.get(nombre) != null ? requestvalues.get(
                nombre).get(0) : null;
    }

    public void redirect(String path)throws IOException{        
        if (!path.startsWith("http") && !path.startsWith("/")) {
            path = getHttpServletRequest().getContextPath() + "/" + path;
        }
        getHttpServletResponse().sendRedirect(path);               
    }
    
}
