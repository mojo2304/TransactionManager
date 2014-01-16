package tm.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import tm.server.impl.BankImpl;

public class Server extends Thread {
	
	boolean _work;
	private Bank _bank = null;
	
	public Server() throws Exception {
		_work = true;
		
//		// the security manager is necessary for RMI
//		if (System.getSecurityManager() == null) {
//		    System.setSecurityManager(new SecurityManager());
//		}
		
		// now setting up RMI: first, create object and make it remote
		_bank = new BankImpl();
		Bank skeleton = (Bank) UnicastRemoteObject.exportObject(_bank, Registry.REGISTRY_PORT);

		// second, get registry and publish the remote object
		LocateRegistry.getRegistry().rebind(Bank.FIRSTBANKNAME, skeleton);
	}
	
	public void run() {
		System.out.println("Server thread started.");
		
		while (_work) {
			try {
	        	sleep(500);
	        } catch(InterruptedException e) {
	        	_work = false;
	        }
		}
		
		System.out.println("Server thread ended.");
	}
	
	public void stopThread() {
		_work = false;
	}
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {		
//		try {
//			Server s = new Server();
//			s.start();
//			
//			System.out.println("Set up RMI successfully.");
//			
//			Registry registry = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
//			Bank stub = (Bank) registry.lookup(Bank.BINDNAME);
//			
//			stub.printTableEntries("KUNDEN");
//			System.out.println("\n\n-----\n\n");
//			stub.printTableEntries("KONTEN");
//		} catch(Exception e) {
//			System.out.println("Could not set up RMI on server side with reason:");
//			System.out.println(e.getMessage());
//			System.exit(-1);
//		}
//	}
}
