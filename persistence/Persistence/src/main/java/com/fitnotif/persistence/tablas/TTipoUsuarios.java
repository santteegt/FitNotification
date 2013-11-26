package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TTIPOUSUARIOS
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TTIPOUSUARIOS")
public class TTipoUsuarios implements Serializable {
    @Column (name="CTIPOUSUARIO")
    @Id
    private String ctipousuario;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="DESCRIPCION")
    private String descripcion;

    public String getCtipousuario() {
        return ctipousuario;
    }

    public void setCtipousuario(String ctipousuario) {
        this.ctipousuario = ctipousuario;
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
