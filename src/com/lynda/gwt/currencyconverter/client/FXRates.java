package com.lynda.gwt.currencyconverter.client;

import com.google.gwt.core.client.JavaScriptObject;
//This is an Overlay type Object, created to work with JavaScript objects in JAVA

public class FXRates extends JavaScriptObject {

    protected FXRates() {
    }

    //Instance methods in overlay type must be final.
    //Declared this(and following) method(s) as native to work with JSNI(JavaScript native Interface).
    public native final String getBase() /*-{
        return this.base;
    }-*/;

    public native final String getDate() /*-{
        return this.date;
    }-*/;

    public native final JavaScriptObject getRates() /*-{
        return this.rates;
    }-*/;
}
