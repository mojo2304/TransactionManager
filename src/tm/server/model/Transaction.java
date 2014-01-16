package tm.server.model;

import java.io.Serializable;

import tm.server.impl.Log;


public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6773977459219839983L;
	private int _transactionId;
	private TransactionStates _state;
	private Log _log;
	
	public Transaction(int id, Log log) {
		_transactionId = id;
		_state = TransactionStates.READY;
		_log = log;
	}
	
	public void begin() {
		_state = TransactionStates.WORKING;
	}
	
	public void commit() {
		_state = TransactionStates.FINISH;
	}
	
	public void abort() {
		_state = TransactionStates.ABBORTED;
	}
	
	public TransactionStates getTransactionState() {
		return _state;
	}
	
	public int getTransactionId() {
		return _transactionId;
	}
}
