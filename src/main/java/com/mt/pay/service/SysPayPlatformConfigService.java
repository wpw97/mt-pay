

package com.mt.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.pay.domain.entity.SysPayPlatformConfig;

/**
 * 支付配置表
 *
 * @author wpw
 * @date 2024-03-01 14:30:06
 */
public interface SysPayPlatformConfigService extends IService<SysPayPlatformConfig> {

	/**
	 * 初始化支付功能
	 * @author wpw
	 * @date 2024/3/2 4:02
	 * @return void
	 */
	int init();

}
