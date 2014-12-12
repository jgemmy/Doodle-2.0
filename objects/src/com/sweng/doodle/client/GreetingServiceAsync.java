package com.sweng.doodle.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.User;


/**
 * The async counterpart of <code>GreetingService</code>.
 */

	public interface GreetingServiceAsync {
		void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
		void registrazione(String nome, String nick, String passw,String mail, AsyncCallback<String> callback) throws IllegalArgumentException;
		void login(String nome, String passw, AsyncCallback<String> callback) throws IllegalArgumentException;
		void GetNick(String id, AsyncCallback<String> callback) throws IllegalArgumentException;
		void GetNome(String id, AsyncCallback<String> callback) throws IllegalArgumentException;
//		void getUserInfo(String id, AsyncCallback<LinkedList<User>> callback) throws IllegalArgumentException;;
		void caricaevento(String snome, String sluogo, String sdescs, String ifrom,String ito,String idKey, int check, String cause, AsyncCallback<String> callback);
		void cancellaevento(String id,String idKey, AsyncCallback<String> callback) throws IllegalArgumentException;
		void chiudievento(String id,String idKey,String commento, AsyncCallback<String> callback) throws IllegalArgumentException;
		void insertcomm(String idEvento, String commento, String il,String iduser, AsyncCallback<String> callback) throws IllegalArgumentException;
		void getAllCommenti(String idevento, AsyncCallback<LinkedList<Commento>> callback ) throws IllegalArgumentException;
		void insertJoin(String idEvento, String nome, String nick, String stato,int disp, String idKey, AsyncCallback<String> callback);
		void deleteJoin(String idEvento, String nome, AsyncCallback<String> callback);
		void getAllUsersJoin(String idEvento, AsyncCallback<LinkedList<User>> callback);
		void getAllUserEvents(String id,AsyncCallback<LinkedList<Evento>> callback);
		void getAllEvents(AsyncCallback<LinkedList<Evento>> callback);
	}

