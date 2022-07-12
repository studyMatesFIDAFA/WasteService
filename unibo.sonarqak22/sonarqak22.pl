%====================================================================================
% sonarqak22 description   
%====================================================================================
context(ctxanalisiproblema, "127.0.0.1",  "TCP", "8058").
context(ctxsonarqak22, "localhost",  "TCP", "8080").
 qactor( sonarsimulator, ctxsonarqak22, "sonarSimulator").
  qactor( sonardatasource, ctxsonarqak22, "sonarHCSR04Support2021").
  qactor( datacleaner, ctxsonarqak22, "dataCleaner").
  qactor( distancefilter, ctxsonarqak22, "distanceFilter").
  qactor( sonarqak22, ctxsonarqak22, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmastermock, ctxsonarqak22, "it.unibo.sonarmastermock.Sonarmastermock").
