package unibo.WasteServiceStatusGUI.controllers;
/*
import unibo.actor22comm.coap.CoapConnection;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.tcp.TcpClientSupport;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;
*/

import it.unibo.kactor.IApplMessage;
import unibo.comm22.coap.CoapConnection;
import unibo.comm22.interfaces.Interaction2021;
import unibo.comm22.tcp.TcpClientSupport;
import unibo.comm22.utils.ColorsOut;
import unibo.comm22.utils.CommUtils;

public class WasteUtils {

    private static Interaction2021 conn;
    private static Interaction2021 connTcp;

    public static CoapConnection connectWithTrolleyUsingCoap(String addr, int port){
        try {
            String ctxqakdest       = "ctxanalisiproblema";
            String qakdestination 	= "trolley";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+port, path);
            ((CoapConnection)conn).observeResource( new HandlerCoapObserver() );
            System.out.println("WasteUtils | connect TROLLEY whit Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("WasteUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }

    public static void connectWithWasteServiceUsingTcp(String addr, int port){
        try {
            connTcp = TcpClientSupport.connect(addr, port, 10);
            System.out.println("WasteUtils | connect WASTE_SERVICE with Tcp conn:" + connTcp );
        }catch(Exception e){
            ColorsOut.outerr("RobotUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }

    public static String sendWasteTruckReq(String payload) {
        String answer="";
        try {
            IApplMessage msg =  CommUtils.buildRequest("webGui", "load_req", payload, "wasteservice");
            //ColorsOut.outappl("RobotUtils | sendMsg msg:" + msg + " conn=" + connTcp, ColorsOut.BLUE);
            if( msg.isRequest() ){
                answer = connTcp.request( msg.toString() );
                //System.out.println("RobotUtils | answer:" + answer );
            } else {
                // altro tipo di msg
            }
        } catch (Exception e) {
            ColorsOut.outerr("RobotUtils | sendMsg ERROR:"+e.getMessage());
        }
        if(answer.contains("loadaccept"))
        {
            return "loadaccept";
        }
        else{
            return "loadrejected";
        }
    }

    public static CoapConnection connectWithLedUsingCoap(String addr, int port){
        try {
            String ctxqakdest       = "ctxraspy";
            String qakdestination 	= "led";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+port, path);
            ((CoapConnection)conn).observeResource( new HandlerCoapObserver() );
            System.out.println("WasteUtils | connect LED with Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("RobotUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }

    public static CoapConnection connectWithWasteServiceUsingCoap(String addr, int port){
        try {
            String ctxqakdest       = "ctxanalisiproblema";
            String qakdestination 	= "wasteservice";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+port, path);
            ((CoapConnection)conn).observeResource( new HandlerCoapObserver() );
            System.out.println("WasteUtils | connect WASTE_SEVICE with Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("RobotUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }
}
