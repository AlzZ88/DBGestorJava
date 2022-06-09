package Enterprise;

import ConexionDB.Conexion;
import ConexionDB.SQLcode;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Alex
 */
public class Interface extends javax.swing.JFrame implements MouseListener,MouseMotionListener{
    JPanel contenedor; // Jpanel principal
    Logueo logueo;     // Jpanel ingreso
    Loading load;      // pantalla de carga
    Client client;     // pantalla venta de softwares
    CardLayout c;      // layout auto ajustable.
    Timer timer;     // timer para la pantalla de carga
    
    int i;
    
    boolean isclient;
    
    private volatile int screenX = 0; //posicion X del mouse 
    private volatile int screenY = 0; // posicion Y del mouse
    private volatile int myX = 0; // posicion X del frame
    private volatile int myY = 0; // posicion Y del frame
    boolean click1; // boolean que solo permite arrastrar el frame con el primer click.e
    
    
    //constructor
    public Interface() {
        //variables
        isclient=false;
        click1=false;
        i=1;
        
        //Jpanels
        logueo=new Logueo(this);
        load=new Loading();
        client=new Client(this);
        c=new CardLayout();
        contenedor=new JPanel(c);
        contenedor.add(load,"1");
        contenedor.add(logueo,"2");
        contenedor.add(client,"3");
        c.show(contenedor,"1");
        contenedor.setBounds(650, 500, 500, 650); //tamaño del Jpanel principal
        
        
        //Jframe
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.add(contenedor);
        setUndecorated(true); //frame sin bordes
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();  //obtener el tamaño de la pantalla
        this.setBounds(650, 500, 500, 650); //tamaño del frame
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //centra el Frame.
        setVisible(true);
        
        
        //timer que va iterando por los distintos iconos del "loadicon", cada 100 ms cambia el
        //icono para que parezca un gif. 
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                System.out.println(i);
                
                ImageIcon aux;
                aux= new ImageIcon(getClass().getResource("/Imagenes/load"+i+".png"));
                load.loadIcon(aux);
                i++;
                if(i==9) i=1;
            }
            
        });
        //client();
        timer.start();
        conectar();        
    }
    
    /* Metodo para obtener la conexion con la base de datos.
     * La animacion del loadicon dura hasta que el programa
     * se conecta con la base de datos, en ese momento se cambia a la pantalla de logueo.
     */
    public void conectar(){
        if(Conexion.conectar()){
            SQLcode.copiarConn();
            timer.stop();
            this.logueo();
        }
    }
    
    public void isclient(boolean a){
        isclient=a;
    }
    
    
    /* Metodo para cambiar a la pantalla de logueo luego de la pantalla de carga.
     */
    
    public void logueo(){
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //dimensiones de la pantalla
        this.setBounds(650, 500, 400, 600); //setear tamaño del frame
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //centrar frame
        contenedor.setBounds(650, 500, 400, 600); //tamaño de "logueo"
        c.show(contenedor, "2");  //mostrar contenedor.
    }
    
    
    /* Metodo para cambiar a la pantalla de cliente luego del logueo.
     */
    public void client(){
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //dimensiones de la pantalla
        this.setBounds(650, 500, 1200, 900); //setear tamaño del frame
        contenedor.setBounds(0, 0, 1200, 900); //tamaño de "client"
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //centrar frame
        c.show(contenedor, "3"); //mostrar contenedor.
    }

    
    /* MouseMotionListener, al arrastar el mouse el frame se mueve segun la diferencia entre
     * la vieja posicion del mouse y su nueva posicion.
     */
   @Override
    public void mouseDragged(MouseEvent e) {
        if(click1){
            int deltaX = e.getXOnScreen() - screenX;
            int deltaY = e.getYOnScreen() - screenY;
            this.setLocation(myX + deltaX, myY + deltaY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //if(isclient)client.isIn(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
    
    /* MouseListener, al mantener apretado el mouse se registran posiciones del mouse y 
     * del frame.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1){
            click1 = true;
            screenX = e.getXOnScreen();
            screenY = e.getYOnScreen();
            myX = getX();
            myY = getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        click1=false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }
    
}
