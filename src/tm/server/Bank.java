package tm.server;

import java.rmi.Remote;

import tm.model.ResultGUI;

public interface Bank extends Remote {
	
	public final String FIRSTBANKNAME = "Bank1";
	
	public ResultGUI<Integer[]> createNewCustomer(String firstName, String lastName, String bankCode);
	
	public ResultGUI<Integer> createNewAccount(int customerId, String bankCode);
	
	public ResultGUI<Void> deleteCustomer(int customerId, String bankCode);
	
	public ResultGUI<Void> deposit(int accountId, float amount, String bankCode);
	
	public ResultGUI<Void> cashout(int accountId, float amount, String bankCode);
	
	public ResultGUI<Void> remittance(int destAccountId, String destBankCode, int srcAccountId, String srcBankCode, float amount);
	
	public ResultGUI<Void> transfer(int destAccountId, int srcAccountId, String bankCode, float amount);
	
	public ResultGUI<Float> getAccountBalance(int accountId, String bankCode);
}
