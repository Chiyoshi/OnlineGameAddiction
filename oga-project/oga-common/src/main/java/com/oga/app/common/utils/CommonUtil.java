package com.oga.app.common.utils;

import com.oga.app.common.prop.OgaProperty;

public class CommonUtil {

	/**
	 * 待機時間
	 */
	public static void sleep() {
		int waitTime = Integer.parseInt(OgaProperty.getProperty("webdriver.sleep.time", "1500"));
		sleep(waitTime);
	}

	/**
	 * 待機時間(時間指定)
	 * 
	 * @param waitTime ミリ秒
	 */
	public static void sleep(int waitTime) {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// 何もしない
		}
	}
}
