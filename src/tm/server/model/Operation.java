package tm.server.model;

import java.io.Serializable;


public class Operation implements Serializable {

	private static final long serialVersionUID = -6235152725685192440L;
	
	private Transaction _associatedTransaction;
	protected OperationTypes _operationType;
	
	public Operation(Transaction t) {
		_associatedTransaction = t;
		_operationType = OperationTypes.UNKNOWN;
	}
	
	public Transaction getAssociatedTransaction() {
		return _associatedTransaction;
	}
	
	public OperationTypes getType() {
		return _operationType;
	}
}
