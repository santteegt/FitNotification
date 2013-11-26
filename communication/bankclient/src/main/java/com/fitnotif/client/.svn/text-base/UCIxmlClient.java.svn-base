package com.fitnotif.client;

import com.fitnotif.common.NotificationException;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.util.PropertiesHandler;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import org.hibernate.util.PropertiesHelper;

/**
 * Conexion al servidor de FitBank
 * @author santiago
 * @version 1.0
 */
public final class UCIxmlClient{      
    
    private static ObjectInputStream input;
    
    private static ObjectOutputStream output;
    
    private UCIxmlClient(){
        
    }    
        
    /**
     * Conexion al Servidor de FitBank
     * @param detail
     * @return
     * @throws Exception 
     */
    public static Serializable connect(Serializable detail)throws Exception{       
        Properties uciprops = PropertiesHandler.getInstance().getProperties("bankparameters");
        String server=uciprops.getProperty("uciserver.ip");
        Integer port=Integer.valueOf(uciprops.getProperty("uciserver.port"));
        Socket client;
        Serializable response = null;
        try{
            client = new Socket( InetAddress.getByName( server ), port );
            output = new ObjectOutputStream(client.getOutputStream());
            UCIxmlClient uci = new UCIxmlClient();
            uci.sendData(detail);
            input = new ObjectInputStream(client.getInputStream());
            response = (Serializable) input.readObject();
            
        }catch(Exception e){
            throw new NotificationException(e);
        }
        client.close();
        input.close();
        output.close();
        return response;
    }    
    
    /**
     * Envio de xml al UCI de Fit
     * @param data
     * @throws Exception 
     */
    private void sendData(Serializable data) throws Exception{
        output.writeObject(data);
        output.flush();        
    }
    
    /**
     * Metodo de pruebas de conexion al servidor
     * @param args 
     */
    public static void main(String args[]){
        try{
            String path = "/home/santiago/";
            FileInputStream fin = new FileInputStream(path+"rq.xml");
            String data = readStream(fin);            
                      
            data = (String) connect((Serializable)data);
            
            FileOutputStream fos = new FileOutputStream(path+"rs.xml");
            fos.write(data.getBytes());
            fos.close();                        
        }catch(Exception e){
            //e.printStackTrace();
            
        }
    }
    
    /**
     * Clase utilizada para pruebas
     * @param pIn Stream de datos de entrada
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