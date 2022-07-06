package it.unibo;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;


public class TestApplSprint1 {
    @Before
    public void up() {
        new Thread(){
            public void run(){
                it.unibo.ctxanalisiproblema.MainCtxanalisiproblemaKt.main();
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
    public void testLoadaccept() {
        System.out.println("TEST STARTS");
        String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8050);
            String answer     = connTcp.request(truckRequestStr);
            System.out.println("Risposta=" + answer );
            connTcp.close();
            assertTrue(answer.contains("loadaccept"));

        }catch(Exception e){
            System.out.println("testLoadok ERROR:" + e.getMessage());

        }

    }

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

    @Test
    public void sequenzaAzioneDeposito() throws Exception {
        CoapClient client = new CoapClient("coap://localhost:8050/ctxanalisiproblema/trolley");
        String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
        client.observe(new CoapHandler() {
            int counter = 0;
            @Override
            public void onLoad(CoapResponse response) {
                counter++;
                System.out.println("Response: "+response.getResponseText());
                switch (counter) {
                    case 1 : assertTrue(response.getResponseText().contains("PICKUP")); break;
                    case 2 : assertTrue(response.getResponseText().contains("TRASFERIMENTO")); break;
                    case 3 : assertTrue(response.getResponseText().contains("DEPOSITO")); break;
                    case 4 : assertTrue(response.getResponseText().contains("HOME")); break;
                    default: break;
                }

            }

            @Override
            public void onError() {
                System.out.println("ERRORE");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8050);
            String answer     = connTcp.request(truckRequestStr);
            System.out.println("Risposta=" + answer );
            connTcp.close();
        }catch(Exception e){
            System.out.println("testLoadok ERROR:" + e.getMessage());
        }
        Thread.sleep(7000);

    }

    @Test
    public void sequenzaAzioneDepositoDoppiaRichiesta() throws Exception {
        CoapClient client = new CoapClient("coap://localhost:8050/ctxanalisiproblema/trolley");
        String truckRequestStr = "msg(load_req, request, algise,wasteservice,load_req(vetro,2),1)";
        client.observe(new CoapHandler() {
            int counter = 0;
            @Override
            public void onLoad(CoapResponse response) {
                counter++;
                System.out.println("Response: "+response.getResponseText());
                switch (counter) {
                    case 1 : assertTrue(response.getResponseText().contains("PICKUP")); break;
                    case 2 : assertTrue(response.getResponseText().contains("TRASFERIMENTO")); break;
                    case 3 : assertTrue(response.getResponseText().contains("DEPOSITO")); break;
                    case 4 : assertTrue(response.getResponseText().contains("PICKUP")); break;
                    case 5 : assertTrue(response.getResponseText().contains("TRASFERIMENTO")); break;
                    case 6 : assertTrue(response.getResponseText().contains("DEPOSITO")); break;
                    case 7 : assertTrue(response.getResponseText().contains("HOME")); break;
                    default: break;
                }

            }

            @Override
            public void onError() {
                System.out.println("ERRORE");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8050);
            String answer     = connTcp.request(truckRequestStr);
            System.out.println("Risposta=" + answer );
            answer     = connTcp.request(truckRequestStr);
            System.out.println("Risposta=" + answer );
            connTcp.close();
        }catch(Exception e){
            System.out.println("testLoadok ERROR:" + e.getMessage());
        }
        Thread.sleep(7000);

    }
}
