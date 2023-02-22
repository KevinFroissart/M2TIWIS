# TP3 : Communication par messages asynchrone avec RabbitMQ

### Q0.1. Indiquer les noms, prénoms et numéros d'étudiants du binôme.

- FROISSART Kévin p2002504
- DECHER Emily p1801979

## 1. Prise en main du code fourni
### Q1.1. Lister les routes et les composants qui sont déjà définis dans chain-manager.
Les routes présentes dans chain-manager sont :
- `GET '/voiture/'` : qui permet de récupérer la liste des voitures
- `POST '/voiture/'` : qui permet d'ajouter une voiture

Les composants présents dans chain-manager sont :
- `VoitureController` : qui permet de gérer les routes liées aux voitures
- `VoitureService` : qui permet de gérer les voitures
- `VoitureRepository` : qui permet de gérer les voitures dans la base de données
- `Voiture` : qui permet de définir une voiture
- `VoitureDTO` : qui permet de définir une voiture en DTO

- `MachineService` : qui permet de gérer les machines
- `MachineDTO` : qui permet de définir une machine en DTO

- `ConfigurationConfirmationReceiver` : qui permet de gérer la réception des messages

- `ChainManagerApplication` : qui permet de lancer l'application

### Q1.2. Sur quel port l'application chain-manager écoute-elle ? Où se port est-il configuré ?
L'application chain-manager écoute sur le port 8081. Ce port est configuré dans le fichier `application.properties` dans le dossier `resources` de l'application.

### Q1.3. Lister les routes et les composants qui sont déjà définis dans machine. Expliquer quel morceau de configuration empêche l'application de démarrer un serveur web alors que la dépendance sur spring-boot-stater-web est bien présente dans le pom.xml.
Il n'y a pas de route définie dans machine. Les composants présents dans machine sont :
- `ConfigurationService` : qui permet de récupérer le nom de la *queue* sur laquelle la machine va recevoir les messages
- `Runner` : qui permet de recevoir les messages
- `MachineApplication` : qui permet de lancer l'application

Le paramètre `spring.main.web-application-type=none` dans le fichier `application.properties` de `machine` empêche l'application de démarrer un serveur web.

### Q1.4. Expliquer pourquoi machine affiche Reçoit les messages sur la queue 'queue-machine1' au démarrage. Ce message reflète-il la réalité en l'état ?
Le fichier `Runner` implémente l'interface `CommandLineRunner` qui permet de lancer une méthode au démarrage de l'application. Cette méthode affiche le message `Reçoit les messages sur la queue 'queue-machine1'`.    
Ce message ne reflète pas la réalité, en l'état, l'application `machine` ne reçoit pas de messages, elle pourra le faire plus tard losrque l'on ajoutera un RabbitListener.

### Q1.5. chain-manager est configurée pour recevoir des messages. Sur quelle queue va-t-elle les chercher ?
chain-manager va chercher les messages sur la queue `chainmanager` tel que défini dans son `application.properties`.

### Q1.6. L'application chain-manager a-t-elle traité le message ? Quelle(s) méthode(s) ont-elles été appelée(s) ? Ajoutez au besoin des logs pour vérifier votre hypothèse.
Oui, l'application chain-manager a traité le message.    
La méthode `receive(byte[] message)` de la classe `ConfigurationConfirmationReceiver` a été appelée, elle appelle ensuite `receive(String message)` de la même classe.

## 2. Clients REST
### Q2.1. Quelles modifications avez-vous apporté à chain-manager ? Copier/coller le code de getMachines.
Dans un premier nous avons ajouté un `@Bean` `RestTemplate` dans la classe `ChainManagerApplication` pour pouvoir utiliser `RestTemplate` dans les autres classes.   
Nous avons ensuite injecté `RestTemplate` dans la classe `MachineService` pour pouvoir communiquer avec l'application `CatalogueApplication`.   

Voici le code de `getMachines` mis à jour :
```java
public Collection<MachineDTO> getMachines() {
	ResponseEntity<MachineDTO[]> response = restTemplate.getForEntity("http://localhost:8080/machine", MachineDTO[].class);
	return Arrays.asList(response.getBody());
}
```

### Q2.2. Y a-t-il besoin d'informations additionnelles pour réaliser ces changements ? Si oui, lesquelles ?
Pour réaliser les changements, il faut ajouter les informations de connexion à l'application Catalogue et s'assurer que les deux applications sont bien en communication.
Voici notre `application.properties` après avoir ajouter ces informations :
```properties
spring.main.web-application-type=none
tiw.is.machine.queue=queue-machine1
tiw.is.catalogue.url=http://localhost:8080
tiw.is.catalogue.ping-interval=30000
```

### Q2.3. Quels problèmes risquent de se poser avec la gestion des informations des machines du catalogue telle qu'elle est proposée ?
Les problèmes qui pourraient se poser avec la gestion des informations des machines dans le catalogue telle qu'elle est proposée sont les suivants :

- Si plusieurs instances de l'application machine sont exécutées, il est possible qu'elles créent chacune une nouvelle entrée dans le catalogue pour la même machine.   
Pour éviter cela, il faudrait utiliser une clé unique pour identifier chaque machine et empêcher la création de doublons.
- Si l'application machine n'arrive pas à contacter l'application catalogue, elle ne pourra pas mettre à jour ses informations ou vérifier que sa machine est bien présente dans le catalogue.   
Il faudrait donc gérer les erreurs de communication entre les deux applications et avoir un plan de secours en cas de défaillance d'une des applications.

