D�tails des programmes r�alis�s en communication client/serveur.

1. Premier test : KnockKnockClient/KnockKnockServer  : voir package ClientServeur

	- Utilisation d'une copie des fichiers pr�sents dans le tutoriel et test d'une communication sur le m�me PC et entre 2 PC diff�rents.
	
///
	
2. Second test : Communication de PC � PC des informations contenues dans un fichier texte � intervalles de temps r�guliers

Les fichiers utilis�s sont les suivants :
	- PCServer.java
	- AndroidSimulatorClient.java
	- PCServerProtocol.java
	
Le fonctionnement du programme se fait par d�marrage de PCServer.java puis de AndroidSimulatorClient.java

///

3. Troisi�me test : Ex�cution d'un seul main contenant 2 thread (un pour le client et un pour le serveur) et reli� au travail du module int�gration

Les fichiers utilis�s sont les suivants :
	- PCServerThread.java
	- AndroidSimulatorClientThread.java
	- PCServerProtocolThread.java
	- TestIntegration1.java dans le package Test
	
Le fonctionnement du programme est effectu� par d�marrage du de TestIntegration1.java dans le package Test.

///

4.Quatri�me test : Test d'une communication avec un client android

Le client android doit envoyer une chaine de caract�res (ici "hello") au serveur PC.

Les fichiers utilis�s sont les suivants :
	- PCServertoAndroid.java
	- AndroidClient.java -> package androidcom
	- AsyncTaskCom.java -> package androidcom
	- PCtoAndroidProtocol.java
	
Le fonctionnement du programme se fait par d�marrage de PCServertoAndroid.java puis de AndroidClient.java

///

5. Cinqui�me Test : On applique le protocole du test 3 au client :

Les fichiers utilis�s sont les suivants :
	- PCServertoAndroid.java
	- AndroidClient.java -> package androidcomv2
	- AsyncTaskCom.java -> package androidcomv2
	- PCtoAndroidProtocol.java
	
Le fonctionnement du programme se fait par d�marrage de PCServer.java puis de AndroidClient.java