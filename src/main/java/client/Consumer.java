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
	private String message;
	private static JFrame frame = new JFrame();

	public Consumer(ActiveMQConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Consumer(String name) throws JMSException {
		this.name = name;
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();

	}

	public void run() {
		try {
			connection.start();

			String[] options = { "Join in a new topic", "Leave a topic", "Process a request", "Shut down the client" };
			String str;
			while (true) {
				String choice = (String) JOptionPane.showInputDialog(frame, "Choose a method", "Options",
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				switch (choice) {
				case "Join in a new topic":
					str = "Name of the topic: ";
					str = JOptionPane.showInputDialog(null, str);
					joinTopic(str);
					break;
				case "Leave a topic":
					str = "Name of the topic: ";
					str = JOptionPane.showInputDialog(null, str);
					leaveTopic(str);

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
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
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
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topicDestination = session.createTopic(topic);
		MessageConsumer messageConsumer = session.createConsumer(topicDestination);
		Message message = messageConsumer.receiveNoWait();
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
}
