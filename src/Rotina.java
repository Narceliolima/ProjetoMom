
public class Rotina extends Thread {
	
	private UsuarioGUI usuario;

	public Rotina(UsuarioGUI usuario) {
		this.usuario = usuario;
		
		this.start();
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			usuario.recebeListaUsuarios();
			usuario.recebeListaTopicos();
			usuario.setMensagemLog("Rotina de manutenção: Ok");
		}
	}
}
