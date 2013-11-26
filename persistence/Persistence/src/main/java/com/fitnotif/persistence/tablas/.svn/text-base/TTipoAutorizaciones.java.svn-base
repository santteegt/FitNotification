package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TTIPOAUTORIZACIONES
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TTIPOAUTORIZACIONES")
public class TTipoAutorizaciones implements Serializable {
    @Column (name="CTIPOAUTORIZACION")
    @Id
    private Integer ctipoautorizacion;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="DESCRIPCION")
    private String descripcion;

    public Integer getCtipoautorizacion() {
        return ctipoautorizacion;
    }

    public void setCtipoautorizacion(Integer ctipoautorizacion) {
        this.ctipoautorizacion = ctipoautorizacion;
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

