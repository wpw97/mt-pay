

package com.mt.pay.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mt.pay.constants.CacheConstants;
import com.mt.pay.domain.entity.SysPayPlatformConfig;
import com.mt.pay.service.SysPayPlatformConfigService;
import com.mt.pay.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


/**
 * 支付配置表
 *
 * @author wpw
 * @date 2024-03-01 14:30:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysPayPlatformConfig" )
public class SysPayPlatformConfigController {

    private final SysPayPlatformConfigService sysPayPlatformConfigService;
	private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysPayPlatformConfig 支付配置表
     * @return
     */
    @GetMapping("/page" )
    public R getSysPayPlatformConfigPage(Page page, SysPayPlatformConfig sysPayPlatformConfig) {
        return R.ok(sysPayPlatformConfigService.page(page, Wrappers.query(sysPayPlatformConfig)));
    }


    /**
     * 通过id查询支付配置表
     * @param id id
     * @return R
     */
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(sysPayPlatformConfigService.getById(id));
    }

    /**
     * 新增支付配置表
     * @param sysPayPlatformConfig 支付配置表
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysPayPlatformConfig sysPayPlatformConfig) {
        return R.ok(sysPayPlatformConfigService.save(sysPayPlatformConfig));
    }

    /**
     * 修改支付配置表
     * @param sysPayPlatformConfig 支付配置表
     * @return R
     */
    @PutMapping
    public R updateById(@RequestBody SysPayPlatformConfig sysPayPlatformConfig) {
		if (StrUtil.isEmpty(sysPayPlatformConfig.getId())) {
			return R.failed("id为空");
		}
		if (sysPayPlatformConfigService.updateById(sysPayPlatformConfig)) {
			redisTemplate.opsForValue()
					.set(CacheConstants.PAY_PLATFORM_CONFIG_KEY + sysPayPlatformConfig.getPayPlatformCode(),
							sysPayPlatformConfigService.getById(sysPayPlatformConfig.getId()));
		}
        return R.ok();
    }

    /**
     * 通过id删除支付配置表
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable String id) {
        return R.ok(sysPayPlatformConfigService.removeById(id));
    }

}
