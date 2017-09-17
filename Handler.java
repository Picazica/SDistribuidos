import java.io.*;
import java.net.Socket;
import java.util.*;


public class Handler implements Runnable{
    private String nome;
    private Socket cliente;
    private Utilizador utilizador;
    private PrintWriter outputServidor;
    private BufferedReader inputCliente;
    private Utilizadores utilizadores;
    private LeiloesLog mensagens;
    private Leiloes leiloes;
              
    
    
    
    public Handler(Socket cliente, Utilizadores utilizadores, Leiloes leiloes, LeiloesLog mensagens) throws IOException{   
        this.nome = null;
        this.utilizador = null;
        this.cliente = cliente;
        this.utilizadores = utilizadores;
        this.leiloes = leiloes;
        this.mensagens = mensagens;           
    }
    
    

    @Override
    public void run() {
        
        try {
            String mensagem;
            outputServidor = new PrintWriter(cliente.getOutputStream());
            inputCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            printMenu();
            while ((mensagem = inputCliente.readLine()) != null){
            	try{
                executarComando(mensagem);
            	}catch(VendedorErradoException ex){break;}
                printMenu();
            }   
            
     		try{
            System.out.println("Desligou-se!");
            if (nome!=null)
                utilizadores.logOut(nome);
            } catch (UtilizadorNaoExisteException | UtilizadorOfflineException ex1) {}
            
          }catch (IOException ex) {}
            finally {
            outputServidor.close();
        }
        
    }

    
    
    
    public void printMenu(){
        outputServidor.println("\n1.Registar\n2.Login\n3.Logout\n4.Adicionar Leilao\n5.Listar todos os leiloes\n6.Adicionar Licitacao\n7.Fechar Leilao\n0.Sair\n\nEscolha o numero\n");
        outputServidor.flush();
    }

    public void printLeilao(){
        Collection<Leilao> s = leiloes.getTodos();
        for(Leilao l : s){
                if(l.getMaiorLicitador().equals(nome)) outputServidor.println(l.toString()+ "+\n");
                else if(l.getVendedor().equals(nome)) outputServidor.println(l.toString()+ "*\n");
                else outputServidor.println(l.toString()+"\n");
        }
    }

    public void printNotificao(Leilao l1){
        l1.addSocket(cliente);
        mensagens.put(l1);
    }
    
