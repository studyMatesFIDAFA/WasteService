package it.unibo;

import static org.junit.Assert.*;

//import it.unibo.ctxraspy.MainCtxraspyKt;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import unibo.actor22comm.utils.CommUtils;

public class LedTest {
	@Before
	public void up() {
		new Thread(){
			public void run(){
                MainCtxraspy.main();
			}
		}.start();
		waitForApplStarted();
	}

	protected void waitForApplStarted(){
		ActorBasic led = QakContext.Companion.getActor("led");
		while( led == null ){
			System.out.println("Attendo applicazione ... ");
			CommUtils.delay(200);
			led = QakContext.Companion.getActor("led");
		}
	}

	@After
	public void down() {
		System.out.println("FINE TEST ");
	}

	
	 @Test
	public void ledOn() throws Exception {
		 CoapClient client = new CoapClient("coap://localhost:8080/ctxraspy/led");
		 String dispatch = "msg(cmd,dispatch,algise,led,cmd(on),18)";
		 client.observe(new CoapHandler() {
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
		 try{
			 ConnTcp connTcp   = new ConnTcp("localhost", 8080);
			 connTcp.forward(dispatch);
			 connTcp.close();
		 }catch(Exception e) {
			 System.out.println("testLoadok ERROR:" + e.getMessage());
		 }

	}

	@Test
	public void ledOff() throws Exception {
		CoapClient client = new CoapClient("coap://localhost:8080/ctxraspy/led");
		String dispatch = "msg(cmd,dispatch,algise,led,cmd(off),18)";
		client.observe(new CoapHandler() {
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
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8080);
			connTcp.forward(dispatch);
			connTcp.close();
		}catch(Exception e) {
			System.out.println("testLoadok ERROR:" + e.getMessage());
		}
	}

	@Test
	public void ledBlink() throws Exception {
		CoapClient client = new CoapClient("coap://localhost:8015/ctxraspy/led");
		String dispatch = "msg(cmd,dispatch,algise,led,cmd(blink),18)";
		client.observe(new CoapHandler() {
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
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8080);
			connTcp.forward(dispatch);
			connTcp.close();
		}catch(Exception e) {
			System.out.println("testLoadok ERROR:" + e.getMessage());
		}
		Thread.sleep(1000);

	}

}
