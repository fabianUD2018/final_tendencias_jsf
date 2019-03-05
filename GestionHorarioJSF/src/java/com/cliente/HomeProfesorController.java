/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cliente;

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

/**
 *
 * @author fcher
 */
@Named(value = "homeProfesorController")
@SessionScoped
public class HomeProfesorController implements Serializable {

    /**
     * Creates a new instance of HorarioController
     */
    private ArrayList<Horario> horarios;
    private GestorBaseDatos gb;
    private ArrayList<Clase> clases;

    public HomeProfesorController() {
        horarios = new ArrayList<>();
        clases = new ArrayList<>();
        gb = GestorBaseDatos.obtenerGestor();
        gb.realizaConexion();
    }

    public void insertarHorario(Horario h, Profesor p) {
        boolean error = p.comprobarColision(h);
        if (!error) {
            String sql = "insert into horario (id_horario, dia, hora_inicio, hora_fin) values "
                    + "(" + h.getId() + ", '" + h.getDia() + "', '" + h.getHoraInicio() + "', '" + h.getHoraFin() + "')";
            System.out.println(sql);
            gb.executeQ(sql);
            sql = "insert into profesorhorario values(" + h.getId() + "," + p.getCedula()+"1" + ")";
            gb.executeQ(sql);
        }
    }
    
    

    public void cargarHorarios(Profesor p) {
        p.getHorarios().clear();
        if (p.isSesion()) { 
            String sql = "select h.id_horario, h.dia, h.hora_inicio, h.hora_fin from horario h, profesorhorario ph, profesor p"
                    + " where h.id_horario = ph.id_horario and p.id_profesor = " + p.getCedula()+"1";
            ResultSet st = gb.read(sql);
            try {
                while (st.next()) {
                    Horario temp = new Horario();
                    temp.setId(st.getInt(1));
                    temp.setDia(st.getString(2));
                    temp.setHoraInicio(st.getString(3));
                    temp.setHoraFin(st.getString(4));
                    p.addHorario(temp);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomeProfesorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cargarClases(Profesor p){
        clases.clear();
        if (p.isSesion()) {
            String sql = "select * from clase, profesor where clase.id_profesor=profesor.id_profesor and profesor.id_profesor="+p.getCedula()+"1";
            System.out.println(sql);
            GestorBaseDatos gb2= new GestorBaseDatos();
            GestorBaseDatos gb3= new GestorBaseDatos();
            gb2.realizaConexion();
            gb3.realizaConexion();
            ResultSet st = gb.read(sql);
            ResultSet st2;
            ResultSet st3;
            try {
                while (st.next()){
                    Clase temp = new Clase();
                    temp.setProfesor(p);
                    temp.setId(st.getInt(1));           
                    temp.setFecha(st.getString(5));
                    temp.setHora_inicio(st.getString(6));
                    temp.setHora_fin(st.getString(7));
                    temp.setDetalles(st.getString(8));
                    temp.setReserva(st.getString(9));
                    temp.setColegio(st.getString(10));
                    temp.setNombre_alumno(st.getString(11));
                    temp.setDireccion(st.getString(12));
                    String idCliente = st.getString(3);
                    
                    sql ="select  nombre from materia where id_materia='"+st.getString(4)+"'";
                    System.out.println(sql);
                    st2 = gb2.read(sql);
                    st2.next();
                    temp.setMateria(st2.getString(1));

                    Cliente tempc= new Cliente();
                    sql ="select * from cliente, persona where cliente.id_cedula=persona.id_cedula and id_cliente='"+idCliente+"'";
                    st3 = gb3.read(sql);
                    st3.next();
                    System.out.println(sql);
                    tempc.setCedula(st3.getInt(2));
                    tempc.setDetalles(st3.getString(3));
                    tempc.setNombre(st3.getString(5));
                    tempc.setEdad(st3.getInt(8));
                    tempc.setCelular(st3.getInt(6));

                    temp.setCliente(tempc);
                    
                    clases.add(temp);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomeProfesorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String aceptarClase(Clase c, Profesor p){
        String sql="update clase set reserva='Aceptada' where id_clase="+c.getId();
        gb.executeQ(sql);
        return "GestionClasesProfesor?faces-redirect=true";
    }
    
    public String rechazarClase(Clase c, Profesor p){
        if(c.isSolicitada()){
            String sql="update clase set reserva='Rechaza' where id_clase="+c.getId();
            gb.executeQ(sql);
            return "GestionClasesProfesor?faces-redirect=true";
        }
        return "GestionClasesProfesor?faces-redirect=true";
    }
    
    public String cancelarClase(Clase c, Profesor p){
        if(c.isSolicitada()){
            String sql="update clase set reserva='Cancelada' where id_clase="+c.getId();
            gb.executeQ(sql);
            return "GestionClasesProfesor?faces-redirect=true";
        }
        return "GestionClasesProfesor?faces-redirect=true";
    }
    
    public void borrarHorario(Horario h) {
        gb.executeQ("delete from profesorhorario h  where h.id_horario = " + h.getId());

        gb.executeQ("delete from horario h  where h.id_horario = " + h.getId() );
    }

    public String editar(Horario h) {

        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 
        Horario temp= h.clone();
        sessionMap.put("editarH", temp);
        return "EditHorary?faces-redirect=true";
    }
    public String updateHorary(Horario h, Profesor p){
        boolean error = p.comprobarColision(h);
        if (!error) {
         String sql = "update  horario set  dia='"+ h.getDia()+"', hora_inicio='"+h.getHoraInicio()+"', hora_fin='"+h.getHoraFin()+"' where id_horario="+h.getId();
            System.out.println("no hubo error +" + sql);
            gb.executeQ(sql);
            return "HomeProfesor?faces-redirect=true";
        }
        System.out.println("hubo error XD");
        return "HomeProfesor?faces-redirect=true";
    }

    /**
     * @return the clases
     */
    public ArrayList<Clase> getClases() {
        return clases;
    }

}
