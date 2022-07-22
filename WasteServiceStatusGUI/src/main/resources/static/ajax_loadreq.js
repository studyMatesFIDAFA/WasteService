function callback(theXhr)
{
	if(theXhr.readyState===4 && theXhr.status===200)
	{
		var risposta = theXhr.responseText;
		//console.log(risposta);
		document.getElementById("response").value=risposta;
	}
}

function sendloadreqAjax(theXhr, url, parametri){
    theXhr.onreadystatechange = function(){ callback(theXhr); };
    try
    {
        theXhr.open("POST", url, true);
    }
    catch(e)
    {
        alert(e);
    }
    theXhr.setRequestHeader("connection", "close");
    theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    theXhr.send(parametri);
}

function sendloadreq(){
    var xhr= new XMLHttpRequest();
    var url="load_req";
    var tipo = document.getElementById("tipo").value;
    var qta = document.getElementById("qta").value;
    var parametri = "tipo="+tipo+"&qta="+qta;
    //console.log(parametri)
    if(xhr)
    {
    		sendloadreqAjax(xhr, url, parametri);
    }
    else
    {
    	alert("Impossibile usare ajax per le richieste");
    }
}