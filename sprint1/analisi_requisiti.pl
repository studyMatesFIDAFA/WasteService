%====================================================================================
% analisi_requisiti description   
%====================================================================================
context(ctxanalisirequisiti, "localhost",  "TCP", "8050").
 qactor( waste_service, ctxanalisirequisiti, "it.unibo.waste_service.Waste_service").
  qactor( waste_truck_mock, ctxanalisirequisiti, "it.unibo.waste_truck_mock.Waste_truck_mock").
