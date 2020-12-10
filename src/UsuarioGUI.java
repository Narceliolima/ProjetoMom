import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JRadioButton;


public class UsuarioGUI {

	//--------------------------------------------/-------------/--------------------------------------------//
	private JFrame frame;
	private UsuarioGUI window;
	private Usuario usuario;
	private ActionListener ato;
	private JTextArea textLog;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Paineis/Scroll/--------------------------------------------//
	private JPanel resumoTopicos;
	private JPanel resumoUsuario;
	private JScrollPane scrollLog;
	private ArrayList<JLabel> celulasFila = new ArrayList<JLabel>();
	private ArrayList<JLabel> celulasTopico = new ArrayList<JLabel>();
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Labels/Botoes/--------------------------------------------//
	private JLabel labelUsuarios;
	private JLabel labelTopicos;
	private JLabel labelLog;
	private JButton assinarTopico ;
	private ArrayList<JButton> xButtonFila = new ArrayList<JButton>();
	private ArrayList<JButton> xButtonTopico = new ArrayList<JButton>();
	private JTextField chat;
	//--------------------------------------------/-------------/--------------------------------------------//
	private JTextArea textDestino;
	private JTextArea textUsuarios;
	private JScrollPane scrollUsuario;
	private JScrollPane scrollChat;
	private JScrollPane scrollTopico;
	private JTextArea textTopicos;
	private JRadioButton radioUsuario;
	private JRadioButton radioTopico;
	private ButtonGroup radioGrupo = new ButtonGroup();
	private JLabel lblNome;
	private JLabel lblDigiteAqui;
	
