package com.sweng.doodle.shared;


import java.io.Serializable;

public class User implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1638298745965991471L;
	private  String id;
	private String nome;
	private String nick;
	private String mail;
	private String passw;


	
	public User (String id, String nome, String nick,String mail, String passw) {
		this.id = id;
		this.nome = nome;
		this.nick = nick;
		this.mail = mail;
		this.passw = passw;
	}

	
	public User (){
		this.id = "no data";
		this.nome = "no data";
		this.nick = "no data";
		this.mail = "no data";
		this.passw = "no data";
	
	}
	
	public String getID() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getNick() {
		return nick;
	}

	public String getMail() {
		return mail;
	}

	public String getPass() {
		return passw;
	}

	
}
