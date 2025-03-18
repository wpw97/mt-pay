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

package com.mt.pay.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mt.pay.domain.dto.PrePayOrderDTO;
import com.mt.pay.domain.entity.PrePayOrder;
import com.mt.pay.service.PrePayOrderService;
import com.mt.pay.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


/**
 * 预支付订单
 *
 * @author wpw
 * @date 2024-03-01 11:42:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/prePayOrder")
public class PrePayOrderController {

    private final PrePayOrderService prePayOrderService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param prePayOrder 预支付订单
     * @return
     */
    @GetMapping("/page" )
    public R getInfPrePayOrderPage(Page page, PrePayOrder prePayOrder) {
        return R.ok(prePayOrderService.page(page, Wrappers.query(prePayOrder)));
    }


    /**
     * 通过id查询预支付订单
     * @param id id
     * @return R
     */
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(prePayOrderService.getById(id));
    }

    /**
     * 新增预支付订单
     * @param prePayOrder 预支付订单
     * @return R
     */
    @PostMapping
    public R save(@RequestBody PrePayOrder prePayOrder) {
        return R.ok(prePayOrderService.save(prePayOrder));
    }

    /**
     * 修改预支付订单
     * @param prePayOrder 预支付订单
     * @return R
     */
    @PutMapping
    public R updateById(@RequestBody PrePayOrder prePayOrder) {
        return R.ok(prePayOrderService.updateById(prePayOrder));
    }

    /**
     * 通过id删除预支付订单
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable String id) {
        return R.ok(prePayOrderService.removeById(id));
    }

	/**
	 * 创建预支付订单
	 * @author wpw
	 * @date 2024/3/20 16:14
	 * @param dto 预支付订单信息
	 * @return com.writeguide.common.core.util.R<java.lang.String>
	 */
	@PostMapping("/createPrePayOrder")
	public R<String> createPrePayOrder(@RequestBody PrePayOrderDTO dto) {
		return R.ok(prePayOrderService.createPrePayOrder(dto));
	}

}
