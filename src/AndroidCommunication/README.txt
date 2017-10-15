Détails des programmes réalisés en communication client/serveur.

1. Premier test : KnockKnockClient/KnockKnockServer  : voir package ClientServeur

	- Utilisation d'une copie des fichiers présents dans le tutoriel et test d'une communication sur le même PC et entre 2 PC différents.
	
///
	
2. Second test : Communication de PC à PC des informations contenues dans un fichier texte à intervalles de temps réguliers

Les fichiers utilisés sont les suivants :
	- PCServer.java
	- AndroidSimulatorClient.java
	- PCServerProtocol.java
	
Le fonctionnement du programme se fait par démarrage de PCServer.java puis de AndroidSimulatorClient.java

///

3. Troisième test : Exécution d'un seul main contenant 2 thread (un pour le client et un pour le serveur) et relié au travail du module intégration

Les fichiers utilisés sont les suivants :
	- PCServerThread.java
	- AndroidSimulatorClientThread.java
	- PCServerProtocolThread.java
	- TestIntegration1.java dans le package Test
	
Le fonctionnement du programme est effectué par démarrage du de TestIntegration1.java dans le package Test.

///

4.Quatrième test : Test d'une communication avec un client android

Le client android doit envoyer une chaine de caractères (ici "hello") au serveur PC.

Les fichiers utilisés sont les suivants :
	- PCServertoAndroid.java
	- AndroidClient.java -> package androidcom
	- AsyncTaskCom.java -> package androidcom
	- PCtoAndroidProtocol.java
	
Le fonctionnement du programme se fait par démarrage de PCServertoAndroid.java puis de AndroidClient.java

///

5. Cinquième Test : On applique le protocole du test 3 au client :

Les fichiers utilisés sont les suivants :
	- PCServertoAndroid.java
	- AndroidClient.java -> package androidcomv2
	- AsyncTaskCom.java -> package androidcomv2
	- PCtoAndroidProtocol.java
	
Le fonctionnement du programme se fait par démarrage de PCServer.java puis de AndroidClient.java