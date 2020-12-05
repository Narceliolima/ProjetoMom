import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueSession;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQQueue;

public class ServidorMOM {
	
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private ActiveMQConnection conexao;
	
	public void conectaBroker() {
		
		try {
			conexao = ActiveMQConnection.makeConnection(url);
			conexao.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void desconectaBroker() {
		
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean verificaFilaExiste(String nomeFila) {
		
		ArrayList<String> nomeFilas = getFilas();
		int i;
		
		for(i=0;i<nomeFilas.size();i++) {
			if(nomeFila.contentEquals(nomeFilas.get(i))) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean criaFila(String nomeFila) {
		
		conectaBroker();
		
		try {
			Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destino = sessao.createQueue(nomeFila);
			MessageProducer produtor = sessao.createProducer(destino);
			produtor.close();
			sessao.close();
			//Assim que instanciar o cliente, criar uma fila e atrela-lo a ela, usando thread?
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		}
		
		desconectaBroker();
		return true;
	}
	
	public boolean criaTopico(String nomeTopico) {
		
		conectaBroker();
		
		try {
			Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destino = sessao.createTopic(nomeTopico);
			MessageProducer publicador = sessao.createProducer(destino);
			publicador.close();
			sessao.close();
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		}
		
		desconectaBroker();
		
		return true;
	}
	
	public void removeFila(String nomeFila) {
		
		conectaBroker();
		
		try {
			conexao.destroyDestination(new ActiveMQQueue(nomeFila));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		desconectaBroker();
	}
	
	public void removeTopico() {
		
	}
	
	public ArrayList<String> getFilas() {
		
		ArrayList<String> nomeFilas = new ArrayList<String>();
		
		conectaBroker();
		
		try {
			Set<ActiveMQQueue> listaFila = conexao.getDestinationSource().getQueues();
			Iterator<ActiveMQQueue> iterator = listaFila.iterator();
			while(iterator.hasNext()) {
				nomeFilas.add(iterator.next().getQueueName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		desconectaBroker();
		
		return nomeFilas;
	}

	public int getQuantidadeMsg(String nomeFila) {
		
		int k = 0;
		
		conectaBroker();
		
		try {
			QueueSession sessaoFila = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue fila = sessaoFila.createQueue(nomeFila);
			QueueBrowser browser = sessaoFila.createBrowser(fila);
			Enumeration<?> mensagensNaFila = browser.getEnumeration();
			
			while (mensagensNaFila.hasMoreElements()) {
				mensagensNaFila.nextElement();
				k++;
			}
			
			sessaoFila.close();
			browser.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		desconectaBroker();
		
		return k;
	}
}
