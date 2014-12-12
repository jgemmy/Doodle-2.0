package com.sweng.doodle.client;

import java.util.LinkedList;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.sweng.doodle.shared.Commento;


public class CommentGridData {  

	private static RecordList dataTable = new RecordList();

	public static RecordList getRecords(LinkedList<Commento> dataTable) {  
		return setCommento(dataTable);

	}    

	public static ListGridRecord createRecord(String id,String nickname,String commento) {  
		ListGridRecord record = new ListGridRecord();    
		record.setAttribute("id", id);  
		record.setAttribute("nickname", nickname);  
		record.setAttribute("commento", commento);



		return record;  
	}  


	public static RecordList setCommento(LinkedList<Commento> commento){
		dataTable = new RecordList();
		for(int i = 0 ; i < commento.size(); i++)
			dataTable.add(createRecord(commento.get(i).getIduser(),commento.get(i).getNickName(),commento.get(i).getCommento()));
		return dataTable;
	}


}  