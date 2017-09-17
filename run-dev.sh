#!/bin/bash
mvn clean package
java -jar target/vertigo-1.0-fat.jar -conf src/main/conf/vertigo-conf.json
