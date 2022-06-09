package ConexionDB;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta clase es la que se encargar de realizar la conexion y desconexion con la base de datos,
 * ademas de  poder entregar el puntero de dicha conexion a la clase SQLcode para su posterior uso.
 */

public class Conexion {
    private static Connection conn;
    
    //private final static String driver="com.mysql.jdbc.Driver";
    private final static String driver="org.postgresql.Driver";
    private final static String user="bdi2020n";
    private final static String password="bdi2020n";
    //jdbc:mysql://25.2.254.93:3306/yonson
    //jdbc:mysql://Localhost:3306/yonson
    
    //private final static String url="jdbc:mysql://25.2.254.93:3306/yonson";
//152.74.52.250
    
    private final static String url="jdbc:postgresql://plop.inf.udec.cl/?database=bdi2020n&currentSchema=empresasoftware";

    public Conexion(){}
    
    
    /** 
     * Establece la Conexion con el Servidor ocupando las constantes ya declaradas.
     */
    public static boolean conectar (){
        
        conn=null;
        
        System.out.println(conn);
        try{
            Class.forName(driver);
            System.out.println("Conección en proceso..");
        }
        catch(Exception e){
            System.out.println("e");
            
        }
        try{
            

            conn=DriverManager.getConnection(url,user,password);
            System.out.println("Conectado..");
            
            
        }
        catch( SQLException e){
            
            System.out.println(e);
            System.out.println("Conexion fallida..");
            return false;
            //System.exit(0);
//JOptionPane.showMessageDialog(null, "No se puede localizar al servidor");  
        }
        
        if(conn!=null)return true;
        else return false;
        
        
    
       //
    }
    
    
    /** 
     * Nos entrega la conexion con el servidor.
     * @return El puntero de la conexion extablecida con el servidor.
     */
    public static Connection getConnection(){
        return conn;
    }
    
    
    /**     
     * Se encarga de devolver al valor nulo a las instancias de Connection en el Programa.
     * @see SQLcode#desConn() 
     */
    public static void desconectar (){
        conn=null; 
        SQLcode.desConn();
        if(conn==null){
            System.out.println("Coneccion Terminada..");
        }
    }
}