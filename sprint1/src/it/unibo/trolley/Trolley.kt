/* Generated by AN DISI Unibo */ 
package it.unibo.trolley

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Trolley ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "start"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Path = ""
				val DelayIndoor=2000L
				val DelayBox=3000L
				val DelayHome=2000L
		return { //this:ActionBasciFsm
				state("start") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | START")
					}
					 transition( edgeName="goto",targetState="home", cond=doswitch() )
				}	 
				state("home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | HOME")
						println("TROLLEY | Attendo un compito dal Waste Service")
					}
					 transition(edgeName="t00",targetState="pickup",cond=whenRequest("pickup"))
					transition(edgeName="t01",targetState="ritorno_home",cond=whenRequest("ritorno_home"))
				}	 
				state("pickup") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | PICKUP")
						if( checkMsgContent( Term.createTerm("pickup(PATH_INDOOR)"), Term.createTerm("pickup(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Path = payloadArg(0)
												println(Path)
								request("dopath", "dopath($Path)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(500) 
								answer("pickup", "pickup_done", "pickup_done(ok)"   )  
						}
					}
					 transition(edgeName="t12",targetState="pickup",cond=whenReply("dopathdone"))
					transition(edgeName="t13",targetState="pathfail",cond=whenReply("dopathfail"))
					transition(edgeName="t14",targetState="trasferimento",cond=whenRequest("trasf"))
				}	 
				state("pathfail") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | PATH FAIL : ERRORE!!!!")
					}
				}	 
				state("trasferimento") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | TRASFERIMENTO ")
						if( checkMsgContent( Term.createTerm("trasf(PATH)"), Term.createTerm("trasf(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Path = payloadArg(0)
												println(Path)
								request("dopath", "dopath($Path)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(500) 
								answer("trasf", "trasf_done", "trasf_done(ok)"   )  
						}
					}
					 transition(edgeName="t35",targetState="trasferimento",cond=whenReply("dopathdone"))
					transition(edgeName="t36",targetState="pathfail",cond=whenReply("dopathfail"))
					transition(edgeName="t37",targetState="deposito",cond=whenRequest("deposit"))
				}	 
				state("deposito") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | DEPOSITO")
						if( checkMsgContent( Term.createTerm("deposit(arg)"), Term.createTerm("deposit(arg)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TROLLEY | Ricevuto deposit")
						}
						delay(500) 
						answer("deposit", "deposit_done", "deposit_done(ok)"   )  
					}
					 transition(edgeName="t48",targetState="ritorno_home",cond=whenRequest("ritorno_home"))
					transition(edgeName="t49",targetState="pickup",cond=whenRequest("pickup"))
				}	 
				state("ritorno_home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | RITORNO HOME")
						if( checkMsgContent( Term.createTerm("ritorno_home(PATH_HOME)"), Term.createTerm("ritorno_home(PATH_HOME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Path = payloadArg(0)
								request("dopath", "dopath($Path)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Trolley home ok")
								answer("ritorno_home", "home_done", "home_done(ok)"   )  
						}
					}
					 transition(edgeName="t510",targetState="ritorno_home",cond=whenReply("dopathdone"))
					transition(edgeName="t511",targetState="pathfail",cond=whenReply("dopathfail"))
					transition(edgeName="t512",targetState="pickup",cond=whenRequest("pickup"))
				}	 
			}
		}
}
