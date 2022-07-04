package it.unibo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import unibo.comm22.utils.CommUtils;

public class TestApplExt {

	@Before
	public void up() {
		System.out.println("Avvio test");
  	}
	
	protected void waitForApplStarted(){
		ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
		while( wasteservice == null ){
			System.out.println("Attendo applicazione ... ");
			CommUtils.delay(200);
			wasteservice = QakContext.Companion.getActor("wasteservice");
		}
	}
	
	@After
	public void down() {
		System.out.println("FINE TEST ");
	}

	@Test
	public void testLoadacceptReject() {
		System.out.println("TEST STARTS");
 		String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8050);
			String answer     = connTcp.request(truckRequestStr);
			System.out.println("Risposta=" + answer );
			assertTrue(answer.contains("loadaccept"));
			//richiesta da rifiutare
			truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,9),2)";
			answer     = connTcp.request(truckRequestStr);
			System.out.println("Risposta=" + answer );
			connTcp.close();
			assertTrue(answer.contains("loadrejected"));
		}catch(Exception e){
			System.out.println("testLoadok ERROR:" + e.getMessage());

		}
		
 	}
	

}
