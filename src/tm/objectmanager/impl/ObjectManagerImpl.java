package tm.objectmanager.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tm.objectmanager.ObjectManager;
import tm.server.model.Operation;
import tm.server.model.Result;
import tm.server.model.Transaction;
import tm.server.model.operation.*;
import tm.server.util.ConnectException;
import tm.server.util.ExecuteException;

import com.ibm.db2.jcc.DB2Driver;

public class ObjectManagerImpl implements ObjectManager {

	private final String USER = "tis2";
	private final String PASSWD = "tis211#";
	
	private Connection _con = null;
	private String _dbName = null;
	
	public ObjectManagerImpl(String dbName) throws ConnectException {
		try {
			DriverManager.registerDriver(new DB2Driver());
			
			System.out.println("Connecting to Database DB2 ...");
			
			_con = DriverManager.getConnection("jdbc:db2://" + dbName
					+ ".prakinf.tu-ilmenau.de:50000/Bank2", USER, PASSWD);
			
			if (_con != null) {
				System.out.println("Connection is ok!");
			} else {
				System.out.println("Connection is not ok!");
				System.exit(-1);
			}
		} catch (SQLException e) {
			System.out.println("Failed registering DB2 Driver");
			throw new ConnectException(e.getMessage());
		}

		_dbName = dbName;
		
		testDBMetaData();
	}

