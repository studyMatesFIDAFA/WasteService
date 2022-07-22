%====================================================================================
% sonar description   
%====================================================================================
context(ctxanalisiproblema, "10.5.5.1",  "TCP", "8078").
context(ctxsonar, "localhost",  "TCP", "8079").
 qactor( sonarsimulator, ctxsonar, "sonarSimulator").
  qactor( sonardatasource, ctxsonar, "sonarHCSR04Support2021").
  qactor( datacleaner, ctxsonar, "dataCleaner").
  qactor( distancefilter, ctxsonar, "distanceFilter").
  qactor( sonarqak22, ctxsonar, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmastermock, ctxsonar, "it.unibo.sonarmastermock.Sonarmastermock").
