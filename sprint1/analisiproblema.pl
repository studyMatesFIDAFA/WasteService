%====================================================================================
% analisiproblema description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxanalisiproblema, "localhost",  "TCP", "8078").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( trolley, ctxanalisiproblema, "it.unibo.trolley.Trolley").
  qactor( wasteservice, ctxanalisiproblema, "it.unibo.wasteservice.Wasteservice").
  qactor( waste_truck_mock, ctxanalisiproblema, "it.unibo.waste_truck_mock.Waste_truck_mock").
