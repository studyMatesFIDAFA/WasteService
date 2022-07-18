package it.unibo;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestApp {


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
    public void testAppl() {
        String truckRequestStr1 = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
        String truckRequestStr2 = "msg(load_req, request, algise,wasteservice,load_req(plastica,7),2)";
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8078);
            //Prima richiesta
            String answer     = connTcp.request(truckRequestStr1);
            System.out.println("Risposta=" + answer );
            assertTrue(answer.contains("loadaccept"));

            Thread.sleep(2000);
            //Seconda richiesta
            answer     = connTcp.request(truckRequestStr2);
            System.out.println("Risposta=" + answer );
            assertTrue(answer.contains("loadaccept"));
            connTcp.close();
            Thread.sleep(20000);
        }catch(Exception e){
            System.out.println("testLoadok ERROR:" + e.getMessage());

        }

    }
}
