package com.sweng.doodle.client;

import java.util.Date;
import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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

public class TuttiGliEventi {

	final DetailViewer commentview = new DetailViewer();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	VerticalPanel registrati = new VerticalPanel();
	HorizontalPanel hPanels = new HorizontalPanel();
	HTML riga = new HTML("<hr>");
	HTML riga1 = new HTML("<hr>");
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
	Label lcomm = new Label("Inserire qui eventuali commenti:");
	Label llcomm = new Label("Lista commenti: ");
	Label disp = new Label("Disponibilita:");

	TabPanel registra = new TabPanel();
	TextBox tnome = new TextBox();
	TextBox tnick = new TextBox();
	TextBox commenti = new TextBox();
	CheckBox yes = new CheckBox("Yes");
	CheckBox no = new CheckBox("No");
	Button salva = new Button("Salva");	
	Button comm = new Button("Commenta");	
	String getdate= (new Date(System.currentTimeMillis())).toString();

	public TuttiGliEventi(final TabPanel pannello){

		final VerticalPanel panel = new VerticalPanel();
		eventsGrid.setWidth(650);  
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

		eventsGrid.addStyleName("tables");

		userGrid.setWidth(180);  
		userGrid.setHeight(200);  
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
		userGrid.setStyleName("tables");
		commentGrid.setWidth(520);  
		commentGrid.setHeight(224);  
		commentGrid.setShowAllRecords(true);  
		commentGrid.setCanEdit(false);  	
		commentGrid.setEditEvent(ListGridEditEvent.CLICK);  
		commentGrid.setModalEditing(false);  
		ListGridField nicknameField = new ListGridField("nickname", "Nick - Name");
		ListGridField commField = new ListGridField("commento", "Commento");
		commentGrid.setFields(new ListGridField[] {nicknameField, commField});


		final DetailViewer detailViewer = new DetailViewer();  
		detailViewer.setWidth(450);  
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
		commentview.addStyleName("tables");

		hPanels.add(detailViewer);
		hPanels.setSpacing(10);
		eventsGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				// TODO Auto-generated method stub
				ListGridRecord record = event.getRecord(); 
				detailViewer.setData(eventsGrid.getSelection());
				//				userViewer.setData(userGrid.getDataAsRecordList());
				idevento = record.getAttribute("id");
				motivi = record.getAttribute("causechiuso");


				if (record.getAttribute("check").contentEquals("chiuso")){
					Window.alert("Evento Chiuso");
					if(userGrid.isAttached()){
						hPanels.remove(userGrid);
						//						panel.remove(userGrid);
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
						registra.removeFromParent();
						//						panel.remove(riga);
						panel.remove(riga1);
					}
				}
				else{
					inGetNick();
					inGetNome();
					inListJoiners();
					inListcomment();	
					hPanels.add(userGrid);
					//					panel.add(userGrid);
					userGrid.setEmptyMessage("Nessun Utente e' inscritto all'evento!");

					panel.add(lcomm);
					panel.add(commenti);
					panel.add(comm);
					panel.add(riga1);
					panel.add(llcomm);
					panel.add(commentview);
					commentview.setEmptyMessage("Nessun commento inserito per l'evento!");
					//					panel.add(riga);
					registrati.add(lnome);
					registrati.add(tnome);
					tnome.setReadOnly(true);
					registrati.add(lnick);
					registrati.add(tnick);
					tnick.setReadOnly(true);
					registrati.add(disp);
					registrati.add(yes);
					yes.setChecked(true);
					registrati.add(no);
					registrati.setSpacing(10);
					registrati.add(salva);
					registra.setAnimationEnabled(true);  
					registra.add(registrati,"Iscriviti");
					registrati.addStyleName("tabLeft");
					Doodle_Main.left.add(registra);
					registra.addStyleName("iscriviti");
					registra.selectTab(0);
					panel.setCellHorizontalAlignment(commentview,HasHorizontalAlignment.ALIGN_CENTER);
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
		panel.add(info);
		info.setStyleName("gwt-Label");
		panel.add(eventsGrid);
		eventsGrid.setEmptyMessage("Nessun evento da visualizzare!");
		panel.add(hPanels);
		detailViewer.setEmptyMessage("");
		pannello.add(panel, "Tutti Gli Eventi");


	}
/*Funzione che permette di iscrivermi ad un evento*/
	public void inInsertJoin(){
		greetingService.insertJoin(new Partecipante( idevento,"", Doodle_Main.idKey, "User", ""+1), new AsyncCallback<String>() {

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
/*Funzione che mi permette di rimuovere la disponibilita per un certo evento*/
	public void inDeleteJoin(){
		greetingService.deleteJoin(idevento, tnome.getText(), new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Window.alert("Disponibilita Rimossa con risultato: "+result);
				Window.Location.reload();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Disponibilita non Rimossa");
			}
		});



	};
	
	/*Funzione che mi restituisce tutte le adesioni per un evento*/
	public void inListJoiners(){
		greetingService.getAllUsersJoin(idevento, new AsyncCallback<LinkedList<Partecipante>>() {

			@Override
			public void onSuccess(LinkedList<Partecipante> result) {	
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
/*Funzione che mi restituisce tutti gli eventi*/
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
				tnick.setText(Cookies.getCookie("MyCookies"));
				
			}

			
/*Funzione che mi restituisce il nome dell Utente*/
	public void inGetNome(){
		greetingService.GetNome(Cookies.getCookie("MyCookies"), new AsyncCallback<String>() {

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
/*funzione che mi permette di caricare un commento per un evento*/
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
/*Scrivo tutti i commenti nella tabella e nel comment view*/
	public void inListcomment(){
		greetingService.getAllCommenti(idevento, new AsyncCallback<LinkedList<Commento>>() {

			@Override
			public void onSuccess(LinkedList<Commento> result) {
				// TODO Auto-generated method stub
				System.out.println("COMMENTI SIZE "+result.size());
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
