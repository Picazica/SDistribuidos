import java.util.*;
import java.net.*;

public class Leilao{
	private Vector<Socket> licitadores;
	private float maior;
	private int codigo;
	private String maiorlicitador;
	private String item;
	private String vendedor;
	private boolean activo;

	public Leilao(String item,String vendedor,float valor,int codigo){
		this.item=item;
		this.vendedor=vendedor;
		this.codigo=codigo;
		maiorlicitador="";
		maior=valor;
		licitadores= new Vector<Socket>();
		activo=true;
	}

	public synchronized boolean temLicitacoes(){
		return (licitadores.size()>0);
	}

	public synchronized boolean estaActivo(){return activo;}

	public synchronized void fecharLeilao(){
		activo=false;
	}

	public synchronized void addLicitacao(String nome,float valor,Socket cliente){
		if(!licitadores.contains(cliente)) 
			licitadores.add(cliente);
		if(valor>maior) {
			maior=valor;
			maiorlicitador=nome;
		}
	}

	public synchronized boolean containsSocket(Socket cs){
		return licitadores.contains(cs);
	}

	public synchronized void addSocket(Socket cs){
		licitadores.add(cs);
	}
	

	public synchronized float getMaior() {return maior;}
	public synchronized String getMaiorLicitador() {return maiorlicitador;}
	public synchronized String getitem() {return item;}
	public synchronized String getVendedor(){return vendedor;}
	public synchronized int getCodigo(){return codigo;}
	public synchronized Vector<Socket> getLicitadores(){return licitadores;}
	public synchronized String toString2(){
		return ("Leilao "+codigo+ " acabou. Vencedor: "+ maiorlicitador+ " com: " + maior + " euros");}
		
	public synchronized String toString(){
		return (codigo+ ".Item: "+item+"; Vendedor: "+vendedor+"; Maior Licitacao: "+ maior+"; ");
	}
}
