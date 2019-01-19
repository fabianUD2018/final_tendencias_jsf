/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usuario;

import com.modelo.gestion.GestorBaseDatos;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fcher
 */
@Named(value = "registroClienteController")
@SessionScoped
public class RegistroController implements Serializable {

    /**
     * Creates a new instance of RegistroClienteController
     */
    private GestorBaseDatos gb;

    public RegistroController() {

        gb = GestorBaseDatos.obtenerGestor();
        gb.realizaConexion();

    }

    public void registrarCliente(Cliente c) {
        String sql = "insert into persona  values "
                + "(" + c.getCedula() + ", '" + c.getNombre() + "', " + c.getCelular()+ ",'" +c.getPass()+ "'," + c.getEdad() + ")";
        System.out.println(sql);
        
        gb.executeQ(sql);
        
        sql = "insert into cliente values  ("+ c.getCedula() +", "+ c.getCedula() +" , '"+c.getDetalles()+"')";
        System.out.println(sql);
        gb.executeQ(sql);
    }
    
    public void registrarProfesor (Profesor c){
        String sql = "insert into persona  values "
                + "(" + c.getCedula() + ", '" + c.getNombre() + "', " + c.getCelular()+ ",'" +c.getPass()+ "'," + c.getEdad() + ")";
        System.out.println(sql);
        
        gb.executeQ(sql);
         sql = "insert into profesor values  ("+ c.getCedula() +", "+ c.getCedula() +" , '"+c.getDetalles()+"')";
        System.out.println(sql);
        gb.executeQ(sql);
        
    }
    
    public String iniciarSesionProfesor(Profesor c){
        String sql = "select * from persona where  id_cedula ="+ c.getCedula() +" and password = '"+ c.getPass()+"'";
                
        System.out.println(sql);
        ResultSet st = gb.read(sql);
        try {
            if (st.next()){
            c.setSesion(true);
                 return "index";//cambiar por la pagina a la que deba redirigir al cliente
            }else{
                return "index";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
       public String iniciarSesionCliente (Cliente c){
        String sql = "select * from persona where  id_cedula = "+ c.getCedula() +" and password = '"+ c.getPass()+"'";
                
        System.out.println(sql);
        ResultSet st = gb.read(sql);
        try {
            if (st.next()){
                 c.setSesion(true);
                 return "webpages/RegistroCliente";
            }else{
                return "index";
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
