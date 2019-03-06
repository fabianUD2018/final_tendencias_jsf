package com.cliente;


import com.modelo.gestion.GestorBaseDatos;
import com.usuario.Cliente;
import com.usuario.Profesor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private GestorBaseDatos gb;
    private ArrayList<Clase> clases;

    public HomeClienteController() {
        
        gb = GestorBaseDatos.obtenerGestor();  
        gb.realizaConexion();
        
    }

    
    public String crearClase(Horario h, Clase c, Profesor p,Cliente cl){
        c.setProfesor(p);
        boolean disponibilidad = false;
        System.out.println(""+ c.getMateria());
        System.out.println("----------------------------------------");
//        String sql  = "select id_cedula from persona";
//        ResultSet st = gb.read(sql);
        String sql = "select h.id_horario, ma.id_materia " +
            "from horario h, profesorhorario ph, materia ma, materiaProfesor mp " +
            "where " +
            "h.id_horario = ph.id_horario and ph.id_profesor = "+c.getProfesor().getCedula()+"1 " +
            "and  h.hora_inicio <= '"+h.getHoraInicio()+"' and h.hora_fin >= '"+h.getHoraFin()+"' and h.dia = '"+h.getDia()+"' " +
            "and ma.id_materia = mp.id_materia and mp.id_profesor = "+c.getProfesor().getCedula()+"1 and ma.nombre = '"+c.getMateria()+"'";
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
        
        if (disponibilidad) {
            
            
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaagrave");
            
            int idMateria = obtenerIndiceM("materia", c.getMateria());
            int llaveP = obtenerIndice("clase", "clase") + 1 ;
              String sql3="insert into clase values ( "+llaveP+" , "+c.getProfesor().getCedula() +
                "1 , "+ cl.getCedula() + "1 , "+ idMateria + " , '"+ c.getFecha()+ "' , '"+
                h.getHoraInicio()+ "' , '"+ h.getHoraFin()+ "' , '"+ c.getDetalles()+ "' , "+
                "'solicitada', '"+ c.getColegio()+ "' , '"+ c.getNombre_alumno()+ "' , '" +
                c.getDireccion()+ "' )";
            gb.executeQ(sql3);  
            
        }      
            
        return "HomeCliente?faces-redirect=true";
    }
    
    public int obtenerIndice(String entidad, String codigo) {
        ResultSet st = gb.read("select * from " + entidad + " ORDER BY id_" + codigo + " DESC LIMIT 1");
        try {
            st.next();

            return Integer.parseInt(st.getString("id_" + codigo));
        } catch (SQLException ex) {
            Logger.getLogger(HomeClienteController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public int obtenerIndiceM(String entidad, String codigo) {
        ResultSet st = gb.read("select * from materia where nombre = '"+codigo+"'");
        try {
            st.next();

            return Integer.parseInt(st.getString("id_materia"));
        } catch (SQLException ex) {
            Logger.getLogger(HomeClienteController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public void cargarClases(Cliente c){
        
    }
    
    
}
