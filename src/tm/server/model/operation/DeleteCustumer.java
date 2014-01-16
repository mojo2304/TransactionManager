package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;

public class DeleteCustumer extends Operation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2185314091002138084L;
	int _custumerID;
	
	public DeleteCustumer(Transaction associatedTransaction, int custumerID) {
		super(associatedTransaction);
		_operationType = OperationTypes.DELETE_CUSTUMER;
		_custumerID = custumerID;
	}
	
	public int getCustumerID() {
		return _custumerID;
	}
}

