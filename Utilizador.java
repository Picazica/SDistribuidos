import java.net.*;

public class Utilizador {
    private String nome;
    private String password;
    private boolean online;
    private Socket cs;
    
    
    public Utilizador(String nome, String password){
        this.nome = nome;
        this.password = password;
        online = false;
        cs=null;
    }
    
    
    public void logIn(String password,Socket cs) throws UtilizadorOnlineException, PasswordIncorretaException{
        if (online) throw new UtilizadorOnlineException();
        else if (!this.password.equals(password)) throw new PasswordIncorretaException();
        else {
            online = true;
            this.cs=cs;}
    }
    
    public void logOut() throws UtilizadorOfflineException{
        if (!online) throw new UtilizadorOfflineException();
        else {
            online = false;
            cs=null;}
    }

    public synchronized boolean estaOnline(){return online;}
    public synchronized Socket getSocket(){return cs;}
    
}
