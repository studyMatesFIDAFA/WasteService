package it.unibo;

import static org.junit.Assert.*;

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
                it.unibo.ctxanalisiproblema.MainCtxanalisiproblemaKt.main();
			}
		}.start();
		waitForApplStarted();
	}

	protected void waitForApplStarted() {
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
		 CoapClient clientLed = new CoapClient("coap://10.5.5.5:8080/ctxraspy/led");
		 String stop = "msg(stop,dispatch,algise,wasteservice,stop(70),18)";
		 try{
			 ConnTcp connTcp   = new ConnTcp("localhost", 8078);
			 Thread.sleep(1000);
			 connTcp.forward(stop);
			 connTcp.close();
		 }catch(Exception e) {
			 System.out.println("testLoadok ERROR:" + e.getMessage());
		 }

		 Thread.sleep(2000);
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
		clientLed.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response){
				System.out.println("Response: "+response.getResponseText());
				assertTrue(response.getResponseText().contains("ON"));
			}

			@Override
			public void onError() {
				System.out.println("ERRORE");
			}
		});
		Thread.sleep(3000);
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
		Thread.sleep(3000);
	}

	@Test
	public void homeAndLedOff() throws Exception {
		CoapClient client = new CoapClient("coap://localhost:8078/ctxanalisiproblema/trolley");
		CoapClient clientLed = new CoapClient("coap://localhost:8080/ctxraspy/led");
		Thread.sleep(2000);
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
		clientLed.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response){
				System.out.println("Response: "+response.getResponseText());
				assertTrue(response.getResponseText().contains("OFF"));
			}

			@Override
			public void onError() {
				System.out.println("ERRORE");
			}
		});
		Thread.sleep(2000);
	}

	@Test
	public void testLoadacceptAndBlink() throws InterruptedException {
		String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
		CoapClient clientLed = new CoapClient("coap://localhost:8080/ctxraspy/led");
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8078);

			String answer     = connTcp.request(truckRequestStr);
			System.out.println("Risposta=" + answer );
			assertTrue(answer.contains("loadaccept"));
			connTcp.close();

		}catch(Exception e){
			System.out.println("testLoadok ERROR:" + e.getMessage());

		}
		clientLed.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response){
				System.out.println("Response: "+response.getResponseText());
				assertTrue(response.getResponseText().contains("BLINK"));
			}

			@Override
			public void onError() {
				System.out.println("ERRORE");
			}
		});
		Thread.sleep(3000);
	}
}
