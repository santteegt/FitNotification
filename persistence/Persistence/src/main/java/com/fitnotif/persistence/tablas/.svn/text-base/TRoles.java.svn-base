package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TROLES
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TROLES")
public class TRoles implements Serializable {
    @Column (name="CROL")
    @Id
    private Integer crol;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="DESCRIPCION")
    private String descripcion;

    public Integer getCrol() {
        return crol;
    }

    public void setCrol(Integer crol) {
        this.crol = crol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFdesde() {
        return fdesde;
    }

    public void setFdesde(Timestamp fdesde) {
        this.fdesde = fdesde;
    }
}
