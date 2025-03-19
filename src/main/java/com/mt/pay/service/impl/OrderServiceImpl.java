
package com.mt.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.pay.domain.entity.Order;
import com.mt.pay.mapper.OrderMapper;
import com.mt.pay.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单表
 *
 * @author wpw
 * @date 2024-03-04 10:24:39
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
