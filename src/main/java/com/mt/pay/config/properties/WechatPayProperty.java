package com.mt.pay.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付 配置参数
 * @author wpw
 * @date 2024-02-26 15:47
 */
@Data
@Component
@ConfigurationProperties(prefix = "pay.wechat")
public class WechatPayProperty {
	/** 商户号 */
	private String merchantId;
	/** 商户API私钥路径 */
	private String privateKeyPath;
	/** 商户证书序列号 */
	private String merchantSerialNumber;
	/** 商户API V3密钥 */
	private String apiV3Key;
	/** 小程序id */
	private String appId;
	/** 异步通知接口 */
	private String notifyUrl;
}
