package tm.objectmanager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import tm.objectmanager.impl.ObjectManagerImpl;

public class OM extends Thread {

	private boolean _work;
	private ObjectManager _om;
	
	public OM(String objectManagerName, String dbName) throws Exception {
		_work = true;
		
//		// the security manager is necessary for RMI
//		if (System.getSecurityManager() == null) {
//		    System.setSecurityManager(new SecurityManager());
//		}
		
		// now setting up RMI: first, create object and make it remote
		_om = new ObjectManagerImpl(dbName);
		ObjectManager skeleton = (ObjectManager) UnicastRemoteObject.exportObject(_om, Registry.REGISTRY_PORT);

		// second, get registry and publish the remote object
		LocateRegistry.getRegistry().rebind(objectManagerName, skeleton);
	}
	
	public void run() {
		System.out.println("ObjectManager thread started.");
		
		while (_work) {
			try {
	        	sleep(500);
	        } catch(InterruptedException e) {
	        	_work = false;
	        }
		}
		
		System.out.println("ObjectManager thread ended.");
	}
	
	public void stopThread() {
		_work = false;
	}
	
	public ObjectManager getObjectManager() {
		return _om;
	}
}
