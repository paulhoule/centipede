#!/bin/sh
#set( $symbol_dollar = '$' )
#set( $slashedGroup = $groupId.replace(".","/") )
alias ${artifactId}="java -jar $HOME/.m2/repository/${slashedGroup}/${artifactId}/${symbol_dollar}{project.version}/${artifactId}-${symbol_dollar}{project.version}.one-jar.jar"
