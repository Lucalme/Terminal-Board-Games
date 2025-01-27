#Livrable 1

- Pour compiler la classe board :  
`javac -classpath src src/board/*.java -d classes`

- Pour executer le .class
`java -classpath classes board/BoardMain`

- Pour compiler l'éxecutable:
`jar cfe livrable1_RC2.jar board.BoardMain -C classes .`

- Pour executer le .jar :
`java -jar livrable1_RC2.jar`

- Pour compiler les tests : 
`javac -classpath junit-console.jar:classes test/board/*.java`

- Pour executer les tests : 
`java -jar junit-console.jar -classpath test:classes -select-class board.BoardTests`



