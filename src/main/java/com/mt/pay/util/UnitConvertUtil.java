package com.mt.pay.util;

/**
 * 单位转换工具
 * @author wpw
 * @date 2024-03-02 03:37
 */
public class UnitConvertUtil {

	/**
	 * 人名币 元转分
	 * @author wpw
	 * @date 2024/3/2 3:43
	 * @param yuan 单位 元
	 * @return int
	 */
	public static int yuanToCentsCNY(double yuan) {
		return (int) (yuan * 10 * 10);
	}
}
