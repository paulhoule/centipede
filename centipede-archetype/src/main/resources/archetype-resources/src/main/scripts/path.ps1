#set( $symbol_dollar = '$' )
#set( $slashedGroup = $groupId.replace('.','\\') )

function ${artifactId} {
   java -jar ${symbol_dollar}env:userprofile\.m2\repository\\${slashedGroup}\\${artifactId}\\${symbol_dollar}{project.version}\\${artifactId}-${symbol_dollar}{project.version}-onejar.jar @args
}