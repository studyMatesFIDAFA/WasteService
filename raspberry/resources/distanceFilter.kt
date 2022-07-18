
import alice.tuprolog.Term
import alice.tuprolog.Struct
import it.unibo.kactor.*
import unibo.actor22.annotations.Context

class distanceFilter (name : String ) : ActorBasic( name ) {
	init {
	    RaspberryConfigurator.setTheConfiguration("./raspberryConfigurator.json")
	}
	val limitDistance = RaspberryConfigurator.dlimit
	var sospeso = false
	var conn = ConnTcp(RaspberryConfigurator.ip_wasteService, RaspberryConfigurator.porta_wasteService)  // mettere ip del proprio pc

    override suspend fun actorBody(msg: IApplMessage) {
		if( msg.msgSender() == name) return //AVOID to handle the event emitted by itself
		if( msg.msgContent().contains("alarm") )return
  		elabData( msg )
 	}

 	
@kotlinx.coroutines.ObsoleteCoroutinesApi

	  suspend fun elabData( msg: IApplMessage ){ //OPTIMISTIC
 		val data  = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0).toString()
  		//println("$tt $name |  data = $data ")
		val Distance = Integer.parseInt( data )

/*
 * Emit a sonarRobot event to test the behavior with MQTT
 * We should avoid this pattern
*/	
//	 	val m0 = MsgUtil.buildEvent(name, "sonarRobot", "sonar($data)")
//	 	emit( m0 )
 		if( Distance < limitDistance && !sospeso){
	 		val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($data)")
			println("$tt $name |  emit m1= $m1")
			//emit(m1)
			sospeso=true
			val m2 = MsgUtil.buildDispatch(name, "stop", "stop($data)", "wasteservice")
		 	conn.forward(m2.toString())

			//emitLocalStreamEvent( m1 ) //propagate event obstacle
			//forward("stop", "stop($data)", "trolley")
     	}else if(Distance>= limitDistance && sospeso){
			val m1 = MsgUtil.buildEvent(name, "noobstacle", "noobstacle($data)")
			println("$tt $name |  emit m1= $m1")
			//emit(m1)
			sospeso=false
			val m2 = MsgUtil.buildDispatch(name, "resume", "resume($data)", "wasteservice")
			//sendMessageToActor(m2, "trolley")
			conn.forward(m2.toString())
			//forward("resume", "resume($data)", "trolley")
 		}
		else{
			//println("Scarto distanza $Distance")
		}
 	}
}