package tm.server.model;

import java.io.Serializable;

public class Result implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -461080657359529495L;
	private String _result;
	
	public Result(String str) {
		_result = str;
	}
	
	public int getInt() throws NumberFormatException {
		return Integer.parseInt(_result);
	}
	
	public float getFloat() throws NumberFormatException {
		return Float.parseFloat(_result);
	}
	
	public String getString() {
		return _result;
	}
}
