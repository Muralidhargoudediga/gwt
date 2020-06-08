package com.lynda.gwt.currencyconverter.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Converter extends Composite {

	private static ConverterUiBinder uiBinder = GWT.create(ConverterUiBinder.class);

	interface ConverterUiBinder extends UiBinder<Widget, Converter> {
	}

	@UiField 
	ListBox primaryRateList, secondRateList;
	
	@UiField
	TextBox primaryAmt, secondAmt;

	String url = "http://data.fixer.io/api/latest";
	//Login to https://fixer.io/ to get the access token
	private static final String ACCESS_KEY = "2e304e64f5496327a1568ca879fcb217";

	String base;
	JSONObject rates;
	
	public Converter() {
		initWidget(uiBinder.createAndBindUi(this));
		setConversionDataUi();
	}

	private  void setConversionDataUi(){
		//We will be using JSONP to get the foreign exchange rates data from https://fixer.io/ provided API'S.
		//The reason we are using JSONP is to by-pass Same Origin Policy(SOP) security. For this to work, fixer.io must provide JSONP API's
		JsonpRequestBuilder jsonpRequestBuilder = new JsonpRequestBuilder();
		jsonpRequestBuilder.requestObject(getEndpoint(), new AsyncCallback<FXRates>() {
			@Override
			public void onFailure(Throwable throwable) {
				GWT.log("Request did not complete");
			}

			@Override
			public void onSuccess(FXRates result) {
				if(result == null) {
					GWT.log("Result was null");
					return;
				}
				base = result.getBase();
				rates = new JSONObject(result.getRates());
				int count = 0, usdIndex = -1;

				for(String rate : rates.keySet()) {
					primaryRateList.addItem(rate);
					secondRateList.addItem(rate);

					if(rate.equals("USD")){
						usdIndex = count;
					}
					count++;
				}

				primaryAmt.setText("1");
				primaryRateList.insertItem(base, 0);
				primaryRateList.setSelectedIndex(0);

				if(usdIndex != -1) {
					secondRateList.setSelectedIndex(usdIndex);
				}

				secondRateList.insertItem(base, 0);

				double secondAmount = rates.get("USD").isNumber().doubleValue();

				secondAmt.setText(Double.toString(secondAmount));
			}
		});

	}

	private  void setConversionDataUi(String selectedBase){
		JsonpRequestBuilder jsonpRequestBuilder = new JsonpRequestBuilder();
		jsonpRequestBuilder.requestObject(getEndpointWithBase(selectedBase), new AsyncCallback<FXRates>() {
			@Override
			public void onFailure(Throwable throwable) {
				GWT.log("Request did not complete");
			}

			@Override
			public void onSuccess(FXRates result) {
				if(result == null) {
					GWT.log("Result was null");
					return;
				}
				base = result.getBase();
				rates = new JSONObject(result.getRates());

				//String primaryRate = primaryRateList.getSelectedItemText();
				String secondRate = secondRateList.getSelectedItemText();

				double primaryAmount = Double.parseDouble(primaryAmt.getValue());

				double secondAmount = rates.get(secondRate).isNumber().doubleValue() * primaryAmount;

				secondAmt.setText(Double.toString(secondAmount));
			}
		});

	}

	@UiHandler({"primaryRateList", "secondRateList", "primaryAmt"})
	void handleRateSelection(ChangeEvent event) {
		GWT.log("Change event occurred");
		setConversionDataUi(primaryRateList.getSelectedItemText());
	}

	private String getEndpoint(){
		return url+"?access_key=" + ACCESS_KEY;
	}

	private String getEndpointWithBase(String base){
		return getEndpoint() + "&base=" + base;
	}
}
