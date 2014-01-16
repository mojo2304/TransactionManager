package tm.server.impl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import tm.server.model.Operation;
import tm.server.model.Result;
import tm.server.model.Transaction;
import tm.server.util.ConnectException;
import tm.server.util.ExecuteException;

public class TransactionManager {

	private int _id;
	private Scheduler _scheduler;
	private Log _log;
	
	public TransactionManager() throws Exception {
		_id = 0;
		try {
			_scheduler = new Scheduler();
		} catch (NotBoundException e) {
			throw new ConnectException("ObjectManager existiert nicht...");
		} catch (RemoteException e) {
			throw new ConnectException("Verbindung zum ObjectManager fehlgeschlagen!");
		}
		
		_log = new Log();
	}
	
	public Transaction createNewTransaction() {
		return new Transaction(_id++, _log);
	}
	
	public Result[] execute(Operation op, String bankCode) throws ExecuteException, RemoteException {
		return _scheduler.schedule(op, bankCode);
	}
	
}
