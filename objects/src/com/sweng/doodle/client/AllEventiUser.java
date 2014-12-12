package com.sweng.doodle.client;

import java.util.Date;
import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.User;

public class AllEventiUser {

	final DetailViewer commentview = new DetailViewer();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	String idevento ;
	String motivi ;
	String nome;
	String nick;
	ListGrid eventsGrid = new ListGrid();  
	ListGrid userGrid = new ListGrid(); 
	ListGrid commentGrid = new ListGrid(); 
	Label info = new Label("Doppio click sull evento per visualizzare le info: ");
	Label lnome = new Label("Nome:");
	Label lnick = new Label("Nick:");
	Label lusers = new Label("Lista utenti inscritti all evento: ");
	Label lcomm = new Label("Inserire qui eventuali commenti:");
	Label llcomm = new Label("Lista commenti: ");
	Label disp = new Label("Disponibilita:");
	
	TextBox tnome = new TextBox();
	TextBox tnick = new TextBox();
	TextBox commenti = new TextBox();
	CheckBox yes = new CheckBox("Yes");
	CheckBox no = new CheckBox("No");
	Button salva = new Button("Salva");	
	Button comm = new Button("Commenta");	
	String getdate= (new Date(System.currentTimeMillis())).toString();

	public AllEventiUser(final TabPanel pannello){

		final VerticalPanel panel = new VerticalPanel();
		eventsGrid.setWidth(700);  
		eventsGrid.setHeight(224);  
		eventsGrid.setShowAllRecords(true);  
		eventsGrid.setCanEdit(false);  
		eventsGrid.setEditEvent(ListGridEditEvent.CLICK);  
		eventsGrid.setModalEditing(false);  
		ListGridField idField = new ListGridField("id", "ID");
		ListGridField nameField = new ListGridField("nome", "Nome Evento");  
		ListGridField placeField = new ListGridField("luogo", "Luogo");  
		ListGridField descrField = new ListGridField("descrizione", "Descrizione");  
		ListGridField fromField = new ListGridField("dal", "Dal");  
		ListGridField toField = new ListGridField("al", "Al");
		ListGridField checkField = new ListGridField("check", "Stato");
		ListGridField causeField = new ListGridField("causechiuso", "Motivi");
		eventsGrid.setFields(new ListGridField[] {idField, nameField, placeField, descrField, fromField, toField,checkField, causeField});

		userGrid.setWidth(224);  
		userGrid.setHeight(224);  
		userGrid.setShowAllRecords(true);  
		userGrid.setCanEdit(false);  
		userGrid.setEditEvent(ListGridEditEvent.CLICK);  
		userGrid.setModalEditing(false);  
		ListGridField nomeField = new ListGridField("nome", "Nome");
		ListGridField stateField = new ListGridField("stato", "Stato");
		ListGridField nickField = new ListGridField("nick", "Username");
		userGrid.setFields(new ListGridField[] {nomeField, nickField, stateField});

	commentGrid.setWidth(550);  
		commentGrid.setHeight(224);  
		commentGrid.setShowAllRecords(true);  
		commentGrid.setCanEdit(false);  	
		commentGrid.setEditEvent(ListGridEditEvent.CLICK);  
		commentGrid.setModalEditing(false);  
		ListGridField nicknameField = new ListGridField("nickname", "Nick - Name");
		ListGridField commField = new ListGridField("commento", "Commento");
		commentGrid.setFields(new ListGridField[] {nicknameField, commField});


		final DetailViewer detailViewer = new DetailViewer();  
		detailViewer.setWidth(500);  
		detailViewer.setFields(  
				new DetailViewerField("id", "ID"),  
				new DetailViewerField("nome", "Nome Evento"),  
				new DetailViewerField("luogo", "Luogo"),
				new DetailViewerField("descrizione", "Descrizione"),
				new DetailViewerField("dal", "Dal"),
				new DetailViewerField("al", "Al"),
				new DetailViewerField("check", "Stato"),
				new DetailViewerField("causechiuso", "Motivi")); 
		
		commentview.setWidth(500);  
		commentview.setFields(  
				new DetailViewerField("nickname", "Nick - Name"),  
				new DetailViewerField("commento", "Commento")); 
		

		eventsGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				// TODO Auto-generated method stub
				ListGridRecord record = (ListGridRecord)event.getRecord(); 
				detailViewer.setData(eventsGrid.getSelection());
				//				userViewer.setData(userGrid.getDataAsRecordList());
				idevento = record.getAttribute("id");
				motivi = record.getAttribute("causechiuso");


				if (record.getAttribute("check").contentEquals("chiuso")){
					Window.alert("Evento Chiuso");
					if(userGrid.isAttached()){
						panel.remove(lusers);
						panel.remove(userGrid);
						panel.remove(llcomm);
						panel.remove(commentview);
						panel.remove(lcomm);
						panel.remove(commenti);
						panel.remove(comm);
						panel.remove(lnome);
						panel.remove(tnome);
						panel.remove(lnick);
						panel.remove(tnick);
						panel.remove(disp);
						panel.remove(yes);
						panel.remove(no);
						panel.remove(salva);
					}
				}
				else{
					inGetNick();
					inGetNome();
					inListJoiners();
					inListcomment();	
					panel.add(lusers);
					panel.add(userGrid);
					userGrid.setEmptyMessage("Nessun Utente è inscritto all'evento!");
					panel.add(llcomm);
					panel.add(commentview);
					commentview.setEmptyMessage("Nessun commento inserito per l'evento!");
					panel.add(lcomm);
					panel.add(commenti);
					panel.add(comm);
					panel.add(lnome);
					panel.add(tnome);
					tnome.setReadOnly(true);
					panel.add(lnick);
					panel.add(tnick);
					tnick.setReadOnly(true);
					panel.add(disp);
					panel.add(yes);
					yes.setChecked(true);
					panel.add(no);

					panel.add(salva);

					comm.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							if ((event.getSource() == comm) && (!(commenti.getText().length() == 0)))
								incommenta();	



						}
					});

					no.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							yes.setChecked(false);	
						}
					});

					yes.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							no.setChecked(false);	
						}
					});

					salva.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							if ((event.getSource() == salva) && (!(tnome.getText().length() == 0)) && (yes.isChecked()))
								inInsertJoin();	

							if ((event.getSource() == salva) && (!(tnome.getText().length() == 0)) && (no.isChecked()))
								inDeleteJoin();
						}
					});

				}


			}
		});



		inGetAllEvents();
		panel.setSpacing(20);
		panel.add(eventsGrid);
		eventsGrid.setEmptyMessage("Nessun evento da visualizzare!");
		panel.add(info);
		panel.add(detailViewer);
		detailViewer.setEmptyMessage("");
		pannello.add(panel, "Tutti Gli Eventi");


	}
	public void inInsertJoin(){
		greetingService.insertJoin(idevento, tnome.getText(), tnick.getText(), "User", 1,Doodle_Main.idKey, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.alert("Inscrizione: " + result);
				Window.Location.reload();

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Iscrizione fallita");
			}
		});


	}

	public void inDeleteJoin(){
		greetingService.deleteJoin(idevento, tnome.getText(), new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.alert("Disponibilita Rimossa");
				Window.Location.reload();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Disponibilita non Rimossa");
			}
		});



	};
	public void inListJoiners(){
		greetingService.getAllUsersJoin(idevento, new AsyncCallback<LinkedList<User>>() {

			@Override
			public void onSuccess(LinkedList<User> result) {	
				// TODO Auto-generated method stu
				userGrid.setData(UsersGridData.getRecords(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("UserGrid non creata");
			}
		});





	}

	public void inGetAllEvents(){
		greetingService.getAllEvents(new AsyncCallback<LinkedList<Evento>>() {

			@Override
			public void onSuccess(LinkedList<Evento> result) {
				eventsGrid.setData(EventsGridData.getRecords(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Procedura Fallita");
			}
		});
	}

	public void inGetNick(){
		greetingService.GetNick(Doodle_Main.idKey, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				tnick.setText(result);
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
				tnome.setText(result);
				nome = result;

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

				Window.alert("fallito");
			}
		});
	}

	public void incommenta(){
		greetingService.insertcomm(idevento, commenti.getText(), getdate,Doodle_Main.idKey, new AsyncCallback<String>() {

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

	public void inListcomment(){
		greetingService.getAllCommenti(idevento, new AsyncCallback<LinkedList<Commento>>() {

			@Override
			public void onSuccess(LinkedList<Commento> result) {
				// TODO Auto-generated method stub
				commentview.setData(CommentGridData.getRecords(result));
				commentGrid.setData(CommentGridData.getRecords(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("CommentGrid non creata");
			}
		});

	}
}
