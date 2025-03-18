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
package com.mt.pay.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mt.pay.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付配置表
 *
 * @author wpw
 * @date 2024-03-01 14:30:06
 */
@Data
@TableName("sys_pay_platform_config")
@EqualsAndHashCode(callSuper = true)
public class SysPayPlatformConfig extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 支付平台
     */
    private String payPlatform;

    /**
     * 支付平台编号（字典）
     */
    private String payPlatformCode;

    /**
     * 缓存key
     */
    private String cacheKey;

    /**
     * 类名
     */
    private String className;

    /**
     * 是否启用（0-禁用，1-启用）
     */
    private String enable;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
