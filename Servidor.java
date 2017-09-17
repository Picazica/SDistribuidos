import java.net.*;
import java.io.*;

public class Servidor
{
    
   
    public static void main(String args[]) throws IOException{
        ServerSocket ss = new ServerSocket(9999);
        Utilizadores u= new Utilizadores();
        Leiloes l= new Leiloes();
        LeiloesLog ml= new LeiloesLog();
        Socket cs;
        while(true)
        {
            cs=ss.accept();
            Handler aaa= new Handler(cs,u,l,ml);
            NotificacaoHandler nl = new NotificacaoHandler(cs,ml);
            (new Thread(aaa)).start();
            (new Thread(nl)).start();
        }
    }
    
}
