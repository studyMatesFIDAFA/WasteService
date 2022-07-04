%====================================================================================
% analisi_problema description   
%====================================================================================
context(ctxanalisiproblema, "localhost",  "TCP", "8050").
 qactor( trolley, ctxanalisiproblema, "it.unibo.trolley.Trolley").
  qactor( wasteservice, ctxanalisiproblema, "it.unibo.wasteservice.Wasteservice").
