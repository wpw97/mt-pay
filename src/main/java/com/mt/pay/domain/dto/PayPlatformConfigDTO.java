package com.mt.pay.domain.dto;

import com.mt.pay.mybatis.base.BaseEntity;
import lombok.Data;


/**
 * 支付平台信息
 * @author wpw
 * @date 2024-02-26 17:09
 */
@Data
public class PayPlatformConfigDTO extends BaseEntity {
	private String id ;
	/** 支付平台 */
	private String payPlatform ;
	/** 支付平台编号（字典） */
	private String payPlatformCode ;
	/** 缓存key */
	private String cacheKey ;
	/** 类名 */
	private String className ;
	/** 是否启用（0-禁用，1-启用） */
	private Integer enable ;
	/** 0-正常，1-删除 */
	private String delFlag ;
}
