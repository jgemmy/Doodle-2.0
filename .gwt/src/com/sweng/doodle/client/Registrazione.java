package com.sweng.doodle.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Registrazione extends Composite  {
	Button singup = new Button("Sing Up");
	Label nome= new Label("Nome:");
	Label nick= new Label("Nickname:");
	Label passw= new Label("Password:");
	Label rpassw= new Label("Ripeti Password:");
	Label mail = new Label("Indirizzo mail:  (opzionale)");
	
	final TextBox tnome = new TextBox();
	final TextBox tnick = new TextBox();
	final PasswordTextBox tpassw = new PasswordTextBox();
	final PasswordTextBox trpassw = new PasswordTextBox();
	final TextBox tmail = new TextBox();
	VerticalPanel panel = new VerticalPanel();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);


	public Registrazione(TabPanel pannello) {

		panel.add(nome);
		panel.add(tnome);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(nick);
		panel.add(tnick);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(passw);
		panel.add(tpassw);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(rpassw);
		panel.add(trpassw);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(mail);
		panel.add(tmail);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(singup);
		pannello.add(panel, "Registrazione");
		panel.getElement().setAttribute("align", "center"); 


		trpassw.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event_){
				boolean enterPressed = KeyCodes.KEY_ENTER == event_.getNativeEvent().getKeyCode();
				if ( (  ((tnome.getText().length() != 0) && 
						(tnick.getText().length() != 0) &&
						(tpassw.getText().length() != 0) &&
						(trpassw.getText().length() != 0) &&
						(tpassw.getText().contentEquals(trpassw.getText())))) && (enterPressed)  ) inRegistazione();  
				else{
					return;
				}
			}
		});



		tmail.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event_){
				boolean enterPressed = KeyCodes.KEY_ENTER == event_.getNativeEvent().getKeyCode();
				if ( (  ((tnome.getText().length() != 0) && 
						(tnick.getText().length() != 0) &&
						(tpassw.getText().length() != 0) &&
						(trpassw.getText().length() != 0) &&
						(tpassw.getText().contentEquals(trpassw.getText())))) && (enterPressed)    ) inRegistazione();  
				else{
					return;
				}
			}
		});

		singup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ( 
						(tnome.getText().length() == 0) ||
						(tnick.getText().length() == 0) ||
						(tpassw.getText().length() == 0) ||
						(trpassw.getText().length() == 0) ||
						!(tpassw.getText().contentEquals(trpassw.getText()))) return; 
				else{

					inRegistazione();

				}}});


	}

	public void inRegistazione(){

		greetingService.registrazione(tnome.getText(),tnick.getText(),tpassw.getText(), tmail.getText(), new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				//							 TODO Auto-generated method stub
				Window.alert("Procedura Remota Fallita");
			}

			@Override
			public void onSuccess(String result) {
				//							 TODO Auto-generated method stub
				Window.alert(result +  ": Registrazione Effettuata");
				Dio.pannello.selectTab(1); 

			}
		});
	}


}


















