package com.mt.pay.domain.vo;

import lombok.Data;

/**
 * 创建预支付订单 响应参数
 * @author wpw
 * @date 2024-02-26 10:31
 */
@Data
public class PrePayOrderResVO {
	private String prepayId;
	private String codeUrl;
	private String timeStamp;
	private String nonceStr;
	private String signType;
	private String paySign;
}
