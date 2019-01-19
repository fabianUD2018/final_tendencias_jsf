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
@Named(value = "cliente")
@SessionScoped
public class Cliente extends Usuario implements Serializable {
    

     private String detalles;
     private boolean sesion;
    /**
     * Creates a new instance of Cliente
     */
    public Cliente() {
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
    
  

    
}
