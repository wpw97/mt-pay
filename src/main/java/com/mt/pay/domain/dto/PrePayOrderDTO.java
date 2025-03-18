package com.mt.pay.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 预订单信息
 * @author wpw
 * @date 2024-02-26 10:33
 */
@Data
public class PrePayOrderDTO {
	@NotBlank(message = "id为空")
	private String id ;
	/** 订单号 */
	private String orderNo ;
	/** 订单名称 */
	private String orderName ;
	/** 商品类型;字典 */
	private Integer commodityType ;
	/** 商品描述 */
	private String description ;
	/** 支付金额 */
	@NotBlank(message = "金额不能为空")
	private Double amount ;
	/** 金额单位 */
	private String amountUnit;
	/** 支付货币 */
	private String currency ;
	/** 商户号 */
	private String merchantId ;
	/** 订单失效时间 */
	private Date timeExpire ;
	/** 支付平台 */
	private String payPlatform ;
	/** 支付平台编号 */
	private String payPlatformCode ;
	/** 支付方式;1-api，2-二维码 */
	private Integer payType;
	/** 支付时间 */
	private Date payTime ;
	/** 支付者 */
	private String payer ;
	/** 支付者openid */
	private String payerOpenid ;
	/** 支付者系统内部id */
	private String payerId ;
	/** 微信预支付id */
	private String prepayId ;
	/** 支付二维码url */
	private String codeUrl ;
	/** 支付状态;字典 */
	private Integer status ;
	/** 0-正常，1-删除 */
	private String delFlag ;

	@Data
	public static class PrePay {
		@NotBlank(message = "id为空")
		private String id ;
		@NotBlank(message = "请选择支付平台")
		private String payPlatformCode ;
		private String payerOpenid ;
		@NotBlank(message = "请选择支付方式")
		private Integer payType;
		@NotBlank(message = "金额不能为空")
		private Double amount ;
		private String description ;
		private String orderNo ;
	}
}
