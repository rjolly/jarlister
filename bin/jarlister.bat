@echo off
set JARLISTER_HOME=%~dp0..
scala -nc "%JARLISTER_HOME%\target\scala-2.11\jarlister_2.11-1.1.jar" %*
