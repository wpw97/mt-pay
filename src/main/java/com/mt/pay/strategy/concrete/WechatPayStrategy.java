package com.mt.pay.strategy.concrete;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mt.pay.config.properties.WechatPayProperty;
import com.mt.pay.domain.dto.PrePayOrderDTO;
import com.mt.pay.domain.vo.PayNotifyRes;
import com.mt.pay.domain.vo.PrePayOrderResVO;
import com.mt.pay.enums.PayNotifyState;
import com.mt.pay.exception.PayException;
import com.mt.pay.strategy.PayStrategy;
import com.mt.pay.util.AssertUtil;
import com.mt.pay.util.UnitConvertUtil;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信支付策略
 * @author wpw
 * @date 2024-02-26 10:39
 */
@Slf4j
public class WechatPayStrategy implements PayStrategy {

	@Override
	public PrePayOrderResVO prePayOrder(PrePayOrderDTO.PrePay dto) {
		AssertUtil.isTrue(ObjectUtil.isEmpty(dto.getPayType()), "请选择支付方式", PayException.class);
		// 获取RAS配置
		Config config = SpringUtil.getBean("rsaAutoCertificateConfig");
		AssertUtil.isTrue(ObjectUtil.isEmpty(config), "缺少微信支付RAS证书配置", PayException.class);
		WechatPayProperty property = SpringUtil.getBean(WechatPayProperty.class);
		PrePayOrderResVO vo = null;
		// 调用微信接口，创建微信预支付订单
		switch (dto.getPayType()) {
			case 1:
				vo = preApiPayOrder(dto, config, property);
				break;
			case 2:
				vo = preNativePayOrder(dto, config, property);
				break;
			default:
				throw new PayException("不支持支付方式：微信支付，支付方式" + dto.getPayType());
		}

		return vo;
	}


	/**
	 * 调用微信预支付接口 api方式
	 * @author wpw
	 * @date 2024/2/26 16:31
	 * @param dto 预支付参数
	 * @param config 证书RSA配置
	 * @param property 微信支付属性
	 * @return com.mt.pay.api.vo.PrePayOrderResVO  预支付id
	 */
	private PrePayOrderResVO preApiPayOrder(PrePayOrderDTO.PrePay dto, Config config, WechatPayProperty property) {
		JsapiService service = new JsapiService.Builder().config(config).build();
		PrepayRequest prepayRequest = new PrepayRequest();
		Amount amount = new Amount();
		// TODO 目前仅支持 金额单位分
		amount.setTotal(UnitConvertUtil.yuanToCentsCNY(dto.getAmount()));
		prepayRequest.setAmount(amount);
		prepayRequest.setAppid(property.getAppId());
		prepayRequest.setMchid(property.getMerchantId());
		prepayRequest.setDescription(dto.getDescription());
		prepayRequest.setNotifyUrl(property.getNotifyUrl() + "/" + dto.getPayPlatformCode() + "/" + dto.getId());
		prepayRequest.setOutTradeNo(dto.getOrderNo());
		PrepayResponse response = service.prepay(prepayRequest);
		PrePayOrderResVO vo = BeanUtil.copyProperties(response, PrePayOrderResVO.class);
		// 创建支付参数
		createParams(vo, config, prepayRequest);
		return vo;
	}

	/**
	 * 调用微信预支付接口 二维码方式
	 * @author wpw
	 * @date 2024/2/26 16:32
	 * @param config 证书RSA配置
	 * @return com.mt.pay.api.vo.PrePayOrderResVO  二维码链接
	 */
	private PrePayOrderResVO preNativePayOrder(PrePayOrderDTO.PrePay dto, Config config, WechatPayProperty property) {
		NativePayService service = new NativePayService.Builder().config(config).build();
		com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest prepayRequest = new com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest();
		// TODO 目前仅支持 金额单位分
		com.wechat.pay.java.service.payments.nativepay.model.Amount amount = new com.wechat.pay.java.service.payments.nativepay.model.Amount();
		amount.setTotal(UnitConvertUtil.yuanToCentsCNY(dto.getAmount()));
		prepayRequest.setAmount(amount);
		prepayRequest.setAppid(property.getAppId());
		prepayRequest.setMchid(property.getMerchantId());
		prepayRequest.setDescription(dto.getDescription());
		prepayRequest.setNotifyUrl(property.getNotifyUrl() + "/" + dto.getPayPlatformCode() + "/" + dto.getId());
		prepayRequest.setOutTradeNo(dto.getOrderNo());
		com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse response = service.prepay(prepayRequest);
		return BeanUtil.copyProperties(response, PrePayOrderResVO.class);
	}

	/**
	 * 构建小程序支付参数
	 * @author wpw
	 * @date 2024/9/30 21:16
	 * @param vo
	 * @param config
	 * @param prepayRequest
	 * @return void
	 */
	private void createParams(PrePayOrderResVO vo, Config config, PrepayRequest prepayRequest) {
		JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(config).build();
		// response包含了调起支付所需的所有参数，可直接用于前端调起支付
		PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(prepayRequest);
		BeanUtils.copyProperties(response, vo);
	}

	@Override
	public PayNotifyRes parserResBody() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String body = ServletUtil.getBody(request);
		//随机串
		String nonceStr = request.getHeader("Wechatpay-Nonce");
		//微信传递过来的签名
		String signature = request.getHeader("Wechatpay-Signature");
		//证书序列号（微信平台）
		String serialNo = request.getHeader("Wechatpay-Serial");
		//时间戳
		String timestamp = request.getHeader("Wechatpay-Timestamp");
		// 获取RAS配置
		RSAAutoCertificateConfig config = SpringUtil.getBean("rsaAutoCertificateConfig");

		// 回调验签参数未填充
		RequestParam requestParam = new RequestParam.Builder()
				.serialNumber(serialNo)
				.nonce(nonceStr)
				.signature(signature)
				.timestamp(timestamp)
				.body(body)
				.build();
		// 初始化 NotificationParser
		NotificationParser parser = new NotificationParser(config);
		Transaction transaction = new Transaction();
		try {
			// 以支付通知回调为例，验签、解密并转换成 Transaction
			transaction = parser.parse(requestParam, Transaction.class);
		} catch (ValidationException e) {
			// TODO 签名验证失败，返回 401 UNAUTHORIZED 状态码
			log.error("支付回调签名验证失败", e);
		}
		PayNotifyRes res = new PayNotifyRes();
		res.setState(PayNotifyState.WeChatPayNotifyState.getStateByName(transaction.getTradeState().name()));
		res.setMessage(transaction.getTradeStateDesc());
		res.setOrderNo(transaction.getOutTradeNo());
		return res;
	}


}
