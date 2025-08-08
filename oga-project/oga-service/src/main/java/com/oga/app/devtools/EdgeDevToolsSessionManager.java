package com.oga.app.devtools;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.Network.GetResponseBodyResponse;
import org.openqa.selenium.devtools.v138.network.model.RequestId;
import org.openqa.selenium.edge.EdgeDriver;

import com.oga.app.devtools.interfaces.DevToolsEventListener;
import com.oga.app.devtools.interfaces.DevToolsSessionManager;

public class EdgeDevToolsSessionManager implements DevToolsSessionManager, AutoCloseable {

	/** DevTools */
	private DevTools devTools;

	@Override
	public void attach(WebDriver driver) {
		if (!(driver instanceof EdgeDriver edgeDriver)) {
			throw new IllegalStateException("DevTools supported only for EdgeDriver");
		}
		devTools = edgeDriver.getDevTools();
		devTools.createSession();
	}

	@Override
	public void addNetworkListeners(DevToolsEventListener listener) {
		// ネットワーク監視を有効化
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.clearBrowserCache());
		devTools.send(Network.setCacheDisabled(true));
		// リクエスト時のリスナーを登録する
		devTools.addListener(Network.requestWillBeSent(), listener::onRequestWillBeSent);
	}

	@Override
	public String getResponseBody(RequestId requestId) {
		try {
			GetResponseBodyResponse bodyResponse = devTools.send(Network.getResponseBody(requestId));
			return bodyResponse.getBody();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void detach() {
		if (devTools != null) {
			devTools.close();
		}
	}

	@Override
	public void close() {
		detach();
	}

}
