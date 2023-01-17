# TP1: Infrastructure et révision Java/Spring

## Compte-rendu

Ce TP n'est pas à rendre, mais comporte une série des questions pour vous guider sur ce qu'il faut en retirer.

Les questions sont se différencient du reste du texte de l'énoncé comme montré ci-dessous:

> Q1. Indiquer les noms, prénoms et numéros d'étudiants du binôme, ainsi que les références du sujet de TP.

## Environnement de travail

Avant de démarrer le TP, il convient d'installer les logiciels suivants sur votre machine:

- Docker Desktop pour [Windows / Mac OS X](https://www.docker.com/get-started).
  Pour Linux, voir: [Install Docker Engine](https://docs.docker.com/engine/install/)
  <!-- Toujours pour Linux, installer ensuite docker-compose (par exemple `sudo apt install docker-compose`sou Ubuntu) et suivre les [instructions pour gérer docker en tant qu'utilisateur normal](https://docs.docker.com/engine/install/linux-postinstall/ #manage-docker-as-a-non-root-user).-->
- Java 17: Utiliser par exemple [Adoptium](https://adoptium.net/), ou passer par [SDKMan](https://sdkman.io/)
- Un IDE Java. Sauf bonne raison, privilégier [IntelliJ/IDEA](https://www.jetbrains.com/idea/). Il est possible d'obtenir une licence étudiant pour la version Ultimate en utilisant l'email de l'université.
- Un shell bash ou zsh, ainsi que git. Normalement fourni sur Linux et Mac OS X. Pour Windows, utiliser par exemple [Git for Windows](https://gitforwindows.org/) qui intègre le shell bash.
- [JMeter](https://jmeter.apache.org/download_jmeter.cgi). Ne pas installer le package fourni avec Ubuntu qui est une vieille version.

<!-- À mettre à jour
Sous Mac OS X, il est possible de passer par [Homebrew](https://brew.sh/) pour tout installer:

```shell
brew cask install docker intellij-idea
brew install adoptopenjdk11 git jmeter
```

Sous Windows, il est possible d'utiliser [Chocolatey](https://chocolatey.org) pour tout installer:

```shell
choco install docker-desktop
choco install adoptopenjdk11
choco install intellijidea-ultimate
choco install git
choco install jmeter
```
-->

## Première partie: déploiement, infrastructure et intégration continue

Le dépôt contenant le code source du projet se trouve ici: https://forge.univ-lyon1.fr/tiw-is/tiw-is-2022-2023-sources

Faire un fork de ce projet dans votre espace et le partager au sein du binôme, puis cloner le projet pour travailler.

### Démarrage d'un serveur via docker

Si un serveur PostgreSQL est déjà démarré sur localhost, arrêter ce serveur pour libérer le port 5432.
Lancer la commande suivante pour vérifier qu'aucun serveur ne tourne via docker:

```shell
docker ps
```

Si un serveur tourne vérifier qu'il n'y a aucune référence aux ports 8080 et 5432.
Le cas échéant utiliser la commande suivante pour arrêter le conteneur, en remplaçant `<CONTAINER ID>` par l'identifiant obtenu via `docker ps`.

```shell
docker stop <CONTAINER ID>
```

> Q2. Combien de conteneurs tournaient déjà sur votre machine, lesquels ?
> Avez-vous eu besoin de les arrêter ?
> Donner la liste des commandes que vous avez exécutées.

Un serveur peut être démarré via la commande `docker` [doc ici][docker-cli], mais on utilisera ici `docker compose` qui s'appuie sur un fichier décrivant les serveurs à démarrer.
Consulter le fichier `docker-compose.yaml` et parcourir la [documentation de l'image postgres][doc-image-postgres].
Lancer le serveur PostgreSQL configuré via la commande suivante (doc)[docker-compose-cli]:

```shell
docker compose up -d
```

> Q3. Expliquer la commande ci-dessus. Vérifier que le serveur a bien été démarré
> et que la redirection de port est conforme au fichier `docker-compose.yaml`.

Lancer la commande suivante pour exécuter un shell `psql` à l'intérieur de conteneur `postgres` lancé via la commande `docker-compose` précédente:

```shell
docker compose exec postgres psql -U mdl mdldb
```

> Q4. Expliquer la commande ci-dessus.

Vérifier dans `psql` via la commande `\d` la liste des tables.

### Compilation, tests, packaging

Lancer la compilation, les tests de l'application `catalogue` via la commande:

```shell
mvn install
```

> Q5. D'après la [documentation][lifecycle-maven], quelles sont les différentes étapes qui ont été exécutées lors de la commande précédente ?

> Q6. La commande `\d` dans `psql` permet de lister les tables. Donner la liste des tables présentes en base après l'exécution de `mvn install`. Quelle étape parmi les précédentes est-elle susceptible d'avoir créer ces nouvelles tables ?

### Test de l'API

Lancer l'application en ligne de commande depuis le répertoire `catalogue`, une fois via:

```
java -jar target/catalogue-0.0.1-SNAPSHOT.jar
```

puis via

```
mvn spring-boot:run
```

> Q7. Quelle est la différence entre ces deux commandes ? Donner des avantages et des inconvénients aux deux commandes.

Une fois l'application lancée, utiliser (éventuellement depuis un autre terminal) la commande curl pour récupérer
Utiliser les commandes `curl` suivantes pour interagir avec l'application:

```
curl http://localhost:8080/machine

curl  --header "Content-Type: application/json" \
      -X POST \
      --data '{"nom":"Snoopy"}' \
      http://localhost:8080/machine

curl http://localhost:8080/machine
```

> Q8. Expliquer ce que font les commandes précédentes et comparer ce qui est renvoyé avec le contenu de la base PostgreSQL afin de faire des hypothèses sur le comportemant de l'application.

Lancer JMeter et ouvrir le fichier `src/test/jmeter/exemple.jmx` situé dans `catalogue`.

Déplier l'arborescence sous "Test Plan", puis exécuter le test (icône "lecture" verte en haut de la fenêtre).
Explorer le résultat des requêtes dans le "View Result Tree", puis regarder et comprendre les différents élements de l'arborescence.

> Q9. Indiquer où on peut retrouver chaque élement des commandes cURL précédentes (_i.e._ chaque argument de la ligne de commande) dans l'arborescence du test JMeter. Détailler les éléments sont présents dans le test JMeter et pas dans la série de commandes cURL.

### Intégration continue

Renommer le fichier `gitlab-ci.yml` en `.gitlab-ci.yml`.
Cela va activer l'intégration continue au prochain `git push` sur votre projet.
Faire ce _push_ et vérifier que les jobs d'intégration continue se déroulent correctement, regarder les logs d'exécution.

> Q10. Expliquer brièvement chaque ligne du fichier `.gitlab-ci.yml` en référençant la [documentation en ligne][gitlab-ci-ref] ou les autres fichiers de configuration du projet lorsque cela a du sens.

Regarder la [notion de profil en Spring][profils-spring] et regarder le contenu des fichier `*.properties` dans`src/main/resources`.

> Q11. Expliquer en particulier comment la configuration du login et du mot de passe de la base postgres sont transmis au conteneur postgres et à l'application Spring:
>
> 1. lors du développement sur votre machine
> 2. dans le cadre de l'intégration continue

### Qualité de code avec Sonarqube

#### Installation simplifiée

Le répertoire `sonar` du dépôt des sources contient un fichier docker-compose ainsi que des fichiers de configuration pour lancer une instance locale de [SonarQube][sonarqube].
Pour l'utiliser, il faut ouvrir un terminal dans le répertoire `sonar`, puis:

1. Lancer la commande `docker compose up -d`
2. Aller sur http://localhost:9000 puis s'authentifier avec `admin` et `admin`
3. Aller dans Administration > Configuration > General Settings > SCM, s'assurer que le paramètre "Disable the SCM Sensor" est à true (le bouton est "on")

Sonar est à présent normalement opérationnel sur votre machine.

Sous Linux: en cas d'erreur de démarrage de sonarqube (vérifier avec `docker compose logs -f sonarqube`), il faut faire des [ajustements sur les paramètres du noyaux][linux-sonar-config-noyau] (> How to use this image > Docker Host Requirements), avec les commandes suivantes (config temporaire réinitialisée au reboot de la machine):

- `sudo sysctl -w vm.max_map_count=262144`
- `sudo sysctl -w fs.file-max=65536`
- `sudo ulimit -n 65536`
- `sudo ulimit -u 4096`

Commandes (toujours depuis le répertoire `sonar`):

- Pour éteindre sonar: `docker compose stop`
- Pour redémarrer sonar: `docker compose start`
- Pour supprimer les données: `docker compose down`

On peut à présent lancer une analyse en procédant comme suit.

1. Créer un projet `catalogue-modeles`. Créer un nouveau token et bien noter celui-ci (par exemple `4f53a0ff3ca72aa8049a96bb05bcef0766a98bd6`)
2. Répondre aux questions suivantes (à priori "Java", puis "Maven") pour obtenir une commande à lancer pour effectuer l'analyse. Cette commande pourra être utiliser pour lancer les analyse ultérieures.

Explorer les retours de sonar sur le code de l'application et faire quelques corrections.

### Automatisation des tests d'API

Si ce n'est pas déjà fait, lancer l'application.
Le cas échéant, rouvrir le test jmeter (le renommer de manière plus parlante, par exemple "api-tests.jmx").
Compléter les tests d'API pour tester le chemin `/modele/{id}` où `{id}` est à remplacer par l'identifiant d'une VM préalablement créée.
Corriger les tests afin que tous le ou les _Thread Group_ passent.

On va ensuite les tests jmeter via le plugin maven dédié. Pour cela, il faut ajouter dans le `pom.xml`:

```
<plugin>
    <groupId>com.lazerycode.jmeter</groupId>
    <artifactId>jmeter-maven-plugin</artifactId>
    <version>2.9.0</version>
    <executions>
        <!-- Run JMeter tests -->
        <execution>
            <id>jmeter-tests</id>
            <goals>
                <goal>jmeter</goal>
            </goals>
        </execution>
        <!-- Fail build on errors in test -->
        <execution>
            <id>jmeter-check-results</id>
            <goals>
                <goal>results</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

On peut ensuite lancer le test via:

```
mvn jmeter:jmeter jmeter:results
```

Vérifier que tout fonctionne et corriger au besoin les problèmes.

> Q12. Quels sont les problèmes qui risquent de se poser si on souhaite lancer ces tests en intégration continue ?

On considère l'exemple de job gitlab-ci suivant (issu d'un autre projet):

```
test-maintenance-api:
  stage: test
  script:
    - cd root
    - mvn $MAVEN_CLI_OPTS install -DskipTests
    - cd maintenance-web
    - mvn $MAVEN_CLI_OPTS jetty:run 2>&1 > maintenance.jetty.log & mvn_jetty_pid=$!
    # wait for jetty to be ready for fail after 45s
    - for i in $(seq 10); do grep -q "Started Jetty Server" maintenance.jetty.log && break; [ $i = 10 ] && echo "Jetty failed to start" && cat maintenance.jetty.log && exit 1; echo "Jetty not ready, waiting 5s"; sleep 5; done
    - mvn $MAVEN_CLI_OPTS jmeter:jmeter jmeter:results || (kill $mvn_jetty_pid; exit 1)
    - kill $mvn_jetty_pid
```

Essayer de comprendre comment fontionne cet exemple et en quoi il est lié à la question précédente.

Attention le switch `-Dspring.profiles.active=ci` ne fonctionne pas avec `mvn spring-boot:run`. Par contre cela fonctionne quand on lance le jar avec java.

> Q13. Adapter le job gitlab-ci exemple pour pouvoir exécuter les tests jmeter dans votre pipeline gitlab (copier/coller le YAML du job gitlab-ci)

## Deuxième partie: codage Spring

### Métier de l'application Catalogue

L'application Catalogue déployée précédement dans le TP a pour objectif de
stocker les informations sur des machines d'une chaîne de production automobile.
Ces machines peuvent être créées, mises-à-jour et supprimées.

Par ailleurs cette application doit héberger l'ensemble des options d'automobiles ainsi que la configuration de chaque machine associée à chaque option. Cependant cette fonctionnalité n'est pas encore implémentée.

### Modification de `MachineController`

La classe `MachineCrontroller` contient des routes pour consulter, ajouter et mettre à jour des machines.

> Q14. Lister les routes (chemin + méthode HTTP) qui sont configurées dans ce contrôleur. Expliquer comment le code de retour HTTP est spécifié et comment les différents paramètres sont récupérés.

Ajouter les routes suivantes:

- `DELETE /machine/{id}`: supprime une machine
- `PUT /machine/{id}`: mets à jour les informations sur une machine (i.e. pour le moment son modèle).

Effectuer les modifications nécessaires dans le reste de l'application pour rendre ces routes fonctionnelles.

> Q15. Quelles sont la/les classes qui ont du être modifiées pour rendre l'application fonctionnelle ?

### Tests

Ajouter des tests JUnit afin de vérifier le comportement du composant `MachineService`.

**Remarque:** la classe `CatalogueApplicationTests` est annotée avec `@SpringBootTest`.
Spring va ainsi crééer le contexte de l'application avant d'exécuter les tests.
De plus cette classe peut utiliser des champs annotés avec `@Autowired` pour utiliser (et donc tester) des composants de ce contexte.

> Q16. Les méthodes de la classe `MachineService` ont-elles pu être testées indépendament les une des autres ?
> Si oui, expliquer comment.
> Si non, expliquer pourquoi.

Ajouter des tests JUnit et des étapes aux tests jmeter pour vérifier que votre code fonctionne correctement

> Q17. Indiquer les étapes et les vérifications que vous avez ajoutées aux tests JMeter

### Options

Créer une entité `Option` ayant un nom qui servira d'identifiant, ainsi qu'une description

Créer un DTO (`OptionDTO`) pour représenter les données de cette entité.

Enfin créer un service (que l'on appelera dans la suite `OptionService`) avec des méthodes métier pour créer, consulter, mettre à jour et supprimer des options.

Ajouter des tests JUnit pour vérifier que votre code est correct.

> Q18. Quelle(s) différence(s) y a-t-il entre les `Machine`s et les `Option`s (au delà du nom des champs, plutôt en termes de comportement et/ou de manière de les gérer) ?

Créer un contrôleur (`OptionController`) pour exposer une API REST de gestion des options.
Tester l'API en enrichissant vos tests JMeter.

> Q19. Quelle(s) différence(s) y a-t-il entre les tests de l'API "machine" et ceux de l'API "options" ?

### Exposer une description de l'API en OpenAPI en utilisant SpringDoc

Ajouter une dépendance vers [SpringDoc][springdoc] dans le `pom.xml` de `catalogue` ([voir ici][springdoc-started]).

Configurer l'application Catalogue afin d'exposer la description de l'API ([doc sur Baeldung][springdoc-baeldung]).

**Remarque:** par défaut l'interface web de swagger est exposée ici: http://localhost:8080/swagger-ui.html

> Q20. Quelle est l'URL à laquelle la description de cette API est exposée ?
> Quels sont les manques dans la description de l'API par rapport au comportement de votre application ?

### Configurations

Les options sont associées aux machines via des configurations. Cette association porte la configuration comme donnée.
Du point de vue de la modélisation Objet, on en fera donc une entité séparée.

Créer une entité `Configuration` ayant trois champs: `Machine machine`, `Option option` et `String cfg`.
Cette entité a les particularités suivantes:

- Son identifiant est composé de la machine et de l'option. Il faudra donc annoter les deux champs avec `@Id`, créer une classe ad-hoc pour représenter les clés composites associée (i.e. une classe avec deux champs `option` et `machine` et des méthodes `equals` et `hashcode` surchargées).
- Chacun des deux champs `machine` et `option` représente une associassion avec l'entité correrspondante, il faut donc l'annoter avec une des annotations d'assosiation (`@OneToOne`, `@OnteToMany`, `@ManyToOne`, `@ManyToMany`).
- Le champ `cfg` pourra être volumineux, il faut donc qu'il puisse être mappé sur un champ de caractères de grande taille, type Clob ou Text.

> Q21. Copier et commenter les déclarations des 3 champs.

Créer une classe `ConfigurationDTO` pour la classe `Configuration`.
Les champs `machine` et `option` devront contenir respectivement le numéro de machine et le nom de l'option.

Créer un service `ConfigurationService` et un controleur `ConfigurationController` pour les configuration permettant de récupérer, créer, modifier et supprimer des configurations.

> Q22. Quels sont les _repository_ utilisés par `COnfigurationService` et pourquoi le repository `ConfigurationRepository` n'est-il pas suffisant ?

Tester le bon fonctionnement de `ConfigurationService` avec JUnit.

> Q23. Quels sont les différents cas que vous avez testé ?

Ajouter au besoin les tests JMeter pour tester les différents controleurs que vous avez ajoutés.

### Lien de retour machine - configuration - option

Ajouter un champ `configurations` dans les machines de type `Collection<Configuration>`.
Bien utiliser le paramètre `mappedBy` dans l'annotation de ce champ.

> Q25. Copier/coller l'annotation que vous avez placée sur le champ `configurations`.

Ajouter un champ `options` de type `Collection<String>` dans `MachineDTO`.
Ce champ contiendra la liste des identifiants des options pour lesquelle une configuration de cette machine existe.

Modifier le code qui permet d'obtenir un `MachineDTO` à partir d'une machine de façon à gérer les champs `options`.

Modifier `MachineService` de façon à intégrer ces nouveaux champs.

**Remarque:** on ne souhaite pas laisser la possibilité de modifier la liste des configurations d'une machine via `MachineService`.

Compléter les tests du service.

> Q26. Quels sont les cas particuliers que vous avez eu à tester dans le cadre de l'ajout du champ du champ `configurations`?

Réaliser des modifications similaires aux options avec un champs `configurations` dans `Option` et un champs `machines` dans `OptionDTO`.

## Références

### Documents

- [CommonMark][commonmark]

### Docker

- [Docker CLI][docker-cli]
- [docker-compose][docker-compose-cli]
- [Documentation de l'image docker pour PostgreSQL][doc-image-postgres]

### Spring

- [Introduction aux profils Spring][profils-spring]
- [Configuration Maven pour SpringDoc][springdoc-maven], [Configuration Spring pour activer SpringDoc][springdoc-started], [Tutoriel SpringDoc][springdoc-baeldung]

### CI

- [Référence .gitlab-ci.yml][gitlab-ci-ref]

[commonmark]: https://commonmark.org/
[docker-cli]: https://docs.docker.com/engine/reference/commandline/cli/
[docker-compose-cli]: https://docs.docker.com/compose/reference/overview/
[doc-image-postgres]: https://hub.docker.com/_/postgres
[lifecycle-maven]: https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
[profils-spring]: https://www.baeldung.com/spring-profiles
[sonarqube]: https://sonarqube.org/
[gitlab-ci-ref]: https://docs.gitlab.com/ce/ci/yaml/
[springdoc]: https://springdoc.org/
[springdoc-started]: https://springdoc.org/#getting-started
[springdoc-maven]: https://springdoc.org/#plugins
[springdoc-baeldung]: https://www.baeldung.com/spring-rest-openapi-documentation
[linux-sonar-config-noyau]: https://hub.docker.com/_/sonarqube
