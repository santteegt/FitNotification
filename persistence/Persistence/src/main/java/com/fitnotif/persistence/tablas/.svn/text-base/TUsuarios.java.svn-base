package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TUSUARIOS
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TUSUARIOS")
public class TUsuarios implements Serializable {
    @Column (name="CUSUARIO")
    @Id    
    private String cusuario;
    @Column (name="FKFHASTA")
    @Id
    private Timestamp fkfhasta;
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="FKCROL")
    private Integer fkcrol;
    @Column (name="FKCTIPOUSUARIO")
    private String fkctipousuario;
    @Column (name="FVIGENCIADESDE")
    private Timestamp fvigenciadesde;
    @Column (name="FVIGENCIAHASTA")
    private Timestamp fvigenciahasta;
    @Column (name="ACTIVO")
    private Boolean activo;

    
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getFkcrol() {
        return fkcrol;
    }

    public void setFkcrol(Integer fkcrol) {
        this.fkcrol = fkcrol;
    }

    public String getFkctipousuario() {
        return fkctipousuario;
    }

    public void setFkctipousuario(String fkctipousuario) {
        this.fkctipousuario = fkctipousuario;
    }

    public String getCusuario() {
        return cusuario;
    }

    public void setCusuario(String cusuario) {
        this.cusuario = cusuario;
    }

    public Timestamp getFdesde() {
        return fdesde;
    }

    public void setFdesde(Timestamp fdesde) {
        this.fdesde = fdesde;
    }

    public Timestamp getFkfhasta() {
        return fkfhasta;
    }

    public void setFkfhasta(Timestamp fkfhasta) {
        this.fkfhasta = fkfhasta;
    }

    public Timestamp getFvigenciadesde() {
        return fvigenciadesde;
    }

    public void setFvigenciadesde(Timestamp fvigenciadesde) {
        this.fvigenciadesde = fvigenciadesde;
    }

    public Timestamp getFvigenciahasta() {
        return fvigenciahasta;
    }

    public void setFvigenciahasta(Timestamp fvigenciahasta) {
        this.fvigenciahasta = fvigenciahasta;
    }

    
}
