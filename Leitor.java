import java.net.*;
import java.io.*;

public class Leitor implements Runnable{
        Socket c;
        
        Leitor(Socket cs){c=cs;}
        
        public void run(){
            try{
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            
            String current;
            while((current=in.readLine())!=null){
                System.out.println(current);
            }
            in.close();
            System.exit(1);
        }catch(IOException e){};
        }
}