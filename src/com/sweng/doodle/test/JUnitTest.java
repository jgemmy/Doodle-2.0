package com.sweng.doodle.test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Date;

import com.sweng.doodle.server.ServerDoodle;
import com.sweng.doodle.shared.Evento;
import com.sweng.doodle.shared.User;


public class JUnitTest {
	String id1;
	String id2;
	String id3;
	String idevento1;
	String idevento2;
	String idevento3;
	String idCommento1;
	String idCommento2;
	String idCommento3;
	@org.junit.Test
	public void JUnitTest() {
		ServerDoodle test = new ServerDoodle();
		
		testRegistrazione(test);
		testLogin(test);	
		testCreaevento(test);
		test_aggiungi_commento(test);
		cleanDatabase(test);
		/*Testing returned id always primary key*/


	}
	public void testRegistrazione(ServerDoodle test){
		id1 = test.registrazione(new User("gio","gio","pass","aaaaaaa"));
		id2 = test.registrazione(new User("carm","carm","pass","aaaaaaa"));
		id3 = test.registrazione(new User("tic","tac","pass","aaaaaaa"));
		
		//Successo
		//mi aspetto che la procedura vada a buon fine (iscrizione tre utenti)
		assertNotSame(id1, id2);
		assertNotSame(id2, id3);
		assertNotSame(id1, id3);
		String id4 = test.registrazione(new User("gio","gio","pass","aaaaaaa"));
		String id5 = test.registrazione(new User("gio","gio","pass","aaaaaaa"));
		String id6 = test.registrazione(new User("gio","gio","pass","aaaaaaa"));
		//Fallimento
		//mi aspetto l'utente id5 registrato in precedenza (id4)
		assertSame(id4, id5);
		assertSame(id5, id6);
		assertSame(id4, id6);
	}	

	public void testLogin(ServerDoodle test){
		String id1 = test.login(new User("gio", "pass"));
		String id2 = test.login(new User("carm", "pass"));
		String id3 = test.login(new User("tac", "pass"));
		assertNotSame(id1, id2);
		assertNotSame(id2, id3);
		assertNotSame(id1, id3);
		//Successo
		//mi aspetto un id diverso per ogni utente registrato che si logga, 
		//poiche corrisponde all sua primary key 

		String id4 =test.login(new User("carm", "pass"));
		String id5 = test.login(new User("carm", "pass"));
		//Falliento
		//mi aspetto un id uguale per gli utenti id4 e id5 (stesso) che si loggano
		assertSame(id4, id5);
	}
//new Evento( "" ,snome, sluogo, sdescs, ifrom, ito,Doodle_Main.idKey,1,"");
	public void testCreaevento(ServerDoodle test){
		idevento1 = test.caricaEvento(new Evento("", "nome1", "luogo1", "descs1", "Sun Mar 09 00:00:00 CET 2014", "Sun Mar 09 00:00:00 CET 2014", "1", 1, " "));
		idevento2 = test.caricaEvento(new Evento("", "nome2", "luogo1", "descs1", "Sun Mar 09 00:00:00 CET 2014", "Sun Mar 09 00:00:00 CET 2014", "1", 1, " "));
		idevento3 = test.caricaEvento(new Evento("", "nome3", "luogo1", "descs1", "Sun Mar 09 00:00:00 CET 2014", "Sun Mar 09 00:00:00 CET 2014", "1", 1, " "));
		//Successo
		//mi aspetto un id diverso per ogni evento caricato dall utente/amministratore, 
		assertNotSame(idevento1, idevento2);
		assertNotSame(idevento2, idevento3);
		assertNotSame(idevento1, idevento3);
	
	}

	public void test_aggiungi_commento(ServerDoodle test){
		idCommento1 = test.insertcomm(""+idevento1, "commento1", ""+new Date(),""+id1);
		idCommento2 = test.insertcomm(""+idevento2, "commento2", ""+new Date(),""+id2);
		idCommento3 = test.insertcomm(""+idevento3, "commento3", ""+new Date(),""+id3);
	}

	public void cleanDatabase(ServerDoodle test){
		test.freedb();
	}


}
