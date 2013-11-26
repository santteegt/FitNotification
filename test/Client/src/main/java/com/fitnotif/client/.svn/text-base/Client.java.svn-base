/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitnotif.client;

import com.fitnotif.notification.Notification;
import com.fitnotif.parser.NotificationParser;
import com.fitnotif.util.PropertiesHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author malgia
 */
public class Client {
       
    private ObjectOutputStream output;   
    
    public static void main(String args[]) throws Exception{
        Properties properties=PropertiesHandler.getInstance().getProperties("notificationclient");
        String server=properties.getProperty("server.ip");
        Integer port=Integer.parseInt(properties.getProperty("server.port"));
        Socket client = new Socket( InetAddress.getByName( server ), port );
        Client c=new Client();
        c.getStreams(client);
        c.sendData();
        client.close();
        c.output.close();
    }
    
    
    private void getStreams(Socket client) throws IOException{
        this.output = new ObjectOutputStream( client.getOutputStream() );      
        this.output.flush(); 
    }
    
    private void sendData() throws Exception{
        File test=new File("/home/santiago/XML");
        InputStream pIn=new FileInputStream(test);
        String xml=readStream(pIn);
        NotificationParser nr=new NotificationParser(xml);
        Notification n=new Notification(nr);
        this.output.writeObject(n);
        this.output.flush();
    }
    
    public static String readStream(InputStream pIn) throws IOException {
        byte b[] = new byte[9999];
        int car = 0;
        String data = "";
        do {
            car = pIn.read(b);
            if (car > 0) {
                data += new String(b, 0, car, "ISO-8859-1");
            }
        } while (car > 0);
        return data;
    }

}
