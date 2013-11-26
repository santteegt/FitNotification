/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitnotif.notifweb;

/**
 *
 * @author santiago
 */
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * App Servlet. Extiende de {@link HttpServlet}
 * @author santiago
 * @version 1.0
 */
public class MainServlet extends HttpServlet{
    
    
    
    @Override
    public void init(ServletConfig config)throws ServletException{             
        
        super.init(config);
    }
        
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{     
        /*System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
*/
            
            this.connection = new URL("https://declaraciones.sri.gov.ec/facturacion-internet/consultas/publico/resultado-validez-documento.jspa");
            this.data = "";
            addParameter("DAY", "28");
            addParameter("MON", "11");
            addParameter("YEAR", "2012");
            addParameter("autorizacion", "1110588873");
            addParameter("ruc", "1790023680001");
            addParameter("tipo_comprobante", "factura");
            addParameter("establecimiento", "001");
            addParameter("punto_emision", "002");
            addParameter("documento", "000098019");
            System.out.println(getRespueta());
        
    }
    
    /**
     * Registra el evento para manejo de request repetidos.
     * Proceso para dispositivos Android
     * @throws Exception 
     */
    
    
    @Override
    public void destroy(){
        
    }
    URL connection;
    String data;    
    
    public void addParameter(String property, String value)throws UnsupportedEncodingException{
        if(data.length() > 0){
            data += "&" + URLEncoder.encode(property, "UTF-8")
                    +"=" + URLEncoder.encode(value, "UTF-8");
        }else{
            data += URLEncoder.encode(property, "UTF-8")
                    +"=" + URLEncoder.encode(value, "UTF-8");
            
        }
    }
    
    public String getRespueta() throws IOException {
        String respuesta = "";        
        //HttpsURLConnection conn =(HttpsURLConnection) 
        HttpURLConnection conn = (HttpURLConnection)connection.openConnection();
        conn.setDoInput(true);         
        conn.setDoOutput(true);        
        conn.setRequestMethod("POST");        
        conn.setFollowRedirects(true);
        conn.setRequestProperty("Content-length",String.valueOf (data.length())); 
        conn.setRequestProperty("Content-Type","application/x-www- form-urlencoded"); 
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)"); 
        
        // open up the output stream of the connection 
        DataOutputStream output = new DataOutputStream( conn.getOutputStream() ); 


        // write out the data 
        int queryLength = data.length(); 
        output.writeBytes( data ); 
        //output.close();


        System.out.println("Resp Code:"+conn.getResponseCode()); 
        System.out.println("Resp Message:"+ conn.getResponseMessage()); 


        // get ready to read the response from the cgi script 
        DataInputStream input = new DataInputStream( conn.getInputStream() ); 


        // read in each character until end-of-stream is detected 
        for( int c = input.read(); c != -1; c = input.read() ){
            System.out.print( (char)c ); 
            respuesta += c;
        }
        input.close(); 

        /*OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());        
        wr.write(data);        
        wr.close();        
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String linea;        
        while ((linea = rd.readLine()) != null) {
            respuesta+= linea;
        }*/
        return respuesta;
    }    
    
    
}
