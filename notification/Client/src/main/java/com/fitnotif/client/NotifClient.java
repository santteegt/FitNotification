package com.fitnotif.client;

import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.parser.NotificationParser;
import com.fitnotif.util.PropertiesHandler;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

/**
 * Conexion al servidor de notificaciones.
 * @author santiago
 * @version 1.0
 */
public final class NotifClient{      
    
    private static ObjectInputStream input;
    
    private static ObjectOutputStream output;
    
    private static final String PATH = "/home/santiago/";
    
    private NotifClient(){
        
    }
    
    /**
     * Envio de datos al servidor de Notificaciones
     * @param notification
     * @return
     * @throws Exception 
     */
    public static Notification send(Notification notification) throws Exception {
        Properties properties=PropertiesHandler.getInstance().getProperties("notifclient");
        String server=properties.getProperty("server.ip");
        Integer port=Integer.parseInt(properties.getProperty("server.port"));
        Socket client;
        Notification response = null;
        try{
            client = new Socket( InetAddress.getByName( server ), port );
            output = new ObjectOutputStream(client.getOutputStream());   
            NotifClient c = new NotifClient();
            c.sendData(notification);
            input = new ObjectInputStream(client.getInputStream());
            response = (Notification) input.readObject();
            
        }catch(Exception e){
            throw new NotificationException("WM001", "ERROR AL CONECTARSE AL SERVIDOR DE NOTIFICACIONES");
        }
        client.close();
        input.close();
        output.close();
        return response;
    }   
    
    /**
     * Envio de stream de datos
     * @param notification
     * @throws Exception 
     */
    private void sendData(Notification notification) throws Exception{
        output.writeObject(notification);
        output.flush();        
    }
    
    /**
     * Funcion para pruebas
     * @param args 
     */
    public static void main(String args[]){
        try{                        
            Notification notification = tstCommunication();
            notification = send(notification);
            FileOutputStream fos = new FileOutputStream(PATH+"notrs.xml");
            fos.write(notification.toXml().getBytes());
            fos.close();                                    
        }catch(Exception e){
            //e.printStackTrace();            
        }
    }
    
    /**
     * Realiza una peticion al servidor de Notificaciones a partir de un
     * xml en disco
     * @param notification
     * @return
     * @throws Exception 
     */
    public static Notification tstCommunication()throws Exception{
        FileInputStream fin = new FileInputStream(PATH+"not.xml");
        String data = readStream(fin);            
        Notification notification = new Notification(new NotificationParser(data));
        return notification;
    }
    
    /**
     * funcion para pruebas
     * @param pIn
     * @return
     * @throws Exception 
     */
    public static String readStream(InputStream pIn) throws Exception {
        byte b[] = new byte[9999];
        int car = 0;
        String data = "";
        do {
            car = pIn.read(b);
            if (car > 0) {
                data += new String(b, 0, car, "UTF-8");
            }
        } while (car > 0);
        return data;
    }
}
