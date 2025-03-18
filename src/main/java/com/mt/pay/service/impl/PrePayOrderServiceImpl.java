package com.mt.pay.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.pay.domain.dto.PrePayOrderDTO;
import com.mt.pay.domain.entity.PrePayOrder;
import com.mt.pay.domain.vo.PayNotifyRes;
import com.mt.pay.domain.vo.PrePayOrderResVO;
import com.mt.pay.enums.OrderStatusEnum;
import com.mt.pay.factory.PayFactory;
import com.mt.pay.mapper.PrePayOrderMapper;
import com.mt.pay.service.PrePayOrderService;
import com.mt.pay.strategy.PayStrategy;
import com.mt.pay.util.OrderNoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 预支付订单 service实现类
 * @author wpw
 * @date 2024-03-01 10:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrePayOrderServiceImpl extends ServiceImpl<PrePayOrderMapper, PrePayOrder> implements PrePayOrderService {
//	private final RemoteThirdPartyUserService remoteThirdPartyUserService;
	private String CODE_PREFIX = "prepay";

	@Override
	public PrePayOrder checkExistAndPaid(String prePayOrderId) {
		PrePayOrder exist = this.baseMapper.selectById(prePayOrderId);
		if (ObjectUtil.isEmpty(exist)) {
			log.error("【订单须核查】预订单不存在，id为{}", prePayOrderId);
			return null;
		}
		if (ObjectUtil.isEmpty(exist.getPayTime()) && exist.getStatus().equals(OrderStatusEnum.PAID.getStatus())) {
			log.info("订单已支付{}, id为", exist.getId());
			return null;
		}
		return exist;
	}

	@Override
	public String createPrePayOrder(PrePayOrderDTO dto) {
		// TODO 制定规则，生成预支付订单编号
		String prePayOrderCode = OrderNoUtil.generateCode(CODE_PREFIX);
		PrePayOrder prePayOrder = BeanUtil.copyProperties(dto, PrePayOrder.class);
		prePayOrder.setOrderNo(prePayOrderCode);
		save(prePayOrder);
		return prePayOrderCode;
	}

	@Override
	public void prePay(PrePayOrderResVO vo, PrePayOrderDTO.PrePay dto) {
		String userName = "test";
		String userId = "test";
		// 获取支付用户信息
//		String userName = SecurityUtils.getUser().getName();
//		String userId = SecurityUtils.getUser().getId();
		AtomicReference<String> openId = new AtomicReference<>(dto.getPayerOpenid());
//		if (StrUtil.isEmpty(dto.getPayerOpenid())) {
//			R<ThirdPartyUser> thirdPartyUserRes = remoteThirdPartyUserService.getById(userId, SecurityConstants.FROM_IN);
//			Optional.ofNullable(thirdPartyUserRes.getData())
//					.ifPresent(thirdPartyUser -> openId.set(thirdPartyUser.getOpenid()));
//		}
		PrePayOrder order = new PrePayOrder();
		order.setId(dto.getId());
		order.setStatus(OrderStatusEnum.CREATE_WECAHT_PRE_PAY_ORDER.getStatus());
		order.setPayType(dto.getPayType());
		order.setPayer(userName);
		order.setPayerId(userId);
		order.setPayerOpenid(openId.get());
		if (StrUtil.isNotBlank(vo.getPrepayId())) {
			order.setPayerId(vo.getPrepayId());
		} else {
			order.setCodeUrl(vo.getCodeUrl());
		}
		// 更新预支付平台订单
		updateById(order);
	}

	@Override
	public void updateOrderByNotify(String payPlatformCode, String prePayOrderId) {

		PayStrategy payStrategy = PayFactory.instance(payPlatformCode);
		PayNotifyRes payNotifyRes = payStrategy.parserResBody();

		PrePayOrder payOrder = getOne(new LambdaQueryWrapper<PrePayOrder>().eq(PrePayOrder::getOrderNo, payNotifyRes.getOrderNo()));


	}
}
