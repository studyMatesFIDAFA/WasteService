package unibo.WasteServiceStatusGUI.controllers;
//https://www.baeldung.com/websockets-spring
//https://www.toptal.com/java/stomp-spring-boot-websocket

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibo.WasteServiceStatusGUI.model.AddressForm;
import unibo.WasteServiceStatusGUI.model.WasteTruckForm;
import unibo.comm22.coap.CoapConnection;
import unibo.comm22.utils.ColorsOut;

//---------------------------------------------------
//import unibo.Robots.common.RobotUtils;

@Controller 
public class RobotController {
    public final static String robotName  = "basicrobot";
    //protected String mainPage             = "WasteServiceStatusGui";
    protected String mainPage             = "WasteServiceStatusGuiAjax";

    @Value("not connected")
    String ledip;
    @Value("not connected")
    String trolleyip;
    @Value("not connected")
    String wasteserviceip;
    @Value("8080")
    int portled;
    @Value("8078")
    int porttrolley;
    @Value("8078")
    int portwasteservice;
    @Value("unknown")
    String materiale;
    @Value("0")
    int qta;




    protected String buildThePage(Model viewmodel) {
        setConfigParams(viewmodel);
        return mainPage;
    }

    protected void setConfigParams(Model viewmodel){
        viewmodel.addAttribute("ledip",  ledip);
        viewmodel.addAttribute("trolleyip", trolleyip);
        viewmodel.addAttribute("wasteserviceip",wasteserviceip);
        viewmodel.addAttribute("portled",  portled);
        viewmodel.addAttribute("porttrolley", porttrolley);
        viewmodel.addAttribute("portwasteservice",portwasteservice);
        viewmodel.addAttribute("qta", qta);
        viewmodel.addAttribute("materiale", materiale);
    }

  @GetMapping("/") 		 
  public String entry(Model viewmodel) {
      return buildThePage(viewmodel);
  }

  @PostMapping("/setip")
    public String setrobotip(Model viewmodel, @ModelAttribute AddressForm addressForm){
        ledip = addressForm.getIp_led();
        portled = addressForm.getPort_led();
        trolleyip = addressForm.getIp_trolley();
        porttrolley = addressForm.getPort_trolley();
        wasteserviceip = addressForm.getIp_ws();
        portwasteservice = addressForm.getPort_ws();

        viewmodel.addAttribute("ledip", ledip);
        viewmodel.addAttribute("trolleyip", trolleyip);
        viewmodel.addAttribute("wasteserviceip",wasteserviceip);
        viewmodel.addAttribute("portled", portled);
        viewmodel.addAttribute("porttrolley", porttrolley);
        viewmodel.addAttribute("portwasteservice",portwasteservice);

        //Attivo connessione TCP per inviare richiesta del waste truck
        RobotUtils.connectWithWasteServiceUsingTcp(wasteserviceip,portwasteservice);


        //Attivo una connessione CoAP per osservare
        CoapConnection connWaste = RobotUtils.connectWithWasteServiceUsingCoap(wasteserviceip,portwasteservice);
        CoapConnection connTrolley = RobotUtils.connectWithTrolleyUsingCoap(trolleyip,porttrolley);
        CoapConnection connLed = RobotUtils.connectWithLedUsingCoap(ledip,portled);

        connWaste.observeResource( new HandlerCoapObserver() );
        connTrolley.observeResource( new HandlerCoapObserver() );
        connLed.observeResource( new HandlerCoapObserver() );

        return buildThePage(viewmodel);
        //return mainPage;
    }
/*
    @PostMapping("/load_req")
    public String sendWasteTruckRequest(Model viewmodel, @ModelAttribute WasteTruckForm wasteTruckForm){
        materiale = wasteTruckForm.getTipo();
        qta = wasteTruckForm.getQta();
        String msg =  "load_req("+materiale+","+qta+")";
        System.out.println(msg);
        try {
            RobotUtils.sendWasteTruckReq(msg);
        } catch (Exception e){
            System.out.println("RobotController | sendWasteTruckRequest ERROR:"+e.getMessage());
        }

        //return mainPage;
        return buildThePage(viewmodel);
    }
*/

    @ResponseBody
    @PostMapping("/load_req")
    public String sendWasteTruckRequest(Model viewmodel, @ModelAttribute WasteTruckForm wasteTruckForm){
        materiale = wasteTruckForm.getTipo();
        qta = wasteTruckForm.getQta();
        String msg =  "load_req("+materiale+","+qta+")";
        System.out.println(msg);
        String res="";
        try {
            res = RobotUtils.sendWasteTruckReq(msg);
        } catch (Exception e){
            System.out.println("RobotController | sendWasteTruckRequest ERROR:"+e.getMessage());
        }

        //return mainPage;
        return res;
    }

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

