package com.sweng.doodle.server;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sweng.doodle.client.GreetingService;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.Partecipante;
import com.sweng.doodle.shared.User;

public class ServerDoodle extends RemoteServiceServlet implements
GreetingService {
	private static final long serialVersionUID = 1L;
	ConcurrentNavigableMap<String,User> users;
	ConcurrentNavigableMap<String,Evento> eventi;
	ConcurrentNavigableMap<String,String> preferenze;
	ConcurrentNavigableMap<String,Partecipante> partecipanti;
	ConcurrentNavigableMap<String,Commento> commenti;
	DB db;
	public void openDb(){
		db = DBMaker.newFileDB(new File("testdb")).closeOnJvmShutdown().encryptionEnable("password").make();
		preferenze = db.getTreeMap("preferenze");
		users = db.getTreeMap("users");
		eventi = db.getTreeMap("eventi");
		partecipanti = db.getTreeMap("partecipanti");
		commenti = db.getTreeMap("commenti");
	}



	public String incrementAndGetUnivoqueIdPartecipant(){
		openDb();
		if(!preferenze.containsKey("partecipantId")){
			//inserisco il primo id nel caso in cui non esiste, successivamente lo incremento soltanto
			preferenze.put("partecipantId", "1");
			db.commit();
			return "1";
		}
		else {
			String temp =  preferenze.get("partecipantId");
			int valueTemp = Integer.parseInt(temp);
			valueTemp++;
			preferenze.put("partecipantId", ""+valueTemp);
			db.commit();
			return ""+valueTemp;
		}
	}

	public String incrementAndGetUnivoqueIdEvent(){
		openDb();
		if(!preferenze.containsKey("eventId")){
			//inserisco il primo id nel caso in cui non esiste, successivamente lo incremento soltanto
			preferenze.put("eventId", "1");
			db.commit();
			return "1";
		}
		else {
			String temp =  preferenze.get("eventId");
			int valueTemp = Integer.parseInt(temp);
			valueTemp++;
			preferenze.put("eventId", ""+valueTemp);
			db.commit();
			return ""+valueTemp;
		}
	}

	@Override
	public String registrazione(User utente) throws IllegalArgumentException {
		openDb();
		if(users.containsKey(utente.getNick()))
			return "Utente esistente";
		else 
			users.put(utente.getNick(), utente);
		db.commit();
		return utente.getNick();
	}



	@Override
	public LinkedList<Commento> getAllCommenti(String idevento) throws IllegalArgumentException {
		// CONTROLLARE ultimaaaaaaaaaaaaa
		openDb();
		LinkedList<Commento> events = new LinkedList<>();
		for (Map.Entry<String,Commento> entry : commenti.entrySet()) {
			String key = entry.getKey();
			Commento value = entry.getValue();
			if(value.getIdEvento().contentEquals(idevento))
				events.add(value);

		}
		return events;

	}

	@Override
	public LinkedList<Partecipante> getAllUsersJoin(String idEvento) throws IllegalArgumentException {
		openDb();
		LinkedList<Partecipante> temp = new LinkedList<Partecipante>();
		//		System.out.println(" richiesto: "+idEvento+partecipanti.size());
		for (Map.Entry<String,Partecipante> entry : partecipanti.entrySet()) {
			Partecipante value = entry.getValue();
			//			System.out.println(value.getIdEvento()+" richiesto: "+idEvento);
			if(value.getIdEvento().contentEquals(idEvento)){
				temp.add(value);
			}
		}
		return temp;
	}

	@Override
	public LinkedList<Evento> getAllUserEvents(String nick) throws IllegalArgumentException {
		openDb();
		LinkedList<Evento> events = new LinkedList<>();
		for (Map.Entry<String,Evento> entry : eventi.entrySet()) {
			String key = entry.getKey();
			Evento value = entry.getValue();
			//			System.out.println(value.getIdKey());
			if(value.getIdKey().contentEquals(nick)){
				events.add(value);
			}
		}
		return events;
	}

	@Override
	public LinkedList<Evento> getAllEvents() throws IllegalArgumentException {
		// CONTROLLARE
		openDb();
		LinkedList<Evento> events = new LinkedList<>();
		for (Map.Entry<String,Evento> entry : eventi.entrySet()) {
			String key = entry.getKey();
			Evento value = entry.getValue();
			events.add(value);

		}
		return events;
	}



	@Override
	public String login(User utente) throws IllegalArgumentException {
		openDb();
		//		System.out.println(users.size());
		if(users.containsKey(utente.getNick())){
			if(users.get(utente.getNick()).getPass().contentEquals(utente.getPass()))
				return utente.getNick();
		}
		return "empty";
	}

	@Override
	public String caricaEvento(Evento event) throws IllegalArgumentException {
		openDb();
		event.setId(incrementAndGetUnivoqueIdEvent());
		eventi.put(event.getID(), event);
		db.commit();
		return event.getID();
	}

	@Override
	public String insertJoin(Partecipante part) throws IllegalArgumentException {
		openDb();
		//		System.out.println("im in insertjoin part: "+part.getIdEvento()+part.getNick()+part.getDisponibilita());

		for (Map.Entry<String,Partecipante> entry : partecipanti.entrySet()) {
			String key = entry.getKey();
			Partecipante value = entry.getValue();
			//			System.out.println("im in insertjoin part: "+value.getIdEvento()+value.getNick()+value.getDisponibilita());
			if(value.getDisponibilita().contentEquals("1")){
				if(value.getNick().contentEquals(part.getNick())){
					if(value.getIdEvento().contentEquals(part.getIdEvento())){
						return "Utente già inscritto";
					}	
				}	
			}

		}
		String id = incrementAndGetUnivoqueIdPartecipant();
		if(users.get(part.getNick()) != null)
			part.setNome(users.get(part.getNick()).getNome());
		partecipanti.put(id, part);
		db.commit();
		//		System.out.println(partecipanti.size());
		return "Inscritto partecipante "+part.getNick();
	}



	@Override
	public String cancellaevento(String id, String idKey)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		openDb();
		String i = "";
		LinkedList<Evento> events = new LinkedList<>();
		for (Map.Entry<String,Evento> entry : eventi.entrySet()) {
			String key = entry.getKey();
			Evento value = entry.getValue();
			if( (value.getID().contentEquals(id)) && (value.getIdKey().contentEquals(idKey))){
				eventi.remove(key);
				i = value.getID();
				db.commit();
			}
		}
		return i;
	}



	@Override
	public String chiudievento(String id, String idKey, String commento)
			throws IllegalArgumentException {
		openDb();
		String i = "";
		LinkedList<Evento> events = new LinkedList<>();
		for (Map.Entry<String,Evento> entry : eventi.entrySet()) {
			String key = entry.getKey();
			Evento value = entry.getValue();
			if( (value.getID().contentEquals(id)) && (value.getIdKey().contentEquals(idKey))){
				if(value.getCheck() == 0){return "evento gia chiuso";}
				else{
					value.setCheck(0);
					value.setCause(commento);
					i = value.getID();
					eventi.replace(key, value);
					db.commit();}
			}
		}
		return i;
	}



	@Override
	public String insertcomm(String idEvento, String commento, String il,String iduser) throws IllegalArgumentException {
		openDb();
		commenti.put(il,new Commento(iduser, users.get(iduser).getNome(), commento, idEvento, il));
		db.commit();
		return "commento inserito";
	}



	@Override
	public String GetNome(String nick) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		openDb();
		String i = "";
		LinkedList<User> utenti = new LinkedList<>();
		for (Map.Entry<String,User> entry : users.entrySet()) {
			String key = entry.getKey();
			User value = entry.getValue();
			if(value.getNick().contentEquals(nick)){
				i = value.getNome();}

		}
		return i;
	}



	@Override
	public String deleteJoin(String idEvento, String nome)	throws IllegalArgumentException {
		openDb();
		String result = "nessuna partecipazione trovata!";
		for (Map.Entry<String,Partecipante> entry : partecipanti.entrySet()) {
			String key = entry.getKey();
			Partecipante value = entry.getValue();
			if(value.getIdEvento().contentEquals(idEvento)){
				if(value.getNick().contentEquals(nome)){
					partecipanti.remove(key);
					result = "partecipazione rimossa!";
				}
			}
			db.commit();
		}
		return result;
	}

	public void freedb(){
		openDb();
		if(users != null)
		users.clear();
		if(eventi != null)
		eventi.clear();
		if(commenti != null)
		commenti.clear();
		db.commit();
	}



}