package tm.server.impl.test;

import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tm.client.impl.ClientImpl;
import tm.objectmanager.OM;
import tm.objectmanager.ObjectManager;
import tm.server.Server;

public class BankImplTest {

	Registry _registry;
	
	Server _s;
	ClientImpl _c;

	OM _om1;
	OM _om2;
	
	@Before
	public void setUp() throws Exception {
		_registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRMI() {
		try {
			_om1 = new OM(ObjectManager.FIRSTOM, "vs12");
			_om1.start();

			_om2 = new OM(ObjectManager.SECONDOM, "vs13");
			_om2.start();
		} catch(Exception e) {
			System.out.println("Could not set up RMI on object manager side with reason:");
			System.out.println(e.getMessage());
			fail();
		}
		
		try {
			_s = new Server();
			_s.start();
		} catch(Exception e) {
			System.out.println("Could not set up RMI on server side with reason:");
			System.out.println(e.getMessage());
			fail();
		}
		
		try {
			_c = new ClientImpl();
			_c.start();
		} catch(Exception e) {
			System.out.println("Could not set up RMI on client side with reason:");
			System.out.println(e.getMessage());
			fail();
		}

		try {
			int[] custumer = _c.getBankAccess().createNewCustomer("L", "B", "vs12");
			_c.getBankAccess().createNewAccount(custumer[0], "vs12");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test createNewCustomer failed. " + e.getMessage());
		}
		
		try {
			_om1.getObjectManager().printTableEntries("Kunden");
			_om1.getObjectManager().printTableEntries("Konten");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
