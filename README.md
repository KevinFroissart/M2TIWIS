# Rapport d'implémentation

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

## Partie 4
- Une tentative d'implémentation du processeur d'annotations @Controller a été effectuée.
- Des classes générées à partir des annotations ont pu être générées, mais l'utilisation sans erreurs n'a pas été possible.
- Après avoir lu la documentation de JavaPoet et essayé plusieurs implémentations, il ne m'a pas été possible de trouver une solution fonctionnelle.
- Les annotations restent présentes dans le framework web, du moins @Controller, vous pouvez tout de même y voir une esquisse de mes essais.