package com.fitnotif.persistence.tablas;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Clase que representa la tabla TTOKENS
 * @author malgia
 * @version 1.0
 */
@Entity
@Table (name="TTOKENS")
public class TTokens implements Serializable {
    @Column (name="TOKEN")
    @Id
    private String token;   
    @Column (name="FDESDE")
    private Timestamp fdesde;

    public TTokens() {
    }

    public TTokens(String token){
        this.token=token;
    }
    
    public Timestamp getFdesde() {
        return fdesde;
    }

    public void setFdesde(Timestamp fdesde) {
        this.fdesde = fdesde;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
