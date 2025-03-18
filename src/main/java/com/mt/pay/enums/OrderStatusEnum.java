package com.mt.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 * @author wpw
 * @date 2024-03-01 10:44
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
	/**  */
	CREATE_ORDER("创建平台订单", 1),
	CREATE_WECAHT_PRE_PAY_ORDER("创建微信预支付订单", 2),
	PAID("已支付", 3);


	private final String desc;
	private final int status;
}
