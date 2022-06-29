%====================================================================================
% analisi_requisiti description   
%====================================================================================
context(ctxanalisirequisiti, "localhost",  "TCP", "8080").
 qactor( trolley, ctxanalisirequisiti, "it.unibo.trolley.Trolley").
  qactor( waste_service, ctxanalisirequisiti, "it.unibo.waste_service.Waste_service").
  qactor( sonar, ctxanalisirequisiti, "it.unibo.sonar.Sonar").
  qactor( led, ctxanalisirequisiti, "it.unibo.led.Led").
  qactor( waste_truck_mock, ctxanalisirequisiti, "it.unibo.waste_truck_mock.Waste_truck_mock").
