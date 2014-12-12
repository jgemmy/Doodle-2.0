package com.sweng.doodle.client;

import java.util.Date;
import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Utils;


public class GestioneEventi {
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	Label nome= new Label("Nome Evento");
	Label luogo= new Label("Luogo:  (opzionale)");
	Label descs = new Label("Descrizione:  (opzionale)");
	Label from = new Label("Dal: , Dalle ore:");
	Label to= new Label("Al: , Alle ore:");
	TextBox tnome = new TextBox();
	TextBox tluogo = new TextBox();
	TextBox tdescs = new TextBox();
	DateBox tfrom = new DateBox();           
	DateBox tto = new DateBox();
	String snome = "empty";
	String sluogo= "empty";
	String sdescs= "empty";
	String ifrom = "empty";
	String ito = "empty";
	String idevento;
	String nick;
	String name;
	Button carica = new Button("Carica Evento");
	String getdate= (new Date(System.currentTimeMillis())).toString();

	public GestioneEventi(final TabPanel pannello){
		VerticalPanel panel = new VerticalPanel();
		inGetNick();
		inGetNome();
		panel.setSpacing(20);
		panel.add(nome);
		panel.add(tnome);
		panel.add(luogo);
		panel.add(tluogo);
		panel.add(descs);
		panel.add(tdescs);
		panel.add(from);
		panel.add(tfrom);
		panel.add(to);
		panel.add(tto);
		panel.add(carica);
		pannello.add(panel, "Carica Evento");
		carica.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!((tnome.getText().length() == 0)) && (!(Utils.isStringNumeric(tnome.getText())))) 
					snome = new String(tnome.getText()); else return;
				if (!((tluogo.getText().length() == 0)) && (!(Utils.isStringNumeric(tluogo.getText())))) 
					sluogo = new String(tluogo.getText());
				if (!((tdescs.getText().length() == 0)) && (!(Utils.isStringNumeric(tdescs.getText())))) 
					sdescs = new String(tdescs.getText());
				if (!((tfrom.getValue().toString().length() == 0)))   
					ifrom = tfrom.getValue().toString(); else return;
				if (!((tto.getValue().toString().length() == 0))) 
					ito = tto.getValue().toString(); else return;
				inEvento();


			} 

		});

	}
	public void inEvento(){

		greetingService.caricaevento(snome, sluogo, sdescs, ifrom, ito,Doodle_Main.idKey,1,"", new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("procedura remota fallita");
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.alert("Evento caricato con successo: id evento  = "+result);
				idevento = result;
				inInsertJoin();
				//				incommenta();

			}
		});	
	}

	public void inInsertJoin(){
		greetingService.insertJoin(idevento, name, nick, "Owner", 1, Doodle_Main.idKey, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.Location.reload();
				return;

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Iscrizione fallita");
				return;
			}
		});


	}

	public void inGetNick(){
		greetingService.GetNick(Doodle_Main.idKey, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				nick = result;

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("fallito");
			}
		});
	}

	public void inGetNome(){
		greetingService.GetNome(Doodle_Main.idKey, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				name = result;

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

				Window.alert("fallito");
			}
		});
	}

	public void incommenta(){
		greetingService.insertcomm(idevento, "", getdate,Doodle_Main.idKey, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.alert(result);
				Window.Location.reload();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("fallit");
			}
		});
	}
	
	public void injuve(){
		greetingService.getAllCommenti(idevento, new AsyncCallback<LinkedList<Commento>>() {
			
			@Override
			public void onSuccess(LinkedList<Commento> result) {
				// TODO Auto-generated method stub
				Window.alert("yo");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}



