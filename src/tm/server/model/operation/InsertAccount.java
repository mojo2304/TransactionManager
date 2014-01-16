package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;

public class InsertAccount extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1171881714298596204L;
	int _custumerID;
	
	public InsertAccount(Transaction associatedTransaction, int custumerID) {
		super(associatedTransaction);
		_operationType = OperationTypes.INSERT_ACCOUNT;
		_custumerID = custumerID;
	}
	
	public int getCustumerID() {
		return _custumerID;
	}
}
