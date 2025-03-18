package com.mt.pay.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mt.pay.constants.CacheConstants;
import com.mt.pay.constants.CommonConstants;
import com.mt.pay.domain.entity.SysPayPlatformConfig;
import com.mt.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 支付 工厂
 * @author wpw
 * @date 2024-02-26 10:41
 */
@Slf4j
public class PayFactory {

	private static final Map<String, PayStrategy> STRATEGY_CACHE = new ConcurrentHashMap<>();

	public static int init() {
		RedisTemplate<String, SysPayPlatformConfig> redisTemplate = SpringUtil.getBean("redisTemplate");
		Set<String> keys = redisTemplate.keys(CacheConstants.PAY_PLATFORM_CONFIG_KEY.concat("*"));
		List<SysPayPlatformConfig> sysPayPlatformConfigList = redisTemplate.opsForValue().multiGet(keys);
		AtomicInteger success = new AtomicInteger(0);
		Optional.ofNullable(sysPayPlatformConfigList)
				.filter(CollUtil::isNotEmpty)
				.ifPresent(infos -> {
					infos.forEach(info -> {
						log.info("加载{}支付配置：{}", info.getPayPlatform(), info.getClassName());
						// 从缓存获取对象
						PayStrategy payStrategy = STRATEGY_CACHE.get(info.getPayPlatformCode());
						if (ObjectUtil.isEmpty(payStrategy)) {
							try {
								payStrategy = (PayStrategy) Class.forName(info.getClassName()).getDeclaredConstructor().newInstance();
								// 缓存
								STRATEGY_CACHE.put(info.getPayPlatformCode(), payStrategy);
							} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
									IllegalAccessException | InvocationTargetException e) {
								e.printStackTrace();
								log.warn("加载类：{}失败", info.getClassName());
							}
						}
						success.set(success.get() + 1);
					});
				});
		return success.get();
	}

	/**
	 * 根据key获取支付对象
	 * @author wpw
	 * @date 2024/2/26 17:59
	 * @param payPlatformCode 缓存key
	 * @return com.mt.pay.biz.strategy.PayStrategy
	 */
	public static PayStrategy instance(String payPlatformCode) {
		return STRATEGY_CACHE.get(payPlatformCode);
	}

	/**
	 * 刷新支付类
	 * @author wpw
	 * @date 2024/3/2 4:50
	 * @param payPlatformCode 支付平台编号
	 * @return void
	 */
	public static void refresh(String payPlatformCode) {
		RedisTemplate<String, SysPayPlatformConfig> redisTemplate = SpringUtil.getBean("redisTemplate");
		SysPayPlatformConfig sysPayPlatformConfig = redisTemplate.opsForValue().get(CacheConstants.PAY_PLATFORM_CONFIG_KEY + payPlatformCode);

		Optional.ofNullable(sysPayPlatformConfig)
				.ifPresent(config -> {
					log.info("刷新{} 支付类：{}", config.getPayPlatform(), config.getClassName());
					if (CommonConstants.YES == Integer.parseInt(config.getEnable())) {
						PayStrategy payStrategy = null;
						try {
							payStrategy = (PayStrategy) Class.forName(config.getClassName()).getDeclaredConstructor().newInstance();
						} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
							e.printStackTrace();
							log.warn("加载类{}失败, \n异常堆栈信息：{}", config.getClassName(), e.getMessage());
						}
						STRATEGY_CACHE.put(sysPayPlatformConfig.getPayPlatformCode(), payStrategy);
					} else {
						log.info("移除{}支付类：{}", config.getPayPlatform(), config.getClassName());
						STRATEGY_CACHE.remove(config.getPayPlatformCode());
					}

				});
	}

}
