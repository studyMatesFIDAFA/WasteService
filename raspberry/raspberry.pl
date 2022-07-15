%====================================================================================
% raspberry description   
%====================================================================================
context(ctxanalisiproblema, "10.5.5.1",  "TCP", "8078").
context(ctxraspy, "localhost",  "TCP", "8080").
 qactor( sonarsimulator, ctxraspy, "sonarSimulator").
  qactor( sonardatasource, ctxraspy, "sonarHCSR04Support2021").
  qactor( datacleaner, ctxraspy, "dataCleaner").
  qactor( distancefilter, ctxraspy, "distanceFilter").
  qactor( sonarqak22, ctxraspy, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmastermock, ctxraspy, "it.unibo.sonarmastermock.Sonarmastermock").
  qactor( led, ctxraspy, "it.unibo.led.Led").
