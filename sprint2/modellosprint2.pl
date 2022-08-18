%====================================================================================
% modellosprint2 description   
%====================================================================================
context(ctxled, "10.5.5.5",  "TCP", "8080").
context(ctxsonar, "10.5.5.5",  "TCP", "8081").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwastetruck, "localhost",  "TCP", "8021").
context(ctxanalisiproblema, "localhost",  "TCP", "8078").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( led, ctxled, "external").
  qactor( trolley, ctxanalisiproblema, "it.unibo.trolley.Trolley").
  qactor( wasteservice, ctxanalisiproblema, "it.unibo.wasteservice.Wasteservice").
  qactor( sonar, ctxsonar, "it.unibo.sonar.Sonar").
  qactor( waste_truck_mock, ctxwastetruck, "it.unibo.waste_truck_mock.Waste_truck_mock").
