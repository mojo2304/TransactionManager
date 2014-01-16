package tm.server.impl;
import java.rmi.RemoteException;

import tm.server.Bank;
import tm.server.model.Result;
import tm.server.model.Transaction;
import tm.server.model.operation.*;
import tm.server.util.ExecuteException;

/**
 * Our global transaction manager GTM
 */
public class BankImpl implements Bank {
	
	private TransactionManager _tm;
	
	public BankImpl() throws Exception {
		_tm = new TransactionManager();
	}
	
	public int[] createNewCustomer(String firstName, String lastName, String bankCode) throws ExecuteException {
		int[] r = new int[2];
		
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			InsertCustumer ic = new InsertCustumer(t, firstName, lastName);
			Result[] result = _tm.execute(ic, bankCode);
			r[0] = result[0].getInt();
			
			InsertAccount ia = new InsertAccount(t, r[0]);
			Result[] result2 = _tm.execute(ia, bankCode);
			r[1] = result2[0].getInt();
			
			t.commit();
			
			return r;
		} catch (ExecuteException e) {
			t.abort();
			throw new ExecuteException("Custumer konnte nicht angelegt werden. Warum auch immer." + e.getMessage());
		} catch (RemoteException e) {
			t.abort();
			throw new ExecuteException("Custumer konnte nicht angelegt werden. Warum auch immer. " + e.getMessage());
		}
	}
	
	public int createNewAccount(int customerId, String bankCode) throws ExecuteException {
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			InsertAccount ia = new InsertAccount(t, customerId);
			Result[] result = _tm.execute(ia, bankCode);
			
			t.commit();
			
			return result[0].getInt();
		} catch (ExecuteException e) {
			t.abort();
			throw new ExecuteException("Account konnte nicht angelegt werden. Warum auch immer.");
		} catch (RemoteException e) {
			t.abort();
			throw new ExecuteException("Account konnte nicht angelegt werden. Warum auch immer.");
		}
	}
	
	public boolean deleteCustomer(int customerId, String bankCode) {
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			// bekomme alle Accounts eines Nutzers
			AllAccountsByCustumer a = new AllAccountsByCustumer(t, customerId);
			Result[] result = _tm.execute(a, bankCode);
			
			// Lösche alle Accounts
			for (int i=0; i<result.length; i++) {
				int accounntID = result[i].getInt();
				DeleteAccount da = new DeleteAccount(t, accounntID);
				_tm.execute(da, bankCode);
			}
			
			// Lösche abschließend den Kunden
			DeleteCustumer dc = new DeleteCustumer(t, customerId);
			_tm.execute(dc, bankCode);

			t.commit();
			
			return true;
		} catch (ExecuteException e) {
			t.abort();
			return false;
		} catch (RemoteException e) {
			t.abort();
			return false;
		}
	}
	
	// Einzahlen
	public boolean deposit(int accountId, float amount, String bankCode) {
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			Read r = new Read(t, accountId);
			Result[] result = _tm.execute(r, bankCode);
			float currentAccountBalance = result[0].getFloat();
			
			float newAccountBalance = currentAccountBalance + amount;
			
			Write w = new Write(t, accountId, newAccountBalance);
			_tm.execute(w, bankCode);
			
			t.commit();
			
			return true;
		} catch (ExecuteException e) {
			t.abort();
			return false;
		} catch (RemoteException e) {
			t.abort();
			return false;
		}
	}
	
	// Auszahlen
	public boolean cashout(int accountId, float amount, String bankCode) {
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			Read r = new Read(t, accountId);
			Result[] result = _tm.execute(r, bankCode);
			float currentAccountBalance = result[0].getFloat();
			
			if (currentAccountBalance < amount) {
				t.abort();
				throw new ExecuteException("There ist not enough money to cash out!");
			}
			
			float newAccountBalance = currentAccountBalance - amount;
			
			Write w = new Write(t, accountId, newAccountBalance);
			_tm.execute(w, bankCode);
			
			t.commit();
			
			return true;
		} catch (ExecuteException e) {
			t.abort();
			return false;
		} catch (RemoteException e) {
			t.abort();
			return false;
		}
	}
	
	// Überweisung
	public boolean remittance(int destAccountId, String destBankCode, int srcAccountId, String srcBankCode, float amount) {
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			if (destBankCode.equalsIgnoreCase(srcBankCode)) {
				t.abort();
				throw new ExecuteException("Please use a transfer instead of a remittance!");
			}
			
			Read srcRead = new Read(t, destAccountId);
			Result[] srcResult = _tm.execute(srcRead, srcBankCode);
			float srcAccountBalance = srcResult[0].getFloat();
			
			if (srcAccountBalance < amount) {
				t.abort();
				throw new ExecuteException();
			}
			
			Read destRead = new Read(t, destAccountId);
			Result[] destResult = _tm.execute(destRead, destBankCode);
			float destAccountBalance = destResult[0].getFloat();
			
			float newDestAccountBalance = destAccountBalance + amount;
			Write destWrite = new Write(t, destAccountId, newDestAccountBalance);
			_tm.execute(destWrite, destBankCode);
			
			float newSrcAccountBalance = srcAccountBalance - amount;
			Write srcWrite = new Write(t, srcAccountId, newSrcAccountBalance);
			_tm.execute(srcWrite, srcBankCode);
			
			t.commit();
			
			return true;
		} catch (ExecuteException e) {
			t.abort();
			return false;
		} catch (RemoteException e) {
			t.abort();
			return false;
		}
	}
	
	// Umbuchung
	public boolean transfer(int destAccountId, int srcAccountId, String bankCode, float amount) {
		Transaction t = _tm.createNewTransaction();
		t.begin();
		
		try {
			Read srcRead = new Read(t, destAccountId);
			Result[] srcResult = _tm.execute(srcRead, bankCode);
			float srcAccountBalance = srcResult[0].getFloat();
			
			if (srcAccountBalance < amount) {
				t.abort();
				throw new ExecuteException();
			}
			
			Read destRead = new Read(t, destAccountId);
			Result[] destResult = _tm.execute(destRead, bankCode);
			float destAccountBalance = destResult[0].getFloat();
			
			float newDestAccountBalance = destAccountBalance + amount;
			Write destWrite = new Write(t, destAccountId, newDestAccountBalance);
			_tm.execute(destWrite, bankCode);
			
			float newSrcAccountBalance = srcAccountBalance - amount;
			Write srcWrite = new Write(t, srcAccountId, newSrcAccountBalance);
			_tm.execute(srcWrite, bankCode);
			
			t.commit();
			
			return true;
		} catch (ExecuteException e) {
			t.abort();
			return false;
		} catch (RemoteException e) {
			t.abort();
			return false;
		}
	}
}
