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
import unibo.comm22.utils.ColorsOut;

public class RobotUtils {
    public static final int ctxanlisiproblema_port           = 8078;
    public static final int ctxraspy_port           = 8080;
    private static Interaction2021 conn;

    public static CoapConnection connectWithTrolleyUsingCoap(String addr){
        try {
            String ctxqakdest       = "ctxanalisiproblema";
            String qakdestination 	= "trolley";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+ctxanlisiproblema_port, path);
            ((CoapConnection)conn).observeResource( new HandlerCoapObserver() );
            System.out.println("HIController | connect Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("RobotUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }

    public static CoapConnection connectWithLedUsingCoap(String addr){
        try {
            String ctxqakdest       = "ctxraspy";
            String qakdestination 	= "led";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+ctxraspy_port, path);
            ((CoapConnection)conn).observeResource( new HandlerCoapObserver() );
            System.out.println("HIController | connect Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("RobotUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }

    public static CoapConnection connectWithWasteServiceUsingCoap(String addr){
        try {
            String ctxqakdest       = "ctxanalisiproblema";
            String qakdestination 	= "wasteservice";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+ctxanlisiproblema_port, path);
            ((CoapConnection)conn).observeResource( new HandlerCoapObserver() );
            System.out.println("HIController | connect Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("RobotUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }
}
