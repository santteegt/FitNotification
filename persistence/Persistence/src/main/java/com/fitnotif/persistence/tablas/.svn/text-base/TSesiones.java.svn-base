package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TSESIONES
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TSESIONES")
public class TSesiones implements Serializable {
    @Column (name="SESIONID")
    @Id
    private String sesionid;
    @Column (name="CUSUARIO")
    @Id
    private String cusuario;    
    @Column (name="FINICIO")
    private Timestamp finicio;
    @Column (name="FFIN")
    private Timestamp ffin;

    public String getCusuario() {
        return cusuario;
    }

    public void setCusuario(String cusuario) {
        this.cusuario = cusuario;
    }

    public Timestamp getFfin() {
        return ffin;
    }

    public void setFfin(Timestamp ffin) {
        this.ffin = ffin;
    }

    public Timestamp getFinicio() {
        return finicio;
    }

    public void setFinicio(Timestamp finicio) {
        this.finicio = finicio;
    }

    public String getSesionid() {
        return sesionid;
    }

    public void setSesionid(String sesionid) {
        this.sesionid = sesionid;
    }
}