	private void testDBMetaData() {
		try {
			DatabaseMetaData metadata = _con.getMetaData();

			boolean customer = false;
			boolean account = false;

			ResultSet resultSet = metadata.getTables(null, null, "%", null);
			while (resultSet.next()) {
				if (resultSet.getString("TABLE_NAME").equals("KUNDEN")) {
					System.out.println("Found existing table: " + resultSet.getString("TABLE_NAME"));
					customer = true;
				}
				if (resultSet.getString("TABLE_NAME").equals("KONTEN")) {
					System.out.println("Found existing table: " + resultSet.getString("TABLE_NAME"));
					account = true;
				}
			}

			System.out.println("customer = " + customer);
			System.out.println("account = " + account);

			if (!customer) {
				System.out.println("Creating table for customers in given database...");
				Statement stmt = _con.createStatement();

				String sql = "CREATE TABLE KUNDEN "
						+ "(KundenNr INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 0 INCREMENT BY 1, NO CACHE), "
						+ " Vorname VARCHAR(255), "
						+ " Nachname VARCHAR(255), "
						+ " PRIMARY KEY ( KundenNr ))";

				stmt.executeUpdate(sql);
				System.out.println("Created table for customers in given database...");
			}

			if (!account) {
				System.out.println("Creating table for accounts in given database...");
				Statement stmt = _con.createStatement();

				String sql = "CREATE TABLE KONTEN ("
						+ " KontenNr INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 0 INCREMENT BY 1, NO CACHE), "
						+ " Kontostand FLOAT, "
						+ " KundenNr INTEGER not NULL, "
						+ " PRIMARY KEY ( KontenNr ), "
						+ " FOREIGN KEY ( KundenNr ) REFERENCES KUNDEN( KundenNr )"
						+ ")";

				stmt.executeUpdate(sql);
				System.out.println("Created table for accounts in given database...");
			}
		} catch (SQLException e) {
			System.out
					.println("Connection to DB or creation of tables failed.");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	public void deleteTables() {
		try {
			Statement stmt = _con.createStatement();

			stmt.executeUpdate("DROP TABLE KONTEN");
			stmt.executeUpdate("DROP TABLE KUNDEN");

			System.out.println("Droped our tables");

		} catch (SQLException e) {
			System.out.println("Droping tables failed.");
			System.out.println(e.getMessage());
		}
	}

	public Result[] executeOperation(Operation op) throws ExecuteException {
		try {
			PreparedStatement s;
			ResultSet set;
			
			switch (op.getType()) {
			case ALLACCOUNTSBYCUSTUMER:
				s = _con.prepareStatement("SELECT KontenNr FROM Konten WHERE KundenNr = ?");
				s.setInt(1, ((AllAccountsByCustumer) op).getCustumerID());
				set = s.executeQuery();
		
				Result[] r = new Result[set.getFetchSize()];
				int i = 0;
				while(set.next()) {
					Integer amount = set.getInt("KontenNr");
					r[i++] = new Result(String.valueOf(amount));
				}
				
				return r;
				
			case DELETE_ACCOUNT:
				s = _con.prepareStatement("DELETE FROM Konten WHERE KontenNr = ?");
				s.setInt(1, ((DeleteAccount) op).getAccountID());
				set = s.executeQuery();
				break;
				
			case DELETE_CUSTUMER:
				s = _con.prepareStatement("DELETE FROM Kunden WHERE KundenNr = ?");
				s.setInt(1, ((DeleteCustumer) op).getCustumerID());
				set = s.executeQuery();
				break;
				
			case INSERT_ACCOUNT:
				s = _con.prepareStatement("INSERT INTO KONTEN (Kontostand, KundenNr) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				s.setInt(1, 0);
				s.setInt(2, ((InsertAccount) op).getCustumerID());
				
				s.executeUpdate();
	
				// f�r automatisch generierten Key
				set = s.getGeneratedKeys();
				
				if (set.next()) {
					Integer accountID = set.getInt(1);
					Result[] result = new Result[1];
					result[0] = new Result(String.valueOf(accountID));
					return result;
				} else {
					throw new ExecuteException();
				}
				
			case INSERT_CUSTUMER:
				s = _con.prepareStatement("INSERT INTO KUNDEN (Vorname, Nachname) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				s.setString(1, ((InsertCustumer) op).getFirstName());
				s.setString(2, ((InsertCustumer) op).getLastName());

				s.executeUpdate();

				// f�r automatisch generierten Key
				set = s.getGeneratedKeys();
				
				if (set.next()) {
					Integer custumerID = set.getInt(1);
					Result[] result = new Result[1];
					result[0] = new Result(String.valueOf(custumerID));
					return result;
				} else {
					throw new ExecuteException();
				}
				
			case READ:
				s = _con.prepareStatement("SELECT Kontostand FROM Konten WHERE KontenNr = ?");
				s.setInt(1, ((Read) op).getAccountID());
				set = s.executeQuery();

				if (set.next()) {
					Float amount = set.getFloat("Kontostand");
					Result[] result = new Result[1];
					result[0] = new Result(String.valueOf(amount));
					return result;
				} else {
					throw new ExecuteException("The account id '" + ((Read) op).getAccountID() + "' doesn't exist.");
				}
				
			case WRITE:
				s = _con.prepareStatement("UPDATE Konten SET Kontostand = ? WHERE KontenNr = ?");
				s.setFloat(1, ((Write) op).getValue());
				s.setInt(2, ((Write) op).getAccountID());
				
				set = s.executeQuery();
				break;
				
			default:
				throw new ExecuteException("Operation unknown.");
				
			}
		} catch (SQLException e) {
			throw new ExecuteException("SQL query failed: " + e.getMessage());
		}
		
		return null;
	}
	
	
	public boolean checkCommit(Transaction t) throws RemoteException {
		// TODO synchronization protocol -> i.e. at this point we can be sure that we can commit
		// 2PCP: normally we would have to log our decision here, but its always true anyways ...
		return true; 
	}
	
	public void commit(Transaction t) throws RemoteException {
		// TODO log that we should commit
		// TODO now we can clear our log for this transaction
		return; // nothing to be done, as the operations already got executed correctly
	}
	public void rollback(Transaction t) throws RemoteException {
		// TODO here we need the log for the local OM in order to undo operations
		// TODO log the fact that we have to do a roll back as well! (in case we fail-stop-return while rolling back)
		return;
	}
	

	public void printTableEntries(String tableName) {
		System.out.println("Table " + tableName + " from " + _dbName);
		printTE(tableName);
		System.out.println("");
		System.out.println("");
	}

	private void printTE(String tableName) {
		// TODO use getTableEntries() here!
		try {
			Statement stmt = _con.createStatement();

			String query = "SELECT * FROM " + tableName;

			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			String header = "";
			for (int i = 1; i <= columnsNumber; ++i) {
				header += rsmd.getColumnName(i);
				if (i != columnsNumber) {
					header += " ";
				}
			}
			System.out.println(header);

			while (rs.next()) {
				String row = "";
				for (int i = 1; i <= columnsNumber; ++i) {
					row += rs.getString(i);
					if (i != columnsNumber) {
						row += "\t";
					}
				}
				System.out.println(row);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void dumpDBEntries(String filename) {
		ArrayList<String> customers = getTableEntries("KUNDEN");
		ArrayList<String> accounts = getTableEntries("KONTEN");

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "utf-8"));

			writer.write("------ " + _dbName + " ------\n\n");

			for (String line : customers) {
				writer.write(line);
				writer.write("\n");
			}

			writer.write("\n");

			for (String line : accounts) {
				writer.write(line);
				writer.write("\n");
			}

			writer.write("\n\n");

			writer.close();
		} catch (IOException ex) {
			System.out.println("Dumping DB entries to file failed!");
		}
	}

	private ArrayList<String> getTableEntries(String tableName) {
		ArrayList<String> tableEntries = new ArrayList<String>();
		try {

			Statement stmt = _con.createStatement();

			String query = "SELECT * FROM " + tableName;

			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			String header = "";
			for (int i = 1; i <= columnsNumber; ++i) {
				header += rsmd.getColumnName(i);
				if (i != columnsNumber) {
					header += " ";
				}
			}
			tableEntries.add(header);

			while (rs.next()) {
				String row = "";
				for (int i = 1; i <= columnsNumber; ++i) {
					row += rs.getString(i);
					if (i != columnsNumber) {
						row += "\t";
					}
				}
				tableEntries.add(row);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return tableEntries;
	}
}
