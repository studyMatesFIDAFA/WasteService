/* Generated by AN DISI Unibo */ 
package it.unibo.led

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "start"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Cmd = ""
				var Blink = false
		return { //this:ActionBasciFsm
				state("start") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						 Runtime.getRuntime().exec("sudo bash led25GpioTurnOff.sh")  
					}
					 transition( edgeName="goto",targetState="wait_cmd", cond=doswitch() )
				}	 
				state("wait_cmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition(edgeName="t06",targetState="esegui_cmd",cond=whenDispatch("cmd"))
				}	 
				state("esegui_cmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("cmd(CMD)"), Term.createTerm("cmd(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Cmd = payloadArg(0)
												println(Cmd)
												Blink = false
													
											
											if ( Cmd=="on" ){
												Runtime.getRuntime().exec("sudo bash led25GpioTurnOn.sh")
												//println("LED | ON")
											}
															
											 else if ( Cmd == "off" ) {
												Runtime.getRuntime().exec("sudo bash led25GpioTurnOff.sh")
												//println("LED | OFF")
											}
											else {
												Blink = true
											}
						}
						if(  Cmd == "on"  
						 ){updateResourceRep( "LED:ON"  
						)
						}
						if(  Cmd == "off"  
						 ){updateResourceRep( "LED:OFF"  
						)
						}
					}
					 transition( edgeName="goto",targetState="blink", cond=doswitchGuarded({ Blink  
					}) )
					transition( edgeName="goto",targetState="wait_cmd", cond=doswitchGuarded({! ( Blink  
					) }) )
				}	 
				state("blink") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						updateResourceRep( "LED:BLINK"  
						)
						
									Runtime.getRuntime().exec("sudo bash led25GpioTurnOn.sh")
									//println("LED | ON")	
						delay(500) 
						
									Runtime.getRuntime().exec("sudo bash led25GpioTurnOff.sh")
									//println("LED | OFF")	
						stateTimer = TimerActor("timer_blink", 
							scope, context!!, "local_tout_led_blink", 500.toLong() )
					}
					 transition(edgeName="t17",targetState="blink",cond=whenTimeout("local_tout_led_blink"))   
					transition(edgeName="t18",targetState="esegui_cmd",cond=whenDispatch("cmd"))
				}	 
			}
		}
}
