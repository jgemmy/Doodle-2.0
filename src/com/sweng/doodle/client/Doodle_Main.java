package com.sweng.doodle.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Doodle_Main extends Composite{
	static VerticalPanel left = new VerticalPanel();
	HorizontalPanel hPanel = new HorizontalPanel();
	public static String idKey="";
	final Button logout = new Button("Log Out");
	Button Login = new Button("Login");
	Button Registrazione = new Button("Registrazione");
	static TabPanel pannello = new TabPanel();
	TabPanel create = new TabPanel();
	TabPanel log = new TabPanel();
	TabPanel sign = new TabPanel();

/*Controllo i Cookie e carico la pagina*/
	public Doodle_Main(){

		pannello.setAnimationEnabled(true);  
		if ( Cookies.getCookie("MyCookies") != null && !(Cookies.getCookie("MyCookies").contentEquals("-1"))){
			//			Window.alert("Welcome user with id: "+Cookies.getCookie("MyCookies"));
			idKey = Cookies.getCookie("MyCookies");
			new IMieiEventi(pannello);
			new TuttiGliEventi(pannello);
			pannello.getElement().setAttribute("align", "center");			
			new CreaEventi(create);
			left.add(logout);
			hPanel.add(left);
			left.setSpacing(5);
			hPanel.add(new HTML("<br />"));
			hPanel.add(new HTML("<br />"));
			hPanel.add(new HTML("<br />"));
			hPanel.add(new HTML("<br />"));
			hPanel.add(new HTML("<br />"));
			hPanel.add(new HTML("<br />"));
			hPanel.add(pannello);
			hPanel.getElement().setAttribute("align", "left");
			left.getElement().setAttribute("align", "left");
			hPanel.setSpacing(25);
			hPanel.add(create);
			create.selectTab(0);
			create.addStyleName("tabRight");
			pannello.addStyleName("tabCenter");
			RootPanel.get().add(hPanel);
		} else {
			new TuttiGliEventiAnonimo(pannello);
			new Login(log);
			new Registrazione(sign);
			hPanel.add(log);
			log.selectTab(0);
			hPanel.add(pannello);
			hPanel.add(sign);
			sign.selectTab(0);
			hPanel.setSpacing(30);
			RootPanel.get().add(hPanel);
			pannello.addStyleName("tabCenter");
			pannello.selectTab(0);	

		}

		pannello.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == 0) {
					if(left.getWidgetCount() >=2){	
						left.remove(1);
					}
				}
			}
		});


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


