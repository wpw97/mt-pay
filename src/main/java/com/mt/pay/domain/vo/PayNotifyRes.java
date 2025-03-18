package com.mt.pay.domain.vo;

import com.mt.pay.enums.PayNotifyState;
import lombok.Data;
/**
 * 微信支付结果返回对象
 *
 * @author wpw
 */
@Data
public class PayNotifyRes {
	private String orderNo;
	private PayNotifyState state;
	private String message;
	private String resParam;
}