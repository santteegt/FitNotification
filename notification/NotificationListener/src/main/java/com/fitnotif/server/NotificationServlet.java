package com.fitnotif.server;

import com.fitnotif.common.Server;
import com.fitnotif.common.NotificationException;
import com.fitnotif.util.Servicios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet de Notificaciones
 * @author malgia
 */
public class NotificationServlet extends HttpServlet {
    
    private final List<Server> servers = new ArrayList<Server>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        /*resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Prueba</title>");
            out.println("</body>");
            out.println("</html>");
            } catch (Exception e) {
            throw new ServletException(e);
            } finally {
            out.close();
        }*/
        PrintWriter out = null;
        try {
            resp.setContentType("text/html");
            out = resp.getWriter();
            for(Server server:this.servers){
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servidores</title>");
                out.println("<head>");
                out.println("<body>");
                out.println(server.getClass().getName()+"</br>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (IOException ex) {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        //this.doGet(req, resp);
    }

    /**
     * Método que se ejecuta al subir el servlet
     * Ejecuta un thread
     * @param config 
     */
    @Override
    public void init(ServletConfig config){
        for(Server server:Servicios.load(Server.class)){
            try {
                this.servers.add(server);
                server.start();
            } catch (Exception ex) {
                throw new NotificationException("ERRSRV", "ERROR EN EL SERVLET", ex);
            }
        }
    }
    
    /**
     * Método que se ejecuta al bajar el servlet
     * @param config 
     */
    public void destroy(ServletConfig config){
        for(Server server:this.servers){
            server.closeConnection();
        }
        
    }  
}