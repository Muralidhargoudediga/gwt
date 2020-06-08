/**
 * 
 */
package com.lynda.gwt.myfirstapp.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * @author kylegowerwinter
 *
 */
public interface Resources extends ClientBundle {

	@Source("com/lynda/gwt/myfirstapp/client/myResources/external.css")
	External external();

	@Source("com/lynda/gwt/myfirstapp/client/myResources/GWT_Brand_Guidelines.pdf")
	DataResource gWTBrandGuidelines();

	@Source("com/lynda/gwt/myfirstapp/client/myResources/GWT_logo.png")
	ImageResource gWTLogo();

	@Source("com/lynda/gwt/myfirstapp/client/myResources/name.txt")
	TextResource name();

}
