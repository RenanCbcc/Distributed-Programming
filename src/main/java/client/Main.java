package client;

import javax.jms.JMSException;

public class Main {

	public static void main(String[] args) throws JMSException {

		new Thread(new Consumer(args[0])).start();
	}

}
