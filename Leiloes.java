import java.util.*;
import java.net.*;

public class Leiloes {
    private HashMap<Integer,Leilao> todos;
    private int total;
    
    
    public Leiloes(){
        todos=new HashMap<Integer,Leilao>();
        total=0;
    }
    
    public synchronized int adicionarLeilao(String item,String vendedor,float valor){
       Leilao l = new Leilao(item,vendedor,valor,++total);
       todos.put(total,l);
       return total;
    }

    public synchronized void adicionarLicitacao(int codigo,Socket cliente,String nome,float valor)throws LeilaoInexistenteException{
        int i=0;
        for(Map.Entry<Integer,Leilao> x : todos.entrySet())
            if((x.getValue().getCodigo())==codigo && x.getValue().estaActivo()) {
                (x.getValue()).addLicitacao(nome,valor,cliente);
                break;
            }
        if(i==todos.size()) throw new LeilaoInexistenteException();
    }

    public synchronized Collection<Leilao> getTodos(){return todos.values();}
    
    public synchronized Leilao fecharLeilao(int x,String nome)throws NullPointerException,VendedorErradoException{
        Leilao l=todos.get(x);
        if(!l.getVendedor().equals(nome)) throw new VendedorErradoException();
        else {l.fecharLeilao();todos.remove(x) ;return l;}
    }
}
