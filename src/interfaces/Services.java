package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Used to intermediate the communication between client and server.
 * @author Renan Rosa
 *
 */
public interface Services extends Remote {
	
	public boolean isPrimenumber (int number, int range) throws RemoteException;
	
	public boolean createAccount(String name, int id) throws RemoteException;
	
	public boolean removeAccount(int id) throws RemoteException;
	
	public String getAccounst() throws RemoteException;
}
