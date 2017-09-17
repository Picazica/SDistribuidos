import java.util.*;
import java.net.*;

public class Utilizadores {
    private TreeMap<String, Utilizador> utilizadores;
     
    public Utilizadores(){
        utilizadores = new TreeMap<String,Utilizador>();
    }
    
    public Utilizadores(TreeMap<String, Utilizador> utilizadores){
        this.utilizadores = utilizadores;
    }
    
    public synchronized void adicionarUtilizador(String nome, String password) throws UtilizadorJaExisteException{
        if (!utilizadores.containsKey(nome))
            utilizadores.put(nome, new Utilizador(nome, password));
        else throw new UtilizadorJaExisteException();
    }
        
   
    public synchronized Utilizador logIn(String nome, String password,Socket cs) throws UtilizadorNaoExisteException, UtilizadorOnlineException, PasswordIncorretaException{
        Utilizador u;
        if (utilizadores.containsKey(nome)){
            u = utilizadores.get(nome);
            u.logIn(password,cs);
        }
        else throw new UtilizadorNaoExisteException();
        return u;
    }

    public synchronized boolean contem(String x){
        return utilizadores.containsKey(x);
    }

    public synchronized List<Utilizador> compara(Set<String> licitadores){
        List<Utilizador> novo= new ArrayList<Utilizador>();
        for(String s : licitadores){
            if(utilizadores.containsKey(s))
                if((utilizadores.get(s)).estaOnline()){
                    novo.add(utilizadores.get(s));}
        }
        return novo;
    }
     
    public synchronized void logOut(String nome) throws UtilizadorNaoExisteException, UtilizadorOfflineException{
        if (utilizadores.containsKey(nome)){
            Utilizador u = utilizadores.get(nome);
            u.logOut();
        }
        else throw new UtilizadorNaoExisteException();
    }
    
    
}

