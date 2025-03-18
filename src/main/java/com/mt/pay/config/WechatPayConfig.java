package com.mt.pay.config;

import com.mt.pay.config.properties.WechatPayProperty;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 微信支付配置类
 * @author wpw
 * @date 2024-02-26 14:14
 */
@Data
@DependsOn(value = "wechatPayProperty")
@Configuration
public class WechatPayConfig {
	@Autowired
	private WechatPayProperty property;

	@Bean("rsaAutoCertificateConfig")
	public RSAAutoCertificateConfig rsaAutoCertificateConfig() {
		return new RSAAutoCertificateConfig.Builder()
				.merchantId(property.getMerchantId())
				.privateKeyFromPath(property.getPrivateKeyPath())
				.merchantSerialNumber(property.getMerchantSerialNumber())
				.apiV3Key(property.getApiV3Key())
				.build();
	}
}
