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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login{

	final TextBox tuser = new TextBox();
	final PasswordTextBox tpassw = new PasswordTextBox();
	public static String idKey = "";
	String user;
	VerticalPanel panel ;
	TabPanel pannello;
	final long DURATION = 1000 * 60 * 60 * 24 * 14;
	Date expires = new Date(System.currentTimeMillis() + DURATION);
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	final Button Login = new Button("Log in");
	public Login(final TabPanel pannello){
		this.pannello = pannello;
		Label user= new Label("User:");
		Label passw = new Label("Password:");
		panel = new VerticalPanel();
		panel.add(user);		
		panel.add(tuser);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(passw);		
		panel.add(tpassw);		
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(Login);
		panel.getElement().setAttribute("align", "center");
		pannello.add(panel, "Log in");
		tpassw.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event_){
				boolean enterPressed = KeyCodes.KEY_ENTER == event_.getNativeEvent().getKeyCode();
				if ( enterPressed && tuser.getText().length() != 0 && tpassw.getText().length() == 0 ){
					inLogin();
				}
			}
		});
		Login.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ((event.getSource() == Login) &&!(tuser.getText().length() == 0) &&!(tpassw.getText().length() == 0)) 
					inLogin();
			}
		});

	}

	public void inLogin(){
		greetingService.login(tuser.getText(), tpassw.getText(), new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if(result.contentEquals("empty") ){
					Dio.pannello.selectTab(0);
					Window.alert("username e/o password errati");
				} else {
					idKey = result;	
//					user = User.getNome();
					Cookies.setCookie("MyCookies", result , expires);
					Window.alert("Benvenuto: "+user+" Login Effettuato");
					
					Window.Location.reload();
					
				}
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}


}












