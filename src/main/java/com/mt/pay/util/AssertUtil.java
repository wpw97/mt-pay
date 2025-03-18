package com.mt.pay.util;

import com.mt.pay.exception.PayException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * 断言工具类
 * @author wpw
 * @date 2023-07-14 19:40
 */
@Slf4j
public class AssertUtil<T extends Exception> {
/*
	private static Exception newException(String msg, Class<? extends Exception> e) {
		Exception exception = null;
		try {
			exception = e.getDeclaredConstructor(String.class).newInstance(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return exception;
	}*/

	/**
	 * 条件为true则抛出异常
	 * @author wpw
	 * @date 2023/7/14 20:13
	 * @param flag
	 * @param msg
	 * @return: void
	 */
	public static void isTrue(boolean flag, String msg) {
		if (flag) {
			throw new PayException(msg);
		}
	}

	/**
	 * 条件为true则抛出异常
	 * @author wpw
	 * @date 2023/7/14 20:13
	 * @param flag
	 * @param msg
	 * @param e 异常类
	 * @return: void
	 */
	@SneakyThrows
	public static void isTrue(boolean flag, String msg, Class<? extends Exception> e) {
		if (flag) {
			throw newException(msg, e);
		}
	}

	private static <E extends Exception> E newException(String msg, Class<E> eClass) throws Exception {
		// 获取异常类的构造函数，假设它有一个接受String为参数的构造函数
		Constructor<E> constructor = eClass.getConstructor(String.class);
		// 创建一个新的异常实例
		return constructor.newInstance(msg);
	}
}
