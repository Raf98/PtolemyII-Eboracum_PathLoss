@echo off
rem This script was automatically generated by makebat.
rem DO NOT EDIT.  Instead, edit ptinvoke.in, and run make.

if "%PTII%" == "" set PTII=/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1
set params=
:start
if "%1" == "" goto stop
set params=%params% %1
shift
goto start
:stop

"\usrin\javaw"   "-Djava.security.policy=${PTII}/bin/policy.all" "-Doutrigger.spacename=JavaSpaces" "-Dcom.sun.jini.lookup.groups=public" "-Djava.rmi.server.codebase=http://localhost:4444/" "-Dpython.home=%PTII%/lib" -Xmx1024M "-Dptolemy.ptII.dir=%PTII%"    -classpath "%PTII%:%PTII%/lib/diva.jar:%PTII%/ptolemy/distributed/jini/jar/jini-core.jar:%PTII%/ptolemy/distributed/jini/jar/jini-ext.jar:%PTII%/ptolemy/distributed/jini/jar/sun-util.jar:%PTII%/lib/ptCal.jar:%PTII%/lib/java_cup.jar:%PTII%/lib/ptcolt.jar:%PTII%/lib/sootclasses.jar:%PTII%/lib/jasminclasses.jar:%PTII%/lib/polyglotclasses-1.3.2.jar::/usr/lib/jvm/java-14-openjdk-amd64/lib/jce.jar:%PTII%/lib/ptjacl.jar:%PTII%/lib/jython.jar:%PTII%/lib/kieler.jar:/gdk.jar:%PTII%/ptolemy/actor/lib/database/mysql-connector-java-5.1.6-bin.jar:%PTII%/ptolemy/actor/lib/database/ojdbc6.jar:%PTII%/lib/PDFRenderer.jar:%PTII%/ptolemy/actor/ptalon/antlr/antlr.jar:%PTII%/lib/mapss.jar:%PTII%/lib/saxon8.jar:%PTII%/lib/saxon8-dom.jar:/usr/lib/jvm/java-14-openjdk-amd64/../Classes/classes.jar:%PTII%/ptolemy/domains/ptinyos/lib/jdom.jar:%PTII%/ptolemy/domains/ptinyos/lib/nesc.jar" ptolemy.actor.gui.MoMLApplication   %params%
pause
