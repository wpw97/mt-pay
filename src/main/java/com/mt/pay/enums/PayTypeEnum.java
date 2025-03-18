package com.mt.pay.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 微信支付方式
 * @author wpw
 * @date 2024-02-26 14:37
 */
@Getter
@RequiredArgsConstructor
public enum PayTypeEnum {
	/**  */
	JSAPI("JSAPI", 1, "api方式"),
	NATIVE("native", 2, "二维码方式");

	private final String name;
	private final int type;
	private final String desc;

}
