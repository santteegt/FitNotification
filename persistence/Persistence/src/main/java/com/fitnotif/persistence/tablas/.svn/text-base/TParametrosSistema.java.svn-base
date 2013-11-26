package com.fitnotif.persistence.tablas;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Clase que representa la tabla TPARAMETROSSISTEMA
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TPARAMETROSSISTEMA")
public class TParametrosSistema implements Serializable, Cloneable {
    @Column (name="CPARAMENTRO")
    @Id
    private String cparametro;    
    @Column (name="FHASTA")
    @Id
    private Timestamp fhasta;    
    @Column (name="FDESDE")
    private Timestamp fdesde;
    @Column (name="VALOR_ENTERO")
    private Integer valorentero;
    @Column (name="VALOR_CADENA")
    private String valorcadena;

    public Object cloneMe(){
        TParametrosSistema parametrossistema=null;
        try {
            parametrossistema=(TParametrosSistema) this.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return parametrossistema;
    }    
    
    public String getCparametro() {
        return cparametro;
    }

    public void setCparametro(String cparametro) {
        this.cparametro = cparametro;
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

    public String getValorcadena() {
        return valorcadena;
    }

    public void setValorcadena(String valorcadena) {
        this.valorcadena = valorcadena;
    }

    public Integer getValorentero() {
        return valorentero;
    }

    public void setValorentero(Integer valorentero) {
        this.valorentero = valorentero;
    }
}
