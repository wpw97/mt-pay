/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.mt.pay.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.pay.constants.CacheConstants;
import com.mt.pay.constants.CommonConstants;
import com.mt.pay.domain.entity.SysPayPlatformConfig;
import com.mt.pay.factory.PayFactory;
import com.mt.pay.mapper.SysPayPlatformConfigMapper;
import com.mt.pay.service.SysPayPlatformConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 支付配置表
 *
 * @author wpw
 * @date 2024-03-01 14:30:06
 */
@Service
@RequiredArgsConstructor
public class SysPayPlatformConfigServiceImpl extends ServiceImpl<SysPayPlatformConfigMapper, SysPayPlatformConfig> implements SysPayPlatformConfigService {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public int init() {
		List<SysPayPlatformConfig> sysPayPlatformConfigList = this.baseMapper
				.selectList(new LambdaQueryWrapper<SysPayPlatformConfig>()
						.eq(SysPayPlatformConfig::getEnable, CommonConstants.YES));
		AtomicInteger success = new AtomicInteger();
		Optional.ofNullable(sysPayPlatformConfigList)
				.filter(CollUtil::isNotEmpty)
				.ifPresent(list -> {
					list.forEach(config -> redisTemplate.opsForValue().set(CacheConstants.PAY_PLATFORM_CONFIG_KEY + config.getPayPlatformCode(), config));
					success.set(PayFactory.init());
				});
		return success.get();
	}
}
