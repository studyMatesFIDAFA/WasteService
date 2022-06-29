%====================================================================================
% waste_service description   
%====================================================================================
context(ctx_waste_service, "localhost",  "TCP", "8080").
 qactor( transport_trolley, ctx_waste_service, "it.unibo.transport_trolley.Transport_trolley").
  qactor( waste_truck, ctx_waste_service, "it.unibo.waste_truck.Waste_truck").
  qactor( waste_service, ctx_waste_service, "it.unibo.waste_service.Waste_service").
