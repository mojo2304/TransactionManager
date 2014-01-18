package tm.client;

import java.rmi.registry.Registry;

import tm.server.Bank;

public interface Client {
	
	final String standardBankIPAdress = "127.0.0.1";
	final int standardBankPort = Registry.REGISTRY_PORT;
	final String standardBankName = Bank.FIRSTBANKNAME;
	
	public boolean connectToBank(String bankIPAdress, int bankPort, String bankName);
	
	public Bank getBankAccess();
}
