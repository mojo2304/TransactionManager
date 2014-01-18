package tm.server.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import tm.objectmanager.ObjectManager;
import tm.server.impl.Log;

public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6773977459219839983L;
	private int _transactionId;
	private TransactionStates _state;
	private Log _log;

	private ArrayList<ObjectManager> _involvedOMs;

	public Transaction(int id, Log log) {
		_transactionId = id;
		_state = TransactionStates.READY;
		_log = log;
	}

	public void begin() {
		_state = TransactionStates.WORKING;
	}

	public void commit() {
		// TODO log that we started the 2 Phase Commit Protocol (prepare phase)
		boolean canCommit = true;
		for (ObjectManager om : _involvedOMs) {
			try {
				boolean canCommitLocal = om.checkCommit(this);
				if (!canCommitLocal) {
					canCommit = false;
				}
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
				canCommit = false;
			}
		}
		// TODO log decision (canCommit)

		for (ObjectManager om : _involvedOMs) {
			try {
				if (canCommit) {
					om.commit(this);
				} else {
					om.rollback(this);
				}

			} catch (RemoteException e) {
				System.out.print(e.getMessage());
				// TODO 2PCP says: try till it works ...
			}
		}

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

	public void addInvolvedOM(ObjectManager om) {
		_involvedOMs.add(om);
	}
}
