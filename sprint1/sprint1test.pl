%====================================================================================
% sprint1test description   
%====================================================================================
context(ctxwasteservicetest, "localhost",  "TCP", "8050").
 qactor( trolley, ctxwasteservicetest, "it.unibo.trolley.Trolley").
  qactor( waste_service, ctxwasteservicetest, "it.unibo.waste_service.Waste_service").
  qactor( wastetruckmock, ctxwasteservicetest, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wastetruckmock2, ctxwasteservicetest, "it.unibo.wastetruckmock2.Wastetruckmock2").
  qactor( wastetruckmock3, ctxwasteservicetest, "it.unibo.wastetruckmock3.Wastetruckmock3").
