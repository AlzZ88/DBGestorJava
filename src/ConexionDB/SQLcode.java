package ConexionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 * Esta clase consiste en su totalidad de metodos y atributos estaticos
 * los cuales sirven para realizar las consultas o actualizaciones necesarias
 * desde java a la base de datos MySQL.
 */
public class SQLcode {
    private static Statement stmt;
    private static ResultSet rs;
    private static Connection conn;
    public SQLcode(){}
    /**
     * Como su nombre indica, copia el puntero de la conexion para su uso posterior.
     * @see Conexion#getConnection() 
     */
    public static void copiarConn(){
        conn=null;
        conn=Conexion.getConnection();
    }
    /**
     * Vuelve nulo el atributo conn para que ya no se tenga acceso a la base de datos.
     */
    public static void desConn(){
        conn=null;
    }
    
    /** 
     * Usado para ver si el numero ingresado es una ID valida, en caso de
     * ser asi, retorna true.
     * @param tabla La tabla en la cual se desea confirmar el ID.
     * @param id El numero identificador que se desea confirmar.
     * @return true si la tabla contiene el ID consultado, false en caso contrario.
     */
    
    /**
     * Este metodo es el encargado de reemplazar un dato ya existente por otro dato
     * nuevo ingresado por el usuario.
     * 
     * @param tabla La tabla en la cual se hará la actualizacion
     * @param id El identificador de la fila en la cual se esta reemplazando el antiguo dato
     * @param campo El nombre de la columna en la cual se realizara el reemplazo.
     * @param nuevoDato El dato que se ingresara en el reemplazo.
     */
    public static void update(String tabla,String id,String campo,String nuevoDato ){ 
        String sql="UPDATE "+tabla+" SET "+campo+" = '"+nuevoDato+"'" +"WHERE ID = '"+id+"' ;";
        try{
            stmt=conn.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException ex){}
                    stmt=null;
            }
        }
    }
    /**
     * Utilizado para borrar una fila en especifico de alguna tabla.
     * 
     * @param tabla La tabla en la que se borrara la fila indicada por su ID
     * @param id El numero identificador de la fila que será borrada.
     */
    public static void borrar(String tabla,String id){  
        try{
            stmt=conn.createStatement();
            stmt.executeUpdate("DELETE FROM "+tabla+" WHERE ID = '"+id+"';");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException ex){}
                    stmt=null;
            }
        }
    }
    /**
     * Utilizado para como su nombre indica, crear una nueva tabla en la base de datos.
     * 
     * @param nombreTabla El nombre de la nueva tabla.
     * @param nombreCampo Un arreglo de strings indicando el nombre de cada columna de la nueva tabla.
     * @param tipoCampo Un arreglo de strings indicando el tipo de dato que almacenara el campo asociado.
     */
    
    /**
     * Confirma el rol del usuario ingresado
     * 
     * @param usuario es el usuario al cual se le consulta.
     * @param rol es el rol por el que se pregunta.
     * @return true, si el rol es el consultado, false en caso contrario.
     */
    public static boolean consultaB(String user,String pass){
        //String sql="SELECT p.Rut,t.serie as Tarjeta,s.Número as Suscripción,So.Nombre  from Persona as p,Cliente as c, Tarjeta as t, Suscripción as s,Software as So where p.Rut=c.Rut and c.Id_Cliente=t.Id_Cliente and c.Id_Cliente=s.Id_Cliente and So.Código=s.Código;";
        String sql="SELECT u.usuario, u.password from Usuario as u where u.usuario='"+user+"' and u.password='"+pass+"';";
        
        System.out.println(sql);
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            int i=0;
            return rs.next(); 
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"SQLExepcion:"+ex.getMessage());
            return false;
        }
    }
    public static boolean consulta(String tabla){
        String sql="select * from "+tabla+";";
        System.out.println(sql);
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            int i=0;
            while(rs.next()){
                System.out.println("iterandoooo!!!" +i);
                i++;
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"SQLExepcion:"+ex.getMessage());
        
        }
        finally{
            if(rs!=null){
                try{
                    rs.close();
                }
                catch(SQLException ex){
                    rs=null;
                }   
            }
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException ex){
                    stmt=null;
                }
            } 
        }
        
        return false;
    }
    

    
    /**
     * Modifica el Stock del producto deseado incrementado o decrementandolo.
     * 
     * @param opcion es un string que puede ser "incrementar" o "decrementar".
     * @param id id del dato a modificar.
     * @param cantidad cantidad que se suma o resta.
     * @return retorna true si se logro modificar el stock y false en su defecto.
     * 
     */
    
    /**
     * Uno de los pilares principales del programa, utilizado para realizar la busqueda de alguna
     * consulta ingresada por teclado en alguna tabla especificada.
     * 
     * @param tabla La tabla en la que se desea realizar la busqueda.
     * @param campo El campo en el cual se desea realizar la busqueda, en caso de recibir la string "Todos" la busqueda se realiza en todos los campos a la vez.
     * @param busqueda La String que se desea consultar.
     * @return El modelo o estructura que usará la JTable de Ventana para mostrar los datos especificados. Este variara dependiendo del numero de columnas y filas especificadas.
     */
    public static DefaultTableModel buscar(String tabla,String campo,String busqueda){
        int filasNulas;
        String[] titulos=SQLcode.getCampos(tabla);
        String[] datos=new String[titulos.length];
        DefaultTableModel model=new DefaultTableModel(null,titulos);
        

        String sql=  "SELECT * FROM "+tabla;
        
        if("".equals(busqueda)){
            sql+=";";
        }
        else{
            if("Todos".equals(campo)){
                sql+=" WHERE "+titulos[0]+" LIKE '%"+busqueda+"%'";
                for(int i=1;i<titulos.length;i++){
                    sql+=" OR "+titulos[i]+" LIKE '%"+busqueda+"%'";
                }
                sql+=";";
            }
            else{
                sql+=" WHERE "+campo+" LIKE '%"+busqueda+"%';";
            }
        }
           
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            rs.last();
            filasNulas=20-rs.getRow();
            if(filasNulas<0){
                filasNulas=0;
            }
            rs.beforeFirst();
            
            while(rs.next()){

                for(int i=0; i<datos.length;i++){
                    datos[i]=rs.getString(i+1);
                }

                model.addRow(datos);
            }
            
            if(filasNulas>0){
                for(int i=0;i<filasNulas;i++){
                    for(int j=0; j<datos.length;j++){
                        datos[j]=null;
                    }
                    model.addRow(datos);
                }
            } 
        }

        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"SQLExepcion:"+ex.getMessage());
            JOptionPane.showMessageDialog(null,"SQLState:"+ex.getSQLState());
            JOptionPane.showMessageDialog(null,"Error:"+ex.getErrorCode());
        }

        finally{
            if(rs!=null){
                try{
                    rs.close();
                }
                catch(SQLException ex){}
                rs=null;
            }

            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException ex){}
                stmt=null;
            }
        }

        return model;
    }
    /**
     * Utilizado para retornar el nombre de todas las tablas almacenadas 
     * en la base de datos
     * 
     * @return Un arreglo de Strings conteniendo el nombre de cada tabla. 
     */
    
       
    /**
     * Similar a getTablas, este metodo es utilizado para obtener el nombre de cada
     * uno de los campos o columnas de una tabla especificada.
     * 
     * @param tabla La tabla de la cual se obtendran los nombres de cada campo.
     * @return Un arreglo de strings indicando el nombre de cada campo.
     */
    public static String[] getCampos(String tabla){
        
        
        String sql="DESCRIBE "+tabla+";";
        String[] campos=null;
        int i=0;
        
        
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            rs.last();
            campos=new String[rs.getRow()];
            rs.beforeFirst();
            
            while(rs.next()){
                campos[i]=rs.getString(1);
                i++;
            }
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            if(rs!=null){
                try{
                    rs.close();
                }
                catch(SQLException ex){}
                    rs=null;
                }
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException ex){
                    stmt=null;
                }
            }
        }
        
        return campos;
    }
    /**
     * Utilizado para el ingreso de datos a una tabla desde izquierda a derecha
     * segun el tipo de dato de cada campo, asumiendo que el usuario ingrese los datos
     * correctamente.
     * 
     * @param tabla La tabla en la cual se desean ingresar los datos.
     * @param datos Los datos que se desean ingresar.
     * @param numCamposTabla La cantidad de columnas o campos de la tabla.
     */
    public static void ingresar(String tabla,String[]datos,int numCamposTabla){
        int i,j;
        String sql="INSERT INTO "; 
        String aux=" VALUES (";
        System.out.println(Arrays.toString(datos));
        for(i=0;i<numCamposTabla;i++){
            if(i==numCamposTabla-1)aux=aux+"'"+datos[i]+"')";
            else aux=aux+"'"+datos[i]+"',";
        }
        try{
            stmt=conn.createStatement();
            sql=sql+tabla+aux+";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            if(rs!=null){
                try{
                    rs.close();
                }
                catch(SQLException ex){}
                    rs=null;
                }
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException ex){
                    stmt=null;
                }
            }
        }
    }
}