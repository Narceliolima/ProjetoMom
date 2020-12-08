import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;

public class Usuario extends UnicastRemoteObject implements UsuarioRemoto {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private Registry registro;
	private ServidorRemoto server;
	
	public static void main(String[] args) {
		
		try {
			new Usuario();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("Finalizado");
	}
	
	public Usuario() throws RemoteException{
		
		String host = "localhost";
		int porta = 8888;
		
		try {
			registro = LocateRegistry.getRegistry(porta);
			//registro.bind("//"+host+":"+porta+"/Cliente",this);
			server = (ServidorRemoto)registro.lookup("//"+host+":"+porta+"/Servidor");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		while(!solicitaConexao()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while(true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean solicitaConexao(){
		
		try {
			nome = Notificacao.configuraNome();
			if(nome!=null) {
				int resposta = server.conectaUsuario(this);
				System.out.println(resposta);
				if(resposta==-1) {
					Notificacao.usuarioExiste(nome);
				}
				else {
					System.out.println("Connectado");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void run() {
		
		System.out.println("Novo usuario criado");
		Scanner s = new Scanner(System.in);
		String nomeFila = s.nextLine();
		String mensagem = s.nextLine();
		
		while(!nomeFila.contentEquals("-1")) {
			//produzMensagem(nomeFila, mensagem);
			nomeFila = s.nextLine();
			mensagem = s.nextLine();
			System.out.println("PrÓximo");
		}
	}
	
	/*public void conectaBroker() {
		
		try {
			conexao = ActiveMQConnection.makeConnection(url);
			conexao.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void desconectaBroker() {
		
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean produzMensagem(String nomeFila,String conteudoMsg) {
		
		conectaBroker();
		
		try {
			Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destino = sessao.createQueue(nomeFila);
			MessageProducer produtor = sessao.createProducer(destino);
			TextMessage mensagem = sessao.createTextMessage(conteudoMsg);
			produtor.send(mensagem);
			produtor.close();
			sessao.close();
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		}
		
		desconectaBroker();
		return true;
	}
	
	public boolean consomeMensagem(String nomeFila) {
		
		conectaBroker();
		
		try {
			Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destino = sessao.createQueue(nomeFila);
			MessageConsumer consumidor = sessao.createConsumer(destino);
			Message mensagem = consumidor.receive();
			if (mensagem instanceof TextMessage) {
				TextMessage mensagemTexto = (TextMessage) mensagem;
				String texto = mensagemTexto.getText();
				System.out.println("Recebido: " + texto);
			} else {
				System.out.println("Recebido: " + mensagem);
			}
			consumidor.close();
			sessao.close();
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		}
		
		desconectaBroker();
		return true;
	}*/
	
	public String getNome() throws RemoteException {
		return nome;
	}
}
