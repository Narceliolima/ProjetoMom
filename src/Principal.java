import java.rmi.RemoteException;
import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) throws RemoteException{
		
		ServidorMOM server = new ServidorMOM();
		
		
		ArrayList<String> listaFilas = server.getFilas();
		ArrayList<Integer> listaQntMensagens = new ArrayList<Integer>();
		for(int i=0;i<listaFilas.size();i++) {
			listaQntMensagens.add(server.getQuantidadeMsg(listaFilas.get(i)));
		}
		
		System.out.println(listaFilas);
		System.out.println(listaQntMensagens);
		
		
		GerenciadorGUI win = new GerenciadorGUI();
		win.setlistaFilas(listaFilas);
		win.setListaQntMensagens(listaQntMensagens);
	}
}
