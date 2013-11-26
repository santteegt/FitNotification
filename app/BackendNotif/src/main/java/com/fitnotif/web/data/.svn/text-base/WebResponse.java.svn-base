package com.fitnotif.web.data;

import com.fitnotif.notification.Request;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

/**
 * Contiene los datos de una respuesta web.
 * 
 * @author santiago
 * @version 1.0
 */
public final class WebResponse {
    

    private byte[] contenido = null;

    private String contentType = "application/json";

    private HttpServletResponse servletResponse;
    
    private Request transportData;
    
    private String characterEncoding = "UTF-8";    

    public WebResponse(Request data, WebRequest request) {
        setTransportData(data);
        setHttpServletResponse(request.getHttpServletResponse());
    }
    

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido, String contentType) {
        this.contenido = contenido;
        setContentType(contentType);
    }
/*
    public void setContenido(Object contenido) {
        if (contenido instanceof byte[]) {
            this.contenido = (byte[]) contenido;
        } else {
            try {
                this.contenido = String.valueOf(contenido).getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new ErrorWeb(e);
            }
        }

        if (contenido == null) {
            setContentType("application/other");
        }
        try {
            setContentType(FileHelper.getContentType(this.contenido));
        } catch (Exception e) {
            Debug.error(e);
        }
    }*/

    /**
     * Establece el contenido de un webPage
     * @param content 
     */
    public void setContent(WebPageTransport content) {
        setContenido(content.toJSON(), "application/json");
    }
    
    /**
     * Funcion principal para el envio de informacion a la aplicacion
     * @param contenido
     * @param contentType 
     */
    public void setContenido(String contenido, String contentType) {
        try {
            this.contenido = contenido.getBytes(getCharacterEncoding());
            this.setContentType(contentType);
        } catch (UnsupportedEncodingException e) {
            
        }
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public HttpServletResponse getHttpServletResponse() {
        return servletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse response) {
        servletResponse = response;
    }
    
    
    public Request getTransportData() {
        return transportData;
    }

    public void setTransportData(Request transportData) {
        this.transportData = transportData;
    }

    public void escribir() throws IOException {
        if (this.getContenido() != null) {
            servletResponse.setCharacterEncoding(this.getCharacterEncoding());
            servletResponse.setContentType(this.getContentType());
            //this.noCaching();
            OutputStream os = servletResponse.getOutputStream();
            os.write(this.getContenido());
            os.close();
        } else if (!servletResponse.containsHeader("Location")) {
            //throw new ErrorWeb("No hay contenido");
        }
    }

    /**
     * Agrega encabezados a la respuesta para asegurarse que no quede guardada
     * en la cache del navegador.
     */
    private void noCaching() {
        servletResponse.setDateHeader("Date", new Date().getTime());
        servletResponse.setDateHeader("Expires", 0);
        servletResponse.setHeader("Pragma", "no-cache");
        servletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        /*servletResponse.setHeader("ETag", UUID.randomUUID().toString().replace(
                "-", ""));       */
    }

}
