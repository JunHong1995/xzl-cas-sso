package com.xzl.common.exception;

/**
 *
 */

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1340328704145759576L;

	private String errorCode;
	private String errorMessage;
	
	public BusinessException() {
        super();
    }
    
    public BusinessException(String msg) {
        super(msg);
    }
    
    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public BusinessException(Throwable cause) {
        super(cause);
    }
    
	public BusinessException(String type, String msg){
		this.errorCode = type;
		this.errorMessage = msg;
	}



	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
