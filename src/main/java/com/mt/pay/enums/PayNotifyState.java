package com.mt.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付回调状态
 * @author wpw
 * @date 2024-10-17 22:01
 */
@Getter
public enum PayNotifyState {
	/**/
	SUCCESS, FAIL;

	@Getter
	@AllArgsConstructor
	public enum WeChatPayNotifyState {
		/**/
		SUCCESS(PayNotifyState.SUCCESS);

		private final PayNotifyState state;

		public static PayNotifyState getStateByName(String name) {
			for (WeChatPayNotifyState value : WeChatPayNotifyState.values()) {
				if (value.name().equals(name)) {
					return value.getState();
				}
			}
			return null;
		}
	}
}
