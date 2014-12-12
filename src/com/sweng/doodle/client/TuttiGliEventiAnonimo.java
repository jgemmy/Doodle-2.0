package com.sweng.doodle.client;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.Partecipante;
import com.sweng.doodle.shared.User;

public class TuttiGliEventiAnonimo {
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	final VerticalPanel panel = new VerticalPanel();
	HorizontalPanel hPanels = new HorizontalPanel();
	String idevento ;
	String motivi ;
	String nome;
	final DetailViewer commentview = new DetailViewer();
	final DetailViewer detailViewer = new DetailViewer();  
	ListGrid eventsGrid = new ListGrid();  
	ListGrid userGrid = new ListGrid(); 
	ListGrid commentGrid = new ListGrid();  
	Label info = new Label("Doppio click sull evento per visualizzare le info: ");
	Label lnome = new Label("Inserisci il tuo nome per iscriverti all'evento:");
	Label lcomm = new Label("Lista Commenti: ");
	TextBox tnome = new TextBox();
	Button salva = new Button("Iscriviti");	
	HTML riga = new HTML("<hr>");
	public TuttiGliEventiAnonimo(final TabPanel pannello){

		eventsGrid.setWidth(720);  
		eventsGrid.setHeight(214);  
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
		eventsGrid.setHeaderHeight(40);
		eventsGrid.setHeaderSpans(
				new HeaderSpan("Eventi", new String[]{"id", "nome","luogo","descrizione","dal","al","check","causechiuso"}));


		commentGrid.setWidth(550);  
		commentGrid.setHeight(224);  
		commentGrid.setShowAllRecords(true);  
		commentGrid.setCanEdit(false);  	
		commentGrid.setEditEvent(ListGridEditEvent.CLICK);  
		commentGrid.setModalEditing(false);  
		ListGridField nicknameField = new ListGridField("nickname", "Nick - Name");
		ListGridField commField = new ListGridField("commento", "Commento");
		commentGrid.setFields(new ListGridField[] {nicknameField, commField});


		userGrid.setWidth(200);  
		userGrid.setHeight(190);  
		userGrid.setShowAllRecords(true);  
		userGrid.setCanEdit(false);  
		userGrid.setEditEvent(ListGridEditEvent.CLICK);  
		userGrid.setModalEditing(false);  
		ListGridField nomeField = new ListGridField("nome", "Nome");
		ListGridField stateField = new ListGridField("stato", "Stato");
		ListGridField nickField = new ListGridField("nick", "Username");	
		userGrid.setFields(new ListGridField[] {nomeField, nickField, stateField});
		userGrid.setHeaderHeight(40);
		userGrid.setHeaderSpans(
				new HeaderSpan("Utenti Iscritti", new String[]{"nome", "stato","nick"}));
		userGrid.addStyleName("tables");


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
		detailViewer.addStyleName("tables");

		commentview.setWidth(500);  
		commentview.setFields(  
				new DetailViewerField("nickname", "Nick - Name"),  
				new DetailViewerField("commento", "Commento")); 
		hPanels.add(detailViewer);
		hPanels.setSpacing(10);
		eventsGrid.addStyleName("tables");
		eventsGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				// TODO Auto-generated method stub
				ListGridRecord record = event.getRecord(); 
				detailViewer.setData(eventsGrid.getSelection());
				idevento = record.getAttribute("id");
				motivi = record.getAttribute("causechiuso");


				if (record.getAttribute("check").contentEquals("chiuso")){
					Window.alert("Evento Chiuso");
					if(userGrid.isAttached()){
						hPanels.remove(userGrid);
						//						panel.remove(userGrid);
						panel.remove(riga);
						panel.remove(lnome);
						panel.remove(tnome);
						panel.remove(lcomm);
						panel.remove(commentview);
						panel.remove(salva);
					}
				}
				else{
					inListJoiners();		
					inListcomment();	
					hPanels.add(userGrid);
					//					panel.add(userGrid);
					panel.add(lcomm);
					panel.add(commentview);
					commentview.setEmptyMessage("Nessun commento inserito per l'evento!");
					panel.add(riga);
					panel.add(lnome);
					panel.add(tnome);
					panel.add(salva);

					panel.setCellHorizontalAlignment(commentview,HasHorizontalAlignment.ALIGN_CENTER);
					panel.setCellHorizontalAlignment(lnome,HasHorizontalAlignment.ALIGN_CENTER);
					panel.setCellHorizontalAlignment(tnome,HasHorizontalAlignment.ALIGN_CENTER);
					panel.setCellHorizontalAlignment(salva,HasHorizontalAlignment.ALIGN_CENTER);
					salva.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							if ((event.getSource() == salva) && (!(tnome.getText().length() == 0)) && (!(tnome.getText().contains("'"))))
								inInsertJoin();	
						}
					});

				}


			}
		});



		inGetAllEvents();
		panel.setSpacing(20);
		panel.add(info);
		info.setStyleName("gwt-Label");
		panel.add(eventsGrid);
		eventsGrid.setEmptyMessage("Non ci sono eventi");
		panel.add(hPanels);
		pannello.add(panel, "Eventi");
		panel.addStyleName("tabCenter");
		commentview.addStyleName("tables");
		commentview.setEmptyMessage("Selezionare un evento");
	}
	public void inInsertJoin(){
		greetingService.insertJoin(new Partecipante(idevento, tnome.getText(), "Anonymus", "User" ,""+1), new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.alert("Inscrizione: " + result);
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
		greetingService.getAllUsersJoin(idevento, new AsyncCallback<LinkedList<Partecipante>>() {

			@Override
			public void onSuccess(LinkedList<Partecipante> result) {
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
