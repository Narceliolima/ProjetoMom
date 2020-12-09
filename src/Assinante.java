import java.rmi.RemoteException;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;

public class Assinante implements MessageListener{
	
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private ServidorMOM server;
	private String nomeUsuario;
	private ActiveMQConnection conexao;

	public Assinante(ServidorMOM server, String nomeTopico, String nomeUsuario) {
		this.server = server;
		this.nomeUsuario = nomeUsuario;
		
		assina(nomeTopico);
	}
	
	private boolean assina(String nomeTopico) {
		
		conectaBroker();
		
		try {
			Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destino = sessao.createTopic(nomeTopico);
			MessageConsumer assinante = sessao.createConsumer(destino);
			assinante.setMessageListener(this);
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void onMessage(Message mensage) {
		if(mensage instanceof TextMessage){
			try{
				System.out.println(nomeUsuario+"receberia");
				System.out.println(((TextMessage)mensage).getText());
			}catch(JMSException e){
				e.printStackTrace();
			}
		}
	}
	
	private void conectaBroker() {
		
		try {
			conexao = ActiveMQConnection.makeConnection(url);
			conexao.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void desconectaBroker() {
		
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
