package tm.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import tm.server.Bank;

public class Client extends Thread  {
	
	boolean work;
	private Bank bank = null;

	public Client(String bankIP, String bankName) throws RemoteException, NotBoundException {
		work = true;
		
//		// the security manager is necessary for RMI
//		if (System.getSecurityManager() == null) {
//          System.setSecurityManager(new SecurityManager());
//        }
		
		// get registry and the bank (the remote object)
		Registry registry = LocateRegistry.getRegistry(bankIP, Registry.REGISTRY_PORT);
		bank = (Bank) registry.lookup(bankName);
	}
	
	public void run() {
		System.out.println("Client thread started.");
		
		while (work) {
			try {
	        	sleep(500);
	        } catch(InterruptedException e) {
	        	work = false;
	        }
		}
		
		System.out.println("Client thread ended.");
	}
	
	public void stopThread() {
		work = false;
	}
	
	public Bank getBankAccess() {
		return bank;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client c = new Client("127.0.0.1", Bank.FIRSTBANKNAME);
			c.start();
			
			//TODO: do anything like c.getBankAccess().doAnthing();
		} catch(Exception e) {
			System.out.println("Could not set up RMI on client side with reason:");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
}
