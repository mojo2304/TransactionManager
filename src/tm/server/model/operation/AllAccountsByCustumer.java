package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;

public class AllAccountsByCustumer extends Operation {

	private static final long serialVersionUID = -1636739516622772510L;
	int _custumerID;
	
	public AllAccountsByCustumer(Transaction associatedTransaction, int custumerID) {
		super(associatedTransaction);
		_operationType = OperationTypes.ALLACCOUNTSBYCUSTUMER;
		_custumerID = custumerID;
	}
	
	public int getCustumerID() {
		return _custumerID;
	}
}
