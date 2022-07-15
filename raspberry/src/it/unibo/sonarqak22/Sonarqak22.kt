/* Generated by AN DISI Unibo */ 
package it.unibo.sonarqak22

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonarqak22 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 val simulate       = false
			   val sonarActorName = "sonarqak22"
			   val usingDomain    = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						sonarConfig.configureTheSonar( simulate, sonarActorName, usingDomain  )
					}
					 transition(edgeName="t00",targetState="activateTheSonar",cond=whenDispatch("sonaractivate"))
					transition(edgeName="t01",targetState="deactivateTheSonar",cond=whenDispatch("sonardeactivate"))
				}	 
				state("activateTheSonar") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if(  simulate  
						 ){forward("sonaractivate", "info(ok)" ,"sonarsimulator" ) 
						}
						else
						 {forward("sonaractivate", "info(ok)" ,"sonardatasource" ) 
						 }
					}
					 transition(edgeName="t02",targetState="handleSonarData",cond=whenEvent("sonar"))
					transition(edgeName="t03",targetState="deactivateTheSonar",cond=whenDispatch("sonardeactivate"))
				}	 
				state("deactivateTheSonar") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="end", cond=doswitch() )
				}	 
				state("handleSonarData") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("distance(V)"), Term.createTerm("distance(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val D = payloadArg(0)  
								emit("sonardata", "distance($D)" ) 
						}
					}
					 transition(edgeName="t04",targetState="handleSonarData",cond=whenEvent("sonar"))
					transition(edgeName="t05",targetState="deactivateTheSonar",cond=whenDispatch("sonardeactivate"))
				}	 
				state("end") { //this:State
					action { //it:State
						println("sonarqak22 BYE")
						 System.exit(0)  
					}
				}	 
			}
		}
}
