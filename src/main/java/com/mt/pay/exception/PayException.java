package com.mt.pay.exception;

import lombok.NoArgsConstructor;

/**
 * 支付异常类
 * @author wpw
 * @date 2024-02-26 15:27
 */
@NoArgsConstructor
public class PayException extends RuntimeException {
	public PayException(String message) {
		super(message);
	}

	public PayException(Throwable cause) {
		super(cause);
	}

	public PayException(String message, Throwable cause) {
		super(message, cause);
	}

	public PayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
