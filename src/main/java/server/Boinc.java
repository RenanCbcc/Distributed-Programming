package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Boinc implements Runnable {

	// Connection Factory which will help in connecting to ActiveMQ server
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Scanner scanner;
	private Thread server;

	public Boinc() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		scanner = new Scanner(System.in);
		System.out.println("Boinc Systens is onLine!");
		Listening();
	}

	public void run() {
		try {

			connection.start();
			int op = 1;
			while (op != 0) {
				exhibitMenu();
				op = scanner.nextInt();
				switch (op) {
				case 1:
					createTopic();
					break;
				case 2:
					System.out.println("Anything here yet");
					break;
				case 3:
					System.out.println("Anything here yet");
					break;
				case 4:
					System.out.println("Anything here yet");
					break;

				}

			}
			// connection.close();

		} catch (JMSException jmse) {
			scanner.close();
			System.out.println("Exception: " + jmse.getMessage());
		}
	}

	private void Listening() {
		server = new Thread(new Listener());
		server.start();
		System.out.println("Boinc is listening for clients.");
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
			System.out.println("Exception: " + jmse.getMessage());
		}

		System.out.println("Operation have been finenhid with success");
	}

	public void showTopics() {
	}

	public void deleteTopics() {
	}

	public void exhibitMenu() {
		System.out.println("1 - Create a new topic");
		System.out.println("2 - Show all the topics");
		System.out.println("3 - Delete a topic");
		System.out.println("4 - Shut down the server");
	}

	public void shutDown() throws JMSException {
		connection.close();
		server.interrupt();

	}

	public void createTopic() {
		String topic = "";
		String body = "";
		System.out.print("Name of the new topic: ");
		topic = scanner.next();
		System.out.println("Body of the topic: ");
		body = scanner.next();
		send(topic, body);
	
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, JMSException {
		new Thread(new Boinc()).start();

	}
}
