package com.admin.exception;
/**
 * 校验异常
 */
public class ValidateException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidateException(String validateMessage) {
		super(validateMessage);
	}
}
