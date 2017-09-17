import java.util.*;
import java.io.*;
import java.net.*;

public class LeiloesLog{
	Vector<Leilao> ll;
	int total;

	public LeiloesLog(){
		ll=new Vector<Leilao>();
		total=0;
	}

	public synchronized void put(Leilao l){
		ll.add(l);
		total++;
		notifyAll();
	}

	public synchronized void writeloop(PrintWriter pw,Socket cs){
		int i=total;
		try{
			while(true){
				while(i>=total ) wait();
				if(ll.elementAt(i).containsSocket(cs)){
					pw.println(ll.elementAt(i).toString2());
					i++;
				}
				else i++;
			}
		}
		catch(InterruptedException e){}
	}
}
