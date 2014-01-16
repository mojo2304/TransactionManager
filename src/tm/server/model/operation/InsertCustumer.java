package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;

public class InsertCustumer extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5978762186754738878L;
	String _firstName;
	String _lastName;
	
	public InsertCustumer(Transaction associatedTransaction, String firstName, String lastName) {
		super(associatedTransaction);
		_operationType = OperationTypes.INSERT_CUSTUMER;
		_firstName = firstName;
		_lastName = lastName;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public String getLastName() {
		return _lastName;
	}
}
