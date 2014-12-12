package com.sweng.doodle.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.Partecipante;
import com.sweng.doodle.shared.User;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
//	String greetServer(String name) throws IllegalArgumentException;
	String registrazione(User utente) throws IllegalArgumentException;
	String login(User utente) throws IllegalArgumentException;

	String GetNome(String id) throws IllegalArgumentException;
	String caricaEvento(Evento event) throws IllegalArgumentException;
	String cancellaevento(String id,String idKey) throws IllegalArgumentException;
	String chiudievento(String id,String idKey,String commento) throws IllegalArgumentException;
	String insertJoin(Partecipante part) throws IllegalArgumentException;
	String insertcomm(String idEvento, String commento, String il,String iduser) throws IllegalArgumentException;
	String deleteJoin(String idEvento, String nome)throws IllegalArgumentException;
	LinkedList<Commento> getAllCommenti(String idevento) throws IllegalArgumentException;
	LinkedList<Partecipante> getAllUsersJoin(String idEvento) throws IllegalArgumentException;
	LinkedList<Evento> getAllUserEvents(String id) throws IllegalArgumentException;
	LinkedList<Evento> getAllEvents() throws IllegalArgumentException;
}
