package com.mt.pay.runner;

import com.mt.pay.service.SysPayPlatformConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化系统配置
 * @author wpw
 * @date 2024-03-02 04:32
 */
@Slf4j
@AllArgsConstructor
@Component
public class SystemApplicationRunner implements ApplicationRunner {

	private final SysPayPlatformConfigService sysPayPlatformConfigService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		int success = sysPayPlatformConfigService.init();
		if (success > 0) {
			log.info("加载支付功能成功");
		} else {
			log.debug("加载支付功能失败");
		}
	}
}
