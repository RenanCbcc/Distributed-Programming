package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable {

	// Connection Factory which will help in connecting to ActiveMQ server
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Scanner scanner;
	private String name;
	private String message;

	public Consumer(ActiveMQConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Consumer(String name) throws JMSException {
		this.name = name;
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		scanner = new Scanner(System.in);
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
					System.out.println("Name of the topic: ");
					joinTopic(scanner.nextLine());
					break;
				case 2:
					System.out.println("Name of the topic: ");
					leaveTopic(scanner.nextLine());
					break;
				case 3:
					System.out.println("Process a request");
					processRequest();
					break;
				case 4:
					System.out.println("Closing the connection");
					shutDown();
					break;

				}

			}

			/// connection.close();
		} catch (JMSException jmse) {
			System.out.println("Exception: " + jmse.getMessage());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exhibitMenu() {
		System.out.println("1 - Join in a new topic");
		System.out.println("2 - Leave a topic");
		System.out.println("3 - Process a request");
		System.out.println("4 - Shut down the client");
	}

	private void processRequest() throws UnknownHostException, IOException {
		if (message.length() == 0) {
			System.out.println("No requests yet");
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
		System.out.println("Message from Boinc: " + textMessage.getText());
		this.message = textMessage.toString();
		session.close();

	}

	private void leaveTopic(String topic) {
	}

	private void shutDown() throws JMSException {
		connection.close();

	}
}
