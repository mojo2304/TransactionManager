package tm.server.impl.test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tm.client.Client;
import tm.objectmanger.OM;
import tm.objectmanger.ObjectManager;
import tm.server.Bank;
import tm.server.Server;

public class BankImplTest {

	Registry _registry;
	
	Server _s;
	Client _c;

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
			_c = new Client("127.0.0.1", Bank.FIRSTBANKNAME);
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
	
	/*
	@Test
	public void testOtherOperations() {
		try {
			Registry registry = LocateRegistry.getRegistry();
			
			Bank bank = (Bank) registry.lookup("Bank");
			
			bank.deposit(0, (float)1024, "vs12");
			
			bank.createNewAccount(0, "vs12");
			bank.createNewAccount(0, "vs12");
			
			bank.remittance(1, "vs12", 0, "vs12", (float)512);
			bank.remittance(2, "vs12", 1, "vs12", (float)256);
			bank.remittance(3, "vs12", 2, "vs12", (float)128);
			
			bank.remittance(0, "vs13", 3, "vs12", (float)64);
			
			bank.remittance(1, "vs13", 0, "vs13", (float)32);
			bank.remittance(2, "vs13", 1, "vs13", (float)16);
			bank.remittance(3, "vs13", 2, "vs13", (float)8);
			
			bank.deleteCustomer(0, "vs12");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test otherOperations failed. " + e.getMessage());
		}
	}

	@Test
	public void otherOperations() {
		bank.cashout(aid, amount, strs[3]);
		bank.transfer(destAID, srcAID, strs[3], amount);
	}
	*/
}
