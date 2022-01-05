package com.bshp.common.vo;

public class ResponseVo<T> {	

	private String resultCode;
	
	private String resultMessage;
	
	private T data;
	
	public ResponseVo() {}
	
	public ResponseVo(String resultCode, String resultMessage, T data) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.data = data;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
