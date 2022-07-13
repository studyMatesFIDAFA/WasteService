package it.unibo;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestAnalisiRequisiti {
    @Before
    public void up() {
        /*
        new Thread(){
            public void run(){
                it.unibo.ctxanalisiproblema.MainCtxanalisirequisitiKt.main();
            }
        }.start();


        waitForApplStarted();

         */
    }

    protected void waitForApplStarted(){
        ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
        while( wasteservice == null ){
            System.out.println("Attendo applicazione ... ");
            CommUtils.delay(200);
            wasteservice = QakContext.Companion.getActor("wasteservice");
        }
    }

    @Test
    public void testLoadaccept() {
        System.out.println("TEST STARTS");
        String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8078);
            String answer     = connTcp.request(truckRequestStr);
            System.out.println("Risposta=" + answer );
            connTcp.close();
            assertTrue(answer.contains("loadaccept"));

        }catch(Exception e){
            System.out.println("testLoadok ERROR:" + e.getMessage());

        }

    }
    /*
    @Test
    public void loadReject(){
        System.out.println("TEST STARTS");
        try{
            //richiesta da rifiutare
            ConnTcp connTcp   = new ConnTcp("localhost", 8050);
            String  truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,11),2)";
            String answer     = connTcp.request(truckRequestStr);
            System.out.println("Risposta=" + answer );
            connTcp.close();
            assertTrue(answer.contains("loadrejected"));
        }catch(Exception e){
            System.out.println("testLoadok ERROR:" + e.getMessage());

        }
    }
*/
    @After
    public void down() {
        System.out.println("FINE TEST ");
    }
}
