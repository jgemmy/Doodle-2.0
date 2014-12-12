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
	
	public String getIduser(){
		return iduser;
		
	}
	
	public String getCommento(){
		return commento;
		
	}
	
	public String getNickName(){
		return nickName;
		
	}
	
	public void setNickName(String nickname){
		this.nickName = nickname;
	}
	
	
}
