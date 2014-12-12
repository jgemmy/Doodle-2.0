package com.sweng.doodle.shared;

import java.io.Serializable;

public class Commento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6029418623644147281L;
	private String commento;
	private String iduser;
	private String nickName;
	private String date;
	private String idEvento;

	public Commento(){
		this.commento = "no data";
		this.iduser = "no data";
		this.nickName = "no data";
	}

	public Commento(String iduser,String commento){
		this.iduser = iduser;
		this.commento = commento;
		this.nickName = "no data";
	}

	public Commento(String iduser,String commento,String idEvento,String date){
		this.iduser = iduser;
		this.commento = commento;
		this.idEvento = idEvento;
		this.date = date;
	}
	public Commento(String iduser,String nickName,String commento,String idEvento,String date){
		this.iduser = iduser;
		this.nickName = nickName;
		this.commento = commento;
		this.idEvento = idEvento;
		this.date = date;
	}
	
	
	public String getIduser(){
		return iduser;

	}
	

	public String getCommento(){
		return commento;

	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}




}
