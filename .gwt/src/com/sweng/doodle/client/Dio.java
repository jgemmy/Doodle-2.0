package com.sweng.doodle.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class Dio extends Composite{
	public static String idKey="";
	Button Login = new Button("Login");
	final Button logout = new Button("Log Out");
	Button Registrazione = new Button("Registrazione");
	static TabPanel pannello = new TabPanel();



	public Dio(){

		if (!(Cookies.getCookie("MyCookies").contentEquals("-1"))){
			//			Window.alert("Welcome user with id: "+Cookies.getCookie("MyCookies"));
			idKey = Cookies.getCookie("MyCookies");
			new UserSondaggio(pannello);
			new GestioneEventi(pannello);
			new AllEventi(pannello);
			RootPanel.get().add(logout);} else {
				new Login(pannello);
				new Registrazione(pannello);
				new AllEventi(pannello);
			}




		pannello.getElement().setAttribute("align", "center");
		RootPanel.get().add(pannello);


		logout.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Cookies.setCookie("MyCookies","-1");
				Window.Location.reload();
				Window.alert("Logout Eseguito");

			}
		});


	}
}


