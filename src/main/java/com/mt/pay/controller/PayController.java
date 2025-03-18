package com.mt.pay.controller;

import com.mt.pay.domain.dto.PrePayOrderDTO;
import com.mt.pay.domain.vo.PrePayOrderResVO;
import com.mt.pay.exception.PayException;
import com.mt.pay.factory.PayFactory;
import com.mt.pay.service.PrePayOrderService;
import com.mt.pay.strategy.PayStrategy;
import com.mt.pay.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * 支付管理
 * @author wpw
 * @date 2024-03-01 14:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PayController {

	private final PrePayOrderService prePayOrderService;

	/**
	 * 支付平台预支付接口调用
	 * @author wpw
	 * @date 2024/3/2 2:18
	 * @param dto 预支付信息
	 * @return com.mt.pay.util.R<com.mt.pay.vo.PrePayOrderResVO>
	 */
	@PostMapping("/prePay")
	@Transactional(rollbackFor = PayException.class)
	public R<PrePayOrderResVO> pay(@RequestBody PrePayOrderDTO.PrePay dto) {
		PayStrategy payStrategy = PayFactory.instance(dto.getPayPlatformCode());
		// 平台预支付调用
		PrePayOrderResVO vo = payStrategy.prePayOrder(dto);
		// 系统预支付订单更新
		prePayOrderService.prePay(vo, dto);
		return R.ok(vo);
	}

	/**
	 * 支付回调
	 * @author wpw
	 * @date 2024/3/4 11:28
	 * @param prePayOrderId 预支付订单id
	 * @return void
	 */
	@Transactional(rollbackFor = PayException.class)
	@PostMapping("/notify/{payPlatformCode}/{prePayOrderId}")
	public void payNotify(@PathVariable("payPlatformCode") String payPlatformCode,
						  @PathVariable("prePayOrderId") String prePayOrderId) {
		log.info("支付回调：{}", prePayOrderId);
		prePayOrderService.updateOrderByNotify(payPlatformCode, prePayOrderId);

	}
}
