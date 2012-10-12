package com.jairek.kalkulator.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("engine")
public interface EngineService  extends RemoteService {
	String wykonajDzialanie(Double lewa, String znak, Double prawa);
}

