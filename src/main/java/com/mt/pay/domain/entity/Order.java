
package com.mt.pay.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mt.pay.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单表
 *
 * @author wpw
 * @date 2024-03-04 10:24:39
 */
@Data
@TableName("inf_order")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

	/**
	 * 预支付订单id
	 */
	private String prePayOrderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 商品类型;字典
     */
    private Integer commodityType;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 支付金额
     */
    private Double amount;

    /**
     * 金额单位
     */
    private String amountUnit;

    /**
     * 支付货币
     */
    private String currency;

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 支付平台
     */
    private String payPlatform;

    /**
     * 支付平台编号
     */
    private String payPlatformCode;

    /**
     * 支付方式;1-api，2-二维码
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付者
     */
    private String payer;

    /**
     * 支付者openid
     */
    private String payerOpenid;

    /**
     * 支付者系统内部id
     */
    private String payerId;

    /**
     * 支付二维码url
     */
    private String codeUrl;

    /**
     * 支付状态;字典
     */
    private Integer status;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
