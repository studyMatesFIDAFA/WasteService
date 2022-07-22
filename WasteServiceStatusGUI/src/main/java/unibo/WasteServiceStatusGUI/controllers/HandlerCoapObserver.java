package unibo.WasteServiceStatusGUI.controllers;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.comm22.utils.ColorsOut;

public class HandlerCoapObserver implements CoapHandler{

    @Override
    public void onLoad(CoapResponse response) {
        //System.out.println("RobotCoapObserver changed!" + response.getResponseText());
        //send info over the websocket
        WebSocketConfiguration.wshandler.sendToAll("" + response.getResponseText());
        //simpMessagingTemplate.convertAndSend(WebSocketConfig.topicForTearoomstatemanager, new ResourceRep("" + HtmlUtils.htmlEscape(response.getResponseText())));
    }

    @Override
    public void onError() {
        ColorsOut.outerr("HandlerCoapObserver observe error!");
    }
}
