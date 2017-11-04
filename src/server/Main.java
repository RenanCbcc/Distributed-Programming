package server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(666);
		registry.bind("Boinc", Manager.getInstance());
		System.out.println("Manager running...");
	}

}