### Q2.4. Quels changements avez-vous apporté aux applications et en quoi ces changements résolvent-ils le problème précédent ?
Dans un premier temps nous avons ajouté toutes les informations de connexion dans le fichier `application.properties` de l'application `machine`.     
Ensuite, nous avons mis en place la classe `CataloguePing` qui permet de vérifier que l'application `machine` est bien en communication avec l'application `catalogue` en lançant un ping à interval régulier.
Les pings non fructueux sont loggés afin de pouvoir les traiter en cas de problème.   

Une classe `CatalogueInitializer` a été ajoutée dans l'application `machine` pour s'assurer que la machine est bien présente dans le catalogue en faisant un appel à la méthode `getOrCreateMachine` du service `CatalogueService` créé a cet effet.
Si la machine n'est pas présente dans le catalogue, elle est créée, grâce à des appels RestTemplate à l'application `catalogue`.   

Ces changements permettent de résoudre le problème de création de doublons dans le catalogue et de vérifier que l'application `machine` est bien en communication avec l'application `catalogue`.


## 3. Réception de message par les machines
### Q3.1. Quelles erreurs peuvent se produire lors de la gestion du JSON ? Quels problèmes se posent si on souhaite gérer correctement ces erreurs ?
Pour gérer les JSON, les erreurs suivantes peuvent se produire :

- Erreur de syntaxe : Le JSON est malformé
- Erreur de deserialization : Le JSON est valide, mais ne peut pas être converti en objet Java

Si on souhaite gérer correctement ces erreurs, plusieurs problèmes se posent.   
Si les objets sont sérialisés et que l'on souhaite les désérialiser, il faut que les classes Java correspondent exactement à la structure du JSON.   
Ce qui nous oblige à soit avoir une dépendance directe vers le projet qui contient les classes Java, soit à dupliquer les classes Java dans le projet qui désérialise les objets.   

Par choix de simplicité, j'ai décidé de dupliquer le code, et dans certains cas de transformer les objets en `String` pour les envoyer dans la queue et les traiter comme de pures JSON.   
De cette manière, aucun problème de déserialisation ne se pose, puisqu'on l'on utilise des structures de données identiques dans les deux projets.

### Q3.2 Copier/coller le code de votre @RabbitListener
Voici le code de notre `@RabbitListener` :
```java
@Slf4j
@Component
public class RabbitMQListener {

	private final ConfigurationService configurationService;

	@Autowired
	public RabbitMQListener(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@RabbitListener(queues = "${tiw.is.machine.queue}")
	public void receiveReconfiguration(@Payload String payload) {
		log.info("RabbitListener received: {}", payload);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Voiture voiture = objectMapper.readValue(payload, Voiture.class);
			configurationService.reconfigure(voiture);
		}
		catch (Exception e) {
			log.error("Error deserializing Voiture object: ", e);
		}
	}
}
```
## 4. Envoi de message par chain-manager et machine
### Q4.1 Copier/coller le code de la méthode envoieOptionsVoiture
Voici le code de la méthode `envoieOptionsVoiture` :
```java
public void envoieOptionsVoiture(String queueName, Voiture voiture) throws JsonProcessingException {
	Collection<String> options = voiture.getOptions();
	log.info("Envoi des options '{}' pour la voiture {} sur la queue '{}'", options, voiture.getId(), queueName);
	if (queueName != null) {
	    String payload = new ObjectMapper().writeValueAsString(voiture);
	    rabbitTemplate.convertAndSend(queueName, payload);
	}
}
```

### Q4.2 Seul le premier appel déclenche la production de message(s) dans RabbitMQ. Pourquoi ?
Car la méthode `demarreConfigurationVoiture` est appelée lors de l'ajout d'une voiture.   
Dans cette méthode, on vérifie si la voiture est déjà en cours de configuration en regardant le statut des voitures. Si c'est le cas, on ne fait rien.

### Q4.3. Quelles sont les informations transmises lors de la confirmation de reconfiguration ? Avez-vous du ajouter des données dans la demande de reconfiguration ? Si oui, lesquelles ?
Non, aucune donnée n'a été ajoutée dans la demande de reconfiguration. Nous avons simplement utilisé les options de la voiture ainsi que le numéro de la machine pour les configurer un par un en faisant des appels RestTemplate vers `CatalogueApplication`.   
Une fois l'opération terminée, nous envoyons un message de confirmation à chain-manager avec en objet la voiture reconfigurée, de manière à ce que chain-manager puisse mettre à jour le statut de la voiture.


## 5. Synchronisation et démarrage de la configuration suivante
### Q5.1. Quel champ de Voiture dans chain-manager est particulièrement utile ici ? A quel moment ce champ doit-il être réellement initialisé ?


### Q5.2. Avez-vous du ajouter des informations dans certains messages ? Si oui, lesquelles ?


### Q5.3 Normalement, la voiture ne passe pas au statut TERMINE. Pourquoi ? Élaborer une stratégie qui permettrait de mitiger (à défaut de faire disparaitre) le problème.

## Comment lancer les applications ?
- Lancer les serveurs RabbitMQ et PostgreSQL via le docker avec la commande `docker-compose up -d`
- Se rendre sur http://localhost:15672/ pour accéder à l'interface web de RabbitMQ, avec les identifiants `guest` et `guest`
- Se rendre sur http://localhost:15672/#/queues et ajouter une *queue* `queue-machine1`, les autres *queues* seront créées __automatiquement__
- Lancer les applications avec la commande `mvn spring-boot:run` ou via l'IDE dans l'ordre suivant :
    - `catalogue`
    - `chain-manager`
    - `machine`

Commandes pour tester les queues :
- `curl -X POST -H "Content-Type: application/json" -d '{"nom": "option1"}' http://localhost:8080/option`. Cette commande ajoute une option au catalogue.
- `curl -X POST -H "Content-Type: application/json" -d '{"options": ["option1"]}' http://localhost:8081/voiture`. Cette commande ajoute une voiture et déclenche sa reconfiguration.
