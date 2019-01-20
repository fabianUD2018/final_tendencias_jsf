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
    private String otraCosa;
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

    public String getOtraCosa() {
        return otraCosa;
    }

    public void setOtraCosa(String otraCosa) {
        this.otraCosa = otraCosa;
    }

    public void addHorario(Horario temp) {
        horarios.add(temp);
    }

    public boolean comprobarColision(Horario h) {
        for (Horario t : horarios) {
            System.out.println("hora: " + t.getId());
        }

        String dia = h.getDia();
        LocalTime inicio = LocalTime.parse(h.getHoraInicio());
        LocalTime fin = LocalTime.parse(h.getHoraFin());
//        int inicio = Integer.parseInt(h.getHoraInicio());
//        int fin = Integer.parseInt(h.getHoraFin());
        boolean comprobacion = false;
        if (inicio.isAfter(fin) || inicio.equals(fin)) {
            comprobacion = true;
            System.out.println("comprobacion: " + comprobacion);
        } else {
            for (Horario t : horarios) {
                if (t.getDia().equals(dia) && t.getId()!=h.getId()) {
                    System.out.println("----------");
                    System.out.println(t.getDia());
                    System.out.println(dia);
                    System.out.println("----------");
                    //inicio es la nueva hora de inicio
                    //t es la hora del que ya poseo
                    if (inicio.isBefore(LocalTime.parse(t.getHoraFin())) && inicio.isAfter(LocalTime.parse(t.getHoraInicio()) )) {
                        System.out.println("***" + inicio.isBefore(LocalTime.parse(t.getHoraFin())));
                        System.out.println(inicio);
                        System.out.println(t.getHoraInicio());
                        System.out.println("***" + inicio.equals(LocalTime.parse(t.getHoraInicio())));
                        comprobacion = true;
                        System.out.println("xd");
                        //falta cundo se envuelve
                        break;
                    }else if (fin.isBefore(LocalTime.parse(t.getHoraFin())) && fin.isAfter(LocalTime.parse(t.getHoraInicio()) ) ) {
                        System.out.println("***" + inicio.isBefore(LocalTime.parse(t.getHoraFin())));
                        System.out.println(inicio);
                        System.out.println(t.getHoraInicio());
                        System.out.println("***" + inicio.equals(LocalTime.parse(t.getHoraInicio())));
                        comprobacion = true;
                        System.out.println("xd");
                        //falta cundo se envuelve
                        break;
                    }else if (fin.isAfter(LocalTime.parse(t.getHoraFin())) && inicio.isBefore(LocalTime.parse(t.getHoraInicio()) ) ) {
                        System.out.println("***" + inicio.isBefore(LocalTime.parse(t.getHoraFin())));
                        System.out.println(inicio);
                        System.out.println(t.getHoraInicio());
                        System.out.println("***" + inicio.equals(LocalTime.parse(t.getHoraInicio())));
                        comprobacion = true;
                        System.out.println("xd");
                        //falta cundo se envuelve
                        break;
                    }else if (fin.equals(LocalTime.parse(t.getHoraFin())) && inicio.equals(LocalTime.parse(t.getHoraInicio()) ) ) {
                        System.out.println("***" + inicio.isBefore(LocalTime.parse(t.getHoraFin())));
                        System.out.println(inicio);
                        System.out.println(t.getHoraInicio());
                        System.out.println("***" + inicio.equals(LocalTime.parse(t.getHoraInicio())));
                        comprobacion = true;
                        System.out.println("xd");
                        //falta cundo se envuelve
                        break;
                    }
                }
            }
        }
        return comprobacion;
    }

}
