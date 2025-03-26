## Livrable 3

On a avancé sur les actions, en proposant un modèle d'héritage pour chaque action. On a également des classes helpers pour gérer les actions. Chaque action est constituée comme suit:  

    - Une méthode statique isPossible() qui permet de savoir si on peut proposer l'action au joueur en premier lieu. Par exemple on ne lui proposera pas si il n'a pas suffisament de resources ou si certaines préconditions ne sont pas remplies.

    - Un Constructeur, qui prend differents arguments selon le type d'action. On a un switch dans la classe ActionMaker qui gère l'appel de ce constructeur en fournissant les arguments en promptant les entrées nécessaires au joueur.
    
    - Une méthode isInstancePossible, qui vérifie les que les conditions spécifiques à l'instance de l'action sont réunies.

    - Une méthode Effect(), qui sera l'effet de l'action.

    - Une Description(), qui fournira la représentation textuelle de l'action après éxécution. 


Un test de partie automatique, jouée par des bots, a été fait pour Ares. Pour lancer la partie bot vs bot, executer le jar : 
`jar cfe livrable2.jar ares.AresMain -C classes .`
(Pour le recompiler, en cas de problème de compatibilité : `jar cfe livrable3.jar ares.Livrable3 -C classes .`)