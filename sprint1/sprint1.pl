%====================================================================================
% sprint1 description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8050").
 qactor( trolley, ctxwasteservice, "it.unibo.trolley.Trolley").
  qactor( waste_service, ctxwasteservice, "it.unibo.waste_service.Waste_service").
  qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( guimock, ctxwasteservice, "it.unibo.guimock.Guimock").
