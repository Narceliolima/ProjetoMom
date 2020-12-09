import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario extends UnicastRemoteObject implements UsuarioRemoto {
	
	private static final long serialVersionUID = 1L;
	protected String nome;
	private Registry registro;
	private ServidorRemoto server;
	private UsuarioGUI janela;
	
	/*public static void main(String[] args) {
		
		try {
			new Usuario();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("Finalizado");
	}*/
	
	public Usuario(UsuarioGUI janela) throws RemoteException{
		
		this.janela = janela;
		String host = "localhost";
		int porta = 8888;
		
		try {
			registro = LocateRegistry.getRegistry(porta);
			server = (ServidorRemoto)registro.lookup("//"+host+":"+porta+"/Servidor");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		/*while(!solicitaConexao()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(nome);
		
		Scanner s = new Scanner(System.in);
		System.out.println("Digite o destino");
		String nome = s.nextLine();
		System.out.println("Digite a mensagem");
		String mensagem = s.nextLine();
		
		while(!nome.contentEquals("-1")) {
			System.out.println("PrÓximo");
			enviaMensagem(nome, mensagem, false);
			System.out.println("Digite o destino");
			nome = s.nextLine();
			System.out.println("Digite a mensagem");
			mensagem = s.nextLine();
		}
		
		recebeMensagem(this.nome, true);
		
		while(true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
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
	
	public void enviaMensagem(String nome, String conteudoMsg, boolean tipoFila) {
		
		try {
			if(tipoFila) {
				server.produzMensagemFila(nome, conteudoMsg);
			}
			else {
				server.produzMensagemTopico(nome, conteudoMsg);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> recebeMensagem(String nome, boolean tipoFila) {
		
		try {
			if(tipoFila) {
				return server.recebeMensagemFila(nome);
			}
			else {
				server.assinaTopico(nome, this.nome);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public void notificaMensagem() throws RemoteException{
		janela.recebeMensagensUsuarios();
	}
	
	public void setMensagemTopico(String mensagem) {
		janela.escreveMensagensTopico(mensagem);
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
	
	public String getNome() throws RemoteException {
		return nome;
	}
}
