package it.unibo;

import static org.junit.Assert.*;

import it.unibo.ctxanalisiproblema.MainCtxanalisiproblemaKt;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import unibo.actor22comm.utils.CommUtils;



public class TestApplSprint2 {
	@Before
	public void up() {
	new Thread(){
			public void run(){
                MainCtxanalisiproblema.main();
			}
		}.start();
		waitForApplStarted();
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
	public void stopAndLedOn() throws Exception {
		 CoapClient client = new CoapClient("coap://localhost:8078/ctxanalisiproblema/trolley");
		 String stop = "msg(stop,dispatch,algise,wasteservice,stop(70),18)";
		 try{
			 ConnTcp connTcp   = new ConnTcp("localhost", 8078);
			 ConnTcp connRaspy = new ConnTcp("127.0.0.1", 8080);
			 connTcp.forward(stop);
			 String cmd = connRaspy.receiveMsg();
			 assertTrue(cmd.contains("led,cmd(on),"));
			 connTcp.close();
			 connRaspy.close();
		 }catch(Exception e) {
			 System.out.println("testLoadok ERROR:" + e.getMessage());
		 }

		 client.observe(new CoapHandler() {
			 @Override
			 public void onLoad(CoapResponse response){
				 System.out.println("Response: "+response.getResponseText());
				 assertTrue(response.getResponseText().contains("STOPPED"));
			 }

			 @Override
			 public void onError() {
				 System.out.println("ERRORE");
			 }
		 });

	}

	@Test
	public void resume() throws Exception {
		CoapClient client = new CoapClient("coap://localhost:8078/ctxanalisiproblema/trolley");
		String stop = "msg(stop,dispatch,algise,wasteservice,stop(70),18)";
		String resume = "msg(resume,dispatch,algise,wasteservice,resume(80),18)";
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8078);
			connTcp.forward(stop);
			Thread.sleep(1000);
			connTcp.forward(resume);
			connTcp.close();
		}catch(Exception e) {
			System.out.println("testLoadok ERROR:" + e.getMessage());
		}

		client.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response){
				System.out.println("Response: "+response.getResponseText());
				assertTrue(!response.getResponseText().contains("STOPPED"));
			}

			@Override
			public void onError() {
				System.out.println("ERRORE");
			}
		});

	}

	@Test
	public void ledOff() throws Exception {
		CoapClient client = new CoapClient("coap://localhost:8078/ctxanalisiproblema/trolley");
		client.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response){
				System.out.println("Response: "+response.getResponseText());
				assertTrue(response.getResponseText().contains("HOME"));
			}

			@Override
			public void onError() {
				System.out.println("ERRORE");
			}
		});
		try{
			ConnTcp connRaspy = new ConnTcp("127.0.0.1", 8080);
			String cmd = connRaspy.receiveMsg();
			assertTrue(cmd.contains("led,cmd(off),"));
			connRaspy.close();
		}catch(Exception e) {
			System.out.println("testLoadok ERROR:" + e.getMessage());
		}

	}

	@Test
	public void testLoadacceptAndBlink() {
		String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8078);
			ConnTcp connRaspy = new ConnTcp("127.0.0.1", 8080);
			String answer     = connTcp.request(truckRequestStr);
			System.out.println("Risposta=" + answer );
			assertTrue(answer.contains("loadaccept"));
			String cmd = connRaspy.receiveMsg();
			assertTrue(cmd.contains("led,cmd(blink),"));
			connRaspy.close();
			connTcp.close();

		}catch(Exception e){
			System.out.println("testLoadok ERROR:" + e.getMessage());

		}

	}
}
