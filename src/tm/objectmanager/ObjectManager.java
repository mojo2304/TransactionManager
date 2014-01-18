package tm.objectmanager;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tm.server.model.Operation;
import tm.server.model.Result;
import tm.server.model.Transaction;
import tm.server.util.ExecuteException;

public interface ObjectManager extends Remote {

	public final String FIRSTOM = "om1";
	public final String SECONDOM = "om2";
	
	public void deleteTables() throws RemoteException;
	
	public Result[] executeOperation(Operation op) throws RemoteException, ExecuteException;
	
	/**
	 * check if we can commit a transaction t or not (2 Phase Commit Protocol - 2PCP)
	 * 
	 * @return true if we can commit, false otherwise
	 * @throws RemoteException
	 */
	public boolean checkCommit(Transaction t) throws RemoteException;
	
	
	/**
	 * commit a transaction t (2PCP) 
	 *
	 * @throws RemoteException
	 */
	public void commit(Transaction t) throws RemoteException;
	
	/**
	 * roll back a transaction t (2PCP)
	 * 
	 * @throws RemoteException
	 */
	public void rollback(Transaction t) throws RemoteException;
	
	public void printTableEntries(String tableName) throws RemoteException;
	
	public void dumpDBEntries(String filename) throws RemoteException;
}
