/*
ioutils.js
*/

    const stato_trolley     = document.getElementById("stato_trolley");
    const pos_trolley = document.getElementById("pos_trolley");
    const led    = document.getElementById("led");
    const pbox = document.getElementById("pBox");
    const gbox = document.getElementById("gBox");

    function setMessageToWindow(outfield, message) {
         var output = message
         outfield.innerHTML = `<tt>${output}</tt>`
    }

    function addMessageToWindow(outfield, message) {
         var output = message.replace("\n","<br/>")
          outfield.innerHTML += `<div>${output}</div>`
    }

