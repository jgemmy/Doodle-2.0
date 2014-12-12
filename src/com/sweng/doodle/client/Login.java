package com.sweng.doodle.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sweng.doodle.shared.User;

public class Login{

	final TextBox tuser = new TextBox();
	final PasswordTextBox tpassw = new PasswordTextBox();
	public static String idKey = "";
	public static String username;
	public static String nome= "anonymous";
	VerticalPanel panel ;
	TabPanel pannello;
	final long DURATION = 1000 * 60 * 60 * 24 * 14;
	Date expires = new Date(System.currentTimeMillis() + DURATION);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	final Button Login = new Button("Log in");
	public Login(final TabPanel pannello){
		this.pannello = pannello;
		Label user= new Label("Username:");
		Label passw = new Label("Password:");
		panel = new VerticalPanel();
		panel.setHeight("50");
		panel.setWidth("150");
		panel.setSpacing(20);
		panel.add(user);		
		panel.add(tuser);
		panel.add(passw);		
		panel.add(tpassw);		
		panel.add(Login);
		panel.getElement().setAttribute("align", "center");
		pannello.add(panel, "Log in");
		panel.addStyleName("tabLeft");
		tpassw.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event_){
				boolean enterPressed = KeyCodes.KEY_ENTER == event_.getNativeEvent().getKeyCode();
				if ( (enterPressed) && (!(tuser.getText().length() == 0)) && 
						(!(tpassw.getText().length() == 0 )) && (!(tuser.getText().contains("'"))) && 
						(!(tpassw.getText().contains("'")))){
					inLogin();
				}
			}
		});

		Login.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ((event.getSource() == Login) && (!(tuser.getText().length() == 0)) && (!(tpassw.getText().length() == 0)) 
						&& (!(tuser.getText().contains("'"))) && (!(tpassw.getText().contains("'"))) ) 
					inLogin();

			}
		});

	}
/*Funzione che mi permette il meccanismo di login e setta i coockie*/
	public void inLogin(){
		greetingService.login(new User (tuser.getText(), tpassw.getText()), new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if(result.contentEquals("empty") ){
					Doodle_Main.pannello.selectTab(0);
					Window.alert("username e/o password errati");
				} else {
					idKey = result;	
					username = tuser.getText();
					Cookies.setCookie("MyCookies", result , expires);
					Window.alert("Benvenuto: "+username+" Login Effettuato");

					Window.Location.reload();

				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("username e/o password errati");

			}
		});
	}


}












