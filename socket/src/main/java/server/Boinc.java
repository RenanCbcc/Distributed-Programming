package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Boinc implements Runnable {

	// Connection Factory which will help in connecting to ActiveMQ server
	//Science is a way of thinking much more than it is a body of knowledge.
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Thread server;
	private static JFrame frame = new JFrame();

	public Boinc() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		System.out.println("Boinc Systens is onLine!");
		Listening();
	}

	public void run() {

		try {
			connection.start();

			String[] options = { "Create a new topic", "Show all the topics", "Delete a topic", "Shut down the server" };
			while (true) {
				String choice = (String) JOptionPane.showInputDialog(frame, "Select a method", "Boinc Options",
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				switch (choice) {
				case "Create a new topic":
					createTopic();
					break;
				case "Show all the topics":
					JOptionPane.showMessageDialog(null, "Anything here yet", "Info", 1);
					break;
				case "Delete a topic":
					JOptionPane.showMessageDialog(null, "Anything here yet", "Info", 1);
					break;
				case "Shut down the server":
					shutDown();
					System.exit(0);
					break;
				}
			}

		} catch (JMSException jmse) {
			JOptionPane.showMessageDialog(null, jmse.getMessage(), "Error", 0);
		}
	}

	private void Listening() {
		server = new Thread(new Listener());
		server.start();

	}

	private void send(String topic, String body) {
		
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(topic);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage message = session.createTextMessage(body);
			producer.send(message);
			session.close();
		} catch (JMSException jmse) {
			JOptionPane.showMessageDialog(null, jmse.getMessage(), "Error", 0);

		}
		JOptionPane.showMessageDialog(null, "Operation has been finished with success", "Info", 1);
	}

	public void showTopics() {
	}

	public void deleteTopics() {
	}

	public void shutDown() throws JMSException {
		connection.close();
		server.interrupt();

	}

	public void createTopic() {

		String topic, body;
		topic = JOptionPane.showInputDialog(null, "Name of the topic");
		body = JOptionPane.showInputDialog(null, "Body of the topic");
		send(topic, body);

	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, JMSException {
		new Thread(new Boinc()).start();

	}
}
