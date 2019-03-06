package com.cliente;

import com.usuario.Profesor;
import java.io.Serializable;

import com.modelo.gestion.GestorBaseDatos;
import com.usuario.Cliente;
import com.usuario.Profesor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rodrigon
 */
@Named(value = "homeClienteController")
@SessionScoped
public class HomeClienteController implements Serializable {
    
    private ArrayList<Horario> horarios;
    private GestorBaseDatos gb;
    private ArrayList<Clase> clases;

    public HomeClienteController() {
        
        gb = GestorBaseDatos.obtenerGestor();  
        gb.realizaConexion();
        
    }

    
    public String crearClase(Profesor p, Horario h, Materia m){
        boolean disponibilidad = false;
        System.out.println(""+ m.getNombre());
        System.out.println("----------------------------------------");
//        String sql  = "select id_cedula from persona";
//        ResultSet st = gb.read(sql);
        String sql = "select h.id_horario, ma.id_materia " +
            "from horario h, profesorhorario ph, materia ma, materiaProfesor mp " +
            "where " +
            "h.id_horario = ph.id_horario and ph.id_profesor = "+p.getCedula()+"1 " +
            "and  h.hora_inicio <= '"+h.getHoraInicio()+"' and h.hora_fin >= '"+h.getHoraFin()+"' and h.dia = '"+h.getDia()+"' " +
            "and ma.id_materia = mp.id_materia and mp.id_profesor = "+p.getCedula()+"1 and ma.nombre = '"+m.getNombre()+"'";
        ResultSet st = gb.read(sql);

        try {
                while (st.next()) {
                     System.out.println(st.getString("id_horario"));
                     disponibilidad = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomeProfesorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("----------------------------------------");
        
        String sql2 = "select ca.id_clase, ca.reserva " +
            "from clase ca, profesor pr " +
            "where " +
            "ca.id_profesor = 10151 and ( ca.reserva != 'cancelada' and  ca.reserva != 'realizada' ) and " +
            "( " +
            "(ca.hora_inicio >= '"+h.getHoraInicio()+"' and ca.hora_fin <= '"+h.getHoraFin()+"') " +
            "or (ca.hora_inicio <= '"+h.getHoraInicio()+"' and ca.hora_fin <= '"+h.getHoraFin()+"' and ca.hora_fin >= '"+h.getHoraInicio()+"' ) " +
            "or (ca.hora_inicio <= '"+h.getHoraInicio()+"' and ca.hora_fin >= '"+h.getHoraFin()+"' ) " +
            "or (ca.hora_inicio >= '"+h.getHoraInicio()+"' and ca.hora_inicio <= '"+h.getHoraFin()+"' and ca.hora_fin >= '"+h.getHoraFin()+"' ) " +
            ")";
        ResultSet st2 = gb.read(sql2);

        try {
                while (st2.next()) {
                     System.out.println(st2.getString("id_clase"));
                     disponibilidad = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomeProfesorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("----------------------------------------");        
        System.out.println("Estado:     "+ disponibilidad);
        
        
        
        
        return "HomeCliente?faces-redirect=true";
    }
    
    
}
