//package rx
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.IApplMessage
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.runBlocking

/*
-------------------------------------------------------------------------------------------------
 
-------------------------------------------------------------------------------------------------
 */

class sonarSimulator ( name : String ) : ActorBasic( name ) {
	var goon = true
	val data = sequence<Int>{
		var v0 = 80
		yield(v0)
		/*
		while(true){
			v0 = v0 - 5
			yield( v0 )
		}*/
		/*
		var i=0
		while(true){
			if(i%5==0)
			{
				v0 = 70
			}
			if(i%11==0)
			{
				v0 = 80
			}
			i=i+1
			println(i)
			yield( v0 )

		}
		*/
	}

    override suspend fun actorBody(msg : IApplMessage){
  		//println("$tt $name | received  $msg "  )  //RICEVE GLI EVENTI!!!
		if( msg.msgId() == "sonaractivate") startDataReadSimulation(   )
		if( msg.msgId() == "sonardeactivate") goon=false
	}

	suspend fun startDataReadSimulation(    ){
  			var i = 0
			var dist = 0
			//i < 50 && goon
			while( true ){
				//Versione FABIO
				if(i<15) {
					dist = 80
				}
				else if ( i>= 15 && i<30)
				{
					dist = 70
				}
				/*
				else if (i>=30 && i<35){
					dist = 80
				}
				else if (i>=35 && i<50){
					dist = 70
				}
				else if (i>=50 && i<60){
					dist = 80
				}
				else if(i>=60 && i<70){
					dist = 70
				}
				 */
				else{
					dist = 80
				}
				println("SonarSim: $dist");
				val m1 = "distance($dist)"
				val event = MsgUtil.buildEvent( name,"sonar",m1)
				emitLocalStreamEvent( event )
				delay( 500 )
				i++


				//Versione prof
			/*
				val m1 = "distance( ${data.elementAt(i*2)} )"
				i++
 				val event = MsgUtil.buildEvent( name,"sonar",m1)								
  				emitLocalStreamEvent( event )
 				//println("$tt $name | generates $event")
 				//emit(event)  //APPROPRIATE ONLY IF NOT INCLUDED IN A PIPE
 				delay( 500 )

 	 			 */
  			}

			terminate()
	}

} 
