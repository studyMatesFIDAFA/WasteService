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
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var Path = ""
				val DelayIndoor=2000L
				val DelayBox=3000L
				val DelayHome=2000L
				
				var RemainPath = ""
				var LastState = ""
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
						updateResourceRep( "TROLLEY:HOME"  
						)
						emit("updatepostrolley", "updatepostrolley(HOME)" ) 
						emit("updatestatetrolley", "updatestatetrolley(HOME)" ) 
					}
					 transition(edgeName="t00",targetState="go_indoor",cond=whenRequest("go_indoor"))
					transition(edgeName="t01",targetState="ritorno_home",cond=whenRequest("ritorno_home"))
					transition(edgeName="t02",targetState="stopped",cond=whenDispatch("stop"))
				}	 
				state("go_indoor") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | GO INDOOR")
						updateResourceRep( "TROLLEY:GO_INDOOR"  
						)
						if( checkMsgContent( Term.createTerm("go_indoor(PATH_INDOOR)"), Term.createTerm("go_indoor(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Path = payloadArg(0)
												println(Path)
								request("dopath", "dopath($Path)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("go_indoor", "indoor_done", "indoor_done(ok)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var RemainPath = payloadArg(0)
								if(  RemainPath.length == 1 || RemainPath.length == 0 
								 ){answer("go_indoor", "indoor_done", "indoor_done(ok)"   )  
								}
								else
								 {request("dopath", "dopath($RemainPath)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t13",targetState="go_indoor",cond=whenReply("dopathdone"))
					transition(edgeName="t14",targetState="go_indoor",cond=whenReply("dopathfail"))
					transition(edgeName="t15",targetState="alarm",cond=whenDispatch("stop"))
					transition(edgeName="t16",targetState="pickup",cond=whenRequest("pickup"))
				}	 
				state("pickup") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | PICKUP")
						updateResourceRep( "TROLLEY:PICKUP"  
						)
						if( checkMsgContent( Term.createTerm("pickup(arg)"), Term.createTerm("pickup(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(500) 
								answer("pickup", "pickup_done", "pickup_done(ok)"   )  
						}
					}
					 transition(edgeName="t27",targetState="trasferimento",cond=whenRequest("trasf"))
					transition(edgeName="t28",targetState="stopped",cond=whenDispatch("stop"))
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
						updateResourceRep( "TROLLEY:TRASFERIMENTO"  
						)
						if( checkMsgContent( Term.createTerm("trasf(PATH)"), Term.createTerm("trasf(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Path = payloadArg(0)
												println(Path)
								request("dopath", "dopath($Path)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("trasf", "trasf_done", "trasf_done(ok)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var RemainPath = payloadArg(0)
								if(  RemainPath.length == 1 || RemainPath.length == 0 
								 ){answer("trasf", "trasf_done", "trasf_done(ok)"   )  
								}
								else
								 {request("dopath", "dopath($RemainPath)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t39",targetState="trasferimento",cond=whenReply("dopathdone"))
					transition(edgeName="t310",targetState="trasferimento",cond=whenReply("dopathfail"))
					transition(edgeName="t311",targetState="deposito",cond=whenRequest("deposit"))
					transition(edgeName="t312",targetState="alarm",cond=whenDispatch("stop"))
				}	 
				state("deposito") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | DEPOSITO")
						updateResourceRep( "TROLLEY:DEPOSITO"  
						)
						if( checkMsgContent( Term.createTerm("deposit(arg)"), Term.createTerm("deposit(arg)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(500) 
								answer("deposit", "deposit_done", "deposit_done(ok)"   )  
						}
					}
					 transition(edgeName="t413",targetState="ritorno_home",cond=whenRequest("ritorno_home"))
					transition(edgeName="t414",targetState="go_indoor",cond=whenRequest("go_indoor"))
					transition(edgeName="t415",targetState="stopped",cond=whenDispatch("stop"))
				}	 
				state("ritorno_home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | RITORNO HOME")
						updateResourceRep( "TROLLEY:RITORNO_HOME"  
						)
						if( checkMsgContent( Term.createTerm("ritorno_home(PATH_HOME)"), Term.createTerm("ritorno_home(PATH_HOME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Path = payloadArg(0)
								request("dopath", "dopath($Path)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Trolley home ok")
								answer("ritorno_home", "home_done", "home_done(ok)"   )  
								updateResourceRep( "TROLLEY:HOME"  
								)
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var RemainPath = payloadArg(0)
								if(  RemainPath.length == 1 || RemainPath.length == 0 
								 ){answer("ritorno_home", "home_done", "home_done(ok)"   )  
								updateResourceRep( "TROLLEY:HOME"  
								)
								}
								else
								 {request("dopath", "dopath($RemainPath)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t516",targetState="ritorno_home",cond=whenReply("dopathdone"))
					transition(edgeName="t517",targetState="ritorno_home",cond=whenReply("dopathfail"))
					transition(edgeName="t518",targetState="go_indoor",cond=whenRequest("go_indoor"))
					transition(edgeName="t519",targetState="ritorno_home",cond=whenRequest("ritorno_home"))
					transition(edgeName="t520",targetState="alarm",cond=whenDispatch("stop"))
				}	 
				state("alarm") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | ALARM")
						emit("alarm", "alarm(STOP)" ) 
						stateTimer = TimerActor("timer_alarm", 
							scope, context!!, "local_tout_trolley_alarm", 1000.toLong() )
					}
					 transition(edgeName="t621",targetState="stopped",cond=whenTimeout("local_tout_trolley_alarm"))   
					transition(edgeName="t622",targetState="stopped",cond=whenReply("dopathdone"))
					transition(edgeName="t623",targetState="savepath",cond=whenReply("dopathfail"))
				}	 
				state("savepath") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												RemainPath = payloadArg(0)
						}
						println("TROLLEY | SAVE PATH: $RemainPath")
					}
					 transition( edgeName="goto",targetState="stopped", cond=doswitch() )
				}	 
				state("stopped") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | STOPPED")
						updateResourceRep( "TROLLEY:STOPPED"  
						)
					}
					 transition(edgeName="t724",targetState="resume",cond=whenDispatch("resume"))
				}	 
				state("resume") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TROLLEY | RESUME")
						if( checkMsgContent( Term.createTerm("resume(STATE)"), Term.createTerm("resume(STATE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												LastState = payloadArg(0)
								request("dopath", "dopath($RemainPath)" ,"pathexec" )  
								 RemainPath = ""  
						}
						println("TROLLEY | $LastState")
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var RemainPath = payloadArg(0)
								if(  RemainPath.length == 1 || RemainPath.length == 0 
								 ){ RemainPath = ""  
								request("dopath", "dopath($RemainPath)" ,"pathexec" )  
								}
								else
								 {request("dopath", "dopath($RemainPath)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t825",targetState="home",cond=whenReplyGuarded("dopathdone",{ LastState == "home"  
					}))
					transition(edgeName="t826",targetState="go_indoor",cond=whenReplyGuarded("dopathdone",{ LastState == "go_indoor"  
					}))
					transition(edgeName="t827",targetState="pickup",cond=whenReplyGuarded("dopathdone",{ LastState == "pickup"  
					}))
					transition(edgeName="t828",targetState="trasferimento",cond=whenReplyGuarded("dopathdone",{ LastState == "trasferimento"  
					}))
					transition(edgeName="t829",targetState="deposito",cond=whenReplyGuarded("dopathdone",{ LastState == "deposito"  
					}))
					transition(edgeName="t830",targetState="ritorno_home",cond=whenReplyGuarded("dopathdone",{ LastState == "ritorno_home"  
					}))
					transition(edgeName="t831",targetState="resume",cond=whenReply("dopathfail"))
				}	 
			}
		}
}
