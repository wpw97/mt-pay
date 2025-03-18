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
import com.mt.pay.domain.entity.Order;
import com.mt.pay.service.OrderService;
import com.mt.pay.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 订单表
 *
 * @author wpw
 * @date 2024-03-04 10:24:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order" )
public class OrderController {

    private final OrderService orderService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param infOrder 订单表
     * @return
     */
    @GetMapping("/page" )
    public R getInfOrderPage(Page page, Order infOrder) {
        return R.ok(orderService.page(page, Wrappers.query(infOrder)));
    }


    /**
     * 通过id查询订单表
     * @param id id
     * @return R
     */
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(orderService.getById(id));
    }

    /**
     * 新增订单表
     * @param infOrder 订单表
     * @return R
     */
    @PostMapping
    public R save(@RequestBody Order infOrder) {
        return R.ok(orderService.save(infOrder));
    }

    /**
     * 修改订单表
     * @param infOrder 订单表
     * @return R
     */
    @PutMapping
    public R updateById(@RequestBody Order infOrder) {
        return R.ok(orderService.updateById(infOrder));
    }

    /**
     * 通过id删除订单表
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable String id) {
        return R.ok(orderService.removeById(id));
    }

}
