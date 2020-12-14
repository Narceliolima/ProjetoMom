import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JList;

public class UsuarioGUI {

	//--------------------------------------------/-------------/--------------------------------------------//
	private JFrame frame;
	private UsuarioGUI window;
	private Usuario usuario;
	private ActionListener ato;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Paineis/Scroll/Text--------------------------------------------//
	private JPanel resumoTopicos;
	private JPanel resumoUsuario;
	private JScrollPane scrollLog;
	private JScrollPane scrollUsuario;
	private JScrollPane scrollChat;
	private JScrollPane scrollTopico;
	private JTextArea textUsuarios;
	private JTextArea textLog;
	private JTextArea textTopicos;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Labels/Botoes/--------------------------------------------//
	private JLabel labelUsuarios;
	private JLabel labelTopicos;
	private JLabel labelLog;
	private JLabel lblDigiteAqui;
	private JButton assinarTopico ;
	private JTextField chat;
	//--------------------------------------------/-------------/--------------------------------------------//
	private JRadioButton radioUsuario;
	private JRadioButton radioTopico;
	private ButtonGroup radioGrupo = new ButtonGroup();
	private JPanel resumoDisponiveis;
	private JLabel labelUsuariosExistentes;
	private JScrollPane scrollUsuarioDis;
	private JScrollPane scrollTopicosDis;
	private JLabel labelTopicosDisponveis;
	private JList<String> jListaUsuarios;
	private JList<String> jListaTopicos;
	private DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
	private DefaultListModel<String> modeloTopicos = new DefaultListModel<>();

	public static void main(String[] args) {
		
		new UsuarioGUI();
	}

