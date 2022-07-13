%====================================================================================
% analisiproblema description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxanalisiproblema, "localhost",  "TCP", "8078").
context(ctxraspy, "127.0.0.1",  "TCP", "8080").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( led, ctxraspy, "external").
  qactor( trolley, ctxanalisiproblema, "it.unibo.trolley.Trolley").
  qactor( wasteservice, ctxanalisiproblema, "it.unibo.wasteservice.Wasteservice").
  qactor( waste_truck_mock, ctxanalisiproblema, "it.unibo.waste_truck_mock.Waste_truck_mock").
