%====================================================================================
% analisi_problema description   
%====================================================================================
context(ctxanalisiproblema, "localhost",  "TCP", "8050").
 qactor( trolley, ctxanalisiproblema, "it.unibo.trolley.Trolley").
  qactor( wasteservice, ctxanalisiproblema, "it.unibo.wasteservice.Wasteservice").
  qactor( wastetruckmock, ctxanalisiproblema, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wastetruckmock2, ctxanalisiproblema, "it.unibo.wastetruckmock2.Wastetruckmock2").
