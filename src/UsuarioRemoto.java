import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UsuarioRemoto extends Remote{
	
	public String getNome() throws RemoteException;
	public void notificaMensagem() throws RemoteException;
}