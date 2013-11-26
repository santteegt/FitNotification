package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TAUTORIZACIONES
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TAUTORIZACIONES")
public class TAutorizaciones implements Serializable, Cloneable {
    @Column (name="NUMEROMENSAJE")
    @Id
    private String numeromensaje;
    @Column (name="FK2FHASTA")
    @Id
    private Timestamp fk2fhasta;
    @Column (name="FKCESTATUSAUTORIZACION")
    private String fkcestatusautorizacion;    
    @Column (name="FKCTIPOAUTORIZACION")
    private String fkctipoautorizacion;
    @Column (name="NOMBRE")
    private String nombre;
    @Column (name="FKCUSUARIO")
    private String fkcusuario;   
    @Column (name="CUSUARIO_ORIGEN")
    private String cusuarioorigen;   
    @Column (name="CTRANSACCION")
    private String ctransaccion;     
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="FPROCESO")
    private Timestamp fproceso; 
    @Column (name="FKTOKEN")
    private String fktoken;
    @Column (name="MENSAJE")
    private String mensaje;


    public Object cloneMe(){
        TAutorizaciones autorizaciones=null;
        try {
            autorizaciones=(TAutorizaciones) this.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return autorizaciones;
    }
    
    public TAutorizaciones(){
    }
    
    public TAutorizaciones(String numero, Timestamp fkfhasta){
        this.numeromensaje=numero;
        this.fk2fhasta=fkfhasta;
    }
    
    public String getFkcestatusautorizacion() {
        return fkcestatusautorizacion;
    }

    public void setFkcestatusautorizacion(String fkcestatusautorizacion) {
        this.fkcestatusautorizacion = fkcestatusautorizacion;
    }

    public String getFkctipoautorizacion() {
        return fkctipoautorizacion;
    }

    public void setFkctipoautorizacion(String fkctipoautorizacion) {
        this.fkctipoautorizacion = fkctipoautorizacion;
    }

    public String getCtransaccion() {
        return ctransaccion;
    }

    public void setCtransaccion(String ctransaccion) {
        this.ctransaccion = ctransaccion;
    }
    
    public String getFkcusuario() {
        return fkcusuario;
    }

    public void setFkcusuario(String fkcusuario) {
        this.fkcusuario = fkcusuario;
    }

    public Timestamp getFdesde() {
        return fdesde;
    }

    public void setFdesde(Timestamp fdesde) {
        this.fdesde = fdesde;
    }

    public Timestamp getFk2fhasta() {
        return fk2fhasta;
    }

    public void setFk2fhasta(Timestamp fk2fhasta) {
        this.fk2fhasta = fk2fhasta;
    }

    public Timestamp getFproceso() {
        return fproceso;
    }

    public void setFproceso(Timestamp fproceso) {
        this.fproceso = fproceso;
    }

    public String getNumeromensaje() {
        return numeromensaje;
    }

    public void setNumeromensaje(String numeromensaje) {
        this.numeromensaje = numeromensaje;
    }

    public String getFktoken() {
        return fktoken;
    }

    public void setFktoken(String fktoken) {
        this.fktoken = fktoken;
    }

    public String getCusuarioOrigen() {
        return cusuarioorigen;
    }

    public void setCusuarioOrigen(String cusuario_origen) {
        this.cusuarioorigen = cusuario_origen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
