package com.jairek.kalkulator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>EngineService</code>.
 */
public interface EngineServiceAsync {
	
	void wykonajDzialanie(Double lewa, String znak, Double prawa, AsyncCallback<String> callback);
}
