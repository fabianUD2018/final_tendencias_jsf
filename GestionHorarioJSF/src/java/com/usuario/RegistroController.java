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

    public String registrarCliente(Cliente c) {
        String sql = "insert into persona  values "
                + "(" + c.getCedula() + ", '" + c.getNombre() + "', " + c.getCelular()+ ",'" +c.getPass()+ "'," + c.getEdad() + ")";
        System.out.println(sql);
        
        gb.executeQ(sql);
        
        sql = "insert into cliente values  ("+ c.getCedula()+"1" +", "+ c.getCedula() +" , '"+c.getDetalles()+"')";
        System.out.println(sql);
        gb.executeQ(sql);
        return "index?faces-redirect=true";
    }
    
    public String registrarProfesor (Profesor c){
        String[] materias = c.getDetalles().split(",");
        ResultSet st;
        
        String sql = "insert into persona  values "
                + "(" + c.getCedula() + ", '" + c.getNombre() + "', " + c.getCelular()+ ",'" +c.getPass()+ "'," + c.getEdad() + ")";
        System.out.println(sql);
        
        gb.executeQ(sql);
         sql = "insert into profesor values  ("+ c.getCedula()+"1" +", "+ c.getCedula() +" , '"+c.getCostoHora()+"')";
        System.out.println(sql);
        gb.executeQ(sql);
        
        for (String materia : materias) {
            sql="select * from materia where nombre='"+materia+"'";
            System.out.println(sql);
            st = gb.read(sql);
            try {
                if (!st.next()){
                    registrarMaterias(materia);
                    sql="insert into materiaprofesor values ('"+obtenerIndice("materia", "materia")+"','"+c.getCedula()+"1"+"' )";
                    gb.executeQ(sql); 
                }else{
                    sql="insert into materiaprofesor values ('"+st.getString("id_materia")+"','"+c.getCedula()+"1"+"' )";
                    gb.executeQ(sql);    
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "index?faces-redirect=true";
        
    }
    
    public void registrarMaterias(String materia){
        int id=obtenerIndice("materia", "materia")+1;
        String sql= "insert into materia values ("+(id)+",'"+materia+"')";
        System.out.println(sql);
        gb.executeQ(sql);
        
    }
    
    
    
    public String iniciarSesionProfesor(Profesor c){
        String sql = "	select * from persona, profesor where persona.id_cedula=profesor.id_cedula and profesor.id_cedula="+ c.getCedula() +" and persona.password= '"+ c.getPass()+"'";
                
        System.out.println(sql);
        ResultSet st = gb.read(sql);
        try {
            if (st.next()){
            c.setSesion(true);
                 return "HomeProfesor";
            }else{
                return "index";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
       public String iniciarSesionCliente (Cliente c){
        String sql = "select * from persona, cliente where persona.id_cedula=cliente.id_cedula and cliente.id_cedula="+ c.getCedula() +" and persona.password = '"+ c.getPass()+"'";
                
        System.out.println(sql);
        ResultSet st = gb.read(sql);
        try {
            if (st.next()){
                 c.setSesion(true);
                 return "ClasesCliente";//cambiar por donde deba redirigir al cliente
            }else{
                return "index";
            }   
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
       
       
       
    public int obtenerIndice(String entidad, String codigo) {
        ResultSet st = gb.read("select * from " + entidad + " ORDER BY id_" + codigo + " DESC LIMIT 1");
        try {
            st.next();
            return Integer.parseInt(st.getString("id_" + codigo));
        } catch (SQLException ex) {
            System.out.println("lesto");
            return 0;
        }
    }  
    
   
}
