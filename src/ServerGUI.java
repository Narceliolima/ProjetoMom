import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ServerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<JLabel> paineis;
	private int i = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}

	/**
	 * Create the frame.
	 */
	public ServerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 691, 402);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel qntMensagensFila = new JLabel("Qnt. Mensagens");
		qntMensagensFila.setBounds(98, 12, 129, 15);
		panel.add(qntMensagensFila);
		
		JLabel qnttopico = new JLabel("Qnt. Topicos");
		qnttopico.setBounds(248, 201, 101, 15);
		panel.add(qnttopico);
		
		JLabel valorF = new JLabel("0");
		valorF.setBounds(124, 39, 70, 15);
		panel.add(valorF);
		
		JLabel valorT = new JLabel("0");
		valorT.setBounds(124, 66, 70, 15);
		panel.add(valorT);
		
		JLabel nomeFila = new JLabel("Nome");
		nomeFila.setHorizontalAlignment(SwingConstants.CENTER);
		nomeFila.setBounds(12, 12, 70, 15);
		panel.add(nomeFila);
	}
}
