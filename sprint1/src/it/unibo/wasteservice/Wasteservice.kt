/* Generated by AN DISI Unibo */ 
package it.unibo.wasteservice

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wasteservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "start"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var MAXPB = 10
				var MAXGB = 10
				var CurrentPlasticWeight=0
				var CurrentGlassWeight=0
				var Trolley_home=true	
				var Carico_accettato = false
				var Tipo_carico = ""
				var Peso_carico = 0
				val Percorso_vetro = "lwwwwwwwlwwwww"
				val Percorso_plastica = "lwwww"
				val Percorso_home = "lwwwwwl"
				val Percorso_indoor = "wwwww"
				var PercorsoCurr = ""
				val XIndoor = 0
				val YIndoor = 5
				val XVetro = 7
				val YVetro = 0
				val XPlastica = 7
				val YPlastica = 5
				val XHome = 0
				val YHome = 0
		return { //this:ActionBasciFsm
				state("start") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						unibo.kotlin.planner22Util.loadRoomMap( "mapRoomEmpty.txt"  )
						unibo.kotlin.planner22Util.initAI(  )
						unibo.kotlin.planner22Util.showCurrentRobotState(  )
						println("WASTE SERVICE | START")
					}
					 transition( edgeName="goto",targetState="attesa_load_req", cond=doswitch() )
				}	 
				state("attesa_load_req") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | ATTESA LOAD REQUEST(CAMION)")
					}
					 transition(edgeName="t011",targetState="gestisci_richiesta",cond=whenRequest("load_req"))
				}	 
				state("gestisci_richiesta") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | GESTIONE RICHIESTA")
						if( checkMsgContent( Term.createTerm("load_req(TYPE,WEIGHT)"), Term.createTerm("load_req(TYPE,WEIGHT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Tipo_carico = payloadArg(0)
												Peso_carico = payloadArg(1).toInt()
												if (Tipo_carico  == "plastica") {
													Carico_accettato = (CurrentPlasticWeight + Peso_carico <= MAXPB)
												} else if (Tipo_carico  == "vetro") {
													Carico_accettato = ( CurrentGlassWeight + Peso_carico <= MAXGB)
												} else {
													Carico_accettato = false
													println("WASTE SERVICE | Tipo sbagliato")
												}
								if(  ! Carico_accettato  
								 ){answer("load_req", "loadrejected", "loadrejected($Tipo_carico,$Peso_carico)"   )  
								if(  ! Trolley_home  
								 ){forward("ritorno_home", "ritorno_home($Percorso_home)" ,"trolley" ) 
								 Trolley_home = true  
								}
								}
						}
					}
					 transition( edgeName="goto",targetState="attiva_pickup", cond=doswitchGuarded({ Carico_accettato  
					}) )
					transition( edgeName="goto",targetState="attesa_load_req", cond=doswitchGuarded({! ( Carico_accettato  
					) }) )
				}	 
				state("attiva_pickup") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | ATTIVA DEPOSITO")
						 Trolley_home = false  
						unibo.kotlin.planner22Util.setGoal( XIndoor, YIndoor  )
						 PercorsoCurr = unibo.kotlin.planner22Util.doPlan().toString()  //List<aima.core.agent.Action>  [w, w, l, w] 
									.replace(" ","")
									.replace(",","")
									.replace("[","")
									.replace("]","")
						request("pickup", "pickup($PercorsoCurr)" ,"trolley" )  
					}
					 transition(edgeName="t112",targetState="attiva_trasferimento",cond=whenReply("pickup_done"))
				}	 
				state("attiva_trasferimento") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | ATTIVA TRASFERIMENTO (IL CAMION LIBERA INDOOR)")
						if( checkMsgContent( Term.createTerm("pickup_done(ok)"), Term.createTerm("pickup_done(ok)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("load_req", "loadaccept", "loadaccept($Tipo_carico,$Peso_carico)"   )  
						}
						unibo.kotlin.planner22Util.updateMapWithPath( PercorsoCurr  )
						unibo.kotlin.planner22Util.showCurrentRobotState(  )
						
									if (Tipo_carico  == "plastica") {
											CurrentPlasticWeight = CurrentPlasticWeight + Peso_carico
						unibo.kotlin.planner22Util.setGoal( XPlastica, YPlastica  )
								
									} else if (Tipo_carico  == "vetro") {
											CurrentGlassWeight = CurrentGlassWeight + Peso_carico
						unibo.kotlin.planner22Util.setGoal( XVetro, YVetro  )
						
									}
								
								PercorsoCurr = unibo.kotlin.planner22Util.doPlan().toString() 
									.replace(" ","")
									.replace(",","")
									.replace("[","")
									.replace("]","")
						request("trasf", "trasf($PercorsoCurr)" ,"trolley" )  
					}
					 transition(edgeName="t213",targetState="attiva_deposito",cond=whenReply("trasf_done"))
				}	 
				state("attiva_deposito") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | ATTIVA DEPOSITO")
						unibo.kotlin.planner22Util.updateMapWithPath( PercorsoCurr  )
						unibo.kotlin.planner22Util.showCurrentRobotState(  )
						request("deposit", "deposit(arg)" ,"trolley" )  
					}
					 transition(edgeName="t314",targetState="controlla_req",cond=whenReply("deposit_done"))
				}	 
				state("controlla_req") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | CONTROLLA NUOVE RICHIESTE")
						stateTimer = TimerActor("timer_controlla_req", 
							scope, context!!, "local_tout_wasteservice_controlla_req", 100.toLong() )
					}
					 transition(edgeName="t415",targetState="go_home",cond=whenTimeout("local_tout_wasteservice_controlla_req"))   
					transition(edgeName="t416",targetState="gestisci_richiesta",cond=whenRequest("load_req"))
				}	 
				state("go_home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTE SERVICE | GO HOME")
						unibo.kotlin.planner22Util.setGoal( XHome, YHome  )
						
								PercorsoCurr = unibo.kotlin.planner22Util.doPlan().toString() 
									.replace(" ","")
									.replace(",","")
									.replace("[","")
									.replace("]","")
						forward("ritorno_home", "ritorno_home($PercorsoCurr)" ,"trolley" ) 
						 Trolley_home = true  
					}
					 transition( edgeName="goto",targetState="attesa_load_req", cond=doswitch() )
				}	 
			}
		}
}
