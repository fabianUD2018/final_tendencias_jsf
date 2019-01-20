/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cliente;

import com.modelo.gestion.GestorBaseDatos;
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

    public HomeProfesorController() {
        horarios = new ArrayList<>();
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
            sql = "insert into profesorhorario values(" + h.getId() + "," + p.getCedula() + ")";
            gb.executeQ(sql);
        }
    }

    public void cargarHorarios(Profesor p) {
        p.getHorarios().clear();
        if (p.isSesion()) {
            String sql = "select h.id_horario, h.dia, h.hora_inicio, h.hora_fin from horario h, profesorhorario ph, profesor p"
                    + " where h.id_horario = ph.id_horario and p.id_profesor = " + p.getCedula();
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

}
