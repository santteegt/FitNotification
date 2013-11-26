package com.fitnotif.persistence.tablas;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Clase que representa la tabla TUSUARIODISPOSITIVOS
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TUSUARIODISPOSITIVOS")
public class TUsuarioDispositivos implements Serializable, Cloneable {
    @Column (name="ID")
    @Id
    private String id;
    @Column (name="FKCUSUARIO")
    @Id    
    private String fkcusuario;
    @Column (name="MODELO")
    @Id
    private String modelo;
    @Column (name="FHASTA")
    @Id
    private Timestamp fhasta;    
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="FCONEXION")
    private Timestamp fconexion;    
    @Column (name="ESTADO")
    private String estado;

    public TUsuarioDispositivos() {
    }

    public TUsuarioDispositivos(String id, String fkcusuario, String modelo, Timestamp fhasta){
        this.id=id;
        this.fkcusuario=fkcusuario;
        this.fhasta=fhasta;
        this.modelo=modelo;
    }
    
    public Object cloneMe(){
        TUsuarioDispositivos userDevice=null;
        try {
            userDevice=(TUsuarioDispositivos) this.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return userDevice;
    }
    
    public String getFkcusuario() {
        return fkcusuario;
    }

    public void setFkcusuario(String fkcusuario) {
        this.fkcusuario = fkcusuario;
    }

    public Timestamp getFhasta() {
        return fhasta;
    }

    public void setFhasta(Timestamp fhasta) {
        this.fhasta = fhasta;
    }

    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public Timestamp getFdesde() {
        return fdesde;
    }

    public void setFdesde(Timestamp fdesde) {
        this.fdesde = fdesde;
    }

    public Timestamp getFconexion() {
        return fconexion;
    }

    public void setFconexion(Timestamp fconexion) {
        this.fconexion = fconexion;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

     
    
}
