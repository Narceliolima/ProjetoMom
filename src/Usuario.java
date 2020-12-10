import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Usuario extends UnicastRemoteObject implements UsuarioRemoto {
	
	private static final long serialVersionUID = 1L;
	protected String nome;
	private Registry registro;
	private ServidorRemoto server;
	private UsuarioGUI janela;
	private String host;
	private int porta;
	
	public Usuario(UsuarioGUI janela) throws RemoteException{
		this.janela = janela;
		
		host = Notificacao.configuraHost();
		porta = Notificacao.configuraPorta();
		
		try {
			registro = LocateRegistry.getRegistry(porta);
			server = (ServidorRemoto)registro.lookup("//"+host+":"+porta+"/Servidor");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean solicitaConexao(){
		
		try {
			nome = Notificacao.configuraNome();
			if(nome!=null) {
				int resposta = server.conectaUsuario(this);
				janela.setMensagemLog("Codigo do retorno: "+resposta);
				if(resposta==-1) {
					Notificacao.usuarioExiste(nome);
				}
				else {
					janela.setMensagemLog("Connectado");
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
				if(!server.produzMensagemFila(nome, conteudoMsg)) {
					Notificacao.naoExisteUsuario();
				}
			}
			else {
				if(!server.produzMensagemTopico(nome, conteudoMsg)) {
					Notificacao.naoExisteTopico();
				}
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
				if(!server.assinaTopico(nome, this.nome)) {
					Notificacao.topicoJaAssinado();
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public void notificaMensagem() throws RemoteException {
		janela.recebeMensagensUsuarios();
	}
	
	public void notificaDesconexao() throws RemoteException {
		Notificacao.desconectado();
		System.exit(0);
	}
	
	public void setMensagemTopico(String mensagem) throws RemoteException {
		janela.escreveMensagensTopico(mensagem);
	}
	
	public ArrayList<String> getUsuarios() {
		try {
			return server.getUsuariosOnline();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public ArrayList<String> getTopicos() {
		try {
			return server.getTopicosDisponiveis();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public String getNome() throws RemoteException {
		return nome;
	}
}
