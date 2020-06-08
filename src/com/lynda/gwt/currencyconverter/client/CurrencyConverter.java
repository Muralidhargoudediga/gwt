package com.lynda.gwt.currencyconverter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class CurrencyConverter implements EntryPoint {
    public void onModuleLoad() {
        Converter converter = new Converter();
        RootPanel.get().add(converter);
    }
}
