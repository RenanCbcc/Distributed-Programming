package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import interfaces.Services;

public final class Manager extends UnicastRemoteObject implements Services {

	private static Services services = null;
	private static final long serialVersionUID = 1L;
	private List<Account> accounts;

	protected Manager() throws RemoteException {
		super();
		accounts = new ArrayList<Account>();
	}

	@Override
	public boolean isPrimenumber(int number, int range) throws RemoteException {

		return false;
	}

	@Override
	public boolean createAccount(String name, int id) throws RemoteException {
		for (Account account : accounts) {
			if (account.getId() == id) {
				return false;
			}
		}
		accounts.add(new Account(name, id));
		return true;
	}

	@Override
	public boolean removeAccount(int id) throws RemoteException {
		for (Account account : accounts) {
			if (account.getId() == id) {
				return accounts.remove(account);
			}
		}

		return false;
	}

	public static Services getInstance() throws RemoteException {
		if (services == null) {
			services = new Manager();
		}
		return services;
	}

	public String getAccounst() throws RemoteException {
		return Arrays.toString(accounts.toArray());
	}
}
