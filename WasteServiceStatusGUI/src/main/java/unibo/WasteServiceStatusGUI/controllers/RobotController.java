package unibo.WasteServiceStatusGUI.controllers;
//https://www.baeldung.com/websockets-spring
//https://www.toptal.com/java/stomp-spring-boot-websocket

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unibo.comm22.coap.CoapConnection;
import unibo.comm22.utils.ColorsOut;

//---------------------------------------------------
//import unibo.Robots.common.RobotUtils;


@Controller 
public class RobotController {
    public final static String robotName  = "basicrobot";
    protected String mainPage             = "WasteServiceStatusGui";

    protected String buildThePage(Model viewmodel) {
        //setConfigParams(viewmodel);
        return mainPage;
    }

    /*protected void setConfigParams(Model viewmodel){
        //
    }*/

  @GetMapping("/") 		 
  public String entry(Model viewmodel) {
      CoapConnection connWaste = RobotUtils.connectWithWasteServiceUsingCoap("localhost");
      CoapConnection connTrolley = RobotUtils.connectWithTrolleyUsingCoap("localhost");
      CoapConnection connLed = RobotUtils.connectWithLedUsingCoap("10.5.5.5");
      //TODO aggiungere campo per specificare l'ip del rasp
      connWaste.observeResource( new HandlerCoapObserver() );
      connTrolley.observeResource( new HandlerCoapObserver() );
      connLed.observeResource( new HandlerCoapObserver() );
      return buildThePage(viewmodel);
  }


    /*@PostMapping("/setrobotip")
    public String setrobotip(Model viewmodel, @RequestParam String ipaddr  ){
        robotip = ipaddr;
        System.out.println("RobotHIController | setrobotip:" + ipaddr );
        viewmodel.addAttribute("robotip", robotip);
//        setConfigParams(viewmodel);
        //Uso basicrobto22 sulla porta 8020
        //robotName  = "basicrobot";
        if( usingTcp ) RobotUtils.connectWithRobotUsingTcp(ipaddr);
        //Attivo comunque una connessione CoAP per osservare basicrobot
        CoapConnection conn = RobotUtils.connectWithRobotUsingCoap(ipaddr);
        conn.observeResource( new RobotCoapObserver() );
        return buildThePage(viewmodel);
    }*/

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity(
                "BaseController ERROR " + ex.getMessage(),
                responseHeaders, HttpStatus.CREATED);
    }
 
/*
 * curl --location --request POST 'http://localhost:8080/move' --header 'Content-Type: text/plain' --form 'move=l'	
 * curl -d move=r localhost:8080/move
 */
}

