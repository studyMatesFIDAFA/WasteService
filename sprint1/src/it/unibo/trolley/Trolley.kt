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
		return "home"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var Another = false  
		return { //this:ActionBasciFsm
				state("home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println(" TROLLEY | HOME ")
					}
					 transition( edgeName="goto",targetState="pickup", cond=doswitch() )
				}	 
				state("pickup") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println(" TROLLEY | CARICA un WASTE-LOAD da INDOOR ")
					}
					 transition( edgeName="goto",targetState="trasferimento_e_deposito", cond=doswitch() )
				}	 
				state("trasferimento_e_deposito") { //this:State
					action { //it:State
						
									Another = ((0..1).random())==1
						println("$name in ${currentState.stateName} | $currentMsg")
						println(" TROLLEY | trasfermimento e deposito WASTE-LOAD nel CONTAINER ")
					}
					 transition( edgeName="goto",targetState="pickup", cond=doswitchGuarded({ Another  
					}) )
					transition( edgeName="goto",targetState="home", cond=doswitchGuarded({! ( Another  
					) }) )
				}	 
			}
		}
}
