@echo off
rem @version $Revision: 700 $ ($Author: vlads $)  $Date: 2007-02-11 13:32:44 -0600 (Sun, 11 Feb 2007) $
title *Jetty:bytecode-webstart

call mvn -P debug webstart:jnlp

echo Go to http://localhost:8080/bytecode-webstart/

call mvn %* jetty:run

title Jetty:bytecode-webstart - ended

pause
