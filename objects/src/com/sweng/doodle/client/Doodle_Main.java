package com.sweng.doodle.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class Doodle_Main extends Composite{
	public static String idKey="";
	Button Login = new Button("Login");
	final Button logout = new Button("Log Out");
	Button Registrazione = new Button("Registrazione");
	static TabPanel pannello = new TabPanel();



	public Doodle_Main(){

		if (!(Cookies.getCookie("MyCookies").contentEquals("-1"))){
			//			Window.alert("Welcome user with id: "+Cookies.getCookie("MyCookies"));
			idKey = Cookies.getCookie("MyCookies");
			new AmministrativoEventi(pannello);
//			new GestioneEventi(pannello);
			new AllEventiUser(pannello);
			pannello.getElement().setAttribute("align", "center");
			TabPanel tab = new TabPanel();
			new GestioneEventi(tab);
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.add(logout);
			hPanel.add(pannello);
			hPanel.setSpacing(40);
			hPanel.add(tab);
			tab.selectTab(0);
			tab.addStyleName("tabKey");
			RootPanel.get().add(hPanel);
			} else {
//				new Login(pannello);
//				new Registrazione(pannello);
				new AllEventiAnonymus(pannello);
				TabPanel log = new TabPanel();
				new Login(log);
				TabPanel reg = new TabPanel();
				new Registrazione(reg);
				HorizontalPanel hPanel = new HorizontalPanel();
				hPanel.add(log);
				log.selectTab(0);
				hPanel.add(pannello);
				hPanel.add(reg);
				reg.selectTab(0);
				hPanel.setSpacing(40);
				RootPanel.get().add(hPanel);
				
				pannello.selectTab(0);	
				
			}




		
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


