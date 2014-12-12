package com.sweng.doodle.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.User;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	String registrazione(String nome, String nick, String password, String mail) throws IllegalArgumentException;
	String login(String nick, String passw) throws IllegalArgumentException;
//	LinkedList<User> getUserInfo(String id) throws IllegalArgumentException;
	String GetNick(String id) throws IllegalArgumentException;
	String GetNome(String id) throws IllegalArgumentException;
	String caricaevento(String nome, String luogo, String descs,String dal,String al,String idKey, int check, String cause) throws IllegalArgumentException;
	String cancellaevento(String id,String idKey) throws IllegalArgumentException;
	String chiudievento(String id,String idKey,String commento) throws IllegalArgumentException;
	String insertJoin(String idEvento, String nome, String nick, String stato,int disp,String idKey) throws IllegalArgumentException;
	String insertcomm(String idEvento, String commento, String il,String iduser) throws IllegalArgumentException;
	LinkedList<Commento> getAllCommenti(String idevento) throws IllegalArgumentException;
	String deleteJoin(String idEvento, String nome)throws IllegalArgumentException;
	LinkedList<User> getAllUsersJoin(String idEvento) throws IllegalArgumentException;
	LinkedList<Evento> getAllUserEvents(String id) throws IllegalArgumentException;
	LinkedList<Evento> getAllEvents() throws IllegalArgumentException;
}
