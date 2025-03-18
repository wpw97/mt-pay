package com.mt.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.pay.domain.dto.PrePayOrderDTO;
import com.mt.pay.domain.entity.PrePayOrder;
import com.mt.pay.domain.vo.PrePayOrderResVO;

/**
 * 预支付订单 service
 * @author wpw
 * @date 2024-03-01 10:06
 */
public interface PrePayOrderService extends IService<PrePayOrder> {
	/**
	 * 检查是否存在，并校验状态是否已支付
	 * @author wpw
	 * @date 2024/3/4 11:49
	 * @param prePayOrderId 预支付订单id
	 * @return com.mt.pay.api.entity.PrePayOrder
	 */
    PrePayOrder checkExistAndPaid(String prePayOrderId);

	/**
	 * 创建预支付订单
	 *
	 * @author wpw
	 * @date 2024/3/20 16:15
	 * @param dto 预支付订单信息
	 * @return java.lang.String
	 */
    String createPrePayOrder(PrePayOrderDTO dto);

	/**
	 * 平台预支付成功后，更新订单信息
	 * @author wpw
	 * @date 2024/9/30 20:01
	 * @param vo
	 * @param dto
	 * @return void
	 */
	void prePay(PrePayOrderResVO vo, PrePayOrderDTO.PrePay dto);

	/**
	 * 平台支付成功后通知更新
	 * @author wpw
	 * @date 2024/10/13 13:14
	 * @param prePayOrderId    系统预支付id
	 * @param payPlatformCode  支付平台编号
	 * @param body             通知body
	 * @return void
	 */
	void updateOrderByNotify(String payPlatformCode, String prePayOrderId);
}
