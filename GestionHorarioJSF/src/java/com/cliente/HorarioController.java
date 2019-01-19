/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cliente;

import com.modelo.gestion.GestorBaseDatos;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author fcher
 */
@Named(value = "horarioController")
@SessionScoped
public class HorarioController implements Serializable {

    /**
     * Creates a new instance of HorarioController
     */
    private ArrayList<Horario> horarios ;
    private GestorBaseDatos gb;
    public HorarioController() {
        horarios = new ArrayList<>();
        gb = GestorBaseDatos.obtenerGestor();
        gb.realizaConexion();
    }
    
    public void insertarHorario(Horario h){
        String sql = "insert into horario (id_horario, dia, hora_inicio, hora_fin) values "
                + "("+ h.getId()+", '"+ h.getDia()+"', '"+h.getHoraInicio()+"', '"+h.getHoraFin()+"')";
        System.out.println(sql);
        gb.executeQ(sql);
    }
    
}
