package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TUSUARIOPASSWORD
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TUSUARIOPASSWORD")
public class TUsuarioPassword implements Serializable, Cloneable{
    @Column (name="FKCUSUARIO")
    @Id
    private String fkcusuario;
    @Column (name="FHASTA")
    @Id
    private Timestamp fhasta;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="PASSWORD", nullable=false)
    private String password;
    @Column (name="FCADUCIDAD")
    private Date fcaducidad;

    public Object cloneMe(){
        TUsuarioPassword newUserPassword=null;
        try {
            newUserPassword = (TUsuarioPassword) this.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return newUserPassword;
    }
    
    public String getFkcusuario() {
        return fkcusuario;
    }

    public void setFkcusuario(String fkcusuario) {
        this.fkcusuario = fkcusuario;
    }

    public Date getFcaducidad() {
        return fcaducidad;
    }

    public void setFcaducidad(Date fcaducidad) {
        this.fcaducidad = fcaducidad;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}