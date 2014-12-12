package com.sweng.doodle.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.sweng.doodle.shared.Commento;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.User;

public class QueryMethods {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/test";
	String NOMECOGNPASSW = "SELECT nome, nick, password,mail from users";
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";
	static final String TABLENAME = "users";
	static final String TABLENAME2 = "eventi";
	static final String TABLENAME3 = "partecipanti";
	static final String TABLENAME4 = "commenti";

	/*__________________________________________________ConnessioneDB_____________________________________________________*/

	public static Statement connect(Connection conn) {

		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			//STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();



			//		      insertUser(stmt, "Carmelo", "De Paperinis", "ndoCulo");
			//		      deleteUser(stmt, "Carmelo", "De Paperinis", "ndoCulo");
			//		      deleteIdUser(stmt, 0);

			System.out.println("Created table in given database...");

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		//finally block used to close resources
		return stmt;

	}//end main

	public static void close(Statement stmt,Connection conn){
		try{
			if(stmt!=null)
				conn.close();
		}catch(SQLException se){
		}// do nothing
		try{
			if(conn!=null)
				conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}//end finally try
		//end try
		System.out.println("Goodbye!");
	}


	/*__________________________________________________USERS_____________________________________________________*/


	public static void   creatabella(Statement statement, String tablename) throws SQLException{
		String createTable = "CREATE TABLE IF NOT EXISTS "+tablename +
				" (id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT , " +
				" nome VARCHAR(255), " + 
				" nick VARCHAR(255), " +
				" mail VARCHAR(255), " +
				" password VARCHAR(255))"; 
		statement.executeUpdate(createTable);
		System.out.println("Created Users table in given database...");
	}
	/*REGISTRAZIONE UTENTE*/

	public static String checkExNick(Statement statement) throws SQLException{
		String select = "SELECT nick FROM "+TABLENAME;
		String nick = "";
		ResultSet rs = statement.executeQuery(select);
		while (rs.next()) {

			nick = rs.getString("nick");
			System.out.println("disponibilita: " +nick);

		}
		return nick;
	}
	public static void insertUser(Statement statement, String nome, String nick, String password,String mail) throws SQLException{
		String insertTableSQL = "INSERT INTO "+TABLENAME
				+ "(nome, nick, password,mail) " + "VALUES"
				+ "('"+nome+"','"+nick+"','"+password+"', '"+mail+"')";
		statement.executeUpdate(insertTableSQL);   
		System.out.println("Created User in given database...Utente Registrato");
	}

	public static String login(Statement statement, String nick, String passw) throws SQLException{
		String get = "SELECT * FROM "+TABLENAME + " WHERE password = '"+passw+"' AND nick = '"+nick+"' ";

		String userid = "empty";
		ResultSet rs = statement.executeQuery(get);
		while (rs.next()) {

			userid = rs.getString("id");
			System.out.println("Loggato id : " + userid);

		}
		return userid;
	}


	public static void getAllInformationsUsers(Statement statement) throws SQLException{
		String get = "SELECT nome,nick,password,mail FROM "+TABLENAME;
		ResultSet rs = statement.executeQuery(get);
		while (rs.next()) {

			String userid = rs.getString("nome");
			String username = rs.getString("nick");
			String password = rs.getString("password");
			String mail = rs.getString("mail");
			System.out.println("nome : " + userid);
			System.out.println("nick : " + username);
			System.out.println("password : " + password);
			System.out.println("mail : " + mail);
		}
	}

	public static void deleteUser(Statement statement, String nome, String nick, String password,String mail) throws SQLException{
		String removeUser = "DELETE FROM "+TABLENAME+" WHERE nome = '"+nome+"' AND nick = '"+nick+"' AND password = '"+password+"' AND mail = '"+mail+"'";
		statement.executeUpdate(removeUser);   
	}

	public static void deleteIdUser(Statement statement, int id) throws SQLException{
		String removeUser = "DELETE FROM "+TABLENAME+" WHERE id = "+id;
		statement.executeUpdate(removeUser);
		System.out.println("Utente Rimosso ID: " + id);
	}

