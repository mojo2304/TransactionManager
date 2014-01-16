package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;

public class DeleteAccount extends Operation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8298729030945445184L;
	int _accountID;
	
	public DeleteAccount(Transaction associatedTransaction, int accountID) {
		super(associatedTransaction);
		_operationType = OperationTypes.DELETE_ACCOUNT;
		_accountID = accountID;
	}
	
	public int getAccountID() {
		return _accountID;
	}
}

