package tm.server.model.operation;

import tm.server.model.Operation;
import tm.server.model.OperationTypes;
import tm.server.model.Transaction;


public class Write extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6523764286590685289L;
	int _accountID;
	float _value;
	
	/**
	 * @param db (i.e. "vs12" or "vs13")
	 * @param objectId (i.e. customer ID or account ID)
	 */
	public Write(Transaction associatedTransaction, int accountID, float value) {
		super(associatedTransaction);
		_operationType = OperationTypes.WRITE;
		_accountID = accountID;
		_value = value;
	}
	
	public int getAccountID() {
		return _accountID;
	}
	
	public float getValue() {
		return _value;
	}
}
