%====================================================================================
% raspberry description   
%====================================================================================
context(ctxanalisiproblema, "127.0.0.1",  "TCP", "8058").
context(ctxrapsy, "localhost",  "TCP", "8080").
 qactor( sonarsimulator, ctxrapsy, "sonarSimulator").
  qactor( sonardatasource, ctxrapsy, "sonarHCSR04Support2021").
  qactor( datacleaner, ctxrapsy, "dataCleaner").
  qactor( distancefilter, ctxrapsy, "distanceFilter").
  qactor( sonarqak22, ctxrapsy, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmastermock, ctxrapsy, "it.unibo.sonarmastermock.Sonarmastermock").
  qactor( led, ctxrapsy, "it.unibo.led.Led").
