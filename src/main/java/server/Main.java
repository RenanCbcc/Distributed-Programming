package server;

import java.util.Scanner;

public class Main {
	private static Scanner scanner;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		String topic,body;
		System.out.println("Name of the new topic: ");
		topic = scanner.nextLine();
		System.out.println("Body of the topic: ");
		body = scanner.nextLine();
		send(topic, body);
	

	}
	
	private static void send(String topic, String body) {
		scanner.close();
		System.out.println("Operation has been finenhid with success");
		System.out.println("sent message: \n"+ topic+" - "+body);
	}


}
