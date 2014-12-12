package com.sweng.doodle.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.User;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl {

	
	/*_____________________________________________UTENTI______________________________________________________________________________ */
	 
//	public String registrazione(String nome, String nick, String password,String mail)
//			throws IllegalArgumentException {
//
//		Connection conn = null;
//		Statement statement = null;
//		@SuppressWarnings("unused")
//		String returned = "";
//
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
//			statement = conn.createStatement();
//			QueryMethods.creatabella(statement, QueryMethods.TABLENAME);
//			if(QueryMethods.checkExNick(statement,nick).contentEquals(nick)){
//				return returned = "Username gia impegnato";
//			} else QueryMethods.insertUser(statement, nome, nick, password,mail);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return returned = nick+": non registrato";
//		} catch (ClassNotFoundException e) {	
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		QueryMethods.close(statement, conn);
//		return returned = nick;
//	}

	 
//	public String login(String nick, String passw)
//			throws IllegalArgumentException {
//		Connection conn = null;
//		Statement statement = null;
//		String returned = "empty";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
//			statement = conn.createStatement();
//			//			QueryMethods.creatabella(statement, QueryMethods.TABLENAME);
//			returned= QueryMethods.login (statement, nick, passw);
//
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "non registrato";
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		QueryMethods.close(statement, conn);
//		return returned;
//	}

	 
//	public String GetNick(String id) throws IllegalArgumentException {
//		Connection conn = null;
//		Statement statement = null;
//		String returned = "";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
//			statement = conn.createStatement();
//			returned = QueryMethods.GetNick(statement, id);
//		} catch (SQLException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return returned;
//
//	}

	 
//	public String GetNome(String id) throws IllegalArgumentException {
//		Connection conn = null;
//		Statement statement = null;
//		String returned = "";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
//			statement = conn.createStatement();
//			returned = QueryMethods.GetNome(statement, id);
//		} catch (SQLException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return returned;
//
//	}


	//	 
	//	public LinkedList<User> getUserInfo(String id)
	//			throws IllegalArgumentException {
	//		Connection conn = null;
	//		Statement statement = null;
	//
	//		try {
	//			Class.forName("com.mysql.jdbc.Driver");
	//			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
	//			statement = conn.createStatement();
	//		} catch (SQLException | ClassNotFoundException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//		return QueryMethods.getUserInfo(statement, id);		
	//
	//	}

	/*_____________________________________________EVENTI______________________________________________________________________________ */

	 
	public String caricaevento(String nome, String luogo, String descs,	String dal, String al,String idKey, int check, String cause) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement statement = null;
		String returned ="id non esistente";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			QueryMethods.creatabellaeventi(statement);
			QueryMethods.creatabellacommenti(statement);
			returned = 	QueryMethods.insertEvent(statement, nome, luogo, descs, dal, al,idKey, check, cause);



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Evento non caricato";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return returned ;
	}

	 
	public String cancellaevento(String id,String idKey) throws IllegalArgumentException {
		// TODO Auto-generated method stubss
		Connection conn = null;
		Statement statement = null;
		String returned = id;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			returned = QueryMethods.deleteIdEvent(statement, id,idKey);



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Evento non cancellato";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return "evento cancellato "
		+ returned;
	}


	 
	public String chiudievento(String id, String idKey, String commento)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		String returned = id;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			if (QueryMethods.isCloseIdEvent(statement, id).contentEquals("0")){
				returned = "evento gia chiuso";} 
			else returned = QueryMethods.closeIdEvent(statement, id,idKey,commento);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Evento non chiuso";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);		return  returned;	}

	 	public LinkedList<Evento> getAllUserEvents(String id)  {		Connection conn = null;		Statement statement = null;

		try {			Class.forName("com.mysql.jdbc.Driver");			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);			statement = conn.createStatement();		} catch (SQLException | ClassNotFoundException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}
		return QueryMethods.getAllUserEvents(statement,id);		
	}
	 	public LinkedList<Evento> getAllEvents() throws IllegalArgumentException {		Connection conn = null;		Statement statement = null;

		try {			Class.forName("com.mysql.jdbc.Driver");			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);			statement = conn.createStatement();			QueryMethods.creatabellaeventi(statement);		} catch (SQLException | ClassNotFoundException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}
		return QueryMethods.getAllEvents(statement);		
	}



	 
	public String insertJoin(String idEvento,String nome, String nick, String stato, int disp,String idKey)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			QueryMethods.creatabellacommenti(statement);
			QueryMethods.creatabellapartecipanti(statement);
			//				System.out.println(QueryMethods.checkInsertJoin(statement, idEvento, id));
			//				
			if(idKey.contentEquals("-2")){
				QueryMethods.insertJoin(statement, idEvento, nome, nick, stato, disp);
			return "Utente Anonimo Iscritto";
			}
			if(QueryMethods.checkInsertJoins(statement, idEvento,idKey).contentEquals("1")){
				return " Fallita: Gia Inscritto all evento";
			}
			else
			QueryMethods.insertJoin(statement, idEvento, nome, nick, stato, disp);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "non Inscritto";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return "fatto";
	}

	


	 
	public String deleteJoin(String idEvento, String nome)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		String returned = nome;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			returned = QueryMethods.deleteJoin(statement, idEvento, nome);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Disponibilita non revocata";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return returned+ ": Disponibilta revocata";
	}



	 
	public LinkedList<User> getAllUsersJoin(String idEvento)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;


		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			QueryMethods.creatabellapartecipanti(statement);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return QueryMethods.getAllUsersJoin(statement, idEvento);

	}
	
	 
	public String insertcomm(String idPart,String commento, String il,String iduser)throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		String returned = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			QueryMethods.creatabellacommenti(statement);
			returned = QueryMethods.insertCommento(statement, idPart, commento, il,iduser);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Commento non registrato";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return  "Commento Registrato id evento: "+returned;
	}

	 
	public LinkedList<Commento> getAllCommenti(String idevento)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		LinkedList<Commento> commenti = new LinkedList<Commento>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			commenti = QueryMethods.getAllCommenti(statement,idevento);
			commenti = QueryMethods.getAllnickName(statement,commenti);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commenti;
	}


public void rimuoviEvento(String idEvento){
	Connection conn = null;
	Statement statement = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
		statement = conn.createStatement();
		QueryMethods.deleteIdEvent(statement, idEvento);
		}
	catch(Exception e){
		
	}
}
public void rimuoviCommento(String idUser){
	Connection conn = null;
	Statement statement = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
		statement = conn.createStatement();
		QueryMethods.deleteCommento(statement, idUser);
		}
	catch(Exception e){
		
	}
}


public void rimuoviUtente(String idUser){
	Connection conn = null;
	Statement statement = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
		statement = conn.createStatement();
		QueryMethods.deleteUtente(statement, idUser);
		}
	catch(Exception e){
		
	}
}
}
