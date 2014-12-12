package com.sweng.doodle.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sweng.doodle.client.GreetingService;
import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.FieldVerifier;
import com.sweng.doodle.shared.User;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	/*_____________________________________________UTENTI______________________________________________________________________________ */
	@Override
	public String registrazione(String nome, String nick, String password,String mail)
			throws IllegalArgumentException {

		Connection conn = null;
		Statement statement = null;
		@SuppressWarnings("unused")
		String returned = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			QueryMethods.creatabella(statement, QueryMethods.TABLENAME);
			if(QueryMethods.checkExNick(statement).contentEquals(nick)){
				return returned = nick+": Username gia impegnato";
			} else QueryMethods.insertUser(statement, nome, nick, password,mail);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return returned = nick+": non registrato";
		} catch (ClassNotFoundException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return returned = nick+": Registazione Effettuata";
	}

	@Override
	public String login(String nick, String passw)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		String returned = "empty";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			//			QueryMethods.creatabella(statement, QueryMethods.TABLENAME);
			returned= QueryMethods.login (statement, nick, passw);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "non registrato";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryMethods.close(statement, conn);
		return returned;
	}

	@Override
	public String GetNick(String id) throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		String returned = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			returned = QueryMethods.GetNick(statement, id);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returned;

	}

	@Override
	public String GetNome(String id) throws IllegalArgumentException {
		Connection conn = null;
		Statement statement = null;
		String returned = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);
			statement = conn.createStatement();
			returned = QueryMethods.GetNome(statement, id);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returned;

	}


	//	@Override
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

	@Override
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


	@Override
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

	@Override	public LinkedList<Evento> getAllUserEvents(String id)  {		Connection conn = null;		Statement statement = null;

		try {			Class.forName("com.mysql.jdbc.Driver");			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);			statement = conn.createStatement();		} catch (SQLException | ClassNotFoundException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}
		return QueryMethods.getAllUserEvents(statement,id);		
	}
	@Override	public LinkedList<Evento> getAllEvents() throws IllegalArgumentException {		Connection conn = null;		Statement statement = null;

		try {			Class.forName("com.mysql.jdbc.Driver");			conn =  DriverManager.getConnection(QueryMethods.DB_URL, QueryMethods.USER, QueryMethods.PASS);			statement = conn.createStatement();			QueryMethods.creatabellaeventi(statement);		} catch (SQLException | ClassNotFoundException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}
		return QueryMethods.getAllEvents(statement);		
	}



	@Override
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
			if(QueryMethods.checkInsertJoin(statement, idEvento,idKey).contentEquals("1")){
				return " Fallita: Gia Inscritto all evento";
			}
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

	


	@Override
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



	@Override
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

	@Override
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






}
