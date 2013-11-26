package com.fitnotif.notification;

import com.fitnotif.notification.emuntypes.OperationTypes;
import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import java.sql.Timestamp;
import org.w3c.dom.Node;

/**
 * Clase utilizada para enviar mensajes entre los distintos servlets
 * @author malgia
 * @version 1.0
 */
public abstract class Request implements Serializable, Cloneable{
    
    private static final long serialVersionUID = 9L;
    
    protected String id; 
    protected String sid;
    protected String user;
    protected String originUser;
    protected String ip;
    protected String device;
    protected String type;
    protected String transaction;
    protected String name;
    protected Timestamp date;
    protected String operation;
    protected String password;
    protected String newPassword;
    protected String status;
    protected String stacktrace;
            
    /**
     * Método que guarda la información que viene en la cabecera del archivo XML
     * @param node 
     */
    protected void fill(Node node){
        this.id=XMLHelper.getStringValueByTag(node, "ID");
        this.sid=XMLHelper.getStringValueByTag(node, "SID");
        this.user=XMLHelper.getStringValueByTag(node, "USER");
        this.originUser=XMLHelper.getStringValueByTag(node, "ORIGIN_USER");
        this.ip=XMLHelper.getStringValueByTag(node, "IP");
        this.device=XMLHelper.getStringValueByTag(node, "DEVICE");
        this.type=XMLHelper.getStringValueByTag(node, "TYPE");
        this.transaction=XMLHelper.getStringValueByTag(node, "TRANS");
        this.name=XMLHelper.getStringValueByTag(node, "NAME");
        this.date=XMLHelper.getTimestampValueByTag(node, "DATE");
        this.operation=XMLHelper.getStringValueByTag(node, "OP");
        if(this.operation.compareTo(OperationTypes.LOGIN.getType())==0
                || this.operation.compareTo(OperationTypes.CHANGEPWD.getType())==0){
            this.password=XMLHelper.getStringValueByTag(node, "PASSWORD");
        }
        if(this.operation.compareTo(OperationTypes.CHANGEPWD.getType())==0){
            this.newPassword=XMLHelper.getStringValueByTag(node, "NPASSWORD");
        }
        this.status=XMLHelper.getStringValueByTag(node, "STATUS");
        this.stacktrace=XMLHelper.getStringValueByTag(node, "STACK");        
    }
    
    /**
     * Método que pone en un archivo XML la información de cabecera
     * @return
     * @throws Exception 
     */
    public Node getHeaderNode(){
        Node node = XMLHelper.createNode("HEADER");
        XMLHelper.appendChild(node, "ID", this.id);
        XMLHelper.appendChild(node, "SID", this.sid);
        XMLHelper.appendChild(node, "IP", this.ip);
        XMLHelper.appendChild(node, "DEVICE", this.device);
        XMLHelper.appendChild(node, "USER", this.user);
        XMLHelper.appendChild(node, "ORIGIN_USER", this.originUser);
        XMLHelper.appendChild(node, "TYPE", this.type);
        XMLHelper.appendChild(node, "TRANS", this.transaction);
        XMLHelper.appendChild(node, "NAME", this.name);
        XMLHelper.appendChild(node, "DATE", this.date);
        XMLHelper.appendChild(node, "OP", this.operation);
        if(this.operation.compareTo(OperationTypes.LOGIN.getType())==0 ||
                this.operation.compareTo(OperationTypes.CHANGEPWD.getType())==0){
            XMLHelper.appendChild(node, "PASSWORD", this.password);
        }
        if(this.operation.compareTo(OperationTypes.CHANGEPWD.getType())==0){
            XMLHelper.appendChild(node, "NPASSWORD", this.newPassword);
        }
        XMLHelper.appendChild(node, "STATUS", this.password);
        XMLHelper.appendChild(node, "STACK", this.stacktrace);
        return node;
    }
    
    public Object cloneMe(){
        Request request=null;
        try {
            request=(Request) this.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return request;
    }
        
    /**
     * Devuelve el numero de mensaje de la notificacion
     * @return 
     */

    public String getId(){
        return this.id;
    }
    
    /**
     * Establece el numero de mensaje de la notificacion
     * @param ip 
     */    
    
    public void setId(String id){
        this.id=id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
    
    public String getUser(){
        return this.user;
    }
    
    public void setUser(String user){
        this.user=user;
    }
    
    /**
     * Devuelve el subsistema de la notificacion
     * @return 
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Establece el subsistema de la notificacion
     * @param type 
     */
    public void setType(String type){
        this.type=type;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    
    /**
     * Devuelve el nombre de la notificacion
     * @return 
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Establece el nombre de la notificacion
     * @param name 
     */
    public void setName(String name){    
        this.name=name;
    }
    
    public Timestamp getDate(){
        return this.date;
    }
    
    public void setDate(Timestamp date){
        this.date=date;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    /**
     * Devuelve el estado de la notificacion
     * @return 
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado de la notificacion
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Devuelve el nombre del usuario que genero la autorizacion
     * @return 
     */
    public String getOriginUser() {
        return originUser;
    }

    /**
     * Establece el nombre del usuario que genero la autorizacion
     * @param origin_user 
     */
    public void setOriginUser(String origin_user) {
        this.originUser = origin_user;
    }
}
