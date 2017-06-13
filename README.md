# Java_UPnP_Mouse_Controller
Composant UPnP permettant de contrôler la sourie de l'ordinateur

<strong>Description : </strong>

Ce composant permet de contrôler la sourie de l'ordinateur par UPnP. Il peut prendre en entrée des valeurs afin de déplacer le
curseur sur les axes X et Y. Le composant permet aussi de contrôler les boutons de la sourie. Ce composant peut éventuellement être
assemblé avec Android Remote Control et Adnroid UPnP Device Orientation pour respectivement contrôler les boutons de la sourie
et le curseur.

<strong>Lancement de l'application : </strong>

Pour lancer l'application il suffit de lancer le .jar se situant dans le répertoire build via un terminal en utilisant la commande
java -jar nomFichierJar.

Cette application ne présente aucune interface graphique.

<strong>Spécification UPnP : </strong>


Ce composant présente les services suivants :

  1. MouseButtonsService
  2. MouseCursorService
  
MouseButtonsService donne accès à la méthode suivante :

  1. setButtonCommand(String commandButton) : prend en entrée une commande sous forme de fichier XML et contenant une action qui 
  sera associèe à un des boutons de la sourie, par exemple CENTRE sera associé à un clic gauche.
  
MouseCursorService donne accès aux commandes suivantes :

  1. setXYPercent(String Commande) : prend en entrée un fichier XML qui contient les coordonnées en pourcentage du curseur de la
  sourie par rapport à la taille de l'écran sur les axes X et Y. Par exemple si X = 50 et Y = 50 le curseur sera au milieu de l'écran
  après appel de la méthode.
  
  2. setSubXY(String Commande) : prend en entrée un fichier XML qui contient une valeur qui sera soustraite aux coordonnées actuelles
  du curseur sur l'écran. 
  
Voici un schéma représentant ce composant : 

![alt tag](https://github.com/components-upnp/Java_UPnP_Mouse_Controller/blob/master/MouseController.png)

<strong>Maintenance : </strong>
  
C'est un projet Maven. Effectuer les modifications à faire, ajouter une configuration 
d'éxecution Maven avec la phase "package" pour exporter en .jar Executer cette commande.
  
