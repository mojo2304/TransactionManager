package tm.objectmanger;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tm.server.model.Operation;
import tm.server.model.Result;
import tm.server.util.ExecuteException;

public interface ObjectManager extends Remote {

	public final String FIRSTOM = "om1";
	public final String SECONDOM = "om2";
	
	public void deleteTables() throws RemoteException;
	
	public Result[] executeOperation(Operation op) throws RemoteException, ExecuteException;
	
	public void printTableEntries(String tableName) throws RemoteException;
	
	public void dumpDBEntries(String filename) throws RemoteException;
}
