import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher{

	/*
	 * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
	*/	
	
 	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	private static String topicName = "raspberry";
    
	
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
		 * Criando Topic
		 */     
		Destination dest = session.createTopic(topicName);

		/*
		 * Criando Produtor
		 */
		MessageProducer publisher = session.createProducer(dest);

		TextMessage message = session.createTextMessage();
		message.setText("Mensagem do Publisher.");


		/*
		 * Publicando Mensagem
		 */
		publisher.send(message);
       
		publisher.close();
		session.close();
		connection.close();
	}
   
}
