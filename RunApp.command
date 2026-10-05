#!/bin/bash
cd "$(dirname "$0")"
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
mvn clean javafx:run
