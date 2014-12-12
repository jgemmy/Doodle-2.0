package com.sweng.doodle.shared;
import java.io.Serializable;

public class Evento implements Serializable {

	private static final long serialVersionUID = 1058023843439640732L;
	private  String id, nome, luogo, descrizione,cause;
	private  String dal,al;
	private int check;
	private String idKey;

//	public Evento (String id, String nome, String luogo,String descrizione, String dal, String al, int check, String cause) {
//		this.id = id;
//		this.nome = nome;
//		this.luogo = luogo;
//		this.descrizione = descrizione;
//		this.dal = dal;
//		this.al = al;
//		this.check = check;
//		this.cause = cause;
//	}
//	
	public Evento (String id, String nome, String luogo,String descrizione, String dal, String al, String idKey,int check, String cause) {
		this.id = id;
		this.nome = nome;
		this.luogo = luogo;
		this.descrizione = descrizione;
		this.dal = dal;
		this.al = al;
		this.idKey = idKey;
		this.check = check;
		this.cause = cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public Evento (){
//		this.id = "no data";
//		this.nome = "no data";
//		this.luogo = "no data";
//		this.descrizione = "no data";
//		this.dal = "no data";
//		this.al = "no data";
//		this.check = 0;
//		this.cause = "nodata";
	}

	@Override
	public String toString(){
		return "id "+id+" nome: "+nome;
	}


	public String getID() {
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
	public String getNome() {
		return nome;
	}

	public String getLuogo() {
		return luogo;
	}

	public String getDesc() {
		return descrizione;
	}

	public String getDal() {
		return dal;
	}

	public String getAl() {
		return al;
	}
	public int getCheck() {
		return check;
	}
	public String getCause() {
		return cause;
	}

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

}
