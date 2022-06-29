%====================================================================================
% waste_service description   
%====================================================================================
context(ctx_waste_service, "localhost",  "TCP", "8080").
 qactor( trolley, ctx_waste_service, "it.unibo.trolley.Trolley").
  qactor( waste_service, ctx_waste_service, "it.unibo.waste_service.Waste_service").
  qactor( waste_truck_mock, ctx_waste_service, "it.unibo.waste_truck_mock.Waste_truck_mock").
