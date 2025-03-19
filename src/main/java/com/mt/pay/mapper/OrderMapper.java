

package com.mt.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.pay.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表
 *
 * @author wpw
 * @date 2024-03-04 10:24:39
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
