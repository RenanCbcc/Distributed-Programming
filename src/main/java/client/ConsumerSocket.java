package client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.swing.JOptionPane;

public class ConsumerSocket implements Runnable {

	private String body;
	private String name;
	private Socket socket;
	public final static int PORT = 1987;
	
	public ConsumerSocket(String name, String body) throws UnknownHostException, IOException {
		
		this.body = body;
		this.name = name;
		socket = new Socket("localhost", PORT);
		
	}

	public void run() {

		try {
			System.out.println("passou -2");
			String builder = new String("Message from :" + name + " \t" + body);
			System.out.println("passou -1");
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			System.out.println("passou 0");
			InputStream stream = new ByteArrayInputStream(builder.getBytes(StandardCharsets.UTF_8.name()));
			System.out.println("passou 1");
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(stream));
			System.out.println("passou 2");
			toServer.writeBytes(fromUser.readLine().toUpperCase());
			System.out.println("passou 3");
			socket.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
			
		}

	}

}