    public void executarComando(String mensagem) throws VendedorErradoException{
        
        int x=Integer.parseInt(mensagem);
        switch (x){
            
            
        
            case 1: {
                try{
                outputServidor.println("Qual o seu nome?");
                outputServidor.flush();
                String nomet=inputCliente.readLine();
                if(nomet==null) throw new VendedorErradoException();
                outputServidor.println("Qual a sua password?");
                outputServidor.flush();
                String passwordt=inputCliente.readLine();
                if(passwordt==null) throw new VendedorErradoException();
                    utilizadores.adicionarUtilizador(nomet,passwordt);
                    outputServidor.println("Utilizador adicionado");
                    outputServidor.flush();
                }catch (UtilizadorJaExisteException ex) {
                    outputServidor.println("O utilizador ja existe!");
                    outputServidor.flush();
                }catch(IOException e){}
                break;
            }
            
            
            
            case 2: {
                if (this.nome!=null) {
                    outputServidor.println("Ja se encontra online!");
                    outputServidor.flush();
                    break;
                }
                else{
                    try {
                    outputServidor.println("Qual o seu nome?");
                    outputServidor.flush();
                    String nomei=inputCliente.readLine();
                    if(nomei==null) throw new VendedorErradoException();
                    outputServidor.println("Qual a sua password?");
                    outputServidor.flush();
                    String passwordi=inputCliente.readLine();
                    if(passwordi==null) throw new VendedorErradoException();
                    utilizador = utilizadores.logIn(nomei,passwordi,cliente );
                    this.nome = nomei;
                    outputServidor.println("Login efetuado com sucesso");
                    outputServidor.flush();
                        } catch (UtilizadorNaoExisteException ex) {
                            outputServidor.println("O utilizador nao existe!");
                            outputServidor.flush();
                        } catch (UtilizadorOnlineException ex) {
                            outputServidor.println("O utilizador ja esta online!");
                            outputServidor.flush();
                        } catch (PasswordIncorretaException ex) {
                            outputServidor.println("A password esta incorreta!");
                            outputServidor.flush();
                        } catch (IOException e){}
                    }
                break;
                }
      

            
            
            case 3: {
                if (this.nome==null){
                    outputServidor.println("Nao se encontra online!");
                    outputServidor.flush();
                    break;
                }
                else try {
                    utilizadores.logOut(this.nome);
                    this.nome = null;
                    this.utilizador = null;
                    outputServidor.println("Logout efetuado com sucesso");
                    outputServidor.flush();
                } catch (UtilizadorOfflineException ex) {
                    outputServidor.println("Nao se encontra online!");
                    outputServidor.flush();
                } catch (UtilizadorNaoExisteException ex) {}
                break;
            }
            
            
            case 4: {
                
                if (this.nome==null){
                    outputServidor.println("Nao se encontra online!");
                    outputServidor.flush();
                    break;
                }
                try{
                outputServidor.println("Qual o seu item?");
                outputServidor.flush();
                String item= inputCliente.readLine();
                if(item==null) throw new VendedorErradoException();
                outputServidor.println("Qual o valor base?");
                outputServidor.flush();
                String s = inputCliente.readLine();
                if(item==null) throw new VendedorErradoException();
                float f= Float.parseFloat(s);
                
                int n=leiloes.adicionarLeilao(item,nome,f);             
                outputServidor.println("O seu numero de leilao é: "+n);
                }catch(IOException e) {}
                break;
            }
            
            
            
            case 5: {
                
                if (this.nome==null){
                    outputServidor.println("Nao se encontra online!");
                    outputServidor.flush();
                    break;
                }           
                printLeilao();
                break;
            }

            case 6:{
                if (this.nome==null){
                    outputServidor.println("Nao se encontra online!");
                    outputServidor.flush();
                    break;
                }
                try{
                outputServidor.println("Qual o codigo do leilao?");
                outputServidor.flush();
                String codigo= inputCliente.readLine();
                if(codigo==null) throw new VendedorErradoException();
                int cod= Integer.parseInt(codigo);
                outputServidor.println("Qual o valor da licitacao?");
                outputServidor.flush();
                String s2 = inputCliente.readLine();
                if(s2==null) throw new VendedorErradoException();
                float f2= Float.parseFloat(s2);
                leiloes.adicionarLicitacao(cod,cliente,nome,f2);
                outputServidor.println("Licitacao feita com sucesso");}
                catch(LeilaoInexistenteException e){                    
                    outputServidor.println("O leilao nao existe!");
                    outputServidor.flush();
                }
                catch(IOException e){}
                break;
            }

            case 7:{
                if (this.nome==null){
                    outputServidor.println("Nao se encontra online!");
                    outputServidor.flush();
                    break;
                }
                try{
                outputServidor.println("Qual o numero de leilao?");
                outputServidor.flush();
                String s3 = inputCliente.readLine();
                if(s3==null) throw new VendedorErradoException();
                int n2= Integer.parseInt(s3);
                Leilao l1=leiloes.fecharLeilao(n2,nome);
                printNotificao(l1);

                }catch(NullPointerException e){
                    outputServidor.println("O numero de leilao nao existe!");
                    outputServidor.flush();
                }catch(VendedorErradoException e){
                    outputServidor.println("Voce nao e o vendedor deste leilao!");
                    outputServidor.flush();
                }catch(IOException e){}
                 break;
            }
            
            
            
            case 0: {
                try {
                    if (this.nome!=null) {
                        utilizadores.logOut(this.nome);
                        nome = null;
                    }
                } catch (UtilizadorOfflineException | UtilizadorNaoExisteException ex) {}
                break;
            }
                        
            
            
            default : {
                outputServidor.println("Comando inexistente!");
                outputServidor.flush();
            }            
            
        }        
        
    }
    
    
    
    
    
}