	public static void changepassword(Statement stmt,String password) throws SQLException{


		String changepsw = "SET PASSWORD FOR 'root'@'localhost' = PASSWORD('"+password+"');";
		stmt.executeUpdate(changepsw);
	}

	public static String GetNick(Statement statement,String id) throws SQLException{
		String nick = "";
		String select = "SELECT nick FROM "+TABLENAME+" WHERE id = "+id;
		ResultSet rs = statement.executeQuery(select);
		while (rs.next()) {

			nick = rs.getString("nick");

		}
		return nick;
	}

	public static String GetNome(Statement statement,String id) throws SQLException{
		String nome = "";
		String select = "SELECT nome FROM "+TABLENAME+" WHERE id = "+id;
		ResultSet rs = statement.executeQuery(select);
		while (rs.next()) {

			nome = rs.getString("nome");
			System.out.println("Getting User Info");

		}
		return nome;
	}




	/*__________________________________________________EVENTI_____________________________________________________*/

	public static void creatabellaeventi(Statement statement ) throws SQLException{
		String createTablee = "CREATE TABLE IF NOT EXISTS "+ TABLENAME2 +
				"(id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT , " +
				" idKey INTEGER, " +
				" nome VARCHAR(255), " +
				" luogo VARCHAR(255), " +
				" descrizione VARCHAR(255), " +
				" dal DATETIME, " +								
				" al DATETIME, " +
				" checks INTEGER, " +
				" causechiuso VARCHAR(255)) ";
		statement.executeUpdate(createTablee);
		System.out.println("Created Eventi table in given database...");
	}

	public static String insertEvent(Statement statement, String nome, String luogo, String descrizione,String dal, String al,String idKey, int check, String cause) throws SQLException{
		//		Sun Mar 09 00:00:00 CET 2014
		StringTokenizer token = new StringTokenizer(dal, " ");
		token.nextToken();
		String month = token.nextToken();
		String day =  token.nextToken();
		String hour = token.nextToken();
		token.nextToken();
		String year = token.nextToken();
		String dateDal = day+","+month+","+year+" "+hour;
		//		System.out.println(dateDal);
		StringTokenizer token1 = new StringTokenizer(al, " ");
		token1.nextToken();
		String month1 = token1.nextToken();
		String day1 =  token1.nextToken();
		String hour1 = token1.nextToken();
		token1.nextToken();
		String year1 = token1.nextToken();
		String dateAl = day1+","+month1+","+year1+" "+hour1;
		//		System.out.println(dateAl);
		String insertTableSQL = "INSERT INTO "+ TABLENAME2
				+ "(nome, luogo, descrizione, dal, al,idKey, checks, causechiuso) " + "VALUES"
				+ "('"+nome+"','"+luogo+"','"+descrizione+"',STR_TO_DATE('"+dateDal+"', '%d,%b,%Y %H:%i:%s'),  STR_TO_DATE('"+dateAl+"', '%d,%b,%Y %T'), '"+idKey+"', '"+check+"', '"+cause+"')";
		statement.executeUpdate(insertTableSQL);
		String get = "SELECT id FROM "+TABLENAME2 + " WHERE nome = '"+nome+"' ";

		String userid = "empty";
		ResultSet rs = statement.executeQuery(get);
		while (rs.next()) {

			userid = rs.getString("id");
			System.out.println("Evento Inserito ID: " + userid);

		}
		return userid;
	}

	public static String deleteIdEvent(Statement statement, String id,String idKey) throws SQLException{

		String removeEvent = "DELETE FROM "+TABLENAME2+" WHERE idKey = "+idKey+" AND id = "+id;
		String eventid = id ;
		statement.executeUpdate(removeEvent);
		System.out.println("Evento Rimosso ID: " + id);

		return eventid;

	}

