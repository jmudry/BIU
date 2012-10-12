package com.jairek.kalkulator.server;


import org.apache.commons.el.DivideOperator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jairek.kalkulator.client.EngineService;

@SuppressWarnings("serial")
public class EngineServiceImpl extends RemoteServiceServlet implements
		EngineService {

	public String wykonajDzialanie(Double lewa, String znak, Double prawa) {
		double wynik = 0;
		double prawa_liczba = prawa;
		double lewa_liczba = lewa;
		boolean check_error = false;

		String dzialanie = znak;
		
		if (dzialanie.equalsIgnoreCase("+")){
			wynik = lewa_liczba + prawa_liczba;	
		} else if (dzialanie.equalsIgnoreCase("-")){
			wynik = lewa_liczba - prawa_liczba;	
		} else if (dzialanie.equalsIgnoreCase("*")){
			wynik = lewa_liczba * prawa_liczba;	
		} else if (dzialanie.equalsIgnoreCase("/")){
			wynik = lewa_liczba / prawa_liczba;		
		} 
		return ""+wynik;
	}
	
}
