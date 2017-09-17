import java.net.*;
import java.io.*;


public class Cliente {
    
    private static Socket cliente;
    private static PrintWriter out; 
    private static BufferedReader stdIn;
    
    public static void main(String[] args) throws Exception{                    
        try {
            String comando, resposta;
            cliente = new Socket("localhost", 9999);            
            out = new PrintWriter(cliente.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));

             (new Thread(new Leitor(cliente))).start();
         
            
            while (!(comando = stdIn.readLine()).equals("0")){ 
                out.println(comando);
                 out.flush();
            }
            
            out.println(comando);
            out.flush();            
            
            stdIn.close();
            out.close();
            System.exit(1);
            
        } catch (Exception e){System.out.println("Servidor offline!");}             
    }
    
    
}
