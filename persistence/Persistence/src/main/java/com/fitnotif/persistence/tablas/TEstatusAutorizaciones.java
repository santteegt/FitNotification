package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TESTATUSAUTORIZACIONES
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TESTATUSAUTORIZACIONES")
public class TEstatusAutorizaciones implements Serializable {
    @Column (name="CESTATUSAUTORIZACION")
    @Id
    private String cestatusautorizacion;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="DESCRIPCION")
    private String descripcion;

    public String getCestatusautorizacion() {
        return cestatusautorizacion;
    }

    public void setCestatusautorizacion(String cestatusautorizacion) {
        this.cestatusautorizacion = cestatusautorizacion;
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
