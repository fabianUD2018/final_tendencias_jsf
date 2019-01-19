/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usuario;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author fcher
 */
@Named(value = "profesor")
@SessionScoped
public class Profesor extends Usuario implements Serializable {

    /**
     * Creates a new instance of Profesor
     */
    private String detalles;
    private String otraCosa;
    private boolean sesion;
    public Profesor() {
        sesion =false;
    }

    public boolean isSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getOtraCosa() {
        return otraCosa;
    }

    public void setOtraCosa(String otraCosa) {
        this.otraCosa = otraCosa;
    }
    
    
}
