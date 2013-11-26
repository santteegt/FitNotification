package com.fitnotif.common;

import com.fitnotif.common.NotificationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author malgia
 */
public class StreamManager {
    
    /**
     * Stream de salida
     */
    private ObjectOutputStream output;

    /**
     * Stream de entrada
     */
    private ObjectInputStream input;   
    
    /**
     * Cliente
     */
    private Socket client;
    
    public StreamManager(Socket client){
        this.input=null;
        this.output=null;
        this.client=client;
    }
    
    /**
     * Método que lee un inputStream
     * @return 
     */
    public Object readMessage(){
        Object message = null;
        try {
            this.input=new ObjectInputStream(this.client.getInputStream());
            message = this.input.readObject();
        } catch (Exception ex) {
            this.close();
            throw new NotificationException("STR001", "NO SE PUDO LEER EL STREAM.", ex);
        }
        return message;
    }
    
    
    /**
     * Método que envía una respuestas
     * @param message
     * @param wait true si recibe respuesta
     * @return 
     */
    public Object sendMessage(Object message, Boolean wait){
        try{    
            this.output = new ObjectOutputStream(this.client.getOutputStream());
            this.output.writeObject(message);
            this.output.flush();
            if(wait){
                return this.readMessage();
            }else{
                return null;
            }
        }catch (IOException ex) {
            this.close();
            throw new NotificationException("STR002", "NO SE PUDO ENVIAR EL STREAM", ex);
        } 
    }     
    
    
    public void close(){
        try {
            if(this.input!=null){
                this.input.close();
            }
            if(this.output!=null){
                this.output.close();
            }
        }catch (Exception ex) {
            throw new NotificationException("STR003", "NO SE PUDO CERRAR LA CONEXIÓN.", ex);
        }     
    }
}
