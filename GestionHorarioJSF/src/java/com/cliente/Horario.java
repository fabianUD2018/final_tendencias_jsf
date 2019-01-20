/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cliente;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author fcher
 */
@Named(value = "horario")
@SessionScoped
public class Horario implements Serializable {

    /**
     * Creates a new instance of Horario
     */
    private int  id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    
    public Horario() {
    }

    public Horario(int id, String dia, String inicio, String fin) {
        this.id= id;
        this.dia =dia;
        this.horaInicio= inicio;
        this.horaFin=fin;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public Horario clone(){
        return new Horario(id, dia, horaInicio, horaFin);
    }
}
