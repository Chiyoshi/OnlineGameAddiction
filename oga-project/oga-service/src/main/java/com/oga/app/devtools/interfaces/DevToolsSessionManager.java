package com.oga.app.devtools.interfaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v138.network.model.RequestId;

public interface DevToolsSessionManager {

	/**
	 * DevToolsセッションに接続する
	 * 
	 * @param driver WEBドライバー
	 */
	public void attach(WebDriver driver);

	/**
	 * ネットワーク監視リスナー追加
	 * 
	 * @param listener リスナー
	 */
	public void addNetworkListeners(DevToolsEventListener listener);

	/**
	 * 指定したリクエストIDのレスポンスを取得する
	 * 
	 * @param requestId リクエストID
	 * @return レスポンスボディー
	 */
	public String getResponseBody(RequestId requestId);

	/**
	 * DevToolsセッションを切断する
	 */
	public void detach();

}
