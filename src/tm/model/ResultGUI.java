package tm.model;

public class ResultGUI<T> {

	// our Result
	private T _result;
	
	// for Errors
	private boolean _success;
	private String _errorMessage;
	
	public ResultGUI(T r, boolean s, String e) {
		_result = r;
		_success = s;
		_errorMessage = e;
	}
	
	public boolean isSuccessful() {
		return _success;
	}
	
	public String getErrorMessage() {
		return _errorMessage;
	}
	
	public T getResult() {
		return _result;
	}
}
