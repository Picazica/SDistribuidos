import java.net.*;
import java.io.*;

class NotificacaoHandler implements Runnable{
	Socket cs;
	LeiloesLog l;

	NotificacaoHandler(Socket cs, LeiloesLog l){
		this.cs = cs; this.l=l;
	}

	public void run(){
		try{
			PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
			l.writeloop(out,cs);
		} catch(IOException e){}
	}
}