	public UsuarioGUI() {
		window = this;
		try {
			usuario = new Usuario(window);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1010, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		iniciaPaineis();
		iniciaLabels();
		configuraUsuario();
		iniciaValores();
		createRunnable();
	}
	
	public void varreBotao() {
		
		ato = new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == assinarTopico) {
					int index = jListaTopicos.getSelectedIndex();
					String nome = modeloTopicos.get(index);
					if(nome!=null) {
						usuario.recebeMensagem(nome, false);
					}
				}
			}
		};
		
		assinarTopico.addActionListener(ato);
		
		chat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String mensagem = arg0.getActionCommand();
				if(radioUsuario.isSelected()) {
					int index = jListaUsuarios.getSelectedIndex();
					if(usuario.enviaMensagem(modeloUsuarios.get(index).replace(modeloUsuarios.get(index).charAt(0), ' ').replaceFirst(" ", ""), usuario.nome+": "+mensagem, true)) {
						textUsuarios.append("Você>"+modeloUsuarios.get(index).replace(modeloUsuarios.get(index).charAt(0), ' ').replaceFirst(" ", "")+": "+mensagem+"\n");
					}
				}
				else {
					int index = jListaTopicos.getSelectedIndex();
					if(usuario.enviaMensagem(modeloTopicos.get(index), modeloTopicos.get(index)+"<"+usuario.nome+": "+mensagem, false)){
						textTopicos.append("Você>"+modeloTopicos.get(index)+": "+mensagem+"\n");
					}
				}
				chat.setText("");
			}
		});
	}
	
	public void recebeMensagensUsuarios() {
		ArrayList<String> listaMensagens = usuario.recebeMensagem(usuario.nome, true);
		while(!listaMensagens.isEmpty()) {
			textUsuarios.append("Você<"+listaMensagens.remove(0)+"\n");
		}
	}
	
	public int contador = 0;
	
	public void recebeListaUsuarios() {
		System.out.println(contador);
		ArrayList<String> listaUsuarios = usuario.getUsuarios();
		System.out.println(listaUsuarios);
		int index = jListaUsuarios.getSelectedIndex();
		if(index<0) {
			index = 0;
		}
		modeloUsuarios.removeAllElements();
		for(int i=0;i<listaUsuarios.size();i++) {
			modeloUsuarios.addElement(listaUsuarios.get(i));
		}
		jListaUsuarios.setSelectedIndex(index);
		contador++;
	}
	
	public void recebeListaTopicos() {
		ArrayList<String> listaTopicos = usuario.getTopicos();
		System.out.println(listaTopicos);
		int index = jListaTopicos.getSelectedIndex();
		if(index<0) {
			index = 0;
		}
		modeloTopicos.removeAllElements();
		for(int i=0;i<listaTopicos.size();i++) {
			modeloTopicos.addElement(listaTopicos.get(i));
		}
		jListaTopicos.setSelectedIndex(index);
	}
	
	public void escreveMensagensTopico(String mensagem) {
		textTopicos.append(mensagem+"\n");
	}
	
	public void setMensagemLog(String mensagem) {
		textLog.append(mensagem+"\n");
		textLog.setCaretPosition(textLog.getText().length());
	}
	
	private void iniciaPaineis() {
		frame.getContentPane().setLayout(null);
		
		resumoUsuario = new JPanel();
		resumoUsuario.setBounds(197, 2, 259, 435);
		resumoUsuario.setLayout(null);
		frame.getContentPane().add(resumoUsuario);
		
		resumoTopicos = new JPanel();
		resumoTopicos.setBounds(468, 2, 259, 435);
		resumoTopicos.setLayout(null);
		frame.getContentPane().add(resumoTopicos);
		
		labelLog = new JLabel("Log");
		labelLog.setBounds(846, 4, 42, 15);
		frame.getContentPane().add(labelLog);
		
		scrollLog = new JScrollPane();
		scrollLog.setBounds(739, 31, 254, 406);
		frame.getContentPane().add(scrollLog);
		
		textLog = new JTextArea();
		textLog.setEditable(false);
		scrollLog.setViewportView(textLog);
		
		scrollChat = new JScrollPane();
		scrollChat.setBounds(10, 467, 983, 30);
		frame.getContentPane().add(scrollChat);
		
		chat = new JTextField();
		scrollChat.setViewportView(chat);
		chat.setColumns(10);
		
	}
	
	private void iniciaLabels() {
		
		labelUsuarios = new JLabel("Mensagem Usuarios");
		labelUsuarios.setBounds(55, 6, 152, 15);
		resumoUsuario.add(labelUsuarios);
		
		labelTopicos = new JLabel("Mensagem Topicos");
		labelTopicos.setBounds(59, 6, 151, 15);
		resumoTopicos.add(labelTopicos);
		
		scrollUsuario = new JScrollPane();
		scrollUsuario.setBounds(12, 33, 235, 390);
		resumoUsuario.add(scrollUsuario);
		
		textUsuarios = new JTextArea();
		textUsuarios.setEditable(false);
		scrollUsuario.setViewportView(textUsuarios);
		
		assinarTopico = new JButton("Assinar Topico");
		assinarTopico.setBounds(12, 398, 235, 25);
		resumoTopicos.add(assinarTopico);
		
		scrollTopico = new JScrollPane();
		scrollTopico.setBounds(12, 33, 235, 363);
		resumoTopicos.add(scrollTopico);
		
		textTopicos = new JTextArea();
		textTopicos.setEditable(false);
		scrollTopico.setViewportView(textTopicos);
		
		lblDigiteAqui = new JLabel("Digite aqui sua mensagem");
		lblDigiteAqui.setBounds(10, 449, 201, 15);
		frame.getContentPane().add(lblDigiteAqui);
		
		resumoDisponiveis = new JPanel();
		resumoDisponiveis.setLayout(null);
		resumoDisponiveis.setBounds(0, 2, 194, 435);
		frame.getContentPane().add(resumoDisponiveis);
		
		labelUsuariosExistentes = new JLabel("Usuarios");
		labelUsuariosExistentes.setBounds(74, 12, 77, 15);
		resumoDisponiveis.add(labelUsuariosExistentes);
		
		scrollUsuarioDis = new JScrollPane();
		scrollUsuarioDis.setBounds(12, 33, 168, 179);
		resumoDisponiveis.add(scrollUsuarioDis);
		
		jListaUsuarios = new JList<String>(modeloUsuarios);
		scrollUsuarioDis.setViewportView(jListaUsuarios);
		
		scrollTopicosDis = new JScrollPane();
		scrollTopicosDis.setBounds(12, 255, 168, 168);
		resumoDisponiveis.add(scrollTopicosDis);
		
		jListaTopicos = new JList<String>(modeloTopicos);
		scrollTopicosDis.setViewportView(jListaTopicos);
		
		labelTopicosDisponveis = new JLabel("Topicos");
		labelTopicosDisponveis.setBounds(73, 224, 65, 15);
		resumoDisponiveis.add(labelTopicosDisponveis);
		
		radioUsuario = new JRadioButton("");
		radioUsuario.setBounds(46, 8, 29, 23);
		resumoDisponiveis.add(radioUsuario);
		radioUsuario.setSelected(true);
		radioGrupo.add(radioUsuario);
		
		radioTopico = new JRadioButton("");
		radioTopico.setBounds(46, 220, 28, 23);
		resumoDisponiveis.add(radioTopico);
		radioGrupo.add(radioTopico);
	}
	
	private void iniciaValores() {
		
		recebeMensagensUsuarios();
		
		setMensagemLog("Bem vindo(a) "+usuario.nome);
		setMensagemLog("Lista de usuarios");
		setMensagemLog("Legenda: * = online, - = offline");
		recebeListaUsuarios();
		recebeListaTopicos();
		new Rotina(this);
	}
	
	private void configuraUsuario() {
		while(!usuario.solicitaConexao()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createRunnable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
					window.varreBotao();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
