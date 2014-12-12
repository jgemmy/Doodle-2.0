package com.sweng.doodle.client;

import java.util.LinkedList;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.sweng.doodle.shared.Partecipante;


public class UsersGridData {  

	private static RecordList dataTable = new RecordList();

	public static RecordList getRecords(LinkedList<Partecipante> dataTable) {  
		return setUser(dataTable);

	}    

	public static ListGridRecord createRecord(String nome, String nick,String stato) {  
		ListGridRecord record = new ListGridRecord();    
		record.setAttribute("nome", nome);  
		record.setAttribute("nick", nick);  
		record.setAttribute("stato", stato);
		
		 
		   
		return record;  
	}  


	public static RecordList setUser(LinkedList<Partecipante> user){
		dataTable = new RecordList();
		for(int i = 0 ; i < user.size(); i++)
			dataTable.add(createRecord(user.get(i).getNome(),user.get(i).getNick(),user.get(i).getStato()));
		return dataTable;
	}


}  