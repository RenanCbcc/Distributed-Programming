package client;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import interfaces.Services;

public class Main {

	private static JFrame frame = new JFrame();
	int one, two, three;

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {

		Registry resgistry = LocateRegistry.getRegistry(666);
		Services manager = (Services) resgistry.lookup("Boinc");

		String str = "";
		String[] options = { "Calculate Nº", "Create Account", "Delete Account","Exhibit Accounts","Exit" };

		while (true) {
			String choose = (String) JOptionPane.showInputDialog(frame, "Choose a method", "Options",
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			switch (choose) {
			case "Calculate Nº":
				break;
			case "Create Account":
				str = "Informe a Number:";
				str = JOptionPane.showInputDialog(null, str);
				if (manager.createAccount("", Integer.parseInt(str))) {
					JOptionPane.showMessageDialog(null, "Account created successfully!");
				}
				break;
			case "Delete Account":
				str = JOptionPane.showInputDialog(null, "If you are sure, insert the Id");
				if (manager.removeAccount(Integer.parseInt(str))) {
					JOptionPane.showMessageDialog(null, "Account deleted successfully!");
				}
				break;

			case "Exhibit Accounts":
				JOptionPane.showMessageDialog(null, manager.getAccounst());
				break;

			case "Exit":
				System.exit(0);

			}
		}

	}

}
