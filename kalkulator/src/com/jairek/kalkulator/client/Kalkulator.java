package com.jairek.kalkulator.client;

import java.awt.font.NumericShaper;

import com.jairek.kalkulator.shared.FieldVerifier;
import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Kalkulator implements EntryPoint {
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final EngineServiceAsync engineService = GWT
			.create(EngineService.class);

	
	public void onModuleLoad() {
		
		final Button sendButton = new Button("Send");
		final TextBox textArea = new TextBox();
		
		final Label errorLabel = new Label();
		
		final Label dzialanie = new Label();
		final Label lewa = new Label();
		final Label prawa = new Label();
		
		Button[] number_buttons = new Button[10];

		class MyCharHandler implements ClickHandler {
			String znak;
			
			public MyCharHandler(String x) {
				znak = x;
			}
			@Override
			public void onClick(ClickEvent event) {
				double lewa_liczba = 0;
				try {
					lewa_liczba = Double.parseDouble(textArea.getText());
				} catch (NumberFormatException e) { }
				lewa.setText(""+lewa_liczba);
				dzialanie.setText(znak);
				textArea.setText("");
				prawa.setText("");
			}
			
		};
		
		
		class NumberButtonsHandler implements ClickHandler {
			String number;
			
			public NumberButtonsHandler(String i) {
				this.number = i;
			}
			
			@Override
			public void onClick(ClickEvent event) {
				if (DOM.getElementAttribute(textArea.getElement(), "data-result").equalsIgnoreCase("true")) {
					textArea.setValue(""+number);
				} else {
					try {
						Double.parseDouble(textArea.getValue() + number);	
						textArea.setValue(textArea.getValue() + number);
					} catch (Exception e){}
				}
				DOM.setElementAttribute(textArea.getElement(), "data-result", "false");
			}
			
		}
			
		
		for (int i = 0; i < 10; i++) {
			number_buttons[i] = new Button("" + i);
			number_buttons[i].addClickHandler(new NumberButtonsHandler(""+i));
			RootPanel.get("number" + i).add(number_buttons[i]);
			
		}
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		Button buttonPrzecinek = new Button(",");
		Button buttonPlus = new Button("+");
		Button buttonMinus = new Button("-");
		Button buttonRazy = new Button("*");
		Button buttonRownaSie = new Button("=");
		Button buttonDiv = new Button("/");

		
		buttonPlus.addClickHandler(new MyCharHandler("+"));
		buttonMinus.addClickHandler(new MyCharHandler("-"));
		buttonRazy.addClickHandler(new MyCharHandler("*"));
		buttonDiv.addClickHandler(new MyCharHandler("/"));
		buttonPrzecinek.addClickHandler(new NumberButtonsHandler("."));
		
		 buttonRownaSie.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				double prawa_liczba = 0;
				double lewa_liczba = 0;

				
				try {
					prawa_liczba = Double.parseDouble(textArea.getText());
				} catch (NumberFormatException e) {
				}
				try {
					lewa_liczba = Double.parseDouble(lewa.getText());
				} catch (NumberFormatException e) {
				}
				
				engineService.wykonajDzialanie(lewa_liczba,dzialanie.getText(), prawa_liczba, 
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						textArea.setText("error");
					}

					public void onSuccess(String result) {
						textArea.setText(result);
						lewa.setText("");
						dzialanie.setText("");
						DOM.setElementAttribute(textArea.getElement(), "data-result", "true");
					}
					});		
							
			}
		});
		
		
		RootPanel.get("plus").add(buttonPlus);
		RootPanel.get("minus").add(buttonMinus);
		RootPanel.get("razy").add(buttonRazy);
		RootPanel.get("rownasie").add(buttonRownaSie);
		RootPanel.get("dzielenie").add(buttonDiv);
		RootPanel.get("dzialanie").add(dzialanie);
		RootPanel.get("lewa").add(lewa);
		RootPanel.get("przecinek").add(buttonPrzecinek);
		RootPanel.get("textArea").add(textArea);

	}
}