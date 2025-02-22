## Livrable 2

Pour ce livrable, afin de palier à nos difficultés de modélisation et d'organisation, on a décidé de créer un prototype du jeu Ares qui nous servira de base à améliorer. Ceci nous permet de nous rendre compte des besoins de l'application en termes de modèle. On est donc sur une approche naïve du type "Proof of Concept".
Nous avons écrit les composants de base du jeu complet, à savoir les classes buildings, player, actions, et Game. On a également fait une tentative d'implémentation graphique (GUI) du jeu avec les bibliothèques standards du JDK.
La suite de nos travaux se portera donc sur l'implémentation des composants manquants, les objectifs, mais également sur l'amélioration du modèle existant à travers une concertation sur les choix de modélisation mais aussi sur l'écriture de tests adaptés afin d'anticiper d'éventuels ajouts d'extensions. 
 

- Pour compiler les classes :  
- `javac -classpath src src/board/*.java -d classes`
- `javac -classpath src src/player/*.java -d classes`
- `javac -classpath src src/action/*.java -d classes`
- `javac -classpath src src/building/*.java -d classes`
- `javac -classpath src src/Game/*.java -d classes`
- `javac -classpath src src/ares/*.java -d classes`

- Pour démarrer une partie (WIP):
`java -classpath classes ares/AresMain`

- Pour tester l'implémentation graphique:
`java -classpath classes ares/GUIAres`

- Pour compiler l'éxecutable:
`jar cfe livrable2.jar ares.AresMain -C classes .`

- Pour executer le .jar :
`java -jar livrable2.jar`

- Pour compiler les tests : 
`javac -classpath junit-console.jar:classes test/board/*.java`

- Pour executer les tests : 
`java -jar junit-console.jar -classpath test:classes -select-class board.BoardTests`



