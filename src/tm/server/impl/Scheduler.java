package tm.server.impl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tm.objectmanager.ObjectManager;
import tm.server.model.Operation;
import tm.server.model.Result;
import tm.server.util.ExecuteException;


public class Scheduler {

	private ObjectManager _om1 = null;
	private ObjectManager _om2 = null;
	
	public Scheduler() throws RemoteException, NotBoundException {
		// get registry and the remote object
		Registry registry1 = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
		_om1 = (ObjectManager) registry1.lookup(ObjectManager.FIRSTOM);
		
		// get registry and the remote object
		Registry registry2 = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
		_om2 = (ObjectManager) registry2.lookup(ObjectManager.SECONDOM);
	}
	
	public Result[] schedule(Operation op, String bankCode) throws ExecuteException, RemoteException {
		ObjectManager om = getObjectManagerbyBankCode(bankCode);
		
		if (om != null) {
			op.getAssociatedTransaction().addInvolvedOM(om);
			return om.executeOperation(op);
		} else {
			System.out.println("Unknown bank code!");
			throw new ExecuteException();
		}
	}
	
	private ObjectManager getObjectManagerbyBankCode(String bankCode) {
		if (bankCode.equals("vs12")) {
			return _om1;
		} else if (bankCode.equals("vs13")) {
			return _om2;
		} else {
			return null;
		}
	}
}
