package com.sweng.doodle.client;

import java.util.LinkedList;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.sweng.doodle.shared.Partecipa;

public class UserGridData {  

	private static RecordList dataTable = new RecordList();

	public static RecordList getRecords(LinkedList<Partecipa> dataTable) {  
		return setUser(dataTable);

	}    

	public static ListGridRecord createRecord(String nome, String commento) {  
		ListGridRecord record = new ListGridRecord();    
		record.setAttribute("nome", nome);  
		record.setAttribute("commento", commento);  
		   
		return record;  
	}  


	public static RecordList setUser(LinkedList<Partecipa> partecipa){
		for(int i = 0 ; i < partecipa.size(); i++)
			dataTable.add(createRecord(partecipa.get(i).getNome(),partecipa.get(i).getCommento()));
		return dataTable;
	}


}  