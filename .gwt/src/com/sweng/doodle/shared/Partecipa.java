package com.sweng.doodle.shared;

import java.io.Serializable;



public class Partecipa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3174431754706973487L;
	private  String id, nome, nick, commento;
	private  int disp;

	public Partecipa (String id, String nome, String nick, String commento, int disp) {
		this.id = id;
		this.nome = nome;
		this.nick = nick;
		this.commento = commento;
		this.disp = disp;

	}
	
	public Partecipa () {
		this.id = "nodata";
		this.nome = "nodata";
		this.nick = "nodata";
		this.commento = "nodata";
		this.disp = 0;

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

	public String getCommento() {
		return commento;
	}

	public int getDisp() {
		return disp;
	}
}
