package tm.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tm.server.util.ExecuteException;

public interface Bank extends Remote {
	
	public final String FIRSTBANKNAME = "Bank1";
	
	public int[] createNewCustomer(String firstName, String lastName, String bankCode) throws RemoteException, ExecuteException;
	
	public int createNewAccount(int customerId, String bankCode) throws RemoteException, ExecuteException;
	
	public boolean deleteCustomer(int customerId, String bankCode) throws RemoteException;
	
	public boolean deposit(int accountId, float amount, String bankCode) throws RemoteException;
	
	public boolean cashout(int accountId, float amount, String bankCode) throws RemoteException;
	
	public boolean remittance(int destAccountId, String destBankCode, int srcAccountId, String srcBankCode, float amount) throws RemoteException;
	
	public boolean transfer(int destAccountId, int srcAccountId, String bankCode, float amount) throws RemoteException;
}
