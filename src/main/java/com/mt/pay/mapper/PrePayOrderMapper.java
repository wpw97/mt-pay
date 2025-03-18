package com.mt.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.pay.domain.entity.PrePayOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预支付订单 mapper
 * @author wpw
 * @date 2024-03-01 10:04
 */
@Mapper
public interface PrePayOrderMapper extends BaseMapper<PrePayOrder> {
}
