package com.sweng.doodle.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sweng.doodle.shared.User;

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
		panel.setSpacing(20);
		panel.add(nome);
		panel.add(tnome);
		panel.add(nick);
		panel.add(tnick);
		panel.add(passw);
		panel.add(tpassw);
		panel.add(rpassw);
		panel.add(trpassw);
		panel.add(mail);
		panel.add(tmail);
		panel.add(singup);
		pannello.add(panel, "Registrazione");
		panel.getElement().setAttribute("align", "center"); 
		panel.setWidth("500");
		panel.addStyleName("tabRight");

		singup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ( 
						(tnome.getText().length() == 0) ||
						(tnome.getText().contains("'")) ||
						(tnick.getText().length() == 0) ||
						(tnick.getText().contains("'")) ||
						(tpassw.getText().length() == 0) ||
						(tpassw.getText().contains("'")) ||
						(trpassw.getText().length() == 0) ||
						(tnome.getText().contains("'")) ||
						(!(trpassw.getText().contentEquals(trpassw.getText())))) return; 
				else{

					inRegistazione();

				}}});


	}

	public void inRegistazione(){

		greetingService.registrazione(new User(tnome.getText(),tnick.getText(),tpassw.getText(), tmail.getText()), new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				//							 TODO Auto-generated method stub
				Window.alert("Procedura Remota Fallita");
			}

			@Override
			public void onSuccess(String result) {
				//							 TODO Auto-generated method stub
				if( result.contentEquals("Utente esistente")){
					Window.alert(result+": Registazione Non Effettuata");
				}else 
				Window.alert(result+": Registazione Effettuata");
				Window.Location.reload();

			}
		});
	}


}


















