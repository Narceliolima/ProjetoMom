import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;


public class GerenciadorGUI {

	//--------------------------------------------/-------------/--------------------------------------------//
	private JFrame frame;
	private GerenciadorGUI window;
	private ServidorMOM server;
	private ArrayList<String> listaFilas;
	private ArrayList<Integer> listaQntMensagens;
	private ActionListener ato;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Paineis/Scroll/--------------------------------------------//
	private JPanel resumoTopicos;
	private JPanel resumoFilas;
	private JPanel painelTopicos;
	private JPanel painelFilas;
	private JScrollPane scrollPT;
	private JScrollPane scrollPF;
	private ArrayList<JLabel> celulasFila = new ArrayList<JLabel>();
	private ArrayList<JLabel> celulasTopico = new ArrayList<JLabel>();
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Labels/Botoes/--------------------------------------------//
	private JLabel labelFilas;
	private JLabel labelTopicos;
	private JLabel nomeFila;
	private JLabel nomeTopico;
	private JLabel qntMensagensFila;	
	private JLabel semNomePorEnquanto;
	private JButton addUsuario;
	private JButton addTopico ;
	private JLabel contaCelulas;
	private ArrayList<JButton> xButton = new ArrayList<JButton>();
	//--------------------------------------------/-------------/--------------------------------------------//
	
	public static void main(String[] args) {
		
		GerenciadorGUI gui = new GerenciadorGUI();
		
	}

	public GerenciadorGUI() {
		window = this;
		server = new ServidorMOM();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		iniciaPaineis();
		iniciaLabels();
		iniciaValores();
		createRunnable();
	}
	
	public void atualizaInterface() {
		
		//int celula = 0;
		
		contaCelulas.setText(""+celulasFila.size());
		contaCelulas.setText(""+celulasTopico.size());
		
		/*while(celula<celulasFila.size()) {
			//celulasFila.get(celula).setText("0");
			celula++;
		}*/
	}
	
	public void varreBotao() {
		
		ato = new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == addUsuario) {
					String nome = Notificacao.addFila();
					if(nome!=null) {
						if(!server.verificaFilaExiste(nome)) {
							server.criaFila(nome);
							criarBotao();
							criarLabelFila(nome);
							criarLabelFila("0");
						}
					}
				}
				if(arg0.getSource() == addTopico) {
					String nome = Notificacao.addTopico();
					if(nome!=null) {
						//if(!server.verificaFilaExiste(nome)) {
						server.criaTopico(nome);
						criarLabelTopico(nome);
						//}
					}
				}
				for(int i=0;i<xButton.size();i++) {
					if(arg0.getSource() == xButton.get(i)) {
						System.out.println(celulasFila.get(i*2).getText());
						//server.removeFila(listaFilas.get(i));
						//removeBotao(i);
						//removeLabelFila(i);
						//removeLabelFila(i);
					}
				}
				atualizaInterface();
			}
		};
		
		addUsuario.addActionListener(ato);
		addTopico.addActionListener(ato);
		
		/*for(int i=0;i<xButton.size();i++) {
			xButton.get(i).addActionListener(ato);
			System.out.println(i);
		}*/
	}
	
	public void criarBotao() {
		xButton.add(new JButton("X"));
		xButton.get(xButton.size()-1).addActionListener(ato);
		painelFilas.add(xButton.get(xButton.size()-1));
		System.out.println(xButton.size()-1);
	}
	
	public void removeBotao(int i) {
		xButton.remove(i);
		painelFilas.remove(i*3);
	}
	
	public void criarLabelFila(String valor) {
		celulasFila.add(new JLabel(""+valor));
		painelFilas.add(celulasFila.get(celulasFila.size()-1));
	}
	
	public void removeLabelFila(int i) {
		celulasFila.remove(i*2);
		painelFilas.remove(i*3);
	}
	
	public void criarLabelTopico(String valor) {
		celulasTopico.add(new JLabel(""+valor));
		painelTopicos.add(celulasTopico.get(celulasTopico.size()-1));
	}

	public void setlistaFilas(ArrayList<String> listaFilas) {
		this.listaFilas = listaFilas;
	}

	public void setListaQntMensagens(ArrayList<Integer> listaQntMensagens) {
		this.listaQntMensagens = listaQntMensagens;
	}
	
	public void preenchePainelFila() {
		for(int i=0;i<listaFilas.size();i++) {
			criarBotao();
			criarLabelFila(listaFilas.get(i));
			criarLabelFila(""+listaQntMensagens.get(i));
		}
		//varreBotao();
	}
	
	private void iniciaPaineis() {
		
		resumoFilas = new JPanel();
		resumoFilas.setBounds(0, 0, 259, 435);
		resumoFilas.setLayout(null);
		frame.getContentPane().add(resumoFilas);
		
		resumoTopicos = new JPanel();
		resumoTopicos.setLayout(null);
		resumoTopicos.setBounds(267, 0, 259, 435);
		frame.getContentPane().add(resumoTopicos);
		
		scrollPF = new JScrollPane();
		scrollPF.setBounds(12, 52, 235, 340);
		resumoFilas.add(scrollPF);
		
		scrollPT = new JScrollPane();
		scrollPT.setBounds(12, 52, 235, 340);
		resumoTopicos.add(scrollPT);
		
		painelFilas = new JPanel();
		scrollPF.setViewportView(painelFilas);
		painelFilas.setLayout(new GridLayout(0, 3, 10, 10));
		
		painelTopicos = new JPanel();
		scrollPT.setViewportView(painelTopicos);
		painelTopicos.setLayout(new GridLayout(0, 1, 10, 10));
		
		contaCelulas = new JLabel("0");
		contaCelulas.setBounds(544, 12, 70, 15);
		frame.getContentPane().add(contaCelulas);
	}
	
	private void iniciaLabels() {
		
		labelFilas = new JLabel("Filas");
		labelFilas.setBounds(102, 6, 44, 15);
		resumoFilas.add(labelFilas);
		
		labelTopicos = new JLabel("Topicos");
		labelTopicos.setBounds(93, 6, 63, 15);
		resumoTopicos.add(labelTopicos);
		
		nomeFila = new JLabel("Nome");
		nomeFila.setBounds(68, 33, 53, 15);
		resumoFilas.add(nomeFila);
		
		nomeTopico = new JLabel("Nome");
		nomeTopico.setBounds(68, 33, 53, 15);
		resumoTopicos.add(nomeTopico);
		
		qntMensagensFila = new JLabel("Qnt mensagens");
		qntMensagensFila.setBounds(133, 33, 126, 15);
		resumoFilas.add(qntMensagensFila);
		
		semNomePorEnquanto = new JLabel("????");
		semNomePorEnquanto.setBounds(133, 33, 126, 15);
		resumoTopicos.add(semNomePorEnquanto);
		
		addUsuario = new JButton("Novo Usuario");
		addUsuario.setBounds(12, 398, 235, 25);
		resumoFilas.add(addUsuario);
		
		addTopico = new JButton("Novo Topico");
		addTopico.setBounds(12, 398, 235, 25);
		resumoTopicos.add(addTopico);
	}
	
	private void iniciaValores() {
		
		listaFilas = server.getFilas();
		listaQntMensagens = new ArrayList<Integer>();
		for(int i=0;i<listaFilas.size();i++) {
			listaQntMensagens.add(server.getQuantidadeMsg(listaFilas.get(i)));
		}
		
		preenchePainelFila();
		atualizaInterface();
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
