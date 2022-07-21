/*
wsminimal.js
*/

    var socket;

    function sendMessage(message) {
        var jsonMsg = JSON.stringify( {'name': message});
        socket.send(jsonMsg);
        console.log("Sent Message: " + jsonMsg);
    }

    function connect(){
        var host     =  document.location.host;
        var pathname =  "/"                   //document.location.pathname;
        var addr     = "ws://" +host  + pathname + "socket"  ;
        //alert("connect addr=" + addr   );

        // Assicura che sia aperta un unica connessione
        if(socket !== undefined && socket.readyState !== WebSocket.CLOSED){
             alert("WARNING: Connessione WebSocket già stabilita");
        }
        socket = new WebSocket(addr);

        socket.onopen = function (event) {
            //console.log("Connected to " + addr);
            setMessageToWindow(infoDisplay,"Connected to " + addr);
        };

        socket.onmessage = function (event) {
            //alert(`Got Message: ${event.data}`);
            msg = event.data;
			
            //alert(`Got Message: ${msg}`);
            console.log("ws-status:" + msg);
            if( msg.includes("TROLLEY") ) {
				m = elab_msg(msg);
                setMessageToWindow(pos_trolley,m);
				var stato = stato_trolley(m);
				setMessageToWindow(stato_trolley,stato);
			}
            else if(msg.includes("LED")) {
				m = elab_msg(msg);
                setMessageToWindow(led,msg); //""+`${event.data}`*/
			}
            else if(msg.includes("GBOX")){
				m = elab_msg(msg);
                setMessageToWindow(gbox,msg)
			}
            else if(msg.includes("PBOX")){
				m = elab_msg(msg);
                setMessageToWindow(pbox,msg)
			}
            else console.log("ERROR formato messaggio coap")
         };
    }//connect
	
	function elab_msg (m) {
		msg = m.split(":");
		return msg[1];
	}
	
	function stato_trolley (m) {
		if (m == "HOME" || m == "STOPPED") return m;
		else return "MOVIMENTO";
	}
		

