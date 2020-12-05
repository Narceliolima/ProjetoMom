import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Produtor
{

	/*
	 * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
	 * 
	 */	
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	private static String queueName = "teste";

	public static void main(String[] args) throws JMSException
	{

		/*
		 * Estabelecendo conexão com o Servidor JMS
		 */		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		/*
		 * Criando Session 
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		/*
		 * Criando Queue
		 */
		Destination destination = session.createQueue(queueName);

		/*
		 * Criando Produtor
		 */		
		MessageProducer producer = session.createProducer(destination);
		TextMessage message = session.createTextMessage("Eah boy");

		/*
		 * Enviando Mensagem
		 */
		producer.send(message);

		System.out.println("Messagem: '" + message.getText() + ", Enviada para a Fila");

		producer.close();
		session.close();
		connection.close();
	}
}
