%====================================================================================
% analisiproblema description   
%====================================================================================
context(ctxraspy, "10.5.5.5",  "TCP", "8080").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxanalisiproblema, "localhost",  "TCP", "8078").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( led, ctxraspy, "external").
  qactor( trolley, ctxanalisiproblema, "it.unibo.trolley.Trolley").
  qactor( wasteservice, ctxanalisiproblema, "it.unibo.wasteservice.Wasteservice").
