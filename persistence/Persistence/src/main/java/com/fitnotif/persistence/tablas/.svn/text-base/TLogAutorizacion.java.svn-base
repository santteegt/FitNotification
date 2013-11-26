package com.fitnotif.persistence.tablas;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Clase que representa la tabla TLOGAUTORIZACION
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TLOGAUTORIZACION")
public class TLogAutorizacion implements Serializable {
    @Column (name="NUMEROMENSAJE")
    @Id
    private String numeromensaje;
    @Column (name="CESTATUSAUTORIZACION")
    @Id
    private String cestatusautorizacion;    
    @Column (name="MENSAJE_XML")
    @Lob @Basic(fetch= FetchType.LAZY)
    private byte[] mensajexml;
    @Column (name="ORIGEN_MENSAJE")
    private Boolean origenmensaje;

    public String getCestatusautorizacion() {
        return cestatusautorizacion;
    }

    public void setCestatusautorizacion(String cestatusautorizacion) {
        this.cestatusautorizacion = cestatusautorizacion;
    }

    public byte[] getMensajeXml() {
        return mensajexml;
    }

    public void setMensajeXml(byte[] mensaje_xml) {
        this.mensajexml = mensaje_xml;
    }

    public String getNumeromensaje() {
        return numeromensaje;
    }

    public void setNumeromensaje(String numeromensaje) {
        this.numeromensaje = numeromensaje;
    }

    public Boolean getOrigenMensaje() {
        return origenmensaje;
    }

    public void setOrigenMensaje(Boolean origen_mensaje) {
        this.origenmensaje = origen_mensaje;
    }
    
    
}

