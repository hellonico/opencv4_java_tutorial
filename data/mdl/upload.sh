#!/bin/bash
mvn deploy:deploy-file -DgroupId=networks.$2 -DartifactId=$3 -Dversion=1.0.0 -DgeneratePom=true -Dpackaging=zip -DrepositoryId=vendredi -Durl=https://repository.hellonico.info/repository/hellonico/ -Dfile=$1