	public static void main(String[] args) {
		
		UsuarioGUI gui = new UsuarioGUI();
		
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
		frame.setBounds(100, 100, 804, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		iniciaPaineis();
		iniciaLabels();
		configuraUsuario();
		iniciaValores();
		createRunnable();
	}
	
	public void atualizaInterface() {
		
		labelLog.setText(""+celulasFila.size());
		labelLog.setText(""+celulasTopico.size());
		labelLog.setText("Log");
	}
	
	public void varreBotao() {
		
		ato = new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {/*
				if(arg0.getSource() == addUsuario) {
					String nome = Notificacao.configuraNome();
					server.criaUsuario(new UsuarioRemoto() {
						
						@Override
						public String getNome() throws RemoteException {
							// TODO Auto-generated method stub
							return null;
						}
					});
					if(nome!=null) {
						if(!server.verificaFilaExiste(nome)) {
							server.criaFila(nome);
							criarBotaoFila();
							criarLabelFila(nome);
							criarLabelFila("0");
							iniciaBotaoFila();
							setMensagemLog("Usuário '"+nome+"' Criado");
						}
						else {
							Notificacao.usuarioExiste(nome);
							setMensagemLog("Erro: Usuário Duplicado");
						}
					}
				}*/
				if(arg0.getSource() == assinarTopico) {
					String nome = Notificacao.addTopico();
					if(nome!=null) {
						usuario.recebeMensagem(nome, false);
					}
				}/*
				for(int i=0;i<xButtonFila.size();i++) {
					if(arg0.getSource() == xButtonFila.get(i)) {
						setMensagemLog(celulasFila.get(i*2).getText());
						server.removeFila(celulasFila.get(i*2).getText());
						setMensagemLog("Usuario '"+celulasFila.get(i*2).getText()+"' Deletado");
						removeBotaoFila(i);
						removeLabelFila(i);
						removeLabelFila(i);
					}
				}
				for(int i=0;i<xButtonTopico.size();i++) {
					if(arg0.getSource() == xButtonTopico.get(i)) {
						setMensagemLog(celulasTopico.get(i).getText());
						server.removeTopico(celulasTopico.get(i).getText());
						setMensagemLog("Usuario '"+celulasTopico.get(i).getText()+"' Deletado");
						removeBotaoTopico(i);
						removeLabelTopico(i);
					}
				}*/
				atualizaInterface();
			}
		};
		assinarTopico.addActionListener(ato);
		
		for(int i=0;i<xButtonFila.size();i++) {
			xButtonFila.get(i).addActionListener(ato);
			System.out.println(i);
		}
		
		for(int i=0;i<xButtonTopico.size();i++) {
			xButtonTopico.get(i).addActionListener(ato);
			System.out.println(i);
		}
		
		chat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String mensagem = arg0.getActionCommand();
				if(radioUsuario.isSelected()) {
					textUsuarios.append("Você>"+textDestino.getText()+": "+mensagem+"\n");
					usuario.enviaMensagem(textDestino.getText(), usuario.nome+": "+mensagem, true);
					
				}
				else {
					textTopicos.append("Você>"+textDestino.getText()+": "+mensagem+"\n");
					usuario.enviaMensagem(textDestino.getText(), textDestino.getText()+"<"+usuario.nome+": "+mensagem, false);
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
	
	public void escreveMensagensTopico(String mensagem) {
		textTopicos.append(mensagem+"\n");
	}
	
	/*public void escreveChatUsuarios(String mensagem) {
		textUsuarios.append("Você>"+textDestino.getText()+": "+mensagem);
		usuario.enviaMensagem(textDestino.getText(), mensagem, true);
	}
	
	public void escreveChatTopico(String mensagem) {
		textTopicos.append("Você>"+textDestino.getText()+": "+mensagem);
		usuario.enviaMensagem(textDestino.getText(), mensagem, false);
	}*/
	
	public void criarBotaoFila() {
		xButtonFila.add(new JButton("X"));
		//painelFilas.add(xButtonFila.get(xButtonFila.size()-1));
	}
	
	public void criarBotaoTopico() {
		xButtonTopico.add(new JButton("X"));
		//painelTopicos.add(xButtonTopico.get(xButtonTopico.size()-1));
	}
	
	public void iniciaBotaoFila() {
		xButtonFila.get(xButtonFila.size()-1).addActionListener(ato);
	}
	
	public void iniciaBotaoTopico() {
		xButtonTopico.get(xButtonTopico.size()-1).addActionListener(ato);
	}
	
	public void removeBotaoFila(int i) {
		xButtonFila.remove(i);
		//painelFilas.remove(i*3);
	}
	
	public void removeBotaoTopico(int i) {
		xButtonTopico.remove(i);
		//painelTopicos.remove(i*2);
	}
	
	public void criarLabelFila(String valor) {
		celulasFila.add(new JLabel(""+valor));
		//painelFilas.add(celulasFila.get(celulasFila.size()-1));
	}
	
	public void criarLabelTopico(String valor) {
		celulasTopico.add(new JLabel(""+valor));
		//painelTopicos.add(celulasTopico.get(celulasTopico.size()-1));
	}

	public void removeLabelFila(int i) {
		celulasFila.remove(i*2);
		//painelFilas.remove(i*3);
	}
	
	public void removeLabelTopico(int i) {
		celulasTopico.remove(i);
		//painelTopicos.remove(i*2);
	}
	
	public void setMensagemLog(String mensagem) {
		textLog.append(mensagem+"\n");
		textLog.setCaretPosition(textLog.getText().length());
	}
	
	public void preenchePainelFila(ArrayList<String> listaFilas, ArrayList<Integer> listaQntMensagens) {
		for(int i=0;i<listaFilas.size();i++) {
			criarBotaoFila();
			criarLabelFila(listaFilas.get(i));
			criarLabelFila(""+listaQntMensagens.get(i));
		}
	}
	
	public void preenchePainelTopico(ArrayList<String> listaTopicos) {
		for(int i=0;i<listaTopicos.size();i++) {
			criarBotaoTopico();
			criarLabelTopico(listaTopicos.get(i));
		}
	}
	
	private void iniciaPaineis() {
		
		resumoUsuario = new JPanel();
		resumoUsuario.setBounds(0, 0, 259, 435);
		resumoUsuario.setLayout(null);
		frame.getContentPane().add(resumoUsuario);
		
		resumoTopicos = new JPanel();
		resumoTopicos.setLayout(null);
		resumoTopicos.setBounds(267, 0, 259, 435);
		frame.getContentPane().add(resumoTopicos);
		
		labelLog = new JLabel("Log");
		labelLog.setBounds(645, 5, 42, 15);
		frame.getContentPane().add(labelLog);
		
		scrollLog = new JScrollPane();
		scrollLog.setBounds(534, 32, 254, 391);
		frame.getContentPane().add(scrollLog);
		
		textLog = new JTextArea();
		textLog.setEditable(false);
		scrollLog.setViewportView(textLog);
		
		scrollChat = new JScrollPane();
		scrollChat.setBounds(10, 510, 782, 30);
		frame.getContentPane().add(scrollChat);
		
		chat = new JTextField();
		scrollChat.setViewportView(chat);
		chat.setColumns(10);
		
		textDestino = new JTextArea();
		textDestino.setToolTipText("");
		textDestino.setBounds(331, 445, 457, 21);
		frame.getContentPane().add(textDestino);
		
	}
	
	private void iniciaLabels() {//Reajuste a interface
		
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
		
		radioUsuario = new JRadioButton("Usuario");
		radioUsuario.setSelected(true);
		radioUsuario.setBounds(10, 443, 88, 23);
		radioGrupo.add(radioUsuario);
		frame.getContentPane().add(radioUsuario);
		
		radioTopico = new JRadioButton("Topico");
		radioTopico.setBounds(102, 443, 81, 23);
		radioGrupo.add(radioTopico);
		frame.getContentPane().add(radioTopico);
		
		lblNome = new JLabel("Nome do destino");
		lblNome.setBounds(197, 447, 131, 15);
		frame.getContentPane().add(lblNome);
		
		lblDigiteAqui = new JLabel("Digite aqui sua mensagem");
		lblDigiteAqui.setBounds(10, 491, 201, 15);
		frame.getContentPane().add(lblDigiteAqui);
	}
	
	private void iniciaValores() {
		
		recebeMensagensUsuarios();
		setMensagemLog("Bem vindo(a) "+usuario.nome);
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
