/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usuario;

import com.cliente.Horario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;

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
    private String costoHora;
    private boolean sesion;
    private ArrayList<Horario> horarios;

    public Profesor() {
        sesion = false;
        horarios = new ArrayList<Horario>();
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
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

    public String getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(String costoHora) {
        this.costoHora = costoHora;
    }

    public void addHorario(Horario temp) {
        horarios.add(temp);
    }

    public boolean comprobarColision(Horario h) {

        String dia = h.getDia();
        LocalTime inicio = LocalTime.parse(h.getHoraInicio());
        LocalTime fin = LocalTime.parse(h.getHoraFin());
//        int inicio = Integer.parseInt(h.getHoraInicio());
//        int fin = Integer.parseInt(h.getHoraFin());
        boolean comprobacion = false;
        if (inicio.isAfter(fin) || inicio.equals(fin)) {
            comprobacion = true;
     
        } else {
            for (Horario t : horarios) {
                if (t.getDia().equals(dia) && t.getId()!=h.getId()) {
                 
                    //inicio es la nueva hora de inicio
                    //t es la hora del que ya poseo
                    if (inicio.isBefore(LocalTime.parse(t.getHoraFin())) && inicio.isAfter(LocalTime.parse(t.getHoraInicio()) )) {
                        System.out.println(inicio);
                        System.out.println(t.getHoraInicio());
                       
                        comprobacion = true;
                        //falta cundo se envuelve
                        break;
                    }else if (fin.isBefore(LocalTime.parse(t.getHoraFin())) && fin.isAfter(LocalTime.parse(t.getHoraInicio()) ) ) {
                    
                        comprobacion = true;
                        break;
                    }else if (fin.isAfter(LocalTime.parse(t.getHoraFin())) && inicio.isBefore(LocalTime.parse(t.getHoraInicio()) ) ) {
                      
                        comprobacion = true;
                    
                        break;
                    }else if (fin.equals(LocalTime.parse(t.getHoraFin())) && inicio.equals(LocalTime.parse(t.getHoraInicio()) ) ) {
                       
                        comprobacion = true;
                      
                        break;
                    }
                }
            }
        }
        return comprobacion;
    }

}