	public static String closeIdEvent(Statement statement, String id,String idKey,String commento) throws SQLException{

		String closeEvent = "UPDATE "+TABLENAME2+" SET checks = 0 ,causechiuso = '"+commento+"' WHERE idKey = "+idKey+" AND id = "+id;
		String eventid = id ;
		statement.executeUpdate(closeEvent);
		System.out.println("Evento Chiuso ID: " + id);

		return eventid;

	}


	public static String isCloseIdEvent(Statement statement, String id) throws SQLException{
		String check = "";
		String isCloseEvent = "SELECT checks FROM "+TABLENAME2+" WHERE id = "+id;
		ResultSet rs = statement.executeQuery(isCloseEvent);
		while(rs.next()){
			check = rs.getString("checks");
			System.out.println(check);
		}
		return check;

	}




	public static LinkedList<Evento> getAllEvents(Statement statement){
		LinkedList<Evento> list = new LinkedList<Evento>();
		String select = "SELECT * FROM "+TABLENAME2;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(select);
			while(rs.next()) {
				System.out.println("Adding All Events");
				list.add(new Evento(rs.getString(1), rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getInt(8),rs.getString(9)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static LinkedList<Evento> getAllUserEvents(Statement statement, String idkey){
		LinkedList<Evento> list = new LinkedList<Evento>();
		String select = "SELECT * FROM "+TABLENAME2 + " WHERE idKey = '"+idkey+"' ;";
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(select);
			while(rs.next()) {
				System.out.println("Adding User Events");
				list.add(new Evento(rs.getString(1), rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getInt(8),rs.getString(9)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("catch");
		}
		System.out.println("list size: "+list.size());
		return list;
	}




	/*__________________________________________________PARTECIPANTI_______________________________________________*/


	public static void   creatabellapartecipanti(Statement statement ) throws SQLException{
		String createTablee = "CREATE TABLE IF NOT EXISTS "+ TABLENAME3 +
				"(id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT , " +
				" idEvento VARCHAR(255), " +
				" nome VARCHAR(255), " +
				" nick VARCHAR(255), " +
				" stato VARCHAR(255), " +
				" disponibilita INTEGER) "; 
		statement.executeUpdate(createTablee);
		System.out.println("Created Partecipanti table in given database...");
	}



	public static LinkedList<User> getAllUsersJoin(Statement statement, String idEvento){
		LinkedList<User> list = new LinkedList<User>();	
		String select = "SELECT nome,nick,stato FROM "+TABLENAME3+" WHERE idEvento = "+idEvento;
		//		String select = "SELECT "+TABLENAME3+".nome, "+TABLENAME3+".nick, "+TABLENAME3+".stato, "+TABLENAME4+".commento " +
		//	             "FROM "+TABLENAME3+" INNER JOIN "+TABLENAME4+" " +
		//	             "ON "+TABLENAME3+".idEvento = "+TABLENAME4+".idevento " + 
		//	             "WHERE "+TABLENAME3+".idEvento = '"+idEvento+"' ";
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(select);
			while(rs.next()) {
				System.out.println("Adding All Joiners");
				list.add(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
			}
		} catch (SQLException e) {
			//			e.printStackTrace();
		}
		return list;
	}

	public static String insertJoin(Statement statement, String idEvento, String nome, String nick, String stato,int disp) throws SQLException{
		String name = nome;
		String insert = "INSERT INTO "+ TABLENAME3
				+ "(idEvento, nome, nick, stato, disponibilita) " + "VALUES"
				+ "('"+idEvento+"','"+nome+"','"+nick+"','"+stato+"','"+disp+"')";
		statement.executeUpdate(insert);
		System.out.println("Created join in given database...Utente inscritto all evento");
		return name;
	}

	//	public static String checkInsertJoin(Statement statement,String idEvento,String idKey) throws SQLException{
	//		String disp= "-1";
	//		String check = "select "+TABLENAME3+".disponibilita " +
	//	             "from "+TABLENAME2+", "+TABLENAME3+" " +
	//	             "where "+idEvento+" like "+TABLENAME2+".id and " +
	//	             ""+TABLENAME2+".idKey = '"+idKey+"'";
	//
	//		System.out.println("query: " +check);
	//		ResultSet rs = statement.executeQuery(check);
	//		while (rs.next()) {
	//
	//			disp = ""+rs.getInt("disponibilita");
	//			System.out.println("disponibilita: " +disp);
	//
	//		}
	//		return disp;
	//	}
	public static String checkInsertJoin(Statement statement,String idEvento,String idKey) throws SQLException{
		String disp= "-1";
		String check = "SELECT "+TABLENAME3+".disponibilita " +
				"FROM "+TABLENAME2+" INNER JOIN "+TABLENAME3+" " +
				"ON "+TABLENAME2+".id = "+TABLENAME3+".idEvento " + 
				"WHERE "+TABLENAME3+".idEvento = '"+idEvento+"' " +
				"AND "+TABLENAME2+".idKey = '"+idKey+"'";

		ResultSet rs = statement.executeQuery(check);
		while (rs.next()) {

			disp = ""+rs.getInt("disponibilita");
			System.out.println("disponibilita: " +disp);

		}
		return disp;
	}


	public static String deleteJoin(Statement statement, String idEvento, String nome) throws SQLException{
		String name = nome;
		String remove = "DELETE FROM "+TABLENAME3+" WHERE idEvento = '"+idEvento+"' AND nome = '"+nome+"' ;";
		statement.executeUpdate(remove);   
		System.out.println("Deleted join in given database...Utente ritirato dall evento");
		return name;
	}


	/*__________________________________________________EVENTI_____________________________________________________*/

	public static void creatabellacommenti(Statement statement) throws SQLException{
		String createTablee = "CREATE TABLE IF NOT EXISTS "+ TABLENAME4 +
				"(id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT , " +
				" idevento VARCHAR(255), " +
				" iduser VARCHAR(255), " +
				" commento VARCHAR(255), " +
				" il DATETIME) ";
		statement.executeUpdate(createTablee);
		System.out.println("Created Commenti table in given database...");
	}

	public static String insertCommento(Statement statement, String idPart, String commento, String il,String iduser) throws SQLException{
		String idPar = idPart;
		StringTokenizer token = new StringTokenizer(il, " ");
		token.nextToken();
		String month = token.nextToken();
		String day =  token.nextToken();
		String hour = token.nextToken();
		token.nextToken();
		String year = token.nextToken();
		String dateil = day+","+month+","+year+" "+hour;
		String insertTableSQL = "INSERT INTO "+TABLENAME4
				+ "(idevento, commento, il,iduser) " + "VALUES"
				+ "('"+idPart+"','"+commento+"',STR_TO_DATE('"+dateil+"', '%d,%b,%Y %H:%i:%s'),'"+iduser+"')";	
		statement.executeUpdate(insertTableSQL);   
		System.out.println("Created Commento in given database...Commento Registrato");
		return idPar;
	}

	public static LinkedList<Commento> getAllCommenti(Statement statement, String idevento){
		LinkedList<Commento> list = new LinkedList<Commento>();	
		String select = "SELECT iduser,commento,il FROM "+TABLENAME4+" WHERE idEvento = "+idevento;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(select);
			while(rs.next()) {
				System.out.println("Adding All Comments");
				list.add(new Commento(rs.getString(1),rs.getString(2)+" - Aggiunto il "+rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public static  LinkedList<Commento> getAllnickName(Statement statement,LinkedList<Commento> commenti){

		ResultSet rs = null;
		try {

			for(int k = 0 ; k < commenti.size(); k++){
				rs = statement.executeQuery("SELECT nome,nick FROM "+TABLENAME+" WHERE id = "+commenti.get(k).getIduser());
				System.out.println("Adding Comment");
				while(rs.next())
					commenti.get(k).setNickName(rs.getString(1)+" - "+rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return commenti;

	}



}

