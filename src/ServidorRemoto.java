import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorRemoto extends Remote{
	
	public int conectaUsuario(UsuarioRemoto usuario) throws RemoteException;
}