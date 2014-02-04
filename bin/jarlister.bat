@echo off
set JARLISTER_HOME=%~dp0..
scala -nc "%JARLISTER_HOME%\target\scala-2.11.0-M8\jarlister_2.11.0-M8-1.0.jar" %*
