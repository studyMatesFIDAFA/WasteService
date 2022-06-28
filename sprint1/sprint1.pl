%====================================================================================
% sprint1 description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8050").
 qactor( trolley, ctxwasteservice, "it.unibo.trolley.Trolley").
  qactor( waste_service, ctxwasteservice, "it.unibo.waste_service.Waste_service").
  qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wastetruckmock2, ctxwasteservice, "it.unibo.wastetruckmock2.Wastetruckmock2").
  qactor( wastetruckmock3, ctxwasteservice, "it.unibo.wastetruckmock3.Wastetruckmock3").
