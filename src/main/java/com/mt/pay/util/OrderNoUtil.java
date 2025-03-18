package com.mt.pay.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * 订单编号生成
 * @author wpw
 * @date 2024-09-30 21:37
 */
public class OrderNoUtil {

	public static String generateCode(String prefix) {
		return prefix + IdWorker.getIdStr();
	}
}
