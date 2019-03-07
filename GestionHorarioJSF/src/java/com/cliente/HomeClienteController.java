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
        clases = new ArrayList<>();
        gb.realizaConexion();
        
    }

    
    public String crearClase(Horario h, Clase c, Profesor p,Cliente cl){
        p.setCedula(obtenerIndiceP(p.getNombre()));
        System.out.println("asdasdasdasd"+p.getCedula());
        c.setProfesor(p);
        boolean disponibilidad = false;
        System.out.println(""+ c.getMateria());
        System.out.println("----------------------------------------");
//        String sql  = "select id_cedula from persona";
//        ResultSet st = gb.read(sql);
        String sql = "select h.id_horario, ma.id_materia " +
            "from horario h, profesorhorario ph, materia ma, materiaProfesor mp " +
            "where " +
            "h.id_horario = ph.id_horario and ph.id_profesor = "+c.getProfesor().getCedula()+" " +
            "and  h.hora_inicio <= '"+h.getHoraInicio()+"' and h.hora_fin >= '"+h.getHoraFin()+"' and h.dia = '"+h.getDia()+"' " +
            "and ma.id_materia = mp.id_materia and mp.id_profesor = "+c.getProfesor().getCedula()+" and ma.nombre = '"+c.getMateria()+"'";
        ResultSet st = gb.read(sql);

        try {
                while (st.next()) {
                     System.out.println(st.getString("id_horario"));
                     disponibilidad = true;
                     System.out.println("tengo horario dispobible");
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
                " , "+ cl.getCedula() + "1 , "+ idMateria + " , '"+ c.getFecha()+ "' , '"+
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
    public int obtenerIndiceP(String codigo) {
        ResultSet st = gb.read("select * from profesor, persona where persona.nombre = '"+codigo+"' and"
                + " profesor.id_cedula = persona.id_cedula");
        try {
            st.next();

            return Integer.parseInt(st.getString("id_profesor"));
        } catch (SQLException ex) {
            Logger.getLogger(HomeClienteController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public void cargarClases(Cliente p){
        clases.clear();
        if (p.isSesion()) {
            String sql = "select * from clase, cliente where clase.id_cliente =cliente.id_cliente and cliente.id_cliente="+p.getCedula()+"1";
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
                    temp.setCliente(p);
                    
                    temp.setId(st.getInt(1));           
                    temp.setFecha(st.getString(5));
                    temp.setHora_inicio(st.getString(6));
                    temp.setHora_fin(st.getString(7));
                    temp.setDetalles(st.getString(8));
                    temp.setReserva(st.getString(9));
                    temp.setColegio(st.getString(10));
                    temp.setNombre_alumno(st.getString(11));
                    temp.setDireccion(st.getString(12));
                    String idProfesor = st.getString("id_profesor");
                    
                    sql ="select  nombre from materia where id_materia='"+st.getString(4)+"'";
                    System.out.println(sql);
                    st2 = gb2.read(sql);
                    st2.next();
                    temp.setMateria(st2.getString(1));

                    Profesor tempc= new Profesor();
                    sql ="select * from profesor, persona where profesor.id_cedula=persona.id_cedula and id_profesor='"+idProfesor+"'";
                    st3 = gb3.read(sql);
                    st3.next();
                    System.out.println(sql);
                    tempc.setCedula(st3.getInt(2));                    
                    tempc.setNombre(st3.getString(5));
                    tempc.setEdad(st3.getInt(8));
                    tempc.setCelular(st3.getInt(6));

                    temp.setProfesor(tempc);
                    
                    clases.add(temp);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomeProfesorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Clase> getSolicitadas(){
        ArrayList<Clase> clasesAsignadas= new ArrayList();
        for (Clase clase : clases){
            if(clase.isSolicitada()){
            clasesAsignadas.add(clase);
            }
        }
        return clasesAsignadas;
    }
    
    public ArrayList<Clase> getAceptadas(){
        ArrayList<Clase> clasesAceptadas= new ArrayList();
        for (Clase clase : clases){
            if(clase.isAceptada()){
            clasesAceptadas.add(clase);
            }
        }
        return clasesAceptadas;
    }
    
    public String cancelarClase(Clase c){
        if(c.isSolicitada()){
            String sql="update clase set reserva='cancelada' where id_clase="+c.getId();
            gb.executeQ(sql);
            return "ClasesCliente?faces-redirect=true";
        }
        return "ClasesCliente?faces-redirect=true";
    }
    
    
}
