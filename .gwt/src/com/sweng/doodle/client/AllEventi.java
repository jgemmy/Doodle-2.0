package com.sweng.doodle.client;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
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
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.Partecipa;

public class AllEventi {
	String idevento ;

	ListGrid countryGrid = new ListGrid();  
	ListGrid userGrid = new ListGrid(); 
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	Label info = new Label("Doppio click per visualizzare le info:");
	Label lnome = new Label("Nome:");
	TextBox tnome = new TextBox();
	CheckBox yes = new CheckBox("Yes");
	CheckBox no = new CheckBox("No");
	TextBox commenti = new TextBox();
	Button salva = new Button("Salva");	
	


	public AllEventi(final TabPanel pannello){
		
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


		final VerticalPanel panel = new VerticalPanel();
		countryGrid.setWidth(700);  
		countryGrid.setHeight(224);  
		countryGrid.setShowAllRecords(true);  
		countryGrid.setCanEdit(false);  
		countryGrid.setEditEvent(ListGridEditEvent.CLICK);  
		countryGrid.setModalEditing(false);  
		ListGridField idField = new ListGridField("id", "ID");
		ListGridField nameField = new ListGridField("nome", "Nome Evento");  
		ListGridField placeField = new ListGridField("luogo", "Luogo");  
		ListGridField descrField = new ListGridField("descrizione", "Descrizione");  
		ListGridField fromField = new ListGridField("dal", "Dal");  
		ListGridField toField = new ListGridField("al", "Al");
		ListGridField checkField = new ListGridField("check", "Stato");
		ListGridField causeField = new ListGridField("causechiuso", "Motivi");
		countryGrid.setFields(new ListGridField[] {idField, nameField, placeField, descrField, fromField, toField,checkField, causeField});
		userGrid.setWidth(400);  
		userGrid.setHeight(224);  
		userGrid.setShowAllRecords(true);  
		userGrid.setCanEdit(false);  
		userGrid.setEditEvent(ListGridEditEvent.CLICK);  
		userGrid.setModalEditing(false);  
		ListGridField nomeField = new ListGridField("nome", "Nome");
		userGrid.setFields(new ListGridField[] {nomeField});
		countryGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				// TODO Auto-generated method stub
				ListGridRecord record = (ListGridRecord)event.getRecord(); 
				detailViewer.setData(countryGrid.getSelection());
				idevento = record.getAttribute("id");
				/*nome user nel textbox*/
				inListJoiners();
				panel.add(userGrid);
				panel.add(new HTML("<text> <br> </text>"));
				panel.add(commenti);
				commenti.setText("inserire qui eventuale commento");
				panel.add(new HTML("<text> <br> </text>"));
				panel.add(lnome);
				panel.add(new HTML("<text> <br> </text>"));
				panel.add(tnome);
				panel.add(new HTML("<text> <br> </text>"));
				panel.add(yes);
				yes.setChecked(true);
				panel.add(new HTML("<text> <br> </text>"));
				panel.add(no);
				panel.add(new HTML("<text> <br> </text>"));
				panel.add(salva);
				
				salva.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						if ((event.getSource() == salva) && (!(tnome.getText().length() == 0)))
					inInsertJoin();	
					}
				});
				
			}
		});
		inGetAllEvents();
		panel.add(countryGrid);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(info);
		panel.add(new HTML("<text> <br> </text>"));
		panel.add(detailViewer);
		panel.add(new HTML("<text> <br> </text>"));
	
		
		
		panel.add(new HTML("<text> <br> </text>"));
		
		pannello.add(panel, "Eventi");
		
		
	}
	public void inInsertJoin(){
		greetingService.insertJoin(idevento, tnome.getText(), tnome.getText(), tnome.getText(), 1, new AsyncCallback<String>() {
			
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
	public void inListJoiners(){
		greetingService.getAllUsersJoin(idevento, new AsyncCallback<LinkedList<Partecipa>>() {
			
			@Override
			public void onSuccess(LinkedList<Partecipa> result) {
				// TODO Auto-generated method stub
				System.out.println(result);
				userGrid.setData(UserGridData.getRecords(result));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		}); 
		
		
	}

	public void inGetAllEvents(){
		greetingService.getAllEvents(new AsyncCallback<LinkedList<Evento>>() {

			@Override
			public void onSuccess(LinkedList<Evento> result) {
				countryGrid.setData(CountrySampleData.getRecords(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Procedura Fallita");
			}
		});
	}
}
