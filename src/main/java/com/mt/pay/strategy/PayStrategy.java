package com.mt.pay.strategy;


import com.mt.pay.domain.dto.PrePayOrderDTO;
import com.mt.pay.domain.vo.PayNotifyRes;
import com.mt.pay.domain.vo.PrePayOrderResVO;

/**
 * 支付策略
 * @author wpw
 * @date 2024-02-26 10:29
 */
public interface PayStrategy {

	/**
	 * 调用第三方 创建预订单
	 * @author wpw
	 * @date 2024/2/26 10:34
	 * @param dto 订单信息
	 * @return com.mt.pay.api.vo.CreateOrderResVO
	 */
	PrePayOrderResVO prePayOrder(PrePayOrderDTO.PrePay dto);

	/**
	 * 支付回调参数验签
	 * @author wpw
	 * @date 2024/10/14 22:51
	 * @param
	 * @return com.wechat.pay.java.service.payments.model.Transaction
	 */
	PayNotifyRes parserResBody();
}
