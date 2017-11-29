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

public class ConsumerSocket implements Runnable {

	private String body;
	private String name;
	private Socket socket;

	public ConsumerSocket(String name, String body) throws UnknownHostException, IOException {
		this.body = body;
		this.name = name;
		socket = new Socket(name, 13);
	}

	public void run() {

		try {
			String builder = new String("Message from :" + name + " \t" + body);
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

			InputStream stream = new ByteArrayInputStream(builder.getBytes(StandardCharsets.UTF_8.name()));
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(stream));
			toServer.writeBytes(fromUser.readLine().toUpperCase());
			socket.close();
		} catch (IOException e) {
			System.err.println(e);
		}

	}

}
