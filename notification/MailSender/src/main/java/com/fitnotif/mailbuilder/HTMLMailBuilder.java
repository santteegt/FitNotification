package com.fitnotif.mailbuilder;

import com.fitnotif.notification.Notification;

/**
 * Clase que se encarga de armar el correo electrónico para notificar al usuario
 * @author malgia
 * @version 1.0
 */
public class HTMLMailBuilder {
    private String header;
    
    private String body;
    
    private String footer;
    
    private String message;
    
    /**
     * Constructor de la clase
     * @param n 
     */
    public HTMLMailBuilder(Notification n){
        builtStructure(n);
        setMainInformation(n);
        setLink(n);
        this.message=this.header+this.body+this.footer;
    }
    
    /**
     * Método en el que se genera la estructura general del correo
     * @param n 
     */
    private void builtStructure(Notification n){
            this.header="<html>"
                + "<head>"
                + "<tittle>"
                + n.getName().toUpperCase()
                + "</tittle>"
                + "</head>";
        this.body="<body><h1>"
                + n.getName().toUpperCase()
                + "</h1><br/>";
        this.footer="</body></html>";
    }
    
    /**
     * Método que llena el mail con la información principal
     * @param n 
     */
    private void setMainInformation(Notification n){
        this.body=this.body+"<p>";
        addMainItem("Id", n.getId());
        addMainItem("Usuario", n.getUser());
        addMainItem("Fecha", n.getDate()!=null?n.getDate().toString():null);
        this.body=this.body+"</p>";
                
    }
    
    /**
     * Método que agregar un Item al correo
     * @param tittle
     * @param info 
     */
    private void addMainItem(String tittle, String info){
        if(info!=null){
            this.body=this.body+
                    "<b>"
                    + tittle.toUpperCase()
                    + ": </b>"
                    + info.toUpperCase()
                    + "<br/>";
        }
    }
    
    /**
     * Método que agrega el link para acceder a la aplicación
     * @param n 
     */
    private void setLink(Notification n){
        this.body=this.body+n.getId();
    }
    
    public String getMessage(){
        return this.message;
    }
}
