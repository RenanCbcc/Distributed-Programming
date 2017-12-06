package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {

	public final static int PORT = 1984;
	ServerSocket serverSocket;

	public void run() {
		System.out.println("Boinc is listening for clients!");
		try {

			serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socketConnection = serverSocket.accept();
				BufferedReader fromClient = new BufferedReader(
						new InputStreamReader(socketConnection.getInputStream()));
				System.out.println("From client: "+fromClient.readLine().toString());
			}
		} catch (IOException e) {
			System.err.println(e);
			
		}
	}

}
