/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cliente;

import com.usuario.Cliente;
import com.usuario.Profesor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author leml
 */

@Named(value = "clase")
@SessionScoped
public class Clase implements Serializable {

    private int id;
    private String materia;
    private String colegio;
    private String detalles;
    private String fecha;
    private int grado;
    private String hora_inicio;
    private String hora_fin;
    private String nombre_alumno;
    private Profesor profesor;
    private Cliente cliente;
    private String reserva;
    private String direccion;
    
    public Clase() {
        
    }

    public Clase(int aId, String aMateria, String aColegio, String aDetalles, String aFecha, String aHora_inicio, String aHora_fin, String aNombre_alumno, int aGrado, Cliente aCliente, Profesor aProfesor, String aDireccion) {
        id = aId;
        materia = aMateria;
        colegio = aColegio;
        detalles = aDetalles;
        fecha = aFecha;
        grado = aGrado;
        hora_inicio = aHora_inicio;
        hora_fin = aHora_fin;
        nombre_alumno = aNombre_alumno;
        cliente = aCliente;
        profesor = aProfesor;
        reserva = "solicitada";
        direccion = aDireccion;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
        
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @return the colegio
     */
    public String getColegio() {
        return colegio;
    }

    /**
     * @param colegio the colegio to set
     */
    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    /**
     * @return the detalles
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the grado
     */
    public int getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(int grado) {
        this.grado = grado;
    }

    /**
     * @return the hora_inicio
     */
    public String getHora_inicio() {
        return hora_inicio;
    }

    /**
     * @param hora_inicio the hora_inicio to set
     */
    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    /**
     * @return the hora_fin
     */
    public String getHora_fin() {
        return hora_fin;
    }

    /**
     * @param hora_fin the hora_fin to set
     */
    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    /**
     * @return the nombre_alumno
     */
    public String getNombre_alumno() {
        return nombre_alumno;
    }

    /**
     * @param nombre_alumno the nombre_alumno to set
     */
    public void setNombre_alumno(String nombre_alumno) {
        this.nombre_alumno = nombre_alumno;
    }

    /**
     * @return the profesor
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the reserva
     */
    public String getReserva() {
        return reserva;
    }

    /**
     * @param reserva the reserva to set
     */
    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public boolean isSolicitada() {
        if (reserva.equals("solicitada")){
            return true;
        }
        return false;
    }
    
    public boolean isAceptada() {
        if (reserva.equals("aceptada")){
            return true;
        }
        return false;
    }
}
