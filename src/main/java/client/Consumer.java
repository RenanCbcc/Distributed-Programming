package client;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable {

	// Connection Factory which will help in connecting to ActiveMQ server
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private String name;
	private String message = "Science is a way of thinking much more than it is a body of knowledge";
	private static JFrame frame = new JFrame();


	public Consumer(String name) throws JMSException {
		this.name = name;
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();

	}

	public void run() {
		try {
			connection.start();

			String[] options = { "Join in a new topic", "Leave a topic", "Process a request", "Shut down the client" };
			
			while (true) {
				String choice = (String) JOptionPane.showInputDialog(frame, "Choose a method", name,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				switch (choice) {
				case "Join in a new topic":					
					joinTopic(JOptionPane.showInputDialog(null, "Name of the topic"));
					break;
				case "Leave a topic":
					leaveTopic(JOptionPane.showInputDialog(null, "Name of the topic: "));
					break;
				case "Process a request":
					processRequest();
					break;
				case "Shut down the client":
					shutDown();
					System.exit(0);
					break;
				}
			}

			/// connection.close();
		} catch (JMSException jmse) {
			JOptionPane.showMessageDialog(null, jmse.getMessage(), "Error", 0);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()+" UnknownHostException", "Errorr", 0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
		
		}
	}

	private void processRequest() throws UnknownHostException, IOException {
		if (message.length() == 0) {

			JOptionPane.showMessageDialog(null, "No requests yet", "Info", 1);
		} else {
			new Thread(new ConsumerSocket(this.name, this.message)).start();
		}
	}

	private void joinTopic(String topic) throws JMSException {
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topicDestination = session.createTopic(topic);
		MessageConsumer messageConsumer = session.createConsumer(topicDestination);
		System.out.println("Just wating...");
		Message message = messageConsumer.receive();
		TextMessage textMessage = (TextMessage) message;
		JOptionPane.showMessageDialog(null, textMessage.getText(), "Message from Boinc: ", 1);
		this.message = textMessage.toString();
		session.close();

	}

	private void leaveTopic(String topic) {
	}

	private void shutDown() throws JMSException {
		connection.close();

	}
	
	public static void main(String[] args) throws JMSException {
		new Thread(new Consumer("One")).start();
		
	}
}
