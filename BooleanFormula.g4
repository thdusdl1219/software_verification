grammar BooleanFormula;

NOT : '!'  ;
AND : '&&' ;
OR  : '||' ;
IMP : '=>' ;
 
TRUE  : 'true' ;
FALSE : 'false' ;
  
LPAREN : '(' ;
RPAREN : ')' ;
  
ID : 'p'[1-9][0-9]* ;
WS : [ \r\t\u000C\n]+ -> skip ; 
 
formula
 : op=NOT  sub=formula					# unaryFormula
 | left=formula  op=AND  right=formula	# binaryFormula
 | left=formula  op=OR   right=formula	# binaryFormula
 | left=formula  op=IMP  right=formula	# binaryFormula
 | LPAREN  sub=formula  RPAREN			# parenFormula
 | name=ID								# variable
 | value=(TRUE|FALSE)					# constant
 ;
 

