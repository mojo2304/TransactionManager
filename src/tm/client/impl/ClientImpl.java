package tm.client.impl;

import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tm.client.view.ClientGUI;
import tm.server.Bank;

public class ClientImpl extends Thread  {
	
	boolean _work;
	
	private Bank bank = null;
	
	final String _bankIPAdress = "127.0.0.1";
	final int _bankPort = Registry.REGISTRY_PORT;
	final String _bankName = Bank.FIRSTBANKNAME;

	public ClientImpl() {
		_work = true;
	}
	
	public boolean connectToBank(String bankIPAdress, int bankPort, String bankName) {
//		// the security manager is necessary for RMI
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}

		try {
			Registry registry = LocateRegistry.getRegistry(bankIPAdress, bankPort);
			bank = (Bank) registry.lookup(bankName);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public void run() {
		buildGUI();
		
		while (_work) {
			try {
	        	sleep(500);
	        } catch(InterruptedException e) {
	        	_work = false;
	        }
		}
	}
	
	private void buildGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void stopThread() {
		_work = false;
	}
	
	public Bank getBankAccess() {
		return bank;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientImpl c = new ClientImpl();
		c.start();
	}
}
