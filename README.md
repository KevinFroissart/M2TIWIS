# Rapport d'implémentation du TP2

## Dépendances
- Toutes les dépendances ont été maintenues au minimum, à part une dépendance a web-framework et à Jackson.

## Use cases et méthodes
- Tous les use cases ont été implémentés et testés.
- Toutes les méthodes ont été documentées ou héritent de la documentation de leur interface.

## Implémentation des parties 1 à 3
- Les parties 1 à 3 ont été implémentées.
- Le fichier de configuration a été modifié pour spécifier les classes qui nécessitent l'implémentation d'une interface.
- Le moteur de containérisation permet de containériser les classes présentes dans le fichier de configuration de manière dynamique.
- Seuls les paramètres de DBAccess ne sont pas "dynamiques", mais ils pourraient être récupérés de la même manière s'ils étaient stockés dans un fichier application.properties.

## Contrôleurs et tests
- Les contrôleurs dirigent les requêtes vers les services ou ressources en fonction de leur lien avec le DAO (Restful ou non).
- La classe ServeurImplTest.java teste chaque route avec un maximum de cas de test, pour vérifier le bon fonctionnement des use cases et des exceptions.
- Chacun des tests a été modifié et adapté pour suivre la template `should...when...with...` et les tags `Given`, `When` et `Then`.
- Concernant les tests, si le temps me l'avait permis, j'aurais ajouté un fichier de configuration spécifique aux tests, de manière à utiliser une seconde base de données pour les tests, qui se viderait à chaque lancement.
- La quasi-totalité des méthodes et routes ont été testées, mise-à-part les SQLException qui dans certains cas ne sont pas testables en l'état, et l'aurait été grâce à l'utilisation de Mockito par exemple.

![GeneratedClasses.PNG](commandes%2Fsrc%2Fmain%2Fresources%2Fcaptures%2FGeneratedClasses.PNG)

### Un détail supplémentaire concernant les tests...
L'ajout des annotations semble créer des problèmes au sein du projet, du moins au niveau des tests.  
En effet, la tâche `mvn test` renvoie une erreur de compilation si deux ou plus d'annotations sont présentes dans le projet.  
Il est tout de même possible de lancer les tests via l'IDE malgré cette erreur.  
Vous pouvez alors exécuter l'ensemble des tests en faisant clic droit sur le dossier `test/java` puis `Run 'All Tests'`.  
De plus vous pouvez vérifier que les tests sont bien lancés en exécutant la commande `mvn clean install` ou `mvn clean package`, étape à faire aussi avant de lancer les tests avec l'IDE.

En espérant vous simplifier la vie, j'ai ajouté une branche avec une version du projet qui fonctionne sans les annotations, si vous souhaitez lancer les tests depuis `mvn test` ou que vous souhaitez adresser le problème.  
La branche s'appelle `TP2_sans_annotations`.

## Partie 4
- Les annotations `@Controller`, `@Service`, `@Persitence` et `@Component` ont été implémentées.
- DBAccess a été annoté avec `@Persistence` et suit un traitement spécial de manière à injecter les paramètres `dbUrl`, `username` et `password`.
- Toutes les classes se génèrent avec les constructeurs, paramètres et méthodes requises.
- Nous ne sommes cependant pas parvenus à utiliser les classes générées dans le serveur, c'est la raison pour laquelle nous avons laissé les `extends` dans les classes injectées par le serveur.

![GeneratedClasses.PNG](commandes%2Fsrc%2Fmain%2Fresources%2Fcaptures%2FGeneratedClasses.PNG)

## Lancez le projet
- Positionnez-vous à la racine du projet et exécutez la commande `mvn clean install`.
