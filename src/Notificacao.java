import javax.swing.JOptionPane;

public class Notificacao {

	public static String configuraNome() {
		
		String nomeFila = null;
		
		nomeFila = JOptionPane.showInputDialog("Digite um nome de usuário");
		
		while(nomeFila!=null&&nomeFila.contentEquals("")) {
			nomeFila = JOptionPane.showInputDialog("Nome inválido, digite um nome de usuário");
		}
		
		return nomeFila;
	}
	
	public static String addTopico() {
		
		String nomeTopico = null;
		
		nomeTopico = JOptionPane.showInputDialog("Digite um nome para o Topico");
		
		while(nomeTopico!=null&&nomeTopico.contentEquals("")) {
			nomeTopico = JOptionPane.showInputDialog("Nome inválido, digite um nome para o Topico");
		}
		
		return nomeTopico;
	}
	
	public static void usuarioExiste(String nome) {
		JOptionPane.showMessageDialog(null, "Usuario '"+nome+"' ja existe");
	}
	
	public static void topicoExiste(String nome) {
		JOptionPane.showMessageDialog(null, "Topico '"+nome+"' ja existe");
	}
}
