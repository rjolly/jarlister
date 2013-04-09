@echo off
set JARLISTER_HOME=%~dp0..
scala "%JARLISTER_HOME%"\target\scala-2.11\jarlister_2.11-1.0.jar %*
