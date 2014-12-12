package com.sweng.doodle.client;

import java.util.Date;
import java.util.LinkedList;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.sweng.doodle.shared.Evento;


public class EventsGridData {  

	private static RecordList dataTable = new RecordList();
	public static RecordList getRecords(LinkedList<Evento> dataTable) {  
		return setEventi(dataTable);

	}    

	public static ListGridRecord createRecord(String id, String nome, String luogo, String descrizione, Date dal, Date al, String check, String cause) {  
		ListGridRecord record = new ListGridRecord();  
		record.setAttribute("id", id);  
		record.setAttribute("nome", nome);  
		record.setAttribute("luogo", luogo);  
		record.setAttribute("descrizione", descrizione);  
		record.setAttribute("dal", dal);  
		record.setAttribute("al", al);    
		record.setAttribute("check", check);  
		record.setAttribute("causechiuso", cause);  
		return record;  
	}  


	public static RecordList setEventi(LinkedList<Evento> eventi){
		dataTable = new RecordList();
		for(int i = 0 ; i < eventi.size(); i++){
			dataTable.add(createRecord(eventi.get(i).getID(), eventi.get(i).getNome(), eventi.get(i).getLuogo(), eventi.get(i).getDesc(), eventi.get(i).getDal(), eventi.get(i).getAl(),eventi.get(i).getCheck() == 0 ? "chiuso" :  "aperto",eventi.get(i).getCause()));
		}
		return dataTable;
	}


}  

