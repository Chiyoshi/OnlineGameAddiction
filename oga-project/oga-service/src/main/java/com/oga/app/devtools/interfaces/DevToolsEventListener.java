package com.oga.app.devtools.interfaces;

import org.openqa.selenium.devtools.v138.network.model.RequestWillBeSent;

@FunctionalInterface
public interface DevToolsEventListener {
    public void onRequestWillBeSent(RequestWillBeSent requestWillBeSent);
}
