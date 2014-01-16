package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;

public class Read extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1645272757687214109L;
	int _accountID;
	
	public Read(Transaction associatedTransaction, int accountID) {
		super(associatedTransaction);
		_operationType = OperationTypes.READ;
		_accountID = accountID;
	}
	
	public int getAccountID() {
		return _accountID;
	}
}

