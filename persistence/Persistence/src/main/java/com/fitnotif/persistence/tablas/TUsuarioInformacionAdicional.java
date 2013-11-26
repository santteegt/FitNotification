package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TUSUARIOINFORMACIONADICIONAL
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TUSUARIOINFORMACIONADICIONAL")
public class TUsuarioInformacionAdicional implements Serializable, Cloneable {
    @Column (name="FKCUSUARIO")
    @Id
    private String fkcusuario;
    @Column (name="FHASTA")
    @Id
    private Timestamp fhasta;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="NOMBRE")
    private String nombre;
    @Column (name="MAIL", nullable=false)
    private String mail;
    @Column (name="KEYWORD", nullable=false)
    private String keyword;

    public Object cloneMe(){
        TUsuarioInformacionAdicional newUserInfo=null;
        try {
            newUserInfo = (TUsuarioInformacionAdicional) this.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return newUserInfo;
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

    public Timestamp getFhasta() {
        return fhasta;
    }

    public void setFhasta(Timestamp fhasta) {
        this.fhasta = fhasta;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
