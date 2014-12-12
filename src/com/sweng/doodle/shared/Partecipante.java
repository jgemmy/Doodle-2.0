package com.sweng.doodle.shared;

import java.io.Serializable;

public class Partecipante implements Serializable {

	private static final long serialVersionUID = -1595413505827385923L;

	String	idEvento;
	String	nome;
	String	nick;
	String	stato;
	String	disponibilita;

	public Partecipante() {
	}

	public Partecipante(String idEvento, String nome, String nick, String stato, String disponibilita) {
		this.idEvento = idEvento;
		this.nome = nome;
		this.nick = nick;
		this.stato = stato;
		this.disponibilita = disponibilita;
	}

	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(String disponibilita) {
		this.disponibilita = disponibilita;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
