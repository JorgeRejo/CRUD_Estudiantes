/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jorge
 */
public class estudiante extends persona {
    private String carne;
    private int id_tipo_sangre;
    private Conexion cn;
    public estudiante() {}

    public estudiante(String carne, int id_estudiante, String nombres
            , String apellidos, String direccion,String telefono
            , String correo_electronico,  int id_tipo_sangre,String fecha_nacimiento) {
        
        super( id_estudiante,  nombres,  apellidos,  direccion,  telefono,
             correo_electronico,  id_tipo_sangre ,  fecha_nacimiento) ;
        this.carne = carne;
        this.id_tipo_sangre = id_tipo_sangre;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public int getid_tipo_sangre() {
        return id_tipo_sangre;
    }

    public void setId_puesto(int id_tipo_sangre) {
        this.id_tipo_sangre = id_tipo_sangre;
    }
    public HashMap drop_sangre(){
        HashMap<String,String> drop = new HashMap();
        try {
            cn = new Conexion();
            String query= "SELECT id_tipo_sangre as id, sangre FROM tipos_sangre; ";
            cn.abrir_conexion();
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            while (consulta.next()){
                drop.put(consulta.getString("id"), consulta.getString("sangre"));
            }  
            cn.cerrar_conexion();
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            
         }
        return drop;
    }
    
    public DefaultTableModel leer() {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "SELECT e.id_estudiante as id,"
                    + "e.carne,"
                    + "e.nombres,"
                    + "e.apellidos,"
                    + "e.direccion,"
                    + "e.telefono,"
                    + "e.fecha_nacimiento,"
                    + "p.sangre,"
                    + "p.id_tipo_sangre "
                    + " FROM estudiantes as e "
                    + "inner join tipos_sangre as p "
                    + "on e.id_tipo_sangre = p.id_tipo_sangre;";
            System.out.println(query);
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            
           
            
            String encabezado[] = {"id", "carne", "nombres", "apellidos", "direccion", "telefono", "nacimiento", "sangre", "id_tipo_sangre"};
            tabla.setColumnIdentifiers(encabezado);
            String datos[] = new String[9];
            while (consulta.next()) {
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("carne");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("sangre");
                datos[8] = consulta.getString("id_tipo_sangre");
                tabla.addRow(datos);

            }

            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tabla;
    }
    
    @Override
    public int agregar(){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "insert into estudiantes (carne,nombres,apellidos"
                    + ",direccion,telefono,correo_electronico "
                    + ",id_tipo_sangre,fecha_nacimiento) values(?,?,?,?,?,?,?,?);";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getCarne());
            parametro.setString(2,getNombres());
            parametro.setString(3,getApellidos());
            parametro.setString(4,getDireccion());
            parametro.setString(5,getTelefono());
            parametro.setString(6,getCorreo_electronico());
            parametro.setInt(7, getId_tipo_sangre());
            parametro.setString(8,getFecha_nacimiento());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
    
    @Override
    public int modificar (){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "update estudiantes set carne = ?,nombres= ?,apellidos= ?"
                    + ",direccion= ?,telefono= ?,fecha_nacimiento= ?"
                    + ",correo_electronico= ?,id_tipo_sangre = ? "
                    + "where id_estudiante = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getCarne());
            parametro.setString(2,getNombres());
            parametro.setString(3,getApellidos());
            parametro.setString(4,getDireccion());
            parametro.setString(5,getTelefono());
            parametro.setString(6,getFecha_nacimiento());
            parametro.setString(7,getCorreo_electronico());
            parametro.setInt(8, getId_tipo_sangre());
            parametro.setInt(9, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
    
    @Override
    public int eliminar (){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "delete from estudiantes where id_estudiante = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
}
